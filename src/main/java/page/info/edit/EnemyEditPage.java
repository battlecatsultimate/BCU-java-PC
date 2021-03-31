package page.info.edit;

import common.battle.data.CustomEnemy;
import common.battle.data.CustomEntity;
import common.pack.PackData;
import common.util.unit.Enemy;
import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;
import page.info.StageFilterPage;
import page.info.filter.EnemyEditBox;

import static utilpc.Interpret.EABIIND;
import static utilpc.Interpret.IMUSFT;

public class EnemyEditPage extends EntityEditPage {

	private static final long serialVersionUID = 1L;

	private final JL ldr = new JL(1, "drop");
	private final JTF fdr = new JTF();
	private final JTF fsr = new JTF();
	private final JL cdps = new JL();
	private final JL ldps = new JL(1,"DPS");
	private final JBTN vene = new JBTN(0, "vene");
	private final JBTN appr = new JBTN(0, "stage");
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

		if (jtf == fdr) {
			ce.drop = (int) (v[0] / bas.t().getDropMulti());
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

		set(ldr);
		set(fdr);
		set(fsr);
		set(ldps);
		set(cdps);
		super.ini();
		add(eeb);
		add(vene);
		add(appr);
		add(impt);
		add(vuni);
		appr.setLnr(x -> changePanel(new StageFilterPage(getThis(), ene.findApp())));
		subListener(impt, vuni, vene, ene);
	}

	@Override
	protected void resized(int x, int y) {
		super.resized(x, y);
		set(ldr, x, y, 50, 350, 100, 50);
		set(fdr, x, y, 150, 350, 200, 50);
		set(eeb, x, y, 350, 50, 200, 1100);
		set(fsr, x, y, 350, 1200, 200, 50);
		set(vene, x, y, 900, 1200, 200, 50);
		set(ldps, x, y, 900, 1000, 200, 50);
		set(cdps, x, y, 1100, 1000, 200, 50);
		set(appr, x, y, 1100, 1200, 200, 50);
		set(impt, x, y, 1350, 1050, 200, 50);
		set(vuni, x, y, 1350, 1100, 200, 50);
		eeb.resized();

	}

	@Override
	protected void setData(CustomEntity data) {
		super.setData(data);
		fsr.setText("star: " + ce.star);
		fdr.setText("" + (int) (ce.getDrop() * bas.t().getDropMulti()));
		int imu = 0;
		for (int j : EABIIND)
			if (j > 100) {
				int id = j - 100;
				if (ce.getProc().getArr(id).exists())
					imu |= 1 << id - IMUSFT;
			}
		cdps.setText("" + (int) ((30.0/ce.getItv()) * ce.allAtk()));
		eeb.setData(new int[] { ce.type, ce.abi, imu });

	}

}
