package page.info;

import common.util.stage.MapColc;
import common.util.stage.RandStage;
import common.util.stage.Stage;
import common.util.stage.StageMap;
import main.MainBCU;
import page.JBTN;
import page.JTF;
import page.Page;
import page.battle.BattleSetupPage;
import page.battle.StRecdPage;
import utilpc.UtilPC;

import javax.swing.*;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class StageViewPage extends StagePage {

	private static final long serialVersionUID = 1L;

	private final JList<MapColc> jlmc = new JList<>();
	private final JScrollPane jspmc = new JScrollPane(jlmc);

	private final Vector<StageMap> vtsm = new Vector<>();
	private final JList<StageMap> jlsm = new JList<>();
	private final JScrollPane jspsm = new JScrollPane(jlsm);

	private final Vector<Stage> vtst = new Vector<>();
	private final JList<Stage> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);

	private final JBTN cpsm = new JBTN(0, "cpsm");
	private final JBTN shmc = new JBTN(0, "showmc");
	private final JBTN cpst = new JBTN(0, "cpst");
	private final JBTN shsm = new JBTN(0, "showsm");

	private final JBTN dgen = new JBTN(0, "dungeon");
	private final JBTN srch = new JBTN(0, "asrch");

	private final JBTN recd = new JBTN(0, "replay");

	private final JTF smnm = new JTF();
	private final JTF snam = new JTF();

	public StageViewPage(Page p, Collection<MapColc> collection) {
		super(p);
		jlmc.setListData(new Vector<>(collection));

		ini();
	}

	public StageViewPage(Page p, Collection<MapColc> col, Stage st) {
		this(p, col);
		if (st == null)
			return;
		jlmc.setSelectedValue(st.getCont().getCont(), true);
		jlsm.setSelectedValue(st.getCont(), true);
		jlst.setSelectedValue(st, true);
	}

	@Override
	protected void resized(int x, int y) {
		super.resized(x, y);

		set(smnm, x, y, 0, 50, 400, 50);
		set(jspsm, x, y, 0, 100, 400, 1100);
		set(cpsm, x, y, 0, 1200, 200, 50);
		set(shmc, x, y, 200, 1200, 200, 50);

		set(jspmc, x, y, 400, 50, 400, 500);
		set(snam, x, y, 400, 550, 400, 50);
		set(jspst, x, y, 400, 600, 400, 600);
		set(cpst, x, y, 400, 1200, 200, 50);
		set(shsm, x, y, 600, 1200, 200, 50);

		set(dgen, x, y, 600, 0, 200, 50);
		set(strt, x, y, 400, 0, 200, 50);
		set(srch, x, y, 200, 0, 200, 50);

		set(recd, x, y, 1850, 350, 200, 50);
	}

	@Override
	protected void setData(Stage st, int starId) {
		super.setData(st, starId);
		cpst.setEnabled(st != null);
		shsm.setEnabled(st != null);
		recd.setEnabled(st != null);
	}

	@Override
	public void callBack(Object v) {
		if (v instanceof Integer)
			setData(stage, (int) v);
	}

	private void addListeners() {

		recd.setLnr(x -> changePanel(new StRecdPage(this, stage, false)));

		jlmc.addListSelectionListener(arg0 -> {
			if (arg0.getValueIsAdjusting() || jlmc.isSelectionEmpty())
				return;
			vtsm.clear();
			for (MapColc m : jlmc.getSelectedValuesList())
				vtsm.addAll(m.maps.getList());
			confirmSearchSM();
		});

		jlsm.addListSelectionListener(arg0 -> {
			if (arg0.getValueIsAdjusting())
				return;
			vtst.clear();
			List<StageMap> maps = jlsm.getSelectedValuesList();
			cpsm.setEnabled(false);
			shsm.setEnabled(false);
			if (maps == null)
				return;
			cpsm.setEnabled(true);
			shmc.setEnabled(true);
			for (StageMap sm : maps)
				vtst.addAll(sm.list.getList());
			confirmSearchST();
		});

		jlst.addListSelectionListener(arg0 -> {
			if (arg0.getValueIsAdjusting())
				return;
			Stage s = jlst.getSelectedValue();
			cpst.setEnabled(false);
			shsm.setEnabled(false);
			if (s == null)
				return;
			setData(s, 0);
		});

		cpsm.addActionListener(arg0 -> {
			List<StageMap> lsm = jlsm.getSelectedValuesList();
			if (lsm.isEmpty())
				return;
			MapColc mc = Stage.CLIPMC;
			for (StageMap sm : lsm)
				mc.maps.add(sm.copy(mc));
		});

		cpst.addActionListener(arg0 -> {
			List<Stage> stages = jlst.getSelectedValuesList();
			if (stages.isEmpty())
				return;
			for (Stage st : stages)
				Stage.CLIPSM.add(st.copy(Stage.CLIPSM));
		});

		dgen.setLnr(x -> {
			StageMap sm = jlsm.getSelectedValue();
			if (sm == null)
				changePanel(new StageRandPage(getThis(), jlmc.getSelectedValue()));
			else {
				Stage s = RandStage.getStage(sm);
				changePanel(new BattleSetupPage(getThis(), s, 0));
			}
		});

		srch.setLnr(x -> changePanel(new StageSearchPage(getThis())));

		shmc.setLnr(x -> {
			StageMap sm = jlsm.getSelectedValue();
			jlmc.clearSelection();
			jlmc.setSelectedValue(sm.getCont(), true);
		});

		shsm.setLnr(x -> {
			Stage st = jlst.getSelectedValue();
			jlmc.clearSelection();
			jlsm.clearSelection();
			jlmc.setSelectedValue(st.getCont().getCont(), true);
			jlsm.setSelectedValue(st.getCont(), true);
			jlst.setSelectedValue(st, true);
		});
	}

	private void addListeners2() {
		smnm.setTypeLnr(x -> confirmSearchSM());
		snam.setTypeLnr(x -> confirmSearchST());
	}

	private void confirmSearchSM() {
		Vector<StageMap> filtered = new Vector<>();
		String text = smnm.getText().toLowerCase();
		int minDiff = MainBCU.searchTolerance;
		for (StageMap sm : vtsm) {
			int diff = UtilPC.damerauLevenshteinDistance(sm.toString().toLowerCase(), text);
			minDiff = Math.min(minDiff, diff);
			if (diff == minDiff)
				filtered.add(sm);
		}
		StageMap curr = jlsm.getSelectedValue();
		jlsm.setListData(filtered);
		jlsm.setSelectedIndex(Math.max(filtered.indexOf(curr), 0));
	}

	private void confirmSearchST() {
		Vector<Stage> filtered = new Vector<>();
		String text = snam.getText().toLowerCase();
		int minDiff = MainBCU.searchTolerance;
		for (Stage st : vtst) {
			int diff = UtilPC.damerauLevenshteinDistance(st.toString().toLowerCase(), text);
			minDiff = Math.min(minDiff, diff);
			if (diff == minDiff)
				filtered.add(st);
		}
		Stage curr = jlst.getSelectedValue();
		jlst.setListData(filtered);
		jlst.setSelectedIndex(Math.max(filtered.indexOf(curr), 0));
	}

	private void ini() {
		add(jspmc);
		add(jspsm);
		add(jspst);
		add(recd);
		add(cpsm);
		add(cpst);
		add(dgen);
		add(srch);
		add(smnm);
		add(snam);
		add(shmc);
		add(shsm);
		cpsm.setEnabled(false);
		shmc.setEnabled(false);
		cpst.setEnabled(false);
		shsm.setEnabled(false);
		recd.setEnabled(false);
		smnm.setHint("Search stage map");
		snam.setHint("Search stage");
		addListeners();
		addListeners2();
	}

	public List<Stage> getSelectedStages() {
		return jlst.getSelectedValuesList();
	}
}
