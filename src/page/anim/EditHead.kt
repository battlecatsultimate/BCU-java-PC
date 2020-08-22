package page.anim

import common.CommonStatic.EditLink
import common.util.anim.AnimCE
import io.BCUWriter
import page.JBTN
import page.Page
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.function.Consumer

class EditHead(private var cur: Page, private var `val`: Int) : Page(cur.front), EditLink {
    private val undo: JBTN = JBTN(0, "undo")
    private val save: JBTN = JBTN(0, "save")
    private val view: JBTN = JBTN(0, "vdiy")
    private val icut: JBTN = JBTN(0, "caic")
    private val mmdl: JBTN = JBTN(0, "camm")
    private val manm: JBTN = JBTN(0, "cama")
    var focus: AnimCE? = null
    private var p0: DIYViewPage? = null
    private var p1: ImgCutEditPage? = null
    private var p2: MaModelEditPage? = null
    private var p3: MaAnimEditPage? = null
    private var changing = false
    var anim: AnimCE? = null
    override fun review() {
        undo.isEnabled = anim != null && anim.history.size > 1
        cur.callBack("review")
        if (anim != null) undo.setToolTipText(anim.getUndo())
    }

    fun setAnim(da: AnimCE?) {
        if (changing) return
        anim = da
        if (anim != null) anim.link = this
        review()
    }

    override fun resized(x: Int, y: Int) {
        Page.Companion.set(undo, x, y, 250, 0, 200, 50)
        Page.Companion.set(save, x, y, 500, 0, 200, 50)
        Page.Companion.set(view, x, y, 800, 0, 200, 50)
        Page.Companion.set(icut, x, y, 1050, 0, 200, 50)
        Page.Companion.set(mmdl, x, y, 1300, 0, 200, 50)
        Page.Companion.set(manm, x, y, 1550, 0, 200, 50)
    }

    private fun addListeners() {
        val thi = this
        view.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (`val` == 0) return
                changing = true
                cur.remove(thi)
                if (p0 == null) p0 = DIYViewPage(front, thi)
                changePanel(p0.also { cur = it })
                cur.add(thi)
                (cur as AbEditPage).setSelection(anim)
                `val` = 0
                changing = false
            }
        })
        icut.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (`val` == 1) return
                changing = true
                cur.remove(thi)
                if (p1 == null) p1 = ImgCutEditPage(front, thi)
                changePanel(p1.also { cur = it!! })
                cur.add(thi)
                (cur as AbEditPage).setSelection(anim)
                `val` = 1
                changing = false
            }
        })
        mmdl.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (`val` == 2) return
                changing = true
                cur.remove(thi)
                if (p2 == null) p2 = MaModelEditPage(front, thi)
                changePanel(p2.also { cur = it!! })
                cur.add(thi)
                (cur as AbEditPage).setSelection(anim)
                `val` = 2
                changing = false
            }
        })
        manm.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (`val` == 3) return
                changing = true
                cur.remove(thi)
                if (p3 == null) p3 = MaAnimEditPage(front, thi)
                changePanel(p3.also { cur = it!! })
                cur.add(thi)
                (cur as AbEditPage).setSelection(anim)
                `val` = 3
                changing = false
            }
        })
        save.setLnr(Consumer { e: ActionEvent? -> BCUWriter.writeData() })
        undo.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                anim.restore()
                review()
                (cur as AbEditPage).setSelection(anim)
            }
        })
    }

    private fun ini() {
        add(view)
        add(icut)
        add(mmdl)
        add(manm)
        add(save)
        add(undo)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        if (`val` == 0) p0 = cur as DIYViewPage else if (`val` == 1) p1 = cur as ImgCutEditPage else if (`val` == 2) p2 = cur as MaModelEditPage else if (`val` == 3) p3 = cur as MaAnimEditPage
        ini()
    }
}
