package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import util.EAnimU;
import util.ImgCore;
import util.P;

class ViewBoxDef extends Canvas implements MouseListener, MouseWheelListener, MouseMotionListener {

	private static final Color c0 = new Color(70, 140, 160), c1 = new Color(85, 185, 205);
	private static final double res = 0.95;
	private static final long serialVersionUID = 1L;

	private EAnimU ent;
	private final P ori = new P(0, 0);

	private Point p = null;
	private BufferedImage prev = null;

	private double siz = 0.5;

	protected ViewBoxDef() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);

		setIgnoreRepaint(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public synchronized void mouseDragged(MouseEvent e) {
		if (p == null)
			return;
		ori.x += p.x - e.getX();
		ori.y += p.y - e.getY();
		p = e.getPoint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public synchronized void mousePressed(MouseEvent e) {
		p = e.getPoint();
	}

	@Override
	public synchronized void mouseReleased(MouseEvent e) {
		p = null;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (!(e.getSource() instanceof ViewBoxDef))
			return;
		double d = e.getPreciseWheelRotation();

		siz *= Math.pow(res, d);
	}

	@Override
	public synchronized void paint(Graphics g) {
		prev = getImage();
		if (prev == null)
			return;
		g.drawImage(prev, 0, 0, null);
		g.setColor(Color.ORANGE);
		g.drawString("Time cost: " + Timer.inter + "%", 20, 20);
		g.dispose();
	}

	protected void setEnt(EAnimU ent) {
		this.ent = ent;
	}

	protected void timer(int t) {
		if (ent != null)
			ent.update(true);
		paint(getGraphics());
	}

	private synchronized BufferedImage getImage() {
		int w = getWidth();
		int h = getHeight();
		if (w <= 0 || h <= 0)
			return null;
		BufferedImage img = (BufferedImage) createImage(w, h);
		Graphics2D gra = (Graphics2D) img.getGraphics();
		ImgCore.setRenderingHints(gra);
		GradientPaint gdt = new GradientPaint(w / 2, 0, c0, w / 2, h / 2, c1, true);
		Paint p = gra.getPaint();
		gra.setPaint(gdt);
		gra.fillRect(0, 0, w, h);
		gra.setPaint(p);

		gra.translate(w / 2, h * 3 / 4);
		if (ent != null)
			ent.draw(gra, ori.copy().times(-1), siz);
		gra.dispose();

		return img;
	}

}
