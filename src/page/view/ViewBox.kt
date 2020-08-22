package page.view

import common.system.P
import common.util.anim.EAnimI
import page.JTG
import page.RetFunc
import page.awt.RecdThread
import java.awt.Color
import java.awt.Pointimport
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import java.util.*

com.google.api.client.json.jackson2.JacksonFactory
interface ViewBox {
    object Conf {
        var white = false
    }

    open class Controller {
        val ori: P = P(0, 0)
        var siz = 0.5
        protected var p: Point? = null
        protected var cont: ViewBox? = null
        @Synchronized
        open fun mouseDragged(e: MouseEvent) {
            if (p == null) return
            ori.x += p!!.x - e.x.toDouble()
            ori.y += p!!.y - e.y.toDouble()
            p = e.point
        }

        @Synchronized
        open fun mousePressed(e: MouseEvent) {
            p = e.point
        }

        @Synchronized
        open fun mouseReleased(e: MouseEvent?) {
            p = null
        }

        fun resize(pow: Double) {
            siz *= pow
        }

        fun setCont(vb: ViewBox?) {
            cont = vb
        }
    }

    class Loader(list: Queue<BufferedImage?>?) : RetFunc {
        val thr: RecdThread
        private var jtb: JTG? = null
        override fun callBack(o: Any?) {
            jtb.setEnabled(true)
        }

        fun finish(btn: JTG?) {
            jtb = btn
            jtb.setEnabled(false)
        }

        fun getProg(): String {
            return "remain: " + thr.remain()
        }

        fun start() {
            thr.start()
        }

        init {
            thr = RecdThread.Companion.getIns(this, list, null, RecdThread.Companion.GIF)
        }
    }

    interface VBExporter {
        fun end(btn: JTG?)
        fun getPrev(): BufferedImage?
        fun start(): page.view.ViewBox.Loader?
    }

    fun end(btn: JTG?) {
        if (getExp() != null) getExp()!!.end(btn)
    }

    fun getCtrl(): Controller
    fun getEnt(): EAnimI?
    fun getExp(): VBExporter?
    fun getPrev(): BufferedImage? {
        return if (getExp() != null) getExp()!!.getPrev() else null
    }

    fun isBlank(): Boolean
    fun mouseDragged(e: MouseEvent) {
        getCtrl().mouseDragged(e)
    }

    fun mousePressed(e: MouseEvent) {
        getCtrl().mousePressed(e)
    }

    fun mouseReleased(e: MouseEvent?) {
        getCtrl().mouseReleased(e)
    }

    fun paint()
    fun resize(pow: Double) {
        getCtrl().resize(pow)
    }

    fun setEntity(ieAnim: EAnimI?)
    fun start(): page.view.ViewBox.Loader? {
        return if (getExp() != null) getExp()!!.start() else null
    }

    fun update()

    companion object {
        val c0 = Color(70, 140, 160)
        val c1 = Color(85, 185, 205)
    }
}
