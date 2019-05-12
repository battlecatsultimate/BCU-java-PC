package util.anim;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import util.BattleObj;
import util.system.P;

public abstract class EAnimI extends BattleObj {

	public int sele = -1;
	public EPart[] ent = null;

	protected final AnimI a;
	protected MaModel mamodel;
	protected List<EPart> order;

	public EAnimI(AnimI ia, MaModel mm) {
		a = ia;
		mamodel = mm;
		organize();
	}

	public AnimI anim() {
		return a;
	}

	public abstract void draw(Graphics2D g, P ori, double siz);

	public abstract int ind();

	public abstract int len();

	public void organize() {
		ent = mamodel.arrange(this);
		order = new ArrayList<>();
		for (EPart e : ent) {
			e.ea = this;
			order.add(e);
		}
		order.sort(null);
	}

	public abstract void setTime(int value);

	public abstract void update(boolean b);

}
