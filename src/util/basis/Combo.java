package util.basis;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import util.Data;
import util.system.VFile;

public class Combo extends Data {

	public static final Combo[][] combos = new Combo[C_TOT][];
	public static final int[][] values = new int[C_TOT][5];
	public static int[][] filter;

	public static void readFile() {
		VFile vf = VFile.getFile("./org/data/NyancomboData.csv");
		Queue<String> qs = readLine(vf);
		List<Combo> list = new ArrayList<>();
		int i = 0;
		int[] ns = new int[C_TOT];
		for (String str : qs) {
			if (str.length() < 20)
				continue;
			Combo c = new Combo(i++, str.trim());
			if (c.show > 0) {
				list.add(c);
				ns[c.type]++;
			}
		}
		for (i = 0; i < C_TOT; i++)
			combos[i] = new Combo[ns[i]];
		ns = new int[C_TOT];
		for (Combo c : list)
			combos[c.type][ns[c.type]++] = c;

		vf = VFile.getFile("./org/data/NyancomboParam.tsv");
		qs = readLine(vf);
		for (i = 0; i < C_TOT; i++) {
			String[] strs = qs.poll().trim().split("\t");
			if (strs.length < 5)
				continue;
			for (int j = 0; j < 5; j++) {
				values[i][j] = Integer.parseInt(strs[j]);
				if (i == C_RESP)
					values[i][j] *= 2.6;
				if (i == C_C_SPE)
					values[i][j] = (values[i][j] - 10) * 15;
			}
		}
		vf = VFile.getFile("./org/data/NyancomboFilter.tsv");
		qs = readLine(vf);
		filter = new int[qs.size()][];
		for (i = 0; i < filter.length; i++) {
			String[] strs = qs.poll().trim().split("\t");
			filter[i] = new int[strs.length];
			for (int j = 0; j < strs.length; j++)
				filter[i][j] = Integer.parseInt(strs[j]);
		}
	}

	public final int name, show, id, type, lv;
	public final int[][] units;

	protected Combo(int ID, String str) {
		id = ID;
		String[] strs = str.split(",");
		name = Integer.parseInt(strs[0]);
		show = Integer.parseInt(strs[1]);
		int n = 0;
		for (n = 0; n < 5; n++)
			if (Integer.parseInt(strs[2 + n * 2]) == -1)
				break;
		units = new int[n][2];
		for (int i = 0; i < n; i++) {
			units[i][0] = Integer.parseInt(strs[2 + i * 2]);
			units[i][1] = Integer.parseInt(strs[3 + i * 2]);
		}
		type = Integer.parseInt(strs[12]);
		lv = Integer.parseInt(strs[13]);
	}

	@Override
	public String toString() {
		return name + "," + show;
	}

}
