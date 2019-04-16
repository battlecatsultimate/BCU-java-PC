package util.entity.attack;

import util.entity.Cannon;

public class AttackCanon extends AttackSimple {

	public AttackCanon(Cannon c, int ATK, int t, int eab, int[][] pro, double p0, double p1) {
		super(c, ATK, t, eab, pro, p0, p1);
		canon = c.id;
		waveType |= WT_CANN;
		if (canon == 5)
			touch = TCH_UG | TCH_N;
	}

}
