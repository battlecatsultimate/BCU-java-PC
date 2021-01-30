package page.pack;

import common.pack.PackData.UserPack;
import common.util.stage.Music;
import io.BCMusic;
import main.Opts;
import page.JBTN;
import page.Page;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MusicEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JList<Music> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);

	private final JBTN relo = new JBTN(0, "read list");
	private final JBTN play = new JBTN(0, "start");
	private final JBTN show = new JBTN(0, "show");

	private final UserPack pack;
	private Music sele;

	public MusicEditPage(Page p, UserPack ac) {
		super(p);
		pack = ac;
		ini();
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jspst, x, y, 50, 100, 300, 1000);
		set(relo, x, y, 400, 100, 200, 50);
		set(play, x, y, 400, 200, 200, 50);
		set(show, x, y, 400, 300, 200, 50);
	}

	private void addListeners() {
		back.addActionListener(arg0 -> {
			if (BCMusic.BG != null && BCMusic.BG.isPlaying()) {
				BCMusic.BG.stop();
				BCMusic.clear();
			}

			changePanel(getFront());
		});

		relo.addActionListener(arg0 -> {
			pack.loadMusics();
			setList();
		});

		show.addActionListener(arg0 -> {
			File f = new File("./workspace/" + pack.desc.id + "/musics/");
			if(!f.exists()) {
				boolean result = f.mkdirs();

				if(!result) {
					Opts.pop("Couldn't create folder "+f.getAbsolutePath(), "IO Error");
					return;
				}
			}
			try {
				Desktop.getDesktop().open(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		play.addActionListener(arg0 -> {
			if (sele == null)
				return;
			BCMusic.setBG(sele, 0);
		});

		jlst.addListSelectionListener(arg0 -> {
			if (isAdj() || arg0.getValueIsAdjusting())
				return;
			sele = jlst.getSelectedValue();
		});
	}

	private void ini() {
		add(back);
		add(jspst);
		add(show);
		add(relo);
		add(play);
		setList();
		addListeners();

	}

	private void setList() {
		change(this, p -> {
			int ind = jlst.getSelectedIndex();
			Music[] arr = pack.musics.toArray();
			jlst.setListData(arr);
			if (ind < 0)
				ind = 0;
			if (ind >= arr.length)
				ind = arr.length - 1;
			jlst.setSelectedIndex(ind);
			if (ind >= 0)
				sele = arr[ind];
		});
	}

}
