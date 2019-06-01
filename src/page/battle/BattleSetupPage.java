package page.battle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import page.JBTN;
import page.JTG;
import page.Page;
import page.basis.BasisPage;
import page.basis.LineUpBox;
import page.basis.LubCont;
import util.basis.BasisLU;
import util.basis.BasisSet;
import util.stage.RandStage;
import util.stage.Stage;

public class BattleSetupPage extends LubCont {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN strt = new JBTN(0, "start");
	private final JBTN tmax = new JBTN(0, "tomax");
	private final JTG rich = new JTG(0, "rich");
	private final JTG snip = new JTG(0, "sniper");
	private final JList<String> jls = new JList<>();
	private final JScrollPane jsps = new JScrollPane(jls);
	private final JLabel jl = new JLabel();
	private final JBTN jlu = new JBTN(0, "line");
	private final LineUpBox lub = new LineUpBox(this);

	private final Stage st;

	private final int[] conf;

	public BattleSetupPage(Page p, Stage s, int... confs) {
		super(p);
		st = s;
		conf = confs;
		ini();
		resized();
	}

	@Override
	protected LineUpBox getLub() {
		return lub;
	}

	@Override
	protected void renew() {
		BasisSet b = BasisSet.current;
		jl.setText(b + "-" + b.sele);
		if (st.lim != null && st.lim.lvr != null)
			strt.setEnabled(st.lim.lvr.isValid(b.sele.lu));
		else
			tmax.setEnabled(false);
		lub.setLU(b.sele.lu);
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
	}

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		jls.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting())
					return;
				if (jls.getSelectedIndex() == -1)
					jls.setSelectedIndex(0);
			}
		});

		jlu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new BasisPage(getThis()));
			}
		});

		strt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int star = jls.getSelectedIndex();
				int[] ints = new int[1];
				if (rich.isSelected())
					ints[0] |= 1;
				if (snip.isSelected())
					ints[0] |= 2;
				BasisLU b = BasisSet.current.sele;
				if (conf.length == 1 && conf[0] == 0)
					b = RandStage.getLU(star);
				changePanel(new BattleInfoPage(getThis(), st, 0, b, ints));
			}
		});

		tmax.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				st.lim.lvr.validate(BasisSet.current.sele.lu);
				renew();
			}
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
		if (conf.length == 0) {
			String[] tit = new String[st.map.stars.length];
			String star = get(1, "star");
			for (int i = 0; i < st.map.stars.length; i++)
				tit[i] = (i + 1) + star + ": " + st.map.stars[i] + "%";
			jls.setListData(tit);
		} else if (conf.length == 1 && conf[0] == 0) {
			String[] tit = new String[5];
			String star = get(1, "attempt");
			for (int i = 0; i < 5; i++)
				tit[i] = star + (i + 1);
			jls.setListData(tit);
		}
		jls.setSelectedIndex(0);
		addListeners();
	}

}
