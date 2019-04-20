package util.stage;

import io.InStream;
import io.OutStream;
import util.pack.Pack;
import util.unit.Enemy;

public class SCDef implements StageCont {

	public static final int SIZE = 13;

	public static final int E = 0, N = 1, S0 = 2, R0 = 3, R1 = 4, C0 = 5, L0 = 6, L1 = 7, B = 8, M = 9, S1 = 10,
			C1 = 11, G = 12;

	public int[][] datas;

	protected SCDef(InStream is, int ver) {
		if (ver >= 305) {
			int n = is.nextByte();
			datas = new int[n][SIZE];
			for (int i = 0; i < n; i++)
				for (int j = 0; j < 10; j++)
					datas[i][j] = is.nextInt();
		} else if (ver >= 203) {
			int n = is.nextByte();
			datas = new int[n][SIZE];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < 10; j++)
					datas[i][j] = is.nextInt();
				if (datas[i][5] < 100)
					datas[i][2] *= -1;
			}
		} else
			datas = new int[0][SIZE];
	}

	protected SCDef(int s) {
		datas = new int[s][SIZE];
	}

	@Override
	public boolean contains(Enemy e) {
		for (int[] dat : datas)
			if (dat[0] == e.id)
				return true;
		return false;
	}

	@Override
	public SCDef copy() {
		SCDef ans = new SCDef(datas.length);
		for (int i = 0; i < datas.length; i++)
			ans.datas[i] = datas[i].clone();
		return ans;
	}

	@Override
	public int[][] getSimple() {
		return datas;
	}

	@Override
	public int[] getSimple(int i) {
		return datas[i];
	}

	@Override
	public boolean isSuitable(Pack p) {
		for (int[] ints : datas) {
			if (ints[0] < 1000)
				continue;
			int pac = ints[0] / 1000;
			boolean b = pac == p.id;
			for (int rel : p.rely)
				b |= pac == rel;
			if (!b)
				return false;
		}
		return true;
	}

	@Override
	public boolean isTrail() {
		for (int[] data : datas)
			if (data[5] > 100)
				return true;
		return false;
	}

	@Override
	public void merge(int id, int pid, int[] esind) {
		for (int[] dat : datas)
			if (dat[0] / 1000 == pid)
				dat[0] = esind[dat[0] % 1000] + id * 1000;

	}

	@Override
	public int relyOn(int p) {
		for (int[] data : datas)
			if (data[0] / 1000 == p)
				return Pack.RELY_ENE;
		return -1;
	}

	@Override
	public void removePack(int p) {
		for (int[] data : datas)
			if (data[0] / 1000 == p)
				data[0] = 0;
	}

	@Override
	public OutStream write() {
		OutStream os = new OutStream();
		os.writeInt(0);
		os.writeString("0.4.0");
		os.writeInt(datas.length);
		os.writeInt(SIZE);
		for (int i = 0; i < datas.length; i++)
			for (int j = 0; j < SIZE; j++)
				os.writeInt(datas[i][j]);
		os.terminate();
		return os;
	}

}
