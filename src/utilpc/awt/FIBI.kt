package utilpc.awt

import common.pack.Context
import common.system.fake.FakeImage
import common.system.fake.ImageBuilder
import common.system.files.FileData
import common.util.Data
import java.awt.image.BufferedImage
import java.io.*
import java.util.function.Supplier
import javax.imageio.ImageIO

class FIBI(read: BufferedImage?) : FakeImage {
    private val bimg: BufferedImage?
    override fun bimg(): BufferedImage? {
        return bimg
    }

    override fun getHeight(): Int {
        return bimg.getHeight()
    }

    override fun getRGB(i: Int, j: Int): Int {
        return bimg.getRGB(i, j)
    }

    override fun getSubimage(i: Int, j: Int, k: Int, l: Int): FIBI? {
        return try {
            builder.build(bimg.getSubimage(i, j, k, l)) as FIBI
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun getWidth(): Int {
        return bimg.getWidth()
    }

    override fun gl(): Any? {
        return null
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun setRGB(i: Int, j: Int, p: Int) {
        bimg.setRGB(i, j, p)
    }

    override fun unload() {}

    companion object {
        val builder: ImageBuilder = BIBuilder()
        fun build(bimg2: BufferedImage?): FakeImage? {
            return try {
                builder.build(bimg2)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    init {
        bimg = read
    }
}

internal class BIBuilder : ImageBuilder() {
    @Throws(IOException::class)
    override fun build(o: Any?): FakeImage? {
        if (o == null) return null
        if (o is BufferedImage) return FIBI(o as BufferedImage?)
        var b: BufferedImage? = null
        b = if (o is File) ImageIO.read(o as File?) else if (o is FileData) ImageIO.read(o.stream) else if (o is ByteArray) ImageIO.read(ByteArrayInputStream(o as ByteArray?)) else if (o is Supplier<*>) ImageIO.read((o as Supplier<InputStream?>).get()) else throw IOException("unknown class type " + o.javaClass)
        return if (b == null) null else FIBI(b)
    }

    @Throws(IOException::class)
    override fun write(img: FakeImage, fmt: String?, o: Any?): Boolean {
        val bimg: BufferedImage = img.bimg() as BufferedImage
        if (o is File) {
            Data.Companion.err(Context.RunExc { Context.Companion.check(o as File?) })
            return ImageIO.write(bimg, fmt, o as File?)
        } else if (o is OutputStream) return ImageIO.write(bimg, fmt, o as OutputStream?)
        return false
    }
}
