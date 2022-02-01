package page;

import page.support.ColorPicker;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ColorPickPage extends Page {

    private static final long serialVersionUID = 1L;

    private final ColorPicker picker = new ColorPicker();

    protected ColorPickPage(Page p) {
        super(p);

        ini();

        listeners();
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);

        set(picker, x, y, 0, 0, 1300, 1000);
    }

    private void ini() {
        add(picker);

        picker.invalidate();
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
    }
}
