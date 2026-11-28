package page.view;

import common.pack.Identifier;
import common.pack.PackData;
import common.pack.UserProfile;
import common.util.stage.Music;
import io.BCMusic;
import page.JBTN;
import page.Page;
import page.SupPage;
import page.info.StageFilterPage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import common.util.lang.MultiLangCont;

public class MusicPage extends Page implements SupPage<Music> {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN strt = new JBTN(0, "start");
	private final JBTN find = new JBTN(0, "stage");

	private final JList<Music> jlf = new JList<>();
	private final JScrollPane jsp = new JScrollPane(jlf);

	public MusicPage(Page p) {
		super(p);
		List<Music> mus = new ArrayList<>();
		for (PackData pac : UserProfile.getAllPacks())
			mus.addAll(pac.musics.getList());

		jlf.setListData(mus.toArray(new Music[0]));
		ini();
	}

	public MusicPage(Page p, Collection<Music> mus) {
		super(p);
		jlf.setListData(mus.toArray(new Music[0]));
		ini();
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

		set(back, x, y, 0, 0, 200, 50); //back
		set(jsp, x, y, 50, 200, 1000, 1000); // music list
		set(strt, x, y, 50, 100, 200, 50); // start music
		set(find, x, y, 550, 100, 200, 50); // find stage
		set(stop, x, y, 300, 100, 200, 50); // stop music
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

		find.addActionListener(x -> {
			if (jlf.getSelectedValue() == null)
				return;

			changePanel(new StageFilterPage(this, jlf.getSelectedValue().getStages()));
		});
		stop.addActionListener(arg0 -> {
			BCMusic.stopAll();
		});
	}

	private final JBTN stop = new JBTN(0, "stop");

	private void ini() {
		DefaultListCellRenderer renderer = new DefaultListCellRenderer();

		jlf.setCellRenderer((list, music, index, isSelected, cellHasFocus) -> {
			JLabel label = (JLabel) renderer.getListCellRendererComponent(
					list, music, index, isSelected, cellHasFocus
			);

			if (music != null && music.id != null &&
					Identifier.DEF.equals(music.id.pack)) {

				String name = MultiLangCont.getStatic().MUSNAME.getCont(music.id.id);

				if (name != null && !name.isEmpty())
					label.setText(music + " - " + name);
			}

			return label;
		});

		add(back);
		add(strt);
		add(jsp);
		add(find);
		addListeners();
		add(stop);
	}

	@Override
	public Music getSelected() {
		return jlf.getSelectedValue();
	}
}
