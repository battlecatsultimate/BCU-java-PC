package jogl

import com.jogamp.opengl.GL2
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import common.CommonStatic
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
import common.system.fake.FakeGraphics
import common.system.fake.FakeImage
import common.system.fake.FakeTransform
import common.util.anim.EAnimI
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import jogl.util.GLGraphics
import page.JL
import page.JTG
import page.anim.AnimBox
import page.anim.IconBox
import page.anim.IconBox.IBConf
import page.anim.IconBox.IBCtrl
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.AWTException
import java.awt.Point
import java.awt.Rectangle
import java.awt.Robot
import java.awt.image.BufferedImage
import java.util.*

internal class GLIconBox : GLViewBox(IBCtrl()), IconBox {
    override fun changeType() {
        val bimg: FakeImage = CommonStatic.getBCAssets().ico.get(IBConf.mode).get(IBConf.type).getImg()
        IBConf.line.get(2) = bimg.getWidth()
        IBConf.line.get(3) = bimg.getHeight()
    }

    public override fun draw(gra: FakeGraphics) {
        val b: Boolean = CommonStatic.getConfig().ref
        CommonStatic.getConfig().ref = false
        getCtrl().predraw(gra)
        val at: FakeTransform = gra.getTransform()
        super.draw(gra)
        gra.setTransform(at)
        CommonStatic.getConfig().ref = b
        getCtrl().postdraw(gra)
    }

    val clip: BufferedImage?
        get() {
            val r: Rectangle = getBounds()
            val p: Point = getLocationOnScreen()
            r.x = p.x + IBConf.line.get(0)
            r.y = p.y + IBConf.line.get(1)
            r.width = IBConf.line.get(2)
            r.height = IBConf.line.get(3)
            return try {
                Robot().createScreenCapture(r)
            } catch (e: AWTException) {
                e.printStackTrace()
                null
            }
        }

    override fun getCtrl(): IBCtrl {
        return ctrl as IBCtrl
    }

    override fun setBlank(selected: Boolean) {
        isBlank = selected
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        setFocusable(true)
        IBConf.glow = 0
        changeType()
    }
}

internal class GLVBExporter(private val vb: GLViewBox) : VBExporter {
    private var loader: ViewBox.Loader? = null
    private var glr: GLRecdBImg? = null
    override fun end(btn: JTG?) {
        if (loader == null) return
        loader!!.finish(btn)
        glr!!.end()
        loader = null
        glr = null
    }

    val prev: BufferedImage?
        get() = vb.getScreen()

    override fun start(): ViewBox.Loader? {
        if (loader != null) return loader
        val qb: Queue<BufferedImage?> = ArrayDeque<BufferedImage?>()
        loader = ViewBox.Loader(qb)
        glr = GLRecdBImg(vb, qb, loader!!.thr)
        loader!!.start()
        return loader
    }

    fun update() {
        if (glr != null) glr.update()
    }
}

internal open class GLViewBox(c: Controller) : GLCstd(), ViewBox, GLEventListener {
    protected val ctrl: Controller
    protected val exp: GLVBExporter
    var isBlank = false
        protected set
    private var ent: EAnimI? = null
    override fun display(drawable: GLAutoDrawable) {
        val gl: GL2 = drawable.getGL().getGL2()
        val w: Int = getWidth()
        val h: Int = getHeight()
        val g = GLGraphics(drawable.getGL().getGL2(), w, h)
        if (!isBlank) {
            val c = intArrayOf(ViewBox.Companion.c0.getRed(), ViewBox.Companion.c0.getGreen(), ViewBox.Companion.c0.getBlue())
            val f = intArrayOf(ViewBox.Companion.c1.getRed(), ViewBox.Companion.c1.getGreen(), ViewBox.Companion.c1.getBlue())
            g.gradRect(0, 0, w, h / 2, w / 2, 0, c, w / 2, h / 2, f)
            g.gradRect(0, h / 2, w, h / 2, w / 2, h / 2, f, w / 2, h, c)
        } else {
            g.setColor(FakeGraphics.Companion.WHITE)
            g.fillRect(0, 0, w, h)
        }
        draw(g)
        g.dispose()
        gl.glFlush()
    }

    override fun getCtrl(): Controller {
        return ctrl
    }

    override fun getEnt(): EAnimI? {
        return ent
    }

    override fun getExp(): VBExporter? {
        return exp
    }

    override fun paint() {
        display()
    }

    override fun setEntity(ieAnim: EAnimI?) {
        ent = ieAnim
    }

    override fun update() {
        if (ent != null) {
            ent.update(true)
            exp.update()
        }
    }

    protected open fun draw(g: FakeGraphics) {
        val w: Int = getWidth()
        val h: Int = getHeight()
        g.translate(w / 2.toDouble(), h * 3 / 4.toDouble())
        g.setColor(FakeGraphics.Companion.BLACK)
        if (ent != null) ent.draw(g, ctrl.ori.copy().times(-1.0), ctrl.siz)
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        exp = GLVBExporter(this)
        ctrl = c
        c.setCont(this)
    }
}
