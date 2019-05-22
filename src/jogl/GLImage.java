package jogl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import util.system.fake.FakeImage;

public class GLImage implements FakeImage {

	@SuppressWarnings("unused")
	private final GLImage par;
	private final TextureData data;
	private final int[] rect;

	@SuppressWarnings("unused")
	private Texture t;

	protected GLImage(BufferedImage b) throws IOException {
		par = null;
		data = AWTTextureIO.newTextureData(GLStatic.GLP, b, GLStatic.MIP);
		rect = new int[] { 0, 0, data.getWidth(), data.getHeight() };
	}

	protected GLImage(File is) throws IOException {
		par = null;
		data = TextureIO.newTextureData(GLStatic.GLP, is, GLStatic.MIP, "PNG");
		rect = new int[] { 0, 0, data.getWidth(), data.getHeight() };
	}

	protected GLImage(GLImage p, int... r) {
		par = p;
		data = p.data;
		rect = r;
	}

	protected GLImage(InputStream is) throws IOException {
		par = null;
		data = TextureIO.newTextureData(GLStatic.GLP, is, GLStatic.MIP, "PNG");
		rect = new int[] { 0, 0, data.getWidth(), data.getHeight() };
	}

	@Override
	public BufferedImage bimg() {
		return null;
	}

	@Override
	public int getHeight() {
		return rect[3];
	}

	@Override
	public int getRGB(int i, int j) {
		return 0;
	}

	@Override
	public FakeImage getSubimage(int i, int j, int k, int l) {
		return new GLImage(this, i, j, k, l);
	}

	@Override
	public int getWidth() {
		return rect[2];
	}

	@Override
	public Object gl() {
		return this;
	}

	@Override
	public void setRGB(int i, int j, int p) {
	}

}
