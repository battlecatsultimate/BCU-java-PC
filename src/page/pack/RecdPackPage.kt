package page.pack

import common.io.assets.Admin
import common.io.assets.Admin.StaticPermitted
import common.io.assets.AssetLoader
import common.io.assets.AssetLoader.AssetHeader
import common.io.assets.AssetLoader.AssetHeader.AssetEntry
import common.io.json.JsonEncoder
import common.io.json.Test
import common.io.json.Test.JsonTest_0.JsonD
import common.io.json.Test.JsonTest_2
import common.pack.PackData.UserPack
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.pack.Source.SourceAnimLoader
import common.pack.Source.SourceAnimSaver
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.util.stage.EStage
import common.util.stage.Recd
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import main.MainBCU
import page.JL
import page.JTF
import page.Page
import page.anim.AnimBox
import page.battle.AbRecdPage
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.event.FocusEvent
import java.io.File
import java.util.function.Consumer
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class RecdPackPage(p: Page?, pack: UserPack?) : AbRecdPage(p, pack.editable) {
    private val rena: JTF = JTF()
    private val jlr: JList<Recd> = JList<Recd>()
    private val jspr: JScrollPane = JScrollPane(jlr)
    private val pac: UserPack?
    override fun getSelection(): Recd {
        return jlr.getSelectedValue()
    }

    protected override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(jspr, x, y, 50, 100, 500, 1100)
        Page.Companion.set(rena, x, y, 600, 500, 300, 50)
    }

    protected override fun setList() {
        change(true)
        val r: Recd = jlr.getSelectedValue()
        jlr.setListData(pac.getReplays().toTypedArray())
        jlr.setSelectedValue(r, true)
        setRecd(r)
        change(false)
    }

    protected override fun setRecd(r: Recd?) {
        super.setRecd(r)
        rena.setEditable(r != null)
        rena.setText(if (r == null) "" else r.name)
    }

    private fun addListeners() {
        jlr.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (isAdj() || jlr.getValueIsAdjusting()) return
                setRecd(jlr.getSelectedValue())
            }
        })
        rena.setLnr(Consumer<FocusEvent> { x: FocusEvent? ->
            if (isAdj() || jlr.getValueIsAdjusting()) return@setLnr
            val r: Recd = jlr.getSelectedValue() ?: return@setLnr
            val f = File("./replay/" + r.name + ".replay")
            if (f.exists()) {
                var str: String = MainBCU.validate(rena.getText().trim { it <= ' ' })
                str = Recd.Companion.getAvailable(str)
                if (f.renameTo(File("./replay/$str.replay"))) {
                    Recd.Companion.map.remove(r.name)
                    r.name = str
                    Recd.Companion.map.put(r.name, r)
                }
            }
            rena.setText(r.name)
        })
    }

    private fun ini() {
        add(jspr)
        add(rena)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        pac = pack
        preini()
        ini()
        resized()
    }
}
