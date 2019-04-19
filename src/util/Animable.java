package util;

import util.anim.AnimI;
import util.anim.EAnimI;

public abstract class Animable<T extends AnimI> extends ImgCore {

	public T anim;

	public abstract EAnimI getEAnim(int t);

}
