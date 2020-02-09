package event;

public class EventGroup implements DisplayEvent {

	private final EventRaw[] ers;
	private final Event[] es;
	private final GroupPattern gp;

	protected EventGroup(EventRaw[] raws, GroupPattern pattern) {
		ers = raws;
		gp = pattern;
		Event[][] ess = new Event[raws.length][];
		int n = 0;
		for (int i = 0; i < raws.length; i++) {
			ess[i] = raws[i].produceEvents();
			n += ess[i].length;
		}
		es = new Event[n];
		int k = 0;
		for (int i = 0; i < ess.length; i++)
			for (int j = 0; j < ess[i].length; j++)
				es[k++] = ess[i][j];
	}

	@Override
	public Event[] getEvents() {
		return es;
	}

	@Override
	public String getID() {
		return gp.name;
	}

	@Override
	public int getType() {
		int t = 0;
		for (EventRaw er : ers)
			t |= er.type;
		return t;
	}

	@Override
	public String toString() {
		return gp.toString();
	}

}
