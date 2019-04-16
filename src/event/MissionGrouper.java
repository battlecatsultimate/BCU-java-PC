package event;

import java.util.ArrayList;
import java.util.List;

public class MissionGrouper extends HourGrouper {

	public MissionGrouper() {
		lgh.add(this);
	}

	@Override
	public String getName() {
		return "UNK-MIS";
	}

	@Override
	public String toString() {
		return Namer.getE(-8000);
	}

	@Override
	protected void grouping(TimedEvent te) {
		List<Event> list = new ArrayList<>();
		for (DisplayEvent de : te.situ)
			if (de instanceof Event && de.toString().startsWith(Namer.getE(-8000)))
				list.add((Event) de);
		if (list.size() == 0)
			return;
		int i = 0;
		while (i < te.situ.size())
			if (te.situ.get(i++) instanceof Event) {
				Event e = (Event) te.situ.get(i - 1);
				if (list.contains(e))
					te.situ.remove(--i);
			}
		te.situ.add(new HourGroup(list.toArray(new Event[0]), this));

	}

}
