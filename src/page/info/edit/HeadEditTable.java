package page.info.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import common.CommonStatic;
import common.util.pack.BGStore;
import common.util.pack.Pack;
import common.util.stage.Castles;
import common.util.stage.Limit;
import common.util.stage.Recd;
import common.util.stage.Stage;
import page.JBTN;
import page.JL;
import page.JTF;
import page.JTG;
import page.Page;
import page.view.BGViewPage;
import page.view.CastleViewPage;
import page.view.MusicPage;

class HeadEditTable extends Page {

	private static final long serialVersionUID = 1L;

	private final JL hea = new JL(1, "ht00");
	private final JL len = new JL(1, "ht01");
	private final JBTN mus = new JBTN(1, "mus");
	private final JL max = new JL(1, "ht02");
	private final JBTN bg = new JBTN(1, "ht04");
	private final JBTN cas = new JBTN(1, "ht05");
	private final JTF name = new JTF();
	private final JTF jhea = new JTF();
	private final JTF jlen = new JTF();
	private final JTF jbg = new JTF();
	private final JTF jcas = new JTF();
	private final JTF jm0 = new JTF();
	private final JTF jmh = new JTF();
	private final JTF jm1 = new JTF();
	private final JTG con = new JTG(1, "ht03");
	private final JTF[] star = new JTF[4];
	private final JTF jmax = new JTF();
	private final LimitTable lt;

	private Stage sta;
	private final Pack pac;
	private BGViewPage bvp;
	private CastleViewPage cvp;
	private MusicPage mp;

	private int musl = 0;

	protected HeadEditTable(Page p, Pack pack) {
		super(p);
		pac = pack;
		lt = new LimitTable(p, this, pac);
		ini();
	}

	@Override
	public void callBack(Object o) {
		setData(sta);
	}

	@Override
	protected void renew() {
		lt.renew();
		if (bvp != null) {
			int val = bvp.getSelected().id;
			if (val == -1)
				return;
			jbg.setText("" + val);
			input(jbg, "" + val);
		}
		if (cvp != null) {
			int val = cvp.getVal();
			if (val == -1)
				return;
			jcas.setText("" + val);
			input(jcas, "" + val);
		}

		if (mp != null) {
			JTF jtf = musl == 0 ? jm0 : jm1;
			jtf.setText("" + mp.getSelected());
			input(jtf, "" + mp.getSelected());
		}
		bvp = null;
		cvp = null;
		mp = null;
	}

	@Override
	protected void resized(int x, int y) {
		int w = 1400 / 8;
		set(name, x, y, 0, 0, w * 2, 50);
		set(hea, x, y, 0, 50, w, 50);
		set(jhea, x, y, w, 50, w, 50);
		set(len, x, y, w * 2, 50, w, 50);
		set(jlen, x, y, w * 3, 50, w, 50);
		set(max, x, y, w * 4, 50, w, 50);
		set(jmax, x, y, w * 5, 50, w, 50);
		set(con, x, y, w * 6, 50, w, 50);
		set(bg, x, y, 0, 100, w, 50);
		set(jbg, x, y, w, 100, w, 50);
		set(cas, x, y, w * 2, 100, w, 50);
		set(jcas, x, y, w * 3, 100, w, 50);
		set(mus, x, y, w * 4, 100, w, 50);
		set(jm0, x, y, w * 5, 100, w, 50);
		set(jmh, x, y, w * 6, 100, w, 50);
		set(jm1, x, y, w * 7, 100, w, 50);
		for (int i = 0; i < 4; i++)
			set(star[i], x, y, w * (2 + i), 0, w, 50);
		set(lt, x, y, 0, 150, 1400, 100);
		lt.componentResized(x, y);
	}

	protected void setData(Stage st) {
		sta = st;
		abler(st != null);
		if (st == null)
			return;
		change(true);
		name.setText(st.toString());
		jhea.setText("" + st.health);
		jlen.setText("" + st.len);
		jbg.setText("" + st.bg);
		jcas.setText("" + st.getCastle());
		jm0.setText("" + st.mus0);
		jmh.setText("<" + st.mush + "% health:");
		jm1.setText("" + st.mus1);
		jmax.setText("" + st.max);
		con.setSelected(!st.non_con);
		String str = get(1, "star") + ": ";
		for (int i = 0; i < 4; i++)
			if (i < st.map.stars.length)
				star[i].setText(i + 1 + str + st.map.stars[i] + "%");
			else
				star[i].setText(i + 1 + str + "/");
		Limit lim = st.lim;
		lt.setLimit(lim);
		change(false);
	}

