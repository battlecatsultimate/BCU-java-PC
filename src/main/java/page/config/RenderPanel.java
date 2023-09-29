package page.config;

import common.CommonStatic;
import common.util.ImgCore;
import page.JBTN;
import page.JL;
import page.MainLocale;
import page.Page;

import javax.swing.*;

public class RenderPanel extends Page implements ConfigPage.ConfigPanel {

    private static class RenderHint extends Page {

        private static final long serialVersionUID = 1L;

        private final int hintId;
        private final JBTN left = new JBTN("<");
        private final JBTN right = new JBTN(">");
        private final JL mode = new JL();

        protected RenderHint(Page p, int hintId) {
            super(p);
            this.hintId = hintId;

            ini();
        }

        @Override
        protected void resized(int x, int y) {
            set(left, x, y, 0, 0, 100, 50);
            set(mode, x, y, 100, 0, 300, 50);
            set(right, x, y, 400, 0, 100, 50);
        }

        private void ini() {
            add(left);
            add(mode);
            add(right);
            left.setHorizontalAlignment(SwingConstants.CENTER);
            right.setHorizontalAlignment(SwingConstants.CENTER);
            mode.setHorizontalAlignment(SwingConstants.CENTER);
            reset();
            addListeners();
        }

        private void reset() {
            mode.setText(0, MainLocale.getLoc(0, ImgCore.NAME[hintId])
                    + ": "
                    + MainLocale.getLoc(0, ImgCore.VAL[CommonStatic.getConfig().ints[hintId]]));
            left.setEnabled(CommonStatic.getConfig().ints[hintId] > 0);
            right.setEnabled(CommonStatic.getConfig().ints[hintId] < 2);
        }

        private void addListeners() {
            left.addActionListener(x -> {
                CommonStatic.getConfig().ints[hintId]--;
                reset();
            });

            right.addActionListener(x -> {
                CommonStatic.getConfig().ints[hintId]++;
                reset();
            });
        }

        @Override
        protected JButton getBackButton() {
            return null;
        }
    }

    private static final long serialVersionUID = 1L;
    private final RenderHint[] hints = new RenderHint[4];
    private final JL jlmin = new JL(MainLocale.PAGE, "opamin");
    private final JL jlmax = new JL(MainLocale.PAGE, "opamax");

    protected RenderPanel(Page p) {
        super(p);

        ini();
    }

    private void ini() {
        for (int i = 0; i < hints.length; i++)
            add(hints[i] = new RenderHint(this, i));
    }

    @Override
    protected void resized(int x, int y) {
        for (int i = 0; i < hints.length; i++)
            set(hints[i], x, y, 0, i * 50, 500, 50);
    }

    @Override
    protected JButton getBackButton() {
        return null;
    }

    @Override
    public int getH() {
        return hints.length * 50;
    }
}
