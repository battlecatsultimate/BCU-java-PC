package jogl;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import common.CommonStatic;
import common.system.fake.FakeGraphics;
import common.system.fake.FakeImage;
import common.system.fake.FakeTransform;
import common.util.anim.EAnimI;
import common.util.anim.EAnimU;
import jogl.util.GLGraphics;
import page.JTG;
import page.anim.IconBox;
import page.view.ViewBox;
import page.view.ViewBox.Loader;
import utilpc.awt.FG2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

import static page.anim.IconBox.IBConf.*;

class GLIconBox extends GLViewBox implements IconBox {

	private static final long serialVersionUID = 1L;

	protected GLIconBox() {
		super(new IBCtrl());
		setFocusable(true);
		glow = 0;
		changeType();
	}

	@Override
	public void changeType() {
		FakeImage bimg = CommonStatic.getBCAssets().ico[mode][type].getImg();
		line[2] = bimg.getWidth();
		line[3] = bimg.getHeight();
	}

	@Override
	public void draw(FakeGraphics gra) {
		boolean b = CommonStatic.getConfig().ref;
		CommonStatic.getConfig().ref = false;
		getCtrl().predraw(gra);
		FakeTransform at = gra.getTransform();
		super.draw(gra);
		gra.setTransform(at);
		CommonStatic.getConfig().ref = b;
		getCtrl().postdraw(gra);
	}

	@Override
	public BufferedImage getClip() {
		Rectangle r = getBounds();
		Point p = getLocationOnScreen();
		r.x = p.x + line[0];
		r.y = p.y + line[1];
		r.width = line[2];
		r.height = line[3];
		try {
			BufferedImage clip = new Robot().createScreenCapture(r);
			FakeImage img = CommonStatic.getBCAssets().ico[mode][type].getImg();

			int bw = img.getWidth();
			int bh = img.getHeight();

			double a = Math.min(1.0 * line[2] / bw, 1.0 * line[3] / bh);

			Image tmp = clip.getScaledInstance((int) (line[2] / a), (int) (line[3] /a), Image.SCALE_SMOOTH);

			BufferedImage res = new BufferedImage(bw, bh, BufferedImage.TYPE_3BYTE_BGR);
			res.getGraphics().drawImage(tmp, 0, 0, null);

			return res;

		} catch (AWTException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public IBCtrl getCtrl() {
		return (IBCtrl) ctrl;
	}

	@Override
	public void setBlank(boolean selected) {
		blank = selected;
	}

	@Override
	public void updateControllerDimension(int w, int h) {
		getCtrl().w = w;
		getCtrl().h = h;
		getCtrl().synchronizeDimension();
	}

}

class GLVBExporter implements ViewBox.VBExporter {

	private final GLViewBox vb;

	private Loader loader;
	private GLRecdBImg glr;

	protected GLVBExporter(GLViewBox box) {
		vb = box;
	}

	@Override
	public void end(JTG btn) {
		if (loader == null)
			return;
		loader.finish(btn);
		glr.end();
		loader = null;
		glr = null;
	}

	@Override
	public BufferedImage getPrev() {
		return vb.getScreen(true);
	}

	@Override
	public Loader start() {
		if (loader != null)
			return loader;
		Queue<BufferedImage> qb = new ArrayDeque<>();
		loader = new Loader(qb);
		glr = new GLRecdBImg(vb, qb, loader.thr);
		loader.start();
		return loader;
	}

	protected void update() {
		if (glr != null)
			glr.update();
	}

}

class GLViewBox extends GLCstd implements ViewBox, GLEventListener {

	private static final long serialVersionUID = 1L;

	protected final Controller ctrl;
	protected final GLVBExporter exp;

	protected boolean blank;

	private EAnimI ent;

	protected GLViewBox(Controller c) {
		exp = new GLVBExporter(this);
		ctrl = c;
		c.setCont(this);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		int w = getWidth();
		int h = getHeight();
		GLGraphics g = new GLGraphics(drawable.getGL().getGL2(), w, h);

		if (!blank) {
			if(CommonStatic.getConfig().viewerColor != -1) {
				int rgb = CommonStatic.getConfig().viewerColor;

				int b = rgb & 0xFF;
				int gr = (rgb >> 8) & 0xFF;
				int r = (rgb >> 16) & 0xFF;

				g.setColor(r, gr, b);
				g.fillRect(0, 0, w, h);
			} else {
				int[] c = new int[] { c0.getRed(), c0.getGreen(), c0.getBlue() };
				int[] f = new int[] { c1.getRed(), c1.getGreen(), c1.getBlue() };
				g.gradRect(0, 0, w, h / 2, w / 2, 0, c, w / 2, h / 2, f);
				g.gradRect(0, h / 2, w, h / 2, w / 2, h / 2, f, w / 2, h, c);
			}
		} else {
			g.setColor(FakeGraphics.WHITE);
			g.fillRect(0, 0, w, h);
		}
		draw(g);
		g.dispose();
		gl.glFlush();
	}

	@Override
	public Controller getCtrl() {
		return ctrl;
	}

	@Override
	public EAnimI getEnt() {
		return ent;
	}

	@Override
	public VBExporter getExp() {
		return exp;
	}

	@Override
	public boolean isBlank() {
		return blank;
	}

	@Override
	public void paint() {
		display();
	}

	@Override
	public void setEntity(EAnimI ieAnim) {
		ent = ieAnim;
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
			exp.update();
		}
	}

	protected void draw(FakeGraphics g) {
		int w = getWidth();
		int h = getHeight();
		g.translate(w / 2.0, h * 3 / 4.0);
		g.setColor(FakeGraphics.BLACK);
		if (ent != null)
			ent.draw(g, ctrl.ori.copy().times(-1), ctrl.siz);
	}

	@Override
	public BufferedImage getScreen() {
		return getScreen(false);
	}

	public BufferedImage getScreen(boolean transparent) {
		if (!Conf.white)
			return getScreen();
		int w = getWidth();
		int h = getHeight();

		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D gra = (Graphics2D) img.getGraphics();

		if (!transparent) {
			if(CommonStatic.getConfig().viewerColor != -1) {
				gra.setColor(new Color(CommonStatic.getConfig().viewerColor));
				gra.fillRect(0, 0, w, h);
			} else {
				gra.setColor(Color.GREEN);
				gra.fillRect(0, 0, w, h);
			}
		}

		gra.translate(w / 2.0, h * 3 / 4.0);
		gra.setColor(Color.BLACK);
		if (ent != null)
			ent.draw(new FG2D(gra), ctrl.ori.copy().times(-1), ctrl.siz);
		gra.dispose();
		return img;
	}
}
