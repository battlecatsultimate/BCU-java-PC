package util.entity;

import java.awt.Graphics2D;

import util.anim.EAnimD;
import util.basis.StageBasis;
import util.entity.attack.AtkModelAb;
import util.entity.attack.AttackAb;
import util.entity.attack.AttackSimple;
import util.pack.EffAnim;
import util.system.P;

public class Sniper extends AtkModelAb {

	private EAnimD anim = EffAnim.effas[A_SNIPER].getEAnim(0);
	private EAnimD atka = EffAnim.effas[A_SNIPER].getEAnim(1);
	private int coolTime = SNIPER_CD, preTime = 0, atkTime = 0, angle = 0;
	public boolean enabled = true;
	public double pos, height,updown;

	public Sniper(StageBasis sb) {
		super(sb);
	}

	/** attack part of animation */
	public void drawAtk(Graphics2D g, P ori, double siz) {
		// TODO
	}

	/** base part of animation */
	public void drawBase(Graphics2D g, P ori, double siz) {
		// TODO
		// double angle = Math.atan2(getPos() - pos, height);
		
		height = ori.y;
		if (atkTime == 0)
			anim.draw(g, ori, siz);
		else
			atka.draw(g,ori, siz);

	}

	@Override
	public int getAbi() {
		return 0;
	}

	@Override
	public int getDire() {
		return -1;
	}

	@Override
	public double getPos() {
		return b.st.len + SNIPER_POS;
	}

	public void update() {
		
		if (enabled && coolTime > 0)
			coolTime--;
		if (coolTime == 0 && enabled && pos > 0) {
			coolTime = SNIPER_CD;
			preTime = SNIPER_PRE;
			atkTime = atka.len();
			atka.setup();
		}
		if (preTime > 0) {
			preTime--;
			if (preTime == 0) {
				// attack
				int atk = b.b.t().getBaseHealth() / 20;
				int[][] proc = new int[PROC_TOT][PROC_WIDTH];
				proc[P_SNIPER][0] = 1;
				AttackAb a = new AttackSimple(this, atk, -1, 0, proc, 0, getPos(), false);
				a.canon = -1;
				b.getAttack(a);
			}
		}
		if (atkTime > 0) {
			atkTime--;
			atka.update(false);
		} else {
			anim.update(true);
		}

		// find enemy pos
		pos = -1;
		for (Entity e : b.le)
			if (e.dire == 1 && e.pos > pos && !e.isBase && (e.touchable() & TCH_N) > 0)
				pos = e.pos;
				
		//Get angle of cannon and bullet
		angle =  -(int)(Math.atan2(height,getPos()-pos+300)*1800);

		anim.ent[4].alter(11, angle);
		atka.ent[4].alter(11, angle);
		
		//Get distance which bullet will fly
		//path = new P(-(getPos()-pos),height);
	}
}
