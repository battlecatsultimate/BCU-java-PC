package jogl.awt;

import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

import jogl.recd.GLRecdBImg;
import page.JTG;
import page.view.ViewBox;
import page.view.ViewBox.Loader;

class GLVBExporter implements ViewBox.VBExporter {

	private final GLViewBox vb;

	private Loader loader;
	private GLRecdBImg glr;

	protected GLVBExporter(GLViewBox box) {
		vb = box;
	}

	@Override
	public void end(JTG btn) {
		if (loader == null)
			return;
		loader.finish(btn);
		glr.end();
		loader = null;
		glr = null;
	}

	@Override
	public BufferedImage getPrev() {
		return vb.getScreen();
	}

	@Override
	public Loader start() {
		if (loader != null)
			return loader;
		Queue<BufferedImage> qb = new ArrayDeque<>();
		loader = new Loader(qb);
		glr = new GLRecdBImg(vb, qb, loader.thr);
		loader.start();
		return loader;
	}

	protected void update() {
		if (glr != null)
			glr.update();
	}

}
