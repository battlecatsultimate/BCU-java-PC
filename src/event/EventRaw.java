package event;

import static event.Repeat.NONE;
import static event.Repeat.construct;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import main.Printer;

public class EventRaw extends RawLine {

	protected static final int PERM = 1, SITU = 2, NEWV = 4, FTRE = 8, ONET = 16;

	protected static void pack(EventBase eb) {
		eb.le.clear();
		for (EventRaw er : eb.ler)
			er.classification();
		List<EventRaw> ler = new ArrayList<>();
		ler.addAll(eb.ler);
		while (ler.size() > 0) {
			EventRaw er = ler.get(0);
			boolean b = false;
			for (GroupPattern gp : GroupPattern.lgp)
				if (b = gp.grouping(eb, ler))
					break;
			if (!b) {
				for (Event e : er.produceEvents())
					eb.le.add(e);
				ler.remove(0);
			}
		}

	}

	protected int loop, stage, type;
	protected TimePattern[] loops;
	protected int[] stages;

	protected Event[] es;

	protected EventRaw(EventBase eb, String str) {
		String[] strs = str.trim().split("\t");
		Queue<Integer> ints = new ArrayDeque<>(strs.length);
		for (String s : strs)
			ints.add(Integer.parseInt(s));
		sd = new EventDate(ints.poll());
		st = new EventTime(ints.poll());
		ed = new EventDate(ints.poll());
		et = new EventTime(ints.poll());
		sv = new Version(eb, ints.poll());
		ev = new Version(eb, ints.poll());
		int temp = ints.poll();
		if (temp != 0)
			Printer.p("EventRaw", 64, "new mechanism: " + temp);
		loop = ints.poll();
		loops = new TimePattern[loop];
		for (int i = 0; i < loop; i++)
			loops[i] = new TimePattern(this, ints);
		stage = ints.poll();
		stages = new int[stage];
		for (int i = 0; i < stage; i++)
			stages[i] = ints.poll();
		if (!ints.isEmpty())
			Printer.p("EventRaw", 75, "error in reading");
		eb.ler.add(this);
		es = new Event[stage];
		for (int i = 0; i < stage; i++)
			es[i] = new Event(this, stages[i]);
	}

	public int getType() {
		return type;
	}

	@Override
	public boolean[] valid(EventDate d) {
		boolean[] avail = super.valid(d);
		boolean[] inter = construct(NONE, -1);
		if (avail == null)
			return construct(NONE, -1);
		for (TimePattern l : loops) {
			boolean[] bs = l.valid(d);
			if (bs == null)
				continue;
			for (int i = 0; i < 24; i++)
				inter[i] |= bs[i];
		}
		if (loop > 0)
			for (int i = 0; i < 24; i++)
				avail[i] &= inter[i];
		return avail;
	}

	protected void classification() {
		if (loop == 0)
			type |= ONET;
		if (ed.infinite())
			type |= PERM;
		if (sv.newest())
			type |= NEWV;
		if (ed.status() >= 0)
			type |= FTRE;
		if (ed.getInt() - sd.getInt() <= 100)
			type |= SITU;
	}

	protected Event[] produceEvents() {
		return es;
	}

}
