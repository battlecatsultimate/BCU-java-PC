package util.system.fake;

import java.awt.image.BufferedImage;

public class FIBI implements FakeImage {

	public static FIBI build(BufferedImage read) {
		return read == null ? null : new FIBI(read);
	}

	private final BufferedImage bimg;

	private FIBI(BufferedImage read) {
		if (read == null)
			throw new NullPointerException();
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
	public FakeImage getSubimage(int i, int j, int k, int l) {
		return FIBI.build(bimg.getSubimage(i, j, k, l));
	}

	@Override
	public int getWidth() {
		return bimg.getWidth();
	}

	@Override
	public void setRGB(int i, int j, int p) {
		bimg.setRGB(i, j, p);
	}

}
