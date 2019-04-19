package util.entity;

import java.awt.Graphics2D;

import util.Data;
import util.anim.EAnimD;
import util.system.P;

public class EAnimCont extends Data {

	public final double pos;
	private final EAnimD anim;

	public EAnimCont(double p, EAnimD ead) {
		pos = p;
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
