package jogl.awt;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import jogl.util.GLGraphics;
import page.battle.BBCtrl;
import page.battle.BattleBox;
import util.basis.BattleField;
import util.basis.SBCtrl;

public class GLBattleBox extends GLCstd implements BattleBox, GLEventListener {

	private static final long serialVersionUID = 1L;

	protected final BBPainter bbp;

	public GLBattleBox(OuterBox bip, BattleField bf, int type) {
		bbp = type == 0 ? new BBPainter(bip, bf, this) : new BBCtrl(bip, (SBCtrl) bf, this);
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
	public BBPainter getPainter() {
		return bbp;
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
