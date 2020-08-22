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
import common.system.VImg
import common.util.Animable
import common.util.anim.AnimU
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.AbEnemy
import common.util.unit.UnitLevel
import io.BCPlayer
import main.MainBCU
import page.JL
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import utilpc.Theme
import utilpc.UtilPC
import java.awt.Component
import javax.swing.DefaultListCellRenderer
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.SwingConstants

class AnimLCR : DefaultListCellRenderer() {
    override fun getListCellRendererComponent(l: JList<*>?, o: Any, ind: Int, s: Boolean, f: Boolean): Component {
        val jl = super.getListCellRendererComponent(l, o, ind, s, f) as JLabel
        jl.text = o.toString()
        jl.icon = null
        jl.horizontalTextPosition = SwingConstants.RIGHT
        if (s && MainBCU.nimbus) {
            jl.setBackground(if (MainBCU.light) Theme.LIGHT.NIMBUS_SELECT_BG else Theme.DARK.NIMBUS_SELECT_BG)
        }
        val v: VImg?
        v = if (o is Animable<*, *>) (o as Animable<out AnimU<*>?, *>).anim.getEdi() else if (o is AbEnemy) (o as AbEnemy).getIcon() else null
        if (v == null) return jl
        jl.icon = UtilPC.getIcon(v)
        return jl
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
