package page.info.edit;

import common.battle.data.CustomEntity;
import common.battle.data.CustomUnit;
import common.pack.PackData.UserPack;
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
	private final JL llr = new JL(1, "t7");
	private final JTF fdr = new JTF();
	private final JTF flv = new JTF();
	private final JTF frs = new JTF();
	private final JTF flr = new JTF();
	private final JBTN vuni = new JBTN(0, "vuni");
	private final JBTN impt = new JBTN(0, "import");
	private final JBTN vene = new JBTN(0, "enemy");
	private final JL ldps = new JL("DPS");
	private final JL vdps = new JL();
	private final UnitEditBox ueb;
	private final Form form;
	private final CustomUnit cu;
	private int lv;

	public FormEditPage(Page p, UserPack pac, Form f) {
		super(p, pac.desc.id, (CustomEntity) f.du, pac.editable, false);
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
		return bas.t().getAtkMulti();
	}

	@Override
	protected double getLvAtk() {
		return form.unit.lv.getMult(lv);
	}

	@Override
	protected double getDef() {
		double mul = form.unit.lv.getMult(lv);
		double def = bas.t().getDefMulti();
		return mul * def;
	}

	@Override
	protected void getInput(JTF jtf, int[] v) {
		if (jtf == fdr)
			cu.price = (int) (v[0] / 1.5);
		if (jtf == flv) {
			if (v[0] <= 0)
				v[0] = 1;
			lv = v[0];
		}
		if (jtf == frs) {
			if (v[0] <= 60)
				v[0] = 60;
			cu.resp = bas.t().getRevRes(v[0]);
		}
		if (jtf == flr) {
			try {
				if (v.length == 1) {
					int firstLayer = v[0];
					if (firstLayer >= 0)
						cu.back = cu.front = firstLayer;
				} else if (v.length >= 2) {
					int firstLayer = v[0];
					int secondLayer = v[1];
					if (firstLayer < 0 || secondLayer < 0) {
						return;
					} else if (firstLayer == secondLayer) {
						cu.back = cu.front = firstLayer;
					} else if (firstLayer < secondLayer) {
						cu.back = firstLayer;
						cu.front = secondLayer;
					}
				}

				flr.setText(interpretLayer(cu.back, cu.front));
			} catch (Exception ignored) { }
		}
	}

	@Override
	protected void ini() {
		llr.setToolTipText("<html>set possible layers of which units can spawn on.</html>");

		set(ldr);
		set(llv);
		set(lrs);
		set(llr);

		set(flv);

		set(fdr);
		set(frs);
		super.ini();

		set(flr);

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
		set(llr, x, y, 550, 900, 100, 50);
		set(flr, x, y, 650, 900, 200, 50);
		set(ueb, x, y, 350, 50, 200, 1200);
		set(ldps, x, y, 900, 1000, 200, 50);
		set(vdps, x, y, 1100, 1000, 200, 50);
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
		flr.setText(interpretLayer(cu.back, cu.front));
		vdps.setText("" + (int) (cu.allAtk() * 30 * getLvAtk() * getAtk() / cu.getItv()));

	}

	private String interpretLayer(int back, int front) {
		if (front == back)
			return front + "";
		else
			return back + "~" + front;
	}
}
