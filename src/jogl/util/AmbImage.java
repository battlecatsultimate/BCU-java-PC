package jogl.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jogl.GLStatic;
import util.system.fake.FakeImage;
import util.system.fake.awt.FIBI;

public class AmbImage implements FakeImage {

	private byte[] stream;
	private File file;
	private AmbImage par;
	private int[] cs;
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

	protected AmbImage(byte[] is) {
		stream = is;
		file = null;
		par = null;
		cs = null;
	}

	protected AmbImage(File f) {
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
	public void mark(Object o) {
		if (o instanceof String) {
			String str = (String) o;
			if (str.equals("uni"))
				forceBI();
			if (str.equals("BG"))
				checkBI();
			if (str.equals("edi"))
				forceBI();
			if (str.equals("uni or edi"))
				forceBI();
			if (str.equals("recolor"))
				checkBI();
			if (str.equals("recolor-finished")) {
				// TODO if graphics is faster?
				ByteArrayOutputStream abos = new ByteArrayOutputStream();
				try {
					ImageIO.write(bimg(), "PNG", abos);
				} catch (IOException e) {
					e.printStackTrace();
				}
				force = false;
				stream = abos.toByteArray();
			}
		}
	}

	@Override
	public void setRGB(int i, int j, int p) {
		forceBI();
		bimg.setRGB(i, j, p);
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
			gl = GLImage.build(stream);
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
