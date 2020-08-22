package res

import java.awt.Colorimport
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

/**
 * Class AnimatedGifEncoder - Encodes a GIF file consisting of one or more
 * frames.
 *
 * <pre>
 * Example:
 * AnimatedGifEncoder e = new AnimatedGifEncoder();
 * e.start(outputFileName);
 * e.setDelay(1000);   // 1 frame per sec
 * e.addFrame(image1);
 * e.addFrame(image2);
 * e.finish();
</pre> *
 *
 * No copyright asserted on the source code of this class. May be used for any
 * purpose, however, refer to the Unisys LZW patent for restrictions on use of
 * the associated LZWEncoder class. Please forward any corrections to
 * kweiner@fmsware.com.
 *
 * @author Kevin Weiner, FM Software
 * @version 1.03 November 2003
 */
class AnimatedGifEncoder {
    protected var width // image size
            = 0
    protected var height = 0
    protected var transparent: Color? = null // transparent color if given
    protected var transIndex // transparent index in color table
            = 0
    protected var repeat = -1 // no repeat
    protected var delay = 0 // frame delay (hundredths)
    protected var started = false // ready to output frames
    protected var out: OutputStream? = null
    protected var image // current frame
            : BufferedImage? = null
    protected var pixels // BGR byte array from frame
            : ByteArray?
    protected var indexedPixels // converted frame indexed to palette
            : ByteArray?
    protected var colorDepth // number of bit planes
            = 0
    protected var colorTab // RGB palette
            : ByteArray?
    protected var usedEntry = BooleanArray(256) // active palette entries
    protected var palSize = 7 // color table size (bits-1)
    protected var dispose = -1 // disposal code (-1 = use default)
    protected var closeStream = false // close stream when finished
    protected var firstFrame = true
    protected var sizeSet = false // if false, get size from first frame
    protected var sample = 10 // default sample interval for quantizer

    /**
     * Adds next GIF frame. The frame is not written immediately, but is actually
     * deferred until the next frame is received so that timing data can be
     * inserted. Invoking `finish()` flushes all frames. If
     * `setSize` was not invoked, the size of the first image is used for
     * all subsequent frames.
     *
     * @param im BufferedImage containing frame to write.
     * @return true if successful.
     */
    fun addFrame(im: BufferedImage?): Boolean {
        if (im == null || !started) {
            return false
        }
        var ok = true
        try {
            if (!sizeSet) {
                // use first frame's size
                setSize(im.width, im.height)
            }
            image = im
            imagePixels // convert to correct format if necessary
            analyzePixels() // build color table & map pixels
            if (firstFrame) {
                writeLSD() // logical screen descriptior
                writePalette() // global color table
                if (repeat >= 0) {
                    // use NS app extension to indicate reps
                    writeNetscapeExt()
                }
            }
            writeGraphicCtrlExt() // write graphic control extension
            writeImageDesc() // image descriptor
            if (!firstFrame) {
                writePalette() // local color table
            }
            writePixels() // encode and write pixel data
            firstFrame = false
        } catch (e: IOException) {
            ok = false
        }
        return ok
    }

    /**
     * Flushes any pending data and closes output file. If writing to an
     * OutputStream, the stream is not closed.
     */
    fun finish(): Boolean {
        if (!started) return false
        var ok = true
        started = false
        try {
            out!!.write(0x3b) // gif trailer
            out!!.flush()
            if (closeStream) {
                out!!.close()
            }
        } catch (e: IOException) {
            ok = false
        }

        // reset for subsequent use
        transIndex = 0
        out = null
        image = null
        pixels = null
        indexedPixels = null
        colorTab = null
        closeStream = false
        firstFrame = true
        return ok
    }

    /**
     * Sets the delay time between each frame, or changes it for subsequent frames
     * (applies to last frame added).
     *
     * @param ms int delay time in milliseconds
     */
    fun setDelay(ms: Int) {
        delay = Math.round(ms / 10.0f)
    }

    /**
     * Sets the GIF frame disposal code for the last added frame and any subsequent
     * frames. Default is 0 if no transparent color has been set, otherwise 2.
     *
     * @param code int disposal code.
     */
    fun setDispose(code: Int) {
        if (code >= 0) {
            dispose = code
        }
    }

    /**
     * Sets frame rate in frames per second. Equivalent to
     * `setDelay(1000/fps)`.
     *
     * @param fps float frame rate (frames per second)
     */
    fun setFrameRate(fps: Float) {
        if (fps != 0f) {
            delay = Math.round(100f / fps)
        }
    }

