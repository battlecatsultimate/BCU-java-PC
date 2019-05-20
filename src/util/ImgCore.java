package util;

import static java.awt.AlphaComposite.SRC_OVER;
import static java.awt.AlphaComposite.getInstance;

import java.awt.Composite;
import java.awt.Point;
import util.system.P;
import util.system.fake.FakeGraphics;
import util.system.fake.FakeImage;

public class ImgCore extends Data {

	public static final String[] NAME = new String[] { "opacity", "color", "accuracy", "scale" };
	public static final String[] VAL = new String[] { "fast", "default", "quality" };

	public static int deadOpa = 10, fullOpa = 90;
	public static int[] ints = new int[] { 1, 1, 1, 1 };
	public static boolean ref = true, battle = false;

	public static void set(FakeGraphics g) {
		if (battle)
			return;
		for (int i = 0; i < 4; i++)
			g.setRenderingHint(i, ints[i]);
	}

	protected static void drawImg(FakeGraphics g, FakeImage bimg, P piv, P sc, double opa, boolean glow,
			double extend) {
		Composite c = g.getComposite();
		if (opa < fullOpa * 0.01 - 1e-5)
			if (!glow) {
				Composite a = getInstance(SRC_OVER, (float) opa);
				g.setComposite(a);
			} else
				g.setComposite(new Blender((int) (opa * 256), glow ? 1 : 0));
		else if (glow)
			g.setComposite(new Blender(256, 1));
		if (extend == 0)
			drawImage(g, bimg, -piv.x, -piv.y, sc.x, sc.y);
		else {
			double x = -piv.x;
			while (extend > 1) {
				drawImage(g, bimg, x, -piv.y, sc.x, sc.y);
				x += sc.x;
				extend--;
			}
			int w = (int) (bimg.getWidth() * extend);
			int h = bimg.getHeight();
			if (w > 0) {
				FakeImage par = bimg.getSubimage(0, 0, w, h);
				drawImage(g, par, x, -piv.y, sc.x * extend, sc.y);
			}
		}
		g.setComposite(c);
	}

	protected static void drawSca(FakeGraphics g, Point piv, Point sc) {
		g.setColor(FakeGraphics.RED);
		g.fillOval(-10, -10, 20, 20);
		g.drawOval(-40, -40, 80, 80);
		int x = -piv.x;
		int y = -piv.y;
		if (sc.x < 0)
			x += sc.x;
		if (sc.y < 0)
			y += sc.y;
		int sx = Math.abs(sc.x);
		int sy = Math.abs(sc.y);
		g.drawRect(x, y, sx, sy);
		g.setColor(FakeGraphics.YELLOW);
		g.drawRect(x - 40, y - 40, sx + 80, sy + 80);
	}

	private static void drawImage(FakeGraphics g, FakeImage bimg, double x, double y, double w, double h) {
		int ix = (int) Math.round(x);
		int iy = (int) Math.round(y);
		int iw = (int) Math.round(w);
		int ih = (int) Math.round(h);
		g.drawImage(bimg, ix, iy, iw, ih);

	}

}
