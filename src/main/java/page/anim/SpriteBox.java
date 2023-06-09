package page.anim;

import common.util.anim.AnimCE;
import common.util.anim.ImgCut;
import page.Page;
import utilpc.Interpret;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

class SpriteBox extends JPanel implements KeyListener, MouseInputListener, MouseWheelListener {

	private static final long serialVersionUID = 1L;

	private AnimCE anim;
	private Point c;
	private int skip = 0;
	private boolean drag;
	private final Page page;
	protected int sele = -1;
	protected boolean white = true;

	protected double x = 0, y = 0;
	protected double size = 1.0;
	protected double initSize = 1.0;

	protected SpriteBox(Page p) { // TODO: figure out how to make this thing have a damn border
		page = p;
		this.setBorder(BorderFactory.createEtchedBorder());
		setIgnoreRepaint(true);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		ini();
	}

	@Override
	public synchronized void paint(Graphics g) {
		calculateSize(false);

		BufferedImage bimg = getImage();

		if (bimg == null)
			return;

		g.drawImage(bimg, 0, 0, null);
		g.dispose();
	}

	public synchronized void setAnim(AnimCE anim) {
		if (this.anim != anim) {
			this.anim = anim;

			calculateSize(true);

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
		gra.translate(-x , -y);
		if (anim != null) {
			BufferedImage spr = (BufferedImage) anim.getNum().bimg();
			int aw = spr.getWidth();
			int ah = spr.getHeight();
			int rw = (int) (size * aw);
			int rh = (int) (size * ah);

			int bw = rw % 20 != 0 ? 20 * (1 + rw / 20) : rw;
			int bh = rh % 20 != 0 ? 20 * (1 + rh / 20) : rh;

			gra.setColor(white ? Color.WHITE : new Color(64, 64, 64));
			gra.fillRect(0, 0, bw, bh);

			gra.setColor(Color.LIGHT_GRAY);
			for (int i = 0; i < rw; i += 20)
				for (int j = i / 20 % 2 * 20; j < rh; j += 40)
					gra.fillRect(i, j, 20, 20);

			gra.drawImage(spr, 0, 0, rw, rh, null);
			ImgCut ic = anim.imgcut;
			for (int i = 0; i < ic.n; i++) {
				int[] val = ic.cuts[i];
				int sx = (int) (val[0] * size - 1);
				int sy = (int) (val[1] * size - 1);
				int sw = (int) (val[2] * size + 2);
				int sh = (int) (val[3] * size + 2);
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

	protected boolean isAnimValid() {
		return anim != null;
	}

	private int findSprite(Point p) {
		if (anim == null)
			return -1;
		ImgCut ic = anim.imgcut;
		int ski = skip;
		int fir = -1;
		int sel = -1;
		for (int i = 0; i < ic.n; i++)
			if (inside(ic.cuts[i], p)) {
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

	private boolean inside(int[] data, Point p) {
		Point leftUpper = new Point((int) (data[0] * size), (int) (data[1] * size));
		Point rightLower = new Point( (int) ((data[0] + data[2]) * size), (int) ((data[1] + data[3]) * size));

		return p.x >= leftUpper.x && p.x <= rightLower.x && p.y <= rightLower.y && p.y >= leftUpper.y;
	}

	private Point getPoint(Point p) {
		return Interpret.getPoint(p, x, y, size);
	}

	private void limit() {
		if(anim == null) {
			x = 0;
			y = 0;
			return;
		}

		if(size <= initSize)
			size = initSize;

		int spriteW = (int) (anim.getNum().getWidth() * size);
		int spriteH = (int) (anim.getNum().getHeight() * size);

		if(x < 0)
			x = 0;

		if(y < 0)
			y = 0;

		if(getWidth() >= spriteW) {
			x = 0;
		} else if(x + getWidth() >= spriteW) {
			x = spriteW - getWidth();
		}

		if(getHeight() >= spriteH) {
			y = 0;
		} else if(y + getHeight() >= spriteH) {
			y = spriteH - getHeight();
		}
	}

	private void calculateSize(boolean first) {
		if(anim == null) {
			size = 1.0;
			return;
		}

		BufferedImage img = (BufferedImage) anim.getNum().bimg();

		int spriteW = img.getWidth();
		int spriteH = img.getHeight();

		int w = getWidth();
		int h = getHeight();

		initSize = Math.min(1.0 * w / spriteW, 1.0 * h / spriteH);

		if(first)
			size = initSize;
	}

	private void relativeScaling(Point p, double factor) {
		x = (int) ((x + p.x) * factor - p.x);
		y = (int) ((y + p.y) * factor - p.y);
	}

	private void ini() { // TODO: This has a side effect that it'll now respond on model and animation page as well. Seems like it works fine though... we'll see
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_TAB) {
			skip++;
			sele = findSprite(c);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		c = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point p = e.getPoint();
		if (drag && sele >= 0) {
			anim.ICedited();
			anim.unSave("imgcut drag");
		} else if(!drag) {
			skip = 0;
			c = p;
			Point p2 = new Point(p.x + (int) x, p.y + (int) y);
			sele = findSprite(p2);
			page.callBack(this);
		}

		if(drag)
			drag = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		if (!drag) {
			Point p2 = new Point(p.x + (int) x, p.y + (int) y);
			sele = findSprite(p2);
			page.callBack(this);
		}

		drag = true;
		Point p0 = getPoint(c);
		Point p1 = getPoint(c = p);

		if	(sele == -1 || e.isShiftDown()) {
			x -= (p1.x - p0.x) * size;
			y -= (p1.y - p0.y) * size;

			limit();
		} else {
			int[] line = anim.imgcut.cuts[sele];
			int modifier = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
			if ((e.getModifiers() & modifier) > 0) {
				line[2] += p1.x - p0.x;
				line[3] += p1.y - p0.y;
			} else {
				line[0] += p1.x - p0.x;
				line[1] += p1.y - p0.y;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double factor = Math.pow(0.95, e.getPreciseWheelRotation());
		size *= factor;

		relativeScaling(e.getPoint(), factor);
		limit();
	}

	public void setSprite(int id, boolean callback) {
		sele = id;
		if (callback)
			page.callBack(this);
	}
}
