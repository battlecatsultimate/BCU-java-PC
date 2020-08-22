package jogl

import common.battle.BattleField
import common.battle.SBCtrl
import page.anim.IconBox
import page.awt.BBBuilder
import page.awt.RecdThread
import page.battle.BattleBox
import page.battle.BattleBox.OuterBox
import page.view.ViewBox
import page.view.ViewBox.Controller

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
