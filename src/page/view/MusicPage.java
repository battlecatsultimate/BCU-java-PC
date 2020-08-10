package page.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JList;
import javax.swing.JScrollPane;

import common.pack.PackData.Identifier;
import common.pack.UserProfile;
import common.util.stage.Music;
import io.BCMusic;
import page.JBTN;
import page.Page;

public class MusicPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN strt = new JBTN(0, "start");

	private final JList<Music> jlf = new JList<>();
	private final JScrollPane jsp = new JScrollPane(jlf);

	public MusicPage(Page p) {
		this(p, UserProfile.getBCData().musics.getList());

	}

	public MusicPage(Page p, Collection<Music> mus) {
		super(p);
		jlf.setListData(mus.toArray(new Music[0]));
		ini();
		resized();
	}

	public MusicPage(Page p, Identifier id) {
		this(p, id.pack);
		jlf.setSelectedValue(UserProfile.getMusic(id), true);
	}

	public MusicPage(Page p, String pack) {
		this(p, UserProfile.getAll(pack, Music.class));
	}

	public Identifier getSelected() {
		return jlf.getSelectedValue().getID();
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

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BCMusic.clear();
				changePanel(getFront());
			}
		});

		strt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (jlf.getSelectedValue() == null)
					return;
				BCMusic.setBG(jlf.getSelectedValue(), 0);
			}
		});

	}

	private void ini() {
		add(back);
		add(strt);
		add(jsp);
		addListeners();
	}

}
