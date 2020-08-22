package page.awt

import common.battle.BattleField
import common.battle.SBCtrl
import page.anim.IconBox
import page.battle.BattleBox
import page.battle.BattleBox.OuterBox
import page.view.ViewBox

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
