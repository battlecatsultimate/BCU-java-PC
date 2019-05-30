package jogl.awt;

import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

import page.RetFunc;
import page.awt.RecdThread;

class GLBImg extends GLRecorder {

	private final Queue<BufferedImage> qb;
	protected final RecdThread th;

	protected GLBImg(GLCstd scr, Queue<BufferedImage> lb, RecdThread rt) {
		super(scr);
		qb = lb;
		th = rt;
	}

	protected GLBImg(GLCstd scr, String path, int type, RetFunc ob) {
		super(scr);
		qb = new ArrayDeque<BufferedImage>();
		th = RecdThread.getIns(ob, qb, path, type);
	}

	@Override
	public void end() {
		synchronized (th) {
			th.end = true;
		}
	}

	@Override
	public void quit() {
		synchronized (th) {
			th.quit = true;
		}
	}

	@Override
	public int remain() {
		int size;
		synchronized (qb) {
			size = qb.size();
		}
		return size;
	}

	@Override
	protected void update() {
		synchronized (qb) {
			qb.add(screen.getScreen());
		}
	}

	@Override
	protected void start() {
		th.start();
	}

}

abstract class GLRecorder {

	protected static GLRecorder getIns(GLCstd scr, String path, int type, RetFunc ob) {
		return new GLBImg(scr, path, type, ob);
	}

	protected final GLCstd screen;

	protected GLRecorder(GLCstd scr) {
		screen = scr;
	}

	protected abstract void end();

	protected abstract void quit();

	protected abstract int remain();

	protected abstract void update();

	protected abstract void start();

}
