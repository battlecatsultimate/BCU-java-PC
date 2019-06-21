package utilpc;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import common.CommonStatic.Itf;
import common.util.Res;
import common.util.system.VImg;
import page.LoadPage;
import page.MainLocale;
import page.Page;

public class UtilPC {

	public static class PCItr extends Itf {

		@Override
		public String get(int i, String s) {
			return Page.get(i, s);
		}

		@Override
		public String[] get(int i, String s, int n) {
			return Page.get(i, s, n);
		}

		@Override
		public String[] getLoc(int i, String... s) {
			return MainLocale.getLoc(i, s);
		}

		@Override
		public void prog(String str) {
			LoadPage.prog(str);

		}

		@Override
		public VImg readReal(File fi) {
			try {
				return new VImg(ImageIO.read(fi));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

	}

	public static BufferedImage getIcon(int type, int id) {
		type += id / 100;
		id %= 100;
		if (Res.icon[type][id] == null)
			return null;
		return (BufferedImage) Res.icon[type][id].getImg().bimg();
	}

	public static ImageIcon getIcon(VImg v) {
		v.check();
		if (v.bimg == null || v.bimg.bimg() == null)
			return null;
		return new ImageIcon((Image) v.bimg.bimg());
	}

}
