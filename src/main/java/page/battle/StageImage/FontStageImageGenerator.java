package page.battle.StageImage;

import main.MainBCU;
import utilpc.awt.FG2D;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Used for fonts that are not writable by StageImageGenerator due to lacking characters
 */
public class FontStageImageGenerator implements ImageGenerator {
    private static final Font font = new Font("sans-serif", Font.BOLD, 102);
    private static final float strokeWidth = 12f;

    private BufferedImage generateBufferedImage(String message) {
        if(valid(message)) {
            AffineTransform affine = new AffineTransform();

            FontRenderContext frc = new FontRenderContext(affine, true, false);

            double w = generateWidth(message, frc);
            double[] h = generateHeight(message, frc, affine);

            BufferedImage img = new BufferedImage((int) (w + strokeWidth * 2 + xGap * 2), (int) (h[0] + h[1] + strokeWidth * 2 + yGap * 2), BufferedImage.TYPE_INT_ARGB_PRE);

            FG2D g = new FG2D(img.getGraphics());

            g.setRenderingHint(3, 2);
            g.enableAntialiasing();

            double pad = 0.0;

            for(int i = 0; i < message.length(); i++) {
                String str = Character.toString(message.charAt(i));

                if(str.equals(" ")) {
                    pad += space;
                    continue;
                }

                Shape outline = font.createGlyphVector(frc, str).getGlyphOutline(0);

                double[] offset = decideOffset(pad, h[0] + h[1], h[1]);
                double left = getLeftPoint(outline.getPathIterator(affine));

                offset[0] -= left - strokeWidth - xGap;
                offset[1] += strokeWidth;

                Path2D path = generatePath2D(offset, outline.getPathIterator(affine));

                g.drawFontOutline(path, strokeWidth * 2);

                pad += generateLetterWidth(str, frc) + 4;
            }

            pad = 0.0;

            for(int i = 0; i < message.length(); i++) {
                String str = Character.toString(message.charAt(i));

                if(str.equals(" ")) {
                    pad += space;
                    continue;
                }

                Shape outline = font.createGlyphVector(frc, str).getGlyphOutline(0);

                double[] offset = decideOffset(pad, h[0] + h[1], h[1]);
                double left = getLeftPoint(outline.getPathIterator(affine));

                offset[0] -= left - strokeWidth - xGap;
                offset[1] += strokeWidth + 1;

                Path2D path = generatePath2D(offset, outline.getPathIterator(affine));

                g.setGradient(0, 0, 0, (int) offset[1], new Color(255, 245, 0), new Color(236, 156, 0));
                g.fillPath2D(path);

                pad += generateLetterWidth(str, frc) + 4;
            }

            return img;
        } else {
            System.out.println(message + " contains invalid characters: " + getInvalids(message));
            return null;
        }
    }

    @Override
    public BufferedImage generateRealImage(String message) {
        BufferedImage img = generateBufferedImage(message);

        if(img == null)
            return null;

        BufferedImage real = new BufferedImage(256, 64, BufferedImage.TYPE_INT_ARGB_PRE);
        FG2D g = new FG2D(real.getGraphics());

        g.setRenderingHint(3, 1);
        g.enableAntialiasing();
        double ratio = 42.0 / img.getHeight();

        BufferedImage scaled = new BufferedImage((int) (img.getWidth() * ratio), 42, BufferedImage.TYPE_INT_ARGB_PRE);
        FG2D sg = new FG2D(scaled.getGraphics());

        sg.setRenderingHint(3, 1);
        sg.enableAntialiasing();

        sg.drawImage(MainBCU.builder.build(img), 0, 0, scaled.getWidth(), scaled.getHeight());

        if(scaled.getWidth() > 228)
            ratio = 228.0 / scaled.getWidth();
        else
            ratio = 1.0;

        g.drawImage(MainBCU.builder.build(scaled), 3, 2, scaled.getWidth() * ratio, scaled.getHeight());
        return real;
    }

    public ArrayList<String> getInvalids(String message) {
        ArrayList<String> res = new ArrayList<>();

        for(int i = 0; i < message.length(); i++) {
            String str = Character.toString(message.charAt(i));

            if(str.equals(" "))
                continue;

            if(!font.canDisplay(message.charAt(i)))
                res.add(str);
        }

        return res;
    }

    private boolean valid(String message) {
        for(int i = 0; i < message.length(); i++) {
            if(message.charAt(i) == ' ')
                continue;

            if(!font.canDisplay(message.charAt(i)))
                return false;
        }

        return true;
    }

    private double[] getAscendDescend(PathIterator path) {
        double[] d = new double[6];

        double descend = 0;
        double ascend = 0;

        while(!path.isDone()) {
            path.currentSegment(d);

            descend = Math.min(d[1] * -1.0, descend);
            ascend = Math.max(d[1] * -1.0, ascend);

            if(!path.isDone())
                path.next();
        }

        return new double[] {ascend, descend};
    }

    private double generateWidth(String message, FontRenderContext frc) {
        double w = 0.0;

        for(int i = 0; i < message.length(); i++) {
            String str = Character.toString(message.charAt(i));

            if(str.equals(" ")) {
                w += space;
                continue;
            }

            GlyphVector glyph = font.createGlyphVector(frc, str);

            w += glyph.getVisualBounds().getWidth() + 4;
        }

        return w - 4;
    }

    private double[] generateHeight(String message, FontRenderContext frc, AffineTransform aff) {
        GlyphVector glyph = font.createGlyphVector(frc, message);

        double[] res = new double[2];

        for(int i = 0; i < message.length(); i++) {
            Shape outline = glyph.getGlyphOutline(i);

            PathIterator path = outline.getPathIterator(aff);

            double[] result = getAscendDescend(path);

            res[0] = Math.max(res[0], result[0]);
            res[1] = Math.min(res[1], result[1]);
        }

        res[1] *= -1.0;

        return res;
    }

    private double[] decideOffset(double padding, double h, double base) {
        return new double[] {padding, h - base};
    }

    private double generateLetterWidth(String str, FontRenderContext frc) {
        GlyphVector glyph = font.createGlyphVector(frc, str);

        return glyph.getVisualBounds().getWidth();
    }

    private double getLeftPoint(PathIterator path) {
        double res = Double.MAX_VALUE;

        double[] d = new double[6];

        while(!path.isDone()) {
            path.currentSegment(d);

            res = Math.min(res, d[0]);

            if(!path.isDone())
                path.next();
        }

        return res;
    }

    private Path2D generatePath2D(double[] offset, PathIterator path) {
        Path2D path2D = new Path2D.Double();

        double[] d = new double[6];

        while(!path.isDone()) {
            switch (path.currentSegment(d)) {
                case PathIterator.SEG_MOVETO:
                    path2D.moveTo(d[0] + offset[0], d[1] + offset[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    path2D.lineTo(d[0] + offset[0], d[1] + offset[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    path2D.quadTo(d[0] + offset[0], d[1] + offset[1], d[2] + offset[0], d[3] + offset[1]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    path2D.curveTo(d[0] + offset[0], d[1] + offset[1], d[2] + offset[0], d[3] + offset[1], d[4] + offset[0], d[5] + offset[1]);
                    break;
                case PathIterator.SEG_CLOSE:
                    path2D.closePath();
                    break;
            }

            if(!path.isDone())
                path.next();
        }

        return path2D;
    }
}