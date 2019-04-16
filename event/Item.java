package event;

import static event.Event.prev;
import static event.Event.sufx;

import java.util.ArrayDeque;
import java.util.Queue;

import main.Printer;

public class Item extends RawLine implements Displayable {

	protected static final int PERM = 1, SITU = 2, NEWV = 4, FTRE = 8;
	protected static final int UNKNOWN = 0, POPUP = 1, ITEM = 2, PLA = 3;

	protected static void pack(EventBase eb) {
		for (Item i : eb.li)
			i.classification();
	}

	protected String str0;
	protected String[] str1;
	protected int id0, id1, id2, n0, n1, n2;

	protected int type, clas;

	protected Item(EventBase eb, String str) {
		String[] strs = str.trim().split("\t");
		if (strs.length != 16)
			Printer.p("Item", 37, "error in reading");
		Queue<Integer> ints = new ArrayDeque<>(strs.length);
		String strt = "";
		for (int i = 0; i < 16; i++)
			if (i == 11)
				str0 = strs[i].trim();
			else if (i == 12)
				strt = strs[i].trim();
			else
				ints.add(Integer.parseInt(strs[i]));
		sd = new EventDate(ints.poll());
		st = new EventTime(ints.poll());
		ed = new EventDate(ints.poll());
		et = new EventTime(ints.poll());
		sv = new Version(eb, ints.poll());
		ev = new Version(eb, ints.poll());
		str1 = strt.split("<br>");
		int temp = ints.poll();
		if (temp != 0)
			Printer.p("Item", 56, "new mechanism: " + temp);
		temp = ints.poll();
		if (temp != 0)
			Printer.p("Item", 59, "new mechanism: " + temp);
		id0 = ints.poll();
		id1 = ints.poll();
		id2 = ints.poll();
		n0 = ints.poll();
		n1 = ints.poll();
		n2 = ints.poll();
		eb.li.add(this);
	}

	public int getClas() {
		return clas;
	}

	public String[] getStrings() {
		String[] ans = new String[4 + str1.length];
		ans[0] = super.toString();
		ans[1] = id0 + ", " + id1 + ", " + id2;
		ans[2] = n0 + ", " + n1 + ", " + n2;
		ans[3] = str0;
		for (int i = 0; i < str1.length; i++)
			ans[4 + i] = str1[i];
		if (id1 > 11000 && id1 < 12000)
			ans[3] = prev + id1 + sufx;
		return ans;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		String ans = Namer.getI(id1);
		if (id2 > 0)
			ans += "*" + id2;
		return ans;
	}

	protected void classification() {
		if (ed.infinite())
			type |= PERM;
		if (sv.newest())
			type |= NEWV;
		if (ed.status() >= 0)
			type |= FTRE;
		if (ed.getInt() - sd.getInt() <= 100)
			type |= SITU;
		if (id1 == 300 || id1 == 303)
			clas = POPUP;
		else if (id1 > 10000 && id1 < 11000)
			clas = PLA;
		else if (Namer.getI(id1).startsWith("ID="))
			clas = UNKNOWN;
		else
			clas = ITEM;
	}

}
