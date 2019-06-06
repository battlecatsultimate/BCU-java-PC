package page.internet;

import static io.WebPack.packlist;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import io.BCJSON;
import io.WebPack;
import main.Opts;
import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;
import page.support.Importer;
import util.Data;
import util.pack.Pack;

public class WebUserPage extends Page {

	private class WebItem {

		private class ItemDetl extends JPanel {

			private static final long serialVersionUID = 1L;

			private final JTextPane desp = new JTextPane();
			private final JScrollPane jsps = new JScrollPane(desp);
			private final JTF name = new JTF(obj.name);
			private final JBTN subm = new JBTN(2, "update");
			private final JBTN dele = new JBTN(2, (obj.state == 1 ? "un" : "") + "delete");
			private final JBTN reup = new JBTN(2, "reupload");
			private final JBTN uicn = new JBTN(2, "icon");
			private final JBTN uthm = new JBTN(2, "image");
			private final JBTN udel = new JBTN(2, "delimg");
			private final JL pkid = new JL(obj.pid + "-" + obj.version);

			private final JLabel icon = new JLabel();
			private final JLabel thumb = new JLabel();

			private ItemDetl() {
				init();
				addListeners();
			}

			private void addListeners() {

				uicn.setLnr(x -> {
					if (!upload("add pack icon", "icon", 300, 300))
						return;
					obj.icon = new WebPack.WebImg(obj.pid + "/img/icon.png");
					obj.icon.load(icon);
				});

				uthm.setLnr(x -> {
					int id = obj.getNextThumbID();
					if (id == -1) {
						Opts.servErr("failed to upload");
						return;
					}
					if (!upload("add pack thumbnail", Data.trio(id), 400, 800))
						return;
					WebPack.WebImg wi = new WebPack.WebImg(obj.pid + "/img/" + Data.trio(id) + ".png");
					obj.thumbs.add(wi);
					wi.load(thumb);
				});

				subm.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						String s0 = name.getText().trim();
						String s1 = desp.getText().trim();
						boolean b0 = s0.length() == 0;
						boolean b1 = s1.length() == 0;
						boolean b2 = false;
						if (!b0 && !b1)
							b2 = BCJSON.update(obj.pid, s0, s1);
						if (b0 || !b2)
							name.setText(obj.name);
						if (b1 || !b2)
							desp.setText(obj.getDesp());
						prev.name.setText(name.getText());
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

				dele.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						boolean b = BCJSON.delete(obj.pid);
						if (!b) {
							Opts.servErr("failed to edit");
							return;
						}
						dele.setText(2, (obj.state == 1 ? "un" : "") + "delete");
						prev.visi.setText(2, (obj.state == 1 ? "un" : "") + "visible");
					}

				});

