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
		if(o == null)
			return new FIBI(o);

		if(o.getType() != BufferedImage.TYPE_INT_ARGB_PRE) {
			BufferedImage temp = new BufferedImage(o.getWidth(), o.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);

			for(int x = 0; x < o.getWidth(); x++) {
				for(int y = 0; y < o.getHeight(); y++) {
					temp.setRGB(x, y, o.getRGB(x, y));
				}
			}

			o = temp;
		}

		return new FIBI(o);
	}

	@Override
	public FakeImage build(File f) throws IOException {
		BufferedImage o = ImageIO.read(f);

		if(o.getType() != BufferedImage.TYPE_INT_ARGB_PRE) {
			BufferedImage temp = new BufferedImage(o.getWidth(), o.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);

			for(int x = 0; x < o.getWidth(); x++) {
				for(int y = 0; y < o.getHeight(); y++) {
					temp.setRGB(x, y, o.getRGB(x, y));
				}
			}

			o = temp;
		}

		return build(o);
	}

	@Override
	public FakeImage build(Supplier<InputStream> sup) throws IOException {
		InputStream stream = sup.get();

		BufferedImage o = ImageIO.read(stream);

		stream.close();

		if(o == null)
			return build(o);

		if(o.getType() != BufferedImage.TYPE_INT_ARGB_PRE) {
			BufferedImage temp = new BufferedImage(o.getWidth(), o.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);

			for(int x = 0; x < o.getWidth(); x++) {
				for(int y = 0; y < o.getHeight(); y++) {
					temp.setRGB(x, y, o.getRGB(x, y));
				}
			}

			o = temp;
		}

		return build(o);
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
