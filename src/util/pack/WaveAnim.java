package util.pack;

import java.awt.image.BufferedImage;

import util.anim.AnimI;
import util.anim.EAnimD;
import util.anim.MaAnim;
import util.anim.MaModel;

public class WaveAnim extends AnimI {

	private final Background bg;
	private final MaModel mamodel;
	private final MaAnim maanim;

	private BufferedImage[] parts;

	public WaveAnim(Background BG, MaModel model, MaAnim anim) {
		bg = BG;
		mamodel = model;
		maanim = anim;
	}

	@Override
	public void check() {
		if (parts == null)
			load();
	}

	@Override
	public EAnimD getEAnim(int t) {
		return new EAnimD(this, mamodel, maanim);
	}

	@Override
	public void load() {
		bg.check();
		parts = bg.parts;
	}

	@Override
	public String[] names() {
		return new String[] { "wave" };
	}

	@Override
	public BufferedImage parts(int i) {
		check();
		return parts[i];
	}

}
