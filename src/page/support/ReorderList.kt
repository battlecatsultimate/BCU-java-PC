package page.support

import common.battle.data.DataEntity
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
import main.Printer
import page.JL
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.util.*
import javax.swing.DefaultListModel
import javax.swing.JList

class ReorderList<T> : JList<T>, Reorderable {
    private val model: DefaultListModel<T>
    val copymap: MutableMap<Int, T> = TreeMap<Int, T>()
    var list: ReorderListener<T>? = null
    var copable = false

    constructor() {
        model = DefaultListModel<T>()
        setModel(model)
        try {
            setTransferHandler(InListTH<T>(this))
            setDragEnabled(true)
        } catch (e: Exception) {
            Printer.e("ReorderList", 24, "cannot drag row")
        }
    }

    constructor(vec: Array<T>) : this() {
        setListData(vec)
    }

    constructor(vec: Vector<T>) : this() {
        setListData(vec)
    }

    constructor(vec: Vector<T>, cls: Class<out T>?, str: String?) {
        model = DefaultListModel<T>()
        setModel(model)
        try {
            setTransferHandler(InListTH<T>(this, cls, str))
            setDragEnabled(true)
            copable = true
        } catch (e: Exception) {
            Printer.e("ReorderList", 24, "cannot drag row")
        }
        setListData(vec)
    }

    fun add(t: T?): Boolean {
        if (t == null) return false
        if (list!!.add(t)) model.addElement(t) else return false
        return true
    }

    override fun reorder(ori: Int, fin: Int) {
        var fin = fin
        if (list != null) list.reordering()
        val lm: DefaultListModel<T> = getModel() as DefaultListModel<T>
        val `val`: T = lm.get(ori)
        lm.remove(ori)
        if (fin > ori) fin--
        lm.add(fin, `val`)
        if (list != null) list.reordered(ori, fin)
    }

    override fun setListData(data: Array<T>) {
        model.clear()
        for (t in data) model.addElement(t)
    }

    override fun setListData(data: Vector<out T>) {
        model.clear()
        for (t in data) model.addElement(t)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
