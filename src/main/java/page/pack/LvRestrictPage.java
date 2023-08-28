package page.pack;

import common.pack.PackData.UserPack;
import common.pack.UserProfile;
import common.util.Data;
import common.util.stage.CharaGroup;
import common.util.stage.LvRestrict;
import common.util.unit.Unit;
import page.JBTN;
import page.Page;
import page.support.UnitLCR;

import javax.swing.*;
import java.util.Collection;
import java.util.Set;

import static utilpc.Interpret.RARITY;

public class LvRestrictPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN cglr = new JBTN(0, "edit");
	private final JList<UserPack> jlpk = new JList<>(UserProfile.getUserPacks().toArray(new UserPack[0]));
	private final JList<LvRestrict> jllr = new JList<>();
	private final JList<CharaGroup> jlcg = new JList<>();
	private final JList<Unit> jlus = new JList<>();
	private final JScrollPane jsppk = new JScrollPane(jlpk);
	private final JScrollPane jsplr = new JScrollPane(jllr);
	private final JScrollPane jspcg = new JScrollPane(jlcg);
	private final JScrollPane jspus = new JScrollPane(jlus);

	private final JLabel lsb = new JLabel();
	private final JLabel lal = new JLabel();
	private final JLabel[] lra = new JLabel[Data.RARITY_TOT];

	private boolean changing = false;
	private UserPack pack;
	public LvRestrict lr;
	public CharaGroup cg;

	public LvRestrictPage(Page p) {
		super(p);

		ini();
	}

	public LvRestrictPage(Page p, LvRestrict lvr) {
		this(p);
		lr = lvr;
		pack = (UserPack) lr.getCont();
		jlpk.setSelectedValue(pack, true);
		jllr.setSelectedValue(lr, true);
	}

	public LvRestrictPage(Page p, UserPack pac, boolean b) {
		this(p);
		jlpk.setSelectedValue(pac, true);
		jlpk.setEnabled(b);
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);

		set(back, x, y, 0, 0, 200, 50);
		set(jsppk, x, y, 50, 100, 400, 800);
		set(cglr, x, y, 50, 950, 400, 50);
		set(jsplr, x, y, 500, 100, 300, 800);
		set(jspcg, x, y, 850, 100, 300, 800);
		set(jspus, x, y, 1200, 100, 300, 800);
		set(lal, x, y, 1550, 100, 400, 50);
		set(lsb, x, y, 1550, 550, 400, 50);

		for (int i = 0; i < lra.length; i++)
			set(lra[i], x, y, 1550, 200 + 50 * i, 400, 50);
	}

	private void addListeners() {

		back.addActionListener(arg0 -> changePanel(getFront()));

		cglr.addActionListener(arg0 -> changePanel(new CGLREditPage(getThis(), pack)));

		jlpk.addListSelectionListener(arg0 -> {
			if (changing || jlpk.getValueIsAdjusting())
				return;
			changing = true;
			pack = jlpk.getSelectedValue();
			updatePack();
			changing = false;
		});

		jllr.addListSelectionListener(arg0 -> {
			if (changing || jllr.getValueIsAdjusting())
				return;
			changing = true;
			lr = jllr.getSelectedValue();
			updateLR();
			changing = false;
		});

		jlcg.addListSelectionListener(arg0 -> {
			if (changing || jlcg.getValueIsAdjusting())
				return;
			changing = true;
			cg = jlcg.getSelectedValue();
			updateCG();
			changing = false;
		});
	}

	private void ini() {
		add(back);
		add(jsppk);
		add(jsplr);
		add(jspcg);
		add(jspus);
		add(cglr);
		add(lsb);
		add(lal);
		lsb.setBorder(BorderFactory.createEtchedBorder());
		lal.setBorder(BorderFactory.createEtchedBorder());
		for (int i = 0; i < lra.length; i++) {
			add(lra[i] = new JLabel());
			lra[i].setBorder(BorderFactory.createEtchedBorder());
		}
		jlus.setCellRenderer(new UnitLCR());
		updatePack();
		addListeners();
	}

	private void set(JLabel jl, String str, int[] lvs) {
		if (lvs != null) {
			str += "Lv." + lvs[0] + " {";

			StringBuilder strBuilder = new StringBuilder(str);

			for (int i = 1; i < 5; i++)
				strBuilder.append(lvs[i]).append(",");

			str = strBuilder.toString();
			str += lvs[5] + "}";
		}
		jl.setText(str);
	}

	private void updateCG() {
		jlus.setEnabled(lr != null);
		if (cg == null) {
			set(lsb, "group: ", null);
			jlus.setListData(new Unit[0]);
		} else {
			set(lsb, "group: ", lr.res.get(cg));
			jlus.setListData(cg.set.toArray(new Unit[0]));
		}
	}

	private void updateLR() {
		jllr.setSelectedValue(lr, true);
		jlus.setEnabled(lr != null);
		if (lr == null) {
			set(lal, "all: ", null);
			for (int i = 0; i < lra.length; i++)
				set(lra[i], RARITY[i] + ": ", null);

			jlcg.setListData(new CharaGroup[0]);
			cg = null;
		} else {
			set(lal, "all: ", lr.all);
			for (int i = 0; i < lra.length; i++)
				set(lra[i], RARITY[i] + ": ", lr.rares[i]);

			Set<CharaGroup> scg = lr.res.keySet();
			if (cg != null && !scg.contains(cg))
				cg = null;
			jlcg.setListData(scg.toArray(new CharaGroup[0]));
		}
		updateCG();
	}

	private void updatePack() {
		jllr.setEnabled(pack != null);
		cglr.setEnabled(pack != null && pack.editable);
		Collection<LvRestrict> clr = null;
		if (pack == null)
			jllr.setListData(new LvRestrict[0]);
		else
			clr = pack.lvrs.getList();
		if (clr != null) {
			jllr.setListData(clr.toArray(new LvRestrict[0]));
			if (lr != null && !clr.contains(lr))
				lr = null;
		} else
			lr = null;
		updateLR();
	}

}
