package io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import common.CommonStatic;
import common.util.Data;
import common.util.pack.MusicStore;
import common.util.pack.Pack;

public class BCMusic extends Data {

	private static final int FACTOR = 20, TOT = 125;
	private static final byte[][] CACHE = new byte[TOT][];

	public static boolean play = true;
	public static int music = -1;
	public static int VOL_BG = 20, VOL_SE = 20;
	private static boolean[] secall = new boolean[TOT];

	private static BCPlayer BG;
	
	private static BCPlayer[] hit;
	private static BCPlayer[] hit1;
	private static BCPlayer[] baseHit;
	
	private static boolean h, h1, bh;
	
	protected static Map<Integer,ArrayDeque<BCPlayer>> sounds = new HashMap<Integer, ArrayDeque<BCPlayer>>();
	
	public static synchronized void flush(boolean allow) {
		if(hit == null) {
			hit = new BCPlayer[2];
			
			for(int i = 0; i < hit.length; i++) {
				try {
					hit[i] = new BCPlayer(openFile(MusicStore.getMusic(20)), 20);
					hit[i].setVolume(VOL_SE);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		if(hit1 == null) {
			hit1 = new BCPlayer[2];
			
			for(int i = 0; i < hit1.length; i++) {
				try {
					hit1[i] = new BCPlayer(openFile(MusicStore.getMusic(21)), 21);
					hit1[i].setVolume(VOL_SE);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		if(baseHit == null) {
			baseHit = new BCPlayer[2];
			
			for(int i = 0; i < baseHit.length; i++) {
				try {
					baseHit[i] = new BCPlayer(openFile(MusicStore.getMusic(22)), 22);
					baseHit[i].setVolume(VOL_SE);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		for (int i = 0; i < TOT; i++) {
			if (secall[i] && allow)
				try {
					if (CACHE[i] == null)
						loadSound(i, MusicStore.getMusic(i), getVol(VOL_SE), false);
					else
						loadSound(i, CACHE[i], getVol(VOL_SE), false);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		secall = new boolean[TOT];
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
	
	public static void clear() {
		for(ArrayDeque<BCPlayer> clips : sounds.values()) {
			while(true) {
				BCPlayer c = clips.poll();
				
				if(c != null) {
					c.release();
					c = null;
				} else {
					break;
				}
			}
		}
		
		if(hit != null) {
			for(int i = 0; i < hit.length; i++) {
				hit[i].release();
				hit[i] = null;
			}
			
			hit = null;
		}
		
		if(hit1 != null) {
			for(int i = 0; i < hit1.length; i++) {
				hit1[i].release();
				hit1[i] = null;
			}
			
			hit1 = null;
		}
		
		if(baseHit != null) {
			for(int i = 0; i < baseHit.length; i++) {
				baseHit[i].release();
				baseHit[i] = null;
			}
			
			baseHit = null;
		}
	}

	public static synchronized void setBG(File f) {
		if (!play)
			return;
		try {
			loadSound(-1, f, getVol(VOL_BG), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized void setBGVol(int vol) {
		VOL_BG = vol;
		
		if(BG != null) {
			BG.setVolume(vol);
		}
	}

	public static synchronized void setSE(int ind) {
		if (!play || VOL_SE == 0)
			return;
		secall[ind] = true;
	}

	public static synchronized void setSEVol(int vol) {
		VOL_SE = vol;
		
		for(ArrayDeque<BCPlayer> players : sounds.values()) {
			players.forEach((player) -> {
				player.setVolume(vol);
			});
		}
	}

	public static synchronized void stopAll() {
		if (BG != null)
			BG.stop();
		
		BG = null;
		
		for(ArrayDeque<BCPlayer> players : sounds.values()) {
			players.forEach((player) -> {
				player.stop();
			});
		}
		
		clear();
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
	
	private static void loadSound(int ind, File file, float vol, boolean b) throws Exception {
		// set ind to -1 to tell it's BG
		
		if(b) {
			Clip c = openFile(file);
			
			c.loop(Clip.LOOP_CONTINUOUSLY);
			
			if(BG != null) {
				BG.stop();
			}
			
			BG = new BCPlayer(c, -1);
			BG.setVolume(VOL_BG);
			
			BG.start();
			
			return;
		} 
		
		ArrayDeque<BCPlayer> clips = sounds.get(ind);
		
		if(clips == null) {
			clips = new ArrayDeque<BCPlayer>();
			
			sounds.put(ind, clips);
			
			loadSound(ind, openFile(file));
		} else {
			BCPlayer player = clips.poll();
			
			if(player != null) {
				player.rewind();
				player.start();
			} else {
				loadSound(ind, openFile(file));
			}
		}
	}
	
	private static void loadSound(int ind, byte[] bytes, float vol, boolean b) throws Exception {
		// set ind to -1 to tell it's BG
		
		if(b) {
			Clip c = openFile(bytes);
			
			c.loop(Clip.LOOP_CONTINUOUSLY);
			
			if(BG != null) {
				BG.stop();
			}
			
			BG = new BCPlayer(c, -1);
			BG.setVolume(VOL_BG);
			
			BG.start();
			
			return;
		} 
		
		ArrayDeque<BCPlayer> clips = sounds.get(ind);
		
		if(clips == null) {
			clips = new ArrayDeque<BCPlayer>();
			
			sounds.put(ind, clips);
			
			loadSound(ind, openFile(bytes));
		} else {
			BCPlayer player = clips.poll();
			
			if(player != null) {
				player.rewind();
				player.start();
			} else {
				switch(ind) {
					case 20:
						if(hit != null) {
							if(h) {
								hit[0].stop();
								hit[0].rewind();
								hit[1].start();
							} else {
								hit[1].stop();
								hit[1].rewind();
								hit[0].start();
							}
							
							h = !h;
						}
						break;
					case 21:
						if(hit1 != null) {
							if(h1) {
								hit1[0].stop();
								hit1[0].rewind();
								hit1[1].start();
							} else {
								hit1[1].stop();
								hit1[1].rewind();
								hit1[0].start();
							}
							
							h1 = !h1;
						}
						break;
					case 22:
						if(baseHit != null) {
							if(bh) {
								baseHit[0].stop();
								baseHit[0].rewind();
								baseHit[1].start();
							} else {
								baseHit[1].stop();
								baseHit[1].rewind();
								baseHit[0].start();
							}
							
							bh = !bh;
						}
						break;
					default:
						loadSound(ind, openFile(bytes));
				}
			}
		}
	}
	
	private static void loadSound(int ind, Clip c) {
		BCPlayer player = new BCPlayer(c, ind);
		player.setVolume(VOL_SE);
		
		player.start();
	}
}
