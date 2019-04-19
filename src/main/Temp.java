package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import io.Reader;
import io.Writer;

public class Temp {

	public static void main(String[] args) {
		System.out.println(Reader.parseIntN("001"));
	}

	public static void main$0(String[] args) throws FileNotFoundException {
		for (File fi : new File("./lib/lang/zh/").listFiles()) {
			Queue<String> qs = Reader.readLines(fi);
			PrintStream out = new PrintStream(fi);
			for (String str : qs)
				out.println(unescapeJava(str));
			out.close();
		}

	}

	public static void main$1(String[] args) {
		try {
			BufferedImage b0 = ImageIO.read(new File("./img/in.png"));

			int w = b0.getWidth();
			int h = b0.getHeight();
			for (int i = 0; i < w; i++)
				for (int j = 0; j < h; j++) {
					int p = b0.getRGB(i, j);
					int b = p & 255;
					int g = p >> 8 & 255;
					int r = p >> 16 & 255;
					int a = p >> 24;
					p = a << 24 | r << 16 | b << 8 | g;
					b0.setRGB(i, j, p);
				}

			File f = new File("./img/test.png");
			Writer.check(f);
			ImageIO.write(b0, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main$2(String[] arguments) throws IOException, NoSuchAlgorithmException {
		File f0 = new File("./img/md0");
		File f1 = new File("./img/md1");
		byte[] bs0 = Files.readAllBytes(f0.toPath());
		byte[] bs1 = Files.readAllBytes(f1.toPath());
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		for (int i = 0; i < bs0.length; i++)
			if (bs0[i] != bs1[i])
				System.out.println(i);
		md5.reset();
	}

	public static void main$3(String[] args) {
		File udi = new File("./org/page/udi_EFFG.png");
		int x = 13, y = 1, w = 298, h = 112, w1 = 85, h1 = 32;
		BufferedImage bimg = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_ARGB);
		BufferedImage src;
		try {
			src = ImageIO.read(udi);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Graphics2D gra = (Graphics2D) bimg.getGraphics();
		gra.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		src = src.getSubimage(x, y, w, h);
		gra.drawImage(src, 0, 0, w1, h1, null);
		gra.dispose();
		File out = new File(udi.getParent() + "/e" + udi.getName().substring(1));
		try {
			ImageIO.write(bimg, "PNG", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("done: " + out.getName());
	}

	public static void main$4(String[] args) {
		File f0 = new File("./img/in.png");
		File f1 = new File("./img/out.png");
		try {
			if (!f1.exists())
				f1.createNewFile();
			ImageIO.write(retc(ImageIO.read(f0)), "PNG", f1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// create unit icon
	public static void main$5(String[] args) {
		File f = new File("./org/unit/");
		File[] fs = f.listFiles();
		for (File file : fs) {
			for (File fi : file.listFiles())
				if (fi.isDirectory()) {
					boolean cont = false;
					File udi = null;
					for (File fp : fi.listFiles())
						if (fp.getName().startsWith("edi"))
							cont = true;
						else if (fp.getName().startsWith("udi"))
							udi = fp;
					if (!cont && udi != null) {
						int x = 13, y = 1, w = 298, h = 112, w1 = 85, h1 = 32;
						BufferedImage bimg = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_ARGB);
						BufferedImage src;
						try {
							src = ImageIO.read(udi);
						} catch (IOException e) {
							e.printStackTrace();
							continue;
						}
						Graphics2D gra = (Graphics2D) bimg.getGraphics();
						gra.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
						gra.setColor(Color.BLACK);
						gra.fillRect(0, 0, w1, h1);
						src = src.getSubimage(x, y, w, h);
						gra.drawImage(src, 0, 0, w1, h1, null);
						gra.dispose();
						File out = new File(udi.getParent() + "/e" + udi.getName().substring(1));
						try {
							ImageIO.write(bimg, "PNG", out);
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println("done: " + out.getName());
					}
				}

		}
	}

	public static void main$6(String[] args) {
		System.out.println(UIManager.getSystemLookAndFeelClassName());
		System.out.println(UIManager.getCrossPlatformLookAndFeelClassName());
		System.out.println(UIManager.getLookAndFeel());

		System.out.println(UIManager.getLookAndFeelDefaults());
		LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo laf : lafs)
			System.out.println(laf);
	}

	public static BufferedImage real(BufferedImage bimg, BufferedImage exc) {
		int w = bimg.getWidth();
		int h = bimg.getHeight();
		int type = BufferedImage.TYPE_INT_ARGB;
		BufferedImage ans = new BufferedImage(w, h, type);
		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++) {
				int p = bimg.getRGB(i, j);
				int ae = exc.getRGB(i, j);
				ans.setRGB(i, j, real(p, ae));
			}

		return ans;
	}

	public static BufferedImage retc(BufferedImage bimg) {
		int w = bimg.getWidth();
		int h = bimg.getHeight();
		int type = BufferedImage.TYPE_INT_ARGB;
		BufferedImage ans = new BufferedImage(w, h, type);
		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++) {
				int p = bimg.getRGB(i, j);
				ans.setRGB(i, j, retc(p));
			}

		return ans;
	}

	public static String unescapeJava(String escaped) {
		if (escaped.indexOf("\\u") == -1)
			return escaped;

		String processed = "";

		int position = escaped.indexOf("\\u");
		while (position != -1) {
			if (position != 0)
				processed += escaped.substring(0, position);
			String token = escaped.substring(position + 2, position + 6);
			escaped = escaped.substring(position + 6);
			processed += (char) Integer.parseInt(token, 16);
			position = escaped.indexOf("\\u");
		}
		processed += escaped;

		return processed;
	}

	private static int real(int p, int ae) {
		int r = (p >> 16) & 0xff;
		int g = (p >> 8) & 0xff;
		int b = p & 0xff;
		int a = (ae >> 24) & 0xff;
		if (a == 0)
			return 0;
		r = (r + a - 255) * 255 / a;
		g = (g + a - 255) * 255 / a;
		b = (b + a - 255) * 255 / a;
		int ans = b + (g << 8) + (r << 16) + (a << 24);
		return ans;
	}

	private static int retc(int p) {
		int r = (p >> 16) & 0xff;
		int g = (p >> 8) & 0xff;
		int b = p & 0xff;
		int a = (p >> 24) & 0xff;
		if (a == 0)
			return 0;
		r = 255 - r;
		g = 255 - g;
		b = 255 - b;
		int ans = b + (g << 8) + (r << 16) + (a << 24);
		return ans;
	}

}
