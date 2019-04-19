package util.entity.attack;

import util.entity.EEnemy;
import util.entity.EntCont;
import util.pack.EffAnim;
import util.unit.AbEnemy;
import util.unit.EnemyStore;

public class AtkModelEnemy extends AtkModelEntity {

	protected AtkModelEnemy(EEnemy ent, double d0) {
		super(ent, d0);
	}

	@Override
	public AttackAb getAttack(int ind) {
		int[][] proc = new int[PROC_TOT][PROC_WIDTH];
		if (abis[ind] == 1) {
			extraAtk(ind);

			setProc(ind, proc);

			if (b.r.nextDouble() * 100 < getProc(ind, P_SPAWN, 0)) {
				AbEnemy ene = EnemyStore.getAbEnemy(getProc(ind, P_SPAWN, 1), false);
				int conf = getProc(ind, P_SPAWN, 4);
				int time = getProc(ind, P_SPAWN, 5);
				if (ene != null && (b.entityCount(1) < b.st.max || (conf & 4) > 0)) {
					double ep = getPos() + getDire() * getProc(ind, P_SPAWN, 2);
					double mult = getProc(ind, P_SPAWN, 3) * 0.01;
					if ((conf & 8) == 0)
						mult *= ((EEnemy) e).mult;
					EEnemy ee = ene.getEntity(b, acs[ind], mult, 0, e.layer, e.layer);
					if (ep < ee.data.getWidth())
						ep = ee.data.getWidth();
					if (ep > b.st.len - 800)
						ep = b.st.len - 800;
					ee.added(1, (int) ep);
					b.tempe.add(new EntCont(ee, time));
					if ((conf & 16) != 0)
						ee.health = e.health;
					conf &= 3;
					if (conf == 1) {
						ee.kbType = INT_WARP;
						ee.kbTime = EffAnim.effas[A_W].len(1);
						ee.status[P_WARP][2] = 1;
					}
					if (conf == 2 && ee.anim.anim().anims.length >= 7)
						ee.kbTime = -3;
				}
			}

		}
		int atk = atks[ind];
		if (e.status[P_WEAK][1] != 0)
			atk = atk * e.status[P_WEAK][1] / 100;
		if (e.status[P_STRONG][0] != 0)
			atk += atk * e.status[P_STRONG][0] / 100;
		double[] ints = inRange(ind);
		return new AttackSimple(this, atk, e.type, getAbi(), proc, ints[0], ints[1], e.data.getAtkModel(ind));
	}

	@Override
	protected int getProc(int ind, int type, int ety) {
		if (e.status[P_CURSE][0] > 0 && (type == P_KB || type == P_STOP || type == P_SLOW || type == P_WEAK
				|| type == P_WARP || type == P_CURSE || type == P_SNIPER || type == P_SEAL))
			return 0;
		return super.getProc(ind, type, ety);
	}

}
