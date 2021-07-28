package utilpc.awt;

import page.view.ViewBox;

import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class Blender implements Composite, CompositeContext {

	private final int opa, glow;

	public Blender(int opa2, int gl) {
		opa = opa2;
		glow = gl;
	}

	@Override
	public void compose(Raster src, Raster dst, WritableRaster out) {
		if (ViewBox.Conf.white)
			comp4(src, dst, out);
		else
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
						dsts[ind * 3 + k] = ((255 - a) * dsts[ind * 3 + k] + a * (dsts[ind * 3 + k] + ((255 - dsts[ind * 3 + k]) * srcs[ind << 2 | k] * a >> 16))) >> 8;
					else if (glow == -1)
						dsts[ind * 3 + k] = Math.max(0, dsts[ind * 3 + k] - (srcs[ind << 2 | k] * a >> 8));
					else if(glow == -2) {
						float d = dsts[ind * 3 + k] / 255f;
						float s = (srcs[ind << 2 | k] * a >> 8) / 255f;

						if(d > 0.5f) {
							dsts[ind * 3 + k] = (int) ((255 - a) * d + a * (1 - ( 1- 2 * (d - 0.5)) * (1 - s)));
						} else {
							dsts[ind * 3 + k] = (int) ((255 - a) * d + a * 2 * d * s);
						}
					} else
						dsts[ind * 3 + k] = dsts[ind * 3 + k] * (255 - a) + srcs[ind << 2 | k] * a >> 8;
				}
			}
		out.setPixels(0, 0, w, h, dsts);
	}

	private void comp4(Raster src, Raster dst, WritableRaster out) {
		int w = src.getWidth();
		int h = src.getHeight();
		int[] srcs = src.getPixels(0, 0, w, h, new int[w * h * 4]);
		int[] dsts = dst.getPixels(0, 0, w, h, new int[w * h * 4]);
		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++) {
				int ind = i * h + j;
				int a = srcs[ind << 2 | 3] * opa >> 8;
				for (int k = 0; k < 3; k++) {
					int idx = ind << 2 | k;
					if (glow == 1)
						dsts[idx] = Math.min(255, dsts[idx] + (srcs[idx] * a >> 8));
					else if (glow == 2)
						dsts[idx] -= dsts[idx] * (255 - srcs[idx]) * a >> 16;
					else if (glow == 3)
						dsts[idx] += (255 - dsts[idx]) * srcs[idx] * a >> 16;
					else if (glow == -1)
						dsts[idx] = Math.max(0, dsts[idx] - (srcs[idx] * a >> 8));
					else if (glow == -2) {
						float d = dsts[idx] / 255f;
						float s = (srcs[idx] * a >> 8) / 255f;

						if(d > 0.5f) {
							dsts[idx] = (int) ((255 - a) * d + a * (1 - (1 - 2 * (d - 0.5)) * (1 - s)));
						} else {
							dsts[idx] = (int) ((255 - a) * d + a * 2 * d * s);
						}
					} else
						dsts[idx] = (dsts[idx] * (255 - a) + srcs[idx] * a >> 8);
				}

				if (glow == 1) {
					a = (srcs[ind << 2] + srcs[ind << 2 | 1] + srcs[ind << 2 | 2]) * a / 3 >> 8;
				}

				dsts[ind << 2 | 3] = (dsts[ind << 2 | 3] * (255 - a) >> 8) + a;
			}
		out.setPixels(0, 0, w, h, dsts);
	}

}
