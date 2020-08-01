package page.info.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import common.CommonStatic;
import common.util.pack.BGStore;
import common.util.pack.MusicStore;
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
	private final JL loop = new JL(1, "lop");
	private final JL loop1 = new JL(1, "lop1");
	private final JTF lop = new JTF();
	private final JTF lop1 = new JTF();
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
		set(mus, x, y, 0, 150, w, 50);
		set(jm0, x, y, w , 150, w, 50);
		set(loop, x, y, w * 2, 150, w, 50);
		set(lop, x, y, w * 3, 150, w, 50);
		set(jmh, x, y, w * 4, 150, w, 50);
		set(jm1, x, y, w * 5, 150, w, 50);
		set(loop1, x, y, w * 6, 150, w, 50);
		set(lop1, x, y, w * 7, 150, w, 50);
		for (int i = 0; i < 4; i++)
			set(star[i], x, y, w * (2 + i), 0, w, 50);
		set(lt, x, y, 0, 200, 1400, 100);
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
		
		lop.setText(convertTime(sta.loop0));
		lop1.setText(convertTime(sta.loop1));
		
		if(sta.mus0 != -1) {
			lop.setEnabled(true);
			getMusTime(sta.mus0, lop);
		} else {
			lop.setText("00:00.000");
			lop.setToolTipText("No music");
			sta.loop0 = 0;
			lop.setEnabled(false);
		}
		
		if(sta.mus1 != -1) {
			lop1.setEnabled(true);
			getMusTime(sta.mus1, lop1);
		} else {
			lop1.setText("00:00.000");
			lop1.setToolTipText("No music");
			sta.loop1 = 0;
			lop1.setEnabled(false);
		}
		
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
		set(loop);
		set(lop);
		set(loop1);
		set(lop1);
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
		if (jtf == jm0) {
			sta.mus0 = val;
			
			if(sta.mus0 != -1) {
				lop.setEnabled(true);
				getMusTime(sta.mus0, lop);
			} else {
				lop.setText("00:00.000");
				sta.loop0 = 0;
				lop.setEnabled(false);
			}
		}
		
		if (jtf == jmh)
			sta.mush = val;
		
		if (jtf == jm1) {
			sta.mus1 = val;
			
			if(sta.mus1 != -1) {
				lop1.setEnabled(true);
				getMusTime(sta.mus1, lop1);
			} else {
				lop1.setText("00:00.000");
				sta.loop1 = 0;
				lop1.setEnabled(false);
			}
		}
		
		if (jtf == lop) {
			int tim = toMilli(jtf.getText());
			
			if(tim != -1) {
				sta.loop0 = tim;
			}
			
			lop.setText(convertTime(sta.loop0));
		}
		
		if (jtf == lop1) {
			int tim = toMilli(jtf.getText());
			
			if(tim != -1) {
				sta.loop1 = tim;
			}
			
			lop1.setText(convertTime(sta.loop1));
		}
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
	
	private void getMusTime(int mus, JTF jtf) {
		
		File f = MusicStore.getMusic(mus);
		
		if(f == null || !f.exists()) {
			jtf.setToolTipText("Music not found");
			return;
		}
		
		try {
			long duration = CommonStatic.def.getMusicLength(f);
			
			if(duration == -1) {
				jtf.setToolTipText("Invalid Format");
			} else if(duration == -2) {
				jtf.setToolTipText("Unsupported Format");
			} else if(duration == -3) {
				jtf.setToolTipText("Can't get duration");
			} else if(duration >= 0) {
				jtf.setToolTipText(convertTime(duration));
			} else {
				jtf.setToolTipText("Unknown error "+duration);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String convertTime(long milli) {
		long min = milli / 60 / 1000;
		
		double time = (double) milli - (double) min * 60000;
		
		time /= 1000;
		
		DecimalFormat df = new DecimalFormat("#.###");
		
		double s = Double.parseDouble(df.format(time));
		
		if(s >= 60) {
			s -= 60;
			min += 1;
		}
		
		if(s < 10) {
			return min + ":"+"0"+df.format(s);
		} else {
			return min + ":"+df.format(s);
		}
	}
	
	private int toMilli(String time) {
		try {
			int [] times = CommonStatic.parseIntsN(time);
			
			if(times.length == 1) {
				return times[0] * 1000;
			} else if(times.length == 2) {
				return (times[0] * 60 + times[1]) * 1000;
			} else if(times.length == 3) {
				if(times[2] < 1000) {
					return (times[0] * 60 + times[1]) * 1000 + times[2];
				} else {
					String decimal = Integer.toString(times[2]).substring(0, 3);
					return (times[0] * 60 + times[1]) * 1000 + Integer.parseInt(decimal);
				}
			} else {
				return -1;
			}
		} catch(Exception e) {
			return -1;
		}
	}

}