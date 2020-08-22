package page.awt;

import common.battle.BattleField;
import common.battle.SBCtrl;
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
