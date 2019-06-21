package utilpc;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import common.CommonStatic.Itf;
import common.util.Res;
import common.util.pack.Background;
import common.util.system.VImg;
import page.LoadPage;
import page.MainLocale;
import page.Page;
import utilpc.awt.FG2D;

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

	public static ImageIcon getBg(Background bg, int w, int h) {
		double r = h / 1100.0;
		int fw = (int) (768 * r);
		int fh = (int) (510 * r);
		bg.check();
		BufferedImage temp = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) temp.getGraphics();
		FG2D fg = new FG2D(g);
		if (bg.top && bg.parts.length > Background.TOP)
			for (int i = 0; i * fw < w; i++)
				fg.drawImage(bg.parts[Background.TOP], fw * i, 0, fw, fh);
		else {
			fg.gradRect(0, 0, w, fh, 0, 0, bg.cs[0], 0, fh, bg.cs[1]);
		}
		for (int i = 0; i * fw < w; i++)
			fg.drawImage(bg.parts[Background.BG], fw * i, fh, fw, fh);
		fg.gradRect(0, fh * 2, w, h - fh * 2, 0, fh * 2, bg.cs[2], 0, fh * 3, bg.cs[3]);
		g.dispose();
		return new ImageIcon(temp);
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
