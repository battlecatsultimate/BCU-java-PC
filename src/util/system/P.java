package util.system;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import util.Copible;

public strictfp class P extends Copible {

	public static P polar(double r, double t) {
		return new P(r * Math.cos(t), r * Math.sin(t));
	}

	public static Polygon polygon(P[] ps) {
		int n = ps.length;
		int[] x = new int[n];
		int[] y = new int[n];
		for (int i = 0; i < n; i++) {
			x[i] = (int) ps[i].x;
			y[i] = (int) ps[i].y;
		}
		return new Polygon(x, y, n);
	}

	public double x, y;

	public P(Dimension d) {
		x = d.getWidth();
		y = d.getHeight();
	}

	public P(double X, double Y) {
		x = X;
		y = Y;
	}

	public P(Point p) {
		x = p.getX();
		y = p.getY();
	}

	public P(Point2D p) {
		x = p.getX();
		y = p.getY();
	}

	public double abs() {
		return dis(new P(0, 0));
	}

	public double atan2() {
		return Math.atan2(y, x);
	}

	public double atan2(P p) {
		return sf(p).atan2();
	}

	@Override
	public P copy() {
		return new P(x, y);
	}

	public double crossP(P p) {
		return x * p.y - y * p.x;
	}

	public double dis(P p) {
		return Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
	}

	public P divide(P p) {
		x /= p.x;
		y /= p.y;
		return this;
	}

	public double dotP(P p) {
		return x * p.x + y * p.y;
	}

	@Override
	public boolean equals(Object obj) {
		if (P.class.isAssignableFrom(obj.getClass())) {
			P i = (P) obj;
			if (Math.abs(i.x - x) + Math.abs(i.y - y) < 1e-10)
				return true;
		}
		return false;
	}

	public boolean limit(P b2) {
		return limit(new P(0, 0), b2);
	}

	public boolean limit(P b1, P b2) {
		boolean ans = out(b1, b2, 0);
		if (x < b1.x)
			x = b1.x;
		if (x > b2.x)
			x = b2.x;
		if (y < b1.y)
			y = b1.y;
		if (y > b2.y)
			y = b2.y;
		return ans;
	}

	public P middle(P p, double per) {
		return copy().plus(sf(p), per);
	}

	public P middleC(P p, double per) {
		return copy().plus(sf(p), (1 - Math.cos(Math.PI * per)) / 2);
	}

	public boolean moveOut(P v, P b2, double r) {
		return moveOut(v, new P(0, 0), b2, r);
	}

	public boolean moveOut(P v, P b1, P b2, double r) {
		return x + r < b1.x && v.x <= 0 || y + r < b1.y && v.y <= 0 || x - r > b2.x && v.x >= 0
				|| y - r > b2.y && v.y >= 0;
	}

	public boolean out(int[] rect, double sca, double r) {
		P p0 = new P(rect[0], rect[1]);
		P p1 = new P(rect[2], rect[3]).plus(p0);
		p0.times(sca);
		p1.times(sca);
		return out(p0, p1, r);
	}

	public boolean out(P b2, double r) {
		return out(new P(0, 0), b2, r);
	}

	public boolean out(P b1, P b2, double r) {
		return x + r < b1.x || y + r < b1.y || x - r > b2.x || y - r > b2.y;
	}

	public P plus(double px, double py) {
		x += px;
		y += py;
		return this;
	}

	public P plus(P p) {
		x += p.x;
		y += p.y;
		return this;
	}

	public P plus(P p, double n) {
		x += p.x * n;
		y += p.y * n;
		return this;
	}

	public P positivize() {
		if (x < 0)
			x = -x;
		if (y < 0)
			y = -y;
		return this;
	}

	public P rotate(double t) {
		return setTo(x * Math.cos(t) - y * Math.sin(t), y * Math.cos(t) + x * Math.sin(t));
	}

	public P setTo(double tx, double ty) {
		x = tx;
		y = ty;
		return this;
	}

	/* return this */
	public P setTo(P p) {
		x = p.x;
		y = p.y;
		return this;
	}

	public P sf(P p) {
		return substractFrom(p);
	}

	public P substractFrom(P p) {
		return new P(p.x - x, p.y - y);
	}

	public P times(double d) {
		x *= d;
		y *= d;
		return this;
	}

	public P times(double hf, double vf) {
		x *= hf;
		y *= vf;
		return this;
	}

	public P times(P p) {
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

	@Override
	public String toString() {
		return x + "," + y;
	}
}
