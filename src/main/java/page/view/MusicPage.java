package page.view;

import common.pack.Identifier;
import common.pack.PackData;
import common.pack.UserProfile;
import common.util.stage.Music;
import io.BCMusic;
import page.JBTN;
import page.Page;
import page.SupPage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MusicPage extends Page implements SupPage<Music> {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN strt = new JBTN(0, "start");

	private final JList<Music> jlf = new JList<>();
	private final JScrollPane jsp = new JScrollPane(jlf);

	public MusicPage(Page p) {
		super(p);
		List<Music> mus = new ArrayList<>();
		for (PackData pac : UserProfile.getAllPacks())
			mus.addAll(pac.musics.getList());

		jlf.setListData(mus.toArray(new Music[0]));
		ini();
		resized();
	}

	public MusicPage(Page p, Collection<Music> mus) {
		super(p);
		jlf.setListData(mus.toArray(new Music[0]));
		ini();
		resized();
	}

	public MusicPage(Page p, Identifier<Music> id) {
		this(p, id.pack);
		jlf.setSelectedValue(Identifier.get(id), true);
	}

	public MusicPage(Page p, String pack) {
		this(p, UserProfile.getAll(pack, Music.class));
	}

	public Identifier<Music> getSelectedID() {
		return jlf.getSelectedValue() == null ? null : jlf.getSelectedValue().getID();
	}

	@Override
	protected void exit() {
		BCMusic.stopAll();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jsp, x, y, 50, 100, 300, 800);
		set(strt, x, y, 400, 100, 200, 50);
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	private void addListeners() {
		back.addActionListener(arg0 -> {
			BCMusic.clear();
			changePanel(getFront());
		});

		strt.addActionListener(arg0 -> {
			if (jlf.getSelectedValue() == null)
				return;
			BCMusic.setBG(jlf.getSelectedValue());
		});

	}

	private void ini() {
		add(back);
		add(strt);
		add(jsp);
		addListeners();
	}

	@Override
	public Music getSelected() {
		return jlf.getSelectedValue();
	}
}
