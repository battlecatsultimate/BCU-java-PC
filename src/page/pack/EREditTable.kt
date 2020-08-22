package page.pack

import common.CommonStatic
import common.pack.PackData
import common.util.EREnt
import common.util.unit.AbEnemy
import common.util.unit.EneRand
import common.util.unit.Enemy
import page.MainFrame
import page.Page
import page.info.EnemyInfoPage
import page.support.AbJTable
import page.support.EnemyTCR
import page.support.InTableTH
import page.support.Reorderable
import java.awt.Component
import java.awt.Point
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.text.JTextComponent

internal class EREditTable(private val page: Page) : AbJTable(), Reorderable {
    companion object {
        private const val serialVersionUID = 1L
        private var title: Array<String>
        fun redefine() {
            title = Page.Companion.get(1, "er", 3)
        }

        init {
            redefine()
        }
    }

    private var rand: EneRand? = null
    override fun editCellAt(r: Int, c: Int, e: EventObject): Boolean {
        val result: Boolean = super.editCellAt(r, c, e)
        val editor: Component = editorComponent
        if (editor == null || editor !is JTextComponent) return result
        val jtf: JTextComponent = editor
        if (e is KeyEvent) jtf.selectAll()
        if (lnk.get(c) == 0 && jtf.text.length > 0) jtf.text = (get(r, c) as AbEnemy?).getID().toString() + ""
        return result
    }

    override fun getColumnClass(c: Int): Class<*> {
        return if (lnk.get(c) == 0) Int::class.java else String::class.java
    }

    override fun getColumnCount(): Int {
        return title.size
    }

    override fun getColumnName(c: Int): String {
        return title[lnk.get(c)]
    }

    @Synchronized
    override fun getRowCount(): Int {
        return if (rand == null) 0 else rand.list.size
    }

    @Synchronized
    override fun getValueAt(r: Int, c: Int): Any {
        return if (rand == null || r < 0 || c < 0 || r >= rand.list.size || c > lnk.size) null else get(r, lnk.get(c))!!
    }

    override fun isCellEditable(r: Int, c: Int): Boolean {
        return true
    }

    @Synchronized
    override fun reorder(ori: Int, fin: Int) {
        var fin = fin
        if (fin > ori) fin--
        if (fin == ori) return
        rand.list.add(fin, rand.list.removeAt(ori))
    }

    @Synchronized
    override fun setValueAt(arg0: Any, r: Int, c: Int) {
        var c = c
        if (rand == null) return
        c = lnk.get(c)
        if (c > 0) {
            val `is`: IntArray = CommonStatic.parseIntsN(arg0 as String)
            if (`is`.size == 0) return
            if (`is`.size == 1) set(r, c, `is`[0], -1) else set(r, c, `is`[0], `is`[1])
        } else {
            val i = if (arg0 is Int) arg0 else CommonStatic.parseIntN(arg0 as String)
            set(r, c, i, 0)
        }
    }

    @Synchronized
    fun addLine(enemy: AbEnemy?): Int {
        if (rand == null) return -1
        var ind: Int = selectedRow
        if (ind == -1) ind = 0
        val er: EREnt<PackData.Identifier<AbEnemy>> = EREnt<PackData.Identifier<AbEnemy>>()
        rand.list.add(er)
        er.ent = if (enemy == null) null else enemy.getID()
        return rand.list.size - 1
    }

    @Synchronized
    fun clicked(p: Point) {
        if (rand == null) return
        var c: Int = getColumnModel().getColumnIndexAtX(p.x)
        c = lnk.get(c)
        if (c != 0) return
        val r: Int = p.y / getRowHeight()
        val er: EREnt<PackData.Identifier<AbEnemy>> = rand.list.get(r)
        val e: AbEnemy = er.ent.get()
        if (e != null && e is Enemy) MainFrame.Companion.changePanel(EnemyInfoPage(page, e, er.multi, er.mula))
    }

    @Synchronized
    fun remLine(): Int {
        if (rand == null) return -1
        var ind: Int = selectedRow
        if (ind >= 0) rand.list.removeAt(ind)
        if (rand.list.size > 0) {
            if (ind == 0) ind = 1
            return ind - 1
        }
        return -1
    }

    @Synchronized
    fun setData(st: EneRand?) {
        if (cellEditor != null) cellEditor.stopCellEditing()
        rand = st
        clearSelection()
    }

    private operator fun get(r: Int, c: Int): Any? {
        if (rand == null) return null
        val er: EREnt<PackData.Identifier<AbEnemy>> = rand.list.get(r)
        if (c == 0) return er.ent.get() else if (c == 1) return CommonStatic.toArrayFormat(er.multi, er.mula) + "%" else if (c == 2) return er.share
        return null
    }

    private operator fun set(r: Int, c: Int, v: Int, para: Int) {
        var v = v
        if (rand == null) return
        val er: EREnt<PackData.Identifier<AbEnemy>> = rand.list.get(r)
        if (v < 0) v = 0
        if (c == 1) {
            er.multi = v
            er.mula = if (para != -1) para else v
        } else if (c == 2) er.share = v
    }

    init {
        transferHandler = InTableTH(this)
        setDefaultRenderer(Int::class.java, EnemyTCR())
    }
}
