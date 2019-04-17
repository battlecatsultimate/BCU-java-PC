package util.entity.data;

import io.InStream;
import io.OutStream;
import util.Data;
import util.system.BasedCopable;

public class AtkDataModel extends Data implements MaskAtk, BasedCopable<AtkDataModel, CustomEntity> {

	public final CustomEntity ce;
	public String str = "";
	public int atk, pre, ld0, ld1, targ = TCH_N;
	public boolean rev;
	public int[][] proc = new int[PROC_TOT][PROC_WIDTH];

	public AtkDataModel(CustomEntity ent) {
		atk = 0;
		pre = 1;
		ce = ent;
		str = ce.getAvailable(str);
	}

	protected AtkDataModel(CustomEntity ene, AtkDataModel adm) {
		ce = ene;
		str = ce.getAvailable(adm.str);
		atk = adm.atk;
		pre = adm.pre;
		ld0 = adm.ld0;
		ld1 = adm.ld1;
		for (int i = 0; i < PROC_TOT; i++)
			proc[i] = adm.proc[i].clone();
	}

	protected AtkDataModel(CustomEntity ent, InStream is, String ver) {
		ce = ent;
		zread(ver, is);
	}

	@Override
	public AtkDataModel clone() {
		return new AtkDataModel(ce, this);
	}

	@Override
	public AtkDataModel copy(CustomEntity nce) {
		return new AtkDataModel(nce, this);
	}

	@Override
	public int getDire() {
		return rev ? -1 : 1;
	}

	@Override
	public int getLongPoint() {
		return isLD() ? ld1 : ce.range;
	}

	@Override
	public int[] getProc(int ind) {
		if (ce.rep != this && ce.common)
			return ce.rep.getProc(ind);
		return proc[ind];
	}

	@Override
	public int getShortPoint() {
		return isLD() ? ld0 : -ce.width;
	}

	@Override
	public int getTarget() {
		return targ;
	}

	@Override
	public String toString() {
		return str;
	}

	protected int[] getAtkData() {
		return new int[] { atk, pre, 1 };
	}

	protected boolean isLD() {
		return ld0 != 0 || ld1 != 0;
	}

	protected boolean isOmni() {
		return ld0 * ld1 < 0;
	}

	protected void write(OutStream os) {
		os.writeString("0.4.0");
		os.writeString(str);
		os.writeInt(atk);
		os.writeInt(pre);
		os.writeInt(ld0);
		os.writeInt(ld1);
		os.writeInt(targ);
		os.writeInt(rev ? 1 : 0);
		os.writeIntBB(proc);
	}

	protected void zread$000301(InStream is) {
		str = is.nextString();
		atk = is.nextInt();
		pre = is.nextInt();
		ld0 = is.nextShort();
		ld1 = is.nextShort();
		int[][] temp = is.nextIntsBB();
		proc = new int[PROC_TOT][PROC_WIDTH];
		for (int i = 0; i < temp.length; i++)
			for (int j = 0; j < temp[i].length; j++)
				proc[i][j] = temp[i][j];
	}

	protected void zread$000400(InStream is) {
		str = is.nextString();
		atk = is.nextInt();
		pre = is.nextInt();
		ld0 = is.nextInt();
		ld1 = is.nextInt();
		targ = is.nextInt();
		rev = is.nextInt() == 1;
		int[][] temp = is.nextIntsBB();
		proc = new int[PROC_TOT][PROC_WIDTH];
		for (int i = 0; i < temp.length; i++)
			for (int j = 0; j < temp[i].length; j++)
				proc[i][j] = temp[i][j];
	}

	private void zread(String ver, InStream is) {
		int val = getVer(ver);
		if (val >= 307)
			val = getVer(is.nextString());
		if (val >= 400)
			zread$000400(is);
		else if (val >= 301)
			zread$000301(is);
	}

}
