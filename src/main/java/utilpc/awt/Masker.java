package utilpc.awt;

import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class Masker implements Composite, CompositeContext {
    private final int opa;

    public Masker(int opa) {
        this.opa = opa;
    }

    @Override
    public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
        return this;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void compose(Raster src, Raster dst, WritableRaster out) {
        int w = src.getWidth();
        int h = src.getHeight();
        int[] srcs = src.getPixels(0, 0, w, h, new int[w * h * 4]);
        int[] dsts = dst.getPixels(0, 0, w, h, new int[w * h * 3]);
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++) {
                int ind = i * h + j;
                float a = (srcs[ind << 2 | 3]) / 255f * opa / 255f;
                for (int k = 0; k < 3; k++) {
                    dsts[ind * 3 + k] = (int) (dsts[ind * 3 + k] * (1f - a));
                }
            }
        out.setPixels(0, 0, w, h, dsts);
    }
}
