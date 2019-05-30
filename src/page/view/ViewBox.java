package page.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Queue;

import page.JTG;
import page.RetFunc;
import page.awt.RecdThread;
import util.anim.EAnimI;
import util.system.P;

public interface ViewBox {

	static class Conf {

		public static boolean white;

	}

	static class Controller {

		public final P ori = new P(0, 0);
		public double siz = 0.5;
		protected Point p = null;
		protected ViewBox cont;

		public synchronized void mouseDragged(MouseEvent e) {
			if (p == null)
				return;
			ori.x += p.x - e.getX();
			ori.y += p.y - e.getY();
			p = e.getPoint();
		}

		public synchronized void mousePressed(MouseEvent e) {
			p = e.getPoint();
		}

		public synchronized void mouseReleased(MouseEvent e) {
			p = null;
		}

		public void resize(double pow) {
			siz *= pow;
		}

		public void setCont(ViewBox vb) {
			cont = vb;
		}

	}

	static class Loader implements RetFunc {

		public final RecdThread thr;

		private JTG jtb;

		public Loader(Queue<BufferedImage> list) {
			thr = RecdThread.getIns(this, list, null, RecdThread.GIF);
		}

		@Override
		public void callBack(Object o) {
			jtb.setEnabled(true);
		}

		public void finish(JTG btn) {
			jtb = btn;
			jtb.setEnabled(false);
		}

		public String getProg() {
			return "remain: " + thr.remain();
		}

		public void start() {
			thr.start();
		}

	}

	static interface VBExporter {

		public void end(JTG btn);

		public BufferedImage getPrev();

		public Loader start();

	}

	public static final Color c0 = new Color(70, 140, 160), c1 = new Color(85, 185, 205);

	public default void end(JTG btn) {
		if (getExp() != null)
			getExp().end(btn);
	}

	public Controller getCtrl();

	public EAnimI getEnt();

	public VBExporter getExp();

	public default BufferedImage getPrev() {
		if (getExp() != null)
			return getExp().getPrev();
		return null;
	}

	public boolean isBlank();

	public default void mouseDragged(MouseEvent e) {
		getCtrl().mouseDragged(e);
	}

	public default void mousePressed(MouseEvent e) {
		getCtrl().mousePressed(e);
	}

	public default void mouseReleased(MouseEvent e) {
		getCtrl().mouseReleased(e);
	}

	public void paint();

	public default void resize(double pow) {
		getCtrl().resize(pow);
	}

	public void setEntity(EAnimI ieAnim);

	public default Loader start() {
		if (getExp() != null)
			return getExp().start();
		return null;
	}

	public void update();
}