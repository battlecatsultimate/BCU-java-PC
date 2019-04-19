package event;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public abstract class HourGrouper {

	protected static final List<HourGrouper> lgh = new ArrayList<>();

	public static void process(Queue<String> qs) {
		lgh.clear();
		while (qs.size() > 0) {
			String[] str = qs.poll().trim().split("\t");
			new StageGrouper(str);
		}
		new MissionGrouper();
	}

	public abstract String getName();

	protected abstract void grouping(TimedEvent te);

}

class MissionGrouper extends HourGrouper {

	protected MissionGrouper() {
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

class StageGrouper extends HourGrouper {

	protected final String name;

	private final int n;
	private final int[] stages;

	public StageGrouper(String[] args) {
		name = args[1];
		n = Integer.parseInt(args[0]);
		stages = new int[n];
		for (int i = 0; i < n; i++)
			stages[i] = Integer.parseInt(args[i + 2].trim());
		lgh.add(this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return Namer.getEG(name);
	}

	@Override
	protected void grouping(TimedEvent te) {
		List<DisplayEvent> list;
		if (name.startsWith("P."))
			list = te.perm;
		else
			list = te.situ;
		Set<Integer> ref = new TreeSet<>();
		Set<Integer> con = new TreeSet<>();
		for (int stage : stages)
			ref.add(stage);
		for (DisplayEvent de : list)
			if (de instanceof Event) {
				Event e = (Event) de;
				if (ref.contains(e.stage))
					con.add(e.stage);
			}
		if (ref.size() == con.size()) {
			int i = 0;
			List<Event> l = new ArrayList<>();
			while (i < list.size())
				if (list.get(i++) instanceof Event) {
					Event e = (Event) list.get(i - 1);
					if (ref.contains(e.stage)) {
						list.remove(--i);
						l.add(e);
					}
				}
			list.add(new HourGroup(l.toArray(new Event[0]), this));
		}
	}

}
