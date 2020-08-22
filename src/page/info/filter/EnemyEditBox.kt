package page.info.filter

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
import page.JL
import page.Page
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import utilpc.Interpret
import java.util.*
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class EnemyEditBox(p: Page?, private val editable: Boolean) : Page(p) {
    private val vt = Vector<String>()
    private val va = Vector<String>()
    private val trait = AttList(3, 0)
    private val abis = AttList(-1, Interpret.EABIIND.size)
    private val jt: JScrollPane = JScrollPane(trait)
    private val jab: JScrollPane = JScrollPane(abis)
    private var changing = false
    fun setData(vals: IntArray) {
        changing = true
        trait.clearSelection()
        for (i in 0..8) if (vals[0] shr i and 1 > 0) trait.addSelectionInterval(i, i)
        abis.clearSelection()
        for (i in Interpret.EABIIND.indices) {
            val ind: Int = Interpret.EABIIND.get(i)
            if (if (ind < 100) vals[1] shr ind and 1 > 0 else vals[2] shr ind - 100 - Interpret.IMUSFT and 1 > 0) abis.addSelectionInterval(i, i)
        }
        changing = false
    }

    override fun resized(x: Int, y: Int) {
        Page.Companion.set(jt, x, y, 0, 0, 200, 400)
        Page.Companion.set(jab, x, y, 0, 450, 200, 650)
    }

    private fun confirm() {
        val ans = IntArray(3)
        for (i in 0..8) if (trait.isSelectedIndex(i)) ans[0] = ans[0] or (1 shl i)
        for (i in Interpret.EABIIND.indices) if (abis.isSelectedIndex(i)) if (Interpret.EABIIND.get(i) < 100) ans[1] = ans[1] or (1 shl Interpret.EABIIND.get(i)) else ans[2] = ans[2] or (1 shl Interpret.EABIIND.get(i) - 100 - Interpret.IMUSFT)
        front.callBack(ans)
    }

    private fun ini() {
        for (i in 0..8) vt.add(Interpret.TRAIT.get(i))
        for (i in Interpret.EABIIND.indices) va.add(Interpret.EABI.get(i))
        trait.setListData(vt)
        abis.setListData(va)
        val m: Int = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        trait.setSelectionMode(m)
        abis.setSelectionMode(m)
        set(trait)
        set(abis)
        add(jt)
        add(jab)
        trait.setEnabled(editable)
        abis.setEnabled(editable)
    }

    private fun set(jl: JList<*>) {
        jl.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (!changing && !jl.getValueIsAdjusting()) confirm()
            }
        })
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
    }
}
