package jogl;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import jogl.util.ResManager;

import java.awt.*;
import java.awt.image.BufferedImage;

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
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

}
