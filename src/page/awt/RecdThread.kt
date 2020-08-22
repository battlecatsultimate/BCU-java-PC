package page.awt

import common.pack.Context
import common.util.Data
import io.BCUWriter
import org.jcodec.api.awt.AWTSequenceEncoder
import page.RetFunc
import res.AnimatedGifEncoder
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO

abstract class RecdThread protected constructor(list: Queue<BufferedImage?>, bip: RetFunc?) : Thread() {
    private class GIFThread(list: Queue<BufferedImage?>, path: String?, bip: RetFunc?) : RecdThread(list, bip) {
        private val gif: AnimatedGifEncoder
        override fun finish() {
            gif.finish()
        }

        override fun quit() {
            finish()
        }

        override fun recd(bimg: BufferedImage?) {
            gif.addFrame(bimg)
        }

        init {
            gif = AnimatedGifEncoder()
            gif.setDelay(33)
            gif.setRepeat(0)
            BCUWriter.writeGIF(gif, path)
        }
    }

    private class MP4Thread(list: Queue<BufferedImage?>, str: String, bip: RetFunc?) : RecdThread(list, bip) {
        val file: File
        private var encoder: AWTSequenceEncoder? = null
        override fun finish() {
            try {
                encoder.finish()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun quit() {
            finish()
            Data.Companion.err(Context.RunExc { Context.Companion.delete(file) })
        }

        override fun recd(bimg: BufferedImage?) {
            try {
                encoder.encodeImage(bimg.getSubimage(0, 0, fw, fh))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        init {
            file = File("./img/$str.mp4")
            try {
                encoder = AWTSequenceEncoder.create30Fps(file)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private class PNGThread(list: Queue<BufferedImage?>, out: String, bip: RetFunc?) : RecdThread(list, bip) {
        private val path: String
        private var count = 0
        override fun finish() {}
        override fun quit() {
            Data.Companion.err(Context.RunExc { Context.Companion.delete(File(path)) })
        }

        override fun recd(bimg: BufferedImage?) {
            count++
            var str = ""
            if (count < 10000) str += "0"
            if (count < 1000) str += "0"
            if (count < 100) str += "0"
            if (count < 10) str += "0"
            str += count
            val f = File("$path$str.png")
            Data.Companion.err(Context.RunExc { Context.Companion.check(f) })
            try {
                ImageIO.write(bimg, "PNG", f)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        init {
            path = "./img/$out/"
            val f = File(path)
            if (f.exists()) Data.Companion.err(Context.RunExc { Context.Companion.delete(f) })
        }
    }

    val bimgs: Queue<BufferedImage?>
    private val p: RetFunc?
    var fw = 0
    var fh = 0
    var end = false
    var quit = false
    fun remain(): Int {
        synchronized(bimgs) { return bimgs.size }
    }

    override fun run() {
        while (true) {
            var size = 0
            synchronized(this) {
                if (quit) break
                synchronized(bimgs) { size = bimgs.size }
                if (bimgs.size == 0 && end) break
            }
            if (size > 0) {
                var bimg: BufferedImage?
                synchronized(bimgs) { bimg = bimgs.poll() }
                if (fw <= 0 || fh <= 0) {
                    fw = bimg.getWidth() / 2 * 2
                    fh = bimg.getHeight() / 2 * 2
                }
                if (fw <= 0 || fh <= 0) continue
                recd(bimg)
                try {
                    sleep(1)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            } else try {
                sleep(30)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        if (quit) {
            quit()
            return
        }
        finish()
        if (p != null) p.callBack(null)
    }

    abstract fun finish()
    abstract fun quit()
    abstract fun recd(bimg: BufferedImage?)

    companion object {
        const val PNG = 0
        const val MP4 = 1
        const val GIF = 2
        fun getIns(bip: RetFunc?, qb: Queue<BufferedImage?>, out: String, type: Int): RecdThread? {
            if (type == PNG) return PNGThread(qb, out, bip)
            if (type == MP4) return MP4Thread(qb, out, bip)
            return if (type == GIF) GIFThread(qb, out, bip) else null
        }
    }

    init {
        p = bip
        bimgs = list
    }
}
