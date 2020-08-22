package page.info.filter

import common.util.unit.Form
import common.util.unit.Unit
import page.*
import page.info.filter.UnitFilterBox
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.util.function.Consumer
import javax.swing.JScrollPane

class UnitFindPage(p: Page?, pack: String?) : Page(p), SupPage<Unit?> {
    private val back: JBTN = JBTN(0, "back")
    private val show: JTG = JTG(0, "showf")
    private val ult: UnitListTable = UnitListTable(this)
    private val jsp: JScrollPane = JScrollPane(ult)
    private val ufb: UnitFilterBox?
    private val seatf: JTF = JTF()
    private val seabt: JBTN = JBTN(0, "search")
    override fun callBack(o: Any?) {
        ult.setList(o as List<Form?>?)
        resized()
    }

    fun getForm(): Form? {
        return if (ult.getSelectedRow() == -1) null else ult.list.get(ult.getSelectedRow())
    }

    fun getList(): List<Form> {
        return ult.list
    }

    override fun getSelected(): Unit? {
        val f = getForm()
        return f?.unit
    }

    override fun mouseClicked(e: MouseEvent) {
        if (e.source !== ult) return
        ult.clicked(e.point)
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(show, x, y, 250, 0, 200, 50)
        Page.Companion.set(seatf, x, y, 550, 0, 1000, 50)
        Page.Companion.set(seabt, x, y, 1600, 0, 200, 50)
        if (show.isSelected()) {
            val siz: IntArray = ufb!!.getSizer()
            Page.Companion.set(ufb, x, y, 50, 100, siz[0], siz[1])
            var mx = 0
            var my = 0
            if (siz[2] == 0) mx = siz[3] else my = siz[3]
            Page.Companion.set(jsp, x, y, 50 + mx, 100 + my, 2200 - mx, 1150 - my)
        } else Page.Companion.set(jsp, x, y, 50, 100, 2200, 1150)
        ult.setRowHeight(Page.Companion.size(x, y, 50))
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        show.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (show.isSelected()) add(ufb) else remove(ufb)
            }
        })
        seabt.setLnr(Consumer { b: ActionEvent? ->
            if (ufb != null) {
                ufb.name = seatf.getText()
                ufb.callBack(null)
            }
        })
        seatf.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                if (ufb != null) {
                    ufb.name = seatf.getText()
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
        add(seatf)
        add(seabt)
        show.setSelected(true)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ufb = UnitFilterBox.Companion.getNew(this, pack)
        ini()
        resized()
    }
}
