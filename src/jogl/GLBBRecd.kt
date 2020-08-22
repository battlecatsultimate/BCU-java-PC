package jogl

import common.battle.BattleField
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
import jogl.GLRecorder
import page.JL
import page.anim.AnimBox
import page.battle.BBRecd
import page.battle.BattleBox.OuterBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter

internal class GLBBRecd(bip: OuterBox?, bf: BattleField?, path: String?, type: Int) : GLBattleBox(bip, bf, 0), BBRecd {
    private val glr: GLRecorder
    private var time = 0
    override fun end() {
        glr.end()
    }

    override fun info(): String {
        return "" + glr.remain()
    }

    override fun paint() {
        super.paint()
        if (bbp.bf.sb.time > time) {
            glr.update()
            time = bbp.bf.sb.time
        }
    }

    override fun quit() {
        glr.quit()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        glr = GLRecorder.Companion.getIns(this, path, type, bip)
        glr.start()
    }
}
