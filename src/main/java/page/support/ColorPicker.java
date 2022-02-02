package page.support;

import common.system.fake.FakeImage;
import page.ColorPickPage;
import utilpc.awt.FG2D;
import utilpc.awt.FIBI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ColorPicker extends JPanel {
    public enum MODE {
        HUE,
        SATURATION,
        BRIGHTNESS,
        RED,
        GREEN,
        BLUE
    }

    private enum DRAGMODE {
        FIELD,
        BAR
    }

    private final ColorPickPage page;

    public final float[] hsb = {0f, 1f, 1f};
    public final int[] rgb = {255, 0, 0};

    private final BufferedImage colorField = new BufferedImage(360, 360, BufferedImage.TYPE_INT_ARGB_PRE);
    private final FakeImage colorImage = FIBI.build(colorField);

    private final BufferedImage colorBar = new BufferedImage(36, 360, BufferedImage.TYPE_INT_ARGB_PRE);
    private final FakeImage barImage = FIBI.build(colorBar);

    private int circleX = 0;
    private int circleY = 0;

    private int barPos = 0;

    private DRAGMODE dragmode = null;
    private MODE mode = MODE.HUE;

    public ColorPicker(ColorPickPage page) {
        this.page = page;

        updateBar();
        updateField();

        changeCirclePos();
        changeBarPos();
    }

    public void updateRgb() {
        int c = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]) & 0xFFFFFF;

        rgb[2] = c & 0xFF;
        rgb[1] = (c >> 8) & 0xFF;
        rgb[0] = (c >> 16) & 0xFF;

        page.callBack(this);
    }

    public void updateHsb() {
        Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);

        page.callBack(this);
    }

    public void setHex(int hex) {
        rgb[2] = hex & 0xFF;
        rgb[1] = (hex >> 8) & 0xFF;
        rgb[0] = (hex >> 16) & 0xFF;

        Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
    }

    public void setMode(MODE mode) {
        this.mode = mode;

        updateField();
        updateBar();

        changeBarPos();
        changeCirclePos();
    }

    @Override
    protected void paintComponent(Graphics gra) {
        super.paintComponent(gra);

        if(gra instanceof Graphics2D) {
            ((Graphics2D) gra).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        FG2D g = new FG2D(gra);

        g.setRenderingHint(3, 2);

        int w = getWidth();
        int h = getHeight();

        int ihw = (int) (h * 0.9);
        int iGap = (int) (h * 0.05);
        int gap = (int) (w * 0.075);
        int barH = (int) (ihw / 10.0);
        int triangleSize = (int) (gap / (Math.sqrt(3) * 4.0));
        int outerCircle = (int) (ihw * 0.05 / 2.0);
        int innerCircle = (int) (ihw * 0.0125 / 2.0);

        int cx = (int) (circleX * ihw / 360.0);
        int cy = (int) (circleY * ihw / 360.0);
        int bp = (int) (barPos * ihw / 360.0);

        g.drawImage(colorImage, iGap, iGap, ihw, ihw);

        g.drawImage(barImage, iGap + ihw + gap, iGap, barH, ihw);

        g.setColor(255 - rgb[0], 255 - rgb[1], 255 - rgb[2]);

        g.drawOval(iGap + cx - outerCircle, iGap + cy - outerCircle, outerCircle * 2, outerCircle * 2);
        g.fillOval(iGap + cx - innerCircle, iGap + cy - innerCircle, innerCircle * 2, innerCircle * 2);

        g.drawLine(iGap + gap + ihw, iGap + bp, iGap + gap + ihw + barH, iGap + bp);

        Polygon leftTriangle = new Polygon();

        leftTriangle.addPoint(iGap + gap + ihw, iGap + bp);
        leftTriangle.addPoint((int) (iGap + ihw + gap * 0.875), iGap + (int) (bp - triangleSize / 2.0));
        leftTriangle.addPoint((int) (iGap + ihw + gap * 0.875), iGap + (int) (bp + triangleSize / 2.0));

        g.fillPath(leftTriangle);

        Polygon rightTriangle = new Polygon();

        rightTriangle.addPoint(iGap + gap + ihw + barH, iGap + bp);
        rightTriangle.addPoint((int) (iGap + ihw + barH + gap * 1.125), iGap + (int) (bp - triangleSize / 2.0));
        rightTriangle.addPoint((int) (iGap + ihw + barH + gap * 1.125), iGap + (int) (bp + triangleSize / 2.0));

        g.fillPath(rightTriangle);

        g.setColor(rgb[0], rgb[1], rgb[2]);

        g.fillRect(iGap + ihw + gap + barH + gap, iGap, barH, iGap * 2);
    }

    public void updateField() {
        switch (mode) {
            case HUE:
                //x-axis = S, y-axis = B
                for(int x = 0; x < colorField.getWidth(); x++) {
                    for(int y = 0; y < colorField.getHeight(); y++) {
                        int c = Color.HSBtoRGB(hsb[0], x / 360f, (360f - y) / 360f);

                        colorField.setRGB(x, y, c);
                    }
                }

                break;
            case SATURATION:
                //x-axis = H, y-axis = B
                for(int x = 0; x < colorField.getWidth(); x++) {
                    for(int y = 0; y < colorField.getHeight(); y++) {
                        int c = Color.HSBtoRGB(x / 360f, hsb[1], (360f - y) / 360f);

                        colorField.setRGB(x, y, c);
                    }
                }

                break;
            case BRIGHTNESS:
                //x-axis = H, y-axis = S
                for(int x = 0; x < colorField.getWidth(); x++) {
                    for(int y = 0; y < colorField.getHeight(); y++) {
                        int c = Color.HSBtoRGB(x / 360f, (360f - y) / 360f, hsb[2]);

                        colorField.setRGB(x, y, c);
                    }
                }

                break;
            case RED:
                //x-axis = G, y-axis = B
                for(int x = 0; x < colorField.getWidth(); x++) {
                    for(int y = 0; y < colorField.getHeight(); y++) {
                        int c = 0xFF;

                        c = (c << 8) + rgb[0];
                        c = (c << 8) + (int) (x * 255 / 360.0);
                        c = (c << 8) + (int) ((360 - y) * 255 / 360.0);

                        colorField.setRGB(x, y, c);
                    }
                }

                break;
            case GREEN:
                //x-axis = R, y-axis = B
                for(int x = 0; x < colorField.getWidth(); x++) {
                    for(int y = 0; y < colorField.getHeight(); y++) {
                        int c = 0xFF;

                        c = (c << 8) + (int) (x * 255 / 360.0);
                        c = (c << 8) + rgb[1];
                        c = (c << 8) + (int) ((360 - y) * 255 / 360.0);

                        colorField.setRGB(x, y, c);
                    }
                }

                break;
            case BLUE:
                //x-axis = R, y-axis = G
                for(int x = 0; x < colorField.getWidth(); x++) {
                    for(int y = 0; y < colorField.getHeight(); y++) {
                        int c = 0xFF;

                        c = (c << 8) + (int) (x * 255 / 360.0);
                        c = (c << 8) + (int) ((360 - y) * 255 / 360.0);
                        c = (c << 8) + rgb[2];

                        colorField.setRGB(x, y, c);
                    }
                }

                break;
        }
    }

    public void updateBar() {
        switch (mode) {
            case HUE:
                for(int y = 0; y < colorBar.getHeight(); y++) {
                    int c = Color.HSBtoRGB(1f - y / 360f, 1f, 1f);

                    for(int x = 0; x < 36; x++) {
                        colorBar.setRGB(x, y, c);
                    }
                }

                break;
            case SATURATION:
                for(int y = 0; y < colorBar.getHeight(); y++) {
                    int c = Color.HSBtoRGB(hsb[0], 1f - y / 360f, hsb[2]);

                    for(int x = 0; x < 36; x++) {
                        colorBar.setRGB(x, y, c);
                    }
                }

                break;
            case BRIGHTNESS:
                for(int y = 0; y < colorBar.getHeight(); y++) {
                    int c = Color.HSBtoRGB(hsb[0], hsb[1], 1f - y / 360f);

                    for(int x = 0; x < 36; x++) {
                        colorBar.setRGB(x, y, c);
                    }
                }

                break;
            case RED:
                for(int y = 0; y < colorBar.getHeight(); y++) {
                    int c = 0xFF;

                    c = (c << 8) + (int) ((360 - y) * 255/ 360.0);
                    c = (c << 8) + rgb[1];
                    c = (c << 8) + rgb[2];

                    for(int x = 0; x < 36; x++) {
                        colorBar.setRGB(x, y, c);
                    }
                }

                break;
            case GREEN:
                for(int y = 0; y < colorBar.getHeight(); y++) {
                    int c = 0xFF;

                    c = (c << 8) + rgb[0];
                    c = (c << 8) + (int) ((360 - y) * 255/ 360.0);
                    c = (c << 8) + rgb[2];

                    for(int x = 0; x < 36; x++) {
                        colorBar.setRGB(x, y, c);
                    }
                }

                break;
            case BLUE:
                for(int y = 0; y < colorBar.getHeight(); y++) {
                    int c = 0xFF;

                    c = (c << 8) + rgb[0];
                    c = (c << 8) + rgb[1];
                    c = (c << 8) + (int) ((360 - y) * 255/ 360.0);

                    for(int x = 0; x < 36; x++) {
                        colorBar.setRGB(x, y, c);
                    }
                }

                break;
        }
    }

    public void changeBarPos() {
        switch (mode) {
            case HUE:
                barPos = (int) ((1f - hsb[0]) * 360.0);
                break;
            case SATURATION:
                barPos = (int) ((1f - hsb[1]) * 360.0);
                break;
            case BRIGHTNESS:
                barPos = (int) ((1f - hsb[2]) * 360.0);
                break;
            case RED:
                barPos = (int) ((255 - rgb[0]) * 360 / 255.0);
                break;
            case GREEN:
                barPos = (int) ((255 - rgb[1]) * 360 / 255.0);
                break;
            case BLUE:
                barPos = (int) ((255 - rgb[2]) * 360 / 255.0);
                break;
        }
    }

    public void changeCirclePos() {
        switch (mode) {
            case HUE:
                circleX = (int) (hsb[1] * 360);
                circleY = (int) ((1 - hsb[2]) * 360);
                break;
            case SATURATION:
                circleX = (int) (hsb[0] * 360);
                circleY = (int) ((1- hsb[2]) * 360);
                break;
            case BRIGHTNESS:
                circleX = (int) (hsb[0] * 360);
                circleY = (int) ((1 - hsb[1]) * 360);
                break;
            case RED:
                circleX = (int) (rgb[1] * 360 / 255.0);
                circleY = (int) ((255 - rgb[2]) * 360 / 255.0);
                break;
            case GREEN:
                circleX = (int) (rgb[0] * 360 / 255.0);
                circleY = (int) ((255 - rgb[2]) * 360 / 255.0);
                break;
            case BLUE:
                circleX = (int) (rgb[0] * 360 / 255.0);
                circleY = (int) ((255 - rgb[1]) * 360 / 255.0);
                break;
        }
    }

    public void mousePressed(MouseEvent e) {
        double iGap = getHeight() * 0.05;
        double ihw = getHeight() * 0.9;
        double gap = getWidth() * 0.075;
        double barH = ihw / 10.0;

        if (e.getPoint().x >= iGap && e.getPoint().x <= iGap + ihw && e.getPoint().y >= iGap && e.getPoint().y <= iGap + ihw) {
            dragmode = DRAGMODE.FIELD;

            double x = e.getPoint().x - iGap;
            double y = e.getPoint().y - iGap;

            long round = Math.round((ihw - y) * 255 / ihw);

            switch (mode) {
                case HUE:
                    hsb[1] = (float) (x / ihw);
                    hsb[2] = (float) ((ihw - y) / ihw);

                    updateRgb();
                    break;
                case SATURATION:
                    hsb[0] = (float) (x / ihw);
                    hsb[2] = (float) ((ihw - y) / ihw);

                    updateRgb();
                    break;
                case BRIGHTNESS:
                    hsb[0] = (float) (x / ihw);
                    hsb[1] = (float) ((ihw - y) / ihw);

                    updateRgb();
                    break;
                case RED:
                    rgb[1] = (int) (Math.round(x * 255 / ihw));
                    rgb[2] = (int) round;

                    updateHsb();
                    break;
                case GREEN:
                    rgb[0] = (int) (Math.round(x * 255 / ihw));
                    rgb[2] = (int) round;

                    updateHsb();
                    break;
                case BLUE:
                    rgb[0] = (int) (Math.round(x * 255 / ihw));
                    rgb[1] = (int) round;

                    updateHsb();
                    break;
            }

            changeCirclePos();

            if(mode != MODE.HUE) {
                updateBar();
            }

            invalidate();
        } else if(e.getPoint().x >= iGap + ihw + gap && e.getPoint().x <= iGap + ihw + gap + barH && e.getPoint().y >= iGap && e.getPoint().y <= iGap + ihw) {
            dragmode = DRAGMODE.BAR;

            double y = e.getPoint().y - iGap;

            long round = Math.round((ihw - y) * 255 / ihw);

            switch (mode) {
                case HUE:
                    hsb[0] = (float) (1f - y / ihw);

                    updateRgb();
                    break;
                case SATURATION:
                    hsb[1] = (float) (1f - y / ihw);

                    updateRgb();
                    break;
                case BRIGHTNESS:
                    hsb[2] = (float) (1f - y / ihw);

                    updateRgb();
                    break;
                case RED:
                    rgb[0] = (int) round;

                    updateHsb();
                    break;
                case GREEN:
                    rgb[1] = (int) round;

                    updateHsb();
                    break;
                case BLUE:
                    rgb[2] = (int) round;

                    updateHsb();
                    break;
            }

            changeBarPos();

            updateField();

            invalidate();
        } else {
            dragmode = null;
        }

        invalidate();
    }

    public void mouseDragged(MouseEvent e) {
        double iGap = getHeight() * 0.05;
        double ihw = getHeight() * 0.9;

        if(dragmode == DRAGMODE.FIELD) {
            double x = Math.min(iGap + ihw, Math.max(e.getPoint().x, iGap)) - iGap;
            double y = Math.min(iGap + ihw, Math.max(e.getPoint().y, iGap)) - iGap;

            long round = Math.round((ihw - y) * 255 / ihw);

            switch (mode) {
                case HUE:
                    hsb[1] = (float) (x / ihw);
                    hsb[2] = (float) ((ihw - y) / ihw);

                    updateRgb();
                    break;
                case SATURATION:
                    hsb[0] = (float) (x / ihw);
                    hsb[2] = (float) ((ihw - y) / ihw);

                    updateRgb();
                    break;
                case BRIGHTNESS:
                    hsb[0] = (float) (x / ihw);
                    hsb[1] = (float) ((ihw - y) / ihw);

                    updateRgb();
                    break;
                case RED:
                    rgb[1] = (int) (Math.round(x * 255 / ihw));
                    rgb[2] = (int) round;

                    updateHsb();
                    break;
                case GREEN:
                    rgb[0] = (int) (Math.round(x * 255 / ihw));
                    rgb[2] = (int) round;

                    updateHsb();
                    break;
                case BLUE:
                    rgb[0] = (int) (Math.round(x * 255 / ihw));
                    rgb[1] = (int) round;

                    updateHsb();
                    break;
            }

            changeCirclePos();

            if(mode != MODE.HUE) {
                updateBar();
            }

            invalidate();
        } else if(dragmode == DRAGMODE.BAR) {
            double y = Math.min(iGap + ihw, Math.max(iGap, e.getPoint().y)) - iGap;

            switch (mode) {
                case HUE:
                    hsb[0] = (float) (1f - y / ihw);

                    updateRgb();
                    break;
                case SATURATION:
                    hsb[1] = (float) (1f - y / ihw);

                    updateRgb();
                    break;
                case BRIGHTNESS:
                    hsb[2] = (float) (1f - y / ihw);

                    updateRgb();
                    break;
                case RED:
                    rgb[0] = (int) ((ihw - y) * 255 / ihw);

                    updateHsb();
                    break;
                case GREEN:
                    rgb[1] = (int) ((ihw - y) * 255 / ihw);

                    updateHsb();
                    break;
                case BLUE:
                    rgb[2] = (int) ((ihw - y) * 255 / ihw);

                    updateHsb();
                    break;
            }

            changeBarPos();

            updateField();

            invalidate();
        }
    }

    public void mouseReleased() {
        dragmode = null;
    }

    public void updateData() {
        updateField();
        updateBar();

        changeBarPos();
        changeCirclePos();

        page.callBack(this);
    }
}
