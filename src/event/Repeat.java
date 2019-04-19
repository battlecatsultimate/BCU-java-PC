package event;

import java.util.Queue;

import main.Printer;

public abstract class Repeat {

	protected static final int ALL = -1, NONE = 0, BEFORE = 1, AFTER = 2;

	protected static boolean[] construct(int type, int hr) {
		boolean[] ans = new boolean[24];
		if (type == -1)
			for (int i = 0; i < 24; i++)
				ans[i] = true;
		if (type == 1)
			for (int i = 0; i < hr; i++)
				ans[i] = true;
		if (type == 2)
			for (int i = hr; i < 24; i++)
				ans[i] = true;
		return ans;
	}

	protected abstract boolean[] valid(EventDate d);

}

class TimePattern extends Repeat {

	private static final int YEAR = 1, MONTH = 2, WEEK = 3, DAY = 4;

	protected EventRaw er;
	protected int loopType, time;
	protected EventTime[][] times;
	protected Repeat rep;

	protected TimePattern(EventRaw eventRaw, Queue<Integer> ints) {
		er = eventRaw;
		int t0 = ints.poll();
		if (t0 == 0) {
			int t1 = ints.poll();
			if (t1 == 0) {
				int t2 = ints.poll();
				if (t2 == 0) {
					loopType = DAY;
				} else {
					loopType = WEEK;
					rep = new Weekly(t2);
				}
			} else if (t1 > 0) {
				loopType = MONTH;
				rep = new Monthly(t1, ints);
				int temp = ints.poll();
				if (temp != 0)
					Printer.p("TimePattern", 35, "after monthly 0: " + temp);
			}
		} else if (t0 == 1) {
			loopType = YEAR;
			rep = new Yearly(ints);
			int temp = ints.poll();
			if (temp != 0)
				Printer.p("TimePattern", 42, "after yearly 0: " + temp);
			temp = ints.poll();
			if (temp != 0)
				Printer.p("TimePattern", 45, "after yearly 1: " + temp);
		} else
			Printer.p("TimePattern", 47, "illegal year indication: " + t0);

		time = ints.poll();
		times = new EventTime[time][2];
		for (int i = 0; i < time; i++) {
			times[i][0] = new EventTime(ints.poll());
			times[i][1] = new EventTime(ints.poll());
		}
	}

	@Override
	public String toString() {
		String ans;
		if (rep == null)
			ans = Namer.get("day");
		else
			ans = rep.toString();
		for (EventTime[] ets : times)
			ans += ", " + ets[0] + "~" + ets[1];
		return ans;
	}

	@Override
	protected boolean[] valid(EventDate d) {
		boolean[] avail = null;
		if (rep != null)
			avail = rep.valid(d);
		else
			avail = construct(ALL, -1);
		if (avail == null)
			return null;
		if (time == 0)
			return avail;
		boolean[] inter = construct(NONE, -1);
		for (int i = 0; i < time; i++) {
			int st = times[i][0].toHr();
			int et = times[i][1].toHr();
			for (int j = st; j < et; j++)
				inter[j] = true;
		}
		for (int i = 0; i < 24; i++)
			avail[i] &= inter[i];
		return avail;
	}

}

class Weekly extends Repeat {

	protected static final String[] name = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

	protected boolean[] days = new boolean[7];

	protected Weekly(int week) {
		for (int i = 0; i < 7; i++)
			days[i] = ((week >> i) & 1) == 1;
	}

	@Override
	public String toString() {
		String ans = "";
		for (int i = 0; i < 7; i++)
			if (days[i]) {
				if (ans.length() > 0)
					ans += ", ";
				ans += Namer.get(name[i]);
			}
		return ans;
	}

	@Override
	protected boolean[] valid(EventDate d) {
		if (days[d.getWeek()])
			return construct(ALL, -1);
		else
			return null;
	}

}

class Yearly extends Repeat {

	protected EventDate sd, ed;
	protected EventTime st, et;

	protected Yearly(Queue<Integer> ints) {
		sd = new EventDate(ints.poll());
		st = new EventTime(ints.poll());
		ed = new EventDate(ints.poll());
		et = new EventTime(ints.poll());
	}

	@Override
	public String toString() {
		return Namer.get("year") + ", " + sd + "-" + st + " ~ " + ed + "-" + et;
	}

	@Override
	protected boolean[] valid(EventDate d) {
		if (d.compare(sd) < 0 || d.compare(ed) > 0)
			return null;
		if (d.compare(ed) == 0)
			return construct(BEFORE, st.toHr());
		if (d.compare(sd) == 0)
			return construct(AFTER, et.toHr());
		return construct(ALL, -1);
	}

}
