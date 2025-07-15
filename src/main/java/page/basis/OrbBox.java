package page.basis;

import common.CommonStatic;
import common.battle.data.Orb;
import common.system.fake.FakeGraphics;
import utilpc.awt.FG2D;

import java.awt.*;
import java.awt.image.BufferedImage;

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

		float a = Math.min(getWidth(), getHeight());
		float w = getWidth(), h = getHeight();

		BufferedImage img = (BufferedImage) createImage((int) a, (int) a);

		FG2D f = new FG2D(img.getGraphics());
		f.setRenderingHint(3, CommonStatic.getConfig().ints[2]);

		f.drawImage(CommonStatic.getBCAssets().TRAITS[0][Orb.reverse(orbs[1])], 0, 0, a, a);
		f.setComposite(FakeGraphics.TRANS, 204, 0);
		f.drawImage(CommonStatic.getBCAssets().TYPES[0][orbs[0]], 0, 0, a, a);
		f.setComposite(FakeGraphics.DEF, 0, 0);
		f.drawImage(CommonStatic.getBCAssets().GRADES[0][orbs[2]], 0, 0, a, a);

		g.drawImage(img, (int) ((w - a) / 2), (int) ((h - a) / 2), null);
		g.dispose();
	}
}
