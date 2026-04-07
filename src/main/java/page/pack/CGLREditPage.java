package page.pack;

import common.CommonStatic;
import common.pack.FixIndexList.FixIndexMap;
import common.pack.PackData.UserPack;
import common.util.Data;
import common.util.stage.CharaGroup;
import common.util.stage.LvRestrict;
import common.util.unit.Form;
import common.util.unit.Unit;
import org.jcodec.common.tools.MathUtil;
import page.*;
import page.info.filter.UnitFindPage;
import page.support.UnitLCR;
import utilpc.Interpret;
import utilpc.UtilPC;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

public class CGLREditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(MainLocale.PAGE, "back");
	private final JList<CharaGroup> jlcg = new JList<>();
	private final JList<CharaGroup> jlsb = new JList<>();
	private final JList<LvRestrict> jllr = new JList<>();
	private final JList<Unit> jlus = new JList<>();
	private final JList<Unit> jlua = new JList<>();
	private final JScrollPane jspcg = new JScrollPane(jlcg);
	private final JScrollPane jspsb = new JScrollPane(jlsb);
	private final JScrollPane jsplr = new JScrollPane(jllr);
	private final JScrollPane jspus = new JScrollPane(jlus);
	private final JScrollPane jspua = new JScrollPane(jlua);

	private final JBTN cgt = new JBTN(MainLocale.PAGE, "include");

	private final JBTN addcg = new JBTN(MainLocale.PAGE, "add");
	private final JBTN remcg = new JBTN(MainLocale.PAGE, "rem");
	private final JBTN addus = new JBTN(MainLocale.PAGE, "add");
	private final JBTN remus = new JBTN(MainLocale.PAGE, "rem");
	private final JBTN addlr = new JBTN(MainLocale.PAGE, "add");
	private final JBTN remlr = new JBTN(MainLocale.PAGE, "rem");
	private final JBTN addsb = new JBTN(MainLocale.PAGE, "add");
	private final JBTN remsb = new JBTN(MainLocale.PAGE, "rem");

	private final JTF jtfal = new JTF();
	private final JTF[] jtfra = new JTF[Data.RARITY_TOT];
	private final JTF[] jtfor = new JTF[Data.RARITY_TOT];
	private final JTF jcglv = new JTF();
	private final JTF jcgor = new JTF();
	private final JTF jtfna = new JTF();
	private final JTF jtflr = new JTF();

	private final JL[] jlra = new JL[Data.RARITY_TOT];
	private final JL jlbg = new JL(0, "group");
	private final JL jlba = new JL(0, "allunit");

	private final JBTN vuif = new JBTN(0, "vuif");

	private final UserPack pack;
	private final FixIndexMap<CharaGroup> lcg;
	private final FixIndexMap<LvRestrict> llr;

	private boolean changing = false;
	private CharaGroup cg;
	private CharaGroup sb;
	private LvRestrict lr;
	private UnitFindPage ufp;

	protected CGLREditPage(Page p, UserPack pac) {
		super(p);

		pack = pac;
		lcg = pack.groups;
		llr = pack.lvrs;
		ini();
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	protected void renew() {
		if (ufp != null && ufp.getList() != null) {
			changing = true;
			List<Unit> list = new ArrayList<>();
			for (Form f : ufp.getList())
				if (!list.contains(f.unit))
					list.add(f.unit);
			jlua.setListData(list.toArray(new Unit[0]));
			jlua.clearSelection();
			if (!list.isEmpty())
				jlua.setSelectedIndex(0);
			changing = false;
		}
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);

		set(back, x, y, 0, 0, 200, 50);
		set(jtfna, x, y, 50, 150, 300, 50);
		set(jspcg, x, y, 50, 200, 300, 800);
		set(addcg, x, y, 50, 1000, 150, 50);
		set(remcg, x, y, 200, 1000, 150, 50);

		set(cgt, x, y, 350, 150, 300, 50);
		set(jspus, x, y, 350, 200, 300, 800);
		set(remus, x, y, 400, 1000, 200, 50);

		set(vuif, x, y, 650, 150, 300, 50);
		set(jspua, x, y, 650, 200, 300, 800);
		set(addus, x, y, 700, 1000, 200, 50);

		set(jtflr, x, y, 1000, 150, 300, 50);
		set(jsplr, x, y, 1000, 200, 300, 800);
		set(addlr, x, y, 1000, 1000, 150, 50);
		set(remlr, x, y, 1150, 1000, 150, 50);

		set(jspsb, x, y, 1300, 200, 300, 400);
		set(addsb, x, y, 1300, 600, 150, 50);
		set(remsb, x, y, 1450, 600, 150, 50);

		set(jlba, x, y, 1650, 200, 150, 50);
		set(jtfal, x, y, 1800, 200, 400, 50);

		for (int i = 0; i < jtfra.length; i++) {
			set(jlra[i], x, y, 1650, 300 + 100 * i, 150, 50);
			set(jtfra[i], x, y, 1800, 300 + 100 * i, 400, 50);
			set(jtfor[i], x, y, 1800, 350 + 100 * i, 400, 50);
		}

		set(jlbg, x, y, 1650, 950, 150, 50);
		set(jcglv, x, y, 1800, 950, 400, 50);
		set(jcgor, x, y, 1800, 1000, 400, 50);

	}

	private void addListeners() {

		back.addActionListener(arg0 -> changePanel(getFront()));

		vuif.addActionListener(arg0 -> {
			if (ufp == null)
				ufp = new UnitFindPage(getThis(), pack.getSID(), pack.desc.dependency);
			changePanel(ufp);
		});

	}

	private void addListeners$CG() {

		addcg.addActionListener(arg0 -> {
			changing = true;
			cg = new CharaGroup(pack.getNextID(CharaGroup.class));
			lcg.add(cg);
			updateCGL();
			jlcg.setSelectedValue(cg, true);
			changing = false;
		});

		remcg.addActionListener(arg0 -> {
			if (cg == null)
				return;
			changing = true;
			List<CharaGroup> list = lcg.getList();
			int ind = list.indexOf(cg) - 1;
			if (ind < 0 && list.size() > 1)
				ind = 0;
			list.remove(cg);
			lcg.remove(cg);
			if (ind >= 0)
				cg = list.get(ind);
			else
				cg = null;
			updateCGL();
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

		addus.addActionListener(arg0 -> {
			List<Unit> u = jlua.getSelectedValuesList();
			if (cg == null || u.isEmpty())
				return;
			changing = true;
			cg.set.addAll(u);
			updateCG();
			jlus.setSelectedValue(u.get(0), true);
			changing = false;
		});

		remus.addActionListener(arg0 -> {
			Unit u = jlus.getSelectedValue();
			if (cg == null || u == null)
				return;
			changing = true;
			List<Unit> list = new ArrayList<>(cg.set);
			int ind = list.indexOf(u) - 1;
			if (ind < 0 && list.size() > 1)
				ind = 0;
			cg.set.remove(u);
			updateCG();
			jlus.setSelectedIndex(ind);
			changing = false;
		});

		cgt.addActionListener(arg0 -> {
			if (cg == null)
				return;
			cg.type = 2 - cg.type;
			cgt.setText(0, cg.type == 0 ? "include" : "exclude");
		});

		jtfna.setLnr(x -> {
			String str = jtfna.getText();

			if (cg.name.equals(str))
				return;

			cg.name = str;

			jlcg.revalidate();
			jlcg.repaint();
		});
	}

	private void addListeners$LR() {

		addlr.addActionListener(arg0 -> {
			changing = true;
			lr = new LvRestrict(pack.getNextID(LvRestrict.class));
			llr.add(lr);
			updateLRL();
			jllr.setSelectedValue(lr, true);
			changing = false;
		});

		remlr.addActionListener(arg0 -> {
			if (lr == null)
				return;
			changing = true;
			List<LvRestrict> list = llr.getList();
			int ind = list.indexOf(lr) - 1;
			if (ind < 0 && list.size() > 1)
				ind = 0;
			list.remove(lr);
			llr.remove(lr);
			if (ind >= 0)
				lr = list.get(ind);
			else
				lr = null;
			updateLRL();
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

		addsb.addActionListener(arg0 -> {
			changing = true;
			int[] lv = new int[] { 120, 10, 10, 10, 10, 10 };
			lr.groups.put(cg, new LvRestrict.GroupRestrict(lv, -1));
			sb = cg;
			updateLR();
			changing = false;
		});

		remsb.addActionListener(arg0 -> {
			if (sb == null)
				return;
			changing = true;
			int ind = jlsb.getSelectedIndex();
			lr.groups.remove(sb);
			updateLR();
			if (lr.groups.size() >= ind)
				ind = lr.groups.size() - 1;
			jlsb.setSelectedIndex(ind);
			sb = jlsb.getSelectedValue();
			updateSB();
			changing = false;
		});

		jlsb.addListSelectionListener(arg0 -> {
			if (changing || jlsb.getValueIsAdjusting())
				return;
			changing = true;
			sb = jlsb.getSelectedValue();
			updateSB();
			changing = false;
		});

		jtflr.setLnr(x -> {
			String str = jtflr.getText();

			if (lr.name.equals(str))
				return;

			lr.name = str;

			jllr.revalidate();
			jllr.repaint();
		});
	}

	private void ini() {
		add(back);
		add(jspcg);
		add(addcg);
		add(remcg);
		add(jspus);
		add(addus);
		add(remus);
		add(jsplr);
		add(addlr);
		add(remlr);
		add(jspsb);
		add(addsb);
		add(remsb);
		add(vuif);
		add(jspua);
		add(cgt);
		set(jcglv);
//		set(jcgor);
		set(jtfal);
		set(jtfna);
		set(jtflr);
		add(jlba);
		add(jlbg);
		for (int i = 0; i < Data.RARITY_TOT; i++) {
			add(jlra[i] = new JL(Interpret.RARITY[i]));
			set(jtfra[i] = new JTF());
			jtfor[i] = new JTF();
//			set(jtfor[i] = new JTF());
		}
		jlus.setCellRenderer(new UnitLCR());
		jlua.setCellRenderer(new UnitLCR());
		updateCGL();
		updateLRL();
		addListeners();
		addListeners$CG();
		addListeners$LR();
		ufp = new UnitFindPage(getThis(), pack.getSID(), pack.desc.dependency);
	}

	private void put(int[] tar, int[] val) {
		System.arraycopy(val, 0, tar, 0, Math.min(tar.length, val.length));
	}

	private void set(JTF jtf) {
		add(jtf);

		jtf.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent fe) {
				int[] inp = CommonStatic.parseIntsN(jtf.getText());
				if (jtf == jcgor)
					lr.groups.get(sb).orb = inp.length == 0 ? -1 : MathUtil.clip(inp[0], -1, 2);
				for (int i = 0; i < jtfor.length; i++)
					if (jtf == jtfor[i])
						lr.orb[i] = inp.length == 0 ? -1 : MathUtil.clip(inp[0], -1, 2);
				for (int i = 0; i < inp.length; i++)
					if (inp[i] < 0)
						inp[i] = 0;
				if (jtf == jtfal)
					put(lr.all, inp);
				if (jtf == jcglv)
					put(lr.groups.get(sb).lv, inp);
				for (int i = 0; i < jtfra.length; i++)
					if (jtf == jtfra[i])
						put(lr.rares[i], inp);
				updateSB();
			}

		});

	}

	private void setLv(JTF jtf, int[] lvs) {
		jtf.setText(UtilPC.lvText(lvs));
	}

	private void updateCG() {
		jlus.setEnabled(cg != null);
		addus.setEnabled(cg != null);
		remus.setEnabled(cg != null);
		remcg.setEnabled(cg != null && !cg.used());
		cgt.setEnabled(cg != null);
		jtfna.setEnabled(cg != null);
		cgt.setText("");
		jtfna.setText("");
		addsb.setEnabled(lr != null && cg != null && !lr.groups.containsKey(cg));

		if (cg == null)
			jlus.setListData(new Unit[0]);
		else {
			jlus.setListData(cg.set.toArray(new Unit[0]));
			cgt.setText(0, cg.type == 0 ? "include" : "exclude");
			jtfna.setText(cg.name);
		}
	}

	private void updateCGL() {
		jlcg.setListData(lcg.toArray());
		jlcg.setSelectedValue(cg, true);
		updateCG();
	}

	private void updateLR() {
		remlr.setEnabled(lr != null && !lr.used());
		jlsb.setEnabled(lr != null);
		addsb.setEnabled(lr != null && cg != null && !lr.groups.containsKey(cg));
		jtflr.setEnabled(lr != null);
		jtflr.setText("");
		if (lr == null)
			jlsb.setListData(new CharaGroup[0]);
		else {
			jlsb.setListData(lr.groups.keySet().toArray(new CharaGroup[0]));
			jtflr.setText(lr.name);
		}
		if (lr == null || sb == null || !lr.groups.containsKey(sb))
			sb = null;
		jlsb.setSelectedValue(sb, true);
		jtfal.setEnabled(lr != null);
		for (JTF jtf : jtfra)
			jtf.setEnabled(lr != null);
		updateSB();
	}

	private void updateLRL() {
		jllr.setListData(llr.toArray());
		jllr.setSelectedValue(lr, true);
		updateLR();
	}

	private void updateSB() {
		jcglv.setEnabled(sb != null);
		jcgor.setEnabled(sb != null);

		if (lr != null) {
			setLv(jtfal, lr.all);
			for (int i = 0; i < jtfra.length; i++) {
				setLv(jtfra[i], lr.rares[i]);
				jtfor[i].setText("Max orb: " + (lr.orb[i] == -1 ? "--" : lr.orb[i]));
			}
		} else {
			setLv(jtfal, null);
			for (int i = 0; i < jtfra.length; i++) {
				setLv(jtfra[i], null);
				jtfor[i].setText(null);
			}
		}

		if (lr == null || sb == null) {
			setLv(jcglv, null);
			jcgor.setText(null);
		} else {
			LvRestrict.GroupRestrict gr = lr.groups.get(sb);
			setLv(jcglv, gr.lv);
			jcgor.setText("Max orb: " + (gr.orb == -1 ? "--" : gr.orb));
		}
		remsb.setEnabled(sb != null);
	}

}
