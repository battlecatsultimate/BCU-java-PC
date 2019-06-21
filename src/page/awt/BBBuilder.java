package page.awt;

import common.util.basis.BattleField;
import common.util.basis.SBCtrl;
import page.anim.IconBox;
import page.battle.BattleBox;
import page.battle.BattleBox.OuterBox;
import page.view.ViewBox;

public abstract class BBBuilder {

	public static BBBuilder def;

	public abstract BattleBox getCtrl(OuterBox bip, SBCtrl bf);

	public abstract BattleBox getDef(OuterBox bip, BattleField bf);

	public abstract IconBox getIconBox();

	public abstract BattleBox getRply(OuterBox bip, BattleField bf, String str, boolean t);

	public abstract ViewBox getViewBox();

}
