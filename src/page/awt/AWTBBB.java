package page.awt;

import page.anim.IconBox;
import page.battle.BattleBox;
import page.battle.BattleBox.OuterBox;
import page.view.ViewBox;
import util.basis.BattleField;
import util.basis.SBCtrl;

public class AWTBBB extends BBBuilder {

	public static final AWTBBB INS = new AWTBBB();

	private AWTBBB() {
	}

	@Override
	public BattleBox getCtrl(OuterBox bip, SBCtrl bf) {
		return new BattleBoxDef(bip, bf, 1);
	}

	@Override
	public BattleBox getDef(OuterBox bip, BattleField bf) {
		return new BattleBoxDef(bip, bf, 0);
	}

	@Override
	public IconBox getIconBox() {
		return new IconBoxDef();
	}

	@Override
	public BattleBox getRply(OuterBox bip, BattleField bf, String str, boolean t) {
		return new BBRecdAWT(bip, bf, str, t);
	}

	@Override
	public ViewBox getViewBox() {
		return new ViewBoxDef();
	}

}
