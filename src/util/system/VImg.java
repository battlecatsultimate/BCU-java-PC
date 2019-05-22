package util.system;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;

import util.ImgCore;
import util.anim.ImgCut;
import util.system.fake.FakeImage;
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
			g.drawImage((Image) parts[i].bimg(), pos[i], 0, null);
		g.dispose();
		try {
			return FakeImage.read(ans);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private final VFile<? extends FileData> file;

	public String name = "";

	private FakeImage bimg = null;
	private boolean loaded = false;
	private ImgCut ic;

	public VImg(Object o) {
		if (o instanceof String)
			file = VFile.getFile((String) o);
		else if (o instanceof VFile)
			file = (VFile<?>) o;
		else
			file = null;

		if (file == null)
			try {
				bimg = FakeImage.read(o);
			} catch (IOException e) {
				e.printStackTrace();
			}
		loaded = bimg != null;
	}

	public synchronized void check() {
		if (!loaded)
			load();
	}

	public ImageIcon getIcon() {
		check();
		if (bimg == null)
			return null;
		return new ImageIcon((Image) bimg.bimg());
	}

	public FakeImage getImg() {
		check();
		return bimg;
	}

	public void setCut(ImgCut cut) {
		ic = cut;
	}

	public void setImg(Object img) {
		try {
			bimg = FakeImage.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
