package page.info.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import io.Reader;
import page.JBTN;
import page.JTF;
import page.JTG;
import page.Page;
import page.pack.CharaGroupPage;
import page.pack.LvRestrictPage;
import page.view.BGViewPage;
import page.view.CastleViewPage;
import page.view.MusicPage;
import util.pack.BGStore;
import util.pack.Pack;
import util.stage.Castles;
import util.stage.Limit;
import util.stage.Recd;
import util.stage.Stage;

class HeadEditTable extends Page {

	private static final long serialVersionUID = 1L;

	private static String[] infs, limits, rarity;

	static {
		redefine();
	}

	protected static void redefine() {
		infs = Page.get(1, "ht0", 6);
		limits = Page.get(1, "ht1", 7);
		rarity = new String[] { "N", "EX", "R", "SR", "UR", "LR" };
	}

	private final JLabel hea = new JLabel(infs[0]);
	private final JLabel len = new JLabel(infs[1]);
	private final JLabel rar = new JLabel(limits[0]);
	private final JBTN cgb = new JBTN(1, "ht15");
	private final JBTN lrb = new JBTN(1, "ht16");
	private final JBTN mus = new JBTN(1, "mus");
	private final JLabel max = new JLabel(infs[2]);
	private final JBTN bg = new JBTN(infs[4]);
	private final JBTN cas = new JBTN(infs[5]);
	private final JTF name = new JTF();
	private final JTF jhea = new JTF();
	private final JTF jlen = new JTF();
	private final JTF jbg = new JTF();
	private final JTF jcas = new JTF();
	private final JTF jmax = new JTF();
	private final JTF jcmin = new JTF();
	private final JTF jnum = new JTF();
	private final JTF jcmax = new JTF();
	private final JTF jcg = new JTF();
	private final JTF jlr = new JTF();
	private final JTF jm0 = new JTF();
	private final JTF jmh = new JTF();
	private final JTF jm1 = new JTF();
	private final JTG con = new JTG(1, "ht03");
	private final JTG one = new JTG(1, "ht12");
	private final JTG[] brars = new JTG[6];
	private final JTF[] star = new JTF[4];

	private Stage sta;
	private final Pack pac;
	private boolean changing = false;
	private BGViewPage bvp;
	private CastleViewPage cvp;
	private CharaGroupPage cgp;
	private LvRestrictPage lrp;
	private MusicPage mp;

	private int musl = 0;

	protected HeadEditTable(Page p, Pack pack) {
		super(p);
		pac = pack;
		ini();
	}

	@Override
	protected void renew() {
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
		if (cgp != null) {
			jcg.setText("" + cgp.cg);
			input(jcg, cgp.cg == null ? "-1" : cgp.cg.toString());
		}
		if (lrp != null) {
			jlr.setText("" + lrp.lr);
			input(jlr, lrp.lr == null ? "-1" : lrp.lr.toString());
		}
		if (mp != null) {
			JTF jtf = musl == 0 ? jm0 : jm1;
			jtf.setText("" + mp.getSelected());
			input(jtf, "" + mp.getSelected());
		}
		bvp = null;
		cvp = null;
		cgp = null;
		lrp = null;
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
		set(rar, x, y, 0, 150, w, 50);
		for (int i = 0; i < brars.length; i++)
			set(brars[i], x, y, w + w * i, 150, w, 50);
		set(cgb, x, y, w * 4, 200, w, 50);
		set(jcg, x, y, w * 5, 200, w, 50);
		set(lrb, x, y, w * 6, 200, w, 50);
		set(jlr, x, y, w * 7, 200, w, 50);
		for (int i = 0; i < 4; i++)
			set(star[i], x, y, w * (2 + i), 0, w, 50);
		set(jcmin, x, y, 0, 200, w, 50);
		set(jcmax, x, y, w, 200, w, 50);
		set(jnum, x, y, w * 2, 200, w, 50);
		set(one, x, y, w * 3, 200, w, 50);
	}

