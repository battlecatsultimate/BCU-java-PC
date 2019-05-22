package jogl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import util.system.fake.FIBI;
import util.system.fake.FakeImage;

public class AmbImage implements FakeImage {

	private final InputStream stream;
	private final File file;
	private final AmbImage par;
	private final int[] cs;
	private boolean force;

	private FIBI bimg;
	private GLImage gl;

	private void checkBI() {
		if (bimg != null)
			return;
		try {
			if (stream != null)

				bimg = FIBI.read(stream);
			else if (file != null)
				bimg = FIBI.read(file);
			else {
				par.checkBI();
				bimg = par.bimg.getSubimage(cs[0], cs[1], cs[2], cs[3]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void forceBI() {
		checkBI();
		force = true;
		gl = null;
	}

	private void checkGL() {
		if (gl != null)
			return;
		try {
			if (force)
				gl = new GLImage(bimg.bimg());
			if (stream != null)
				gl = new GLImage(stream);
			else if (file != null)
				gl = new GLImage(file);
			else {
				par.checkBI();
				bimg = par.bimg.getSubimage(cs[0], cs[1], cs[2], cs[3]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void check() {
		checkBI();
	}

	public AmbImage(InputStream is) {
		stream = is;
		file = null;
		par = null;
		cs = null;
	}

	public AmbImage(File f) {
		stream = null;
		file = f;
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
	public void setRGB(int i, int j, int p) {
		forceBI();
		bimg.setRGB(i, j, p);
	}

	@Override
	public Object gl() {
		checkGL();
		return gl;
	}

}
