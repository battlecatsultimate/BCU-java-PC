package page.support;

import common.system.fake.FakeGraphics;
import utilpc.awt.FG2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorPicker extends Canvas {
    public enum MODE {
        HUE,
        SATURATION,
        BRIGHTNESS,
        RED,
        GREEN,
        BLUE
    }

    private final float[] hsb = new float[3];
    private final int[] rgb = new int[3];
    private MODE mode = MODE.HUE;

    private final BufferedImage colorField = new BufferedImage(360, 360, BufferedImage.TYPE_INT_ARGB_PRE);

    public void setRgb(int r, int g, int b) {
        rgb[0] = r;
        rgb[1] = g;
        rgb[2] = b;

        Color.RGBtoHSB(r, g, b, hsb);
    }

    public void setHsv(float h, float s, float b) {
        hsb[0] = h;
        hsb[1] = s;
        hsb[2] = b;

        int c = Color.HSBtoRGB(h, s, b);

        rgb[2] = c & 0xFF;
        rgb[1] = (c >> 8) & 0xFF;
        rgb[0] = (c >> 16) & 0xFF;
    }

    public void setHex(int hex) {
        rgb[2] = hex & 0xFF;
        rgb[1] = (hex >> 8) & 0xFF;
        rgb[0] = (hex >> 16) & 0xFF;

        Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
    }

    public void setMode(MODE mode) {
        this.mode = mode;
    }

    @Override
    public void paint(Graphics gra) {
        FakeGraphics g = new FG2D(gra);


    }

    public void updateField() {
        switch (mode) {
            case HUE:
                //x-axis = S, y-axis = B
                for(int x = 0; x < colorField.getWidth(); x++) {
                    for(int y = 0; y < colorField.getHeight(); y++) {
                        int c = Color.HSBtoRGB(hsb[0], x / 360f, y / 360f);

                        colorField.setRGB(x, y, c);
                    }
                }

                break;
            case SATURATION:
                //x-axis = H, y-axis = B
                for(int x = 0; x < colorField.getWidth(); x++) {
                    for(int y = 0; y < colorField.getHeight(); y++) {
                        int c = Color.HSBtoRGB(x, hsb[1], y / 360f);

                        colorField.setRGB(x, y, c);
                    }
                }

                break;
            case BRIGHTNESS:
                //x-axis = H, y-axis = S
                for(int x = 0; x < colorField.getWidth(); x++) {
                    for(int y = 0; y < colorField.getHeight(); y++) {
                        int c = Color.HSBtoRGB(x, y / 360f, hsb[2]);

                        colorField.setRGB(x, y, c);
                    }
                }

                break;
            case RED:
                //x-axis = G, y-axis = B
                for(int x = 0; x < colorField.getWidth(); x++) {
                    for(int y = 0; y < colorField.getHeight(); y++) {
                        int c = rgb[0];

                        c = (c << 8) + (int) (x * 255f / 360f);
                        c = (c << 8) + (int) (y * 255f / 360f);

                        colorField.setRGB(x, y, c);
                    }
                }

                break;
            case GREEN:
                //x-axis = R, y-axis = B
                for(int x = 0; x < colorField.getWidth(); x++) {
                    for(int y = 0; y < colorField.getHeight(); y++) {
                        int c = (int) (x * 255f / 360f);

                        c = (c << 8) + rgb[1];
                        c = (c << 8) + (int) (y * 255f / 360f);

                        colorField.setRGB(x, y, c);
                    }
                }

                break;
            case BLUE:
                //x-axis = R, y-axis = G
                for(int x = 0; x < colorField.getWidth(); x++) {
                    for(int y = 0; y < colorField.getHeight(); y++) {
                        int c = (int) (x * 255f / 360f);

                        c = (c << 8) + (int) (y * 255f / 360f);
                        c = (c << 8) + rgb[2];

                        colorField.setRGB(x, y, c);
                    }
                }

                break;
        }
    }
}
