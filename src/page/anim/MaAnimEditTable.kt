package page.anim

import common.util.anim.AnimCE
import common.util.anim.MaAnim
import common.util.anim.Part
import page.Page
import page.support.AnimTable
import page.support.AnimTableTH
import java.awt.Component
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.ListSelectionModel
import javax.swing.text.JTextComponent

class MaAnimEditTable(private val page: Page) : AnimTable<Part?>() {
    var anim: AnimCE? = null
    var ma: MaAnim? = null
    override fun editCellAt(row: Int, column: Int, e: EventObject): Boolean {
        val result: Boolean = super.editCellAt(row, column, e)
        val editor: Component = getEditorComponent()
        if (editor == null || editor !is JTextComponent) return result
        if (e is KeyEvent) (editor as JTextComponent).selectAll()
        return result
    }

    override fun getColumnClass(c: Int): Class<*> {
        return if (lnk.get(c) == 3) String::class.java else Int::class.java
    }

    override fun getColumnCount(): Int {
        return strs.size
    }

    override fun getColumnName(c: Int): String {
        return strs[lnk.get(c)]
    }

    override fun getRowCount(): Int {
        return if (ma == null) 0 else ma.n
    }

    override fun getSelected(): Array<Part?> {
        val rows: IntArray = getSelectedRows()
        val ps = arrayOfNulls<Part>(rows.size)
        for (i in rows.indices) ps[i] = ma.parts.get(rows[i])
        return ps
    }

    override fun getValueAt(r: Int, c: Int): Any {
        if (ma == null || r < 0 || c < 0 || r >= ma.n || c >= strs.size) return null
        return if (lnk.get(c) == 3) ma.parts.get(r).name else ma.parts.get(r).ints.get(lnk.get(c))
    }

    override fun insert(dst: Int, data: Array<Part>): Boolean {
        val l: MutableList<Part> = ArrayList()
        for (p in ma.parts) if (p != null) l.add(p)
        for (i in data.indices) {
            l.add(i + dst, data[i].clone().also { data[i] = it })
            data[i].check(anim)
        }
        ma.parts = l.toTypedArray()
        ma.n = ma.parts.size
        anim.unSave("maanim paste part")
        page.callBack(intArrayOf(0, dst, dst + data.size - 1))
        return true
    }

    override fun isCellEditable(r: Int, c: Int): Boolean {
        return true
    }

    override fun reorder(dst: Int, ori: IntArray): Boolean {
        val l: MutableList<Part> = ArrayList()
        val ab: MutableList<Part> = ArrayList()
        for (row in ori) {
            ab.add(ma.parts.get(row))
            ma.parts.get(row) = null
        }
        for (i in 0 until dst) if (ma.parts.get(i) != null) l.add(ma.parts.get(i))
        val ind = l.size
        l.addAll(ab)
        for (i in dst until ma.n) if (ma.parts.get(i) != null) l.add(ma.parts.get(i))
        ma.parts = l.toTypedArray()
        anim.unSave("maanim reorder part")
        page.callBack(intArrayOf(0, ind, ind + ori.size - 1))
        return true
    }

    @Synchronized
    override fun setValueAt(`val`: Any, r: Int, c: Int) {
        var c = c
        if (ma == null) return
        c = lnk.get(c)
        if (c == 3) {
            ma.parts.get(r).name = (`val` as String).trim { it <= ' ' }
            anim.unSave("maanim edit name")
            return
        }
        var v = `val` as Int
        if (c == 0) if (v < 0) v = 0 else if (v >= anim.mamodel.n) v = anim.mamodel.n - 1
        if (c == 1) if ((v < 0 || v > 14) && v != 50) v = 5
        if (c == 2 && (v < -1 || v == 0)) v = -1
        ma.parts.get(r).ints.get(c) = v
        ma.parts.get(r).validate()
        ma.validate()
        anim.unSave("maanim edit part")
        page.callBack(null)
    }

    fun setAnim(au: AnimCE?, maa: MaAnim?) {
        if (cellEditor != null) cellEditor.stopCellEditing()
        anim = au
        ma = maa
    }

    companion object {
        private const val serialVersionUID = 1L
        private val strs = arrayOf("part id", "modification", "loop", "name")
    }

    init {
        selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
        setTransferHandler(AnimTableTH<Part>(this, 2))
    }
}
