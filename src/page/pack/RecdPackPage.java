package page.pack;

import java.io.File;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import common.util.stage.Recd;
import main.MainBCU;
import page.JTF;
import page.Page;
import page.battle.AbRecdPage;

public class RecdPackPage extends AbRecdPage {

	private static final long serialVersionUID = 1L;

	private final JTF rena = new JTF();
	private final JList<Recd> jlr = new JList<>();
	private final JScrollPane jspr = new JScrollPane(jlr);

	private final Pack pac;

	public RecdPackPage(Page p, Pack pack) {
		super(p, pack.editable);
		pac = pack;
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
		set(rena, x, y, 600, 500, 300, 50);
	}

	@Override
	protected void setList() {
		change(true);
		Recd r = jlr.getSelectedValue();
		jlr.setListData(pac.getReplays().toArray(new Recd[0]));
		jlr.setSelectedValue(r, true);
		setRecd(r);
		change(false);
	}

	@Override
	protected void setRecd(Recd r) {
		super.setRecd(r);
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

	}

	private void ini() {
		add(jspr);
		add(rena);
		addListeners();
	}

}
