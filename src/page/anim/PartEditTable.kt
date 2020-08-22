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
import common.util.anim.MaAnim
import common.util.anim.Part
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

internal class PartEditTable(private val page: Page) : AnimTable<IntArray?>() {
    var anim: AnimCE? = null
    protected var ma: MaAnim? = null
    var part: Part? = null
    override fun editCellAt(row: Int, column: Int, e: EventObject): Boolean {
        val result: Boolean = super.editCellAt(row, column, e)
        val editor: Component = getEditorComponent()
        if (editor == null || editor !is JTextComponent) return result
        if (e is KeyEvent) (editor as JTextComponent).selectAll()
        return result
    }

    override fun getColumnClass(c: Int): Class<*> {
        return Int::class.java
    }

    override fun getColumnCount(): Int {
        return strs.size
    }

    override fun getColumnName(c: Int): String {
        return strs[lnk.get(c)]
    }

    override fun getRowCount(): Int {
        return if (part == null) 0 else part!!.n
    }

    override fun getSelected(): Array<IntArray?> {
        val rows: IntArray = getSelectedRows()
        val ps = arrayOfNulls<IntArray>(rows.size)
        for (i in rows.indices) ps[i] = part!!.moves[rows[i]].clone()
        return ps
    }

    override fun getValueAt(r: Int, c: Int): Any {
        if (part == null || r < 0 || c < 0 || r >= part!!.n || c >= strs.size) return null
        return if (lnk.get(c) == 0) part!!.moves[r][0] - part!!.off else part!!.moves[r][lnk.get(c)]
    }

    override fun insert(dst: Int, data: Array<IntArray>): Boolean {
        val l: MutableList<IntArray> = ArrayList()
        for (p in part!!.moves) if (p != null) l.add(p)
        for (i in data.indices) l.add(i + dst, data[i])
        part!!.moves = l.toTypedArray()
        part!!.n = part!!.moves.size
        part!!.validate()
        part!!.check(anim)
        anim.unSave("maanim paste line")
        page.callBack(intArrayOf(1, dst, dst + data.size - 1))
        return true
    }

    override fun isCellEditable(r: Int, c: Int): Boolean {
        return true
    }

    override fun reorder(dst: Int, ori: IntArray): Boolean {
        val l: MutableList<IntArray> = ArrayList()
        val ab: MutableList<IntArray> = ArrayList()
        for (row in ori) {
            ab.add(part!!.moves[row])
            part!!.moves[row] = null
        }
        for (i in 0 until dst) if (part!!.moves[i] != null) l.add(part!!.moves[i])
        val ind = l.size
        l.addAll(ab)
        for (i in dst until part!!.n) if (part!!.moves[i] != null) l.add(part!!.moves[i])
        part!!.moves = l.toTypedArray()
        part!!.validate()
        part!!.check(anim)
        anim.unSave("maanim reorder line")
        page.callBack(intArrayOf(1, ind, ind + ori.size - 1))
        return true
    }

    @Synchronized
    override fun setValueAt(`val`: Any, r: Int, c: Int) {
        var c = c
        if (part == null || r >= part!!.n) return
        c = lnk.get(c)
        var v = `val` as Int
        val m = part!!.ints[1]
        if (c == 1) {
            if ((m < 4 || m > 11) && v < 0) v = 0
            if (m == 2 && v >= anim.imgcut.n) v = anim.imgcut.n - 1
            if (m == 12 && v > anim.mamodel.ints.get(2)) v = anim.mamodel.ints.get(2)
        }
        if (c == 0) v += part!!.off
        part!!.moves[r][c] = v
        part!!.validate()
        ma.validate()
        anim.unSave("maanim edit line")
        page.callBack(null)
    }

    fun setAnim(au: AnimCE?, maa: MaAnim?, p: Part?) {
        if (cellEditor != null) cellEditor.stopCellEditing()
        anim = au
        ma = maa
        part = p
    }

    companion object {
        private const val serialVersionUID = 1L
        private val strs = arrayOf("frame", "value", "easing", "parameter")
    }

    init {
        selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
        setTransferHandler(AnimTableTH<IntArray>(this, 3))
    }
}
