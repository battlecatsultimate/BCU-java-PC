package io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;

import util.pack.MusicStore;
import util.pack.Pack;

public class BCMusic implements LineListener {

	private static final List<BCMusic> SES = new ArrayList<BCMusic>();
	private static final int FACTOR = 20;

	public static boolean play = true;
	public static int music = -1;
	public static int VOL_BG = 20, VOL_SE = 20;

	private static BCMusic BG;

	public static void play(int ind) {
		music = ind;
		File f = MusicStore.getMusic(ind);
		if (f != null)
			setBG(f);
	}

	public static void read() {
		File dict = new File("./assets/music/");
		if (!dict.exists())
			return;
		File[] fs = dict.listFiles();
		for (File f : fs) {
			String str = f.getName();
			if (str.length() != 7)
				continue;
			if (!str.endsWith(".ogg"))
				continue;
			int id = Reader.parseIntN(str.substring(0, 3));
			if (id == -1)
				continue;
			Pack.def.ms.set(id, f);
		}
	}

	public static void setBG(File f) {
		if (!play)
			return;
		try {
			new BCMusic(f, getVol(VOL_BG), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setBGVol(int vol) {
		VOL_BG = vol;
		if (BG != null)
			BG.master.setValue(getVol(vol));
	}

	public static void setSE(int ind) {
		if (!play)
			return;
		try {
			new BCMusic(MusicStore.getMusic(ind), getVol(VOL_SE), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setSEVol(int vol) {
		VOL_SE = vol;
		for (BCMusic ms : SES)
			ms.master.setValue(getVol(vol));
	}

	public static void stopAll() {
		if (BG != null)
			BG.stop();
		for (BCMusic ms : SES)
			ms.stop();
		BG = null;
		SES.clear();
	}

	private static float getVol(int vol) {
		return FACTOR * ((float) Math.log10(vol) - 2);
	}

	private static Clip openFile(File file) throws Exception {
		AudioInputStream raw = AudioSystem.getAudioInputStream(file);
		AudioFormat rf = raw.getFormat();
		int ch = rf.getChannels();
		float rate = rf.getSampleRate();
		AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
		AudioInputStream stream = AudioSystem.getAudioInputStream(format, raw);
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		Clip line = (Clip) AudioSystem.getLine(info);
		line.open(stream);
		return line;
	}

	private final Clip clip;
	private final FloatControl master;
	private final boolean loop;

	private BCMusic(File file, float vol, boolean b) throws Exception {
		clip = openFile(file);
		loop = b;
		master = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		master.setValue(vol);
		if (loop) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			if (BG != null)
				BG.stop();
			BG = this;
		} else {
			clip.addLineListener(this);
			SES.add(this);
		}
		clip.start();
	}

	@Override
	public void update(LineEvent event) {
		if (event.getType() == Type.STOP) {
			stop();
			SES.remove(this);
		}
	}

	private void stop() {
		clip.stop();
		clip.flush();
		clip.close();
	}

}
