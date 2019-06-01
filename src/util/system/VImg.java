package util.system;

import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;

import util.ImgCore;
import util.anim.ImgCut;
import util.system.fake.FakeImage;
import util.system.files.FileData;
import util.system.files.VFile;

public class VImg extends ImgCore {

	private final VFile<? extends FileData> file;

	public String name = "";

	private FakeImage bimg = null;
	private boolean loaded = false;
	private ImgCut ic;
	private String marker;

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

	public void mark(String string) {
		marker=string;
	}

	public synchronized void check() {
		if (!loaded)
			load();
	}

	public ImageIcon getIcon() {
		check();
		if (bimg == null || bimg.bimg() == null)
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
		if(marker!=null)
			bimg.mark(marker);
		if (ic != null)
			bimg = ic.cut(bimg)[0];
		try {
			bimg.getWidth();
		} catch (Exception e) {
			bimg = null;
		}
	}

}
