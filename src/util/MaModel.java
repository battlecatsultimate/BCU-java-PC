package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.Queue;

public class MaModel implements Cloneable {

	protected int[][] confs, parts;
	protected int[] ints = new int[3];
	protected int n, m;

	protected MaModel(String str) {
		Queue<String> qs = null;
		try {
			qs = new ArrayDeque<>(Files.readAllLines(new File(str).toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		qs.poll();
		qs.poll();
		n = Integer.parseInt(qs.poll().trim());
		parts = new int[n][14];
		for (int i = 0; i < n; i++) {
			String[] ss = qs.poll().trim().split(",");
			for (int j = 0; j < 13; j++)
				parts[i][j] = Integer.parseInt(ss[j].trim());
		}
		String[] ss = qs.poll().trim().split(",");
		for (int i = 0; i < 3; i++)
			ints[i] = Integer.parseInt(ss[i].trim());
		m = Integer.parseInt(qs.poll().trim());
		confs = new int[m][6];
		for (int i = 0; i < m; i++) {
			ss = qs.poll().trim().split(",");
			for (int j = 0; j < 6; j++)
				confs[i][j] = Integer.parseInt(ss[j].trim());

		}
	}

	protected EPart[] arrange(EAnimU e) {
		EPart[] ents = new EPart[n];
		for (int i = 0; i < n; i++)
			ents[i] = new EPart(this, e.a, parts[i], ents);
		return ents;
	}

}
