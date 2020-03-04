package utilpc.awt;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import common.system.fake.FakeImage;
import common.system.fake.ImageBuilder;
import io.Writer;

public class FIBI implements FakeImage {

	public static final ImageBuilder builder = new BIBuilder();

	public static FakeImage build(BufferedImage bimg2) {
		try {
			return builder.build(bimg2);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
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
		try {
			return (FIBI) builder.build(bimg.getSubimage(i, j, k, l));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
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
	public void setRGB(int i, int j, int p) {
		bimg.setRGB(i, j, p);
	}

	@Override
	public void unload() {
		
	}

}

class BIBuilder extends ImageBuilder {

	@Override
	public FIBI build(Object o) throws IOException {
		if (o == null)
			return null;
		if (o instanceof BufferedImage)
			return new FIBI((BufferedImage) o);

		BufferedImage b = null;
		if (o instanceof File)
			b = ImageIO.read((File) o);
		else if (o instanceof byte[])
			b = ImageIO.read(new ByteArrayInputStream((byte[]) o));
		return b == null ? null : new FIBI(b);
	}

	@Override
	public boolean write(FakeImage img, String fmt, Object o) throws IOException {
		BufferedImage bimg = (BufferedImage) img.bimg();
		if (bimg == null)
			return false;
		if (o instanceof File) {
			Writer.check((File) o);
			return ImageIO.write(bimg, fmt, (File) o);
		} else if (o instanceof OutputStream)
			return ImageIO.write(bimg, fmt, (OutputStream) o);
		return false;
	}
}
