package utilpc.awt

import page.view.ViewBox.Conf
import java.awt.CompositeContext
import java.awt.Compositeimport
import java.awt.RenderingHints
import java.awt.image.ColorModel
import java.awt.image.Raster
import java.awt.image.WritableRaster

class Blender(private val opa: Int, private val glow: Int) : Composite, CompositeContext {
    override fun compose(src: Raster, dst: Raster, out: WritableRaster) {
        if (Conf.white) comp4(src, dst, out) else comp3(src, dst, out)
    }

    override fun createContext(arg0: ColorModel, arg1: ColorModel, arg2: RenderingHints): CompositeContext {
        return this
    }

    override fun dispose() {}
    private fun comp3(src: Raster, dst: Raster, out: WritableRaster) {
        val w: Int = src.getWidth()
        val h: Int = src.getHeight()
        val srcs: IntArray = src.getPixels(0, 0, w, h, IntArray(w * h * 4))
        val dsts: IntArray = dst.getPixels(0, 0, w, h, IntArray(w * h * 3))
        for (i in 0 until w) for (j in 0 until h) {
            val ind = i * h + j
            val a = srcs[ind shl 2 or 3] * opa shr 8
            for (k in 0..2) {
                if (glow == 1) dsts[ind * 3 + k] = Math.min(255, dsts[ind * 3 + k] + (srcs[ind shl 2 or k] * a shr 8)) else if (glow == 2) dsts[ind * 3 + k] -= dsts[ind * 3 + k] * (255 - srcs[ind shl 2 or k]) * a shr 16 else if (glow == 3) dsts[ind * 3 + k] += (255 - dsts[ind * 3 + k]) * srcs[ind shl 2 or k] * a shr 16 else if (glow == -1) dsts[ind * 3 + k] = Math.max(0, dsts[ind * 3 + k] - (srcs[ind shl 2 or k] * a shr 8)) else dsts[ind * 3 + k] = dsts[ind * 3 + k] * (255 - a) + srcs[ind shl 2 or k] * a shr 8
            }
        }
        out.setPixels(0, 0, w, h, dsts)
    }

    private fun comp4(src: Raster, dst: Raster, out: WritableRaster) {
        val w: Int = src.getWidth()
        val h: Int = src.getHeight()
        val srcs: IntArray = src.getPixels(0, 0, w, h, IntArray(w * h * 4))
        val dsts: IntArray = dst.getPixels(0, 0, w, h, IntArray(w * h * 4))
        for (i in 0 until w) for (j in 0 until h) {
            val ind = i * h + j
            var a = srcs[ind shl 2 or 3] * opa shr 8
            for (k in 0..2) {
                val idx = ind shl 2 or k
                if (glow == 1) dsts[idx] = Math.min(255, dsts[idx] + (srcs[idx] * a shr 8)) else if (glow == 2) dsts[idx] -= dsts[idx] * (255 - srcs[idx]) * a shr 16 else if (glow == 3) dsts[idx] += (255 - dsts[idx]) * srcs[idx] * a shr 16 else dsts[idx] = dsts[idx] * (255 - a) + srcs[idx] * a shr 8
            }
            if (glow == 1) {
                a = (srcs[ind shl 2] + srcs[ind shl 2 or 1] + srcs[ind shl 2 or 2]) * a / 3 shr 8
                dsts[ind shl 2 or 3] = (dsts[ind shl 2 or 3] * (255 - a) shr 8) + a
            } else dsts[ind shl 2 or 3] = (dsts[ind shl 2 or 3] * (255 - a) shr 8) + a
        }
        out.setPixels(0, 0, w, h, dsts)
    }
}
