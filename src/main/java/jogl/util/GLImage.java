package jogl.util;

import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import common.system.P;
import common.system.fake.FakeGraphics;
import common.system.fake.FakeImage;
import main.Printer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static jogl.GLStatic.GLP;
import static jogl.GLStatic.MIP;

public class GLImage implements FakeImage {

	protected static GLImage build(Object o) {
		try {
			TextureData data = null;
			if (o instanceof byte[])
				o = new ByteArrayInputStream((byte[]) o);
			if (o instanceof File) {
				BufferedImage img = ImageIO.read((File) o);

				if(img.getType() != BufferedImage.TYPE_INT_ARGB_PRE) {
					BufferedImage temp = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);

					for(int x = 0; x < img.getWidth(); x++) {
						for(int y = 0; y < img.getHeight(); y++) {
							temp.setRGB(x, y, img.getRGB(x, y));
						}
					}

					img = temp;
				}

				data = AWTTextureIO.newTextureData(GLP, img, MIP);
			}
			if (o instanceof InputStream) {
				BufferedImage img = ImageIO.read((InputStream) o);

				if(img.getType() != BufferedImage.TYPE_INT_ARGB_PRE) {
					BufferedImage temp = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);

					for(int x = 0; x < img.getWidth(); x++) {
						for(int y = 0; y < img.getHeight(); y++) {
							temp.setRGB(x, y, img.getRGB(x, y));
						}
					}

					img = temp;
				}

				data = AWTTextureIO.newTextureData(GLP, img, MIP);
			} if (o instanceof BufferedImage) {
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

	private GLImage(TextureData b) {
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

	@Override
	public FakeImage cloneImage() {
		GLImage copy;

		if(par != null)
			copy = new GLImage(par, rect);
		else if(data != null && rect != null) {
			copy = new GLImage(data);
		} else {
			copy = null;
		}

		return copy;
	}

	@Override
	public FakeGraphics getGraphics() {
		return par.getGraphics();
	}

	protected GLImage root() {
		return par == null ? this : par.root();
	}

}
