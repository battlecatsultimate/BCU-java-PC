package page.battle;

import common.CommonStatic;
import common.pack.PackData;
import common.pack.UserProfile;
import common.util.stage.Replay;
import main.MainBCU;
import page.JBTN;
import page.JTF;
import page.Page;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RecdManagePage extends AbRecdPage {

	private static final long serialVersionUID = 1L;

	private final JBTN dele = new JBTN(0, "rem");
	private final JTF rena = new JTF();
	private final JList<Replay> jlr = new JList<>();
	private final JScrollPane jspr = new JScrollPane(jlr);

	public RecdManagePage(Page p) {
		super(p, true);
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
		set(dele, x, y, 600, 400, 300, 50);
		set(rena, x, y, 600, 500, 300, 50);
	}

	@Override
	protected void setList() {
		change(true);
		Replay r = jlr.getSelectedValue();
		Vector<Replay> replays = new Vector<>(Replay.getMap().values());
		for (PackData.UserPack pack : UserProfile.getUserPacks())
			replays.addAll(pack.getReplays());
		jlr.setListData(replays);
		jlr.setSelectedValue(r, true);
		setRecd(r);
		change(false);
	}

	@Override
	protected void setRecd(Replay r) {
		super.setRecd(r);
		dele.setEnabled(r != null);
		rena.setEditable(r != null);
		rena.setText(r == null || r.rl == null ? "" : r.rl.id);
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
			r.rename(MainBCU.validate(rena.getText().trim(),'#'), true);
			rena.setText(r.rl.id);
		});

		dele.addActionListener(arg0 -> {
			Replay r = jlr.getSelectedValue();
			File f = CommonStatic.ctx.getWorkspaceFile(r.rl.getPath() + ".replay");
			if (f.exists()) {
				f.delete();
			} else {
				Replay.getMap().remove(r.rl.id);
				setList();
			}
			setRecd(null);
		});

	}

	private void ini() {
		add(jspr);
		add(dele);
		add(rena);
		addListeners();
	}

}
