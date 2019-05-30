package jogl.awt;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import jogl.util.GLGraphics;
import page.view.ViewBox;
import util.anim.EAnimI;
import util.system.fake.FakeGraphics;

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
			int[] c = new int[] { c0.getRed(), c0.getGreen(), c0.getBlue() };
			int[] f = new int[] { c1.getRed(), c1.getGreen(), c1.getBlue() };
			g.gradRect(0, 0, w, h / 2, w / 2, 0, c, w / 2, h / 2, f);
			g.gradRect(0, h / 2, w, h / 2, w / 2, h / 2, f, w / 2, h, c);
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
			ent.update(true);
			exp.update();
		}
	}

	protected void draw(FakeGraphics g) {
		int w = getWidth();
		int h = getHeight();
		g.translate(w / 2, h * 3 / 4);
		g.setColor(FakeGraphics.BLACK);
		if (ent != null)
			ent.draw(g, ctrl.ori.copy().times(-1), ctrl.siz);
	}

}
