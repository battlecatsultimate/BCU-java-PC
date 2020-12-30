package jogl.util;

import common.system.fake.FakeImage;
import jogl.GLStatic;
import utilpc.awt.FIBI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.function.Supplier;

public class AmbImage implements FakeImage {

	private Supplier<InputStream> stream;
	private final File file;
	private final AmbImage par;
	private final int[] cs;
	private boolean force, failed;

	private FIBI bimg;
	private GLImage gl;

	protected AmbImage(BufferedImage b) {
		stream = null;
		file = null;
		par = null;
		cs = null;
		bimg = (FIBI) FIBI.build(b);
		force = true;
	}

	protected AmbImage(File f) {
		stream = null;
		file = f;
		par = null;
		cs = null;
	}

	protected AmbImage(Supplier<InputStream> sup) {
		stream = sup;
		file = null;
		par = null;
		cs = null;
	}

	private AmbImage(AmbImage img, int... c) {
		stream = null;
		file = null;
		par = img;
		cs = c;
	}

	@Override
	public BufferedImage bimg() {
		checkBI();
		if (bimg == null)
			return null;
		return bimg.bimg();
	}

	@Override
	public int getHeight() {
		check();
		return bimg != null ? bimg.getHeight() : gl.getHeight();
	}

	@Override
	public int getRGB(int i, int j) {
		checkBI();
		return bimg.getRGB(i, j);
	}

	@Override
	public FakeImage getSubimage(int i, int j, int k, int l) {
		return new AmbImage(this, i, j, k, l);
	}

	@Override
	public int getWidth() {
		check();
		return bimg != null ? bimg.getWidth() : gl.getWidth();
	}

	@Override
	public Object gl() {
		checkGL();
		return gl;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void mark(Marker str) {

		if (str == Marker.UNI)
			forceBI();
		if (str == Marker.BG) {
			checkBI();
			if (bimg.bimg().getWidth() % 4 != 0)
				force = true;
		}
		if (str == Marker.EDI)
			forceBI();
		if (str == Marker.RECOLOR)
			checkBI();
		if (str == Marker.RECOLORED) {
			// TODO if graphics is faster?
			ByteArrayOutputStream abos = new ByteArrayOutputStream();
			try {
				ImageIO.write(bimg(), "PNG", abos);
			} catch (IOException e) {
				e.printStackTrace();
			}
			force = false;
			stream = () -> new ByteArrayInputStream(abos.toByteArray());
			gl = null;

		}
	}

	@Override
	public void setRGB(int i, int j, int p) {
		forceBI();
		bimg.setRGB(i, j, p);
	}

	@Override
	public void unload() {
	}

	@Override
	public FakeImage cloneImage() {
		AmbImage copy;

		if(bimg != null) {
			BufferedImage ori = bimg.bimg();

			ColorModel cm = ori.getColorModel();
			boolean alphaMulti = ori.isAlphaPremultiplied();
			WritableRaster wr = ori.copyData(null);

			copy = new AmbImage(new BufferedImage(cm, wr, alphaMulti, null));
		} else if(file != null) {
			copy = new AmbImage(file);
		} else if(stream != null) {
			copy = new AmbImage(stream);
		} else if(par != null && cs != null) {
			copy = new AmbImage(par, cs.clone());
		} else {
			copy = null;
		}

		return copy;
	}

	private void check() {
		if (gl != null || bimg != null)
			return;
		if (GLStatic.ALWAYS_GLIMG || GLGraphics.count > 0)
			checkGL();
		if (gl == null)
			checkBI();
	}

	private void checkBI() {
		if (bimg != null || failed)
			return;
		try {
			if (stream != null)
				bimg = (FIBI) FIBI.builder.build(stream);
			else if (file != null)
				bimg = (FIBI) FIBI.builder.build(file);
			else {
				par.checkBI();
				bimg = par.bimg.getSubimage(cs[0], cs[1], cs[2], cs[3]);
			}
			if (bimg == null)
				failed = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkGL() {
		if (gl != null)
			return;
		if (force)
			gl = GLImage.build(bimg.bimg());
		else if (stream != null)
			gl = GLImage.build(stream.get());
		else if (file != null)
			gl = GLImage.build(file);
		else {
			par.checkGL();
			if (par.gl != null)
				gl = par.gl.getSubimage(cs[0], cs[1], cs[2], cs[3]);
		}
	}

	private void forceBI() {
		checkBI();
		force = true;
		gl = null;
	}

}
