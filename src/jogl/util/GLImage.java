package jogl.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import common.system.P;
import common.system.fake.FakeImage;
import jogl.GLStatic;
import main.Printer;

import static jogl.GLStatic.*;

public class GLImage implements FakeImage {

	protected static GLImage build(Object o) {
		try {
			TextureData data = null;
			if (o instanceof byte[])
				o = new ByteArrayInputStream((byte[]) o);
			if (o instanceof File)
				data = TextureIO.newTextureData(GLStatic.GLP, (File) o, GLStatic.MIP, "PNG");
			if (o instanceof InputStream)
				data = TextureIO.newTextureData(GLP, (InputStream) o, MIP, "PNG");
			if (o instanceof BufferedImage) {
				BufferedImage bimg = (BufferedImage) o;
				bimg = check(bimg);
				data = AWTTextureIO.newTextureData(GLP, bimg, MIP);
			}
			if (data == null) {
				Printer.e("GLImage", 52, "failed to load data: " + o);
				return null;
			}
			return new GLImage(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static BufferedImage check(BufferedImage b) {
		int w = b.getWidth();
		int h = b.getHeight();
		if (w % 4 == 0 && h % 4 == 0)
			return b;
		if ((w & 3) > 0)
			w = (w | 3) + 1;
		if ((h & 3) > 0)
			h = (h | 3) + 1;
		BufferedImage ans = new BufferedImage(w, h, b.getType());
		Graphics2D g = ans.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.drawImage(b, 0, 0, null);
		g.dispose();
		Printer.p("GLImage", 23, "redraw buffer");
		return ans;
	}

	private final GLImage par;
	protected final TextureData data;
	protected final int[] rect;

	private GLImage(GLImage p, int... r) {
		par = p;
		data = p.data;
		rect = r;
	}

	private GLImage(TextureData b) throws IOException {
		par = null;
		data = b;
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

	public float[] getRect() {
		float[] ans = new float[4];
		int[] br = root().rect;
		ans[0] = P.reg((rect[0] + 0.5f) / br[2]);
		ans[1] = P.reg((rect[1] + 0.5f) / br[3]);
		ans[2] = P.reg((rect[2] - 1f) / br[2]);
		ans[3] = P.reg((rect[3] - 1f) / br[3]);

		if (!data.getMustFlipVertically()) {
			ans[1] = 1 - ans[1];
			ans[3] *= -1;
		}
		return ans;
	}

	@Override
	public int getRGB(int i, int j) {
		return 0;
	}

	@Override
	public GLImage getSubimage(int i, int j, int k, int l) {
		return new GLImage(this, rect[0] + i, rect[1] + j, k, l);
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
	public boolean isValid() {
		return true;
	}

	@Override
	public void setRGB(int i, int j, int p) {
	}

	@Override
	public void unload() {

	}

	protected GLImage root() {
		return par == null ? this : par.root();
	}

}
