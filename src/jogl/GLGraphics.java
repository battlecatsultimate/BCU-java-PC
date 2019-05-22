package jogl;

import java.awt.Color;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;

import util.system.fake.FakeGraphics;
import util.system.fake.FakeImage;
import util.system.fake.FakeTransform;

public class GLGraphics implements FakeGraphics {

	private final GL2 g;
	private final int w, h;

	private int color = -1;

	public GLGraphics(GL2 gl2, int wid, int hei) {
		g = gl2;
		w = wid;
		h = hei;
	}

	@Override
	public void colRect(int x, int y, int w, int h, int r, int gr, int b, int... a) {
		g.glBegin(GL2ES3.GL_QUADS);
		if (a.length == 0)
			setColor(r, gr, b);
		else
			g.glColor4f(r / 256f, gr / 256f, b / 256f, a[0] / 256f);
		addP(x, y);
		addP(x + w, y);
		addP(x + w, y + h);
		addP(x, y + h);
		g.glEnd();

	}

	@Override
	public void drawImage(FakeImage bimg, int i, int j) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawImage(FakeImage bimg, int ix, int iy, int iw, int ih) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawLine(int i, int j, int x, int y) {
		g.glBegin(GL.GL_LINES);
		setColor();
		addP(i, j);
		addP(x, y);
		g.glEnd();

	}

	@Override
	public void drawOval(int i, int j, int k, int l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawRect(int x, int y, int w, int h) {
		g.glBegin(GL.GL_LINE_LOOP);
		setColor();
		addP(x, y);
		addP(x + w, y);
		addP(x + w, y + h);
		addP(x, y + h);
		g.glEnd();
	}

	@Override
	public void fillOval(int i, int j, int k, int l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillRect(int x, int y, int w, int h) {
		g.glBegin(GL2ES3.GL_QUADS);
		setColor();
		addP(x, y);
		addP(x + w, y);
		addP(x + w, y + h);
		addP(x, y + h);
		g.glEnd();
	}

	@Override
	public FakeTransform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void gradRect(int x, int y, int w, int h, int a, int b, int[] c, int d, int e, int[] f) {
		g.glBegin(GL2ES3.GL_QUADS);
		setColor(c[0], c[1], c[2]);
		addP(x, y);
		addP(x + w, y);
		addP(x + w, y + h);
		addP(x, y + h);
		g.glEnd();
		// TODO
	}

	@Override
	public void rotate(double d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scale(int hf, int vf) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColor(int c) {
		if (c == RED)
			color = Color.RED.getRGB();
		if (c == YELLOW)
			color = Color.YELLOW.getRGB();
		if (c == BLACK)
			color = Color.BLACK.getRGB();
		if (c == MAGENTA)
			color = Color.MAGENTA.getRGB();
		if (c == BLUE)
			color = Color.BLUE.getRGB();
		if (c == CYAN)
			color = Color.CYAN.getRGB();
	}

	@Override
	public void setComposite(int mode, int... para) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRenderingHint(int key, int object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTransform(FakeTransform at) {
		// TODO Auto-generated method stub

	}

	@Override
	public void translate(double x, double y) {
		// TODO Auto-generated method stub

	}

	private void addP(int x, int y) {
		g.glVertex2f(2.0f * x / w - 1, 1 - 2.0f * y / h);
	}

	private void setColor() {
		if (color == -1)
			return;
		setColor(color >> 16 & 8, color >> 8 & 8, color & 8);
	}

	private void setColor(int r, int gr, int b) {
		g.glColor3f(r / 256f, gr / 256f, b / 256f);
	}

}
