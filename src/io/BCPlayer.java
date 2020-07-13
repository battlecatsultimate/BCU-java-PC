package io;

import java.util.ArrayDeque;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent.Type;

public class BCPlayer implements LineListener{
	private final int ind;
	private final Clip c;
	private final FloatControl master;
	
	private boolean rewinding = false;
	private boolean playing = false;
	
	protected BCPlayer(Clip c, int ind) {
		this.ind = ind;
		this.c = c;
		this.c.addLineListener(this);
		this.master = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
	}
	
	protected void rewind() {
		if(rewinding) {
			return;
		}
		
		rewinding = true;
		c.setFramePosition(0);
		rewinding = false;
	}
	
	protected void setVolume(int vol) {
		master.setValue(vol);
	}
	
	protected void release() {
		playing = false;
		rewinding = false;
		c.close();
	}
	
	protected void stop() {
		playing = false;
		c.stop();
	}
	
	protected void start() {
		if(playing) {
			return;
		}
		
		playing = true;
		c.start();
	}
	
	protected void setLineListener(LineListener l) {
		c.addLineListener(l);
	}

	@Override
	public void update(LineEvent event) {
		if (event.getType() == Type.STOP) {
			playing = false;
			stop();
			
			if(ind >= 0 && ind != 20 && ind != 21 && ind != 22) {
				synchronized (BCMusic.class) {
					ArrayDeque<BCPlayer> players = BCMusic.sounds.get(ind);
					
					if(players != null) {
						players.push(this);
					}
				}
			}
		}
	}
}
