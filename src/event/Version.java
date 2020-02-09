package event;

public class Version {

	protected int a, b, c;
	protected EventBase eb;

	public Version(EventBase base, int v) {
		eb = base;
		if (v > eb.nv && v % 100 != 99)
			eb.nv = v;
		c = v % 100;
		v /= 100;
		b = v % 100;
		v /= 100;
		a = v;
	}

	public boolean newest() {
		return a * 10000 + b * 100 + c >= eb.nv;
	}

	@Override
	public String toString() {
		return a + "." + b + "." + c;
	}

}
