package util;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.image.BufferedImage;

public class ImgCore {

	private static final Key KA = RenderingHints.KEY_ALPHA_INTERPOLATION;
	private static final Object KAS = RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED;
	private static final Object KAD = RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT;
	private static final Object KAQ = RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY;
	private static final Key KC = RenderingHints.KEY_COLOR_RENDERING;
	private static final Object KCS = RenderingHints.VALUE_COLOR_RENDER_SPEED;
	private static final Object KCD = RenderingHints.VALUE_COLOR_RENDER_DEFAULT;
	private static final Object KCQ = RenderingHints.VALUE_COLOR_RENDER_QUALITY;
	private static final Key KF = RenderingHints.KEY_FRACTIONALMETRICS;
	private static final Object KFS = RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
	private static final Object KFD = RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT;
	private static final Object KFQ = RenderingHints.VALUE_FRACTIONALMETRICS_ON;
	private static final Key KI = RenderingHints.KEY_INTERPOLATION;
	private static final Object KIS = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
	private static final Object KID = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
	private static final Object KIQ = RenderingHints.VALUE_INTERPOLATION_BICUBIC;

	private static final Key[] KEYS = new Key[] { KA, KC, KF, KI };
	private static final Object[][] VALS = new Object[][] { { KAS, KAD, KAQ }, { KCS, KCD, KCQ }, { KFS, KFD, KFQ },
			{ KIS, KID, KIQ } };
			
	public static int deadOpa = 10, fullOpa = 90;
	public static int[] ints = { 1, 1, 1, 1 };

	public static void setRenderingHints(Graphics2D g) {
		for (int i = 0; i < 4; i++)
			g.setRenderingHint(KEYS[i], VALS[i][ints[i]]);
	}

	protected static void drawImg(Graphics2D g, BufferedImage bimg, P piv, P sc, double opa, boolean glow,
			double extend) {
		Composite c = g.getComposite();
		if (opa < fullOpa * 0.01 - 1e-5)
			if (!glow)
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) opa));
			else
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
				BufferedImage par = bimg.getSubimage(0, 0, w, h);
				drawImage(g, par, x, -piv.y, sc.x * extend, sc.y);
			}
		}
		g.setComposite(c);
	}

	private static void drawImage(Graphics2D g, BufferedImage bimg, double x, double y, double w, double h) {
		int ix = (int) Math.round(x);
		int iy = (int) Math.round(y);
		int iw = (int) Math.round(w);
		int ih = (int) Math.round(h);
		g.drawImage(bimg, ix, iy, iw, ih, null);

	}

}
