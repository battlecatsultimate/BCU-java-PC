package jogl.awt;

import page.battle.BBRecd;
import util.basis.BattleField;

class GLBBRecd extends GLBattleBox implements BBRecd {

	private static final long serialVersionUID = 1L;

	private final GLRecorder glr;

	protected GLBBRecd(OuterBox bip, BattleField bf, String path, int type) {
		super(bip, bf, 0);
		glr = GLRecorder.getIns(path, type, bip);
	}

	@Override
	public void end() {
		glr.end();

	}

	@Override
	public String info() {
		return "" + glr.remain();
	}

	@Override
	public void paint() {
		super.paint();
		glr.update();
	}

	@Override
	public void quit() {
		glr.quit();
	}

}
