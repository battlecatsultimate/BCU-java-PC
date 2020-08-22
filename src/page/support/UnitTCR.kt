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
import common.util.unit.Form
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
import utilpc.UtilPC
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.SwingConstants
import javax.swing.table.DefaultTableCellRenderer

class UnitTCR(private val lnk: IntArray?) : DefaultTableCellRenderer() {
    override fun getTableCellRendererComponent(t: JTable, v: Any, s: Boolean, f: Boolean, r: Int, c: Int): Component {
        var c = c
        val comp: Component = super.getTableCellRendererComponent(t, v, s, f, r, c)
        if (lnk != null) c = lnk[c]
        if (c != 1) return comp
        val jl = comp as JLabel
        val e = v as Form ?: return jl
        jl.text = e.toString()
        jl.icon = null
        jl.horizontalTextPosition = SwingConstants.RIGHT
        val vimg = e.anim.edi ?: return jl
        jl.icon = UtilPC.getIcon(vimg)
        return jl
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
