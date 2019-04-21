package util.stage;

import java.util.Map;
import java.util.TreeMap;

import io.InStream;
import io.OutStream;
import util.Data;
import util.basis.StageBasis;
import util.pack.Pack;
import util.system.Copable;
import util.system.FixIndexList;
import util.unit.AbEnemy;
import util.unit.Enemy;

public class SCDef implements Copable<SCDef>{

	public static final int SIZE = 13;

	public static final int E = 0, N = 1, S0 = 2, R0 = 3, R1 = 4, C0 = 5, L0 = 6, L1 = 7, B = 8, M = 9, S1 = 10,
			C1 = 11, G = 12;

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
	
	public int[][] datas;

	public final FixIndexList<SCGroup> sub=new FixIndexList<>(new SCGroup[1000]);//TODO
	public final Map<AbEnemy,Integer> smap=new TreeMap<>();//TODO
	public int sdef=0;
	
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

	public boolean allow(StageBasis sb,AbEnemy e) {
		Integer o=smap.get(e);
		return allow(sb,o==null?0:o);
	}
	
	public boolean allow(StageBasis sb,int val) {
		if(val<0||val>1000||sub.get(val)==null)
			return sb.entityCount(1)<sb.st.max;
		SCGroup g=sub.get(val);
		return sb.entityCount(1,val)<g.max;
	}
	
	public boolean contains(Enemy e) {
		for (int[] dat : datas)
			if (dat[0] == e.id)
				return true;
		return false;
	}

	public SCDef copy() {
		SCDef ans = new SCDef(datas.length);
		for (int i = 0; i < datas.length; i++)
			ans.datas[i] = datas[i].clone();
		return ans;
	}

	public int[][] getSimple() {
		return datas;
	}

	public int[] getSimple(int i) {
		return datas[i];
	}

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

	public boolean isTrail() {
		for (int[] data : datas)
			if (data[5] > 100)
				return true;
		return false;
	}

	public void merge(int id, int pid, int[] esind) {
		for (int[] dat : datas)
			if (dat[0] / 1000 == pid)
				dat[0] = esind[dat[0] % 1000] + id * 1000;

	}

	public int relyOn(int p) {
		for (int[] data : datas)
			if (data[0] / 1000 == p)
				return Pack.RELY_ENE;
		return -1;
	}

	public void removePack(int p) {
		for (int[] data : datas)
			if (data[0] / 1000 == p)
				data[0] = 0;
	}

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