    /**
     * Sets quality of color quantization (conversion of images to the maximum 256
     * colors allowed by the GIF specification). Lower values (minimum = 1) produce
     * better colors, but slow processing significantly. 10 is the default, and
     * produces good color mapping at reasonable speeds. Values greater than 20 do
     * not yield significant improvements in speed.
     *
     * @param quality int greater than 0.
     * @return
     */
    fun setQuality(quality: Int) {
        var quality = quality
        if (quality < 1) quality = 1
        sample = quality
    }

    /**
     * Sets the number of times the set of GIF frames should be played. Default is
     * 1; 0 means play indefinitely. Must be invoked before the first image is
     * added.
     *
     * @param iter int number of iterations.
     * @return
     */
    fun setRepeat(iter: Int) {
        if (iter >= 0) {
            repeat = iter
        }
    }

    /**
     * Sets the GIF frame size. The default size is the size of the first frame
     * added if this method is not invoked.
     *
     * @param w int frame width.
     * @param h int frame width.
     */
    fun setSize(w: Int, h: Int) {
        if (started && !firstFrame) return
        width = w
        height = h
        if (width < 1) width = 320
        if (height < 1) height = 240
        sizeSet = true
    }

    /**
     * Sets the transparent color for the last added frame and any subsequent
     * frames. Since all colors are subject to modification in the quantization
     * process, the color in the final palette for each frame closest to the given
     * color becomes the transparent color for that frame. May be set to null to
     * indicate no transparent color.
     *
     * @param c Color to be treated as transparent on display.
     */
    fun setTransparent(c: Color?) {
        transparent = c
    }

    /**
     * Initiates GIF file creation on the given stream. The stream is not closed
     * automatically.
     *
     * @param os OutputStream on which GIF images are written.
     * @return false if initial write failed.
     */
    fun start(os: OutputStream?): Boolean {
        if (os == null) return false
        var ok = true
        closeStream = false
        out = os
        try {
            writeString("GIF89a") // header
        } catch (e: IOException) {
            ok = false
        }
        return ok.also { started = it }
    }

    /**
     * Initiates writing of a GIF file with the specified name.
     *
     * @param file String containing output file name.
     * @return false if open or initial write failed.
     */
    fun start(file: String?): Boolean {
        var ok = true
        try {
            out = BufferedOutputStream(FileOutputStream(file))
            ok = start(out)
            closeStream = true
        } catch (e: IOException) {
            ok = false
        }
        return ok.also { started = it }
    }

    /**
     * Analyzes image colors and creates color map.
     */
    protected fun analyzePixels() {
        val len = pixels!!.size
        val nPix = len / 3
        indexedPixels = ByteArray(nPix)
        val nq = NeuQuant(pixels, len, sample)
        // initialize quantizer
        colorTab = nq.process() // create reduced palette
        // convert map from BGR to RGB
        run {
            var i = 0
            while (i < colorTab!!.size) {
                val temp = colorTab!![i]
                colorTab!![i] = colorTab!![i + 2]
                colorTab!![i + 2] = temp
                usedEntry[i / 3] = false
                i += 3
            }
        }
        // map image pixels to new palette
        var k = 0
        for (i in 0 until nPix) {
            val index: Int = nq.map(pixels!![k++] and 0xff, pixels!![k++] and 0xff, pixels!![k++] and 0xff)
            usedEntry[index] = true
            indexedPixels!![i] = index.toByte()
        }
        pixels = null
        colorDepth = 8
        palSize = 7
        // get closest match to transparent color if specified
        if (transparent != null) {
            transIndex = findClosest(transparent!!)
        }
    }

    /**
     * Returns index of palette color closest to c
     *
     */
    protected fun findClosest(c: Color): Int {
        if (colorTab == null) return -1
        val r = c.red
        val g = c.green
        val b = c.blue
        var minpos = 0
        var dmin = 256 * 256 * 256
        val len = colorTab!!.size
        var i = 0
        while (i < len) {
            val dr: Int = r - (colorTab!![i++] and 0xff)
            val dg: Int = g - (colorTab!![i++] and 0xff)
            val db: Int = b - (colorTab!![i] and 0xff)
            val d = dr * dr + dg * dg + db * db
            val index = i / 3
            if (usedEntry[index] && d < dmin) {
                dmin = d
                minpos = index
            }
            i++
        }
        return minpos
    }// create new image with right size/format

