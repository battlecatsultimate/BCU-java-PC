package page.basis

import page.battle.BattleBox.BBPainter
import utilpc.awt.FG2D
import java.awt.Canvasimport
import java.awt.Graphics
import java.awt.image.BufferedImage

internal class NyCasBox : Canvas() {
    private var ints: IntArray?
    @Synchronized
    override fun paint(g: Graphics) {
        if (ints == null) return
        val w = width
        val h = height
        val sw = 128
        val sh = 258
        val r = Math.min(1.0 * w / sw, 1.0 * h / sh)
        if (w * h == 0) return
        val img: BufferedImage = createImage(w, h) as BufferedImage ?: return
        BBPainter.Companion.drawNyCast(FG2D(img.getGraphics()), h, 0, r, ints)
        g.drawImage(img, 0, 0, null)
        g.dispose()
    }

    fun set(nyc: IntArray?) {
        ints = nyc
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ignoreRepaint = true
    }
}
