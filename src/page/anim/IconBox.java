package page.anim;

import static page.anim.IconBox.IBConf.*;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import page.view.ViewBox;
import util.system.P;

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