package util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class AnimU {

	protected MaAnim[] anims;
	protected ImgCut imgcut;
	protected MaModel mamodel;
	private BufferedImage num;
	private BufferedImage[] parts;

	protected boolean partial = false;

	public AnimU(String str) {
		try {
			num = ImageIO.read(new File(str + ".png"));
			imgcut = new ImgCut(str + ".imgcut");
			parts = imgcut.cut(num);
			mamodel = new MaModel(str + ".mamodel");
			if (new File(str + "_zombie00.maanim").exists())
				anims = new MaAnim[7];
			else if (new File(str + "_entry.maanim").exists())
				anims = new MaAnim[5];
			else
				anims = new MaAnim[4];
			for (int i = 0; i < 4; i++)
				anims[i] = new MaAnim(str + "0" + i + ".maanim");
			if (anims.length == 5)
				anims[4] = new MaAnim(str + "_entry.maanim");
			if (anims.length == 7)
				for (int i = 0; i < 3; i++)
					anims[i + 4] = new MaAnim(str + "_zombie0" + i + ".maanim");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EAnimU getEAnim(int t) {
		if (mamodel == null || t >= anims.length || anims[t] == null)
			return null;
		return new EAnimU(this, t);
	}

	public BufferedImage parts(int i) {
		if (i < 0 || i >= parts.length)
			return null;
		return parts[i];
	}

}
