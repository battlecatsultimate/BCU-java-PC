package util.anim;

import java.awt.image.BufferedImage;

import main.Printer;
import page.MainLocale;
import util.Res;
import util.system.VFile;
import util.system.VImg;

public class AnimU extends AnimD {

	private static String[] strs0, strs1, strs2;

	static {
		redefine();
	}

	public static void redefine() {
		String s0 = "move";
		String s1 = "wait";
		String s2 = "atk";
		String s3 = "kb";
		String s4 = "down";
		String s5 = "under";
		String s6 = "up";
		String s7 = "entry";
		strs0 = MainLocale.getLoc(0, s0, s1, s2, s3);
		strs1 = MainLocale.getLoc(0, s0, s1, s2, s3, s4, s5, s6);
		strs2 = MainLocale.getLoc(0, s0, s1, s2, s3, s7);

	}

	public BufferedImage num;

	public VImg uni = Res.slot[0], edi;

	protected boolean partial = false;

	public AnimU(String st, String ed0, String ed1) {
		super(st + ed0);
		edi = new VImg(st + ed1);
	}

	protected AnimU() {
		super("");
	}

	public int getAtkLen() {
		partial();
		return anims[2].len + 1;
	}

	@Override
	public EAnimU getEAnim(int t) {
		check();
		if (mamodel == null || t >= anims.length || anims[t] == null)
			return null;
		return new EAnimU(this, t);
	}

	@Override
	public BufferedImage getNum() {
		check();
		return num;
	}

	@Override
	public void load() {
		loaded = true;
		try {
			VFile pic;
			num = read(pic = VFile.getFile(str + ".png"));
			imgcut = ImgCut.newIns(str + ".imgcut");
			if (num == null) {
				Printer.e("AnimU", 70, "can't read png: " + str);
				mamodel = null;
				mismatch = true;
				return;
			}
			if (mismatch = !pic.getName().equals(imgcut.name))
				Printer.p("AnimU", 67, "imgcut does not match: " + str);
			parts = imgcut.cut(num);
			partial();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String[] names() {
		partial();
		if (anims.length == 4)
			return strs0;
		if (anims.length == 5)
			return strs2;
		return strs1;
	}

	protected void partial() {
		if (!partial) {
			partial = true;
			mamodel = MaModel.newIns(str + ".mamodel");
			if (VFile.getFile(str + "_zombie00.maanim") != null)
				anims = new MaAnim[7];
			else if (VFile.getFile(str + "_entry.maanim") != null)
				anims = new MaAnim[5];
			else
				anims = new MaAnim[4];
			for (int i = 0; i < 4; i++)
				anims[i] = MaAnim.newIns(str + "0" + i + ".maanim");
			if (anims.length == 5)
				anims[4] = MaAnim.newIns(str + "_entry.maanim");
			if (anims.length == 7)
				for (int i = 0; i < 3; i++)
					anims[i + 4] = MaAnim.newIns(str + "_zombie0" + i + ".maanim");
		}
	}

}
