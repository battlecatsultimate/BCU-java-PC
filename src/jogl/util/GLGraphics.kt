package jogl.util

import com.jogamp.opengl.GL
import com.jogamp.opengl.GL2
import com.jogamp.opengl.GL2ES3
import common.system.P
import common.system.fake.FakeGraphics
import common.system.fake.FakeImage
import common.system.fake.FakeTransform
import jogl.util.GLGraphics.GeomG
import jogl.util.ResManager
import java.awt.Color

class GLGraphics(gl2: GL2, wid: Int, hei: Int) : GeoAuto {
    class GeomG(private val gra: GLGraphics, gl2: GL2) {
        private val g: GL2
        private var color: Int? = null
        fun colRect(x: Int, y: Int, w: Int, h: Int, r: Int, gr: Int, b: Int, a: Int) {
            checkMode()
            if (a == -1) setColor(r.toFloat(), gr.toFloat(), b.toFloat()) else g.glColor4f(r / 256f, gr / 256f, b / 256f, a / 256f)
            color = null
            g.glBegin(GL2ES3.GL_QUADS)
            addP(x, y)
            addP(x + w, y)
            addP(x + w, y + h)
            addP(x, y + h)
            g.glEnd()
        }

        fun drawLine(i: Int, j: Int, x: Int, y: Int) {
            checkMode()
            setColor()
            g.glBegin(GL.GL_LINES)
            addP(i, j)
            addP(x, y)
            g.glEnd()
        }

        fun drawOval(i: Int, j: Int, k: Int, l: Int) {
            // checkMode();
            // setColor();
            // TODO circular
        }

        fun drawRect(x: Int, y: Int, w: Int, h: Int) {
            checkMode()
            setColor()
            g.glBegin(GL.GL_LINE_LOOP)
            addP(x, y)
            addP(x + w, y)
            addP(x + w, y + h)
            addP(x, y + h)
            g.glEnd()
        }

        fun fillOval(i: Int, j: Int, k: Int, l: Int) {
            // checkMode();
            // setColor();
            // TODO circular
        }

        fun fillRect(x: Int, y: Int, w: Int, h: Int) {
            checkMode()
            setColor()
            g.glBegin(GL2ES3.GL_QUADS)
            addP(x, y)
            addP(x + w, y)
            addP(x + w, y + h)
            addP(x, y + h)
            g.glEnd()
        }

        fun gradRect(x: Int, y: Int, w: Int, h: Int, a: Int, b: Int, c: IntArray, d: Int, e: Int, f: IntArray) {
            checkMode()
            val vec = P(d - a, e - b)
            var l: Double = vec.abs()
            l *= l
            g.glBegin(GL2ES3.GL_QUADS)
            for (i in 0..3) {
                var px = x
                var py = y
                if (i == 2 || i == 3) px += w
                if (i == 1 || i == 2) py += h
                var cx = (vec.dotP(P(px - a, py - b)) / l) as Float
                cx = P.Companion.reg(cx)
                val cs = FloatArray(3)
                for (j in 0..2) cs[j] = c[j] + cx * (f[j] - c[j])
                setColor(cs[0], cs[1], cs[2])
                addP(px, py)
            }
            g.glEnd()
            color = null
        }

        fun setColor(c: Int) {
            if (c == FakeGraphics.Companion.RED) color = Color.RED.rgb
            if (c == FakeGraphics.Companion.YELLOW) color = Color.YELLOW.rgb
            if (c == FakeGraphics.Companion.BLACK) color = Color.BLACK.rgb
            if (c == FakeGraphics.Companion.MAGENTA) color = Color.MAGENTA.rgb
            if (c == FakeGraphics.Companion.BLUE) color = Color.BLUE.rgb
            if (c == FakeGraphics.Companion.CYAN) color = Color.CYAN.rgb
            if (c == FakeGraphics.Companion.WHITE) color = Color.WHITE.rgb
        }

        private fun addP(x: Int, y: Int) {
            gra.addP(x.toDouble(), y.toDouble())
        }

