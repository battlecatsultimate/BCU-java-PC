package util.anim;

import util.Animable;
import util.BattleStatic;
import util.system.fake.FakeImage;

public abstract class AnimI extends Animable<AnimI> implements BattleStatic {

	public abstract void check();

	public abstract void load();

	public abstract String[] names();

	public abstract FakeImage parts(int img);

}
