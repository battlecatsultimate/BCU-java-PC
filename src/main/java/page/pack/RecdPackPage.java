package page.pack;

import common.pack.PackData.UserPack;
import common.util.stage.Replay;
import page.JTF;
import page.Page;
import page.battle.AbRecdPage;

import javax.swing.*;

public class RecdPackPage extends AbRecdPage {

	private static final long serialVersionUID = 1L;

	private final JTF rena = new JTF();
	private final JList<Replay> jlr = new JList<>();
	private final JScrollPane jspr = new JScrollPane(jlr);

	private final UserPack pac;

	public RecdPackPage(Page p, UserPack pack) {
		super(p, pack.editable);
		pac = pack;
		preini();
		ini();
		resized(true);
	}

	@Override
	public Replay getSelection() {
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
		Replay r = jlr.getSelectedValue();
		jlr.setListData(pac.getReplays().toArray(new Replay[0]));
		jlr.setSelectedValue(r, true);
		setRecd(r);
		change(false);
	}

	@Override
	protected void setRecd(Replay r) {
		super.setRecd(r);
		rena.setEditable(r != null);
		rena.setText(r == null ? "" : r.rl.id);
	}

	private void addListeners() {

		jlr.addListSelectionListener(arg0 -> {
			if (isAdj() || jlr.getValueIsAdjusting())
				return;
			setRecd(jlr.getSelectedValue());
		});

		rena.setLnr(x -> {
			if (isAdj() || jlr.getValueIsAdjusting())
				return;
			Replay r = jlr.getSelectedValue();
			if (r == null)
				return;
			r.rename(rena.getText().trim(), true);
			rena.setText(r.rl.id);
		});

	}

	private void ini() {
		add(jspr);
		add(rena);
		addListeners();
	}

}
