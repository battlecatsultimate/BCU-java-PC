package utilpc.awt;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class Converter implements Composite, CompositeContext {

	private final int mode;

	public Converter(int t) {
		mode = t;
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
		int w = dst.getWidth();
		int h = dst.getHeight();
		int[] dsts = dst.getPixels(0, 0, w, h, new int[w * h * 3]);
		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++) {
				int ind = i * h + j;
				if (mode == 0) {
					for (int k = 0; k < 3; k++)
						dsts[ind * 3 + k] = 255 - dsts[ind * 3 + k];
				}
			}
		out.setPixels(0, 0, w, h, dsts);
	}

}
