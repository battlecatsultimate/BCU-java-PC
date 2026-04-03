package page.battle;

import common.CommonStatic;
import common.battle.BasisLU;
import common.battle.BasisSet;
import common.util.stage.Limit;
import common.util.stage.RandStage;
import common.util.stage.Stage;
import page.JBTN;
import page.JTG;
import page.MainLocale;
import page.Page;
import page.basis.BasisPage;
import page.basis.LineUpBox;
import page.basis.LubCont;
import page.basis.ModifierList;
import page.info.StageTable;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class BattleSetupPage extends LubCont {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN strt = new JBTN(0, "start");
	private final JBTN tmax = new JBTN(0, "tomax");
	private final JTG rich = new JTG(0, "rich");
	private final JTG snip = new JTG(0, "sniper");
	private final JTG plus = new JTG(MainLocale.PAGE, "plusunlock");
	private final JComboBox<String> lvlim = new JComboBox<>();
	private final JList<String> jls = new JList<>();
	private final JScrollPane jsps = new JScrollPane(jls);
	private final JLabel jl = new JLabel();
	private final JBTN jlu = new JBTN(0, "line");
	private final LineUpBox lub = new LineUpBox(this);
	private final ModifierList mod = new ModifierList();
	private final JScrollPane jmod = new JScrollPane(mod);

	private final StageTable sttb;
	private final JScrollPane jstt;

	private final Stage st;

	private final int conf;

	public BattleSetupPage(Page p, Stage s, int confs) {
		super(p);
		conf = confs;
		sttb = new StageTable(this);
		jstt = new JScrollPane(sttb);
		st = s;

		ini();
		renew();
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	protected LineUpBox getLub() {
		return lub;
	}

	@Override
	protected void renew() {
		BasisSet b = BasisSet.current();
		jl.setText(b + "-" + b.sele);
		Limit lim = st.getLim(getStarLevel());
		if (lim.lvr != null)
			strt.setEnabled(st.lim.lvr.isValid(b.sele.lu));
		else
			tmax.setEnabled(false);
		rich.setEnabled(lim.rich == 0);
		snip.setEnabled(lim.sniper == 0);
		if (lub.getLU() != b.sele.lu)
			lub.setLU(b.sele);
		b.sele.lu.renew();
		mod.setBasis(BasisSet.current());
		mod.setComboList(BasisSet.current().sele.lu.coms);
		mod.setBanned(lub.getLim().stageLimit != null ? lub.getLim().stageLimit.bannedCatCombo : null);
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);

		set(back, x, y, 0, 0, 200, 50);
		set(jsps, x, y, 50, 100, 200, 200);
		set(jl, x, y, 50, 350, 200, 50);
		set(jlu, x, y, 50, 400, 200, 50);
		set(strt, x, y, 50, 500, 200, 50);
		set(rich, x, y, 300, 100, 200, 50);
		set(snip, x, y, 300, 200, 200, 50);
		set(tmax, x, y, 300, 500, 200, 50);
		set(lub, x, y, 550, 50, 600, 300);
		set(jmod, x, y, 550, 350, 600, 200);
		set(plus, x, y, 1200, 100, 200, 50);
		set(lvlim, x, y, 1200, 200, 200, 50);
		set(jstt, x, y, 50, 600, 1400, 650);
		sttb.setRowHeight(size(x, y, 50));
	}

	@Override
	public void callBack(Object newParam) {
		renew();
	}

	@Override
	protected void mouseClicked(MouseEvent e) {
		if (e.getSource() == sttb)
			sttb.clicked(e.getPoint());
		super.mouseClicked(e);
	}

	private void addListeners() {
		back.addActionListener(arg0 -> changePanel(getFront()));

		jls.addListSelectionListener(arg0 -> {
			if (arg0.getValueIsAdjusting())
				return;
			if (jls.getSelectedIndex() == -1)
				jls.setSelectedIndex(0);
			lub.setLimit(st.getLim(getStarLevel()), st.getCont().price);
			sttb.setData(st, getStarLevel());
			renew();
		});

		jlu.addActionListener(arg0 -> changePanel(new BasisPage(getThis(), st, getStarLevel(), st.getCont().price)));

		strt.addActionListener(arg0 -> {
			int star = getStarLevel();
			int[] ints = new int[1];
			Limit lim = st.getLim(star);
			if (rich.isSelected() && lim.rich == 0)
				ints[0] |= 1;
			if (snip.isSelected() && lim.rich == 0)
				ints[0] |= 2;
			BasisLU b = BasisSet.current().sele;
			if (conf == 0) {
				b = RandStage.getLU(star);
				star = 0;
			}
			changePanel(new BattleInfoPage(getThis(), st, star, b, ints));
		});

		tmax.addActionListener(arg0 -> {
			st.lim.lvr.validate(BasisSet.current().sele.lu);
			renew();
		});

		plus.setLnr(a -> CommonStatic.getConfig().plus = plus.isSelected());

		lvlim.addActionListener(a -> {
			CommonStatic.getConfig().levelLimit = lvlim.getSelectedIndex();

			plus.setEnabled(CommonStatic.getConfig().levelLimit != 0);
		});
	}

	private void ini() {
		add(back);
		add(jsps);
		add(jl);
		add(jlu);
		add(strt);
		add(rich);
		add(snip);
		add(tmax);
		add(lub);
		add(jstt);
		add(jmod);
		sttb.setData(st, 0);
		if(st.isAkuStage()) {
			add(plus);
			add(lvlim);

			Vector<String> levLimitText = new Vector<>();

			levLimitText.add(get(MainLocale.PAGE, "levlimoff"));

			for(int i = 1; i < 51; i++) {
				levLimitText.add(Integer.toString(i));
			}

			lvlim.setModel(new DefaultComboBoxModel<>(levLimitText));

			plus.setToolTipText(MainLocale.getLoc(MainLocale.PAGE, "plusunlocktip"));
			lvlim.setToolTipText(MainLocale.getLoc(MainLocale.PAGE, "levellimit"));

			plus.setSelected(CommonStatic.getConfig().plus);
			lvlim.setSelectedIndex(CommonStatic.getConfig().levelLimit);
		}
		if (conf == 1) {
			String[] tit = new String[st.getCont().stars.length];
			String star = get(1, "star");
			for (int i = 0; i < st.getCont().stars.length; i++)
				tit[i] = (i + 1) + star + ": " + st.getCont().stars[i] + "%";
			jls.setListData(tit);
		} else if (conf == 0) {
			String[] tit = new String[5];
			String star = get(1, "attempt");
			for (int i = 0; i < 5; i++)
				tit[i] = star + (i + 1);
			jls.setListData(tit);
		}
		jls.setSelectedIndex(0);
		lub.setLimit(st.getLim(getStarLevel()), st.getCont().price);
		addListeners();
	}

	int getStarLevel() {
		return conf == 1 ? jls.getSelectedIndex() : 0;
	}
}
