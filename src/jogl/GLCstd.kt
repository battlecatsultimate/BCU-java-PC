package jogl

import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import com.jogamp.opengl.awt.GLCanvas
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
import jogl.util.ResManager
import page.JL
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.AWTException
import java.awt.Robot
import java.awt.image.BufferedImage

abstract class GLCstd protected constructor() : GLCanvas(GLStatic.GLC), GLEventListener {
    override fun dispose(drawable: GLAutoDrawable) {
        ResManager.Companion.get(drawable.getGL().getGL2()).dispose()
    }

    val screen: BufferedImage?
        get() = try {
            val r = bounds
            val p = locationOnScreen
            r.x = p.x
            r.y = p.y
            Robot().createScreenCapture(r)
        } catch (e: AWTException) {
            e.printStackTrace()
            null
        }

    override fun init(drawable: GLAutoDrawable) {}
    override fun reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {}

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        addGLEventListener(this)
    }
}
