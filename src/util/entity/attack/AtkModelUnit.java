package util.entity.attack;

import util.basis.Basis;
import util.entity.EUnit;
import util.entity.EntCont;
import util.entity.Entity;
import util.pack.EffAnim;
import util.unit.EForm;
import util.unit.Unit;
import util.unit.UnitStore;

public class AtkModelUnit extends AtkModelEntity {

	private final Basis bas;

	protected AtkModelUnit(Entity ent, double d0) {
		super(ent, d0);
		bas = ent.basis.b;
	}

	@Override
	public AttackAb getAttack(int ind) {
		int[][] proc = new int[PROC_TOT][PROC_WIDTH];
		if (abis[ind] == 1) {

			setProc(ind, proc);
			proc[P_KB][0] = proc[P_KB][0] * (100 + bas.getInc(C_KB)) / 100;

			extraAtk(ind);

			if (b.r.nextDouble() * 100 < getProc(ind, P_SUMMON, 0)) {
				Unit u = UnitStore.get(getProc(ind, P_SUMMON, 1), true);
				int conf = getProc(ind, P_SUMMON, 4);
				int time = getProc(ind, P_SUMMON, 5);
				if (u != null && b.entityCount(-1) < b.max_num) {
					double up = getPos() + getDire() * getProc(ind, P_SUMMON, 2);
					int mult = getProc(ind, P_SUMMON, 3);
					EForm ef = new EForm(u.forms[0], mult);
					EUnit eu = ef.getEntity(b);
					eu.added(-1, (int) up);
					b.tempe.add(new EntCont(eu, time));
					conf &= 3;
					if (conf == 1) {
						eu.kbType = INT_WARP;
						eu.kbTime = EffAnim.effas[A_W].len(1);
						eu.status[P_WARP][2] = 1;
					}
					if (conf == 2)
						eu.kbTime = -3;
				}
			}
		}
		int atk = atks[ind];
		if (e.status[P_WEAK][1] != 0)
			atk = atk * e.status[P_WEAK][1] / 100;
		if (e.status[P_STRONG][0] != 0)
			atk += atk * (e.status[P_STRONG][0] + bas.getInc(C_STRONG)) / 100;
		double[] ints = inRange(ind);
		return new AttackSimple(this, atk, e.type, getAbi(), proc, ints[0], ints[1], e.data.getAtkModel(ind));
	}

	@Override
	protected int getProc(int ind, int type, int ety) {
		int ans = super.getProc(ind, type, ety);
		if (type == P_STOP && ety == 1)
			ans = ans * (100 + bas.getInc(C_STOP)) / 100;
		if (type == P_SLOW && ety == 1)
			ans = ans * (100 + bas.getInc(C_SLOW)) / 100;
		if (type == P_WEAK && ety == 1)
			ans = ans * (100 + bas.getInc(C_WEAK)) / 100;
		if (type == P_CRIT && ety == 0 && ans > 0)
			ans += bas.getInc(C_CRIT);
		// TODO treasure for unit Warp
		return ans;
	}

}
