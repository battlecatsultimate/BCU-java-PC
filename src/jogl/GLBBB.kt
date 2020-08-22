package jogl

import common.battle.BattleField
import common.battle.SBCtrl
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
import page.JL
import page.anim.AnimBox
import page.anim.IconBox
import page.awt.BBBuilder
import page.awt.RecdThread
import page.battle.BattleBox
import page.battle.BattleBox.OuterBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter

class GLBBB : BBBuilder() {
    override fun getCtrl(bip: OuterBox?, bf: SBCtrl?): BattleBox {
        return GLBattleBox(bip, bf, 1)
    }

    override fun getDef(bip: OuterBox?, bf: BattleField?): BattleBox {
        return GLBattleBox(bip, bf, 0)
    }

    val iconBox: IconBox
        get() = GLIconBox()

    override fun getRply(bip: OuterBox?, bf: BattleField?, str: String, t: Boolean): BattleBox {
        return GLBBRecd(bip, bf, str, if (t) RecdThread.Companion.PNG else RecdThread.Companion.MP4)
    }

    val viewBox: ViewBox
        get() = GLViewBox(Controller())
}
