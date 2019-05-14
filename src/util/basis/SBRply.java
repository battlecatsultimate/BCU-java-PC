package util.basis;

import io.InStream;
import page.KeyHandler;
import util.BattleStatic;
import util.stage.EStage;
import util.stage.Recd;

public class SBRply extends Mirror {

	private final Recd r;
	private final MirrorSet mir;

	public SBRply(Recd re) {
		super(re);
		r = re;
		mir = MirrorSet.newIns(r);
	}

	public void back(int t) {
		Mirror m = mir.getReal(sb.time - t);
		sb = m.sb;
		rl = m.rl;
	}

	public void restoreTo(int perc) {
		Mirror m = mir.getRaw(r.len * perc / 100);
		sb = m.sb;
		rl = m.rl;
	}

	public SBCtrl transform(KeyHandler kh) {
		return new SBCtrl(kh, (StageBasis) sb.clone(), r);
	}

	@Override
	public void update() {
		super.update();
		mir.add(this);
	}

}

class Mirror extends BattleField {

	protected Release rl;

	protected Mirror(Mirror sr) {
		super((StageBasis) sr.sb.clone());
		rl = sr.rl.clone();
	}

	protected Mirror(Recd r) {
		super(new EStage(r.st, r.star), r.lu, r.conf, r.seed);
		rl = new Release(r.action.translate());
	}

	/** process the user action */
	@Override
	protected void actions() {
		int rec = rl.get();
		if ((rec & 1) > 0)
			act_mon();
		if ((rec & 2) > 0)
			act_can();
		if ((rec & 4) > 0)
			act_sniper();
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 5; j++) {
				if ((rec & (1 << (i * 5 + j + 3))) > 0)
					act_lock(i, j);
				act_spawn(i, j, (rec & (1 << (i * 5 + j + 13))) > 0);
			}
		sb.rx.add(rec);
	}

}

abstract class MirrorSet {

	protected static MirrorSet newIns(Recd r) {
		return new ShortMirror(r);
	}

	protected abstract void add(SBRply sb);

	protected abstract Mirror getRaw(int t);

	protected abstract Mirror getReal(int t);

}

class Release {

	private final ReleaseSource recd;
	private int ind, rec, rex;

	protected Release(InStream in) {
		recd = new ReleaseSource(in);
	}

	private Release(Release r) {
		recd = r.recd;
		ind = r.ind;
		rec = r.rec;
		rex = r.rex;
	}

	protected int get() {
		if (rex == 0)
			if (recd.recd.length <= ind)
				rec = 0;
			else {
				rec = recd.recd[ind++];
				rex = recd.recd[ind++];
			}
		rex--;
		return rec;
	}

	@Override
	protected Release clone() {
		return new Release(this);
	}

}

class ReleaseSource implements BattleStatic {

	protected final int[] recd;

	protected ReleaseSource(InStream in) {
		int n = in.nextInt();
		recd = new int[n];
		for (int i = 0; i < n; i++)
			recd[i] = in.nextInt();
	}

}

class ShortMirror extends MirrorSet {

	private final Mirror[] mis;
	private final int len, size;

	protected ShortMirror(Recd r) {
		len = r.len + 1;
		size = (int) Math.sqrt(len);
		mis = new Mirror[size];
	}

	@Override
	protected void add(SBRply sb) {
		int t = sb.sb.time;
		if(t*size/len>=size)
			return;
		if (mis[t * size / len] == null)
			mis[t * size / len] = new Mirror(sb);
	}

	@Override
	protected Mirror getRaw(int t) {
		Mirror mr = mis[t * size / len];
		if (mr == null)
			return null;
		return new Mirror(mr);
	}

	@Override
	protected Mirror getReal(int t) {
		Mirror m = getRaw(t);
		while (m.sb.time < t)
			m.update();
		return m;
	}

}
