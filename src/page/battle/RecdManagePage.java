package page.battle;

import common.util.stage.Recd;
import main.MainBCU;
import page.JBTN;
import page.JTF;
import page.Page;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecdManagePage extends AbRecdPage {

	private static final long serialVersionUID = 1L;

	private final JBTN dele = new JBTN(0, "rem");
	private final JTF rena = new JTF();
	private final JList<Recd> jlr = new JList<>();
	private final JScrollPane jspr = new JScrollPane(jlr);

	public RecdManagePage(Page p) {
		super(p, true);
		preini();
		ini();
		resized();
	}

	@Override
	public Recd getSelection() {
		return jlr.getSelectedValue();
	}

	@Override
	protected void resized(int x, int y) {
		super.resized(x, y);
		set(jspr, x, y, 50, 100, 500, 1100);
		set(dele, x, y, 600, 400, 300, 50);
		set(rena, x, y, 600, 500, 300, 50);
	}

	@Override
	protected void setList() {
		change(true);
		Recd r = jlr.getSelectedValue();
		jlr.setListData(Recd.map.values().toArray(new Recd[0]));
		jlr.setSelectedValue(r, true);
		setRecd(r);
		change(false);
	}

	@Override
	protected void setRecd(Recd r) {
		super.setRecd(r);
		dele.setEnabled(r != null);
		rena.setEditable(r != null);
		rena.setText(r == null ? "" : r.name);
	}

	private void addListeners() {

		jlr.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (isAdj() || jlr.getValueIsAdjusting())
					return;
				setRecd(jlr.getSelectedValue());
			}

		});

		rena.setLnr(x -> {
			if (isAdj() || jlr.getValueIsAdjusting())
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
		add(jspr);
		add(dele);
		add(rena);
		addListeners();
	}

}
