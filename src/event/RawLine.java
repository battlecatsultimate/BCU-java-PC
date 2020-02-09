package event;

import static event.Repeat.AFTER;
import static event.Repeat.ALL;
import static event.Repeat.BEFORE;
import static event.Repeat.construct;

public class RawLine {

	protected EventDate sd, ed;
	protected EventTime st, et;
	protected Version sv, ev;

	@Override
	public String toString() {
		return sd + "-" + st + "(" + sv + ") ~ " + ed + "-" + et + "(" + ev + ")";
	}

	public boolean[] valid(EventDate d) {
		if (!ev.newest())
			return null;
		if (d.compare(sd) < 0 || d.compare(ed) > 0)
			return null;
		if (d.compare(ed) == 0 && et.toInt() == 0)
			return null;
		boolean[] avail = null;
		if (d.compare(sd) > 0 && d.compare(ed) < 0)
			avail = construct(ALL, -1);
		else if (d.compare(ed) == 0)
			avail = construct(BEFORE, st.toHr());
		else if (d.compare(sd) == 0)
			avail = construct(AFTER, et.toHr());
		return avail;
	}

}
