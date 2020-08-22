package page

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
import main.MainBCU
import page.JL
import page.MainFrame
import page.MainLocale
import page.Page
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import utilpc.PP
import java.awt.Color
import java.awt.Component
import java.awt.Container
import java.awt.Point
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.util.function.Consumer
import javax.swing.*

abstract class Page protected constructor(private val front: Page?) : JPanel(), RetFunc {
    companion object {
        private const val serialVersionUID = 1L
        var BGCOLOR = if (MainBCU.light || !MainBCU.nimbus) Color(255, 255, 255) else Color(54, 54, 54)
        operator fun get(i: Int, s: String?): String? {
            return MainLocale.Companion.getLoc(i, s)
        }

        operator fun get(i: Int, s: String, m: Int): Array<String?> {
            return MainLocale.Companion.getLoc(i, s, m)
        }

        fun renewLoc(p: Page?) {
            var p = p
            MainLocale.Companion.redefine()
            while (p != null) {
                for (c in p.components) if (c is LocComp) (c as LocComp).reLoc()
                p.renew()
                p = p.front
            }
        }

        operator fun set(jc: Component, winx: Int, winy: Int, x: Int, y: Int, w: Int, h: Int) {
            jc.setBounds(x * winx / 2300, y * winy / 1300, w * winx / 2300, h * winy / 1300)
        }

        fun size(x: Int, y: Int, a: Int): Int {
            return Math.min(a * x / 2300, a * y / 1300)
        }

        protected fun size(x: Int, y: Int, a: Int, v: Int): PP {
            return PP(a * x / 2300, v * y / 1300)
        }

        init {
            ToolTipManager.sharedInstance().setInitialDelay(100)
        }
    }

    private var resizing = false
    private var adjusting = 0
    override fun add(c: Component): Component {
        if (c is CustomComp) {
            c.added(this)
        }
        return super.add(c)
    }

    override fun callBack(newParam: Any?) {}
    @Synchronized
    fun componentResized(x: Int, y: Int) {
        if (resizing) return
        resizing = true
        resized(x, y)
        front?.componentResized(x, y)
        val cs: Array<Component> = getComponents()
        for (c in cs) {
            if (c is Page) c.componentResized(x, y)
            fontSetter(c)
        }
        repaint()
        resizing = false
    }

    fun getFront(): Page? {
        if (front != null) {
            front.background = Page.Companion.BGCOLOR
        }
        return front
    }

    fun getXY(): PP {
        val jrp: JRootPane = MainFrame.Companion.F.getRootPane()
        return PP(jrp.getWidth(), jrp.getHeight())
    }

    fun isAdj(): Boolean {
        return adjusting > 0
    }

    fun resized() {
        val p: Point = getXY().toPoint()
        componentResized(p.x, p.y)
    }

    protected fun change(b: Boolean) {
        adjusting += if (b) 1 else -1
    }

    protected fun <T> change(t: T, c: Consumer<T>) {
        adjusting++
        c.accept(t)
        adjusting--
    }

    @Synchronized
    protected fun changePanel(p: Page?) {
        MainFrame.Companion.changePanel(p)
    }

    open fun exit() {}
    fun exitAll() {
        exit()
        front?.exitAll()
    }

    fun getRootPage(): Page {
        return if (front == null) this else front.getRootPage()
    }

    protected fun getThis(): Page {
        return this
    }

    open fun keyPressed(e: KeyEvent) {}
    open fun keyReleased(e: KeyEvent) {}
    open fun keyTyped(e: KeyEvent) {}
    fun leave() {}
    open fun mouseClicked(e: MouseEvent) {}
    open fun mouseDragged(e: MouseEvent) {}
    fun mouseMoved(e: MouseEvent?) {}
    open fun mousePressed(e: MouseEvent) {}
    open fun mouseReleased(e: MouseEvent) {}
    open fun mouseWheel(e: MouseEvent) {}
    open fun renew() {}
    protected abstract fun resized(x: Int, y: Int)
    @Synchronized
    open fun timer(t: Int) {
        resized()
    }

    fun windowActivated() {}
    fun windowDeactivated() {}
    private fun fontSetter(c: Component?) {
        if (c == null) return
        c.font = MainFrame.Companion.font
        if (c is JScrollPane) {
            val jsp: JScrollPane = c as JScrollPane
            fontSetter(jsp.getViewport().getView())
        } else if (c is JTable) {
            val t: JTable = c as JTable
            fontSetter(t.getTableHeader())
        } else if (c is Container) {
            for (ic in c.components) fontSetter(ic)
        }
    }

    init {
        setBackground(Page.Companion.BGCOLOR)
        setLayout(null)
    }
}
