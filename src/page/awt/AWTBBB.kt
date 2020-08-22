package page.awt

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
import page.battle.BattleBox
import page.battle.BattleBox.OuterBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter

class AWTBBB private constructor() : BBBuilder() {
    override fun getCtrl(bip: OuterBox?, bf: SBCtrl?): BattleBox {
        return BattleBoxDef(bip, bf, 1)
    }

    override fun getDef(bip: OuterBox?, bf: BattleField?): BattleBox {
        return BattleBoxDef(bip, bf, 0)
    }

    override val iconBox: IconBox
        get() = IconBoxDef()

    override fun getRply(bip: OuterBox?, bf: BattleField?, str: String, t: Boolean): BattleBox {
        return BBRecdAWT(bip, bf, str, t)
    }

    override val viewBox: ViewBox
        get() = ViewBoxDef()

    companion object {
        val INS = AWTBBB()
    }
}
