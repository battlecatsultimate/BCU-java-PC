package page.support

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
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import javax.swing.JTable
import javax.swing.ListSelectionModel
import javax.swing.event.TableModelListener
import javax.swing.table.DefaultTableColumnModel
import javax.swing.table.TableColumn
import javax.swing.table.TableModel

abstract class AbJTable protected constructor() : JTable(), TableModel {
    protected val lnk: IntArray
    private val tm = TModel(this)
    override fun addTableModelListener(arg0: TableModelListener) {}
    abstract fun getColumnClass(c: Int): Class<*>?
    abstract fun getColumnCount(): Int
    abstract fun getColumnName(c: Int): String
    abstract fun getRowCount(): Int
    abstract fun getValueAt(r: Int, c: Int): Any
    override fun isCellEditable(arg0: Int, arg1: Int): Boolean {
        return false
    }

    override fun removeTableModelListener(arg0: TableModelListener) {}
    override fun setValueAt(arg0: Any, arg1: Int, arg2: Int) {}
    fun swap(c: Int, nc: Int) {
        val t = lnk[nc]
        lnk[nc] = lnk[c]
        lnk[c] = t
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        lnk = IntArray(getColumnCount())
        for (i in lnk.indices) lnk[i] = i
        setColumnModel(tm)
        setModel(this)
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
    }
}

internal class TModel(private val t: AbJTable) : DefaultTableColumnModel() {
    override fun moveColumn(c: Int, nc: Int) {
        if (c == nc) return
        t.swap(c, nc)
        super.moveColumn(c, nc)
    }

    override fun removeColumn(tc: TableColumn) {}

    companion object {
        private const val serialVersionUID = 1L
    }
}
