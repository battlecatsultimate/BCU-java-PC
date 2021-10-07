package page.pack;

import common.pack.PackData;
import common.pack.PackData.UserPack;
import common.pack.UserProfile;
import common.util.stage.CharaGroup;
import common.util.unit.Unit;
import page.JBTN;
import page.JL;
import page.Page;
import page.support.UnitLCR;

import javax.swing.*;
import java.util.Collection;

public class CharaGroupPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN cglr = new JBTN(0, "edit");
	private final JList<PackData> jlpk = new JList<>(UserProfile.getAllPacks().toArray(new PackData[0]));
	private final JList<CharaGroup> jlcg = new JList<>();
	private final JList<Unit> jlus = new JList<>();
	private final JScrollPane jsppk = new JScrollPane(jlpk);
	private final JScrollPane jspcg = new JScrollPane(jlcg);
	private final JScrollPane jspus = new JScrollPane(jlus);
	private final JL cgt = new JL(0, "include");

	private boolean changing = false;
	private PackData pack;
	public CharaGroup cg;

	public CharaGroupPage(Page p) {
		super(p);

		ini();
		resized();
	}

	public CharaGroupPage(Page p, CharaGroup chg) {
		this(p);
		cg = chg;
		pack = cg.getCont();
		jlpk.setSelectedValue(pack, true);
		jlcg.setSelectedValue(cg, true);
	}

	public CharaGroupPage(Page p, PackData pac, boolean b) {
		this(p);
		jlpk.setSelectedValue(pac, true);
		jlpk.setEnabled(b);
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jsppk, x, y, 50, 100, 400, 800);
		set(cglr, x, y, 50, 950, 400, 50);
		set(jspcg, x, y, 500, 100, 300, 800);
		set(cgt, x, y, 850, 100, 300, 50);
		set(jspus, x, y, 850, 150, 300, 750);
	}

	private void addListeners() {

		back.addActionListener(arg0 -> changePanel(getFront()));

		cglr.addActionListener(arg0 -> changePanel(new CGLREditPage(getThis(), (UserPack) pack)));

		jlpk.addListSelectionListener(arg0 -> {
			if (changing || jlpk.getValueIsAdjusting())
				return;
			changing = true;
			pack = jlpk.getSelectedValue();
			updatePack();
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
		add(jspcg);
		add(jspus);
		add(cgt);
		add(cglr);
		jlus.setCellRenderer(new UnitLCR());
		updatePack();
		addListeners();
	}

	private void updateCG() {
		jlcg.setSelectedValue(cg, true);
		jlus.setEnabled(cg != null);
		if (cg == null)
			jlus.setListData(new Unit[0]);
		else {
			jlus.setListData(cg.set.toArray(new Unit[0]));
			cgt.setText(0, cg.type == 0 ? "include" : "exclude");
		}
	}

	private void updatePack() {
		jlcg.setEnabled(pack != null);
		cglr.setEnabled(pack != null && pack instanceof UserPack && ((UserPack) pack).editable);
		Collection<CharaGroup> ccg = null;
		if (pack == null)
			jlcg.setListData(new CharaGroup[0]);
		else
			ccg = pack.groups.getList();
		if (ccg != null) {
			jlcg.setListData(ccg.toArray(new CharaGroup[0]));
			if (cg != null && !ccg.contains(cg))
				cg = null;
		} else
			cg = null;
		updateCG();
	}

}
