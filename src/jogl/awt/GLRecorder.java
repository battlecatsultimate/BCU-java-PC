package jogl.awt;

import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

import page.awt.RecdThread;
import page.battle.BattleBox;

class GLBImg extends GLRecorder {

	private final Queue<BufferedImage> qb = new ArrayDeque<BufferedImage>();
	private final RecdThread th;

	protected GLBImg(GLCstd scr, String path, int type) {
		super(scr);
		th = RecdThread.getIns(null, qb, path, type);
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

}

abstract class GLRecorder {

	protected static GLRecorder getIns(String path, int type, BattleBox.OuterBox ob) {
		return null;
	}

	protected final GLCstd screen;

	protected GLRecorder(GLCstd scr) {
		screen = scr;
	}

	protected abstract void end();

	protected abstract void quit();

	protected abstract int remain();

	protected abstract void update();

}
