package page.anim;

import static page.anim.IconBox.IBConf.*;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import common.util.Res;
import common.util.system.fake.FakeGraphics;
import common.util.system.fake.FakeImage;
import page.view.ViewBox;
import utilpc.PP;

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
			if (cont.isBlank() && !new PP(p).out(line, 1, -5))
				drag = true;
		}

		@Override
		public synchronized void mouseReleased(MouseEvent e) {
			drag = false;
			super.mouseReleased(e);
		}

		public void postdraw(FakeGraphics gra) {
			if (cont.isBlank()) {
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

		public void predraw(FakeGraphics gra) {
			if (cont.isBlank()) {
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