	protected void setData(Stage st) {
		sta = st;
		abler(st != null);
		if (st == null)
			return;
		changing = true;
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
		String str = Page.get(1, "star") + ": ";
		for (int i = 0; i < 4; i++)
			if (i < st.map.stars.length)
				star[i].setText(i + 1 + str + st.map.stars[i] + "%");
			else
				star[i].setText(i + 1 + str + "/");
		Limit lim = st.lim;
		if (lim.rare > 0) {
			for (int i = 0; i < brars.length; i++)
				brars[i].setSelected(((lim.rare >> i) & 1) > 0);
		} else {
			for (int i = 0; i < brars.length; i++)
				brars[i].setSelected(true);
		}
		jcmax.setText(limits[4] + ": " + sta.lim.max);
		jcmin.setText(limits[3] + ": " + sta.lim.min);
		jnum.setText(limits[1] + ": " + sta.lim.num);
		jcg.setText("" + sta.lim.group);
		jlr.setText("" + sta.lim.lvr);
		one.setSelected(sta.lim.line == 1);
		changing = false;
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
		jcmin.setEnabled(b);
		jnum.setEnabled(b);
		jcmax.setEnabled(b);
		con.setEnabled(b);
		one.setEnabled(b);
		cgb.setEnabled(b);
		jcg.setEnabled(b);
		lrb.setEnabled(b);
		jlr.setEnabled(b);
		mus.setEnabled(b);
		jm0.setEnabled(b);
		jmh.setEnabled(b);
		jm1.setEnabled(b);
		for (JTG jtb : brars)
			jtb.setEnabled(b);
		for (JTF jtf : star)
			jtf.setEnabled(b);
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

		cgb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cgp = new CharaGroupPage(getFront(), pac, false);
				changePanel(cgp);
			}
		});

		lrb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lrp = new LvRestrictPage(getFront(), pac, false);
				changePanel(lrp);
			}
		});

		mus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mp = new MusicPage(getFront());
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

		one.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sta.lim.line = one.isSelected() ? 1 : 0;
				setData(sta);
			}
		});

		for (int i = 0; i < brars.length; i++) {
			int I = i;
			brars[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (changing)
						return;
					sta.lim.rare ^= 1 << I;
					setData(sta);
				}

			});
		}
	}

	private void ini() {
		set(hea);
		set(len);
		set(max);
		set(rar);
		add(cgb);
		add(lrb);
		add(bg);
		add(cas);
		add(con);
		add(one);
		add(mus);
		set(jhea);
		set(jlen);
		set(jbg);
		set(jcas);
		set(jmax);
		set(name);
		set(jcmin);
		set(jcmax);
		set(jnum);
		set(jcg);
		set(jlr);
		set(jm0);
		set(jmh);
		set(jm1);
		con.setSelected(true);
		for (int i = 0; i < brars.length; i++) {
			add(brars[i] = new JTG(rarity[i]));
			brars[i].setSelected(true);
		}
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
		int val = Reader.parseIntN(str);
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
				int[] vals = Reader.parseIntsN(strs[strs.length - 1]);
				val = vals.length == 0 ? -1 : vals[vals.length - 1];
				int[] sr = sta.map.stars;
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
		if (jtf == jcmax) {
			if (val < 0)
				return;
			sta.lim.max = val;
		}
		if (jtf == jcmin) {
			if (val < 0)
				return;
			sta.lim.min = val;
		}
		if (jtf == jnum) {
			if (val < 0 || val > 50)
				return;
			sta.lim.num = val;
		}
		if (jtf == jcg)
			sta.lim.group = pac.mc.groups.get(val);
		if (jtf == jlr)
			sta.lim.lvr = pac.mc.lvrs.get(val);

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
				if (changing)
					return;
				changing = true;
				if (jtf == jm0)
					musl = 0;
				if (jtf == jm1)
					musl = 1;
				changing = false;
			}

			@Override
			public void focusLost(FocusEvent fe) {
				if (changing)
					return;
				input(jtf, jtf.getText());
				setData(sta);
			}
		});

	}

}