package jogl;

import com.jogamp.newt.Window;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.awt.AWTMouseAdapter;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import jogl.util.ResManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GLCstd extends GLJPanel implements GLEventListener {

	private static final long serialVersionUID = 1L;

	protected GLCstd() {
		super(GLStatic.GLC);
		addGLEventListener(this);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		ResManager.get(drawable.getGL().getGL2()).dispose();
	}

	public BufferedImage getScreen() {
		try {
			Rectangle r = getBounds();
			Point p = getLocationOnScreen();
			r.x = p.x;
			r.y = p.y;
			return new Robot().createScreenCapture(r);
		} catch (AWTException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		Object parent = drawable.getUpstreamWidget();

		if (parent instanceof com.jogamp.newt.Window) {
			((Window) parent).addMouseListener(new GLMouseEventListener());
		} else if (parent instanceof Component) {
			new AWTMouseAdapter(new GLMouseEventListener(), drawable).addTo((Component) parent);
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	}

	private static class GLMouseEventListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}

		@Override
		public void mouseDragged(MouseEvent e) {
		}

		@Override
		public void mouseWheelMoved(MouseEvent e) {
		}
	}
}
