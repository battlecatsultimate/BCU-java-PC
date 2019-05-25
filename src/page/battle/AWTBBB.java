package page.battle;

import util.basis.BattleField;
import util.basis.SBCtrl;

public class AWTBBB extends BBBuilder {

	public static final AWTBBB INS = new AWTBBB();

	private AWTBBB() {
	}

	@Override
	public BattleBox getCtrl(BattleInfoPage bip, SBCtrl bf) {
		return new BattleBoxDef(bip, bf, 1);
	}

	@Override
	public BattleBox getDef(BattleInfoPage bip, BattleField bf) {
		return new BattleBoxDef(bip, bf, 0);
	}

	@Override
	public BattleBox getRply(BattleInfoPage bip, BattleField bf, String str, boolean t) {
		return new BBRecdAWT(bip, bf, str, t);
	}

}