				reup.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						boolean b = BCJSON.reversion(obj.pid);
						if (!b) {
							Opts.servErr("failed to upload");
							return;
						}
						load();
					}

				});
			}

			private void init() {
				setBackground(Color.WHITE);
				setLayout(null);
				add(jsps);
				add(name);
				add(subm);
				add(dele);
				add(reup);
				add(pkid);
				add(icon);
				add(thumb);
				add(uicn);
				add(uthm);
				add(udel);

				icon.setBorder(BorderFactory.createEtchedBorder());
				thumb.setBorder(BorderFactory.createEtchedBorder());

				desp.setText(obj.getDesp());

				pkid.setHorizontalAlignment(SwingConstants.CENTER);
				icon.setHorizontalAlignment(SwingConstants.CENTER);
				thumb.setHorizontalAlignment(SwingConstants.CENTER);

				Pack p = Pack.map.get(obj.pid);
				reup.setEnabled(p != null && p.editable);
			}

			private int resize$detl(int x, int y) {
				set(icon, x, y, 50, 50, 300, 300);
				obj.icon.load(icon);
				set(name, x, y, 400, 50, 450, 50);
				set(pkid, x, y, 400, 100, 200, 50);
				set(dele, x, y, 650, 100, 200, 50);
				set(subm, x, y, 400, 150, 200, 50);
				set(reup, x, y, 650, 150, 200, 50);
				set(uicn, x, y, 400, 200, 200, 50);
				set(uthm, x, y, 650, 300, 200, 50);
				set(udel, x, y, 400, 300, 200, 50);
				set(thumb, x, y, 50, 400, 800, 400);
				obj.loadThumb(thumb, 0);// TODO
				set(jsps, x, y, 900, 50, 750, 750);
				return 850;
			}

			private boolean upload(String title, String name, int x, int y) {
				Importer ipt = new Importer(title);
				File f = ipt.file;
				BufferedImage bimg = ipt.getImg();
				if (bimg == null) {
					Opts.loadErr("It's not an valid image");
					return false;
				}
				if (f.length() > (1 << 20)) {
					Opts.loadErr("File should be within 1MB");
					return false;
				}
				if (!BCJSON.uploadImg(obj.pid, name, f)) {
					Opts.servErr("failed to upload");
					return false;
				}
				return true;
			}

		}

		private class ItemPrev extends JPanel {

			private static final long serialVersionUID = 1L;

			private final JL name = new JL(obj.name);
			private final JL pkid = new JL(obj.pid + "-" + obj.version);
			private final JL visi = new JL();

			private ItemPrev() {
				setBackground(Color.WHITE);
				setLayout(null);
				add(name);
				add(pkid);
				add(visi);
				visi.setText(2, (obj.state == 1 ? "in" : "") + "visible");

				pkid.setHorizontalAlignment(SwingConstants.CENTER);
				visi.setHorizontalAlignment(SwingConstants.CENTER);

			}

			private void clicked() {
				loadDetail();
				jdt.setViewportView(detail);
				detl = detail;
			}

			private void resize(int x, int y, int xh) {
				set(this, x, y, 0, xh, 400, 100);
				set(name, x, y, 0, 0, 400, 50);
				set(pkid, x, y, 0, 50, 200, 50);
				set(visi, x, y, 200, 50, 200, 50);
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

	private final JBTN back = new JBTN(0, "back");
	private final JPanel cont = new JPanel();
	private final JScrollPane jsp = new JScrollPane(cont);

	private final JTextPane ndesc = new JTextPane();
	private final JScrollPane njsp = new JScrollPane(ndesc);
	private final JTF nname = new JTF();
	private final JBTN nadd = new JBTN(2, "submit");
	private final JComboBox<Pack> npac = new JComboBox<>();

	private final JScrollPane jdt = new JScrollPane();

	private WebItem.ItemDetl detl;
	private WebItem[] items;
	private int n;

	public WebUserPage(Page p) {
		super(p);

		ini();
		load();
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
		set(jsp, x, y, 50, 400, 450, 850);
		set(jdt, x, y, 550, 400, 1700, 850);
		set(nname, x, y, 50, 250, 600, 50);
		set(npac, x, y, 50, 300, 600, 50);
		set(njsp, x, y, 650, 250, 1400, 100);
		set(nadd, x, y, 2050, 250, 200, 50);
		int dh = 50, xh = 0;
		for (int i = 0; i < n; i++) {
			items[i].prev.resize(x, y, xh);
			xh += 100 + dh;
		}
		if (detl != null) {
			int h = detl.resize$detl(x, y);
			detl.setPreferredSize(size(x, y, 1650, h).toDimension());
		}
		cont.setPreferredSize(size(x, y, 400, xh).toDimension());
	}

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		nadd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Pack p = (Pack) npac.getSelectedItem();
				if (p == null)
					return;
				String strn = nname.getText().trim();
				String strd = ndesc.getText().trim();
				if (strn.length() == 0)
					strn = "title";
				if (strd.length() == 0)
					strd = "description";
				int res = BCJSON.initUpload(p.id, strn, strd);
				if (res == 0)
					load();
				else if (res == 211)
					Opts.servErr("repeat pack ID, please contact developer");
				else
					Opts.servErr("unable to upload pack");

			}
		});

	}

	private void ini() {
		add(back);
		add(jsp);
		add(jdt);
		jsp.getVerticalScrollBar().setUnitIncrement(8);
		jdt.getVerticalScrollBar().setUnitIncrement(8);
		add(nname);
		add(njsp);
		add(nadd);
		add(npac);
		ndesc.setBorder(BorderFactory.createEtchedBorder());
		cont.setLayout(null);
		addListeners();
	}

	private void load() {
		cont.removeAll();
		try {

			if (WebPack.packlist.size() == 0)
				BCJSON.refreshPacks();
			List<WebPack> l = new ArrayList<>();
			for (WebPack wp : packlist.values())
				if (wp.uid == BCJSON.ID)
					l.add(wp);

			l.sort(WebPack.getComp(WebPack.SORT_NEW));

			WebPack[] obj = l.toArray(new WebPack[0]);
			SimpleAttributeSet attr = new SimpleAttributeSet();
			StyleConstants.setAlignment(attr, StyleConstants.ALIGN_CENTER);
			n = obj.length;

			nname.setText("pack title");
			ndesc.setText("description");

			int[] alr = new int[n];
			for (int i = 0; i < n; i++)
				alr[i] = obj[i].pid;
			List<Pack> list = Pack.getEditable(alr);
			Pack[] packs = list.toArray(new Pack[0]);
			DefaultComboBoxModel<Pack> dcm = new DefaultComboBoxModel<>(packs);
			npac.setModel(dcm);

			items = new WebItem[n];

			for (int i = 0; i < n; i++)
				items[i] = new WebItem(obj[i]);
		} catch (IOException e) {
			e.printStackTrace();
			changePanel(new LoginPage(getFront()));
		}
	}

}
