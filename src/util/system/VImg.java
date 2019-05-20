package util.system;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import util.ImgCore;
import util.anim.ImgCut;
import util.system.files.FileData;
import util.system.files.VFile;

public class VImg extends ImgCore {

	public static FakeImage combine(VImg... imgs) {
		FakeImage[] parts = new FakeImage[imgs.length];
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
			g.drawImage(parts[i].bimg(), pos[i], 0, null);
		g.dispose();
		return FIBI.build(ans);
	}

	private final VFile<? extends FileData> file;

	public String name = "";

	private FakeImage bimg = null;
	private boolean loaded = false;
	private ImgCut ic;

	public VImg(BufferedImage img) {
		file = null;
		loaded = true;
		bimg = FIBI.build(img);
	}

	public VImg(BufferedImage img, String str) {
		this(img);
		name = str;
	}

	public VImg(FakeImage img) {
		file = null;
		loaded = true;
		bimg = img;
	}

	public VImg(String str) {
		this(VFile.getFile(str));
	}

	public VImg(VFile<? extends FileData> vf) {
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
		return new ImageIcon(bimg.bimg());
	}

	public FakeImage getImg() {
		check();
		return bimg;
	}

	public void setCut(ImgCut cut) {
		ic = cut;
	}

	public void setImg(FakeImage img) {
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
		bimg = file.getData().getImg();
		if (ic != null)
			bimg = ic.cut(bimg)[0];
	}

}
