package util;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class EAnimU {

	public EPart[] ent = null;
	public int sele = -1;

	public int type;
	protected final AnimU a;
	protected int f = -1;

	protected MaAnim ma;

	protected final MaModel mamodel;

	protected List<EPart> order;

	protected EAnimU(AnimU ani, int i) {
		a = ani;
		mamodel = ani.mamodel;
		organize();
		ma = ani.anims[i];
		type = i;
	}

	public AnimU anim() {
		return a;
	}

	/** change the animation state, for entities only */
	public void changeAnim(int t) {
		if (t >= anim().anims.length)
			return;
		f = -1;
		ma = anim().anims[t];
		type = t;
	}

	public boolean done() {
		return f > ma.max;
	}

	public void draw(Graphics2D g, P ori, double siz) {
		if (f == -1) {
			f = 0;
			setup();
		}
		g.translate(ori.x, ori.y);

		for (EPart e : order)
			e.drawPart(g, new P(siz, siz));
	}

	public int ind() {
		return f;
	}

	public int len() {
		return ma.max + 1;
	}

	public void organize() {
		ent = mamodel.arrange(this);
		order = new ArrayList<>();
		for (EPart e : ent) {
			e.ea = this;
			order.add(e);
		}
		order.sort(null);
	}

	public void setup() {
		ma.update(0, this, false);
	}

	public void update(boolean rotate) {
		f++;
		ma.update(f, this, rotate);
	}

}
