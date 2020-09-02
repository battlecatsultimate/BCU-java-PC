package page.pack;

import common.CommonStatic;
import common.pack.Source;
import common.pack.PackData.UserPack;
import common.pack.Source.Workspace;
import common.util.stage.Replay;
import page.JTF;
import page.Page;
import page.battle.AbRecdPage;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.File;

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
		resized();
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
			Replay r = jlr.getSelectedValue();
			if (r == null)
				return;
			File f = CommonStatic.ctx.getWorkspaceFile(r.rl.getPath(Source.REPLAY) + ".replay");
			if (f.exists()) {
				r.rl.id = rena.getText().trim();
				// FIXME rename replay
				Workspace.validate(Source.REPLAY, r.rl);
				File f1 = CommonStatic.ctx.getWorkspaceFile(r.rl.getPath(Source.REPLAY) + ".replay");
				if (f.renameTo(f1)) {
					Replay.getMap().remove(r.rl.id);
					Replay.getMap().put(r.rl.id, r);
				}
			}
			rena.setText(r.rl.id);
		});

	}

	private void ini() {
		add(jspr);
		add(rena);
		addListeners();
	}

}
