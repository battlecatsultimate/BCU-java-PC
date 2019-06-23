package jogl.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import common.system.fake.FakeImage;
import common.system.fake.ImageBuilder;
import utilpc.awt.FIBI;

public class GLIB extends ImageBuilder {

	@Override
	public FakeImage build(Object o) throws IOException {
		if (o == null)
			return null;
		if (o instanceof FakeImage)
			return (FakeImage) o;
		if (icon)
			return FIBI.builder.build(o);
		if (o instanceof BufferedImage)
			return new AmbImage((BufferedImage) o);
		if (o instanceof File)
			return new AmbImage((File) o);
		if (o instanceof byte[])
			return new AmbImage((byte[]) o);
		return null;
	}

	@Override
	public boolean write(FakeImage o, String fmt, Object out) throws IOException {
		return FIBI.builder.write(o, fmt, out);
	}

}
