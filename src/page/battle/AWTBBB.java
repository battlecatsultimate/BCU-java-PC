package page.battle;

import util.basis.BattleField;
import util.basis.SBCtrl;

public class AWTBBB extends BBBuilder {

	@Override
	public BattleBox getCtrl(BattleInfoPage bip, SBCtrl bf) {
		return new BBCtrl(bip, bf);
	}

	@Override
	public BattleBox getDef(BattleInfoPage bip, BattleField bf) {
		return new BattleBoxDef(bip, bf);
	}

	@Override
	public BattleBox getRply(BattleInfoPage bip, BattleField bf, String str, boolean t) {
		return new BBRecd(bip, bf, str, t);
	}

}
