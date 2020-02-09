package event;

public class HourGroup implements DisplayEvent {

	private final Event[] es;
	private final HourGrouper gh;

	protected HourGroup(Event[] raws, HourGrouper pattern) {
		es = raws;
		gh = pattern;
	}

	@Override
	public Event[] getEvents() {
		return es;
	}

	@Override
	public String getID() {
		return gh.getName();
	}

	@Override
	public int getType() {
		int t = 0;
		for (Event e : es)
			t |= e.getType();
		return t;
	}

	@Override
	public String toString() {
		return gh.toString();
	}

}
