package page.anim;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;

import common.util.ImgCore;
import common.util.anim.EAnimD;
import common.util.anim.EAnimU;
import common.util.system.P;
import common.util.system.fake.awt.FG2D;
import main.Timer;

class AnimBox extends Canvas {

	private static final long serialVersionUID = 1L;

	private static final Color c0 = new Color(70, 140, 160), c1 = new Color(85, 185, 205);

	public BufferedImage prev = null;

	protected final P ori = new P(0, 0);
	protected double siz = 0.5;

	protected EAnimD ent;

	protected AnimBox() {
		setIgnoreRepaint(true);
	}

	@Override
	public synchronized void paint(Graphics g) {
		prev = getImage();
		if (prev == null)
			return;
		g.drawImage(prev, 0, 0, null);
		if (ImgCore.ref) {
			g.setColor(Color.ORANGE);
			g.drawString("Time cost: " + Timer.inter + "%", 20, 20);
		}
		g.dispose();
	}

	protected BufferedImage getImage() {
		int w = getWidth();
		int h = getHeight();
		BufferedImage img = (BufferedImage) createImage(w, h);
		if (img == null)
			return img;
		Graphics2D gra = (Graphics2D) img.getGraphics();
		GradientPaint gdt = new GradientPaint(w / 2, 0, c0, w / 2, h / 2, c1, true);
		Paint p = gra.getPaint();
		gra.setPaint(gdt);
		gra.fillRect(0, 0, w, h);
		gra.setPaint(p);
		gra.translate(w / 2, h * 3 / 4);
		if (ent != null)
			ent.draw(new FG2D(gra), ori.copy().times(-1), siz);
		gra.dispose();
		return img;
	}

	protected void setEntity(EAnimU ieAnim) {
		ent = ieAnim;
	}

	protected void setSele(int val) {
		if (ent != null)
			ent.sele = val;
	}

	protected synchronized void update() {
		if (ent != null)
			ent.update(true);
	}

}
