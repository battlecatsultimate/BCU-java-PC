package page.info.edit;

import common.battle.data.CustomEnemy;
import common.battle.data.CustomEntity;
import common.pack.Identifier;
import common.pack.PackData;
import common.util.unit.Enemy;
import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;
import page.info.EnemyInfoPage;
import page.info.filter.EnemyEditBox;

import static utilpc.Interpret.EABIIND;
import static utilpc.Interpret.IMUSFT;

public class EnemyEditPage extends EntityEditPage {

	private static final long serialVersionUID = 1L;

	private final JL lli = new JL(1, "limit");
	private final JL ldr = new JL(1, "drop");
	private final JTF fli = new JTF();
	private final JTF fdr = new JTF();
	private final JTF fsr = new JTF();
	private final JBTN vene = new JBTN(0, "vene");
	private final JBTN stat = new JBTN(0, "stat");
	private final JBTN impt = new JBTN(0, "import");
	private final JBTN vuni = new JBTN(0, "unit");
	private final EnemyEditBox eeb;
	private final Enemy ene;
	private final CustomEnemy ce;

	public EnemyEditPage(Page p, Enemy e, PackData.UserPack pack) {
		super(p, e.id.pack, (CustomEntity) e.de, pack.editable, true);
		ene = e;
		ce = (CustomEnemy) ene.de;
		eeb = new EnemyEditBox(this, editable);
		ini();
		setData((CustomEnemy) e.de);
		resized();
	}

	@Override
	protected void getInput(JTF jtf, int[] v) {
		if (jtf == fli) {
			ce.limit = v[0];
		}
		if (jtf == fdr) {
			ce.drop = (int) Math.round(v[0] / bas.t().getDropMulti());
		}
		if (jtf == fsr) {
			if (v[0] < 0)
				v[0] = 0;
			if (v[0] > 4)
				v[0] = 1;
			ce.star = v[0];
		}
	}

	@Override
	protected void ini() {
		set(lli);
		set(ldr);
		set(fli);
		set(fdr);
		set(fsr);
		super.ini();
		add(eeb);
		add(vene);
		add(stat);
		add(impt);
		add(vuni);
		stat.setLnr(x -> changePanel(new EnemyInfoPage(this, (Enemy) Identifier.get(ce.getPack().getID()))));
		subListener(impt, vuni, vene, ene);
	}

	@Override
	protected void resized(int x, int y) {
		super.resized(x, y);
		set(ldr, x, y, 50, 350, 100, 50);
		set(fdr, x, y, 150, 350, 200, 50);
		set(eeb, x, y, 50, 650, 600, 500);
		set(fsr, x, y, 50, 1150, 200, 50);
		if (editable) {
			set(vene, x, y, 650, 800, 200, 50);
			set(stat, x, y, 850, 800, 200, 50);
			set(lli, x, y, 650, 1000, 200, 50);
			set(fli, x, y, 850, 1000, 200, 50);
		} else {
			set(vene, x, y, 650, 750, 200, 50);
			set(stat, x, y, 850, 750, 200, 50);
			set(lli, x, y, 650, 950, 200, 50);
			set(fli, x, y, 850, 950, 200, 50);
		}
		set(impt, x, y, 250, 1150, 200, 50);
		set(vuni, x, y, 450, 1150, 200, 50);
		eeb.resized();

	}

	@Override
	protected void setData(CustomEntity data) {
		super.setData(data);
		fli.setText(ce.getLim() + "");
		fsr.setText("star: " + ce.star);
		fdr.setText("" + Math.floor(ce.getDrop() * bas.t().getDropMulti()) / 100);
		int imu = 0;
		for (int j : EABIIND)
			if (j > 100) {
				int id = j - 100;
				if (ce.getProc().getArr(id).exists())
					imu |= 1 << id - IMUSFT;
			}
		if (ce.getLim() >= 100)
			fli.setToolTipText("<html>"
				+ "This enemy, if it's a boss, will always stay at least "
				+ (ce.getLim() - 100)
				+ " units from the base<br>once it passes that threshold."
				+ "</html>");
		else
			fli.setToolTipText("<html>"
					+ "This enemy, if it's a boss, will always stay at least "
					+ (100 - ce.getLim())
					+ " units inside the base<br>once it passes that threshold."
					+ "</html>");
		eeb.setData(new int[] { ce.type, ce.abi, imu });
	}

}
