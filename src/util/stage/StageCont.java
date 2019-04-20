package util.stage;

import io.InStream;
import io.OutStream;
import util.Data;
import util.pack.Pack;
import util.unit.Enemy;

public interface StageCont {

	public static SCDef zread(InStream is) {
		int t = is.nextInt();
		int ver = Data.getVer(is.nextString());
		if (t == 0) {
			if (ver >= 400) {
				int n = is.nextInt();
				int m = is.nextInt();
				SCDef scd = new SCDef(n);
				for (int i = 0; i < n; i++)
					for (int j = 0; j < m; j++)
						scd.datas[i][j] = is.nextInt();
				return scd;
			}
		}
		return null;
	}

	public boolean contains(Enemy e);

	public StageCont copy();

	public int[][] getSimple();

	public int[] getSimple(int i);

	public boolean isSuitable(Pack p);

	public boolean isTrail();

	public void merge(int id, int pid, int[] esind);

	public int relyOn(int p);

	public void removePack(int p);

	public OutStream write();

}
