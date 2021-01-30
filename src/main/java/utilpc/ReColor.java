package utilpc;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ReColor {

	public static final String[] strs, strf;
	private static final int[][] iss;
	private static final float[][] fs;

	static {
		strs = new String[] { "red", "alien", "zombie" };
		strf = new String[] { "red", "alien", "zombie", "white", "black" };
		iss = new int[][] { { 255, 84, 1 }, { 160, 255, 238 }, { 184, 113, 255 }, { 255, 255, 255 }, { 0, 0, 0 } };
		fs = new float[5][];
		for (int i = 0; i < 5; i++)
			fs[i] = Color.RGBtoHSB(iss[i][0], iss[i][1], iss[i][2], new float[3]);
	}

	public static void transcolor(BufferedImage bimg, int[] coor, int from, int to) {
		if (coor == null)
			coor = new int[] { 0, 0, bimg.getWidth(), bimg.getHeight() };
		for (int i = 0; i < coor[2]; i++)
			for (int j = 0; j < coor[3]; j++) {
				int x = i + coor[0];
				int y = j + coor[1];
				int p = bimg.getRGB(x, y);
				bimg.setRGB(x, y, real(p, from, to));
			}
	}

	private static int real(int p, int from, int to) {
		int r = (p >> 16) & 0xff;
		int g = (p >> 8) & 0xff;
		int b = p & 0xff;
		int a = (p >> 24) & 0xff;
		float[] hsb = new float[3];
		Color.RGBtoHSB(r, g, b, hsb);
		if (Math.abs(hsb[0] - fs[from][0]) > 5e-2)
			return p;
		float agre = hsb[1] / fs[from][1] * fs[to][1];
		float alig = hsb[2] / fs[from][2] * fs[to][2];
		Color c = Color.getHSBColor(fs[to][0], agre, alig);
		r = c.getRed();
		g = c.getGreen();
		b = c.getBlue();
		int ans = b + (g << 8) + (r << 16) + (a << 24);
		return ans;
	}

}