        private fun checkMode() {
            gra.checkMode(PURE)
        }

        private fun setColor() {
            if (color == null) return
            setColor((color!! shr 16 and 255.toFloat().toInt()).toFloat(), (color!! shr 8 and 255.toFloat().toInt()).toFloat(), (color!! and 255.toFloat().toInt()).toFloat())
        }

        private fun setColor(c0: Float, c1: Float, c2: Float) {
            g.glColor3f(c0 / 256f, c1 / 256f, c2 / 256f)
        }

        init {
            g = gl2
        }
    }

    private class GLC(var mode: Int, var p0: Int, var p1: Int) {
        var done = false
    }

    private class GLT : FakeTransform {
        val data = FloatArray(6)
        val aT: Any?
            get() = null
    }

    private val g: GL2
    private val tm: ResManager?
    override val geo: GeomG
    private val sw: Int
    private val sh: Int
    private var trans = floatArrayOf(1f, 0f, 0f, 0f, 1f, 0f)
    private var mode = PURE
    private var bind = 0
    private var comp = GLC(FakeGraphics.Companion.DEF, 0, 0)
    fun dispose() {
        checkMode(PURE)
        count--
    }

    override fun drawImage(bimg: FakeImage, x: Double, y: Double) {
        drawImage(bimg, x, y, bimg.getWidth().toDouble(), bimg.getHeight().toDouble())
    }

    override fun drawImage(bimg: FakeImage, x: Double, y: Double, w: Double, h: Double) {
        checkMode(IMG)
        val gl: GLImage = bimg.gl() as GLImage ?: return
        compImpl()
        bind(tm!!.load(this, gl))
        g.glBegin(GL2ES3.GL_QUADS)
        val r: FloatArray = gl.getRect()
        g.glTexCoord2f(r[0], r[1])
        addP(x, y)
        g.glTexCoord2f(r[0] + r[2], r[1])
        addP(x + w, y)
        g.glTexCoord2f(r[0] + r[2], r[1] + r[3])
        addP(x + w, y + h)
        g.glTexCoord2f(r[0], r[1] + r[3])
        addP(x, y + h)
        g.glEnd()
    }

    var transform: FakeTransform
        get() {
            val glt = GLT()
            glt.data = trans.clone()
            return glt
        }
        set(at) {
            trans = (at as GLT).data.clone()
        }

    override fun rotate(d: Double) {
        val c = Math.cos(d).toFloat()
        val s = Math.sin(d).toFloat()
        val f0 = trans[0] * c + trans[1] * s
        val f1 = trans[0] * -s + trans[1] * c
        val f3 = trans[3] * c + trans[4] * s
        val f4 = trans[3] * -s + trans[4] * c
        trans[0] = f0
        trans[1] = f1
        trans[3] = f3
        trans[4] = f4
    }

    override fun scale(hf: Int, vf: Int) {
        trans[0] *= hf
        trans[3] *= hf
        trans[1] *= vf
        trans[4] *= vf
    }

    override fun setComposite(mode: Int, p0: Int, p1: Int) {
        if (mode == FakeGraphics.Companion.GRAY) { // 1-d
            checkMode(PURE)
            g.glBlendFunc(GL.GL_ONE_MINUS_DST_COLOR, GL.GL_ZERO)
            setColor(FakeGraphics.Companion.WHITE)
        } else comp = GLC(mode, p0, p1)
    }

    override fun setRenderingHint(key: Int, `object`: Int) {}
    override fun translate(x: Double, y: Double) {
        (trans[2] += trans[0] * x + trans[1] * y).toFloat()
        (trans[5] += trans[3] * x + trans[4] * y).toFloat()
    }

    fun bind(id: Int) {
        if (bind == id) return
        g.glBindTexture(GL.GL_TEXTURE_2D, id)
        bind = id
    }

    private fun addP(x: Double, y: Double) {
        val fx = trans[0] * x + trans[1] * y + trans[2]
        val fy = trans[3] * x + trans[4] * y + trans[5]
        g.glVertex2f((2 * fx / sw - 1).toFloat(), (1 - 2 * fy / sh).toFloat())
    }

