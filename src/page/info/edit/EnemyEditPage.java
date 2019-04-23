package page.info.edit;

import static util.Interpret.EABIIND;
import static util.Interpret.IMUSFT;

import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;
import page.info.StageFilterPage;
import page.info.filter.EnemyEditBox;
import util.entity.data.CustomEnemy;
import util.entity.data.CustomEntity;
import util.unit.Enemy;

public class EnemyEditPage extends EntityEditPage {

	private static final long serialVersionUID = 1L;

	private final JL ldr = new JL(1, "drop");
	private final JTF fdr = new JTF();
	private final JTF fsr = new JTF();
	private final JBTN vene = new JBTN(0, "vene");
	private final JBTN appr = new JBTN(0, "stage");
	private final JBTN impt = new JBTN(0, "import");
	private final JBTN vuni = new JBTN(0, "unit");
	private final EnemyEditBox eeb;
	private final Enemy ene;
	private final CustomEnemy ce;

	public EnemyEditPage(Page p, Enemy e, boolean edit) {
		super(p, (CustomEntity) e.de, edit);
		ene = e;
		ce = (CustomEnemy) ene.de;
		eeb = new EnemyEditBox(this, edit);
		ini();
		setData((CustomEnemy) e.de);
		resized();
	}

	@Override
	protected void getInput(JTF jtf, int v) {

		if (jtf == fdr) {
			int act = (int) (v / bas.t().getDropMulti());
			ce.drop = act;
		}
		if (jtf == fsr) {
			if (v < 0)
				v = 0;
			if (v > 4)
				v = 1;
			ce.star = v;
		}
	}

	@Override
	protected void ini() {

		set(ldr);
		set(fdr);
		set(fsr);
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
		set(eeb, x, y, 400, 50, 200, 1100);
		set(fsr, x, y, 400, 1200, 200, 50);
		set(vene, x, y, 900, 1200, 200, 50);
		set(appr, x, y, 1100, 1200, 200, 50);
		set(impt, x, y, 1350, 1050, 200, 50);
		set(vuni, x, y, 1350, 1100, 200, 50);
		eeb.resized();

	}

	private void setData(CustomEnemy data) {
		super.setData(data);
		fsr.setText("star: " + ce.star);
		fdr.setText("" + (int) (ce.getDrop() * bas.t().getDropMulti()));
		int imu = 0;
		for (int i = 0; i < EABIIND.length; i++)
			if (EABIIND[i] > 100) {
				int id = EABIIND[i] - 100;
				if (ce.getProc(id)[0] == 100)
					imu |= 1 << id - IMUSFT;
			}
		eeb.setData(new int[] { ce.type, ce.abi, imu });

	}

}
