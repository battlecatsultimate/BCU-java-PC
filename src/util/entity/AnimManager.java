package util.entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import util.Data;
import util.anim.AnimD;
import util.anim.EAnimD;
import util.pack.EffAnim;
import util.system.P;

public class AnimManager extends Data {

	private final Entity e;
	private final int[][] status;

	/** responsive effect FSM time */
	private int efft;

	/** responsive effect FSM type */
	private int eftp;

	/**
	 * on-entity effect icons<br>
	 * index defined by Data.A_()
	 */
	private final EAnimD[] effs = new EAnimD[A_TOT];

	public AnimManager(Entity ent) {
		e = ent;
		status = e.status;
	}

	/** draw the effect icons */
	public void drawEff(Graphics2D g, P p, double siz) {
		if (e.dead != -1)
			return;
		AffineTransform at = g.getTransform();
		int EWID = 48;
		double x = p.x;
		if (effs[eftp] != null) {
			effs[eftp].draw(g, p, siz);
		}
		for (EAnimD eae : effs) {
			if (eae == null)
				continue;
			g.setTransform(at);
			eae.draw(g, new P(x, p.y), siz);
			x -= EWID * e.dire * siz;
		}
	}

	/** get a effect icon */
	public void getEff(int t) {
		int dire = e.dire;
		if (t == INV) {
			effs[eftp] = null;
			eftp = A_EFF_INV;
			effs[eftp] = EffAnim.effas[eftp].getEAnim(0);
			efft = EffAnim.effas[eftp].len(0);
		}
		if (t == P_WAVE) {
			int id = dire == -1 ? A_WAVE_INVALID : A_E_WAVE_INVALID;
			effs[id] = EffAnim.effas[id].getEAnim(0);
			status[P_WAVE][0] = EffAnim.effas[id].len(0);
		}
		if (t == STPWAVE) {
			effs[eftp] = null;
			eftp = dire == -1 ? A_WAVE_STOP : A_E_WAVE_STOP;
			effs[eftp] = EffAnim.effas[eftp].getEAnim(0);
			efft = EffAnim.effas[eftp].len(0);
		}
		if (t == INVWARP) {
			effs[eftp] = null;
			eftp = dire == -1 ? A_FARATTACK : A_E_FARATTACK;
			effs[eftp] = EffAnim.effas[eftp].getEAnim(0);
			efft = EffAnim.effas[eftp].len(0);
		}
		if (t == P_STOP) {
			int id = dire == -1 ? A_STOP : A_E_STOP;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_SLOW) {
			int id = dire == -1 ? A_SLOW : A_E_SLOW;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_WEAK) {
			int id = dire == -1 ? A_DOWN : A_E_DOWN;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_CURSE) {
			int id = A_CURSE;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_POISON) {
			int id = A_POISON;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_SEAL) {
			int id = A_SEAL;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_STRONG) {
			int id = dire == -1 ? A_UP : A_E_UP;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_LETHAL) {
			int id = dire == -1 ? A_SHIELD : A_E_SHIELD;
			AnimD ea = EffAnim.effas[id];
			status[P_LETHAL][1] = ea.len(0);
			effs[id] = ea.getEAnim(0);
		}
		if (t == P_WARP) {
			AnimD ea = EffAnim.effas[A_W];
			int pa = status[P_WARP][2];
			e.basis.lea.add(new WaprCont(e.pos, pa, e.anim));
			status[P_WARP][pa] = ea.len(pa);

		}

		if (t == BREAK_ABI) {
			int id = dire == -1 ? A_U_E_B : A_E_B;
			effs[id] = EffAnim.effas[id].getEAnim(0);
			status[P_BREAK][0] = effs[id].len();
		}
		if (t == BREAK_ATK) {
			int id = dire == -1 ? A_U_E_B : A_E_B;
			effs[id] = EffAnim.effas[id].getEAnim(1);
			status[P_BREAK][0] = effs[id].len();
		}
		if (t == BREAK_NON) {
			int id = dire == -1 ? A_U_B : A_B;
			effs[id] = EffAnim.effas[id].getEAnim(4);
			status[P_BREAK][0] = effs[id].len();
		}
	}

	/** update effect icons animation */
	protected void checkEff() {
		int dire = e.dire;
		if (efft == 0)
			effs[eftp] = null;
		if (status[P_STOP][0] == 0) {
			int id = dire == -1 ? A_STOP : A_E_STOP;
			effs[id] = null;
		}
		if (status[P_SLOW][0] == 0) {
			int id = dire == -1 ? A_SLOW : A_E_SLOW;
			effs[id] = null;
		}
		if (status[P_WEAK][0] == 0) {
			int id = dire == -1 ? A_DOWN : A_E_DOWN;
			effs[id] = null;
		}
		if (status[P_CURSE][0] == 0) {
			int id = A_CURSE;
			effs[id] = null;
		}
		if (status[P_POISON][0] == 0) {
			int id = A_POISON;
			effs[id] = null;
		}
		if (status[P_SEAL][0] == 0) {
			int id = A_SEAL;
			effs[id] = null;
		}
		if (status[P_LETHAL][1] == 0) {
			int id = dire == -1 ? A_SHIELD : A_E_SHIELD;
			effs[id] = null;
		} else
			status[P_LETHAL][1]--;
		if (status[P_WAVE][0] == 0) {
			int id = dire == -1 ? A_WAVE_INVALID : A_E_WAVE_INVALID;
			effs[id] = null;
		} else
			status[P_WAVE][0]--;
		if (status[P_STRONG][0] == 0) {
			int id = dire == -1 ? A_UP : A_E_UP;
			effs[id] = null;
		}
		if (status[P_BREAK][0] == 0) {
			int id = dire == -1 ? A_U_B : A_B;
			effs[id] = null;
		} else
			status[P_BREAK][0]--;
		efft--;

	}

	protected void update() {
		for (int i = 0; i < effs.length; i++)
			if (effs[i] != null)
				effs[i].update(false);
	}

}
