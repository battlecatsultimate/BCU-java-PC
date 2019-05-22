package util.system.fake;

import static java.awt.AlphaComposite.SRC_OVER;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;

public class FG2D implements FakeGraphics {

	private static final Object KAS = RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED;
	private static final Object KAD = RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT;
	private static final Object KAQ = RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY;
	private static final Object KCS = RenderingHints.VALUE_COLOR_RENDER_SPEED;
	private static final Object KCD = RenderingHints.VALUE_COLOR_RENDER_DEFAULT;
	private static final Object KCQ = RenderingHints.VALUE_COLOR_RENDER_QUALITY;
	private static final Object KFS = RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
	private static final Object KFD = RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT;
	private static final Object KFQ = RenderingHints.VALUE_FRACTIONALMETRICS_ON;
	private static final Object KIS = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
	private static final Object KID = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
	private static final Object KIQ = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
	private static final Key KA = RenderingHints.KEY_ALPHA_INTERPOLATION;
	private static final Key KC = RenderingHints.KEY_COLOR_RENDERING;
	private static final Key KF = RenderingHints.KEY_FRACTIONALMETRICS;
	private static final Key KI = RenderingHints.KEY_INTERPOLATION;
	private static final Key[] KEYS = new Key[] { KA, KC, KF, KI };
	private static final Object[][] VALS = new Object[][] { { KAS, KAD, KAQ }, { KCS, KCD, KCQ }, { KFS, KFD, KFQ },
			{ KIS, KID, KIQ } };

	private final Graphics2D g;
	private final Composite comp;

	public FG2D(Graphics graphics) {
		g = (Graphics2D) graphics;
		comp = g.getComposite();
	}

	@Override
	public void colRect(int x, int y, int w, int h, int r, int gr, int b, int... a) {
		int al = a.length > 0 ? a[0] : 255;
		Color c = new Color(r, gr, b, al);
		g.setColor(c);
		g.fillRect(x, y, w, h);
	}

	@Override
	public void drawImage(FakeImage bimg, int i, int j) {
		g.drawImage((Image) bimg.bimg(), i, j, null);
	}

	@Override
	public void drawImage(FakeImage bimg, int ix, int iy, int iw, int ih) {
		g.drawImage((Image) bimg.bimg(), ix, iy, iw, ih, null);
	}

	@Override
	public void drawLine(int i, int j, int x, int y) {
		g.drawLine(i, j, x, y);
	}

	@Override
	public void drawOval(int i, int j, int k, int l) {
		g.drawOval(i, j, k, l);
	}

	@Override
	public void drawRect(int x, int y, int x2, int y2) {
		g.drawRect(x, y, x2, y2);
	}

	@Override
	public void fillOval(int i, int j, int k, int l) {
		g.fillOval(i, j, k, l);
	}

	@Override
	public void fillRect(int x, int y, int w, int h) {
		g.fillRect(x, y, w, h);
	}

	@Override
	public FakeTransform getTransform() {
		return new FTAT(g.getTransform());
	}

	@Override
	public void gradRect(int x, int y, int w, int h, int a, int b, int[] c, int d, int e, int[] f) {
		g.setPaint(new GradientPaint(a, b, new Color(c[0], c[1], c[2]), d, e, new Color(f[0], f[1], f[2])));
		g.fillRect(x, y, w, h);
	}

	@Override
	public void rotate(double d) {
		g.rotate(d);
	}

	@Override
	public void scale(int hf, int vf) {
		g.scale(hf, vf);
	}

	@Override
	public void setColor(int c) {
		if (c == RED)
			g.setColor(Color.RED);
		if (c == YELLOW)
			g.setColor(Color.YELLOW);
		if (c == BLACK)
			g.setColor(Color.BLACK);
		if (c == MAGENTA)
			g.setColor(Color.MAGENTA);
		if (c == BLUE)
			g.setColor(Color.BLUE);
		if (c == CYAN)
			g.setColor(Color.CYAN);
	}

	@Override
	public void setComposite(int mode, int... para) {
		if (mode == DEF)
			g.setComposite(comp);
		if (mode == TRANS)
			g.setComposite(AlphaComposite.getInstance(SRC_OVER, (float) (para[0] / 256.0)));
		if (mode == BLEND)
			g.setComposite(new Blender(para[0], para[1]));
		if (mode == GRAY)
			g.setComposite(new Converter(para[0]));

	}

	@Override
	public void setRenderingHint(int key, int val) {
		g.setRenderingHint(KEYS[key], VALS[key][val]);
	}

	@Override
	public void setTransform(FakeTransform at) {
		g.setTransform(at.getAT());
	}

	@Override
	public void translate(double x, double y) {
		g.translate(x, y);
	}

}
