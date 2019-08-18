package page.internet;

import static io.WebPack.packlist;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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

import common.util.Data;
import common.util.pack.Pack;
import io.BCJSON;
import io.WebFileIO;
import io.WebPack;
import main.MainBCU;
import main.Opts;
import page.JBTN;
import page.JL;
import page.Page;

public class WebMainPage extends Page {

	private class Selector {

		/**
		 * >0: user mode =-1: main page =-2: installed
		 */
		private int uid;

		private boolean canLoadMain() {
			return uid != -1;
		}

		private boolean canLoadUpdate() {
			return uid != -2;
		}

		private boolean canLoadUser() {
			return uid < 0;
		}

		private List<WebPack> getList() throws IOException {
			if (WebPack.packlist.size() == 0)
				BCJSON.refreshPacks();
			List<WebPack> l = new ArrayList<>();
			for (WebPack wp : packlist.values())
				if (uid == -1)
					l.add(wp);
				else if (uid == -2) {
					if (Pack.map.containsKey(wp.pid) && wp.uid != BCJSON.ID)
						l.add(wp);
				} else if (uid > 0) {
					if (wp.uid == uid && wp.state == 0 || wp.uid == uid && uid == BCJSON.ID)
						l.add(wp);
				}
			if (uid == -1)
				l.sort(WebPack.getComp(sort.getSelectedIndex()));
			else if (uid == -2)
				l.sort(WebPack.getComp(WebPack.SORT_UPDATE));
			else if (uid > 0)
				l.sort(WebPack.getComp(sort.getSelectedIndex()));
			return l;
		}

		private void loadMain() {
			uid = -1;
			load();
		}

		private void loadUpdate() {
			uid = -2;
			load();
		}

		private void loadUser(int u) {
			uid = u;
			load();
		}

	}

	private class WebItem {

		private class ItemDetl extends JPanel {

			private static final long serialVersionUID = 1L;

			private final JLabel icon = new JLabel();
			private final JL name = new JL(obj.name);
			private final JL bver = new JL("BCU " + Data.revVer(obj.bcuver));
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
					String url = BCJSON.WEBSITE + "/downloadpack.php?packid=" + obj.pid;
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

				auth.setLnr(x -> sele.loadUser(obj.uid));

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

				icon.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						obj.icon.load(icon);
					}
				});

				thumb.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						obj.loadThumb(thumb, 0);// TODO
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
				add(bver);

				icon.setBorder(BorderFactory.createEtchedBorder());
				thumb.setBorder(BorderFactory.createEtchedBorder());
				icon.setHorizontalAlignment(SwingConstants.CENTER);
				thumb.setHorizontalAlignment(SwingConstants.CENTER);
				bver.setHorizontalAlignment(SwingConstants.CENTER);
				name.setHorizontalAlignment(SwingConstants.CENTER);
				pkid.setHorizontalAlignment(SwingConstants.CENTER);
				rate0.setHorizontalAlignment(SwingConstants.CENTER);
				rate1.setHorizontalAlignment(SwingConstants.CENTER);

				jsps.getVerticalScrollBar().setUnitIncrement(8);

				auth.setEnabled(sele.canLoadUser());

				Pack p = Pack.map.get(obj.pid);
				if (obj.bcuver > MainBCU.ver) {
					down.setText("BCU too old");
				} else if (p == null)
					down.setText(2, "download");
				else if (p.editable)
					down.setEnabled(false);
				else if (p.version != obj.version)
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
				set(auth, x, y, 600, 150, 400, 50);
				set(pkid, x, y, 400, 150, 200, 50);
				set(down, x, y, 600, 200, 400, 50);
				set(bver, x, y, 400, 200, 200, 50);
				set(rate0, x, y, 400, 250, 200, 50);
				set(rate1, x, y, 400, 300, 200, 50);
				set(vote, x, y, 600, 250, 400, 100);

				set(thumb, x, y, 150, 400, 800, 400);
				obj.loadThumb(thumb, 0);// TODO
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
					String url = BCJSON.WEBSITE + "/downloadpack.php?packid=" + obj.pid;
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

				auth.setLnr(x -> sele.loadUser(obj.uid));

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
				icon.setHorizontalAlignment(SwingConstants.CENTER);
				name.setHorizontalAlignment(SwingConstants.CENTER);
				pkid.setHorizontalAlignment(SwingConstants.CENTER);
				rate0.setHorizontalAlignment(SwingConstants.CENTER);
				rate1.setHorizontalAlignment(SwingConstants.CENTER);

				auth.setEnabled(sele.canLoadUser());

				Pack p = Pack.map.get(obj.pid);
				if (obj.bcuver > MainBCU.ver) {
					down.setText("BCU too old");
				}
				if (p == null)
					down.setText(2, "download");
				else if (p.editable)
					down.setEnabled(false);
				else if (p.version != obj.version)
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
	private final JBTN updt = new JBTN(2, "update");
	private final JBTN rfsh = new JBTN(2, "refresh");
	private final JBTN user = new JBTN(2, "user");
	private final JComboBox<String> sort = new JComboBox<>(get(2, "sort", 4));
	private final JPanel cont = new JPanel();
	private final JScrollPane jsp = new JScrollPane(cont);
	private final JScrollPane jdt = new JScrollPane();

	private final Selector sele = new Selector();

	private WebItem.ItemDetl detl = null;
	private WebItem[] items;
	private int n;
	private boolean changing = false;

	public WebMainPage(Page p) {
		super(p);

		ini();
		sele.loadMain();
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
		load();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(user, x, y, 200, 0, 200, 50);
		set(edit, x, y, 400, 0, 200, 50);
		set(updt, x, y, 600, 0, 200, 50);
		set(main, x, y, 800, 0, 200, 50);
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

		main.setLnr(x -> sele.loadMain());

		updt.setLnr(x -> sele.loadUpdate());

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
				load();
			}
		});

		sort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				load();
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
		add(updt);
		sort.setSelectedIndex(WebPack.SORT_POP);
		jsp.getVerticalScrollBar().setUnitIncrement(8);
		jdt.getVerticalScrollBar().setUnitIncrement(8);
		cont.setLayout(null);
		addListeners();
	}

	private void load() {
		changing = true;
		main.setEnabled(sele.canLoadMain());
		updt.setEnabled(sele.canLoadUpdate());
		cont.removeAll();
		try {
			WebPack[] obj = sele.getList().toArray(new WebPack[0]);
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
