package page.battle;

import page.battle.BattleBox.OuterBox;
import util.basis.BattleField;
import util.basis.SBCtrl;

public abstract class BBBuilder {

	public static BBBuilder def;

	public abstract BattleBox getCtrl(OuterBox bip, SBCtrl bf);

	public abstract BattleBox getDef(OuterBox bip, BattleField bf);

	public abstract BattleBox getRply(OuterBox bip, BattleField bf, String str, boolean t);

}
