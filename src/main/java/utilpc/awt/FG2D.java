package utilpc.awt;

import common.system.fake.FakeGraphics;
import common.system.fake.FakeImage;
import common.system.fake.FakeTransform;

import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import static java.awt.AlphaComposite.SRC_OVER;

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
	public void colRect(float x, float y, float w, float h, int r, int gr, int b, int a) {
		int al = a >= 0 ? a : 255;
		Color c = new Color(r, gr, b, al);
		g.setColor(c);
		g.fillRect((int) x, (int) y, (int) w, (int) h);
	}

	@Override
	public void drawImage(FakeImage bimg, float i, float j) {
		g.drawImage((Image) bimg.bimg(), (int) i, (int) j, null);
	}

	@Override
	public void drawImage(FakeImage bimg, float ix, float iy, float iw, float ih) {
		g.drawImage((Image) bimg.bimg(), (int) ix, (int) iy, (int) iw, (int) ih, null);
	}

	@Override
	public void drawLine(float i, float j, float x, float y) {
		g.drawLine((int) i, (int) j, (int) x, (int) y);
	}

	@Override
	public void drawOval(float i, float j, float k, float l) {
		g.drawOval((int) i, (int) j, (int) k, (int) l);
	}

	@Override
	public void drawRect(float x, float y, float x2, float y2) {
		g.drawRect((int) x, (int) y, (int) x2, (int) y2);
	}

	@Override
	public void fillOval(float i, float j, float k, float l) {
		g.fillOval((int) i, (int) j, (int) k, (int) l);
	}

	@Override
	public void fillRect(float x, float y, float w, float h) {
		g.fillRect((int) x, (int) y, (int) w, (int) h);
	}

	@Override
	public FakeTransform getTransform() {
		return new FTAT(g.getTransform());
	}

	@Override
	public void gradRect(float x, float y, float w, float h, float a, float b, int[] c, float d, float e, int[] f) {
		g.setPaint(new GradientPaint(a, b, new Color(c[0], c[1], c[2]), d, e, new Color(f[0], f[1], f[2])));
		g.fillRect((int) x, (int) y, (int) w, (int) h);
	}

	@Override
	public void gradRectAlpha(float x, float y, float w, float h, float a, float b, int al, int[] c, float d, float e, int al2, int[] f) {
		g.setPaint(new GradientPaint(a, b, new Color(c[0], c[1], c[2], al), d, e, new Color(f[0], f[1], f[2], al2)));
		g.fillRect((int) x, (int) y, (int) w, (int) h);
	}

	@Override
	public void rotate(float d) {
		g.rotate(d);
	}

	@Override
	public void scale(float hf, float vf) {
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
		if (c == WHITE)
			g.setColor(Color.WHITE);
	}

	@Override
	public void setColor(int r, int g, int b) {
		this.g.setColor(new Color(r, g, b));
	}

	@Override
	public void setComposite(int mode, int p0, int p1) {
		if (mode == DEF)
			g.setComposite(comp);
		if (mode == TRANS)
			g.setComposite(AlphaComposite.getInstance(SRC_OVER, (float) (p0 / 256.0)));
		if (mode == BLEND)
			g.setComposite(new Blender(p0, p1));
		if (mode == GRAY)
			g.setComposite(new Converter(p0));

	}

	@Override
	public void setRenderingHint(int key, int val) {
		g.setRenderingHint(KEYS[key], VALS[key][val]);
	}

	@Override
	public void setTransform(FakeTransform at) {
		g.setTransform((AffineTransform) at.getAT());
	}

	@Override
	public void translate(float x, float y) {
		g.translate(x, y);
	}

	public void fillPath(Polygon p) {
		g.fillPolygon(p);
	}

	public void enableAntialiasing() {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void setGradient(int x, int y, int x2, int y2, Color c, Color c2) {
		LinearGradientPaint gra = new LinearGradientPaint(x, y, x2, y2, new float[] {0.4f, 1f}, new Color[] {c, c2});

		g.setPaint(gra);
	}

	public void drawFontOutline(Path2D path, float width) {
		Stroke st = g.getStroke();
		Color c = g.getColor();

		Stroke round = new BasicStroke(width,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

		setColor(BLACK);

		g.setStroke(round);
		g.draw(path);

		g.setStroke(st);
		g.setColor(c);
	}

	public void fillPath2D(Path2D path) {
		g.fill(path);
	}
}
