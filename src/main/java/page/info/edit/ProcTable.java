package page.info.edit;

import common.util.Data;
import common.util.Data.Proc;
import common.util.lang.Formatter;
import main.MainBCU;
import page.JTF;
import page.Page;
import page.support.ListJtfPolicy;

import java.awt.*;

public abstract class ProcTable extends Page {

	static class AtkProcTable extends ProcTable {

		private static final long serialVersionUID = 1L;
		public int height = 0;

		private static final int SEC = 16;

		private static final int[] UINDS = new int[] { Data.P_KB, Data.P_STOP, Data.P_SLOW, Data.P_CRIT, Data.P_WAVE, Data.P_MINIWAVE, Data.P_WEAK,  //Procs for units
				Data.P_BREAK, Data.P_SHIELDBREAK, Data.P_WARP, Data.P_CURSE, Data.P_SATK, Data.P_POIATK, Data.P_VOLC, Data.P_BOUNTY, Data.P_ATKBASE,
				Data.P_SEAL, Data.P_SUMMON, Data.P_MOVEWAVE, Data.P_SNIPER, Data.P_BOSS, Data.P_TIME, Data.P_THEME, Data.P_POISON, Data.P_ARMOR, Data.P_SPEED };
		private static final int[] EINDS = new int[] { Data.P_KB, Data.P_STOP, Data.P_SLOW, Data.P_CRIT, Data.P_WAVE, Data.P_MINIWAVE, Data.P_WEAK,  //Procs for enemies
				Data.P_BREAK, Data.P_SHIELDBREAK, Data.P_WARP, Data.P_CURSE, Data.P_SATK, Data.P_POIATK, Data.P_VOLC, Data.P_ATKBASE, Data.P_SEAL,
				Data.P_SUMMON, Data.P_MOVEWAVE, Data.P_SNIPER, Data.P_BOSS, Data.P_TIME, Data.P_THEME, Data.P_POISON, Data.P_ARMOR, Data.P_SPEED };

		protected AtkProcTable(Page p, boolean edit, boolean unit) {
			super(p, unit ? UINDS : EINDS, edit, unit);
		}

		@Override
		protected void resized(int x, int y) {
			int h = 0;
			for (int i = 0; i < group.length; i++) {
				int c = i < SEC ? 0 : 400;
				set(group[i].jlm, x, y, c, h, 350, 50);
				h += 50;
				for (int j = 0; j < group[i].list.length; j++) {
					SwingEditor se = (SwingEditor) group[i].list[j];
					if (se.isInvisible())
						continue;
					se.resize(x, y, c, h, 350, 50);
					h += 50;
				}

				if (h > height)
					height = h;
				if (i == SEC - 1)
					h = 0;
			}
		}

	}

	static class MainProcTable extends ProcTable {

		private static final long serialVersionUID = 1L;

		private static final int[] INDS = { Data.P_STRONG, Data.P_LETHAL, Data.P_BURROW, Data.P_REVIVE, Data.P_CRITI,
				Data.P_COUNTER, Data.P_IMUATK, Data.P_DMGCUT, Data.P_DMGCAP, Data.P_IMUKB, Data.P_IMUSTOP,
				Data.P_IMUSLOW, Data.P_IMUWAVE, Data.P_IMUWEAK, Data.P_IMUWARP, Data.P_IMUCURSE,
				Data.P_IMUSEAL, Data.P_IMUMOVING, Data.P_IMUARMOR, Data.P_IMUPOI, Data.P_IMUPOIATK, Data.P_IMUVOLC,
				Data.P_IMUSPEED, Data.P_IMUSUMMON, Data.P_BARRIER, Data.P_DEMONSHIELD, Data.P_DEATHSURGE, Data.P_BSTHUNT
		}; //Procs for units
		private static final int[] EINDS = { Data.P_STRONG, Data.P_LETHAL, Data.P_BURROW, Data.P_REVIVE, Data.P_CRITI,
				Data.P_COUNTER, Data.P_IMUATK, Data.P_DMGCUT, Data.P_DMGCAP, Data.P_IMUKB, Data.P_IMUSTOP,
				Data.P_IMUSLOW, Data.P_IMUWAVE, Data.P_IMUWEAK, Data.P_IMUWARP, Data.P_IMUCURSE,
				Data.P_IMUSEAL, Data.P_IMUMOVING, Data.P_IMUARMOR, Data.P_IMUPOI, Data.P_IMUPOIATK, Data.P_IMUVOLC,
				Data.P_IMUSPEED, Data.P_IMUSUMMON, Data.P_BARRIER, Data.P_DEMONSHIELD, Data.P_DEATHSURGE, Data.P_IMUCANNON
		}; //Procs for enemies

		protected MainProcTable(Page p, boolean edit, boolean unit) {
			super(p, unit ? INDS : EINDS, edit, unit);
		}

		@Override
		protected void resized(int x, int y) {
			int h = 0;
			for (int i = 0; i < inds.length; i++) {
				set(group[i].jlm, x, y, 0, h, 300, 50);
				h += 50;
				for (int j = 0; j < group[i].list.length; j++) {
					SwingEditor se = (SwingEditor) group[i].list[j];
					if (se.isInvisible())
						continue;

					se.resize(x, y, 0, h, 300, 50);
					h += 50;
				}
			}
			setPreferredSize(size(x, y, 300, h).toDimension());
		}

	}

	private static final long serialVersionUID = 1L;

	protected final int[] inds;
	protected final ListJtfPolicy ljp = new ListJtfPolicy();
	protected final SwingEditor.SwingEG[] group;
	private final boolean editable, isUnit;

	protected ProcTable(Page p, int[] ind, boolean edit, boolean unit) {
		super(p);
		editable = edit;
		isUnit = unit;
		inds = ind;
		group = new SwingEditor.SwingEG[ind.length];
		ini();
	}

	@Override
	public Component add(Component comp) {
		Component ret = super.add(comp);
		if (comp instanceof JTF)
			ljp.add((JTF) comp);
		return ret;
	}

	public void updateVisibility() {
		for (int i = 0; i < inds.length; i++)
			group[i].updateVisibility();
	}

	protected void setData(Proc ints) {
		for (int i = 0; i < inds.length; i++)
			group[i].setData(ints.getArr(inds[i]));
	}

	private void ini() {
		Formatter.Context ctx = new Formatter.Context(!isUnit, MainBCU.seconds, new double[]{1.0, 1.0});
		for (int i = 0; i < group.length; i++) {
			group[i] = new SwingEditor.SwingEG(inds[i], editable, () -> getFront().callBack(null), ctx);
			add(group[i].jlm);
			for (int j = 0; j < group[i].list.length; j++) {
				SwingEditor se = (SwingEditor) group[i].list[j];
				se.add(this::add);
			}
		}
		setFocusTraversalPolicy(ljp);
		setFocusCycleRoot(true);
	}

}
