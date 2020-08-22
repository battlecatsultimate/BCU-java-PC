package page

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
import common.util.lang.LocaleCenter
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
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.function.Consumer
import javax.swing.JToggleButton

class JTG() : JToggleButton(), LocComp {
    private val lsc: LocSubComp

    constructor(binder: LocaleCenter.Binder?) : this() {
        lsc.init(binder)
    }

    constructor(i: Int, str: String) : this() {
        lsc.init(i, str)
    }

    constructor(str: String) : this(-1, str) {}

    override fun getLSC(): LocSubComp {
        return lsc
    }

    fun setLnr(c: Consumer<ActionEvent?>) {
        addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                c.accept(e)
            }
        })
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        lsc = LocSubComp(this)
    }
}
