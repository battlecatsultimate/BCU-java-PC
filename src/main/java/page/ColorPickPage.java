package page;

import common.CommonStatic;
import page.support.ColorPicker;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Locale;

public class ColorPickPage extends Page {

    public final ColorPicker picker = new ColorPicker(this);

    private static final long serialVersionUID = 1L;
    private static final String[] ids = {"hue", "sat", "bri", "red", "gre", "blu", "hex"};

    private final JRadioButton[] radios = new JRadioButton[6];
    private final ButtonGroup group = new ButtonGroup();
    private final JL[] labels = new JL[7];
    private final JTF[] texts = new JTF[7];

    public ColorPickPage(Page p) {
        super(p);

        ini();

        listeners();
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);

        set(picker, x, y, 0, 0, 850, 490);

        for(int i = 0; i < 7; i++) {
            if(i < 6) {
                set(radios[i], x, y, 850, 70 * i, 50, 70);
            }

            if(i != 6) {
                set(labels[i], x, y, 900, 70 * i, 150, 70);
                set(texts[i], x, y, 1050, 70 * i, 150, 70);
            } else {
                set(labels[i], x, y, 900, 70 * i, 100, 70);
                set(texts[i], x, y, 1000, 70 * i, 200, 70);
            }
        }
    }

    private void ini() {
        add(picker);

        for(int i = 0; i < 7; i++) {
            if(i < 6) {
                radios[i] = new JRadioButton();

                radios[i].setHorizontalAlignment(SwingConstants.CENTER);

                group.add(radios[i]);

                add(radios[i]);
            }

            labels[i] = new JL(MainLocale.PAGE, ids[i]);
            texts[i] = new JTF();

            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            texts[i].setHorizontalAlignment(SwingConstants.CENTER);

            add(labels[i]);
            add(texts[i]);
        }

        picker.invalidate();

        updateTexts();

        radios[0].setSelected(true);
    }

    private void listeners() {
        picker.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                picker.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                picker.mouseReleased();
            }
        });

        picker.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                picker.mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        for(int i = 0; i < 7; i ++) {
            final int fi = i;
            if(i < 6) {
                radios[i].addActionListener(l -> {
                    if(radios[fi].isSelected()) {
                        ColorPicker.MODE mode;

                        switch (fi) {
                            case 0:
                                mode = ColorPicker.MODE.HUE;
                                break;
                            case 1:
                                mode = ColorPicker.MODE.SATURATION;
                                break;
                            case 2:
                                mode = ColorPicker.MODE.BRIGHTNESS;
                                break;
                            case 3:
                                mode = ColorPicker.MODE.RED;
                                break;
                            case 4:
                                mode = ColorPicker.MODE.GREEN;
                                break;
                            default:
                                mode = ColorPicker.MODE.BLUE;
                                break;
                        }

                        picker.setMode(mode);
                    }
                });
            }

            texts[i].setLnr(e -> {
                switch (fi) {
                    case 0:
                        float h = Math.min(360, Math.max(0, CommonStatic.parseIntN(texts[fi].getText()))) / 360f;

                        picker.hsb[0] = h;

                        picker.updateRgb();
                        break;
                    case 1:
                        float s = Math.min(100, Math.max(0, CommonStatic.parseIntN(texts[fi].getText()))) / 100f;

                        picker.hsb[1] = s;

                        picker.updateRgb();
                        break;
                    case 2:
                        float b = Math.min(100, Math.max(0, CommonStatic.parseIntN(texts[fi].getText()))) / 100f;

                        picker.hsb[2] = b;

                        picker.updateRgb();
                        break;
                    case 3:
                        int r = Math.min(255, Math.max(0, CommonStatic.parseIntN(texts[fi].getText())));

                        picker.rgb[0] = r;

                        picker.updateHsb();
                        break;
                    case 4:
                        int g = Math.min(255, Math.max(0, CommonStatic.parseIntN(texts[fi].getText())));

                        picker.rgb[1] = g;

                        picker.updateHsb();
                        break;
                    case 5:
                        int bl = Math.min(255, Math.max(0, CommonStatic.parseIntN(texts[fi].getText())));

                        picker.rgb[2] = bl;

                        picker.updateHsb();
                        break;
                    case 6:
                        String hex = texts[fi].getText().toUpperCase(Locale.ENGLISH).replaceAll("^#", "");

                        if(hex.length() <= 6) {
                            for(int j = 0; j < hex.length(); j++) {
                                int c = hex.charAt(j);

                                if((c < 48 || c >= 58) && (c < 65 || c >= 71)) {
                                    updateTexts();

                                    return;
                                }
                            }

                            int c = Integer.parseInt(hex, 16);

                            picker.setHex(c);

                            picker.updateHsb();
                        }

                        break;
                }

                picker.updateData();
            });
        }
    }

    @Override
    public void callBack(Object newParam) {
        if(newParam == null) {
            getFront().callBack(picker);
        } else if(newParam instanceof ColorPicker) {
            updateTexts();
        }
    }

    private void updateTexts() {
        float[] hsb = picker.hsb;
        int[] rgb = picker.rgb;

        for(int i = 0; i < texts.length; i++) {
            switch (i) {
                case 0:
                    texts[i].setText(Math.round(hsb[0] * 360)+"°");
                    break;
                case 1:
                    texts[i].setText(Math.round(hsb[1] * 100)+"%");
                    break;
                case 2:
                    texts[i].setText(Math.round(hsb[2] * 100)+"%");
                    break;
                case 3:
                    texts[i].setText(rgb[0]+"");
                    break;
                case 4:
                    texts[i].setText(rgb[1]+"");
                    break;
                case 5:
                    texts[i].setText(rgb[2]+"");
                    break;
                case 6:
                    int c = rgb[0];

                    c = (c << 8) + rgb[1];
                    c = (c << 8) + rgb[2];

                    StringBuilder hex = new StringBuilder(Integer.toHexString(c).toUpperCase(Locale.ENGLISH));

                    if(hex.length() == 4)
                        hex.insert(0, "00");
                    else if(hex.length() == 2)
                        hex.insert(0, "0000");
                    else if(hex.length() == 1)
                        hex.insert(0, "00000");

                    texts[i].setText("#" + hex);
                    break;
            }
        }
    }
}
