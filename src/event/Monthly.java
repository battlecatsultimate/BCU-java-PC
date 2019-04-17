package event;

import java.util.Queue;

public class Monthly extends Repeat {

	protected int day;
	protected int[] days;

	protected Monthly(int d, Queue<Integer> ints) {
		int day = d;
		days = new int[day];
		for (int i = 0; i < day; i++)
			days[i] = ints.poll();
	}

	@Override
	public String toString() {
		String ans = Namer.get("month");
		for (int i : days)
			ans += ", " + i;
		return ans;
	}

	@Override
	protected boolean[] valid(EventDate d) {
		for (int i : days)
			if (i == d.d)
				return construct(ALL, -1);
		return null;
	}

}
