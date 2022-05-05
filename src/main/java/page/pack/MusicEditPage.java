package page.pack;

import common.CommonStatic;
import common.pack.PackData.UserPack;
import common.util.stage.Music;
import io.BCMusic;
import main.Opts;
import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MusicEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JList<Music> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);

	private final JBTN relo = new JBTN(0, "read list");
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
		set(relo, x, y, 400, 100, 200, 50);
		set(play, x, y, 400, 200, 200, 50);
		set(stop, x, y, 400, 300, 200, 500);
		set(show, x, y, 400, 400, 200, 50);
		set(jlp, x, y, 400, 500, 200, 50);
		set(jtp, x, y, 400, 550, 200, 50);
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

		play.addActionListener(arg0 -> BCMusic.setBG(sele));

		stop.addActionListener(arg -> BCMusic.BG.stop());

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
		add(relo);
		add(play);
		add(jlp);
		add(jtp);
		setList();
		addListeners();
	}

	private void toggleButtons() {
		play.setEnabled(sele != null);
		jtp.setEnabled(sele != null);
		jtp.setText(sele != null ? convertTime(sele.loop) : "-");
		jtp.setToolTipText(getMusTime());
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

	private String convertTime(long milli) {
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

	private long toMilli(String time) {
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

	private long getMili(long milis) {
		if (milis == 0 || milis >= 100)
			return milis;
		else if (milis >= 10)
			return milis * 10;
		else
			return milis * 100;
	}
}
