package utilpc.awt

import common.system.fake.FakeImage
import common.system.fake.ImageBuilder
import utilpc.awt.FIBI
import java.io.IOException

class PCIB : ImageBuilder() {
    @Throws(IOException::class)
    override fun build(o: Any?): FakeImage? {
        if (o == null) return null
        return if (o is FakeImage) o else FIBI.Companion.builder.build(o)
    }

    @Throws(IOException::class)
    override fun write(o: FakeImage, fmt: String?, out: Any?): Boolean {
        return FIBI.Companion.builder.write(o, fmt, out)
    }
}
