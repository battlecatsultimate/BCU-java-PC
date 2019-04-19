package util.system;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import util.ImgCore;
import util.anim.ImgCut;

public class VImg extends ImgCore {

	public static BufferedImage combine(VImg... imgs) {
		BufferedImage[] parts = new BufferedImage[imgs.length];
		int[] pos = new int[imgs.length + 1];
		int h = 0;
		for (int i = 0; i < imgs.length; i++) {
			parts[i] = imgs[i].getImg();
			pos[i + 1] = pos[i] + parts[i].getWidth();
			h = Math.max(h, parts[i].getHeight());
		}
		BufferedImage ans = new BufferedImage(pos[imgs.length], h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = ans.getGraphics();
		for (int i = 0; i < imgs.length; i++)
			g.drawImage(parts[i], pos[i], 0, null);
		g.dispose();
		return ans;
	}

	private final VFile file;

	public String name = "";

	private BufferedImage bimg = null;
	private boolean loaded = false;
	private ImgCut ic;

	public VImg(BufferedImage img) {
		file = null;
		loaded = true;
		bimg = img;
	}

	public VImg(BufferedImage img, String str) {
		this(img);
		name = str;
	}

	public VImg(String str) {
		this(VFile.getFile(str));
	}

	public VImg(VFile vf) {
		file = vf;
	}

	public synchronized void check() {
		if (!loaded)
			load();
	}

	public ImageIcon getIcon() {
		check();
		if (bimg == null)
			return null;
		return new ImageIcon(bimg);
	}

	public BufferedImage getImg() {
		check();
		return bimg;
	}

	public BufferedImage getImg(int width, int height) {
		check();
		double dw = 1.0 * width / bimg.getWidth();
		double dh = 1.0 * height / bimg.getHeight();
		double r = Math.min(dw, dh);
		int w = (int) (bimg.getWidth() * r);
		int h = (int) (bimg.getHeight() * r);
		BufferedImage ans = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		ans.getGraphics().drawImage(bimg, 0, 0, w, h, null);
		return ans;
	}

	public void setCut(ImgCut cut) {
		ic = cut;
	}

	public void setImg(BufferedImage img) {
		bimg = img;
		if (ic != null)
			bimg = ic.cut(bimg)[0];
		loaded = true;
	}

	@Override
	public String toString() {
		return file == null ? name.length() == 0 ? "img" : name : file.getName();
	}

	private void load() {
		loaded = true;
		if (file == null)
			return;
		bimg = read(file);
		if (ic != null)
			bimg = ic.cut(bimg)[0];
	}

}