    /**
     * Extracts image pixels into byte array "pixels"
     */
    protected val imagePixels: Unit
        protected get() {
            val w: Int = image.getWidth()
            val h: Int = image.getHeight()
            val type: Int = image.getType()
            if (w != width || h != height || type != BufferedImage.TYPE_3BYTE_BGR) {
                // create new image with right size/format
                val temp = BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR)
                val g: Graphics2D = temp.createGraphics()
                g.drawImage(image, 0, 0, null)
                image = temp
            }
            pixels = (image.getRaster().dataBuffer as DataBufferByte).data
        }

    /**
     * Writes Graphic Control Extension
     */
    @Throws(IOException::class)
    protected fun writeGraphicCtrlExt() {
        out!!.write(0x21) // extension introducer
        out!!.write(0xf9) // GCE label
        out!!.write(4) // data block size
        val transp: Int
        var disp: Int
        if (transparent == null) {
            transp = 0
            disp = 0 // dispose = no action
        } else {
            transp = 1
            disp = 2 // force clear if using transparent color
        }
        if (dispose >= 0) {
            disp = dispose and 7 // user override
        }
        disp = disp shl 2
        // packed fields
        out!!.write(0 or  // 1:3 reserved
                disp or  // 4:6 disposal
                0 or  // 7 user input - 0 = none
                transp) // 8 transparency flag
        writeShort(delay) // delay x 1/100 sec
        out!!.write(transIndex) // transparent color index
        out!!.write(0) // block terminator
    }

    /**
     * Writes Image Descriptor
     */
    @Throws(IOException::class)
    protected fun writeImageDesc() {
        out!!.write(0x2c) // image separator
        writeShort(0) // image position x,y = 0,0
        writeShort(0)
        writeShort(width) // image size
        writeShort(height)
        // packed fields
        if (firstFrame) {
            // no LCT - GCT is used for first (or only) frame
            out!!.write(0)
        } else {
            // specify normal LCT
            out!!.write(0x80 or  // 1 local color table 1=yes
                    0 or  // 2 interlace - 0=no
                    0 or  // 3 sorted - 0=no
                    0 or  // 4-5 reserved
                    palSize) // 6-8 size of color table
        }
    }

    /**
     * Writes Logical Screen Descriptor
     */
    @Throws(IOException::class)
    protected fun writeLSD() {
        // logical screen size
        writeShort(width)
        writeShort(height)
        // packed fields
        out!!.write(0x80 or  // 1 : global color table flag = 1 (gct used)
                0x70 or  // 2-4 : color resolution = 7
                0x00 or  // 5 : gct sort flag = 0
                palSize) // 6-8 : gct size
        out!!.write(0) // background color index
        out!!.write(0) // pixel aspect ratio - assume 1:1
    }

    /**
     * Writes Netscape application extension to define repeat count.
     */
    @Throws(IOException::class)
    protected fun writeNetscapeExt() {
        out!!.write(0x21) // extension introducer
        out!!.write(0xff) // app extension label
        out!!.write(11) // block size
        writeString("NETSCAPE" + "2.0") // app id + auth code
        out!!.write(3) // sub-block size
        out!!.write(1) // loop sub-block id
        writeShort(repeat) // loop count (extra iterations, 0=repeat forever)
        out!!.write(0) // block terminator
    }

    /**
     * Writes color table
     */
    @Throws(IOException::class)
    protected fun writePalette() {
        out!!.write(colorTab, 0, colorTab!!.size)
        val n = 3 * 256 - colorTab!!.size
        for (i in 0 until n) {
            out!!.write(0)
        }
    }

    /**
     * Encodes and writes pixel data
     */
    @Throws(IOException::class)
    protected fun writePixels() {
        val encoder = LZWEncoder(width, height, indexedPixels, colorDepth)
        encoder.encode(out)
    }

    /**
     * Write 16-bit value to output stream, LSB first
     */
    @Throws(IOException::class)
    protected fun writeShort(value: Int) {
        out!!.write(value and 0xff)
        out!!.write(value shr 8 and 0xff)
    }

    /**
     * Writes string to output stream
     */
    @Throws(IOException::class)
    protected fun writeString(s: String) {
        for (i in 0 until s.length) {
            out.write(s[i] as Byte.toInt())
        }
    }
}
