package page.battle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import io.Reader;
import main.MainBCU;
import main.Opts;
import page.JBTN;
import page.JTF;
import page.JTG;
import page.Page;
import page.info.StageViewPage;
import util.stage.MapColc;
import util.stage.Recd;

public class RecdManagePage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN rply = new JBTN(0, "rply");
	private final JBTN recd = new JBTN(-1, "mp4");
	private final JBTN dele = new JBTN(0, "rem");
	private final JBTN vsta = new JBTN(0, "vsta");
	private final JTF rena = new JTF();
	private final JTF seed = new JTF();
	private final JTG larg = new JTG(0, "larges");
	private final JBTN imgs = new JBTN(-1, "PNG");
	private final JList<Recd> jlr = new JList<>();
	private final JScrollPane jspr = new JScrollPane(jlr);
	private final JLabel len = new JLabel();

	private boolean changing;
	private StageViewPage svp;

	public RecdManagePage(Page p) {
		super(p);

		ini();
		resized();
	}

	@Override
	protected void renew() {
		setList();
		if (svp != null) {
			Recd r = jlr.getSelectedValue();
			if (r != null && svp.getStage() != null && Opts.conf("are you sure to change stage?")) {
				r.st = svp.getStage();
				r.avail = true;
				r.marked = true;
			}
		}
		svp = null;
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(rply, x, y, 400, 100, 300, 50);
		set(recd, x, y, 400, 200, 300, 50);
		set(larg, x, y, 750, 200, 300, 50);
		set(imgs, x, y, 1100, 200, 300, 50);
		set(len, x, y, 400, 300, 300, 50);
		set(jspr, x, y, 50, 100, 300, 600);
		set(dele, x, y, 400, 400, 300, 50);
		set(rena, x, y, 400, 500, 300, 50);
		set(vsta, x, y, 400, 600, 300, 50);
		set(seed, x, y, 750, 300, 500, 50);
	}

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		vsta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Recd r = jlr.getSelectedValue();
				changePanel(svp = new StageViewPage(getThis(), MapColc.MAPS.values(), r.st));
			}
		});

		rply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Recd r = jlr.getSelectedValue();
				changePanel(new BattleInfoPage(getThis(), r, 0));
			}
		});

		recd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Recd r = jlr.getSelectedValue();
				int conf = 1;
				if (larg.isSelected())
					conf |= 2;
				changePanel(new BattleInfoPage(getThis(), r, conf));
			}
		});

		imgs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Recd r = jlr.getSelectedValue();
				int conf = 5;
				if (larg.isSelected())
					conf |= 2;
				changePanel(new BattleInfoPage(getThis(), r, conf));
			}
		});

		jlr.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || jlr.getValueIsAdjusting())
					return;
				setRecd(jlr.getSelectedValue());
			}

		});

		rena.setLnr(x -> {
			if (changing || jlr.getValueIsAdjusting())
				return;
			Recd r = jlr.getSelectedValue();
			if (r == null)
				return;
			File f = new File("./replay/" + r.name + ".replay");
			if (f.exists()) {
				String str = MainBCU.validate(rena.getText().trim());
				str = Recd.getAvailable(str);
				if (f.renameTo(new File("./replay/" + str + ".replay"))) {
					Recd.map.remove(r.name);
					r.name = str;
					Recd.map.put(r.name, r);
				}
			}
			rena.setText(r.name);
		});

		seed.setLnr(x -> {
			if (changing || jlr.getValueIsAdjusting())
				return;
			Recd r = jlr.getSelectedValue();
			if (r == null)
				return;
			r.seed = Reader.parseLongN(seed.getText());
			setRecd(r);
		});

		dele.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Recd r = jlr.getSelectedValue();
				File f = new File("./replay/" + r.name + ".replay");
				if (f.exists())
					f.delete();
				if (!f.exists()) {
					Recd.map.remove(r.name);
					setList();
				}
				setRecd(null);
			}
		});

	}

	private void ini() {
		add(back);
		add(jspr);
		add(rply);
		add(len);
		add(recd);
		add(larg);
		add(imgs);
		add(dele);
		add(rena);
		add(vsta);
		add(seed);
		len.setBorder(BorderFactory.createEtchedBorder());
		addListeners();
	}

	private void setList() {
		boolean boo = changing;
		changing = true;
		Recd r = jlr.getSelectedValue();
		jlr.setListData(Recd.map.values().toArray(new Recd[0]));
		jlr.setSelectedValue(r, true);
		setRecd(r);
		changing = boo;
	}

	private void setRecd(Recd r) {
		rply.setEnabled(r != null && r.avail);
		recd.setEnabled(r != null && r.avail);
		imgs.setEnabled(r != null && r.avail);
		dele.setEnabled(r != null);
		rena.setEditable(r != null);
		seed.setEditable(r != null);
		vsta.setEnabled(r != null);
		if (r == null) {
			rena.setText("");
			len.setText("");
			seed.setText("");
		} else {
			seed.setText("seed: " + r.seed);
			rena.setText(r.name);
			len.setText("length: " + r.getLen() + " frame");
		}
	}

}
