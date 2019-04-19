package event;

import java.util.Calendar;

public class EventDate {

	protected int y, m, d;

	public EventDate(Calendar c) {
		y = c.get(Calendar.YEAR);
		m = c.get(Calendar.MONTH) + 1;
		d = c.get(Calendar.DATE);
	}

	public EventDate(int input) {
		d = input % 100;
		input /= 100;
		m = input % 100;
		input /= 100;
		y = input;
	}

	public int compare(EventDate date) {
		if (y > date.y && y > 0 && date.y > 0)
			return 1;
		if (y < date.y && y > 0 && date.y > 0)
			return -1;
		if (m > date.m && m > 0 && date.m > 0)
			return 1;
		if (m < date.m && m > 0 && date.m > 0)
			return -1;
		if (d > date.d && d > 0 && date.d > 0)
			return 1;
		if (d < date.d && d > 0 && date.d > 0)
			return -1;
		return 0;
	}

	public int getInt() {
		return y * 10000 + m * 100 + d;
	}

	public int getWeek() {
		Calendar c = Calendar.getInstance();
		c.set(y, m - 1, d);
		return c.get(Calendar.DAY_OF_WEEK) - 1;
	}

	public boolean infinite() {
		return y >= 2030;
	}

	public EventDate next(int n) {
		if (n == 0)
			return this;
		Calendar c = Calendar.getInstance();
		c.set(y, m - 1, d);
		c.add(Calendar.DATE, n);
		int ty = c.get(Calendar.YEAR);
		int tm = c.get(Calendar.MONTH) + 1;
		int td = c.get(Calendar.DATE);
		return new EventDate(ty * 10000 + tm * 100 + td);
	}

	public int status() {
		Calendar c = Calendar.getInstance();
		int ty = c.get(Calendar.YEAR);
		int tm = c.get(Calendar.MONTH) + 1;
		int td = c.get(Calendar.DATE);
		if (y < ty)
			return -1;
		if (y > ty)
			return 1;
		if (m < tm)
			return -1;
		if (m > tm)
			return 1;
		if (d < td)
			return -1;
		if (d > td)
			return 1;
		return 0;
	}

	@Override
	public String toString() {
		String ans = "";
		if (y != 0)
			ans += y + "/";
		return ans + Namer.get(m + "") + d;
	}

}