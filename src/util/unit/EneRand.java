package util.unit;

import io.InStream;
import io.OutStream;
import util.EREnt;
import util.EntRand;
import util.Res;
import util.basis.StageBasis;
import util.entity.EEnemy;
import util.pack.Pack;
import util.system.VImg;

public class EneRand extends EntRand<Integer> implements AbEnemy {

	public final Pack pack;
	public final int id;

	public String name = "";

	public EneRand(Pack p, int ID) {
		pack = p;
		id = ID;
	}

	@Override
	public EEnemy getEntity(StageBasis sb, Object obj, double mul, int d0, int d1, int m) {
		return get(getSelection(sb, obj), sb, obj, mul, d0, d1, m);
	}

	@Override
	public VImg getIcon() {
		return Res.ico[0][0];
	}

	@Override
	public int getID() {
		return pack.id * 1000 + id;
	}

	@Override
	public String toString() {
		return trio(id) + " - " + name;
	}

	protected EEnemy get(EREnt<Integer> x, StageBasis sb, Object obj, double mul, int d0, int d1, int m) {
		if (x == null || x.ent == null)
			return EnemyStore.getEnemy(0).getEntity(sb, obj, mul, d0, d1, m);
		return EnemyStore.getAbEnemy(x.ent, false).getEntity(sb, obj, x.multi * mul / 100, d0, d1, m);
	}

	protected OutStream write() {
		OutStream os = new OutStream();
		os.writeString("0.4.0");
		os.writeString(name);
		os.writeInt(type);
		os.writeInt(list.size());
		for (EREnt<Integer> e : list) {
			os.writeInt(e.ent);
			os.writeInt(e.multi);
			os.writeInt(e.share);
		}
		os.terminate();
		return os;
	}

	protected void zread(InStream is) {
		int ver = getVer(is.nextString());
		if (ver >= 400)
			zread$000400(is);
	}

	private void zread$000400(InStream is) {
		name = is.nextString();
		type = is.nextInt();
		int n = is.nextInt();
		for (int i = 0; i < n; i++) {
			EREnt<Integer> ere = new EREnt<Integer>();
			list.add(ere);
			ere.ent = is.nextInt();
			ere.multi = is.nextInt();
			ere.share = is.nextInt();
		}
	}
}
