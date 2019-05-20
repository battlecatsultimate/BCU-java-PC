package util.anim;

import java.awt.Color;
import java.awt.Point;

import util.system.P;
import util.system.fake.FakeGraphics;

public class EAnimS extends EAnimI {

	public EAnimS(AnimI ia, MaModel mm) {
		super(ia, mm);
	}

	@Override
	public void draw(FakeGraphics g, P ori, double siz) {
		set(g);
		g.translate(ori.x, ori.y);
		if (ref && !battle) {
			Point p0 = new P(-200, 0).times(siz).toPoint();
			Point p1 = new P(400, 100).times(siz).toPoint();
			Point p2 = new P(0, -300).times(siz).toPoint();
			g.drawRect(p0.x, p0.y, p1.x, p1.y);
			g.setColor(Color.RED);
			g.drawLine(0, 0, p2.x, p2.y);
		}
		for (EPart e : order)
			e.drawPart(g, new P(siz, siz));
		if (sele >= 0 && sele < ent.length)
			ent[sele].drawScale(g, new P(siz, siz));
	}

	@Override
	public int ind() {
		return 0;
	}

	@Override
	public int len() {
		return 0;
	}

	@Override
	public void setTime(int value) {
	}

	@Override
	public void update(boolean b) {
	}

}
