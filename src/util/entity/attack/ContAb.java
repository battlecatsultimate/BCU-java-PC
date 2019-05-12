package util.entity.attack;

import java.awt.Graphics2D;

import util.Copible;
import util.basis.StageBasis;
import util.system.P;

public abstract class ContAb extends Copible {

	protected final StageBasis sb;

	public double pos;
	public boolean activate = true;
	public int layer;

	protected ContAb(StageBasis b, double p, int lay) {
		sb = b;
		pos = p;
		layer = lay;
		sb.tlw.add(this);
	}

	public abstract void draw(Graphics2D gra, P p, double psiz);

	public abstract void update();

}
