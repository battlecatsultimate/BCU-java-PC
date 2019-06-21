package page.anim;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import common.util.anim.AnimC;
import common.util.anim.ImgCut;
import page.Page;
import utilpc.PP;

class SpriteBox extends Canvas {

	private static final long serialVersionUID = 1L;

	private AnimC anim;
	private Point c;
	private int skip = 0;
	private boolean ctrl, drag;
	private Page page;
	protected int sele = -1;

	protected SpriteBox(Page p) {
		page = p;
		setIgnoreRepaint(true);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	@Override
	public synchronized void paint(Graphics g) {
		BufferedImage bimg = getImage();
		if (bimg == null)
			return;
		g.drawImage(bimg, 0, 0, null);
		g.dispose();
	}

	public synchronized void setAnim(AnimC anim) {
		if (this.anim != anim) {
			this.anim = anim;
			sele = -1;
		} else if (anim != null && sele >= anim.imgcut.n)
			sele = -1;
	}

	protected synchronized BufferedImage getImage() {
		int w = getWidth();
		int h = getHeight();
		BufferedImage img = (BufferedImage) createImage(w, h);
		if (img == null)
			return null;
		Graphics2D gra = (Graphics2D) img.getGraphics();
		if (anim != null) {
			BufferedImage spr = (BufferedImage) anim.num.bimg();
			int aw = spr.getWidth();
			int ah = spr.getHeight();
			double r = Math.min(1.0 * w / aw, 1.0 * h / ah);
			int rw = (int) (r * aw);
			int rh = (int) (r * ah);

			gra.setColor(Color.LIGHT_GRAY);
			for (int i = 0; i < rw; i += 20)
				for (int j = i / 20 % 2 * 20; j < rh; j += 40)
					gra.fillRect(i, j, 20, 20);

			gra.drawImage(spr, 0, 0, rw, rh, null);
			ImgCut ic = anim.imgcut;
			for (int i = 0; i < ic.n; i++) {
				int[] val = ic.cuts[i];
				int sx = (int) (val[0] * r - 1);
				int sy = (int) (val[1] * r - 1);
				int sw = (int) (val[2] * r + 2);
				int sh = (int) (val[3] * r + 2);
				if (i == sele) {
					gra.setColor(Color.RED);
					gra.fillRect(sx - 5, sy - 5, sw + 5, 5);
					gra.fillRect(sx - 5, sy, 5, sh + 5);
					gra.fillRect(sx + sw, sy - 5, 5, sh + 5);
					gra.fillRect(sx, sy + sh, sw + 5, 5);
				} else {
					gra.setColor(Color.BLACK);
					gra.drawRect(sx, sy, sw, sh);
				}

			}
		}
		gra.dispose();
		return img;
	}

	protected synchronized void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_CONTROL)
			ctrl = true;
	}

	protected synchronized void keyReleased(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_CONTROL)
			ctrl = false;
	}

	protected synchronized void keyTyped(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_TAB) {
			skip++;
			sele = findSprite(c);
		}
	}

	protected synchronized void mouseDragged(Point p) {
		if (sele == -1)
			return;
		drag = true;
		Point p0 = getPoint(c);
		Point p1 = getPoint(c = p);
		int[] line = anim.imgcut.cuts[sele];
		line[ctrl ? 2 : 0] += p1.x - p0.x;
		line[ctrl ? 3 : 1] += p1.y - p0.y;
	}

	protected synchronized void mousePressed(Point p) {
		c = p;
	}

	protected synchronized void mouseReleased(Point p) {
		if (drag && sele >= 0) {
			anim.ICedited();
			anim.unSave("imgcut drag");
			drag = false;
		} else {
			skip = 0;
			sele = findSprite(c = p);
			page.callBack(null);
		}
	}

	private int findSprite(Point p) {
		if (anim == null)
			return -1;
		int w = getWidth();
		int h = getHeight();
		BufferedImage spr = (BufferedImage) anim.num.bimg();
		int aw = spr.getWidth();
		int ah = spr.getHeight();
		double r = Math.min(1.0 * w / aw, 1.0 * h / ah);
		ImgCut ic = anim.imgcut;
		int ski = skip;
		int fir = -1;
		int sel = -1;
		for (int i = 0; i < ic.n; i++)
			if (!new PP(p).out(ic.cuts[i], r, -5)) {
				if (fir == -1)
					fir = i;
				if (ski == 0)
					sel = i;
				else
					ski--;
			}
		if (fir >= 0 && sel == -1) {
			skip = 0;
			sel = fir;
		}
		return sel;
	}

	private Point getPoint(Point p) {
		int w = getWidth();
		int h = getHeight();
		BufferedImage spr = (BufferedImage) anim.num.bimg();
		int aw = spr.getWidth();
		int ah = spr.getHeight();
		double r = Math.min(1.0 * w / aw, 1.0 * h / ah);
		return new Point((int) (p.x / r), (int) (p.y / r));
	}

}
