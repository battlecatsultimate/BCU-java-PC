package util.system.fake;

import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints.Key;

public class FG2D implements FakeGraphics {

	private final Graphics2D g;

	public FG2D(Graphics graphics) {
		g = (Graphics2D) graphics;
	}

	@Override
	public void drawImage(FakeImage bimg, int i, int j) {
		g.drawImage(bimg.bimg(), i, j, null);
	}

	@Override
	public void drawImage(FakeImage bimg, int ix, int iy, int iw, int ih) {
		g.drawImage(bimg.bimg(), ix, iy, iw, ih, null);
	}

	@Override
	public void drawLine(int i, int j, int x, int y) {
		g.drawLine(i, j, x, y);
	}

	@Override
	public void drawOval(int i, int j, int k, int l) {
		g.drawOval(i, j, k, l);
	}

	@Override
	public void drawRect(int x, int y, int x2, int y2) {
		g.drawRect(x, y, x2, y2);
	}

	@Override
	public void fillOval(int i, int j, int k, int l) {
		g.fillOval(i, j, k, l);
	}

	@Override
	public void fillRect(int x, int y, int w, int h) {
		g.fillRect(x, y, w, h);
	}

	@Override
	public Composite getComposite() {
		return g.getComposite();
	}

	@Override
	public FakeTransform getTransform() {
		return new FTAT(g.getTransform());
	}

	@Override
	public void gradRect(int x, int y, int w, int h, int a, int b, Color c, int d, int e, Color f) {
		g.setPaint(new GradientPaint(a, b, c, d, e, f));
		g.fillRect(x, y, w, h);
	}

	@Override
	public void rotate(double d) {
		g.rotate(d);
	}

	@Override
	public void scale(int hf, int vf) {
		g.scale(hf, vf);
	}

	@Override
	public void setColor(Color red) {
		g.setColor(red);
	}

	@Override
	public void setComposite(Composite c) {
		g.setComposite(c);
	}

	@Override
	public void setRenderingHint(Key key, Object object) {
		g.setRenderingHint(key, object);
	}

	@Override
	public void setTransform(FakeTransform at) {
		g.setTransform(at.getAT());
	}

	@Override
	public void translate(double x, double y) {
		g.translate(x, y);
	}

}
