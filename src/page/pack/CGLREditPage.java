package page.pack;

import static utilpc.Interpret.RARITY;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import common.util.Data;
import common.util.pack.Pack;
import common.util.stage.CharaGroup;
import common.util.stage.LvRestrict;
import common.util.system.FixIndexList;
import common.util.unit.Form;
import common.util.unit.Unit;
import common.CommonStatic;
import page.JBTN;
import page.JTF;
import page.Page;
import page.info.filter.UnitFindPage;
import page.support.UnitLCR;

public class CGLREditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
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

	private final JBTN cgt = new JBTN(0, "include");

	private final JBTN addcg = new JBTN(0, "add");
	private final JBTN remcg = new JBTN(0, "rem");
	private final JBTN addus = new JBTN(0, "add");
	private final JBTN remus = new JBTN(0, "rem");
	private final JBTN addlr = new JBTN(0, "add");
	private final JBTN remlr = new JBTN(0, "rem");
	private final JBTN addsb = new JBTN(0, "add");
	private final JBTN remsb = new JBTN(0, "rem");

	private final JTF jtfsb = new JTF();
	private final JTF jtfal = new JTF();
	private final JTF[] jtfra = new JTF[Data.RARITY_TOT];
	private final JTF jtfna = new JTF();
	private final JTF jtflr = new JTF();

	private final JBTN vuif = new JBTN(0, "vuif");

	private final Pack pack;
	private final FixIndexList<CharaGroup> lcg;
	private final FixIndexList<LvRestrict> llr;

	private boolean changing = false;
	private CharaGroup cg, sb;
	private LvRestrict lr;
	private UnitFindPage ufp;

	protected CGLREditPage(Page p, Pack pac) {
		super(p);

		pack = pac;
		lcg = pack.mc.groups;
		llr = pack.mc.lvrs;
		ini();
		resized();
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
			if (list.size() > 0)
				jlua.setSelectedIndex(0);
			changing = false;
		}
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jspcg, x, y, 50, 100, 300, 800);
		set(addcg, x, y, 50, 950, 150, 50);
		set(remcg, x, y, 200, 950, 150, 50);
		set(cgt, x, y, 400, 100, 300, 50);
		set(jspus, x, y, 400, 200, 300, 700);
		set(addus, x, y, 400, 950, 150, 50);
		set(remus, x, y, 550, 950, 150, 50);
		set(vuif, x, y, 750, 100, 300, 50);
		set(jspua, x, y, 750, 200, 300, 700);
		set(jsplr, x, y, 1100, 100, 300, 800);
		set(addlr, x, y, 1100, 950, 150, 50);
		set(remlr, x, y, 1250, 950, 150, 50);
		set(jspsb, x, y, 1450, 100, 300, 800);
		set(addsb, x, y, 1450, 950, 150, 50);
		set(remsb, x, y, 1600, 950, 150, 50);
		set(jtfal, x, y, 1800, 100, 400, 50);
		set(jtfsb, x, y, 1800, 550, 400, 50);
		set(jtfna, x, y, 50, 900, 300, 50);
		set(jtflr, x, y, 1100, 900, 300, 50);
		for (int i = 0; i < jtfra.length; i++)
			set(jtfra[i], x, y, 1800, 200 + 50 * i, 400, 50);

	}

	private void addListeners() {

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		vuif.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (ufp == null)
					ufp = new UnitFindPage(getThis(), pack);
				changePanel(ufp);
			}
		});

	}

	private void addListeners$CG() {

		addcg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				int ind = lcg.nextInd();
				cg = new CharaGroup(pack, ind, 0);
				lcg.add(cg);
				updateCGL();
				jlcg.setSelectedValue(cg, true);
				changing = false;
			}
		});

		remcg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
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
			}
		});

		jlcg.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || jlcg.getValueIsAdjusting())
					return;
				changing = true;
				cg = jlcg.getSelectedValue();
				updateCG();
				changing = false;
			}

		});

		addus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<Unit> u = jlua.getSelectedValuesList();
				if (cg == null || u.size() == 0)
					return;
				changing = true;
				cg.set.addAll(u);
				updateCG();
				jlus.setSelectedValue(u.get(0), true);
				changing = false;
			}
		});

		remus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Unit u = jlus.getSelectedValue();
				if (cg == null || u == null)
					return;
				changing = true;
				List<Unit> list = new ArrayList<>();
				list.addAll(cg.set);
				int ind = list.indexOf(u) - 1;
				if (ind < 0 && list.size() > 1)
					ind = 0;
				cg.set.remove(u);
				updateCG();
				jlus.setSelectedIndex(ind);
				changing = false;
			}
		});

		cgt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cg == null)
					return;
				cg.type = 2 - cg.type;
				cgt.setText(0, cg.type == 0 ? "include" : "exclude");
			}

		});

		jtfna.setLnr(x -> {
			String str = jtfna.getText();
			if (cg.name.equals(str))
				return;
			cg.name = str;
		});
	}

	private void addListeners$LR() {

		addlr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				int ind = llr.nextInd();
				lr = new LvRestrict(pack, ind);
				llr.add(lr);
				updateLRL();
				jllr.setSelectedValue(lr, true);
				changing = false;
			}
		});

		remlr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
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
			}
		});

		jllr.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || jllr.getValueIsAdjusting())
					return;
				changing = true;
				lr = jllr.getSelectedValue();
				updateLR();
				changing = false;
			}

		});

		addsb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				int[] lv = new int[] { 120, 10, 10, 10, 10, 10 };
				lr.res.put(cg, lv);
				sb = cg;
				updateLR();
				changing = false;
			}
		});

		remsb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (sb == null)
					return;
				changing = true;
				int ind = jlsb.getSelectedIndex();
				lr.res.remove(sb);
				updateLR();
				if (lr.res.size() >= ind)
					ind = lr.res.size() - 1;
				jlsb.setSelectedIndex(ind);
				sb = jlsb.getSelectedValue();
				updateSB();
				changing = false;
			}
		});

		jlsb.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || jlsb.getValueIsAdjusting())
					return;
				changing = true;
				sb = jlsb.getSelectedValue();
				updateSB();
				changing = false;
			}

		});

		jtflr.setLnr(x -> {
			String str = jtflr.getText();
			if (lr.name.equals(str))
				return;
			lr.name = str;
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
		set(jtfsb);
		set(jtfal);
		set(jtfna);
		set(jtflr);
		for (int i = 0; i < jtfra.length; i++)
			set(jtfra[i] = new JTF());
		jlus.setCellRenderer(new UnitLCR());
		jlua.setCellRenderer(new UnitLCR());
		updateCGL();
		updateLRL();
		addListeners();
		addListeners$CG();
		addListeners$LR();
	}

	private void put(int[] tar, int[] val) {
		for (int i = 0; i < Math.min(tar.length, val.length); i++)
			tar[i] = val[i];
	}

	private void set(JTF jtf) {
		add(jtf);

		jtf.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent fe) {
				int[] inp = CommonStatic.parseIntsN(jtf.getText());
				for (int i = 0; i < inp.length; i++)
					if (inp[i] < 0)
						inp[i] = 0;
				if (jtf == jtfal)
					put(lr.all, inp);
				if (jtf == jtfsb)
					put(lr.res.get(sb), inp);
				for (int i = 0; i < jtfra.length; i++)
					if (jtf == jtfra[i])
						put(lr.rares[i], inp);
				updateSB();
			}

		});

	}

	private void set(JTF jtf, String str, int[] lvs) {
		if (lvs != null) {
			str += "Lv." + lvs[0] + " {";
			for (int i = 1; i < 5; i++)
				str += lvs[i] + ",";
			str += lvs[5] + "}";
		}
		jtf.setText(str);
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
		addsb.setEnabled(lr != null && cg != null && !lr.res.containsKey(cg));

		if (cg == null)
			jlus.setListData(new Unit[0]);
		else {
			jlus.setListData(cg.set.toArray(new Unit[0]));
			cgt.setText(0, cg.type == 0 ? "include" : "exclude");
			jtfna.setText(cg.name);
		}
	}

	private void updateCGL() {
		jlcg.setListData(lcg.getList().toArray(new CharaGroup[0]));
		jlcg.setSelectedValue(cg, true);
		updateCG();
	}

	private void updateLR() {
		remlr.setEnabled(lr != null && !lr.used());
		jlsb.setEnabled(lr != null);
		addsb.setEnabled(lr != null && cg != null && !lr.res.containsKey(cg));
		jtflr.setEnabled(lr != null);
		jtflr.setText("");
		if (lr == null)
			jlsb.setListData(new CharaGroup[0]);
		else {
			jlsb.setListData(lr.res.keySet().toArray(new CharaGroup[0]));
			jtflr.setText(lr.name);
		}
		if (lr == null || sb == null || !lr.res.containsKey(sb))
			sb = null;
		jlsb.setSelectedValue(sb, true);
		jtfal.setEnabled(lr != null);
		for (JTF jtf : jtfra)
			jtf.setEnabled(lr != null);
		updateSB();
	}

	private void updateLRL() {
		jllr.setListData(llr.getList().toArray(new LvRestrict[0]));
		jllr.setSelectedValue(lr, true);
		updateLR();
	}

	private void updateSB() {
		jtfsb.setEnabled(sb != null);

		if (lr != null) {
			set(jtfal, "all: ", lr.all);
			for (int i = 0; i < jtfra.length; i++)
				set(jtfra[i], RARITY[i] + ": ", lr.rares[i]);
		} else {
			set(jtfal, "all: ", null);
			for (int i = 0; i < jtfra.length; i++)
				set(jtfra[i], RARITY[i] + ": ", null);
		}

		if (lr == null || sb == null)
			set(jtfsb, "group: ", null);
		else
			set(jtfsb, "group: ", lr.res.get(sb));
		remsb.setEnabled(sb != null);
	}

}
