package page.info

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
import common.util.stage.Stage
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
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class StageFilterPage(p: Page?, ls: List<Stage?>) : StagePage(p) {
    private val jlst: JList<Stage> = JList<Stage>()
    private val jspst: JScrollPane = JScrollPane(jlst)
    protected override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(jspst, x, y, 400, 550, 300, 650)
        Page.Companion.set(strt, x, y, 400, 0, 300, 50)
    }

    private fun addListeners() {
        jlst.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.getValueIsAdjusting()) return
                val s: Stage = jlst.getSelectedValue() ?: return
                setData(s)
            }
        })
    }

    private fun ini() {
        add(jspst)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        jlst.setListData(ls.toTypedArray())
        ini()
        resized()
    }
}
