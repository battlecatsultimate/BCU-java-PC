package page.basis

import common.battle.BasisSet
import common.util.unit.Form
import page.JBTN
import page.JTF
import page.JTG
import page.Page
import page.info.filter.UnitFilterBox
import page.info.filter.UnitListTable
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.util.function.Consumer
import javax.swing.JScrollPane
import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class UnitFLUPage(p: Page?) : LubCont(p) {
    private val lub: LineUpBox = LineUpBox(this)
    private val back: JBTN = JBTN(0, "back")
    private val show: JTG = JTG(0, "showf")
    private val ult: UnitListTable = UnitListTable(this)
    private val jsp: JScrollPane = JScrollPane(ult)
    private val ufb: UnitFilterBox?
    private val seatf: JTF = JTF()
    private val seabt: JBTN = JBTN()
    override fun callBack(o: Any?) {
        if (o is List<*>) ult.setList(o as List<Form?>?)
        resized()
    }

    fun getList(): List<Form> {
        return ult.list
    }

    override fun getLub(): LineUpBox {
        return lub
    }

    protected override fun mouseClicked(e: MouseEvent) {
        if (e.source === ult) ult.clicked(e.point)
        super.mouseClicked(e)
    }

    protected override fun renew() {
        lub.setLU(BasisSet.Companion.current().sele.lu)
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(show, x, y, 250, 0, 200, 50)
        Page.Companion.set(seatf, x, y, 550, 0, 1000, 50)
        Page.Companion.set(seabt, x, y, 1600, 0, 200, 50)
        val end = intArrayOf(650, 350)
        if (show.isSelected) {
            val siz: IntArray = ufb.getSizer()
            Page.Companion.set(ufb, x, y, 50, 100, siz[0], siz[1])
            var mx = 50
            var my = 100
            var ax = 2200
            var ay = 1150
            if (siz[2] == 0) {
                mx += siz[3]
                ax -= siz[3]
                ay -= end[1 - siz[2]]
            } else {
                my += siz[3]
                ax -= end[1 - siz[2]]
                ay -= siz[3]
            }
            Page.Companion.set(jsp, x, y, mx, my, ax, ay)
        } else Page.Companion.set(jsp, x, y, 50, 100, 1550, 1150)
        Page.Companion.set(lub, x, y, 1650, 950, 600, 300)
        ult.rowHeight = Page.Companion.size(x, y, 50)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(getFront())
            }
        })
        show.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (show.isSelected) add(ufb) else remove(ufb)
            }
        })
        val lsm: ListSelectionModel = ult.selectionModel
        lsm.selectionMode = ListSelectionModel.SINGLE_SELECTION
        lsm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (lsm.valueIsAdjusting) return
                val ind: Int = lsm.anchorSelectionIndex
                if (ind < 0) return
                val f: Form = ult.list.get(ind)
                lub.select(f)
            }
        })
        seabt.setLnr(Consumer { b: ActionEvent? ->
            if (ufb != null) {
                ufb.name = seatf.text
                ufb.callBack(null)
            }
        })
        seatf.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                if (ufb != null) {
                    ufb.name = seatf.text
                    ufb.callBack(null)
                }
            }
        })
    }

    private fun ini() {
        add(back)
        add(show)
        add(ufb)
        add(jsp)
        add(lub)
        add(seatf)
        add(seabt)
        show.isSelected = true
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ufb = UnitFilterBox.Companion.getNew(this, null)
        ini()
        resized()
    }
}
