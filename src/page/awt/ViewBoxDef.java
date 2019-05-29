package page.awt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import io.Writer;
import main.Timer;
import page.JTG;
import page.view.ViewBox;
import res.AnimatedGifEncoder;
import util.ImgCore;
import util.anim.EAnimI;
import util.system.fake.FakeGraphics;
import util.system.fake.awt.FG2D;

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

class ViewBoxDef extends Canvas implements ViewBox, ViewBox.VBExporter {

	private static final long serialVersionUID = 1L;

	protected BufferedImage prev = null;
	protected boolean blank;

	private EAnimI ent;
	protected Controller ctrl;
	private List<BufferedImage> lbimg = null;
	private Loader loader = null;

	protected ViewBoxDef() {
		this(new Controller());
	}

	protected ViewBoxDef(Controller c) {
		ctrl = c;
		c.setCont(this);
		setIgnoreRepaint(true);
	}

	public void draw(FakeGraphics gra) {
		int w = getWidth();
		int h = getHeight();
		gra.translate(w / 2, h * 3 / 4);
		if (ent != null)
			ent.draw(gra, ctrl.ori.copy().times(-1), ctrl.siz);
	}

	@Override
	public void end(JTG btn) {
		if (lbimg == null)
			return;
		loader.finish(btn);
		lbimg = null;
		loader = null;
	}

	@Override
	public Controller getCtrl() {
		return ctrl;
	}

	@Override
	public EAnimI getEnt() {
		return ent;
	}

	@Override
	public VBExporter getExp() {
		return this;
	}

	@Override
	public BufferedImage getPrev() {
		return prev;
	}

	@Override
	public boolean isBlank() {
		return blank;
	}

	@Override
	public void paint() {
		paint(getGraphics());
	}

	@Override
	public synchronized void paint(Graphics g) {
		prev = getImage();
		if (prev == null)
			return;
		int w = getWidth();
		int h = getHeight();
		if (Conf.white) {
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

	@Override
	public void setEntity(EAnimI ieAnim) {
		ent = ieAnim;
	}

	@Override
	public Loader start() {
		if (ent == null)
			return null;
		lbimg = new ArrayList<>();
		loader = new LoaderDef(lbimg);
		loader.start();
		return loader;
	}

	@Override
	public synchronized void update() {
		if (ent != null)
			ent.update(true);
	}

	protected synchronized BufferedImage getImage() {
		int w = getWidth();
		int h = getHeight();
		BufferedImage img;
		Graphics2D gra;
		if (!blank && Conf.white) {
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

}
