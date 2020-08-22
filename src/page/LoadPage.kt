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
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import io.Progress
import page.JL
import page.Page
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.util.function.Consumer
import javax.swing.JLabel
import javax.swing.JProgressBar

class LoadPage : Page(null), Consumer<Progress?> {
    private val jl = JLabel()
    private val jpb: JProgressBar = JProgressBar()
    private var temp: String? = null
    override fun accept(dl: Progress) {
        jpb.setMaximum(1000)
        jpb.setValue((dl.prog * 1000).toInt())
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(jl, x, y, 100, 500, 2000, 50)
        Page.Companion.set(jpb, x, y, 100, 600, 2100, 50)
    }

    override fun timer(t: Int) {
        if (temp != null) {
            jl.text = temp
            temp = null
            jpb.setValue(0)
        }
    }

    private fun set(str: String) {
        temp = str
    }

    companion object {
        private const val serialVersionUID = 1L
        var lp: LoadPage?
        fun prog(str: String) {
            if (lp != null) lp!!.set(str)
        }
    }

    init {
        lp = this
        add(jl)
        add(jpb)
        resized()
    }
}
