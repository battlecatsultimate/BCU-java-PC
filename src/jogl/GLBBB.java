package jogl;

import page.battle.AWTBBB;
import page.battle.BBBuilder;
import page.battle.BattleBox;
import page.battle.BattleInfoPage;
import util.basis.BattleField;
import util.basis.SBCtrl;

public class GLBBB extends BBBuilder {

	@Override
	public BattleBox getCtrl(BattleInfoPage bip, SBCtrl bf) {
		return new GLBattleBox(bip, bf, 1);
	}

	@Override
	public BattleBox getDef(BattleInfoPage bip, BattleField bf) {
		return new GLBattleBox(bip, bf, 0);
	}

	@Override
	public BattleBox getRply(BattleInfoPage bip, BattleField bf, String str, boolean t) {
		return AWTBBB.INS.getRply(bip, bf, str, t);
		// TODO change to GL recorder
	}

}
