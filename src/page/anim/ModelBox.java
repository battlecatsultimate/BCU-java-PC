package page.anim;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;

import util.anim.EAnimS;
import util.system.P;

class ModelBox extends Canvas {

	private static final long serialVersionUID = 1L;
	private static final Color c0 = new Color(70, 140, 160), c1 = new Color(85, 185, 205);

	public BufferedImage prev = null;

	protected final P ori = new P(0, 0);
	protected double siz = 0.5;

	private EAnimS ent;

	protected ModelBox() {
		setIgnoreRepaint(true);
	}

	public synchronized EAnimS getEnt() {
		return ent;
	}

	@Override
	public synchronized void paint(Graphics g) {
		prev = getImage();
		if (prev == null)
			return;
		g.drawImage(prev, 0, 0, null);
		g.dispose();
	}

	public synchronized void setEnt(EAnimS ent) {
		this.ent = ent;
	}

	protected BufferedImage getImage() {
		int w = getWidth();
		int h = getHeight();
		BufferedImage img = (BufferedImage) createImage(w, h);
		if (img == null)
			return null;
		Graphics2D gra = (Graphics2D) img.getGraphics();
		GradientPaint gdt = new GradientPaint(w / 2, 0, c0, w / 2, h / 2, c1, true);
		Paint p = gra.getPaint();
		gra.setPaint(gdt);
		gra.fillRect(0, 0, w, h);
		gra.setPaint(p);
		gra.translate(w / 2, h * 3 / 4);
		if (getEnt() != null)
			getEnt().draw(gra, ori.copy().times(-1), siz);
		gra.dispose();
		return img;
	}

	protected void setEntity(EAnimS ieAnim) {
		setEnt(ieAnim);
	}

}
