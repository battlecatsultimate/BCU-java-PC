package jogl

import com.jogamp.opengl.GL2
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import common.battle.BattleField
import common.battle.SBCtrl
import common.battle.data.DataEntity
import common.io.assets.Admin
import common.io.assets.Admin.StaticPermitted
import common.io.assets.AssetLoader
import common.io.assets.AssetLoader.AssetHeader
import common.io.assets.AssetLoader.AssetHeader.AssetEntry
import common.io.json.JsonEncoder
import common.io.json.Test
import common.io.json.Test.JsonTest_0.JsonD
import common.io.json.Test.JsonTest_2
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.pack.Source.SourceAnimLoader
import common.pack.Source.SourceAnimSaver
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import jogl.util.GLGraphics
import page.JL
import page.anim.AnimBox
import page.battle.BBCtrl
import page.battle.BattleBox
import page.battle.BattleBox.BBPainter
import page.battle.BattleBox.OuterBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter

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
