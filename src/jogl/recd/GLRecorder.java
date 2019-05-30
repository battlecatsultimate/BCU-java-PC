package jogl.recd;

import jogl.awt.GLCstd;
import page.RetFunc;

public abstract class GLRecorder {

	public static GLRecorder getIns(GLCstd scr, String path, int type, RetFunc ob) {
		return new GLRecdBImg(scr, path, type, ob);
	}

	protected final GLCstd screen;

	protected GLRecorder(GLCstd scr) {
		screen = scr;
	}

	public abstract void end();

	public abstract void quit();

	public abstract int remain();

	public abstract void start();

	public abstract void update();

}
