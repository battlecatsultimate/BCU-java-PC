package util.pack;

import util.anim.AnimD;
import util.anim.ImgCut;
import util.anim.MaAnim;
import util.anim.MaModel;
import util.system.FakeImage;
import util.system.VImg;

public class Soul extends AnimD {

	public static void read() {
		String pre = "./org/battle/soul/";
		String mid = "/battle_soul_";
		for (int i = 0; i < 12; i++)
			Pack.def.ss.add(new Soul(pre + trio(i) + mid + trio(i), i));
	}

	private final int index;
	private final VImg img;

	private Soul(String st, int i) {
		super(st);
		img = new VImg(str + ".png");
		index = i;
	}

	@Override
	public FakeImage getNum() {
		return img.getImg();
	}

	@Override
	public void load() {
		loaded = true;
		imgcut = ImgCut.newIns(str + ".imgcut");
		mamodel = MaModel.newIns(str + ".mamodel");
		anims = new MaAnim[] { MaAnim.newIns(str + ".maanim") };
		parts = imgcut.cut(img.getImg());
	}

	@Override
	public String[] names() {
		return new String[] { "soul" };
	}

	@Override
	public String toString() {
		return "soul_" + trio(index);
	}

}
