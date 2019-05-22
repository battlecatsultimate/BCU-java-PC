package jogl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import util.system.fake.FakeImage;

public class GLImage implements FakeImage {

	private final GLImage par;
	private final TextureData data;
	private final int[] rect;

	private Texture t;

	public GLImage(InputStream is) throws IOException {
		par = null;
		data = TextureIO.newTextureData(GLStatic.GLP, is, GLStatic.MIP, "PNG");
		rect = new int[] { 0, 0, data.getWidth(), data.getHeight() };
	}

	private GLImage(GLImage p, int... r) {
		par = p;
		data = p.data;
		rect = r;
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
	public void setRGB(int i, int j, int p) {
	}

}
