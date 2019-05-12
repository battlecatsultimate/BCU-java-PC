package util.anim;

import java.awt.image.BufferedImage;

import util.Animable;
import util.NonCopible;

public abstract class AnimI extends Animable<AnimI> implements NonCopible {

	public abstract void check();

	public abstract void load();

	public abstract String[] names();

	public abstract BufferedImage parts(int img);

}
