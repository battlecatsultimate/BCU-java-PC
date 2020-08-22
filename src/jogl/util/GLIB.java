package jogl.util;

import common.CommonStatic;
import common.system.fake.FakeImage;
import common.system.fake.ImageBuilder;
import common.system.files.FileData;
import common.system.files.VFile;
import utilpc.awt.FIBI;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

public class GLIB extends ImageBuilder {

    @SuppressWarnings("unchecked")
    @Override
    public FakeImage build(Object o) throws IOException {
        if (o == null)
            return null;
        if (o instanceof FakeImage)
            return (FakeImage) o;
        if (CommonStatic.getConfig().icon)
            return FIBI.builder.build(o);
        if (o instanceof FileData)
            return new AmbImage(((FileData) o)::getStream);
        if (o instanceof VFile<?>)
            return new AmbImage(((VFile<?>) o).getData()::getStream);
        if (o instanceof byte[])
            return new AmbImage(() -> new ByteArrayInputStream((byte[]) o));
        if (o instanceof Supplier)
            return new AmbImage((Supplier<InputStream>) o);
        if (o instanceof BufferedImage)
            return new AmbImage((BufferedImage) o);
        if (o instanceof File)
            return new AmbImage((File) o);
        throw new IOException("cannot parse input with class " + o.getClass());
    }

    @Override
    public boolean write(FakeImage o, String fmt, Object out) throws IOException {
        return FIBI.builder.write(o, fmt, out);
    }

}
