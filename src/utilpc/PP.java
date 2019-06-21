package utilpc;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import common.util.system.P;

public strictfp class PP extends P {

	public PP(Dimension d) {
		super(d.getWidth(), d.getHeight());
	}

	public PP(double d, double e) {
		super(d, e);
	}

	public PP(Point p) {
		super(p.getX(), p.getY());
	}

	public PP(Point2D p) {
		super(p.getX(), p.getY());
	}

	@Override
	public PP copy() {
		return new PP(x, y);
	}

	@Override
	public PP divide(P p) {
		x /= p.x;
		y /= p.y;
		return this;
	}

	@Override
	public PP sf(P p) {
		return new PP(p.x - x, p.y - y);
	}

	@Override
	public PP times(double d) {
		x *= d;
		y *= d;
		return this;
	}

	@Override
	public PP times(double hf, double vf) {
		x *= hf;
		y *= vf;
		return this;
	}

	@Override
	public PP times(P p) {
		x *= p.x;
		y *= p.y;
		return this;
	}

	public Dimension toDimension() {
		return new Dimension((int) x, (int) y);
	}

	public Point toPoint() {
		return new Point((int) x, (int) y);
	}

	public Point2D toPoint2D() {
		return new Point2D.Double(x, y);
	}

	public Rectangle toRectangle(int w, int h) {
		return new Rectangle((int) x, (int) y, w, h);
	}

	public Rectangle toRectangle(P p) {
		return new Rectangle((int) x, (int) y, (int) p.x, (int) p.y);
	}

	public Rectangle2D toRectangle2D(P p) {
		return new Rectangle2D.Double(x, y, p.x, p.y);
	}

}
