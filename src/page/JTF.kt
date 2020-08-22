package page

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
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.function.Consumer
import javax.swing.JTextField

class JTF @JvmOverloads constructor(tos: String? = "") : JTextField(tos), CustomComp {
    fun setLnr(c: Consumer<FocusEvent?>) {
        addFocusListener(object : FocusAdapter() {
            override fun focusLost(e: FocusEvent?) {
                c.accept(e)
            }
        })
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(ke: KeyEvent) {
                if (ke.keyCode == KeyEvent.VK_ENTER) transferFocus()
            }
        })
    }
}
