package page.view

import common.CommonStatic
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
import common.pack.UserProfile
import common.util.anim.AnimI
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
import java.util.*
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class EffectViewPage(p: Page?) : AbViewPage(p) {
    private val jlu: JList<AnimI<*, *>> = JList<AnimI<*, *>>()
    private val jspu: JScrollPane = JScrollPane(jlu)
    protected override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(jspu, x, y, 50, 100, 300, 1100)
    }

    protected override fun updateChoice() {
        val u: AnimI<*, *> = jlu.getSelectedValue() ?: return
        setAnim(u)
    }

    private fun addListeners() {
        jlu.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.getValueIsAdjusting()) return
                updateChoice()
            }
        })
    }

    private fun ini() {
        preini()
        add(jspu)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        val va: Vector<AnimI<*, *>> = Vector<AnimI<*, *>>()
        for (a in CommonStatic.getBCAssets().effas.values()) va.add(a)
        for (a in CommonStatic.getBCAssets().atks) va.add(a)
        va.addAll(UserProfile.Companion.getBCData().souls.getList())
        jlu.setListData(va)
        ini()
        resized()
    }
}
