package page.info.edit;

import common.CommonStatic;
import common.pack.PackData.UserPack;
import common.util.stage.MapColc;
import common.util.stage.SCDef;
import common.util.stage.SCGroup;
import common.util.stage.Stage;
import common.util.stage.info.CustomStageInfo;
import common.util.unit.AbEnemy;
import main.Opts;
import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;
import page.info.StageViewPage;
import page.support.AnimLCR;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AdvStEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JTF sdef = new JTF();
	private final JTF smax = new JTF();
	private final JList<SCGroup> jls = new JList<>();
	private final JScrollPane jsps = new JScrollPane(jls);
	private final JList<AbEnemy> jle = new JList<>();
	private final JScrollPane jspe = new JScrollPane(jle);
	private final SCGroupEditTable sget;
	private final JScrollPane jspt;
	private final JL groups = new JL(0, "groups");
	private final JBTN addg = new JBTN(0, "add");
	private final JBTN remg = new JBTN(0, "rem");
	private final JBTN addt = new JBTN(0, "addl");
	private final JBTN remt = new JBTN(0, "reml");

	private final JList<Stage> jex = new JList<>();
	private final JScrollPane jsex = new JScrollPane(jex);
	private final JL exSt = new JL(0, "exsts");
	private final JBTN addex = new JBTN(0, "add");
	private final JBTN remex = new JBTN(0, "rem");
	private final JL jlprob = new JL(0, "prob");
	private final JTF jprob = new JTF();
	private final JL jltprob = new JL(0, "total");
	private final JTF jtprob = new JTF();
	private final JBTN equal = new JBTN(0, "equalprob");
	private StageViewPage svp;

	private final Stage st;
	private final SCDef data;

	protected AdvStEditPage(Page p, Stage stage) {
		super(p);
		st = stage;
		data = st.data;
		sget = new SCGroupEditTable(data);
		jspt = new JScrollPane(sget);
		ini();
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(groups, x, y, 50, 100, 300, 50);
		set(jsps, x, y, 50, 150, 300, 750);
		set(addg, x, y, 50, 900, 150, 50);
		set(remg, x, y, 200, 900, 150, 50);
		set(smax, x, y, 50, 950, 300, 50);
		set(jspe, x, y, 400, 100, 300, 800);
		set(sdef, x, y, 400, 900, 300, 50);
		set(jspt, x, y, 750, 150, 400, 800);
		set(addt, x, y, 750, 100, 200, 50);
		set(remt, x, y, 950, 100, 200, 50);
		set(exSt, x, y, 1200, 100, 300, 50);
		set(jsex, x, y, 1200, 200, 300, 650);
		set(addex, x, y, 1200, 150, 150, 50);
		set(remex, x, y, 1350, 150, 150, 50);
		set(jlprob, x, y, 1200, 850, 150, 50);
		set(jprob, x, y, 1350, 850, 150, 50);
		set(jltprob, x, y, 1200, 900, 150, 50);
		set(jtprob, x, y, 1350, 900, 150, 50);
		set(equal, x, y, 1200, 950, 300, 50);
		sget.setRowHeight(size(x, y, 50));
	}

	private void addListeners$0() {
		back.setLnr(e -> changePanel(getFront()));

		jls.addListSelectionListener(arg0 -> {
			if (isAdj() || jls.getValueIsAdjusting())
				return;
			setSCG(jls.getSelectedValue());
		});

		addg.setLnr(e -> {
			int ind = data.sub.nextInd();
			SCGroup scg = new SCGroup(ind, st.max);
			data.sub.add(scg);
			setListG();
			setSCG(scg);
		});

		remg.setLnr(e -> {
			int ind = jls.getSelectedIndex();
			data.sub.remove(jls.getSelectedValue());
			if (ind > data.sub.size())
				ind--;
			setListG();
			jls.setSelectedIndex(ind);
		});

		smax.setLnr(e -> {
			int val = CommonStatic.parseIntN(smax.getText());
			SCGroup scg = jls.getSelectedValue();
			if (val > 0)
				scg.setMax(val, -1);
			setSCG(scg);
		});

		sdef.setLnr(e -> {
			int i = CommonStatic.parseIntN(sdef.getText());
			if (i >= 0)
				data.sdef = i;
			setListG();
		});

		jex.addListSelectionListener(e -> {
			if (isAdj() || jex.getValueIsAdjusting())
				return;
			if (st.info == null || jex.getSelectedIndex() == -1)
				jprob.setText("");
			else
				jprob.setText(((CustomStageInfo)st.info).chances.get(jex.getSelectedIndex()) + "%");
			jprob.setEnabled(!jprob.getText().equals(""));
		});

		addex.setLnr(e -> {
			if (svp == null) {
				ArrayList<MapColc> maps = new ArrayList<>();
				maps.add(st.getCont().getCont());

				UserPack pack = ((MapColc.PackMapColc)st.getCont().getCont()).pack;
				for (MapColc map : MapColc.values())
					if (map instanceof MapColc.DefMapColc || pack.desc.dependency.contains(((MapColc.PackMapColc)map).pack.desc.id))
						maps.add(map);

				svp = new StageViewPage(this, maps);
			}
			changePanel(svp);
		});

		remex.setLnr(e -> {
			int ind = jex.getSelectedIndex();
			((CustomStageInfo)st.info).remove(jex.getSelectedIndex());
			if (st.info != null) {
				jex.setListData(st.info.getExStages());
				if (ind >= st.info.getExChances().length)
					ind--;
				jex.setSelectedIndex(ind);
				setFollowups((CustomStageInfo) st.info);
			} else {
				jex.setListData(new Stage[0]);
				jex.clearSelection();
				setFollowups(null);
			}
		});

		jprob.setLnr(e -> {
			double d = CommonStatic.parseDoubleN(jprob.getText());
			if (d > 0) {
				CustomStageInfo csi = (CustomStageInfo)st.info;
				csi.chances.set(jex.getSelectedIndex(), (float) d);
				csi.checkChances();
				setFollowups(csi);
			}
		});

		jtprob.setLnr(e -> {
			int p = CommonStatic.parseIntN(jtprob.getText());
			CustomStageInfo csi = (CustomStageInfo) st.info;
			if (p > 0 && p <= 100)
				csi.setTotalChance((byte) p);

			jtprob.setText(csi.totalChance + "%");
		});

		equal.setLnr(e -> ((CustomStageInfo)st.info).equalizeChances());
	}

	private void addListeners$1() {
		addt.setLnr(e -> sget.addLine(jle.getSelectedValue()));

		remt.setLnr(e -> sget.remLine());
	}

	private void ini() {
		AbEnemy[] aes = data.getSummon().toArray(new AbEnemy[0]);
		add(back);
		add(groups);
		add(jsps);
		add(addg);
		add(remg);
		add(smax);

		if (aes.length > 0) {
			add(sdef);
			add(jspe);
			add(jspt);
			add(addt);
			add(remt);
		}

		add(jsex);
		add(exSt);
		add(addex);
		add(remex);
		add(jlprob);
		add(jprob);
		add(jltprob);
		add(jtprob);
		add(equal);
		jle.setCellRenderer(new AnimLCR());
		jle.setListData(aes);
		sdef.setText("default: " + data.sdef);

		if (st.info != null) {
			jex.setListData(st.info.getExStages());
			setFollowups((CustomStageInfo)st.info);
		} else
			setFollowups(null);
		setListG();
		addListeners$0();
		addListeners$1();
	}

	private void setListG() {
		SCGroup scg = jls.getSelectedValue();
		List<SCGroup> l = data.sub.getList();
		change(0, n -> {
			jls.clearSelection();
			jls.setListData(l.toArray(new SCGroup[0]));
		});
		sdef.setText("default: " + data.sdef);
		if (scg != null && !l.contains(scg))
			scg = null;
		setSCG(scg);
	}

	private void setSCG(SCGroup scg) {
		if (jls.getSelectedValue() != scg)
			change(scg, s -> jls.setSelectedValue(s, true));
		remg.setEnabled(scg != null);
		smax.setEnabled(scg != null);
		smax.setText(scg != null ? "max: " + scg.getMax(0) : "");
	}

	private void setFollowups(CustomStageInfo si) {
		remex.setEnabled(si != null);
		jex.setEnabled(si != null);
		jprob.setEnabled(si != null && jex.getSelectedIndex() != -1);

		jtprob.setEnabled(si != null);
		jtprob.setText(si != null ? si.totalChance + "%" : "");
	}

	@Override
	public void renew() {
		if (svp != null && svp.getSelectedStages().size() > 0) {
			if (st.info == null)
				st.info = new CustomStageInfo(st);
			CustomStageInfo csi = (CustomStageInfo)st.info;
			List<Stage> stages = svp.getSelectedStages();
			for (int i = 0; i < stages.size(); i++)
				if (csi.stages.contains(stages.get(i))) {
					Opts.pop("Already added EX stage", stages.get(i).toString() + "already exists in the EX stages list");
					stages.remove(i);
					i--;
				} else {
					csi.stages.add(stages.get(i));
					csi.chances.add(100f / csi.stages.size());
				}
			csi.checkChances();
			jex.setListData(st.info.getExStages());
			setFollowups(csi);
		}
	}

}
