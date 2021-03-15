package utilpc.awt;

import common.system.fake.FakeImage;
import common.system.fake.ImageBuilder;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class FIBI implements FakeImage {

	public static final ImageBuilder<BufferedImage> builder = new PCIB();

	public static FakeImage build(BufferedImage bimg2) {
		return builder.build(bimg2);
	}

	private final BufferedImage bimg;

	protected FIBI(BufferedImage read) {
		bimg = read;
	}

	@Override
	public BufferedImage bimg() {
		return bimg;
	}

	@Override
	public int getHeight() {
		return bimg.getHeight();
	}

	@Override
	public int getRGB(int i, int j) {
		return bimg.getRGB(i, j);
	}

	@Override
	public FIBI getSubimage(int i, int j, int k, int l) {
		return (FIBI) builder.build(bimg.getSubimage(i, j, k, l));
	}

	@Override
	public int getWidth() {
		return bimg.getWidth();
	}

	@Override
	public Object gl() {
		return null;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void setRGB(int i, int j, int p) {
		bimg.setRGB(i, j, p);
	}

	@Override
	public void unload() {

	}

	@Override
	public FakeImage cloneImage() {
		FIBI copy;

		if(bimg != null) {
			BufferedImage ori = bimg;

			ColorModel cm = ori.getColorModel();
			boolean alphaMulti = ori.isAlphaPremultiplied();
			WritableRaster wr = ori.copyData(null);

			copy = new FIBI(new BufferedImage(cm, wr, alphaMulti, null));
		} else {
			copy = new FIBI(null);
		}

		return copy;
	}

}
