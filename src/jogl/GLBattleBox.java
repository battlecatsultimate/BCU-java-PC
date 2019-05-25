package jogl;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

import page.battle.BBCtrl;
import page.battle.BattleBox;
import page.battle.BattleInfoPage;
import util.basis.BattleField;
import util.basis.SBCtrl;

public class GLBattleBox extends GLCanvas implements BattleBox, GLEventListener {

	private static final long serialVersionUID = 1L;

	private final BBPainter bbp;

	public GLBattleBox(BattleInfoPage bip, BattleField bf, int type) {
		bbp = type == 0 ? new BBPainter(bip, bf, this) : new BBCtrl(bip, (SBCtrl) bf, this);
		addGLEventListener(this);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		bbp.draw(new GLGraphics(drawable.getGL().getGL2(), getWidth(), getHeight()));
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
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
