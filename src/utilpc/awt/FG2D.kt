package utilpc.awt

import common.system.fake.FakeGraphics
import common.system.fake.FakeImage
import common.system.fake.FakeTransform
import java.awt.*
import java.awt.geom.AffineTransform

class FG2D(graphics: Graphics) : FakeGraphics {
    private val g: Graphics2D
    private val comp: Composite
    override fun colRect(x: Int, y: Int, w: Int, h: Int, r: Int, gr: Int, b: Int, a: Int) {
        val al = if (a >= 0) a else 255
        val c = Color(r, gr, b, al)
        g.setColor(c)
        g.fillRect(x, y, w, h)
    }

    override fun drawImage(bimg: FakeImage, i: Double, j: Double) {
        g.drawImage(bimg.bimg() as Image, i.toInt(), j.toInt(), null)
    }

    override fun drawImage(bimg: FakeImage, ix: Double, iy: Double, iw: Double, ih: Double) {
        g.drawImage(bimg.bimg() as Image, ix.toInt(), iy.toInt(), iw.toInt(), ih.toInt(), null)
    }

    override fun drawLine(i: Int, j: Int, x: Int, y: Int) {
        g.drawLine(i, j, x, y)
    }

    override fun drawOval(i: Int, j: Int, k: Int, l: Int) {
        g.drawOval(i, j, k, l)
    }

    override fun drawRect(x: Int, y: Int, x2: Int, y2: Int) {
        g.drawRect(x, y, x2, y2)
    }

    override fun fillOval(i: Int, j: Int, k: Int, l: Int) {
        g.fillOval(i, j, k, l)
    }

    override fun fillRect(x: Int, y: Int, w: Int, h: Int) {
        g.fillRect(x, y, w, h)
    }

    override fun getTransform(): FakeTransform {
        return FTAT(g.getTransform())
    }

    override fun gradRect(x: Int, y: Int, w: Int, h: Int, a: Int, b: Int, c: IntArray, d: Int, e: Int, f: IntArray) {
        g.setPaint(GradientPaint(a, b, Color(c[0], c[1], c[2]), d, e, Color(f[0], f[1], f[2])))
        g.fillRect(x, y, w, h)
    }

    override fun rotate(d: Double) {
        g.rotate(d)
    }

    override fun scale(hf: Int, vf: Int) {
        g.scale(hf.toDouble(), vf.toDouble())
    }

    override fun setColor(c: Int) {
        if (c == FakeGraphics.Companion.RED) g.setColor(Color.RED)
        if (c == FakeGraphics.Companion.YELLOW) g.setColor(Color.YELLOW)
        if (c == FakeGraphics.Companion.BLACK) g.setColor(Color.BLACK)
        if (c == FakeGraphics.Companion.MAGENTA) g.setColor(Color.MAGENTA)
        if (c == FakeGraphics.Companion.BLUE) g.setColor(Color.BLUE)
        if (c == FakeGraphics.Companion.CYAN) g.setColor(Color.CYAN)
        if (c == FakeGraphics.Companion.WHITE) g.setColor(Color.WHITE)
    }

    override fun setComposite(mode: Int, p0: Int, p1: Int) {
        if (mode == FakeGraphics.Companion.DEF) g.setComposite(comp)
        if (mode == FakeGraphics.Companion.TRANS) g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (p0 / 256.0).toFloat()))
        if (mode == FakeGraphics.Companion.BLEND) g.setComposite(Blender(p0, p1))
        if (mode == FakeGraphics.Companion.GRAY) g.setComposite(Converter(p0))
    }

    override fun setRenderingHint(key: Int, `val`: Int) {
        g.setRenderingHint(KEYS[key], VALS[key][`val`])
    }

    override fun setTransform(at: FakeTransform) {
        g.setTransform(at.getAT() as AffineTransform)
    }

    override fun translate(x: Double, y: Double) {
        g.translate(x, y)
    }

    companion object {
        private val KAS: Any = RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED
        private val KAD: Any = RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT
        private val KAQ: Any = RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY
        private val KCS: Any = RenderingHints.VALUE_COLOR_RENDER_SPEED
        private val KCD: Any = RenderingHints.VALUE_COLOR_RENDER_DEFAULT
        private val KCQ: Any = RenderingHints.VALUE_COLOR_RENDER_QUALITY
        private val KFS: Any = RenderingHints.VALUE_FRACTIONALMETRICS_OFF
        private val KFD: Any = RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT
        private val KFQ: Any = RenderingHints.VALUE_FRACTIONALMETRICS_ON
        private val KIS: Any = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
        private val KID: Any = RenderingHints.VALUE_INTERPOLATION_BILINEAR
        private val KIQ: Any = RenderingHints.VALUE_INTERPOLATION_BICUBIC
        private val KA: RenderingHints.Key = RenderingHints.KEY_ALPHA_INTERPOLATION
        private val KC: RenderingHints.Key = RenderingHints.KEY_COLOR_RENDERING
        private val KF: RenderingHints.Key = RenderingHints.KEY_FRACTIONALMETRICS
        private val KI: RenderingHints.Key = RenderingHints.KEY_INTERPOLATION
        private val KEYS: Array<RenderingHints.Key> = arrayOf<RenderingHints.Key>(KA, KC, KF, KI)
        private val VALS = arrayOf(arrayOf(KAS, KAD, KAQ), arrayOf(KCS, KCD, KCQ), arrayOf(KFS, KFD, KFQ), arrayOf(KIS, KID, KIQ))
    }

    init {
        g = graphics as Graphics2D
        comp = g.getComposite()
    }
}
