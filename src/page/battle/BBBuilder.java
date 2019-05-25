package page.battle;

import util.basis.BattleField;
import util.basis.SBCtrl;

public abstract class BBBuilder {

	public static BBBuilder def;

	public abstract BattleBox getCtrl(BattleInfoPage bip, SBCtrl bf);

	public abstract BattleBox getDef(BattleInfoPage bip, BattleField bf);

	public abstract BattleBox getRply(BattleInfoPage bip, BattleField bf, String str, boolean t);

}
