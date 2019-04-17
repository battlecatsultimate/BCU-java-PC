package util.entity;

import util.basis.BasisLU;
import util.basis.StageBasis;
import util.entity.attack.AttackAb;
import util.pack.EffAnim;

public class ECastle extends AbEntity {

	private final StageBasis sb;

	public ECastle(StageBasis b) {
		super(b.st.health);
		sb = b;
	}

	public ECastle(StageBasis xb, BasisLU b) {
		super(b.t().getBaseHealth());
		sb = xb;
	}

	@Override
	public void damaged(AttackAb atk) {
		int ans = atk.atk;
		if ((atk.abi & AB_BASE) > 0)
			ans *= 4;
		if (atk.getProc(P_CRIT)[0] > 0) {
			ans *= 0.01 * atk.getProc(P_CRIT)[0];
			sb.lea.add(new EAnimCont(pos, EffAnim.effas[A_CRIT].getEAnim(0)));
		}
		health -= ans;
		if (health > maxH)
			health = maxH;
	}

	@Override
	public int getAbi() {
		return 0;
	}

	@Override
	public boolean isBase() {
		return true;
	}

	@Override
	public void postUpdate() {

	}

	@Override
	public boolean targetable(int type) {
		return true;
	}

	@Override
	public int touchable() {
		return TCH_N;
	}

	@Override
	public void update() {

	}

}