    private fun checkMode(i: Int) {
        if (mode == i) return
        val premode = mode
        mode = i
        if (premode == IMG) {
            g.glDisable(GL.GL_TEXTURE_2D)
            g.glUseProgram(0)
            g.glEnable(GL.GL_BLEND)
            g.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA)
        }
        if (mode == IMG) {
            g.glEnable(GL.GL_TEXTURE_2D)
            g.glEnable(GL.GL_BLEND)
            g.glUseProgram(tm!!.prog)
        }
    }

    private fun compImpl() {
        if (comp.done) return
        val mode = comp.mode
        comp.done = true
        if (mode == FakeGraphics.Companion.DEF) {
            // sC *sA + dC *(1-sA)
            g.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA)
            g.glUniform1i(tm!!.mode, 0)
        }
        if (mode == FakeGraphics.Companion.TRANS) {
            g.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA)
            g.glUniform1i(tm!!.mode, 1)
            g.glUniform1f(tm!!.para, comp.p0 * 1.0f / 256)
        }
        if (mode == FakeGraphics.Companion.BLEND) {
            g.glUniform1f(tm!!.para, comp.p0 * 1.0f / 256)
            if (comp.p1 == 0) {
                g.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA)
                g.glUniform1i(tm!!.mode, 1)
            } else if (comp.p1 == 1) { // d+s*a
                g.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE)
                g.glUniform1i(tm!!.mode, 1) // sA=sA*p
            } else if (comp.p1 == 2) { // d*(1-a+s*a)
                g.glBlendFunc(GL.GL_ZERO, GL.GL_SRC_COLOR)
                g.glUniform1i(tm!!.mode, 2) // sA=sA*p, sC=1-sA+sC*sA
            } else if (comp.p1 == 3) { // d+(1-d)*s*a
                g.glBlendFunc(GL.GL_ONE_MINUS_DST_COLOR, GL.GL_ONE)
                g.glUniform1i(tm!!.mode, 1) // sA=sA*p
            } else if (comp.p1 == -1) { // d-s*a
                g.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE)
                g.glUniform1i(tm!!.mode, 3) // sA=-sA*p
            }
        }
    }

    companion object {
        private const val PURE = 0
        private const val IMG = 1
        var count = 0
    }

    init {
        g = gl2
        geo = GeomG(this, gl2)
        tm = ResManager.Companion.get(g)
        sw = wid
        sh = hei
        count++
        g.glClear(GL.GL_COLOR_BUFFER_BIT or GL.GL_DEPTH_BUFFER_BIT)
        g.glLoadIdentity()
    }
}

internal interface GeoAuto : FakeGraphics {
    override fun colRect(x: Int, y: Int, w: Int, h: Int, r: Int, gr: Int, b: Int, a: Int) {
        geo.colRect(x, y, w, h, r, gr, b, a)
    }

    override fun drawLine(i: Int, j: Int, x: Int, y: Int) {
        geo.drawLine(i, j, x, y)
    }

    override fun drawOval(i: Int, j: Int, k: Int, l: Int) {
        geo.drawOval(i, j, k, l)
    }

    override fun drawRect(x: Int, y: Int, x2: Int, y2: Int) {
        geo.drawRect(x, y, x2, y2)
    }

    override fun fillOval(i: Int, j: Int, k: Int, l: Int) {
        geo.fillOval(i, j, k, l)
    }

    override fun fillRect(x: Int, y: Int, w: Int, h: Int) {
        geo.fillRect(x, y, w, h)
    }

    val geo: GeomG
    override fun gradRect(x: Int, y: Int, w: Int, h: Int, a: Int, b: Int, c: IntArray, d: Int, e: Int, f: IntArray) {
        geo.gradRect(x, y, w, h, a, b, c, d, e, f)
    }

    override fun setColor(c: Int) {
        geo.setColor(c)
    }
}
