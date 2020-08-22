package page.info.filter

import common.util.unit.AbEnemy
import common.util.unit.Enemy
import page.*
import page.info.filter.EnemyFilterBox
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.util.function.Consumer
import javax.swing.JLabel
import javax.swing.JScrollPane

class EnemyFindPage(p: Page?, pack: String?) : Page(p), SupPage<AbEnemy?> {
    private val back: JBTN = JBTN(0, "back")
    private val source = JLabel("Source of enemy icon: DB")
    private val show: JTG = JTG(0, "showf")
    private val elt: EnemyListTable = EnemyListTable(this)
    private val efb: EnemyFilterBox?
    private val jsp: JScrollPane = JScrollPane(elt)
    private val seatf: JTF = JTF()
    private val seabt: JBTN = JBTN(0, "search")
    override fun callBack(o: Any?) {
        elt.setList(o as List<Enemy?>?)
        resized()
    }

    fun getList(): List<Enemy> {
        return elt.list
    }

    override fun getSelected(): Enemy? {
        val sel: Int = elt.selectedRow
        return if (sel < 0) null else elt.list.get(sel)
    }

    override fun mouseClicked(e: MouseEvent) {
        if (e.source !== elt) return
        elt.clicked(e.point)
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(source, x, y, 0, 50, 600, 50)
        Page.Companion.set(show, x, y, 250, 0, 200, 50)
        Page.Companion.set(seatf, x, y, 550, 0, 1000, 50)
        Page.Companion.set(seabt, x, y, 1600, 0, 200, 50)
        if (show.isSelected) {
            val siz: IntArray = efb!!.getSizer()
            Page.Companion.set(efb, x, y, 50, 100, siz[0], siz[1])
            var mx = 0
            var my = 0
            if (siz[2] == 0) mx = siz[3] else my = siz[3]
            Page.Companion.set(jsp, x, y, 50 + mx, 100 + my, 2200 - mx, 1150 - my)
        } else Page.Companion.set(jsp, x, y, 50, 100, 2200, 1150)
        elt.rowHeight = Page.Companion.size(x, y, 50)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        show.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (show.isSelected) add(efb) else remove(efb)
            }
        })
        seabt.setLnr(Consumer { b: ActionEvent? ->
            if (efb != null) {
                efb.name = seatf.text
                efb.callBack(null)
            }
        })
        seatf.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                if (efb != null) {
                    efb.name = seatf.text
                    efb.callBack(null)
                }
            }
        })
    }

    private fun ini() {
        add(back)
        add(show)
        add(efb)
        add(jsp)
        add(source)
        add(seatf)
        add(seabt)
        show.isSelected = true
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        efb = EnemyFilterBox.Companion.getNew(this, pack)
        ini()
        resized()
    }
}
