package page.internet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import io.BCJSON;
import io.WebPack;
import main.Opts;
import page.JBTN;
import page.JL;
import page.Page;
import util.pack.Pack;

public class WebMainPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN edit = new JBTN(2, "manage");
	private final JBTN main = new JBTN(2, "full");
	private final JBTN rfsh = new JBTN(2, "refresh");
	private final JComboBox<String> sort = new JComboBox<>(get(2, "sort", 4));
	private final JPanel cont = new JPanel();
	private final JScrollPane jsp = new JScrollPane(cont);

	private JTextPane[] desp;
	private JScrollPane[] jsps;
	private JLabel[] name, pkid, title, rate0, rate1;
	private JBTN[] down, auth;
	private JSlider[] vote;

	private int uid, n;
	private boolean changing = false;

	public WebMainPage(Page p) {
		super(p);

		ini();
		load(-1);
		resized();
	}

	@Override
	protected void renew() {
		load(uid);
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(edit, x, y, 400, 0, 200, 50);
		set(main, x, y, 800, 0, 200, 50);
		set(rfsh, x, y, 1200, 0, 200, 50);
		set(jsp, x, y, 50, 200, 2200, 1050);
		set(sort, x, y, 1400, 0, 400, 50);

		set(title[0], x, y, 50, 100, 600, 50);
		set(title[1], x, y, 50, 150, 300, 50);
		set(title[2], x, y, 350, 150, 150, 50);
		set(title[3], x, y, 500, 150, 150, 50);
		set(title[4], x, y, 650, 100, 1200, 100);
		set(title[5], x, y, 1850, 100, 100, 100);
		set(title[6], x, y, 1950, 100, 250, 100);

		int h = 100, hh = 50, dh = 50, xh = 0;

		for (int i = 0; i < n; i++) {
			set(name[i], x, y, 0, xh, 600, hh);
			set(auth[i], x, y, 0, xh + hh, 300, hh);
			set(pkid[i], x, y, 300, xh + hh, 150, hh);
			set(down[i], x, y, 450, xh + hh, 150, hh);
			set(jsps[i], x, y, 600, xh, 1150, h);
			set(rate0[i], x, y, 1750, xh, 150, hh);
			set(rate1[i], x, y, 1750, xh + hh, 150, hh);
			set(vote[i], x, y, 1900, xh, 250, h);
			xh += h + dh;
		}
		cont.setPreferredSize(size(x, y, 2150, xh).toDimension());
	}

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		main.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				load(-1);
			}
		});

		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new WebUserPage(getThis()));
			}
		});

		rfsh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					BCJSON.refreshPacks();
				} catch (IOException e) {
					changePanel(new LoginPage(getFront()));
					e.printStackTrace();
				}
				load(uid);
			}
		});

		sort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				load(uid);
			}

		});

	}

	private void ini() {
		add(back);
		add(jsp);
		add(sort);
		add(main);
		add(edit);
		add(rfsh);
		sort.setSelectedIndex(WebPack.SORT_POP);
		jsp.getVerticalScrollBar().setUnitIncrement(8);
		title = new JLabel[7];
		for (int i = 0; i < 7; i++) {
			add(title[i] = new JL(2, "t" + i));
			title[i].setHorizontalAlignment(SwingConstants.CENTER);
			title[i].setBorder(BorderFactory.createEtchedBorder());
		}
		cont.setLayout(null);
		addListeners();
	}

	private void load(int author) {
		changing = true;
		uid = author;
		main.setEnabled(uid != -1);
		cont.removeAll();
		try {
			WebPack[] obj = BCJSON.getPacks(author, sort.getSelectedIndex()).toArray(new WebPack[0]);
			SimpleAttributeSet attr = new SimpleAttributeSet();
			StyleConstants.setAlignment(attr, StyleConstants.ALIGN_CENTER);
			n = obj.length;

			jsps = new JScrollPane[n];
			name = new JLabel[n];
			desp = new JTextPane[n];
			auth = new JBTN[n];
			pkid = new JLabel[n];
			rate0 = new JLabel[n];
			rate1 = new JLabel[n];
			down = new JBTN[n];
			vote = new JSlider[n];

			Hashtable<Integer, JLabel> dict = new Hashtable<>();
			dict.put(0, new JLabel("X"));
			dict.put(1, new JLabel("-2"));
			dict.put(2, new JLabel("-1"));
			dict.put(3, new JLabel("0"));
			dict.put(4, new JLabel("1"));
			dict.put(5, new JLabel("2"));

			for (int i = 0; i < obj.length; i++) {

				cont.add(name[i] = new JLabel(obj[i].name));
				name[i].setHorizontalAlignment(SwingConstants.CENTER);
				name[i].setBorder(BorderFactory.createEtchedBorder());

				cont.add(jsps[i] = new JScrollPane(desp[i] = new JTextPane()));
				desp[i].setEditable(false);
				desp[i].setText(obj[i].desp);

				cont.add(auth[i] = new JBTN(obj[i].author));
				auth[i].setEnabled(uid == -1);

				cont.add(pkid[i] = new JLabel(obj[i].pid + "-" + obj[i].version));
				pkid[i].setHorizontalAlignment(SwingConstants.CENTER);
				pkid[i].setBorder(BorderFactory.createEtchedBorder());

				cont.add(rate0[i] = new JLabel("point: " + obj[i].getRate_0()));
				rate0[i].setHorizontalAlignment(SwingConstants.CENTER);
				rate0[i].setBorder(BorderFactory.createEtchedBorder());

				cont.add(rate1[i] = new JLabel("star: " + obj[i].getRate_1() * 0.01));
				rate1[i].setHorizontalAlignment(SwingConstants.CENTER);
				rate1[i].setBorder(BorderFactory.createEtchedBorder());

				cont.add(down[i] = new JBTN());

				Pack p = Pack.map.get(obj[i].pid);
				if (p == null)
					down[i].setText(2, "download");
				else if (p.editable)
					down[i].setEnabled(false);
				else if (p.version < obj[i].version)
					down[i].setText(p.version + " > " + obj[i].version);
				else {
					down[i].setText(2, "downloaded");
					down[i].setEnabled(false);
				}

				cont.add(vote[i] = new JSlider());
				vote[i].setMinimum(0);
				vote[i].setMaximum(5);
				vote[i].setMajorTickSpacing(1);
				vote[i].setLabelTable(dict);
				vote[i].setPaintTicks(true);
				vote[i].setPaintLabels(true);
				vote[i].setValue(obj[i].vote);

				String url = obj[i].url;
				int I = i;

				down[i].setLnr(x -> {
					File f = new File("./pack/" + obj[I].pid + ".bcupack");
					if (BCJSON.download(url, f, null)) {
						Pack pac = Pack.read(f);
						down[I].setText(2, "downloaded");
						down[I].setEnabled(false);
						obj[I].version = pac.version;
						Opts.success("download success");
					} else
						Opts.dloadErr("");
				});

				auth[i].setLnr(x -> load(obj[I].uid));

				vote[i].addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent arg0) {
						if (changing || vote[I].getValueIsAdjusting())
							return;
						int vot = vote[I].getValue();
						int[][] ret = BCJSON.rate(obj[I].pid, vot);
						if (ret == null) {
							changing = true;
							vote[I].setValue(obj[I].vote);
							changing = false;
						} else {
							obj[I].rate = ret;
							obj[I].vote = vot;
							rate0[I].setText("point: " + obj[I].getRate_0());
							rate1[I].setText("star: " + obj[I].getRate_1() * 0.01);
						}
					}

				});

			}
		} catch (IOException e) {
			e.printStackTrace();
			changePanel(new LoginPage(getFront()));
		}
		changing = false;
	}

}
