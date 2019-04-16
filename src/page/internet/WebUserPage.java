package page.internet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
import main.MainBCU;
import page.JBTN;
import page.JTF;
import page.Page;
import util.pack.Pack;

public class WebUserPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JPanel cont = new JPanel();
	private final JScrollPane jsp = new JScrollPane(cont);

	private final JTextPane ndesc = new JTextPane();
	private final JScrollPane njsp = new JScrollPane(ndesc);
	private final JTF nname = new JTF();
	private final JBTN nadd = new JBTN(2, "submit");
	private final JComboBox<Pack> npac = new JComboBox<>();

	private JTextPane[] desp;
	private JScrollPane[] jsps;
	private JTF[] name;
	private JBTN[] subm, dele, reup;
	private JLabel[] pkid, title, visi;
	private int n;

	public WebUserPage(Page p) {
		super(p);

		ini();
		load();
		resized();
	}

	@Override
	protected void renew() {
		load();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jsp, x, y, 50, 400, 2200, 850);
		set(title[0], x, y, 50, 100, 600, 50);
		set(title[1], x, y, 50, 150, 600, 50);
		set(title[2], x, y, 650, 100, 1400, 100);
		set(nname, x, y, 50, 250, 600, 50);
		set(npac, x, y, 50, 300, 600, 50);
		set(njsp, x, y, 650, 250, 1400, 100);
		set(nadd, x, y, 2050, 250, 200, 50);
		int h = 100, hh = 50, dh = 50, xh = 0;
		for (int i = 0; i < n; i++) {
			set(name[i], x, y, 0, xh, 600, hh);
			set(pkid[i], x, y, 0, xh + hh, 200, hh);
			set(visi[i], x, y, 200, xh + hh, 200, hh);
			set(reup[i], x, y, 400, xh + hh, 200, hh);
			set(jsps[i], x, y, 600, xh, 1400, h);
			set(subm[i], x, y, 2000, xh, 200, hh);
			set(dele[i], x, y, 2000, xh + hh, 200, hh);
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
				int res = BCJSON.upload(p.id, strn, strd);
				if (res == 0)
					load();
				else if (res == 211)
					MainBCU.pop("repeat pack ID, please contact developer", "server error");
				else
					MainBCU.pop("unable to upload pack", "server error");

			}
		});

	}

	private void ini() {
		add(back);
		add(jsp);
		jsp.getVerticalScrollBar().setUnitIncrement(8);
		add(nname);
		add(njsp);
		add(nadd);
		add(npac);
		ndesc.setBorder(BorderFactory.createEtchedBorder());
		title = new JLabel[3];
		for (int i = 0; i < 3; i++) {
			add(title[i] = new JLabel(WebText.get("t" + i * 2)));
			title[i].setHorizontalAlignment(SwingConstants.CENTER);
			title[i].setBorder(BorderFactory.createEtchedBorder());
		}
		cont.setLayout(null);
		addListeners();
	}

	private void load() {
		cont.removeAll();
		try {
			WebPack[] obj = BCJSON.getPacks(BCJSON.ID, WebPack.SORT_NEW).toArray(new WebPack[0]);
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

			jsps = new JScrollPane[n];
			name = new JTF[n];
			desp = new JTextPane[n];
			pkid = new JLabel[n];
			visi = new JLabel[n];
			subm = new JBTN[n];
			dele = new JBTN[n];
			reup = new JBTN[n];

			for (int i = 0; i < obj.length; i++) {

				cont.add(name[i] = new JTF(obj[i].name));

				cont.add(jsps[i] = new JScrollPane(desp[i] = new JTextPane()));
				desp[i].setBorder(BorderFactory.createEtchedBorder());
				desp[i].setText(obj[i].desp);

				cont.add(pkid[i] = new JLabel(obj[i].pid + "-" + obj[i].version));
				pkid[i].setHorizontalAlignment(SwingConstants.CENTER);
				pkid[i].setBorder(BorderFactory.createEtchedBorder());

				cont.add(visi[i] = new JLabel(WebText.get((obj[i].state == 1 ? "in" : "") + "visible")));
				visi[i].setHorizontalAlignment(SwingConstants.CENTER);
				visi[i].setBorder(BorderFactory.createEtchedBorder());

				cont.add(subm[i] = new JBTN(2, "update"));

				cont.add(dele[i] = new JBTN(2, (obj[i].state == 1 ? "un" : "") + "delete"));

				cont.add(reup[i] = new JBTN(2, "reupload"));

				Pack p = Pack.map.get(obj[i].pid);
				reup[i].setEnabled(p != null && p.editable);

				int I = i;

				subm[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						String s0 = name[I].getText().trim();
						String s1 = desp[I].getText().trim();
						boolean b0 = s0.length() == 0;
						boolean b1 = s1.length() == 0;
						System.out.println(obj[I].pid + ", " + s0 + ", " + s1);
						boolean b2 = false;
						if (!b0 && !b1)
							b2 = BCJSON.update(obj[I].pid, s0, s1);
						if (b0 || !b2)
							name[I].setText(obj[I].name);
						if (b1 || !b2)
							desp[I].setText(obj[I].desp);
					}

				});

				dele[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						boolean b = BCJSON.delete(obj[I].pid);
						if (!b) {
							MainBCU.pop("failed to edit", "server error");
							return;
						}
						dele[I].setText(2, (obj[I].state == 1 ? "un" : "") + "delete");
						visi[I].setText(WebText.get((obj[I].state == 1 ? "un" : "") + "visible"));
					}

				});

				reup[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						boolean b = BCJSON.reversion(obj[I].pid);
						if (!b) {
							MainBCU.pop("failed to upload", "server error");
							return;
						}
						load();
					}

				});

			}
		} catch (IOException e) {
			e.printStackTrace();
			changePanel(new LoginPage(getFront()));
		}
	}

}
