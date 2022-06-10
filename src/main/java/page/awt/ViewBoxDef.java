package page.awt;

import common.CommonStatic;
import common.system.fake.FakeGraphics;
import common.util.anim.EAnimI;
import main.Timer;
import page.JTG;
import page.view.ViewBox;
import utilpc.awt.FG2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

class ViewBoxDef extends Canvas implements ViewBox, ViewBox.VBExporter {

	private static final long serialVersionUID = 1L;

	protected BufferedImage prev = null;
	protected boolean blank;

	protected EAnimI ent;
	protected Controller ctrl;
	private Queue<BufferedImage> lbimg = null;
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
		gra.translate(w / 2.0, h * 3 / 4.0);
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
		if (CommonStatic.getConfig().ref) {
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
	public Loader start(boolean mp4) {
		if (ent == null)
			return null;
		lbimg = new ArrayDeque<>();
		loader = new Loader(lbimg, mp4);
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
				if(CommonStatic.getConfig().viewerColor != -1) {
					gra.setColor(new Color(CommonStatic.getConfig().viewerColor));
					gra.fillRect(0, 0, w, h);
				} else {
					GradientPaint gdt = new GradientPaint(w / 2f, 0, c0, w / 2f, h / 2f, c1, true);
					Paint p = gra.getPaint();
					gra.setPaint(gdt);
					gra.fillRect(0, 0, w, h);
					gra.setPaint(p);
				}
			}
		}
		draw(new FG2D(gra));
		gra.dispose();
		return img;
	}

}
