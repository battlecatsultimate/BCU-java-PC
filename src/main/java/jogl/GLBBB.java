package jogl;

import common.battle.BattleField;
import common.battle.SBCtrl;
import page.anim.IconBox;
import page.awt.BBBuilder;
import page.awt.RecdThread;
import page.battle.BattleBox;
import page.battle.BattleBox.OuterBox;
import page.view.ViewBox;

public class GLBBB extends BBBuilder {

	@Override
	public BattleBox getCtrl(OuterBox bip, SBCtrl bf) {
		return new GLBattleBox(bip, bf, 1);
	}

	@Override
	public BattleBox getDef(OuterBox bip, BattleField bf) {
		return new GLBattleBox(bip, bf, 0);
	}

	@Override
	public IconBox getIconBox() {
		return new GLIconBox();
	}

	@Override
	public BattleBox getRply(OuterBox bip, BattleField bf, String str, boolean t) {
		return new GLBBRecd(bip, bf, str, t ? RecdThread.PNG : RecdThread.MP4);
	}

	@Override
	public ViewBox getViewBox() {
		return new GLViewBox(new ViewBox.Controller());
	}
}
