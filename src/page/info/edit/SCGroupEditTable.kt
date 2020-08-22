package page.info.edit

import common.CommonStatic
import common.pack.PackData
import common.util.Data
import common.util.stage.SCDef
import common.util.stage.SCGroup
import common.util.unit.AbEnemy
import page.MainLocale
import page.support.AbJTable
import page.support.EnemyTCR
import java.awt.Component
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.text.JTextComponent

internal class SCGroupEditTable(sc: SCDef?) : AbJTable() {
    companion object {
        private const val serialVersionUID = 1L
        private var title: Array<String>
        fun redefine() {
            title = MainLocale.Companion.getLoc(1, "t1", "t8")
        }

        init {
            redefine()
        }
    }

    protected val scd: SCDef?
    override fun editCellAt(r: Int, c: Int, e: EventObject): Boolean {
        val result: Boolean = super.editCellAt(r, c, e)
        val editor: Component = getEditorComponent()
        if (editor == null || editor !is JTextComponent) return result
        val jtf: JTextComponent = editor as JTextComponent
        if (e is KeyEvent) jtf.selectAll()
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
        return if (scd == null) 0 else scd.smap.size
    }

    @Synchronized
    override fun getValueAt(r: Int, c: Int): Any {
        return if (scd == null || r < 0 || c < 0 || r >= scd.smap.size || c > lnk.size) null else get(r, lnk.get(c))!!
    }

    override fun isCellEditable(r: Int, c: Int): Boolean {
        return lnk.get(c) != 0
    }

    @Synchronized
    override fun setValueAt(arg0: Any, r: Int, c: Int) {
        var c = c
        if (scd == null) return
        c = lnk.get(c)
        if (c == 1) {
            val `is`: IntArray = CommonStatic.parseIntsN(arg0 as String)
            if (`is`.size == 1) set(r, `is`[0])
        }
    }

    @Synchronized
    fun addLine(enemy: AbEnemy?) {
        if (scd == null) return
        var ind: Int = getSelectedRow()
        if (enemy == null) return
        val eid: PackData.Identifier<AbEnemy> = enemy.getID()
        if (scd.smap.containsKey(eid)) return
        scd.smap.put(eid, 0)
        ind++
        if (ind < 0) clearSelection() else setRowSelectionInterval(ind, ind)
    }

    @Synchronized
    fun remLine() {
        if (scd == null) return
        var ind: Int = getSelectedRow()
        if (ind == -1) return
        scd.smap.remove(scd.getSMap().get(ind).key)
        if (ind >= scd.smap.size) ind--
        if (ind < 0) clearSelection() else setRowSelectionInterval(ind, ind)
    }

    private operator fun get(r: Int, c: Int): Any? {
        val info: Array<Map.Entry<PackData.Identifier<AbEnemy>, Int>> = scd.getSMap()
        if (r >= info.size) return null
        if (c == 0) return info[r].key.get() else if (c == 1) {
            val g = info[r].value
            val scg: SCGroup = scd.sub.get(g)
            return if (scg == null) if (g != 0) Data.Companion.trio(g) + " - invalid" else "" else scg.toString()
        }
        return null
    }

    private operator fun set(r: Int, v: Int) {
        var v = v
        val info: Array<Map.Entry<PackData.Identifier<AbEnemy>, Int>> = scd.getSMap()
        if (r >= info.size) return
        val data: Map.Entry<PackData.Identifier<AbEnemy>, Int> = info[r]
        if (v < 0) v = 0
        scd.smap.put(data.key, v)
    }

    init {
        scd = sc
        setDefaultRenderer(Int::class.java, EnemyTCR())
    }
}
