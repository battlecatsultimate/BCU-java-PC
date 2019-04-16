package event;

import java.util.Queue;

public class Yearly extends Repeat {

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
