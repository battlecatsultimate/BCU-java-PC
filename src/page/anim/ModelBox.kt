package page.anim

import common.system.P
import common.util.anim.EAnimS
import utilpc.awt.FG2D
import java.awt.*
import java.awt.Canvasimport
import java.awt.image.BufferedImage

internal class ModelBox : Canvas() {
    var prev: BufferedImage? = null
    val ori: P = P(0, 0)
    var siz = 0.5

    @get:Synchronized
    @set:Synchronized
    var ent: EAnimS? = null
    @Synchronized
    override fun paint(g: Graphics) {
        prev = image
        if (prev == null) return
        g.drawImage(prev, 0, 0, null)
        g.dispose()
    }

    protected val image: BufferedImage?
        protected get() {
            val w = width
            val h = height
            val img: BufferedImage = createImage(w, h) as BufferedImage
            val gra: Graphics2D = img.graphics as Graphics2D
            val gdt = GradientPaint(w / 2, 0, c0, w / 2, h / 2, c1, true)
            val p: Paint = gra.paint
            gra.paint = gdt
            gra.fillRect(0, 0, w, h)
            gra.paint = p
            gra.translate(w / 2, h * 3 / 4)
            if (ent != null) ent.draw(FG2D(gra), ori.copy().times(-1.0), siz)
            gra.dispose()
            return img
        }

    fun setEntity(ieAnim: EAnimS?) {
        ent = ieAnim
    }

    companion object {
        private const val serialVersionUID = 1L
        private val c0 = Color(70, 140, 160)
        private val c1 = Color(85, 185, 205)
    }

    init {
        ignoreRepaint = true
    }
}
