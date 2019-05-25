package jogl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import util.system.fake.FakeImage;
import util.system.fake.ImageBuilder;
import util.system.fake.awt.FIBI;

public class GLIB extends ImageBuilder {

	@Override
	public FakeImage build(Object o) throws IOException {
		if (o == null)
			return null;
		if (o instanceof FakeImage)
			return (FakeImage) o;
		if (o instanceof BufferedImage)
			return new AmbImage((BufferedImage) o);
		if (o instanceof File)
			return new AmbImage((File) o);
		if (o instanceof InputStream)
			return new AmbImage((InputStream) o);
		return null;
	}

	@Override
	public boolean write(FakeImage o, String fmt, Object out) throws IOException {
		return FIBI.builder.write(o, fmt, out);
	}

}
