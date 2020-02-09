package event;

import java.util.ArrayList;
import java.util.List;

public class GroupPattern {

	protected static final List<GroupPattern> lgp = new ArrayList<>();

	protected final String name;

	private final int n;
	private final int[][] times;
	private final int[][] stages;

	public GroupPattern(String Name, String[] args) {
		name = Name;
		n = args.length;
		times = new int[n][2];
		stages = new int[n][];
		for (int i = 0; i < n; i++) {
			String[] strs = args[i].split("\t");
			int[] ints = new int[strs.length];
			for (int j = 0; j < ints.length; j++)
				ints[j] = Integer.parseInt(strs[j].trim());
			times[i][0] = ints[0];
			times[i][1] = ints[1];
			int nt = ints[2];
			stages[i] = new int[nt];
			for (int j = 0; j < nt; j++)
				stages[i][j] = ints[j + 3];
		}
		lgp.add(this);
	}

	@Override
	public String toString() {
		return Namer.getEG(name);
	}

	protected boolean grouping(EventBase eb, List<EventRaw> ler) {
		EventDate ed = null;
		int[] ks = new int[n];
		int k = 0;
		for (int i = 0; i < n; i++) {
			if (i + k >= ler.size())
				return false;
			EventRaw er = ler.get(i + k);
			if (i == 0)
				ed = er.sd;
			while (!match(i, er, ed)) {
				if (i == 0)
					return false;
				k++;
				if (i + k < ler.size())
					er = ler.get(i + k);
				else
					return false;
			}
			ks[i] = k;
			if (i == n - 1) {
				EventRaw[] ers = new EventRaw[n];
				for (int j = 0; j < n; j++) {
					ers[j] = ler.get(ks[j]);
					ler.remove(ks[j]);
				}
				eb.le.add(new EventGroup(ers, this));
				return true;
			}
		}
		return false;
	}

	private boolean match(int index, EventRaw er, EventDate ed) {
		if (stages[index].length != er.stage)
			return false;
		for (int i = 0; i < stages[index].length; i++)
			if (stages[index][i] != er.stages[i])
				return false;
		int t0 = times[index][0];
		int t1 = times[index][1];
		if (t0 >= 0) {
			if (ed.next(t0).compare(er.sd) != 0)
				return false;
			if (er.ed.infinite())
				return false;
			if (t1 != -1 && ed.next(t1).compare(er.ed) != 0)
				return false;
		}
		if (t0 == -2)
			if (!er.ed.infinite())
				return false;
		return true;
	}

}
