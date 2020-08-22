package page.view

import common.util.anim.AnimCE
import common.util.anim.AnimD
import common.util.anim.AnimI
import common.util.anim.AnimI.AnimType
import common.util.anim.EAnimI
import io.BCUWriter
import main.Timer
import page.JBTN
import page.JTG
import page.Page
import page.anim.ImgCutEditPage
import page.awt.BBBuilder
import java.awt.Canvas
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.JSlider
import javax.swing.event.ChangeListener
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

abstract class AbViewPage protected constructor(p: Page?, box: ViewBox = BBBuilder.Companion.def.getViewBox()) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val copy: JBTN = JBTN(0, "copy")
    private val jlt: JList<String> = JList<String>()
    private val jspt: JScrollPane = JScrollPane(jlt)
    private val jst: JSlider = JSlider(100, 900)
    private val jtl: JSlider = JSlider()
    private val jtb: JTG = JTG(0, "pause")
    private val nex: JBTN = JBTN(0, "nextf")
    private val gif: JTG = JTG(0, "gif")
    private val png: JBTN = JBTN(0, "png")
    protected val vb: ViewBox
    private var loader: ViewBox.Loader? = null
    protected var pause = false
    private var changingT = false
    private var changingtl = false
    protected open fun enabler(b: Boolean) {
        jtb.setEnabled(b)
        back.setEnabled(b)
        copy.setEnabled(b)
        jlt.setEnabled(b)
        jst.setEnabled(b)
        jtl.setEnabled(b && pause)
        nex.setEnabled(b && pause)
        gif.setEnabled(b)
        png.setEnabled(b && pause)
    }

    override fun exit() {
        Timer.Companion.p = 33
    }

    override fun mouseDragged(e: MouseEvent) {
        if (e.source === vb) vb.mouseDragged(e)
    }

    override fun mousePressed(e: MouseEvent) {
        if (e.source === vb) vb.mousePressed(e)
    }

    override fun mouseReleased(e: MouseEvent) {
        if (e.source === vb) vb.mouseReleased(e)
    }

    override fun mouseWheel(e: MouseEvent) {
        if (e.source !is ViewBox) return
        val mwe: MouseWheelEvent = e as MouseWheelEvent
        val d: Double = mwe.getPreciseWheelRotation()
        vb.resize(Math.pow(res, d))
    }

    protected fun preini() {
        add(back)
        add(copy)
        add(vb as Canvas)
        add(jspt)
        add(jst)
        add(jtb)
        add(jtl)
        add(nex)
        add(gif)
        add(png)
        jst.setPaintLabels(true)
        jst.setPaintTicks(true)
        jst.setMajorTickSpacing(100)
        jst.setMinorTickSpacing(25)
        jst.setValue(Timer.Companion.p * 100 / 33)
        jtl.setEnabled(false)
        jtl.setPaintTicks(true)
        jtl.setPaintLabels(true)
        png.setEnabled(false)
        addListener()
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(copy, x, y, 250, 0, 200, 50)
        Page.Companion.set(vb as Canvas, x, y, 1000, 100, 1000, 600)
        Page.Companion.set(jspt, x, y, 400, 550, 300, 400)
        Page.Companion.set(jst, x, y, 1000, 750, 1000, 100)
        Page.Companion.set(jtl, x, y, 1000, 900, 1000, 100)
        Page.Companion.set(jtb, x, y, 1300, 1050, 200, 50)
        Page.Companion.set(nex, x, y, 1600, 1050, 200, 50)
        Page.Companion.set(png, x, y, 1300, 1150, 200, 50)
        Page.Companion.set(gif, x, y, 1600, 1150, 400, 50)
    }

    protected fun <T> setAnim(a: AnimI<*, T>) where T : Enum<T>?, T : AnimType<*, T>? {
        if (!changingT) {
            var ind: Int = jlt.getSelectedIndex()
            if (ind == -1) ind = 0
            a.anim.check()
            val strs: Array<String> = a.anim.names()
            jlt.setListData(strs)
            if (ind >= strs.size) ind = 0
            jlt.setSelectedIndex(ind)
        }
        if (jlt.getSelectedIndex() == -1) return
        vb.setEntity(a.getEAnim(a.types().get(jlt.getSelectedIndex())))
        jtl.setMinimum(0)
        jtl.setMaximum(vb.getEnt().len())
        jtl.setLabelTable(null)
        if (vb.getEnt().len() <= 50) {
            jtl.setMajorTickSpacing(5)
            jtl.setMinorTickSpacing(1)
        } else if (vb.getEnt().len() <= 200) {
            jtl.setMajorTickSpacing(10)
            jtl.setMinorTickSpacing(2)
        } else if (vb.getEnt().len() <= 1000) {
            jtl.setMajorTickSpacing(50)
            jtl.setMinorTickSpacing(10)
        } else if (vb.getEnt().len() <= 5000) {
            jtl.setMajorTickSpacing(250)
            jtl.setMinorTickSpacing(50)
        } else {
            jtl.setMajorTickSpacing(1000)
            jtl.setMinorTickSpacing(200)
        }
    }

    override fun timer(t: Int) {
        if (!pause) eupdate()
        vb.paint()
        if (loader == null) gif.setText(0, "gif") else gif.setText(loader!!.prog)
        if (!gif.isSelected() && gif.isEnabled()) loader = null
    }

    protected abstract fun updateChoice()
    private fun addListener() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        copy.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val ei: EAnimI = vb.getEnt()
                if (ei == null || ei.anim() !is AnimD<*, *>) return
                val eau: AnimD<*, *> = ei.anim() as AnimD<*, *>
                var str = "new anim"
                str = AnimCE.Companion.getAvailable(str)
                AnimCE(str, eau)
                changePanel(ImgCutEditPage(getThis()))
            }
        })
        jlt.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.getValueIsAdjusting()) return
                changingT = true
                updateChoice()
                changingT = false
            }
        })
        jst.addChangeListener(ChangeListener {
            if (jst.getValueIsAdjusting()) return@ChangeListener
            Timer.Companion.p = jst.getValue() * 33 / 100
        })
        jtl.addChangeListener(ChangeListener {
            if (changingtl || !pause) return@ChangeListener
            if (vb.getEnt() != null) vb.getEnt().setTime(jtl.getValue())
        })
        jtb.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                pause = jtb.isSelected()
                enabler(true)
            }
        })
        nex.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                eupdate()
            }
        })
        png.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val str: String = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
                val f = File("./img/$str.png")
                BCUWriter.writeImage(vb.getPrev(), f)
            }
        })
        gif.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (gif.isSelected()) loader = vb.start() else vb.end(gif)
            }
        })
    }

    private fun eupdate() {
        vb.update()
        changingtl = true
        if (vb.getEnt() != null) jtl.setValue(vb.getEnt().ind())
        changingtl = false
    }

    companion object {
        private const val serialVersionUID = 1L
        private const val res = 0.95
    }

    init {
        vb = box
    }
}
