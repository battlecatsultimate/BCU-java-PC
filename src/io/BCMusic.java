package io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

import common.CommonStatic;
import common.util.Data;
import common.util.pack.MusicStore;
import common.util.pack.Pack;

import javax.sound.sampled.LineListener;

public class BCMusic extends Data implements LineListener {

	private static final List<BCMusic> SES = new ArrayList<BCMusic>();
	private static final int FACTOR = 20, CD = 5;
	private static final byte[][] CACHE = new byte[100][];

	public static boolean play = true;
	public static int music = -1;
	public static int VOL_BG = 20, VOL_SE = 20;
	private static boolean[] secall = new boolean[100];
	private static int[] secd = new int[100];

	private static BCMusic BG;

	public static synchronized void flush(boolean allow) {
		for (int i = 0; i < 100; i++) {
			if (secd[i] == 0 && secall[i] && allow)
				try {
					secd[i] = CD;
					if (CACHE[i] == null)
						new BCMusic(openFile(MusicStore.getMusic(i)), getVol(VOL_SE), false);
					else
						new BCMusic(openFile(CACHE[i]), getVol(VOL_SE), false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			if (secd[i] > 0)
				secd[i]--;
		}
		secall = new boolean[100];
	}

	public static synchronized void play(int ind) {
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
			int id = CommonStatic.parseIntN(str.substring(0, 3));
			if (id == -1)
				continue;
			Pack.def.ms.set(id, f);
			for (int i : SE_ALL)
				if (i == id) {
					try {
						CACHE[id] = Files.readAllBytes(f.toPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
		}
	}

	public static synchronized void setBG(File f) {
		if (!play)
			return;
		try {
			new BCMusic(openFile(f), getVol(VOL_BG), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized void setBGVol(int vol) {
		VOL_BG = vol;
		if (BG != null)
			BG.master.setValue(getVol(vol));
	}

	public static synchronized void setSE(int ind) {
		if (!play || VOL_SE == 0)
			return;
		secall[ind] = true;
	}

	public static synchronized void setSEVol(int vol) {
		VOL_SE = vol;
		for (BCMusic ms : SES)
			ms.master.setValue(getVol(vol));
	}

	public static synchronized void stopAll() {
		if (BG != null)
			BG.stop();
		BG = null;
		for (BCMusic ms : SES)
			ms.stop();
		SES.clear();
		secd = new int[100];
	}

	private static float getVol(int vol) {
		return FACTOR * ((float) Math.log10(vol) - 2);
	}

	private static Clip openFile(byte[] data) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(data);
		AudioInputStream raw = AudioSystem.getAudioInputStream(is);
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

	private BCMusic(Clip c, float vol, boolean b) throws Exception {
		clip = c;
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
			synchronized (BCMusic.class) {
				SES.add(this);
			}
		}
		clip.start();
	}

	@Override
	public void update(LineEvent event) {
		if (event.getType() == Type.STOP) {
			stop();
			synchronized (BCMusic.class) {
				SES.remove(this);
			}
		}
	}

	private void stop() {
		clip.stop();
		clip.close();
	}

}
