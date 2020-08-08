package jogl.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import common.system.fake.FakeImage;
import common.system.fake.ImageBuilder;
import common.system.files.VFile;
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
		if (o instanceof VFile<?>)
			return new AmbImage(((VFile<?>) o).getData());
		if (o instanceof BufferedImage)
			return new AmbImage((BufferedImage) o);
		if (o instanceof File)
			return new AmbImage((File) o);
		if (o instanceof byte[])
			return new AmbImage(new ByteArrayInputStream((byte[]) o));
		if (o instanceof InputStream)
			return new AmbImage((InputStream) o);
		return null;
	}

	@Override
	public boolean write(FakeImage o, String fmt, Object out) throws IOException {
		return FIBI.builder.write(o, fmt, out);
	}

}
