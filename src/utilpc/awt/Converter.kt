package utilpc.awt

import java.awt.CompositeContext
import java.awt.Compositeimport
import java.awt.RenderingHints
import java.awt.image.ColorModel
import java.awt.image.Raster
import java.awt.image.WritableRaster

class Converter(private val mode: Int) : Composite, CompositeContext {
    override fun compose(src: Raster, dst: Raster, out: WritableRaster) {
        comp3(src, dst, out)
    }

    override fun createContext(arg0: ColorModel, arg1: ColorModel, arg2: RenderingHints): CompositeContext {
        return this
    }

    override fun dispose() {}
    private fun comp3(src: Raster, dst: Raster, out: WritableRaster) {
        val w: Int = dst.getWidth()
        val h: Int = dst.getHeight()
        val dsts: IntArray = dst.getPixels(0, 0, w, h, IntArray(w * h * 3))
        for (i in 0 until w) for (j in 0 until h) {
            val ind = i * h + j
            if (mode == 0) {
                for (k in 0..2) dsts[ind * 3 + k] = 255 - dsts[ind * 3 + k]
            }
        }
        out.setPixels(0, 0, w, h, dsts)
    }
}
