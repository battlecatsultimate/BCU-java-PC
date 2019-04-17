package event;

public class EventTime {

	protected int h, m;

	public EventTime(int input) {
		m = input % 100;
		input /= 100;
		h = input;
	}

	public int toHr() {
		int ans = h;
		if (m >= 59)
			ans++;
		return ans;
	}

	public int toInt() {
		return h * 100 + m;
	}

	@Override
	public String toString() {
		String ans = "";
		if (h < 10)
			ans += 0;
		ans += h + ":";
		if (m < 10)
			ans += 0;
		return ans + m;
	}

}