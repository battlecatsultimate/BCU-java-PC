package page.internet;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import io.WebFileIO;
import io.WebPack;
import main.Opts;
import page.JBTN;
import page.JL;
import page.Page;
import util.pack.Pack;

public class WebMainPage extends Page {

	private class WebItem {

		private class ItemDetl extends JPanel {

			private static final long serialVersionUID = 1L;

			private final JLabel icon = new JLabel();
			private final JL name = new JL(obj.name);
			private final JL pkid = new JL(obj.pid + "-" + obj.version);
			private final JL rate0 = new JL("point: " + obj.getRate_0());
			private final JL rate1 = new JL("star: " + obj.getRate_1() * 0.01);
			private final JBTN down = new JBTN();
			private final JBTN auth = new JBTN(obj.author);

			private final JLabel thumb = new JLabel();
			private final JTextPane desp = new JTextPane();
			private final JScrollPane jsps = new JScrollPane(desp);
			private final JSlider vote = new JSlider();

			private ItemDetl() {
				init();
				addListeners();
			}

			private void addListeners() {

				down.setLnr(x -> {
					File f = new File("./pack/" + obj.pid + ".bcupack");
					String url = "http://battlecatsultimate.cf/downloadpack.php?packid=" + obj.pid;
					if (WebFileIO.download(url, f, null)) {
						Pack pac = Pack.read(f);
						down.setText(2, "downloaded");
						down.setEnabled(false);
						obj.version = pac.version;
						Opts.success("download success");
					} else
						Opts.dloadErr("");
					prev.down.setText(down.getText());
				});

				auth.setLnr(x -> load(obj.uid));

				vote.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent arg0) {
						if (changing || vote.getValueIsAdjusting())
							return;
						int vot = vote.getValue();
						int[][] ret = BCJSON.rate(obj.pid, vot);
						if (ret == null) {
							changing = true;
							vote.setValue(obj.vote);
							changing = false;
						} else {
							obj.rate = ret;
							obj.vote = vot;
							rate0.setText("point: " + obj.getRate_0());
							rate1.setText("star: " + obj.getRate_1() * 0.01);
							prev.rate0.setText("point: " + obj.getRate_0());
							prev.rate1.setText("star: " + obj.getRate_1() * 0.01);
						}
					}

				});
			}

			private void init() {
				setBackground(Color.WHITE);
				setLayout(null);
				add(thumb);
				add(jsps);
				add(vote);
				add(icon);
				add(name);
				add(auth);
				add(pkid);
				add(rate0);
				add(rate1);
				add(down);

				icon.setBorder(BorderFactory.createEtchedBorder());
				thumb.setBorder(BorderFactory.createEtchedBorder());

				name.setHorizontalAlignment(SwingConstants.CENTER);
				pkid.setHorizontalAlignment(SwingConstants.CENTER);
				rate0.setHorizontalAlignment(SwingConstants.CENTER);
				rate1.setHorizontalAlignment(SwingConstants.CENTER);

				jsps.getVerticalScrollBar().setUnitIncrement(8);

				auth.setEnabled(uid == -1);

				Pack p = Pack.map.get(obj.pid);
				if (p == null)
					down.setText(2, "download");
				else if (p.editable)
					down.setEnabled(false);
				else if (p.version < obj.version)
					down.setText(p.version + " > " + obj.version);
				else {
					down.setText(2, "downloaded");
					down.setEnabled(false);
				}

				desp.setEditable(false);
				desp.setText(obj.getDesp());

				vote.setMinimum(0);
				vote.setMaximum(5);
				vote.setMajorTickSpacing(1);
				vote.setLabelTable(dict);
				vote.setPaintTicks(true);
				vote.setPaintLabels(true);
				vote.setValue(obj.vote);
			}

			private int resize$detl(int x, int y) {
				set(icon, x, y, 50, 50, 300, 300);
				obj.icon.load(icon);
				set(name, x, y, 400, 50, 600, 100);
				set(auth, x, y, 400, 150, 200, 50);
				set(pkid, x, y, 600, 150, 200, 50);
				set(down, x, y, 800, 150, 200, 50);
				set(rate0, x, y, 400, 250, 200, 50);
				set(rate1, x, y, 400, 300, 200, 50);
				set(vote, x, y, 600, 250, 400, 100);

				set(thumb, x, y, 150, 400, 800, 400);
				set(jsps, x, y, 50, 850, 1000, 800);
				return 1700;
			}

		}

		private class ItemPrev extends JPanel {

			private static final long serialVersionUID = 1L;

			private final JLabel icon = new JLabel();
			private final JL name = new JL(obj.name);
			private final JL pkid = new JL(obj.pid + "-" + obj.version);
			private final JL rate0 = new JL("point: " + obj.getRate_0());
			private final JL rate1 = new JL("star: " + obj.getRate_1() * 0.01);
			private final JBTN down = new JBTN();
			private final JBTN auth = new JBTN(obj.author);

			private ItemPrev() {
				init();
				addListeners();
			}

			private void addListeners() {
				down.setLnr(x -> {
					File f = new File("./pack/" + obj.pid + ".bcupack");
					String url = "http://battlecatsultimate.cf/downloadpack.php?packid=" + obj.pid;
					if (WebFileIO.download(url, f, null)) {
						Pack pac = Pack.read(f);
						down.setText(2, "downloaded");
						down.setEnabled(false);
						obj.version = pac.version;
						Opts.success("download success");
					} else
						Opts.dloadErr("");
					if (detail != null)
						detail.down.setText(down.getText());
				});

				auth.setLnr(x -> load(obj.uid));

				icon.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						obj.icon.load(icon);
					}
				});
			}

			private void clicked() {
				loadDetail();
				jdt.setViewportView(detail);
				detl = detail;
			}

			private void init() {
				setBackground(Color.WHITE);
				setLayout(null);

				add(icon);
				add(name);
				add(auth);
				add(pkid);
				add(rate0);
				add(rate1);
				add(down);

				icon.setBorder(BorderFactory.createEtchedBorder());
				name.setHorizontalAlignment(SwingConstants.CENTER);
				pkid.setHorizontalAlignment(SwingConstants.CENTER);
				rate0.setHorizontalAlignment(SwingConstants.CENTER);
				rate1.setHorizontalAlignment(SwingConstants.CENTER);

				auth.setEnabled(uid == -1);

				Pack p = Pack.map.get(obj.pid);
				if (p == null)
					down.setText(2, "download");
				else if (p.editable)
					down.setEnabled(false);
				else if (p.version < obj.version)
					down.setText(p.version + " > " + obj.version);
				else {
					down.setText(2, "downloaded");
					down.setEnabled(false);
				}

			}

			private void resize$prev(int x, int y, int xh) {
				set(this, x, y, 0, xh, 600, 150);
				set(icon, x, y, 0, 0, 150, 150);
				obj.icon.load(icon);
				set(name, x, y, 150, 0, 450, 50);
				set(auth, x, y, 150, 50, 300, 50);
				set(pkid, x, y, 450, 50, 150, 50);
				set(rate0, x, y, 150, 100, 150, 50);
				set(rate1, x, y, 300, 100, 150, 50);
				set(down, x, y, 450, 100, 150, 50);

			}

		}

		private final WebPack obj;
		private final ItemPrev prev;
		private ItemDetl detail;

		private WebItem(WebPack wp) {
			obj = wp;
			cont.add(prev = new ItemPrev());

		}

		private void loadDetail() {
			detail = new ItemDetl();
		}

	}

	private static final long serialVersionUID = 1L;

	private static final Hashtable<Integer, JLabel> dict = new Hashtable<>();

	static {
		dict.put(0, new JLabel("X"));
		dict.put(1, new JLabel("-2"));
		dict.put(2, new JLabel("-1"));
		dict.put(3, new JLabel("0"));
		dict.put(4, new JLabel("1"));
		dict.put(5, new JLabel("2"));
	}

	private final JBTN back = new JBTN(0, "back");
	private final JBTN edit = new JBTN(2, "manage");
	private final JBTN main = new JBTN(2, "full");
	private final JBTN rfsh = new JBTN(2, "refresh");
	private final JBTN user = new JBTN(2, "user");
	private final JComboBox<String> sort = new JComboBox<>(get(2, "sort", 4));
	private final JPanel cont = new JPanel();
	private final JScrollPane jsp = new JScrollPane(cont);
	private final JScrollPane jdt = new JScrollPane();

	private WebItem.ItemDetl detl = null;

	private WebItem[] items;

	private int uid, n;
	private boolean changing = false;

	public WebMainPage(Page p) {
		super(p);

		ini();
		load(-1);
		resized();
	}

	@Override
	protected void leave() {
		WebPack.clear();
	}

	@Override
	protected void mousePressed(MouseEvent me) {
		Component c = (Component) me.getSource();
		for (WebItem wi : items)
			if (c.getParent() == wi.prev)
				wi.prev.clicked();
	}

	@Override
	protected void renew() {
		load(uid);
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(user, x, y, 250, 0, 200, 50);
		set(edit, x, y, 500, 0, 200, 50);
		set(main, x, y, 900, 0, 200, 50);
		set(rfsh, x, y, 1150, 0, 200, 50);
		set(jsp, x, y, 50, 100, 650, 1150);
		set(jdt, x, y, 750, 100, 1500, 1150);
		set(sort, x, y, 1400, 0, 400, 50);

		int dh = 50, xh = 0;

		for (int i = 0; i < n; i++) {
			items[i].prev.resize$prev(x, y, xh);
			xh += 150 + dh;
		}

		if (detl != null) {
			int h = detl.resize$detl(x, y);
			detl.setPreferredSize(size(x, y, 1450, h).toDimension());
		}

		cont.setPreferredSize(size(x, y, 600, xh).toDimension());
	}

	private void addListeners() {
		back.setLnr(x -> changePanel(getFront()));

		main.setLnr(x -> load(-1));

		edit.setLnr(x -> changePanel(new WebUserPage(getThis())));

		user.setLnr(x -> changePanel(new UserSettingsPage(getThis())));

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
		add(user);
		add(jdt);
		sort.setSelectedIndex(WebPack.SORT_POP);
		jsp.getVerticalScrollBar().setUnitIncrement(8);
		jdt.getVerticalScrollBar().setUnitIncrement(8);
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
			items = new WebItem[n];

			for (int i = 0; i < n; i++)
				items[i] = new WebItem(obj[i]);
		} catch (IOException e) {
			e.printStackTrace();
			changePanel(new LoginPage(getFront()));
		}
		changing = false;
	}

}
