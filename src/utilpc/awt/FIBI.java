package utilpc.awt;

import common.pack.Context;
import common.system.fake.FakeImage;
import common.system.fake.ImageBuilder;
import common.system.files.FileData;
import common.util.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.function.Supplier;

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

}

class BIBuilder extends ImageBuilder {

	@SuppressWarnings("unchecked")
	@Override
	public FIBI build(Object o) throws IOException {
		if (o == null)
			return null;
		if (o instanceof BufferedImage)
			return new FIBI((BufferedImage) o);
		BufferedImage b = null;
		if (o instanceof File)
			b = ImageIO.read((File) o);
		else if (o instanceof FileData)
			b = ImageIO.read(((FileData) o).getStream());
		else if (o instanceof byte[])
			b = ImageIO.read(new ByteArrayInputStream((byte[]) o));
		else if (o instanceof Supplier)
			b = ImageIO.read(((Supplier<InputStream>) o).get());
		else
			throw new IOException("unknown class type " + o.getClass());
		if (b == null)
			return null;
		return new FIBI(b);
	}

	@Override
	public boolean write(FakeImage img, String fmt, Object o) throws IOException {
		BufferedImage bimg = (BufferedImage) img.bimg();
		if (bimg == null)
			return false;
		if (o instanceof File) {
			Data.err(() -> Context.check((File) o));
			return ImageIO.write(bimg, fmt, (File) o);
		} else if (o instanceof OutputStream)
			return ImageIO.write(bimg, fmt, (OutputStream) o);
		return false;
	}
}
