package jogl.awt;

import jogl.GLStatic;
import page.anim.IconBox;
import page.awt.AWTBBB;
import page.awt.BBBuilder;
import page.battle.BattleBox;
import page.battle.BattleBox.OuterBox;
import page.view.ViewBox;
import util.basis.BattleField;
import util.basis.SBCtrl;

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
		return GLStatic.GLBOXTEST ? new GLBBRecd(bip, bf, str, t ? 1 : 0) : AWTBBB.INS.getRply(bip, bf, str, t);
	}

	@Override
	public ViewBox getViewBox() {
		return GLStatic.GLBOXTEST ? new GLViewBox(new ViewBox.Controller()) : AWTBBB.INS.getViewBox();
	}
}
