package page.pack;

import common.CommonStatic;
import common.pack.Context;
import common.pack.Identifier;
import common.pack.PackData.UserPack;
import common.pack.Source;
import common.util.stage.Music;
import io.BCMusic;
import main.Opts;
import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;
import page.support.Exporter;
import page.support.Importer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MusicEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JList<Music> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);

	private final JBTN addm = new JBTN(0, "add");
	private final JBTN remm = new JBTN(0, "rem");
	private final JBTN impt = new JBTN(0, "import");
	private final JBTN expt = new JBTN(0, "export");
	private final JBTN relo = new JBTN(0, "readl");
	private final JBTN play = new JBTN(0, "start");
	private final JBTN stop = new JBTN(0, "stop");
	private final JBTN show = new JBTN(0, "show");
	private final JL jlp = new JL("loop");
	private final JTF jtp = new JTF();

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

		set(addm, x, y, 400, 100, 200, 50);
		set(remm, x, y, 400, 150, 200, 50);

		set(impt, x, y, 400, 250, 200, 50);
		set(expt, x, y, 400, 300, 200, 50);

		set(show, x, y, 400, 400, 200, 50);
		set(relo, x, y, 400, 450, 200, 50);

		set(play, x, y, 700, 100, 200, 50);
		set(stop, x, y, 700, 150, 200, 50);

		set(jlp, x, y, 700, 250, 200, 50);
		set(jtp, x, y, 700, 300, 200, 50);
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	private void getFile(String dialogue, Identifier<Music> mus) {
		Importer selected = new Importer(dialogue, Importer.FileType.MUS);
		if (!selected.exists())
			return;
		if (mus == null)
			mus = pack.getNextID(Music.class);

		try {
			File f = ((Source.Workspace) pack.source).getMusFile(mus);
			Context.check(f);
			Files.copy(selected.file.toPath(), f.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			CommonStatic.ctx.noticeErr(e, Context.ErrType.WARN, "failed to write file");
			getFile("failed to save file", mus);
			return;
		}

		readMusic();
	}

	private void stopBG() {
		if (BCMusic.BG != null && BCMusic.BG.isPlaying()) {
			BCMusic.BG.stop();
			BCMusic.clear();
		}
	}

	private void readMusic() {
		pack.loadMusics();
		for (Music m : pack.musics)
			BCMusic.CACHE_CUSTOM.remove(m.getID());
		setList();
	}

	private void addListeners() {
		back.addActionListener(arg0 -> {
			stopBG();
			changePanel(getFront());
		});

		addm.addActionListener(x -> getFile("Choose your file", null));

		remm.addActionListener(x -> {
			if (!Opts.conf("Are you sure you want to delete music " + sele.id.id + "?"))
				return;

			File source = ((Source.Workspace) pack.source).getMusFile(sele.id);
			try {
				if (!source.delete())
					Opts.warnPop("Failed to delete music " + sele.id.id, "Delete Failed");
				else
					readMusic();
			} catch (Exception e) {
				CommonStatic.ctx.noticeErr(e, Context.ErrType.WARN, "failed to delete file");
			}
		});

		impt.addActionListener(x -> getFile("Choose your file", jlst.getSelectedValue().getID()));

		expt.addActionListener(x -> {
			try {
				// OutputStream os = ((Source.Workspace) pack.source).streamFile()
				Exporter e = new Exporter(Exporter.EXP_OGG);
				if (e.file == null)
					return;
				if (!e.file.getName().endsWith(".ogg"))
					e.file = new File(e.file + ".ogg");
				File source = ((Source.Workspace) pack.source).getMusFile(sele.id);
				Files.copy(source.toPath(), e.file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				CommonStatic.ctx.noticeErr(e, Context.ErrType.WARN, "failed to write file");
			}
		});

		relo.addActionListener(arg0 -> readMusic());

		show.addActionListener(arg0 -> {
			File f = new File(CommonStatic.ctx.getBCUFolder(), "./workspace/" + pack.desc.id + "/musics/");
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
			BCMusic.setBG(sele);
			toggleButtons();
		});

		stop.addActionListener(x -> {
			stopBG();
			toggleButtons();
		});

		jlst.addListSelectionListener(arg0 -> {
			if (isAdj() || arg0.getValueIsAdjusting())
				return;
			sele = jlst.getSelectedValue();
			toggleButtons();
		});

		jtp.setLnr(x -> {
			if (sele.data == null)
				return;

			long tim = Math.min(toMilli(jtp.getText()), toMilli(getMusTime()) - 1);
			if (tim != -1)
				sele.loop = tim;
			jtp.setText(convertTime(sele.loop));
		});
	}

	private void ini() {
		add(back);
		add(jspst);
		add(show);

		add(addm);
		add(remm);

		add(impt);
		add(expt);

		add(relo);
		add(play);
		add(stop);

		add(jlp);
		add(jtp);
		setList();
		addListeners();
	}

	private void toggleButtons() {
		boolean exists = sele != null;
		remm.setEnabled(exists);
		impt.setEnabled(exists);
		expt.setEnabled(exists);
		play.setEnabled(exists);
		stop.setEnabled(BCMusic.BG != null && BCMusic.BG.isPlaying());
		jtp.setEnabled(exists);
		jtp.setText(exists ? convertTime(sele.loop) : "-");
		jtp.setToolTipText(getMusTime());
	}

	private void setList() {
		change(this, p -> {
			int ind = jlst.getSelectedIndex();
			Music[] arr = pack.musics.toArray();
			jlst.setListData(arr);
			if (arr.length > 0) {
				if (ind < 0)
					ind = 0;
				if (ind >= arr.length)
					ind = arr.length - 1;
				jlst.setSelectedIndex(ind);
				sele = arr[ind];
			} else {
				sele = null;
			}

			toggleButtons();
		});
	}

	private String getMusTime() {
		if (sele == null || sele.data == null) {
			return "Music not found";
		}
		try {
			long duration = CommonStatic.def.getMusicLength(sele);

			if (duration == -1) {
				return "Invalid Format";
			} else if (duration == -2) {
				return "Unsupported Format";
			} else if (duration == -3) {
				return "Can't get duration";
			} else if (duration >= 0) {
				return convertTime(duration);
			} else {
				return "Unknown error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private static String convertTime(long milli) {
		long min = milli / 60 / 1000;
		double time = milli - (double) min * 60000;
		time /= 1000;
		NumberFormat nf = NumberFormat.getInstance(Locale.US);

		DecimalFormat df = (DecimalFormat) nf;

		df.applyPattern("#.###");
		double s = Double.parseDouble(df.format(time));
		if (s >= 60) {
			s -= 60;
			min += 1;
		}
		if (s < 10) {
			return min + ":" + "0" + df.format(s);
		} else {
			return min + ":" + df.format(s);
		}
	}

	private static long toMilli(String time) {
		try {
			long[] times = CommonStatic.parseLongsN(time);

			for (long t : times) {
				if (t < 0) {
					return -1;
				}
			}

			if (times.length == 1) {
				return times[0] * 1000;
			} else if (times.length == 2) {
				return (times[0] * 60 + times[1]) * 1000;
			} else if (times.length == 3) {
				if (times[2] < 1000) {
					return (times[0] * 60 + times[1]) * 1000 + getMili(times[2]);
				} else {
					String decimal = Long.toString(times[2]).substring(0, 3);
					return (times[0] * 60 + times[1]) * 1000 + Integer.parseInt(decimal);
				}
			} else {
				return -1;
			}
		} catch (Exception e) {
			return -1;
		}
	}

	private static long getMili(long milis) {
		if (milis == 0 || milis >= 100)
			return milis;
		else if (milis >= 10)
			return milis * 10;
		else
			return milis * 100;
	}
}
