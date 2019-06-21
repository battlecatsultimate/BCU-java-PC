package utilpc.awt;

import java.io.IOException;

import common.util.system.fake.FakeImage;
import common.util.system.fake.ImageBuilder;

public class PCIB extends ImageBuilder {

	@Override
	public FakeImage build(Object o) throws IOException {
		if (o == null)
			return null;
		if (o instanceof FakeImage)
			return (FakeImage) o;
		return FIBI.builder.build(o);
	}

	@Override
	public boolean write(FakeImage o, String fmt, Object out) throws IOException {
		return FIBI.builder.write(o, fmt, out);
	}

}
