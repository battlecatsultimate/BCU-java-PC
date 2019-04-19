package util.anim;

import java.awt.image.BufferedImage;

import util.Animable;

public abstract class AnimI extends Animable<AnimI> {

	public abstract void check();

	public abstract void load();

	public abstract String[] names();

	public abstract BufferedImage parts(int img);

}
