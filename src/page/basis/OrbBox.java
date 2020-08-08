package page.basis;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import common.battle.data.Orb;
import common.system.fake.FakeGraphics;
import utilpc.awt.FG2D;

public class OrbBox extends Canvas {
	private static final long serialVersionUID = 1L;

	private int[] orbs;

	public OrbBox(int[] orbs) {
		this.orbs = orbs;

		setIgnoreRepaint(true);
	}

	public void changeOrb(int[] orbs) {
		this.orbs = orbs;
	}

	@Override
	public synchronized void paint(Graphics g) {
		if (orbs.length == 0) {
			g.clearRect(0, 0, getWidth(), getHeight());
			return;
		}

		double a = Math.min(getWidth(), getHeight());
		double w = getWidth(), h = getHeight();

		BufferedImage img = (BufferedImage) createImage((int) a, (int) a);

		FG2D f = new FG2D(img.getGraphics());

		f.drawImage(Orb.TRAITS[Orb.reverse(orbs[1])], 0, 0, a, a);
		f.setComposite(FakeGraphics.TRANS, 204, 0);
		f.drawImage(Orb.TYPES[orbs[0]], 0, 0, a, a);
		f.setComposite(FakeGraphics.DEF, 0, 0);
		f.drawImage(Orb.GRADES[orbs[2]], 0, 0, a, a);

		g.drawImage(img, (int) ((w - a) / 2), (int) ((h - a) / 2), null);
		g.dispose();
	}
}
