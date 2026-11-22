package utilpc;

import common.CommonStatic;
import main.MainBCU;
import page.Page;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * custom menu background (Nimbus theme only), read from the user folder (user/background.png).
 * nothing is read or kept in memory unless the option is enabled.
 */
public class BGImage {

	// the image is shrunk to this size at most when it is loaded, to save memory
	private static final int MAX_SIDE = 2560;

	private static BufferedImage source, cache;
	private static boolean tried = false;

	// Releases the loaded image, it is read again the next time it is needed
	public static synchronized void reset() {
		source = null;
		cache = null;
		tried = false;
	}

	// Draws the background behind a page, aligned to the page that fills the window, scaled to cover it
	public static void paint(Graphics g, Page page) {
		if (!MainBCU.nimbus || !MainBCU.customBG)
			return;

		// the topmost page, also through scroll panes and other containers, so tables inside scroll panes use the window's image
		Container root = page;

		for (Container c = page.getParent(); c != null; c = c.getParent()) {
			if (c instanceof Page)
				root = c;
			// the image stays fixed to the window while the page scrolls, so scrolling can't just move the drawn pixels
			if (c instanceof JViewport && ((JViewport) c).getScrollMode() != JViewport.SIMPLE_SCROLL_MODE)
				((JViewport) c).setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		}
		int w = root.getWidth();
		int h = root.getHeight();

		if (w <= 0 || h <= 0)
			return;

		BufferedImage img = get(w, h);

		if (img == null)
			return;

		Point p = SwingUtilities.convertPoint(page, 0, 0, root);
		g.drawImage(img, -p.x, -p.y, null);
	}

	private static synchronized BufferedImage get(int w, int h) {
		if (!tried)
			load();

		if (source == null)
			return null;

		if (cache == null || cache.getWidth() != w || cache.getHeight() != h) {
			int sw = source.getWidth();
			int sh = source.getHeight();
			double s = Math.max((double) w / sw, (double) h / sh);
			int cw = Math.min(sw, (int) Math.ceil(w / s));
			int ch = Math.min(sh, (int) Math.ceil(h / s));

			cache = scale(source.getSubimage((sw - cw) / 2, (sh - ch) / 2, cw, ch), w, h);
		}

		return cache;
	}

	private static void load() {
		tried = true;

		try {
			File f = CommonStatic.ctx.getUserFile("background.png");

			if (f == null || !f.isFile())
				return;

			BufferedImage img = ImageIO.read(f);

			if (img == null)
				return;

			double s = Math.min(1.0, (double) MAX_SIDE / Math.max(img.getWidth(), img.getHeight()));

			source = scale(img, Math.max(1, (int) Math.round(img.getWidth() * s)), Math.max(1, (int) Math.round(img.getHeight() * s)));
		} catch (Exception | OutOfMemoryError e) {
			source = null;
		}
	}

	// Scales in steps of at most half, seems most efficient i think
	private static BufferedImage scale(BufferedImage src, int w, int h) {
		BufferedImage cur = src;
		while (cur.getWidth() > 2 * w || cur.getHeight() > 2 * h)
			cur = step(cur, Math.max(w, cur.getWidth() / 2), Math.max(h, cur.getHeight() / 2));
		return step(cur, w, h);
	}

	private static BufferedImage step(BufferedImage src, int w, int h) {
		BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = out.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(src, 0, 0, w, h, null);
		g.dispose();
		return out;
	}
}
