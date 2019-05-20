package page.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import io.Writer;
import main.Timer;
import page.JTG;
import res.AnimatedGifEncoder;
import util.ImgCore;
import util.anim.EAnimI;
import util.system.P;
import util.system.fake.FG2D;
import util.system.fake.FakeGraphics;

public class ViewBox extends Canvas {

	public static boolean white = false;

	private static final long serialVersionUID = 1L;
	private static final Color c0 = new Color(70, 140, 160), c1 = new Color(85, 185, 205);

	public BufferedImage prev = null;
	public boolean blank;

	protected final P ori = new P(0, 0);
	protected Point p = null;
	protected double siz = 0.5;
	protected EAnimI ent;

	private List<BufferedImage> lbimg = null;
	private Loader loader = null;

	protected ViewBox() {
		setIgnoreRepaint(true);
	}

	@Override
	public synchronized void paint(Graphics g) {
		prev = getImage();
		if (prev == null)
			return;
		int w = getWidth();
		int h = getHeight();
		if (white) {
			BufferedImage img = (BufferedImage) createImage(w, h);
			Graphics2D gra = (Graphics2D) img.getGraphics();
			gra.setColor(Color.WHITE);
			gra.fillRect(0, 0, w, h);
			gra.drawImage(prev, 0, 0, null);
			g.drawImage(img, 0, 0, null);
			gra.dispose();
		} else
			g.drawImage(prev, 0, 0, null);
		if (ImgCore.ref) {
			g.setColor(Color.ORANGE);
			g.drawString("Time cost: " + Timer.inter + "%", 20, 20);
		}
		g.dispose();
		if (lbimg != null)
			lbimg.add(prev);
	}

	protected void draw(FakeGraphics gra) {
		int w = getWidth();
		int h = getHeight();
		gra.translate(w / 2, h * 3 / 4);
		if (ent != null)
			ent.draw(gra, ori.copy().times(-1), siz);
	}

	protected void end(JTG btn) {
		if (lbimg == null)
			return;
		loader.finish(btn);
		lbimg = null;
		loader = null;
	}

	protected synchronized BufferedImage getImage() {
		int w = getWidth();
		int h = getHeight();
		BufferedImage img;
		Graphics2D gra;
		if (!blank && white) {
			img = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
			gra = (Graphics2D) img.getGraphics();
		} else {
			img = (BufferedImage) createImage(w, h);
			gra = (Graphics2D) img.getGraphics();
			if (!blank) {
				GradientPaint gdt = new GradientPaint(w / 2, 0, c0, w / 2, h / 2, c1, true);
				Paint p = gra.getPaint();
				gra.setPaint(gdt);
				gra.fillRect(0, 0, w, h);
				gra.setPaint(p);
			}
		}
		draw(new FG2D(gra));
		gra.dispose();
		return img;
	}

	protected synchronized void mouseDragged(MouseEvent e) {
		if (p == null)
			return;
		ori.x += p.x - e.getX();
		ori.y += p.y - e.getY();
		p = e.getPoint();
	}

	protected synchronized void mousePressed(MouseEvent e) {
		p = e.getPoint();
	}

	protected synchronized void mouseReleased(MouseEvent e) {
		p = null;
	}

	protected void setEntity(EAnimI ieAnim) {
		ent = ieAnim;
	}

	protected Loader start() {
		if (ent == null)
			return null;
		lbimg = new ArrayList<>();
		loader = new Loader(lbimg);
		loader.start();
		return loader;
	}

	protected synchronized void update() {
		if (ent != null)
			ent.update(true);
	}

}

class Loader extends Thread {

	private static final int SLE = 33;

	private final AnimatedGifEncoder gif;
	private final List<BufferedImage> lbimg;

	private int index = 0;
	private boolean finish;
	private JTG jtb;

	protected Loader(List<BufferedImage> list) {
		lbimg = list;
		gif = new AnimatedGifEncoder();
		gif.setDelay(33);
		Writer.writeGIF(gif);
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

	protected void finish(JTG btn) {
		finish = true;
		jtb = btn;
		jtb.setEnabled(false);
	}

	protected String getProg() {
		return index + "/" + lbimg.size();
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
