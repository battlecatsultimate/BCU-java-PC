package page.anim;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import common.CommonStatic;
import common.system.P;
import common.system.fake.FakeGraphics;
import common.util.anim.EAnimD;
import common.util.anim.EAnimU;
import jogl.GLCstd;
import jogl.util.GLGraphics;
import main.MainBCU;
import main.Timer;
import utilpc.awt.FG2D;

import java.awt.*;
import java.awt.image.BufferedImage;

interface AnimBox {
	static AnimBox getInstance() {
		if(MainBCU.USE_JOGL)
			return new GLAnimBox();
		else
			return new BufferedAnim();
	}

	P ori = new P(0, 0);

	EAnimD<?> getEntity();

	void setEntity(EAnimU ieAnim);

	void setSele(int val);

	void update();

	void setSiz(double siz);

	double getSiz();

	void draw();
}

class BufferedAnim extends Canvas implements AnimBox {

	private static final long serialVersionUID = 1L;
	private static final Color c0 = new Color(70, 140, 160), c1 = new Color(85, 185, 205);

	public BufferedImage prev = null;

	private EAnimD<?> ent;

	private double siz = 0.5;

	protected BufferedAnim() {
		setIgnoreRepaint(true);
	}

	@Override
	public void draw() {
		paint(getGraphics());
	}

	@Override
	public synchronized void paint(Graphics g) {
		prev = getImage();
		if (prev == null)
			return;
		g.drawImage(prev, 0, 0, null);
		if (CommonStatic.getConfig().ref) {
			g.setColor(Color.ORANGE);
			g.drawString("Time cost: " + Timer.inter + "%", 20, 20);
		}
		g.dispose();
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
		if (ent != null)
			ent.draw(new FG2D(gra), ori.copy().times(-1), siz);
		gra.dispose();
		return img;
	}

	@Override
	public EAnimD<?> getEntity() {
		return ent;
	}

	@Override
	public void setEntity(EAnimU ieAnim) {
		ent = ieAnim;
	}

	@Override
	public void setSele(int val) {
		if (ent != null)
			ent.sele = val;
	}

	@Override
	public synchronized void update() {
		if (ent != null) {
			if (ent instanceof EAnimU) {
				EAnimU e = (EAnimU) ent;
				ent.update(e.type.rotate());
			} else {
				ent.update(true);
			}
		}
	}

	@Override
	public void setSiz(double siz) {
		this.siz = siz;
	}

	@Override
	public double getSiz() {
		return siz;
	}
}

class GLAnimBox extends GLCstd implements AnimBox {

	private static final long serialVersionUID = 1L;

	int[] c = new int[] { 70, 140, 160 };
	int[] f = new int[] { 85, 185, 205 };

	private double siz = 0.5;

	private EAnimD<?> ent;

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

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

		drawAnim(g);

		g.dispose();
		gl.glFlush();
	}

	private void drawAnim(FakeGraphics g) {
		int w = getWidth();
		int h = getHeight();

		g.translate(w/2f, h*3/4f);
		g.setColor(FakeGraphics.BLACK);
		if(ent != null)
			ent.draw(g, ori.copy().times(-1), siz);
	}

	@Override
	public EAnimD<?> getEntity() {
		return ent;
	}

	@Override
	public void setEntity(EAnimU ieAnim) {
		ent = ieAnim;
	}

	@Override
	public void setSele(int val) {
		if (ent != null)
			ent.sele = val;
	}

	@Override
	public void update() {
		if (ent != null) {
			if (ent instanceof EAnimU) {
				EAnimU e = (EAnimU) ent;
				ent.update(e.type.rotate());
			} else {
				ent.update(true);
			}
		}
	}

	@Override
	public void setSiz(double siz) {
		this.siz = siz;
	}

	@Override
	public double getSiz() {
		return siz;
	}

	@Override
	public void draw() {
		display();
	}
}