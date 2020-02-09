package event;

import java.util.ArrayDeque;
import java.util.Queue;

import main.Printer;

public class GachaTime extends RawLine {

	protected final int type, n;
	protected final Gacha[] gachas;
	protected final EventBase eb;

	protected GachaTime(EventBase base, String str) {
		eb = base;
		String[] strs = str.trim().split("\t");
		Queue<Integer> ints = new ArrayDeque<>(strs.length);
		for (int i = 0; i < 10; i++)
			ints.add(Integer.parseInt(strs[i]));
		sd = new EventDate(ints.poll());
		st = new EventTime(ints.poll());
		ed = new EventDate(ints.poll());
		et = new EventTime(ints.poll());
		sv = new Version(eb, ints.poll());
		ev = new Version(eb, ints.poll());
		int temp = ints.poll();
		if (temp != 0)
			Printer.p("GachaTime", 35, "new mechanism: " + temp);
		temp = ints.poll();
		if (temp != 0)
			Printer.p("GachaTime", 38, "new mechanism: " + temp);
		type = ints.poll();
		n = ints.poll();
		gachas = new Gacha[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 14; j++)
				ints.add(Integer.parseInt(strs[10 + i * 15 + j]));
			String t = 24 + i * 15 >= strs.length ? "" : strs[24 + i * 15];
			gachas[i] = new Gacha(this, ints, t);
		}
		if (!ints.isEmpty())
			Printer.p("GachaTime", 49, "error in reading");
	}

	protected String getType(int id) {
		if (type == 0)
			if (id == 0)
				return Namer.get("g.normal");
			else
				return Namer.get("g.lucky");
		else if (type == 1)
			return Namer.get("g.rare");
		else
			return Namer.get("g.unk") + ", type=" + type;
	}

	protected boolean valid(EventDate d, EventTime t) {
		if (d.compare(sd) < 0 || d.compare(ed) > 0)
			return false;
		if (d.compare(sd) == 0 || t.toInt() < st.toInt())
			return false;
		if (d.compare(ed) == 0 || t.toInt() > et.toInt())
			return false;
		return true;
	}

}
