package jogl.awt;

import java.awt.image.BufferedImage;

import page.JTG;
import page.view.ViewBox;
import page.view.ViewBox.Loader;

class GLVBExporter implements ViewBox.VBExporter {

	private final GLViewBox vb;

	protected GLVBExporter(GLViewBox box) {
		vb = box;
	}

	@Override
	public void end(JTG btn) {
		/*
		 * if (loader == null) return; loader.finish(btn); loader = null;
		 */
	}

	@Override
	public BufferedImage getPrev() {
		return vb.getScreen();
	}

	@Override
	public Loader start() {
		/*
		 * if (loader != null) return loader; return loader = new GLLoader(vb);
		 */
		return null;
	}

	protected void update() {
		/*
		 * if (loader != null) loader.update();
		 */
	}

}