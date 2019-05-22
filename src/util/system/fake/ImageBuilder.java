package util.system.fake;

import java.io.IOException;

public abstract class ImageBuilder {

	public static ImageBuilder builder;

	public abstract FakeImage build(Object o) throws IOException;

	public abstract boolean write(FakeImage o, String fmt, Object out) throws IOException;

}
