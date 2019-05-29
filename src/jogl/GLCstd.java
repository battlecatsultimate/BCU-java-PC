package jogl;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

import jogl.util.ResManager;

public abstract class GLCstd extends GLCanvas implements GLEventListener {

	private static final long serialVersionUID = 1L;

	protected GLCstd() {
		super(GLStatic.GLC);
		addGLEventListener(this);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		ResManager.get(drawable.getGL().getGL2()).dispose();
	}

	@Override
	public void init(GLAutoDrawable drawable) {
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	}

}
