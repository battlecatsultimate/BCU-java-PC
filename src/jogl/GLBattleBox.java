package jogl;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

import jogl.util.GLGraphics;
import jogl.util.ResManager;
import page.battle.BBCtrl;
import page.battle.BattleBox;
import util.basis.BattleField;
import util.basis.SBCtrl;

public class GLBattleBox extends GLCanvas implements BattleBox, GLEventListener {

	private static final long serialVersionUID = 1L;

	private final BBPainter bbp;

	public GLBattleBox(OuterBox bip, BattleField bf, int type) {
		super(GLStatic.GLC);
		bbp = type == 0 ? new BBPainter(bip, bf, this) : new BBCtrl(bip, (SBCtrl) bf, this);
		addGLEventListener(this);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLGraphics g = new GLGraphics(drawable.getGL().getGL2(), getWidth(), getHeight());
		bbp.draw(g);
		g.dispose();
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		ResManager.get(drawable.getGL().getGL2()).dispose();
	}

	@Override
	public BBPainter getPainter() {
		return bbp;
	}

	@Override
	public void init(GLAutoDrawable drawable) {
	}

	@Override
	public void paint() {
		display();
	}

	@Override
	public void reset() {
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		bbp.reset();
	}

}
