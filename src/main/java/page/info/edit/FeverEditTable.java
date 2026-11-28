package page.info.edit;

import common.CommonStatic;
import common.util.stage.FeverSettings;
import common.util.stage.Stage;
import common.util.stage.StageLimit;
import page.*;

import javax.swing.JButton;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

//editor for fever settings
public class FeverEditTable extends Page {

    private static final long serialVersionUID = 1L;

    // highest values the fields take, so the numbers can't get big enough to overflow
    private static final int MAX_POINTS = 100000000, MAX_FRAMES = 1000000, MAX_PERCENT = 10000;
    // the tiers are shown in this many columns
    private static final int TIER_ROWS = 5;

    private final JTG jena = new JTG(MainLocale.INFO, "fev0");
    private final JL lmax = new JL(MainLocale.INFO, "fev1");
    private final JL ldur = new JL(MainLocale.INFO, "fev2");
    private final JL lmul = new JL(MainLocale.INFO, "fev3");
    private final JL lstart = new JL(MainLocale.INFO, "fev4");
    private final JL llen = new JL(MainLocale.INFO, "fev5");
    private final JL ltier = new JL(MainLocale.INFO, "fev6");
    private final JTF jmax = new JTF();
    private final JTF jdur = new JTF();
    private final JTF jmul = new JTF();
    private final JTF jstart = new JTF();
    private final JTF jlen = new JTF();
    private final JTF[] jcost = new JTF[FeverSettings.TIERS];
    private final JTF[] jperc = new JTF[FeverSettings.TIERS];

    private Stage stage;
    private FeverSettings fever;

    protected FeverEditTable(Page p) {
        super(p);
        ini();
    }

    @Override
    protected void resized(int x, int y) {
        int w = 1400 / 8;

        set(jena, x, y, 0, 0, w * 3, 50);
        set(lmax, x, y, 0, 50, w * 2, 50);
        set(jmax, x, y, w * 2, 50, w, 50);
        set(ldur, x, y, 0, 100, w * 2, 50);
        set(jdur, x, y, w * 2, 100, w, 50);
        set(lmul, x, y, 0, 150, w * 2, 50);
        set(jmul, x, y, w * 2, 150, w, 50);
        set(lstart, x, y, 0, 200, w * 2, 50);
        set(jstart, x, y, w * 2, 200, w, 50);
        set(llen, x, y, 0, 250, w * 2, 50);
        set(jlen, x, y, w * 2, 250, w, 50);
        set(ltier, x, y, w * 4, 0, w * 4, 50);

        for (int i = 0; i < FeverSettings.TIERS; i++) {
            int col = i / TIER_ROWS;
            int row = i % TIER_ROWS;

            set(jcost[i], x, y, w * (4 + col * 2), 50 + row * 50, w, 50);
            set(jperc[i], x, y, w * (5 + col * 2), 50 + row * 50, w, 50);
        }
    }

    @Override
    protected JButton getBackButton() {
        return null;
    }
    public int getPWidth() {
        return (int) (1400 / 7.5);
    }
    public int getPHeight() {
        return 350;
    }

    /**
     * @param st the stage that is edited, null if there is none
     */
    public void setData(Stage st) {
        stage = st;

        if (st == null) {
            fever = null;
            abler(false);
            return;
        }
        if (st.lim.stageLimit == null)
            st.lim.stageLimit = new StageLimit();

        fever = st.lim.stageLimit.fever;
        refresh();
    }

    private void abler(boolean b) {
        boolean on = b && fever != null && fever.enabled;

        jena.setEnabled(b && fever != null);
        jmax.setEnabled(on);
        jdur.setEnabled(on);
        jmul.setEnabled(on);
        jstart.setEnabled(on);
        jlen.setEnabled(on);

        for (int i = 0; i < FeverSettings.TIERS; i++) {
            jcost[i].setEnabled(on);
            jperc[i].setEnabled(on);
        }
    }

