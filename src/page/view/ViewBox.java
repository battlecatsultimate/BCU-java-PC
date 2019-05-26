package page.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import io.Writer;
import page.JTG;
import res.AnimatedGifEncoder;
import util.anim.EAnimI;
import util.system.P;
import util.system.fake.FakeGraphics;

public interface ViewBox {

	static class Conf {

		public static boolean white;

	}

	static class Controller {

		protected final P ori = new P(0, 0);
		protected Point p = null;
		protected double siz = 0.5;
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

		protected void setCont(ViewBox vb) {
			cont = vb;
		}

	}

	static interface Loader {

		public void finish(JTG btn);

		public String getProg();

		public void start();

	}

	static interface VBExporter {

		public void end(JTG btn);

		public BufferedImage getPrev();

		public Loader start();

	}

	public static final Color c0 = new Color(70, 140, 160), c1 = new Color(85, 185, 205);

	public void draw(FakeGraphics gra);

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

	public void setEnt(EAnimI ent);

	public void setEntity(EAnimI ieAnim);

	public default Loader start() {
		if (getExp() != null)
			return getExp().start();
		return null;
	}

	public void update();
}

class LoaderDef extends Thread implements ViewBox.Loader {

	private static final int SLE = 33;

	private final AnimatedGifEncoder gif;
	private final List<BufferedImage> lbimg;

	private int index = 0;
	private boolean finish;
	private JTG jtb;

	protected LoaderDef(List<BufferedImage> list) {
		lbimg = list;
		gif = new AnimatedGifEncoder();
		gif.setDelay(33);
		Writer.writeGIF(gif);
	}

	@Override
	public void finish(JTG btn) {
		finish = true;
		jtb = btn;
		jtb.setEnabled(false);
	}

	@Override
	public String getProg() {
		return index + "/" + lbimg.size();
	}

	@Override
	public void run() {
		while (!finish) {
			write();
			sleeper();
		}
		write();
		gif.finish();
		jtb.setEnabled(true);
	}

	private void sleeper() {
		try {
			sleep(SLE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void write() {
		while (index < lbimg.size()) {
			gif.addFrame(lbimg.get(index));
			index++;
			sleeper();
		}
	}

}
