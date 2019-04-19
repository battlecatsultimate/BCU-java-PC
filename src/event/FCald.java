package event;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import main.Printer;

public class FCald {

	private static final Set<String> hideE = new TreeSet<>();
	private static final Set<Integer> hideG = new TreeSet<>();

	static {
		hideE.add("P.NIT");
		hideE.add("105");
		hideE.add("6000");
		hideE.add("12000");
		hideE.add("105");
		hideG.add(23);
		hideG.add(33);
	}

	private static int classify(boolean[] bs) {
		if (bs == null)
			return -1;
		if (deter(bs, 0, 0))
			return -1;
		if (deter(bs, 0, 24))
			return 0;
		if (deter(bs, 0, 11))
			return 1;
		if (deter(bs, 11, 24))
			return 2;
		if (deter(bs, 0, 12))
			return 3;
		if (deter(bs, 12, 24))
			return 4;
		if (deter(bs, 7, 14))
			return 5;
		if (deter(bs, 17, 24))
			return 6;
		return -2;
	}

	private static boolean deter(boolean[] bs, int st, int et) {
		for (int i = 0; i < st; i++)
			if (bs[i])
				return false;
		for (int i = st; i < et; i++)
			if (!bs[i])
				return false;
		for (int i = et; i < 24; i++)
			if (bs[i])
				return false;
		return true;
	}

	public List<TimedEvent> getEvent(EventDate ed) {
		TimedEvent[] tes = new TimedEvent[24];
		for (DisplayEvent de : EventBase.ebe.le) {
			if (hideE.contains(de.getID()))
				continue;
			for (Event e : de.getEvents()) {
				boolean[] bs = e.raw.valid(ed);
				int t = e.raw.getType();
				int type = classify(bs);
				if (type == -1)
					continue;
				if (type == -2)
					for (int i = 0; i < 24; i++) {
						if (bs[i]) {
							if (tes[i] == null)
								tes[i] = new TimedEvent(i);
							tes[i].add(e, t);
						}
					}
				else {
					if (tes[type] == null)
						tes[type] = new TimedEvent(type);
					tes[type].add(e, t);
				}
			}
		}
		List<TimedEvent> list = new ArrayList<>();
		for (TimedEvent te : tes)
			if (te != null) {
				te.pack();
				list.add(te);
			}
		return list;
	}

	public Gacha[][] getGacha(EventDate ed) {
		List<Gacha> l1 = new ArrayList<>();
		List<Gacha> l2 = new ArrayList<>();
		for (Gacha g : EventBase.ebg.lg)
			if (g.getType() > 0 && !hideG.contains(g.getID())) {
				int type = classify(g.valid(ed));
				if (type == -1)
					continue;
				if (type > 2)
					Printer.p("FCald", 106, "new gacha time: " + type);
				if (type != 2)
					l1.add(g);
				if (type != 1)
					l2.add(g);
			}
		Gacha[][] ans = new Gacha[Math.max(l1.size(), l2.size())][2];
		for (int i = 0; i < l1.size(); i++)
			ans[i][0] = l1.get(i);
		for (int i = 0; i < l2.size(); i++)
			ans[i][1] = l2.get(i);
		return ans;
	}

}