    private void ini() {
        add(jena);
        add(lmax);
        reg(jmax);
        add(ldur);
        reg(jdur);
        add(lmul);
        reg(jmul);
        add(lstart);
        reg(jstart);
        add(llen);
        reg(jlen);
        add(ltier);

        for (int i = 0; i < FeverSettings.TIERS; i++) {
            reg(jcost[i] = new JTF());
            reg(jperc[i] = new JTF());
        }
        jmax.setToolTipText("<html>Points that fill the gauge, fever mode starts when it is full");
        jdur.setToolTipText("Length of fever mode in frames");
        jmul.setToolTipText("<html>Percent of the money drop and the score of an enemy that its kill gives as fever points");
        jstart.setToolTipText("<html>Percent the kills are worth at the start of the stage, it goes to 100 over the time bonus length<br>Below 100 it goes up instead");
        for (int i = 0; i < FeverSettings.TIERS; i++) {
            jcost[i].setToolTipText("<html>Units with a cost up to this (and above the tier before) give fever points");
            jperc[i].setToolTipText("Percent of the cost of the unit that is given as fever points");
        }
        jena.setLnr(x -> {
            if (fever == null)
                return;
            fever.enabled = jena.isSelected();
            abler(true);
        });
    }

    private void input(JTF jtf, String text) {
        if (fever == null)
            return;
        // -1 if there is no number in the text, so what has no number keeps its value
        int val = CommonStatic.parseIntN(text);
        if (jtf == jmax) {
            if (val > 0)
                fever.maxPoints = Math.min(val, MAX_POINTS);
        } else if (jtf == jdur) {
            if (val > 0)
                fever.duration = Math.min(val, MAX_FRAMES);
        } else if (jtf == jmul) {
            if (val >= 0)
                fever.killMultiplier = Math.min(val, MAX_PERCENT);
        } else if (jtf == jstart) {
            if (val >= 0)
                fever.timeBonusStart = Math.min(val, MAX_PERCENT);
        } else if (jtf == jlen) {
            // "auto" has no number, that is what sets it
            fever.timeBonusFrames = val < 0 ? -1 : Math.min(val, MAX_FRAMES);
        } else {
            for (int i = 0; i < FeverSettings.TIERS; i++) {
                if (jtf == jcost[i])
                    fever.bonusCost[i] = val > 0 ? Math.min(val, MAX_POINTS) : -1;
                else if (jtf == jperc[i])
                    fever.bonusPercent[i] = Math.max(val, 0);
            }
            // the tiers get back in order
            fever.normalize();
        }
        refresh();
    }

    private void reg(JTF jtf) { // using "reg" for "register" because "set" is already used for ui
        add(jtf);
        jtf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (getFront().isAdj())
                    return;
                input(jtf, jtf.getText());
                getFront().callBack(fever);
            }
        });
    }

    private void refresh() {
        jena.setSelected(fever.enabled);
        jmax.setText(fever.maxPoints + "");
        jdur.setText(fever.duration + "");
        jmul.setText(fever.killMultiplier + "%");
        jstart.setText(fever.timeBonusStart + "%");
        // no number in "auto" and the tooltip tells what it is right now
        jlen.setText(fever.timeBonusFrames < 0 ? "auto" : fever.timeBonusFrames + "");
        jlen.setToolTipText("<html>Length of the time bonus in frames<br>auto: the timer of the dojo, or 3:00 (5400) if the stage isn't one, currently "
                + fever.getTimeBonusFrames(stage) + "<br>0: no time bonus");
        for (int i = 0; i < FeverSettings.TIERS; i++) {
            boolean used = fever.bonusCost[i] > 0;
            jcost[i].setText(used ? fever.bonusCost[i] + "" : "--");
            jperc[i].setText(used ? fever.bonusPercent[i] + "%" : "--");
        }

        abler(true);
    }

}
