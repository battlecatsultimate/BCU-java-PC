package jogl.util

import common.CommonStatic
import common.system.fake.FakeImage
import common.system.fake.ImageBuilder
import common.system.files.FileData
import common.system.files.VFile
import utilpc.awt.FIBI
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.function.Supplier

class GLIB : ImageBuilder() {
    @Throws(IOException::class)
    override fun build(o: Any?): FakeImage? {
        if (o == null) return null
        if (o is FakeImage) return o
        if (CommonStatic.getConfig().icon) return FIBI.Companion.builder.build(o)
        if (o is FileData) return AmbImage(Supplier { o.stream })
        if (o is VFile<*>) return AmbImage(Supplier<InputStream> { o.getData().getStream() })
        if (o is ByteArray) return AmbImage(Supplier<InputStream> { ByteArrayInputStream(o as ByteArray?) })
        if (o is Supplier<*>) return AmbImage(o as Supplier<InputStream?>?)
        if (o is BufferedImage) return AmbImage(o as BufferedImage?)
        if (o is File) return AmbImage(o as File?)
        throw IOException("cannot parse input with class " + o.javaClass)
    }

    @Throws(IOException::class)
    override fun write(o: FakeImage, fmt: String?, out: Any?): Boolean {
        return FIBI.Companion.builder.write(o, fmt, out)
    }
}
