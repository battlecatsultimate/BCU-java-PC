package util.entity.attack;

import java.awt.Graphics2D;

import util.Data;
import util.basis.StageBasis;
import util.system.P;

public abstract class ContAb extends Data {

	protected final StageBasis sb;

	public double pos;
	public boolean activate = true;

	protected ContAb(StageBasis b, double p) {
		sb = b;
		pos = p;
		sb.tlw.add(this);
	}

	public abstract void draw(Graphics2D gra, P p, double psiz);

	public abstract void update();

}
