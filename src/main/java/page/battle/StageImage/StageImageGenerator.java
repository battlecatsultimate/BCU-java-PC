package page.battle.StageImage;

import common.CommonStatic;
import common.system.VImg;
import main.MainBCU;
import utilpc.awt.FG2D;
import common.system.fake.FakeImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StageImageGenerator implements ImageGenerator {
    private static final int[] white = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
            23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
            51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 63, 0};
    private static final String[] texts = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", ".", "'", "-", ":", "!", "&", "?", "=", "(", ")", "+", "#", "/", "\u266a", "\u2192", "\u00e9", "\"", ","};
    public static final String[] specials = {"-", ":", "!", "&", "?", "=", "(", ")", "+", "#", "/", "\u266a", "\u2192", "\u00e9"};
    private static final VImg[] images = CommonStatic.getBCAssets().battle[3];
    private static final int space = 30;

    public int generateWidth(String message) {
        int w = 0;

        for(int i = 0; i < message.length(); i++) {
            String c = Character.toString(message.charAt(i));

            if(c.equals(" ")) {
                w += space;
                continue;
            }

            int ind = indexOf(c);

            if(ind == -1) {
                System.out.println("Invalid index! : "+c);
                return -1;
            }

            ind = white[ind];

            if(ind >= images.length) {
                System.out.println("Too high index! : "+ind+" | "+images.length);
                return -1;
            }

            w += images[ind].getImg().getWidth() + 4;
        }

        return w-4;
    }

    public int[] generateHeight(String message) {
        int upHeight = 0;
        int downHeight = 0;

        for(int i = 0; i < message.length(); i++) {
            String c = Character.toString(message.charAt(i));

            if(c.equals(" "))
                continue;

            int ind = indexOf(c);

            if(ind == -1) {
                System.out.println("Invalid index! : "+c);
                return new int[] {-1, -1};
            }

            ind = white[ind];

            if(ind >= images.length) {
                System.out.println("Too high index! : "+ind+" | "+images.length);
                return new int[] {-1, -1};
            }

            FakeImage img = images[ind].getImg();
            if(c.equals("p") || c.equals("q") || c.equals("y") || c.equals("g") || c.equals("j")) {
                downHeight = Math.max(downHeight, 4);
                upHeight = Math.max(upHeight, img.getHeight()-4);
            } else if(c.equals(",")) {
                downHeight = 6;
                upHeight = Math.max(upHeight, img.getHeight()-4);
            } else if(c.equals("\"") || c.equals("'")) {
                upHeight = Math.max(upHeight, 70);
            } else if(c.equals("Q")) {
                downHeight = Math.max(downHeight, 4);
                upHeight = Math.max(upHeight, 70);
            } else if(spContains(c)) {
                upHeight = Math.max(upHeight, 35 + img.getHeight()/2);
            } else {
                upHeight = Math.max(upHeight, img.getHeight());
            }
        }

        return new int[] {upHeight, downHeight};
    }

    public int[] decideOffset(String c, FakeImage img, int pad, int height, int base) {
        if(c.equals("p") || c.equals("q") || c.equals("y") || c.equals("g") || c.equals("j")) {
            return new int[] {pad, height + 4 - img.getHeight() - base};
        } else if(c.equals(",")) {
            return new int[] {pad, height + 6 - img.getHeight() - base};
        } else if(c.equals("\"") || c.equals("'")) {
            return new int[] {pad, height - 70 - base};
        } else if(c.equals("Q")) {
            return new int[] {pad, height - img.getHeight() - base + 4};
        } else if(spContains(c)) {
            return new int[] {pad, height - base - 35 - img.getHeight()/2};
        } else {
            return new int[] {pad, height - base - img.getHeight()};
        }
    }

    private BufferedImage generateBufferedImage(String message) {
        ArrayList<int []> coord = new ArrayList<>();

        int w = generateWidth(message) + 18;

        int[] hs = generateHeight(message);

        int h = hs[0] + hs[1] + 18;

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D gr = (Graphics2D) img.getGraphics();

        FG2D g = new FG2D(gr);

        int pad = 9;

        for(int i = 0; i < message.length(); i++) {
            String c = Character.toString(message.charAt(i));

            if(c.equals(" ")) {
                pad += space;
                continue;
            }

            int ind = indexOf(c);

            if(ind == -1) {
                System.out.println("Invalid index! : "+c);
                return null;
            }

            ind = white[ind];

            if(ind+79 >= images.length) {
                System.out.println("Too high index! : "+ind+" | "+images.length);
                return null;
            }

            FakeImage image = images[ind].getImg();
            FakeImage shadow = images[ind+79].getImg();

            int[] offset = decideOffset(c, image, pad, h, hs[1] + 9);

            coord.add(offset);

            int[] shadowOffset = {offset[0] + image.getWidth()/2 - shadow.getWidth()/2, offset[1] + image.getHeight()/2 - shadow.getHeight()/2};

            g.drawImage(shadow, shadowOffset[0], shadowOffset[1]);

            pad += image.getWidth() + 4;
        }

        pad = 9;
        int index = 0;

        for(int i = 0; i < message.length(); i++) {
            String c = Character.toString(message.charAt(i));

            if(c.equals(" ")) {
                pad += 15;
                continue;
            }

            int ind = indexOf(c);

            ind = white[ind];

            FakeImage image = images[ind].getImg();

            g.drawImage(image, coord.get(index)[0], coord.get(index)[1]);

            index++;
        }

        return img;
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
        double ratio = 29.0 / img.getHeight();

        BufferedImage scaled = new BufferedImage((int) (img.getWidth() * ratio), 29, BufferedImage.TYPE_INT_ARGB_PRE);
        FG2D sg = new FG2D(scaled.getGraphics());

        sg.setRenderingHint(3, 1);
        sg.enableAntialiasing();

        sg.drawImage(MainBCU.builder.build(img), 0, 0, scaled.getWidth(), scaled.getHeight());

        if(scaled.getWidth() > 228)
            ratio = 228.0 / scaled.getWidth();
        else
            ratio = 1.0;

        g.drawImage(MainBCU.builder.build(scaled), 3, 10, scaled.getWidth() * ratio, scaled.getHeight());
        return real;
    }

    public boolean contains(int a) {
        for(int i : white)
            if(a == i)
                return true;

        return false;
    }

    public static boolean contains(String a) {
        for(String s : texts) {
            if(a.equals(s))
                return true;
        }

        return false;
    }

    public boolean spContains(String a) {
        for(String s : specials) {
            if(a.equals(s))
                return true;
        }

        return false;
    }

    public int indexOf(String c) {
        for(int i = 0; i < texts.length; i++) {
            if(texts[i].equals(c))
                return i;
        }

        return -1;
    }
}