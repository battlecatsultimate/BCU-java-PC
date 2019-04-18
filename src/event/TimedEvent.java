package event;

import java.util.ArrayList;
import java.util.List;

import page.Page;

public class TimedEvent implements Displayable {

	private static final String[] strs = new String[] { Page.get(0, "all-day"), "0:00~11:00", "11:00~24:00",
			"0:00~12:00", "12:00~24:00", "7:00~14:00", "17:00~24:00", };

	protected final int time;

	public final List<DisplayEvent> perm = new ArrayList<>();
	public final List<DisplayEvent> situ = new ArrayList<>();

	protected TimedEvent(int t) {
		time = t;
	}

	public String getTime() {
		if (time < strs.length)
			return strs[time];
		return time + ":00~";
	}

	public int size() {
		return Math.max(perm.size(), situ.size());
	}

	protected void add(DisplayEvent e, int t) {
		if ((t & 1) == 1)
			perm.add(e);
		if ((t & 2) == 2)
			situ.add(e);
	}

	protected void pack() {
		for (HourGrouper gh : HourGrouper.lgh)
			gh.grouping(this);
	}

}