	private void abler(boolean b) {
		bg.setEnabled(b);
		cas.setEnabled(b);
		name.setEnabled(b);
		jhea.setEnabled(b);
		jlen.setEnabled(b);
		jbg.setEnabled(b);
		jcas.setEnabled(b);
		jmax.setEnabled(b);
		con.setEnabled(b);
		mus.setEnabled(b);
		jm0.setEnabled(b);
		jmh.setEnabled(b);
		jm1.setEnabled(b);
		for (JTF jtf : star)
			jtf.setEnabled(b);
		lt.abler(b);
	}

	private void addListeners() {

		bg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				bvp = new BGViewPage(getFront(), pac, sta.bg);
				changePanel(bvp);
			}
		});

		cas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cvp = new CastleViewPage(getFront(), pac.casList(), sta.getCastle());
				changePanel(cvp);
			}
		});

		mus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mp = new MusicPage(getFront(), pac);
				changePanel(mp);
			}
		});

		con.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sta.non_con = !con.isSelected();
				setData(sta);
			}
		});

	}

	private void ini() {
		set(hea);
		set(len);
		set(max);
		add(bg);
		add(cas);
		add(con);
		add(mus);
		set(jhea);
		set(jlen);
		set(jbg);
		set(jcas);
		set(jmax);
		set(name);
		set(jm0);
		set(jmh);
		set(jm1);
		add(lt);
		con.setSelected(true);

		for (int i = 0; i < 4; i++)
			set(star[i] = new JTF());
		addListeners();
		abler(false);
	}

	private void input(JTF jtf, String str) {
		if (sta == null)
			return;
		if (jtf == name) {
			str = str.trim();
			if (str.length() > 0)
				sta.setName(str);
			for (Recd r : Recd.map.values())
				if (r.st == sta)
					r.marked = true;
			return;
		}
		int val = CommonStatic.parseIntN(str);
		if (jtf == jhea) {
			if (val <= 0)
				return;
			sta.health = val;
		}
		if (jtf == jlen) {
			if (val > 8000)
				val = 8000;
			if (val < 2000)
				val = 2000;
			sta.len = val;
		}
		if (jtf == jbg) {
			if (val < 0 || BGStore.getBG(val) == null)
				return;
			if (!pac.usable(val / 1000))
				return;
			sta.bg = val;
		}
		if (jtf == jcas) {
			if (Castles.getCastle(val) == null || !pac.usable(val / 1000))
				jcas.setText("" + sta.getCastle());
			else
				sta.setCast(val);
		}
		if (jtf == jmax) {
			if (val <= 0 || val > 50)
				return;
			sta.max = val;
		}
		for (int i = 0; i < 4; i++)
			if (jtf == star[i]) {
				String[] strs = str.split(" ");
				int[] vals = CommonStatic.parseIntsN(strs[strs.length - 1]);
				val = vals.length == 0 ? -1 : vals[vals.length - 1];
				int[] sr = sta.map.stars;
				if (i == 0 && val <= 0)
					val = 100;
				if (i < sr.length)
					if (val > 0)
						sr[i] = val;
					else
						sta.map.stars = Arrays.copyOf(sr, i);
				else if (val > 0) {
					int[] ans = new int[i + 1];
					for (int j = 0; j < i; j++)
						if (j < sr.length)
							ans[j] = sr[j];
						else
							ans[j] = sr[sr.length - 1];
					ans[i] = val;
					sta.map.stars = ans;
				}
			}
		if (jtf == jm0)
			sta.mus0 = val;
		if (jtf == jmh)
			sta.mush = val;
		if (jtf == jm1)
			sta.mus1 = val;

	}

	private void set(JLabel jl) {
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		jl.setBorder(BorderFactory.createEtchedBorder());
		add(jl);
	}

	private void set(JTF jtf) {
		add(jtf);

		jtf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent fe) {
				if (isAdj())
					return;
				if (jtf == jm0)
					musl = 0;
				if (jtf == jm1)
					musl = 1;
			}

			@Override
			public void focusLost(FocusEvent fe) {
				if (isAdj())
					return;
				input(jtf, jtf.getText());
				setData(sta);
			}
		});

	}

}