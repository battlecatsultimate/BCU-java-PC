package page.info

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
import common.util.stage.MapColc
import common.util.stage.Stage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import main.Opts
import page.JBTN
import page.JL
import page.Page
import page.anim.AnimBox
import page.battle.StRecdPage
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import java.util.function.Consumer
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class StageViewPage(p: Page?, collection: Collection<MapColc?>?) : StagePage(p) {
    private val jlmc: JList<MapColc> = JList<MapColc>()
    private val jspmc: JScrollPane = JScrollPane(jlmc)
    private val jlsm: JList<StageMap> = JList<StageMap>()
    private val jspsm: JScrollPane = JScrollPane(jlsm)
    private val jlst: JList<Stage> = JList<Stage>()
    private val jspst: JScrollPane = JScrollPane(jlst)
    private val cpsm: JBTN = JBTN(0, "cpsm")
    private val cpst: JBTN = JBTN(0, "cpst")
    private val dgen: JBTN = JBTN(0, "dungeon")
    private val recd: JBTN = JBTN(0, "replay")
    private val info: JBTN = JBTN(0, "info")

    constructor(p: Page?, col: Collection<MapColc?>?, st: Stage?) : this(p, col) {
        if (st == null) return
        jlmc.setSelectedValue(st.map.mc, true)
        jlsm.setSelectedValue(st.map, true)
        jlst.setSelectedValue(st, true)
    }

    protected override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(jspsm, x, y, 0, 50, 400, 1150)
        Page.Companion.set(jspmc, x, y, 400, 50, 400, 500)
        Page.Companion.set(jspst, x, y, 400, 550, 400, 650)
        Page.Companion.set(cpsm, x, y, 50, 1200, 300, 50)
        Page.Companion.set(cpst, x, y, 450, 1200, 300, 50)
        Page.Companion.set(dgen, x, y, 600, 0, 200, 50)
        Page.Companion.set(strt, x, y, 400, 0, 200, 50)
        Page.Companion.set(recd, x, y, 1850, 300, 200, 50)
        Page.Companion.set(info, x, y, 1600, 300, 200, 50)
    }

    protected override fun setData(st: Stage?) {
        super.setData(st)
        cpst.setEnabled(st != null)
        recd.setEnabled(st != null)
    }

    private fun addListeners() {
        info.setLnr(Consumer { x: ActionEvent? ->
            if (stage == null || stage.info == null) return@setLnr
            Opts.pop(stage.info.getHTML(), "stage info")
        })
        recd.setLnr(Consumer { x: ActionEvent? -> changePanel(StRecdPage(this, stage, false)) })
        jlmc.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.getValueIsAdjusting()) return
                val mc: MapColc = jlmc.getSelectedValue() ?: return
                jlsm.setListData(mc.maps)
                jlsm.setSelectedIndex(0)
            }
        })
        jlsm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.getValueIsAdjusting()) return
                val sm: StageMap = jlsm.getSelectedValue()
                cpsm.setEnabled(false)
                if (sm == null) return
                cpsm.setEnabled(true)
                jlst.setListData(Vector<Stage>(sm.list))
                jlst.setSelectedIndex(0)
            }
        })
        jlst.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.getValueIsAdjusting()) return
                val s: Stage = jlst.getSelectedValue()
                cpst.setEnabled(false)
                if (s == null) return
                setData(s)
            }
        })
        cpsm.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val sm: StageMap = jlsm.getSelectedValue() ?: return
                val mc: MapColc = Stage.Companion.CLIPMC
                val copy: StageMap = sm.copy(mc)
                mc.maps = Arrays.copyOf(mc.maps, mc.maps.size + 1)
                mc.maps.get(mc.maps.size - 1) = copy
            }
        })
        cpst.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val stage: Stage = jlst.getSelectedValue() ?: return
                Stage.Companion.CLIPSM.add(stage.copy(Stage.Companion.CLIPSM))
            }
        })
        dgen.setLnr(Consumer { x: ActionEvent? -> changePanel(StageRandPage(getThis())) })
    }

    private fun ini() {
        add(jspmc)
        add(jspsm)
        add(jspst)
        add(recd)
        add(cpsm)
        add(cpst)
        add(dgen)
        add(info)
        cpsm.setEnabled(false)
        cpst.setEnabled(false)
        recd.setEnabled(false)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        jlmc.setListData(Vector<MapColc>(collection))
        ini()
        resized()
    }
}
