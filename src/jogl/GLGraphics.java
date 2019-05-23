package jogl;

import static com.jogamp.opengl.GL.*;

import java.awt.Color;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

import util.system.fake.FakeGraphics;
import util.system.fake.FakeImage;
import util.system.fake.FakeTransform;

public class GLGraphics implements FakeGraphics {

	static class GLT implements FakeTransform {

		private float[] data = new float[16];

		@Override
		public Object getAT() {
			return null;
		}

	}

	private static final int PURE = 0, IMG = 1;
	private final GL2 g;
	private final TextureManager tm;

	private final int sw, sh;
	private int mode = PURE;
	private int bind = 0;

	private int color = -1;

	public GLGraphics(GL2 gl2, int wid, int hei) {
		g = gl2;
		tm = TextureManager.get(g);
		sw = wid;
		sh = hei;
		g.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		g.glLoadIdentity();
	}

	@Override
	public void colRect(int x, int y, int w, int h, int r, int gr, int b, int... a) {
		checkMode(PURE);
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
	public void drawImage(FakeImage bimg, int x, int y) {
		checkMode(IMG);
		GLImage gl = (GLImage) bimg.gl();
		bind(tm.load(this, gl));
		g.glBegin(GL2ES3.GL_QUADS);
		int w = gl.getWidth();
		int h = gl.getHeight();
		float[] r = gl.getRect();
		g.glTexCoord2f(r[0], r[1]);
		addP(x, y);
		g.glTexCoord2f(r[0] + r[2], r[1]);
		addP(x + w, y);
		g.glTexCoord2f(r[0] + r[2], r[1] + r[3]);
		addP(x + w, y + h);
		g.glTexCoord2f(r[0], r[1] + r[3]);
		addP(x, y + h);
		g.glEnd();

	}

	@Override
	public void drawImage(FakeImage bimg, int x, int y, int w, int h) {
		checkMode(IMG);
		GLImage gl = (GLImage) bimg.gl();
		bind(tm.load(this, gl));
		g.glBegin(GL2ES3.GL_QUADS);
		float[] r = gl.getRect();
		g.glTexCoord2f(r[0], r[1]);
		addP(x, y);
		g.glTexCoord2f(r[0] + r[2], r[1]);
		addP(x + w, y);
		g.glTexCoord2f(r[0] + r[2], r[1] + r[3]);
		addP(x + w, y + h);
		g.glTexCoord2f(r[0], r[1] + r[3]);
		addP(x, y + h);
		g.glEnd();
	}

	@Override
	public void drawLine(int i, int j, int x, int y) {
		checkMode(PURE);
		g.glBegin(GL.GL_LINES);
		setColor();
		addP(i, j);
		addP(x, y);
		g.glEnd();

	}

	@Override
	public void drawOval(int i, int j, int k, int l) {
		checkMode(PURE);

		// TODO

	}

	@Override
	public void drawRect(int x, int y, int w, int h) {
		checkMode(PURE);
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
		checkMode(PURE);
		// TODO Auto-generated method stub

	}

	@Override
	public void fillRect(int x, int y, int w, int h) {
		checkMode(PURE);
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
		GLT glt = new GLT();
		g.glGetFloatv(GLMatrixFunc.GL_MODELVIEW_MATRIX, glt.data, 0);
		return glt;
	}

	@Override
	public void gradRect(int x, int y, int w, int h, int a, int b, int[] c, int d, int e, int[] f) {
		checkMode(PURE);
		g.glBegin(GL2ES3.GL_QUADS);
		setColor(c[0], c[1], c[2]);
		addP(x, y);
		addP(x, y + h);
		setColor(f[0], f[1], f[2]);
		addP(x + w, y + h);
		addP(x + w, y);
		g.glEnd();
		// TODO
	}

	@Override
	public void rotate(double d) {
		g.glRotated(d, 0, 0, 1);
	}

	@Override
	public void scale(int hf, int vf) {
		g.glScalef(hf, vf, 0);
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
		GLT glt = (GLT) at;
		g.glMatrixMode(GLMatrixFunc.GL_MODELVIEW_MATRIX);
		g.glLoadMatrixf(glt.data, 0);
	}

	@Override
	public void translate(double x, double y) {
		g.glTranslated(x / sw, -y / sh, 0);
	}

	protected void bind(int id) {
		if (bind == id)
			return;
		g.glBindTexture(GL_TEXTURE_2D, id);
		bind = id;
	}

	protected void flush() {
		checkMode(PURE);
	}

	private void addP(int x, int y) {
		g.glVertex2f(2.0f * x / sw - 1, 1 - 2.0f * y / sh);
	}

	private void checkMode(int i) {
		if (mode == i)
			return;
		if (mode == IMG)
			g.glDisable(GL_TEXTURE_2D);
		mode = i;
		if (mode == IMG)
			g.glEnable(GL_TEXTURE_2D);
	}

	private void setColor() {
		if (color == -1)
			return;
		setColor(color >> 16 & 8, color >> 8 & 8, color & 8);
	}

	private void setColor(int r, int gr, int b) {

		// TODO g.glColor3f(r / 256f, gr / 256f, b / 256f);
	}

}
