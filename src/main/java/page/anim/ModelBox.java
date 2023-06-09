package page.anim;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import common.CommonStatic;
import common.system.P;
import common.system.fake.FakeGraphics;
import common.util.anim.EAnimS;
import jogl.GLCstd;
import jogl.util.GLGraphics;
import main.MainBCU;
import utilpc.Interpret;
import utilpc.awt.FG2D;

import java.awt.*;
import java.awt.image.BufferedImage;

interface ModelBox {
	static ModelBox getInstance() {
		if(MainBCU.USE_JOGL)
			return new GLModelBox();
		else
			return new BufferedModel();
	}

	P ori = new P(0, 0);

	EAnimS getEntity();

	void setEntity(EAnimS anim);

	double getSiz();

	void setSiz(double siz);

	void draw();

	Point getPoint(Point p);
}

class BufferedModel extends Canvas implements ModelBox {

	private static final long serialVersionUID = 1L;
	private static final Color c0 = new Color(70, 140, 160), c1 = new Color(85, 185, 205);

	public BufferedImage prev = null;

	protected double siz = 0.5;

	private EAnimS ent;

	protected BufferedModel() {
		setIgnoreRepaint(true);
	}

	public synchronized EAnimS getEnt() {
		return ent;
	}

	@Override
	public synchronized void paint(Graphics g) {
		prev = getImage();
		if (prev == null)
			return;
		g.drawImage(prev, 0, 0, null);
		g.dispose();
	}

	public synchronized void setEnt(EAnimS ent) {
		this.ent = ent;
	}

	@Override
	public double getSiz() {
		return siz;
	}

	@Override
	public void setSiz(double siz) {
		this.siz = siz;
	}

	@Override
	public void draw() {
		paint(getGraphics());
	}

	protected BufferedImage getImage() {
		int w = getWidth();
		int h = getHeight();
		BufferedImage img = (BufferedImage) createImage(w, h);
		if (img == null)
			return null;
		Graphics2D gra = (Graphics2D) img.getGraphics();
		if(CommonStatic.getConfig().viewerColor != -1) {
			gra.setColor(new Color(CommonStatic.getConfig().viewerColor));
			gra.fillRect(0, 0, w, h);
		} else {
			Paint p = gra.getPaint();
			GradientPaint gdt = new GradientPaint(w / 2f, 0, c0, w / 2f, h / 2f, c1, true);
			gra.setPaint(gdt);
			gra.fillRect(0, 0, w, h);
			gra.setPaint(p);
		}
		gra.translate(w / 2, h * 3 / 4);
		if (getEnt() != null)
			getEnt().draw(new FG2D(gra), ori.copy().times(-1), siz);
		gra.dispose();
		return img;
	}

	@Override
	public EAnimS getEntity() {
		return ent;
	}

	public void setEntity(EAnimS ieAnim) {
		ent = ieAnim;
	}

	@Override
	public Point getPoint(Point p) {
		return Interpret.getPoint(p, ori, siz);
	}
}

class GLModelBox extends GLCstd implements ModelBox {
	private static final long serialVersionUID = 1L;

	private double siz = 0.5;

	private EAnimS ent;

	int[] c = new int[] { 70, 140, 160 };
	int[] f = new int[] { 85, 185, 205 };

	@Override
	public EAnimS getEntity() {
		return ent;
	}

	@Override
	public void setEntity(EAnimS anim) {
		ent = anim;
	}

	@Override
	public double getSiz() {
		return siz;
	}

	@Override
	public void setSiz(double siz) {
		this.siz = siz;
	}

	@Override
	public void draw() {
		display();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		int w = getWidth();
		int h = getHeight();
		GLGraphics g = new GLGraphics(drawable.getGL().getGL2(), w, h);

		if(CommonStatic.getConfig().viewerColor != -1) {
			int rgb = CommonStatic.getConfig().viewerColor;

			int b = rgb & 0xFF;
			int gr = (rgb >> 8) & 0xFF;
			int r = (rgb >> 16) & 0xFF;

			g.setColor(r, gr, b);
			g.fillRect(0, 0, w, h);
		} else {
			g.gradRect(0, 0, w, h / 2, w / 2, 0, c, w / 2, h / 2, f);
			g.gradRect(0, h / 2, w, h / 2, w / 2, h / 2, f, w / 2, h, c);
		}
		drawModel(g);

		g.dispose();
		gl.glFlush();
	}

	private void drawModel(FakeGraphics g) {
		int w = getWidth();
		int h = getHeight();

		g.translate(w/2f, h*3/4f);
		g.setColor(FakeGraphics.BLACK);
		if(ent != null)
			ent.draw(g, ori.copy().times(-1), siz);
	}

	@Override
	public Point getPoint(Point p) {
		return Interpret.getPoint(p, ori, siz);
	}
}
