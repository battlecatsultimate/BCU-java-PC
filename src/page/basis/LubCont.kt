package page.basis

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
import common.system.Node
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.Unit
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.Page
import page.anim.AnimBox
import page.info.UnitInfoPage
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent

abstract class LubCont protected constructor(p: Page?) : Page(p) {
    protected abstract fun getLub(): LineUpBox
    override fun keyTyped(e: KeyEvent) {
        if (Character.isDigit(e.keyChar)) {
            var i = (e.keyChar.toString() + "").toInt()
            if (i == 0) i = 9 else i--
            getLub().setPos(i)
        } else if (e.keyChar == 'd') {
            getLub().setPos(10)
        }
    }

    override fun mouseClicked(e: MouseEvent) {
        if (e.source === getLub()) if (e.button == MouseEvent.BUTTON1) getLub().click(e.point) else if (getLub().sf != null) {
            val n = Node<Unit>(getLub().sf!!.unit)
            changePanel(UnitInfoPage(this, n))
        }
    }

    override fun mouseDragged(e: MouseEvent) {
        if (e.source === getLub()) getLub().drag(e.point)
    }

    override fun mousePressed(e: MouseEvent) {
        if (e.source === getLub()) getLub().press(e.point)
    }

    override fun mouseReleased(e: MouseEvent) {
        if (e.source === getLub()) getLub().release(e.point)
    }

    override fun timer(t: Int) {
        getLub().paint(getLub().getGraphics())
        resized()
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
