package page.awt

import common.battle.BattleField
import common.battle.SBCtrl
import page.anim.IconBox
import page.battle.BattleBox
import page.battle.BattleBox.OuterBox
import page.view.ViewBox

abstract class BBBuilder {
    abstract fun getCtrl(bip: OuterBox?, bf: SBCtrl?): BattleBox
    abstract fun getDef(bip: OuterBox?, bf: BattleField?): BattleBox
    abstract val iconBox: IconBox
    abstract fun getRply(bip: OuterBox?, bf: BattleField?, str: String, t: Boolean): BattleBox
    abstract val viewBox: ViewBox

    companion object {
        var def: BBBuilder? = null
    }
}
