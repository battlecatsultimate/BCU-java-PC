package jogl;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public abstract class StdGLC implements GLEventListener {

	protected int x, y, w, h;

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		drawFake(new GLGraphics(drawable.getGL().getGL2(), w, h));
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void init(GLAutoDrawable drawable) {
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int xp, int yp, int width, int height) {
		x = xp;
		y = yp;
		w = width;
		h = height;
	}

	protected abstract void drawFake(GLGraphics fg);

}
