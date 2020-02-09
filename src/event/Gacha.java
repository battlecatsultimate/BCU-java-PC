package event;

import java.util.Queue;

import main.Printer;

public class Gacha implements Displayable {

	public static final int UNK = 0, USR = 1, LUK = 2;

	protected static void pack(EventBase eb) {
		for (Gacha g : eb.lg)
			g.classification();
	}

	private final GachaTime gt;
	protected final int id, ktf, step;
	protected final boolean gua;
	protected final int[] rarity = new int[5];

	protected final String description;

	private int type;

	protected Gacha(GachaTime time, Queue<Integer> qs, String str) {
		time.eb.lg.add(this);
		gt = time;
		id = qs.poll();
		ktf = qs.poll();
		int temp = qs.poll();
		if (temp != 0)
			Printer.p("Gacha.init", 27, "new mechanics: " + temp);
		step = qs.poll();
		if (step != 0 && step != 4 && step != 3288)
			Printer.p("Gacha.init", 30, "new mechanics: " + step);
		boolean g = false;
		for (int i = 0; i < 5; i++) {
			rarity[i] = qs.poll();
			temp = qs.poll();
			if (i == 3)
				g = temp == 1;
			else if (temp != 0)
				Printer.p("Gacha.init", 38, "new mechanics: " + temp);
		}
		gua = g;
		description = str;
	}

	public int getID() {
		return id;
	}

	public String[] getStrings() {
		String[] ans = new String[4];
		ans[0] = gt.toString();
		ans[1] = "" + rarity[0];
		for (int i = 1; i < 5; i++)
			ans[1] += "/" + rarity[i];
		ans[2] = gt.getType(id);
		if (gua)
			ans[2] += ", " + Namer.get("guar");
		if (step == 4)
			ans[2] += ", " + Namer.get("step");
		else if (step > 3000)
			ans[2] += ", " + Namer.get("lucky");
		else if (step != 0)
			ans[2] += ", mark=" + step;
		ans[2] += ", ID=" + id;
		ans[3] = description;
		return ans;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		String ans = Namer.getG(id);
		if (ans.startsWith("ID=")) {
			ans = description;
			String[] ss = ans.split("��");
			if (ss.length > 1)
				ans = ss[1];
			ss = ans.split("��");
			if (ss.length > 1)
				if (ss[0].length() == 0)
					ans = ss[1];
				else
					ans = ss[0];
			ss = ans.split("��");
			ans = ss[ss.length - 1];
		}
		if (gua)
			ans = "[" + Namer.get("guar") + "]" + ans;
		return ans;
	}

	public boolean[] valid(EventDate d) {
		return gt.valid(d);
	}

	private void classification() {
		if (id > 100)
			type = USR;
		else if (id > 0)
			type = LUK;
		else
			type = UNK;
	}

}
