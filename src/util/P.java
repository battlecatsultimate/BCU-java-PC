package util;

public strictfp class P {

	public double x, y;

	public P(double X, double Y) {
		x = X;
		y = Y;
	}

	public P copy() {
		return new P(x, y);
	}

	public P times(double d) {
		x *= d;
		y *= d;
		return this;
	}

	public P times(P p) {
		x *= p.x;
		y *= p.y;
		return this;
	}

}
