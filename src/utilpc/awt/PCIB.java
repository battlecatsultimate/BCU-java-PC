package utilpc.awt;

import common.pack.Context;
import common.system.fake.FakeImage;
import common.system.fake.ImageBuilder;
import common.util.Data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

import javax.imageio.ImageIO;

public class PCIB extends ImageBuilder<BufferedImage> {

	@Override
	public FakeImage build(BufferedImage o) {
		return new FIBI(o);
	}

	@Override
	public FakeImage build(File f) throws IOException {
		return build(ImageIO.read(f));
	}

	@Override
	public FakeImage build(Supplier<InputStream> sup) throws IOException {
		return build(ImageIO.read(sup.get()));
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
