package jogl;

import static page.anim.IconBox.IBConf.glow;
import static page.anim.IconBox.IBConf.line;
import static page.anim.IconBox.IBConf.mode;
import static page.anim.IconBox.IBConf.type;

import java.awt.image.BufferedImage;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import jogl.util.GLGraphics;
import page.anim.IconBox;
import page.view.ViewBox;
import util.Res;
import util.anim.EAnimI;
import util.system.fake.FakeImage;

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
		FakeImage bimg = Res.ico[mode][type].getImg();
		line[2] = bimg.getWidth();
		line[3] = bimg.getHeight();
	}

	@Override
	public BufferedImage getClip() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBCtrl getCtrl() {
		return (IBCtrl) ctrl;
	}

	@Override
	public void setBlank(boolean selected) {
		blank = selected;
	}

}

class GLViewBox extends GLCstd implements ViewBox, GLEventListener {

	private static final long serialVersionUID = 1L;

	protected final Controller ctrl;

	protected boolean blank;

	private EAnimI ent;

	protected GLViewBox(Controller c) {
		ctrl = c;
		c.setCont(this);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLGraphics g = new GLGraphics(drawable.getGL().getGL2(), getWidth(), getHeight());
		int w = getWidth();
		int h = getHeight();
		if (!blank) {
			int[] c = new int[] { c0.getRed(), c0.getGreen(), c0.getBlue() };
			int[] f = new int[] { c1.getRed(), c1.getGreen(), c1.getBlue() };
			g.gradRect(0, 0, w, h / 2, w / 2, 0, c, w / 2, h / 2, f);
			g.gradRect(0, h / 2, w, h / 2, w / 2, h / 2, f, w / 2, h, c);
		}
		g.translate(w / 2, h * 3 / 4);
		if (ent != null)
			ent.draw(g, ctrl.ori.copy().times(-1), ctrl.siz);
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
		// TODO! Auto-generated method stub
		return null;
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
		if (ent != null)
			ent.update(true);
	}

}
