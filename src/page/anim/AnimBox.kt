package page.anim

import common.CommonStatic
import common.system.P
import common.util.anim.EAnimD
import common.util.anim.EAnimU
import main.Timer
import utilpc.awt.FG2D
import java.awt.*
import java.awt.Canvasimport
import java.awt.image.BufferedImage

com.google.api.client.json.jackson2.JacksonFactory
import java.awt.Graphics2D
import java.awt.GradientPaint

internal class AnimBox : Canvas() {
    var prev: BufferedImage? = null
    val ori: P = P(0, 0)
    var siz = 0.5
    var ent: EAnimD<*>? = null
    @Synchronized
    override fun paint(g: Graphics) {
        prev = image
        if (prev == null) return
        g.drawImage(prev, 0, 0, null)
        if (CommonStatic.getConfig().ref) {
            g.color = Color.ORANGE
            g.drawString("Time cost: " + Timer.Companion.inter + "%", 20, 20)
        }
        g.dispose()
    }

    protected val image: BufferedImage?
        protected get() {
            val w = width
            val h = height
            val img: BufferedImage = createImage(w, h) as BufferedImage
            if (img == null) return img
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

    fun setEntity(ieAnim: EAnimU?) {
        ent = ieAnim
    }

    fun setSele(`val`: Int) {
        if (ent != null) ent.sele = `val`
    }

    @Synchronized
    fun update() {
        if (ent != null) ent.update(true)
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
