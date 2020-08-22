package jogl

import com.jogamp.opengl.GL2
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import common.battle.BattleField
import common.battle.SBCtrl
import jogl.util.GLGraphics
import page.battle.BBCtrl
import page.battle.BattleBox
import page.battle.BattleBox.BBPainter
import page.battle.BattleBox.OuterBox

open class GLBattleBox(bip: OuterBox?, bf: BattleField?, type: Int) : GLCstd(), BattleBox, GLEventListener {
    protected val bbp: BBPainter
    override fun display(drawable: GLAutoDrawable) {
        val gl: GL2 = drawable.getGL().getGL2()
        val g = GLGraphics(drawable.getGL().getGL2(), getWidth(), getHeight())
        bbp.draw(g)
        g.dispose()
        gl.glFlush()
    }

    val painter: BBPainter
        get() = bbp

    override fun paint() {
        display()
    }

    override fun reset() {}
    override fun reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
        bbp.reset()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        bbp = if (type == 0) BBPainter(bip, bf, this) else BBCtrl(bip, bf as SBCtrl?, this)
        for (fs in bbp.bf.sb.b.lu.fs) for (f in fs) f?.anim?.check()
        for (e in bbp.bf.sb.st.data.getAllEnemy()) e.anim.check()
    }
}
