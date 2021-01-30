package page.info.edit;

import common.pack.PackData.UserPack;
import common.pack.UserProfile;
import common.util.stage.MapColc;
import common.util.stage.Stage;
import common.util.stage.StageMap;
import common.util.unit.AbEnemy;
import common.util.unit.EneRand;
import common.util.unit.Enemy;
import page.JBTN;
import page.Page;
import page.battle.BattleSetupPage;
import page.battle.StRecdPage;
import page.info.filter.EnemyFindPage;
import page.support.AnimLCR;
import page.support.RLFIM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class StageEditPage extends Page {

	private static final long serialVersionUID = 1L;

	public static void redefine() {
		StageEditTable.redefine();
		LimitTable.redefine();
		SCGroupEditTable.redefine();
	}

	private final JBTN back = new JBTN(0, "back");
	private final JBTN strt = new JBTN(0, "start");
	private final JBTN veif = new JBTN(0, "veif");
	private final JBTN cpsm = new JBTN(0, "cpsm");
	private final JBTN cpst = new JBTN(0, "cpst");
	private final JBTN ptsm = new JBTN(0, "ptsm");
	private final JBTN ptst = new JBTN(0, "ptst");
	private final JBTN rmsm = new JBTN(0, "rmsm");
	private final JBTN rmst = new JBTN(0, "rmst");
	private final JBTN recd = new JBTN(0, "replay");
	private final JBTN elim = new JBTN(0, "limit");
	private final StageEditTable jt;
	private final JScrollPane jspjt;
	private final RLFIM<StageMap> jlsm = new RLFIM<>(() -> this.changing = true, () -> changing = false, this::setAA,
			StageMap::new);
	private final JScrollPane jspsm = new JScrollPane(jlsm);
	private final RLFIM<Stage> jlst = new RLFIM<>(() -> this.changing = true, () -> changing = false, this::setAB,
			Stage::new);
	private final JScrollPane jspst = new JScrollPane(jlst);
	private final JList<StageMap> lpsm = new JList<>(Stage.CLIPMC.maps.toArray());
	private final JScrollPane jlpsm = new JScrollPane(lpsm);
	private final JList<Stage> lpst = new JList<>();
	private final JScrollPane jlpst = new JScrollPane(lpst);
	private final JBTN adds = new JBTN(0, "add");
	private final JBTN rems = new JBTN(0, "rem");
	private final JBTN addl = new JBTN(0, "addl");
	private final JBTN reml = new JBTN(0, "reml");
	private final JBTN advs = new JBTN(0, "advance");
	private final JList<AbEnemy> jle = new JList<>();
	private final JScrollPane jspe = new JScrollPane(jle);

	private final HeadEditTable info;

	private final MapColc mc;
	private final String pack;

	private final EnemyFindPage efp;

	private boolean changing = false;
	private Stage stage;

	public StageEditPage(Page p, MapColc map, UserPack pac) {
		super(p);
		mc = map;
		pack = pac.desc.id;
		jt = new StageEditTable(this, pac);
		jspjt = new JScrollPane(jt);
		info = new HeadEditTable(this, pac);
		jlsm.setListData(mc, mc.maps);
		jle.setListData(UserProfile.getAll(pack, Enemy.class).toArray(new Enemy[0]));
		efp = new EnemyFindPage(getThis(), pac.desc.id, pac.desc.dependency.toArray(new String[0]));
		ini();
	}

	@Override
	protected void mouseClicked(MouseEvent e) {
		if (e.getSource() == jt && !e.isControlDown())
			jt.clicked(e.getPoint());
	}

	@Override
	protected void renew() {
		info.renew();

		Vector<AbEnemy> v = new Vector<>();

		if (efp != null && efp.getList() != null)
			v.addAll(efp.getList());

		UserPack p = UserProfile.getUserPack(pack);

		if(p != null) {
			ArrayList<EneRand> rands = new ArrayList<>(p.randEnemies.getList());

			for(String str : p.desc.dependency) {
				UserPack parent = UserProfile.getUserPack(str);

				if(parent != null) {
					rands.addAll(parent.randEnemies.getList());
				}
			}

			v.addAll(rands);
		}

		jle.setListData(v);
	}

	@Override
	protected synchronized void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(info, x, y, 900, 50, 1400, 300);
		set(addl, x, y, 900, 400, 200, 50);
		set(reml, x, y, 1100, 400, 200, 50);
		set(elim, x, y, 1600, 400, 200, 50);
		set(recd, x, y, 1850, 400, 200, 50);
		set(advs, x, y, 2100, 400, 200, 50);
		set(jspjt, x, y, 900, 450, 1400, 850);

		set(jspsm, x, y, 0, 50, 300, 800);
		set(cpsm, x, y, 0, 850, 300, 50);
		set(ptsm, x, y, 0, 900, 300, 50);
		set(rmsm, x, y, 0, 950, 300, 50);
		set(jlpsm, x, y, 0, 1000, 300, 300);

		set(strt, x, y, 300, 0, 300, 50);
		set(adds, x, y, 300, 50, 150, 50);
		set(rems, x, y, 450, 50, 150, 50);
		set(jspst, x, y, 300, 100, 300, 750);
		set(cpst, x, y, 300, 850, 300, 50);
		set(ptst, x, y, 300, 900, 300, 50);
		set(rmst, x, y, 300, 950, 300, 50);
		set(jlpst, x, y, 300, 1000, 300, 300);

		set(veif, x, y, 600, 0, 300, 50);
		set(jspe, x, y, 600, 50, 300, 1250);
		jt.setRowHeight(size(x, y, 50));
	}

	private void addListeners$0() {

		back.setLnr(x -> changePanel(getFront()));

		strt.setLnr(x -> changePanel(new BattleSetupPage(getThis(), stage)));

		advs.setLnr(x -> changePanel(new AdvStEditPage(getThis(), stage)));

		recd.setLnr(x -> changePanel(new StRecdPage(getThis(), stage, true)));

		elim.setLnr(x -> changePanel(new LimitEditPage(getThis(), stage)));

		addl.addActionListener(arg0 -> {
			int ind = jt.addLine(jle.getSelectedValue());
			setData(stage);
			if (ind < 0)
				jt.clearSelection();
			else
				jt.addRowSelectionInterval(ind, ind);
		});

		reml.addActionListener(arg0 -> {
			int ind = jt.remLine();
			setData(stage);
			if (ind < 0)
				jt.clearSelection();
			else
				jt.addRowSelectionInterval(ind, ind);
		});

		veif.setLnr(x -> changePanel(efp));

	}

	private void addListeners$1() {

		jlsm.addListSelectionListener(arg0 -> {
			if (changing || arg0.getValueIsAdjusting())
				return;
			changing = true;
			setAA(jlsm.getSelectedValue());
			changing = false;
		});

		jlst.addListSelectionListener(arg0 -> {
			if (changing || arg0.getValueIsAdjusting())
				return;
			changing = true;
			setAB(jlst.getSelectedValue());
			changing = false;
		});

		lpsm.addListSelectionListener(arg0 -> {
			if (changing || arg0.getValueIsAdjusting())
				return;
			changing = true;
			setBA(lpsm.getSelectedValue());
			changing = false;
		});

		lpst.addListSelectionListener(arg0 -> {
			if (changing || arg0.getValueIsAdjusting())
				return;
			changing = true;
			setBB(lpst.getSelectedValue());
			changing = false;
		});

	}

	private void addListeners$2() {

		cpsm.addActionListener(arg0 -> {
			StageMap sm = jlsm.getSelectedValue();
			MapColc col = Stage.CLIPMC;
			StageMap copy = sm.copy(col);
			col.maps.add(copy);
			changing = true;
			lpsm.setListData(col.maps.toArray());
			lpsm.setSelectedValue(copy, true);
			setBA(copy);
			changing = false;
		});

		cpst.addActionListener(arg0 -> {
			Stage copy = stage.copy(Stage.CLIPSM);
			Stage.CLIPSM.add(copy);
			changing = true;
			lpst.setListData(Stage.CLIPSM.list.toArray());
			lpst.setSelectedValue(copy, true);
			lpsm.setSelectedIndex(0);
			setBB(copy);
			changing = false;
		});

		ptsm.addActionListener(arg0 -> {
			StageMap sm = lpsm.getSelectedValue();
			StageMap ni = sm.copy(mc);
			mc.maps.add(ni);
			changing = true;
			jlsm.setListData(mc, mc.maps);
			jlsm.setSelectedValue(ni, true);
			setBA(ni);
			changing = false;
		});

		ptst.addActionListener(arg0 -> {
			StageMap sm = jlsm.getSelectedValue();
			stage = lpst.getSelectedValue().copy(sm);
			sm.add(stage);
			changing = true;
			jlst.setListData(sm, sm.list);
			jlst.setSelectedValue(stage, true);
			setBB(stage);
			changing = false;
		});

		rmsm.addActionListener(arg0 -> {
			int ind = lpsm.getSelectedIndex();
			MapColc col = Stage.CLIPMC;
			col.maps.remove(lpsm.getSelectedValue());
			changing = true;
			lpsm.setListData(col.maps.toArray());
			lpsm.setSelectedIndex(ind - 1);
			setBA(lpsm.getSelectedValue());
			changing = false;
		});

		rmst.addActionListener(arg0 -> {
			StageMap sm = lpsm.getSelectedValue();
			Stage st = lpst.getSelectedValue();
			int ind = lpst.getSelectedIndex();
			sm.list.remove(st);
			changing = true;
			lpst.setListData(sm.list.toArray());
			lpst.setSelectedIndex(ind - 1);
			setBB(lpst.getSelectedValue());
			changing = false;
		});

		adds.setLnr(jlst::addItem);

		rems.setLnr(jlst::deleteItem);

	}

	private void checkPtsm() {
		StageMap sm = lpsm.getSelectedValue();
		if (sm == null) {
			ptsm.setEnabled(false);
			return;
		}
		Set<String> set = new TreeSet<>();
		for (Stage st : sm.list)
			set.addAll(st.isSuitable(pack));
		ptsm.setEnabled(set.size() == 0);
		if (set.size() > 0)
			ptsm.setToolTipText("requires: " + set);

	}

	private void checkPtst() {
		Stage st = lpst.getSelectedValue();
		StageMap sm = jlsm.getSelectedValue();
		if (st == null || sm == null)
			ptst.setEnabled(false);
		else {
			Set<String> set = st.isSuitable(pack);
			ptst.setEnabled(set.size() == 0);
			if (set.size() > 0)
				ptst.setToolTipText("requires: " + set);
		}
		rmst.setEnabled(st != null);
	}

	private void ini() {
		add(back);
		add(veif);
		add(adds);
		add(rems);
		add(jspjt);
		add(info);
		add(strt);
		add(jspsm);
		add(jspst);
		add(addl);
		add(reml);
		add(jspe);
		add(cpsm);
		add(cpst);
		add(ptsm);
		add(ptst);
		add(rmsm);
		add(rmst);
		add(jlpsm);
		add(jlpst);
		add(recd);
		add(advs);
		add(elim);
		setAA(null);
		setBA(null);
		jle.setCellRenderer(new AnimLCR());
		addListeners$0();
		addListeners$1();
		addListeners$2();

	}

	private void setAA(StageMap sm) {
		if (sm == null) {
			jlst.setListData(null, null);
			setAB(null);
			cpsm.setEnabled(false);
			ptst.setEnabled(false);
			adds.setEnabled(false);
			return;
		}
		jlst.setListData(sm, sm.list);
		if (sm.list.size() == 0) {
			jlst.clearSelection();
			cpsm.setEnabled(false);
			adds.setEnabled(true);
			checkPtst();
			setAB(null);
			return;
		}
		jlst.setSelectedIndex(0);
		cpsm.setEnabled(true);
		adds.setEnabled(true);
		checkPtst();
		setAB(sm.list.getList().get(0));
	}

	private void setAB(Stage st) {
		if (st == null) {
			setData(lpst.getSelectedValue());
			cpst.setEnabled(false);
			rems.setEnabled(false);
			return;
		}
		cpst.setEnabled(true);
		rems.setEnabled(true);
		lpst.clearSelection();
		checkPtst();
		setData(st);
	}

	private void setBA(StageMap sm) {
		if (sm == null) {
			lpst.setListData(new Stage[0]);
			ptsm.setEnabled(false);
			rmsm.setEnabled(false);
			setBB(null);
			return;
		}
		lpst.setListData(sm.list.toArray());
		rmsm.setEnabled(sm != Stage.CLIPSM);
		if (sm.list.size() == 0) {
			lpst.clearSelection();
			ptsm.setEnabled(false);
			setBB(null);
			return;
		}
		lpst.setSelectedIndex(0);
		setBB(sm.list.getList().get(0));
		checkPtsm();

	}

	private void setBB(Stage st) {
		if (st == null) {
			setData(jlst.getSelectedValue());
			ptst.setEnabled(false);
			rmst.setEnabled(false);
			return;
		}
		cpst.setEnabled(false);
		checkPtst();
		jlst.clearSelection();
		setData(st);
	}

	private void setData(Stage st) {
		stage = st;
		info.setData(st);
		jt.setData(st);
		strt.setEnabled(st != null);
		recd.setEnabled(st != null);
		advs.setEnabled(st != null);
		elim.setEnabled(st != null);
		jspjt.scrollRectToVisible(new Rectangle(0, 0, 1, 1));
		resized();
	}

}
