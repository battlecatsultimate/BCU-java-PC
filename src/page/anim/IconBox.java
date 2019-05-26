package page.anim;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import page.view.ViewBox;
import page.view.ViewBoxDef;
import util.ImgCore;
import util.Res;
import util.system.P;
import util.system.fake.FakeGraphics;
import util.system.fake.FakeImage;
import util.system.fake.FakeTransform;

import static page.anim.IconBox.IBConf.*;

public interface IconBox extends ViewBox {

	static class IBConf {

		public static final int[] line = new int[4];
		public static int mode = 0, type = 0, glow = 0;

	}

	static class IBCtrl extends ViewBox.Controller {

		protected boolean ctrl, drag;

		@Override
		public synchronized void mouseDragged(MouseEvent e) {
			if (!drag) {
				super.mouseDragged(e);
				return;
			}
			Point t = e.getPoint();
			line[ctrl ? 2 : 0] += t.x - p.x;
			line[ctrl ? 3 : 1] += t.y - p.y;
			p = t;
		}

		@Override
		public synchronized void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			if (cont.isBlank() && !new P(p).out(line, 1, -5))
				drag = true;
		}

		@Override
		public synchronized void mouseReleased(MouseEvent e) {
			drag = false;
			super.mouseReleased(e);
		}

		protected synchronized void keyPressed(KeyEvent ke) {
			if (ke.getKeyCode() == KeyEvent.VK_CONTROL)
				ctrl = true;
		}

		protected synchronized void keyReleased(KeyEvent ke) {
			if (ke.getKeyCode() == KeyEvent.VK_CONTROL)
				ctrl = false;
		}

	}

	public void changeType();

	public BufferedImage getClip();

	@Override
	public IBCtrl getCtrl();

	public default void keyPressed(KeyEvent ke) {
		getCtrl().keyPressed(ke);
	}

	public default void keyReleased(KeyEvent ke) {
		getCtrl().keyReleased(ke);
	}

	public void setBlank(boolean selected);

}

class IconBoxDef extends ViewBoxDef implements IconBox {

	private static final long serialVersionUID = 1L;

	protected IconBoxDef() {
		super(new IBCtrl());
		setFocusable(true);
		glow = 0;
		changeType();
	}

	@Override
	public void changeType() {
		FakeImage bimg = Res.ico[mode][type].getImg();
		line[2] = bimg.getWidth();
		line[3] = bimg.getHeight();
	}

	@Override
	public synchronized void draw(FakeGraphics gra) {
		boolean b = ImgCore.ref;
		ImgCore.ref = false;
		if (blank) {
			if (mode == 0 && type > 1 || mode == 1) {
				FakeImage bimg = Res.ico[mode][type].getImg();
				int bw = bimg.getWidth();
				int bh = bimg.getHeight();
				double r = Math.min(1.0 * line[2] / bw, 1.0 * line[3] / bh);
				gra.drawImage(bimg, line[0], line[1], bw * r, bh * r);
				if (glow == 1) {
					gra.setComposite(FakeGraphics.BLEND, 256, -1);
					bimg = Res.ico[0][4].getImg();
					gra.drawImage(bimg, line[0], line[1], bw * r, bh * r);
					gra.setComposite(FakeGraphics.DEF);
				}
			}
		}
		FakeTransform at = gra.getTransform();
		super.draw(gra);
		gra.setTransform(at);
		ImgCore.ref = b;
		if (blank) {
			int t = mode == 0 ? type == 1 ? 1 : 0 : 3;
			FakeImage bimg = Res.ico[mode][t].getImg();
			int bw = bimg.getWidth();
			int bh = bimg.getHeight();
			double r = Math.min(1.0 * line[2] / bw, 1.0 * line[3] / bh);
			gra.setColor(FakeGraphics.BLACK);
			gra.drawRect(line[0] - 1, line[1] - 1, line[2] + 1, line[3] + 1);
			if (glow == 1) {
				gra.setComposite(FakeGraphics.BLEND, 256, 1);
				bimg = Res.ico[0][4].getImg();
				gra.drawImage(bimg, line[0], line[1], (int) (bw * r), (int) (bh * r));
				gra.setComposite(FakeGraphics.DEF);
			}
			if (mode == 0 && type > 1) {
				bimg = Res.ico[0][5].getImg();
				gra.drawImage(bimg, line[0], line[1], (int) (bw * r), (int) (bh * r));
			} else {
				bimg = Res.ico[mode][t].getImg();
				gra.drawImage(bimg, line[0], line[1], (int) (bw * r), (int) (bh * r));
			}

		}
	}

	@Override
	public BufferedImage getClip() {
		FakeImage bimg = Res.ico[mode][type].getImg();
		int bw = bimg.getWidth();
		int bh = bimg.getHeight();
		double r = Math.min(1.0 * line[2] / bw, 1.0 * line[3] / bh);
		BufferedImage clip = prev.getSubimage(line[0], line[1], (int) (bw * r), (int) (bh * r));
		BufferedImage ans = new BufferedImage(bw, bh, BufferedImage.TYPE_3BYTE_BGR);
		ans.getGraphics().drawImage(clip, 0, 0, bw, bh, null);
		return ans;
	}

	@Override
	public IBCtrl getCtrl() {
		return (IBCtrl) ctrl;
	}

	@Override
	public void setBlank(boolean selected) {
		blank = selected;
	}

}
