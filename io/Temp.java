package io;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import util.pack.MusicStore;

public class Temp {

	public static void main(String[] args) {
		try {
			ZipAccess.extractAllList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main$0(String[] args)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		BCMusic.read();
		File file = MusicStore.getMusic(0);
		AudioInputStream raw = AudioSystem.getAudioInputStream(file);
		System.out.println("Raw Audio Format: " + raw.getFormat());

		AudioFormat out = getOutFormat(raw.getFormat());
		System.out.println("Out Audio Format: " + out);

		DataLine.Info srcinfo = new DataLine.Info(SourceDataLine.class, out);
		System.out.println("Source DataLine Info: " + srcinfo);

		Mixer mixer = AudioSystem.getMixer(AudioSystem.getMixerInfo()[0]);

		SourceDataLine sdl = AudioSystem.getSourceDataLine(out, mixer.getMixerInfo());
		System.out.println(sdl.getLineInfo());

	}

	private static AudioFormat getOutFormat(AudioFormat inFormat) {
		final int ch = inFormat.getChannels();
		final float rate = inFormat.getSampleRate();
		AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
		return audioFormat;
	}

}
