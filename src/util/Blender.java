package util;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class Blender implements Composite, CompositeContext {

	private final int opa, glow;

	protected Blender(int opa2, int gl) {
		opa = opa2;
		glow = gl;
	}

	@Override
	public void compose(Raster src, Raster dst, WritableRaster out) {
		comp3(src, dst, out);
	}

	@Override
	public CompositeContext createContext(ColorModel arg0, ColorModel arg1, RenderingHints arg2) {
		return this;
	}

	@Override
	public void dispose() {
	}

	private void comp3(Raster src, Raster dst, WritableRaster out) {
		int w = src.getWidth();
		int h = src.getHeight();
		int[] srcs = src.getPixels(0, 0, w, h, new int[w * h * 4]);
		int[] dsts = dst.getPixels(0, 0, w, h, new int[w * h * 3]);
		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++) {
				int ind = i * h + j;
				int a = srcs[ind << 2 | 3] * opa >> 8;
				for (int k = 0; k < 3; k++) {
					if (glow == 1)
						dsts[ind * 3 + k] = Math.min(255, dsts[ind * 3 + k] + (srcs[ind << 2 | k] * a >> 8));
					else if (glow == 2)
						dsts[ind * 3 + k] -= dsts[ind * 3 + k] * (255 - srcs[ind << 2 | k]) * a >> 16;
					else if (glow == 3)
						dsts[ind * 3 + k] += (255 - dsts[ind * 3 + k]) * srcs[ind << 2 | k] * a >> 16;
					else if (glow == -1)
						dsts[ind * 3 + k] = Math.max(0, dsts[ind * 3 + k] - (srcs[ind << 2 | k] * a >> 8));
					else
						dsts[ind * 3 + k] = dsts[ind * 3 + k] * (255 - a) + srcs[ind << 2 | k] * a >> 8;
				}
			}
		out.setPixels(0, 0, w, h, dsts);
	}

}
