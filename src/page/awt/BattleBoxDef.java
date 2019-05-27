package page.awt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.Timer;
import page.battle.BBCtrl;
import page.battle.BattleBox;
import util.basis.BattleField;
import util.basis.SBCtrl;
import util.system.fake.FakeGraphics;
import util.system.fake.awt.FG2D;

class BattleBoxDef extends Canvas implements BattleBox {

	private static final long serialVersionUID = 1L;

	public final BBPainter bbp;

	protected BufferedImage prev;

	protected BattleBoxDef(OuterBox bip, BattleField bas, int p) {
		bbp = p == 0 ? new BBPainter(bip, bas, this) : new BBCtrl(bip, (SBCtrl) bas, this);
		setIgnoreRepaint(true);
	}

	@Override
	public BBPainter getPainter() {
		return bbp;
	}

	@Override
	public void paint() {
		paint(getGraphics());
	}

	@Override
	public void paint(Graphics g) {
		synchronized (bbp) {
			int w = getWidth();
			int h = getHeight();
			if (w * h == 0)
				return;
			if (bbp.pt < bbp.bf.sb.time || prev == null) {
				bbp.pt = bbp.bf.sb.time;
				prev = getImage();
			}
			if (prev == null)
				return;
			g.drawImage(prev, 0, 0, null);
			g.setColor(Color.ORANGE);
			g.drawString("Time cost: " + Timer.inter + "%, " + bbp.pt, 20, 20);
			g.dispose();
		}
	}

	@Override
	public void reset() {
		prev = null;
	}

	protected BufferedImage getImage() {
		int w = getWidth();
		int h = getHeight();
		BufferedImage img = (BufferedImage) createImage(w, h);
		if (img == null)
			return null;
		Graphics2D gra = (Graphics2D) img.getGraphics();
		FakeGraphics g = new FG2D(gra);
		bbp.draw(g);
		gra.dispose();
		return img;
	}

}
