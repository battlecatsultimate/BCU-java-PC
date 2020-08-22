package jogl.util

import com.jogamp.opengl.util.texture.TextureData
import com.jogamp.opengl.util.texture.TextureIO
import com.jogamp.opengl.util.texture.awt.AWTTextureIO
import common.system.P
import common.system.fake.FakeImage
import jogl.GLStatic
import main.Printer
import java.awt.AlphaComposite
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream

class GLImage : FakeImage {
    private val par: GLImage?
    val data: TextureData
    protected val rect: IntArray

    private constructor(p: GLImage, vararg r: Int) {
        par = p
        data = p.data
        rect = r
    }

    private constructor(b: TextureData) {
        par = null
        data = b
        rect = intArrayOf(0, 0, data.getWidth(), data.getHeight())
    }

    override fun bimg(): BufferedImage? {
        return null
    }

    val height: Int
        get() = rect[3]

    fun getRect(): FloatArray {
        val ans = FloatArray(4)
        val br = root().rect
        ans[0] = P.Companion.reg((rect[0] + 0.5f) / br[2])
        ans[1] = P.Companion.reg((rect[1] + 0.5f) / br[3])
        ans[2] = P.Companion.reg((rect[2] - 1f) / br[2])
        ans[3] = P.Companion.reg((rect[3] - 1f) / br[3])
        if (!data.getMustFlipVertically()) {
            ans[1] = 1 - ans[1]
            ans[3] *= -1
        }
        return ans
    }

    override fun getRGB(i: Int, j: Int): Int {
        return 0
    }

    override fun getSubimage(i: Int, j: Int, k: Int, l: Int): GLImage? {
        return GLImage(this, rect[0] + i, rect[1] + j, k, l)
    }

    val width: Int
        get() = rect[2]

    override fun gl(): Any? {
        return this
    }

    val isValid: Boolean
        get() = true

    override fun setRGB(i: Int, j: Int, p: Int) {}
    override fun unload() {}
    fun root(): GLImage {
        return par?.root() ?: this
    }

    companion object {
        fun build(o: Any): GLImage? {
            var o = o
            try {
                var data: TextureData? = null
                if (o is ByteArray) o = ByteArrayInputStream(o)
                if (o is File) data = TextureIO.newTextureData(GLStatic.GLP, o, GLStatic.MIP, "PNG")
                if (o is InputStream) data = TextureIO.newTextureData(GLStatic.GLP, o, GLStatic.MIP, "PNG")
                if (o is BufferedImage) {
                    var bimg: BufferedImage = o as BufferedImage
                    bimg = check(bimg)
                    data = AWTTextureIO.newTextureData(GLStatic.GLP, bimg, GLStatic.MIP)
                }
                if (data == null) {
                    Printer.e("GLImage", 52, "failed to load data: $o")
                    return null
                }
                return GLImage(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        private fun check(b: BufferedImage): BufferedImage {
            var w: Int = b.getWidth()
            var h: Int = b.getHeight()
            if (w % 4 == 0 && h % 4 == 0) return b
            if (w and 3 > 0) w = (w or 3) + 1
            if (h and 3 > 0) h = (h or 3) + 1
            val ans = BufferedImage(w, h, b.getType())
            val g: Graphics2D = ans.createGraphics()
            g.setComposite(AlphaComposite.Src)
            g.drawImage(b, 0, 0, null)
            g.dispose()
            Printer.p("GLImage", 23, "redraw buffer")
            return ans
        }
    }
}
