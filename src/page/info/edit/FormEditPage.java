package page.info.edit;

import static utilpc.Interpret.ABIIND;
import static utilpc.Interpret.IMUSFT;

import common.battle.data.CustomEntity;
import common.battle.data.CustomUnit;
import common.util.pack.Pack;
import common.util.unit.Form;
import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;
import page.info.filter.UnitEditBox;

public class FormEditPage extends EntityEditPage {

	private static final long serialVersionUID = 1L;

	private final JL llv = new JL(1, "Lv");
	private final JL ldr = new JL(1, "price");
	private final JL lrs = new JL(1, "CD");
	private final JTF fdr = new JTF();
	private final JTF flv = new JTF();
	private final JTF frs = new JTF();
	private final JBTN vuni = new JBTN(0, "vuni");
	private final JBTN impt = new JBTN(0, "import");
	private final JBTN vene = new JBTN(0, "enemy");
	private final JL ldps = new JL("DPS");
	private final JL vdps = new JL();
	private final UnitEditBox ueb;
	private final Form form;
	private final CustomUnit cu;
	private int lv;

	public FormEditPage(Page p, Pack pac, Form f) {
		super(p, pac, (CustomEntity) f.du, pac.editable);
		form = f;
		cu = (CustomUnit) form.du;
		lv = f.unit.getPrefLv();
		ueb = new UnitEditBox(this, pac.editable);
		ini();
		setData((CustomUnit) f.du);
		resized();
	}

	@Override
	protected double getAtk() {
		double mul = form.unit.lv.getMult(lv);
		double atk = bas.t().getAtkMulti();
		return mul * atk;
	}

	@Override
	protected double getDef() {
		double mul = form.unit.lv.getMult(lv);
		double def = bas.t().getDefMulti();
		return mul * def;
	}

	@Override
	protected void getInput(JTF jtf, int v) {
		if (jtf == fdr)
			cu.price = (int) (v / 1.5);
		if (jtf == flv) {
			if (v <= 0)
				v = 1;
			lv = v;
		}
		if (jtf == frs) {
			if (v <= 60)
				v = 60;
			cu.resp = bas.t().getRevRes(v);
		}
	}

	@Override
	protected void ini() {

		set(ldr);
		set(llv);
		set(lrs);

		set(flv);

		set(fdr);
		set(frs);
		super.ini();
		add(ueb);

		add(vuni);
		add(impt);
		add(vene);
		set(ldps);
		set(vdps);

		subListener(vene, impt, vuni, form.unit);
	}

	@Override
	protected void resized(int x, int y) {
		super.resized(x, y);
		set(llv, x, y, 50, 50, 100, 50);
		set(flv, x, y, 150, 50, 200, 50);
		set(ldr, x, y, 50, 350, 100, 50);
		set(fdr, x, y, 150, 350, 200, 50);
		set(lrs, x, y, 50, 400, 100, 50);
		set(frs, x, y, 150, 400, 200, 50);
		set(ueb, x, y, 350, 50, 200, 1200);
		set(ldps, x, y, 900, 1150, 200, 50);
		set(vdps, x, y, 1100, 1150, 200, 50);
		set(vuni, x, y, 1350, 1050, 200, 50);
		set(impt, x, y, 1350, 1100, 200, 50);
		set(vene, x, y, 1350, 1150, 200, 50);

		ueb.resized();

	}

	@Override
	protected void setData(CustomEntity data) {
		super.setData(data);
		flv.setText("" + lv);
		frs.setText("" + bas.t().getFinRes(cu.getRespawn()));
		fdr.setText("" + (int) (cu.getPrice() * 1.5));
		int imu = 0;
		for (int i = 0; i < ABIIND.length; i++) {
			int id = ABIIND[i] - 100;
			if (cu.getProc(id)[0] == 100)
				imu |= 1 << id - IMUSFT;
		}
		ueb.setData(new int[] { cu.type, cu.abi, imu });
		vdps.setText("" + (int) (cu.allAtk() * 30 * getAtk() / cu.getItv()));

	}

}
