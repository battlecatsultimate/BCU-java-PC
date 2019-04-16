package event;

import java.util.Queue;

import main.Printer;

public class TimePattern extends Repeat {

	protected static final int YEAR = 1, MONTH = 2, WEEK = 3, DAY = 4;

	protected EventRaw er;
	protected int loopType, time;
	protected EventTime[][] times;
	protected Repeat rep;

	public TimePattern(EventRaw eventRaw, Queue<Integer> ints) {
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
	public boolean[] valid(EventDate d) {
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