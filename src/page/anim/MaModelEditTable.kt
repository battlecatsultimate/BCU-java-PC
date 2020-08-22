package page.anim

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
import common.util.anim.AnimCE
import common.util.anim.MaModel
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.Page
import page.anim.AnimBox
import page.support.AnimTable
import page.support.AnimTableTH
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.Component
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.ListSelectionModel
import javax.swing.text.JTextComponent

internal class MaModelEditTable(p: Page) : AnimTable<IntArray?>() {
    var anim: AnimCE? = null
    var mm: MaModel? = null
    private val page: Page
    override fun editCellAt(row: Int, column: Int, e: EventObject): Boolean {
        val result: Boolean = super.editCellAt(row, column, e)
        val editor: Component = getEditorComponent()
        if (editor == null || editor !is JTextComponent) return result
        val jtc: JTextComponent = editor as JTextComponent
        if (e is KeyEvent) jtc.selectAll()
        return result
    }

    override fun getColumnClass(c: Int): Class<*> {
        return if (lnk.get(c) == 13) String::class.java else Int::class.java
    }

    override fun getColumnCount(): Int {
        return strs.size
    }

    override fun getColumnName(c: Int): String {
        return strs[lnk.get(c)]
    }

    override fun getRowCount(): Int {
        return if (mm == null) 0 else mm.n
    }

    override fun getSelected(): Array<IntArray?> {
        val rows: IntArray = getSelectedRows()
        val ps = arrayOfNulls<IntArray>(rows.size)
        for (i in rows.indices) {
            ps[i] = mm.parts.get(rows[i]).clone()
            for (j in 0 until i) if (ps[i]!![0] == rows[j]) ps[i]!![0] = -j - 10
        }
        return ps
    }

    override fun getValueAt(r: Int, c: Int): Any {
        if (mm == null || r < 0 || c < 0 || r >= mm.n || c >= strs.size) return null
        if (lnk.get(c) == 0) return r
        if (lnk.get(c) == 1) return mm.parts.get(r).get(0)
        return if (lnk.get(c) == 13) mm.strs0.get(r) else mm.parts.get(r).get(lnk.get(c))
    }

    override fun insert(dst: Int, data: Array<IntArray>): Boolean {
        val inds = IntArray(mm.n)
        val move = IntArray(mm.n + data.size)
        var ind = 0
        for (i in 0 until mm.n) {
            if (i == dst) for (j in data.indices) {
                move[ind] = mm.n + j
                ind++
            }
            inds[i] = ind
            move[ind] = i
            ind++
        }
        if (mm.n == dst) for (j in data.indices) {
            move[ind] = mm.n + j
            ind++
        }
        anim.reorderModel(inds)
        mm.parts = Arrays.copyOf<IntArray>(mm.parts, mm.n + data.size)
        mm.strs0 = Arrays.copyOf<String>(mm.strs0, mm.n + data.size)
        for (i in data.indices) {
            mm.parts.get(mm.n + i) = data[i]
            val par = data[i][0]
            if (par <= -10) data[i][0] = dst - par - 10
            mm.strs0.get(mm.n + i) = "copied"
        }
        mm.n = mm.n + data.size
        mm.reorder(move)
        mm.check(anim)
        anim.unSave("mamodel paste")
        page.callBack(intArrayOf(dst, dst + data.size - 1))
        return true
    }

    override fun isCellEditable(r: Int, c: Int): Boolean {
        return lnk.get(c) != 0
    }

    override fun reorder(dst: Int, ori: IntArray): Boolean {
        val inds = IntArray(mm.n)
        val move = IntArray(mm.n)
        val orid = IntArray(mm.n)
        for (`val` in ori) orid[`val`] = -1
        var ind = 0
        var fin = 0
        for (i in 0..mm.n) {
            if (i == dst) {
                fin = ind
                for (j in ori.indices) {
                    move[ind] = ori[j]
                    inds[ori[j]] = ind
                    ind++
                }
            }
            if (i != mm.n && orid[i] != -1) {
                move[ind] = i
                inds[i] = ind
                ind++
            }
        }
        anim.reorderModel(inds)
        mm.reorder(move)
        anim.unSave("mamodel reorder")
        page.callBack(intArrayOf(fin, fin + ori.size - 1))
        return true
    }

    @Synchronized
    override fun setValueAt(`val`: Any, r: Int, c: Int) {
        var c = c
        if (mm == null) return
        c = lnk.get(c)
        if (c == 13) mm.strs0.get(r) = (`val` as String).trim { it <= ' ' } else {
            var v = `val` as Int
            if (c == 1 && v < -1) v = -1
            if (c == 2) if (v < 0) v = 0 else if (v >= anim.imgcut.n) v = anim.imgcut.n - 1
            if (c == 1) c--
            if (r >= mm.n) return
            mm.parts.get(r).get(c) = v
            mm.parts.get(0).get(0) = -1
        }
        if (c == 0) mm.check(anim)
        anim.unSave("mamodel edit")
        page.callBack(null)
    }

    fun setMaModel(au: AnimCE?) {
        if (cellEditor != null) cellEditor.stopCellEditing()
        anim = au
        mm = if (au == null) null else au.mamodel
    }

    companion object {
        private const val serialVersionUID = 1L
        private val strs = arrayOf("id", "parent", "img", "z-order", "pos-x", "pos-y", "pivot-x",
                "pivot-y", "scale-x", "scale-y", "angle", "opacity", "glow", "name")
    }

    init {
        selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
        setTransferHandler(AnimTableTH<IntArray>(this, 1))
        page = p
    }
}
