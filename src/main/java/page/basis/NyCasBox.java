package page.basis;

import page.battle.BattleBox;
import utilpc.awt.FG2D;

import java.awt.*;
import java.awt.image.BufferedImage;

class NyCasBox extends Canvas {

	private static final long serialVersionUID = 1L;

	private int[] ints;

	protected NyCasBox() {
		setIgnoreRepaint(true);
	}

	@Override
	public synchronized void paint(Graphics g) {
		if (ints == null)
			return;
		int w = getWidth();
		int h = getHeight();
		int sw = 128, sh = 258;
		double r = Math.min(1.0 * w / sw, 1.0 * h / sh);
		if (w * h == 0)
			return;
		BufferedImage img = (BufferedImage) createImage(w, h);
		if (img == null)
			return;
		BattleBox.BBPainter.drawNyCast(new FG2D(img.getGraphics()), h, 0, r, ints);
		g.drawImage(img, 0, 0, null);
		g.dispose();
	}

	protected void set(int[] nyc) {
		ints = nyc;
	}

}
