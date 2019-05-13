package util.entity;

import java.awt.Graphics2D;

import util.BattleObj;
import util.anim.EAnimD;
import util.system.P;

public class EAnimCont extends BattleObj {

	public final double pos;
	public final int layer;
	private final EAnimD anim;

	public EAnimCont(double p, int lay, EAnimD ead) {
		pos = p;
		layer = lay;
		anim = ead;
	}

	/** return whether this animation is finished */
	public boolean done() {
		return anim.done();
	}

	public void draw(Graphics2D gra, P p, double psiz) {
		anim.draw(gra, p, psiz);
	}

	public void update() {
		anim.update(false);

	}

}
