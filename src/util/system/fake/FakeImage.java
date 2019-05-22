package util.system.fake;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public interface FakeImage {

	public static FakeImage read(File file) throws IOException {
		return FIBI.build(ImageIO.read(file));
	}

	public static FakeImage read(InputStream in) throws IOException {
		return FIBI.build(ImageIO.read(in));
	}

	public static boolean write(FakeImage num, String str, File f) throws IOException {
		return ImageIO.write((RenderedImage) num.bimg(), str, f);
	}

	public static boolean write(FakeImage img, String str, OutputStream out) throws IOException {
		return ImageIO.write((RenderedImage) img.bimg(), str, out);
	}

	public Object bimg();
	
	public Object gl();

	public int getHeight();

	public int getRGB(int i, int j);

	public FakeImage getSubimage(int i, int j, int k, int l);

	public int getWidth();

	public void setRGB(int i, int j, int p);

}
