package jogl.util;

import common.CommonStatic;
import common.system.fake.FakeImage;
import common.system.fake.ImageBuilder;
import utilpc.awt.FIBI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

public class GLIB extends ImageBuilder<BufferedImage> {

	@Override
	public FakeImage build(BufferedImage o) {
		if (CommonStatic.getConfig().icon)
			return FIBI.builder.build(o);
		return new AmbImage(o);
	}

	@Override
	public FakeImage build(BufferedImage o, boolean appended) {
		return null; // TODO: GLIB build(image, boolean)
	}

	@Override
	public FakeImage build(int w, int h) {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB_PRE);

		return new AmbImage(img);
	}

	@Override
	public FakeImage build(File f) throws IOException {
		if (CommonStatic.getConfig().icon)
			return FIBI.builder.build(f);
		return new AmbImage(f);
	}

	@Override
	public FakeImage build(Supplier<InputStream> sup) throws IOException {
		if (CommonStatic.getConfig().icon)
			return FIBI.builder.build(sup);
		return new AmbImage(sup);
	}

	@Override
	public boolean write(FakeImage o, String fmt, Object out) throws IOException {
		return FIBI.builder.write(o, fmt, out);
	}

}
