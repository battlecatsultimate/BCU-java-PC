package page.info.edit;

import common.CommonStatic;
import common.pack.PackData;
import common.util.stage.Stage;
import common.util.stage.StageLimit;
import page.*;
import page.support.CrossList;
import utilpc.Interpret;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class StageLimitTable extends Page {

    private static final long serialVersionUID = 1L;

    private static String[] rarity;

    static {
        redefine();
    }

    protected static void redefine() {
        rarity = new String[] { "N", "EX", "R", "SR", "UR", "LR" };
    }

    private final JL bank = new JL(MainLocale.INFO, "ht20");
    private final JL cres = new JL(MainLocale.INFO, "ht21");
    private final JL racost = new JL(MainLocale.INFO, "price");
    private final JL racool = new JL(MainLocale.INFO, "cdo");
    private final JTF jban = new JTF();
    private final JTF jcre = new JTF();
    private final JTF[] jcool = new JTF[rarity.length];
    private final JTF[] jcost = new JTF[rarity.length];
    private final JTG cdst = new JTG(MainLocale.INFO, "ht22");

    private final CrossList<String> jlco = new CrossList<>(Interpret.getComboFilter(0));
    private final JScrollPane jsco = new JScrollPane(jlco);
    private final JBTN banc = new JBTN(MainLocale.PAGE, "ban0");

    private final PackData.UserPack pac;

    private StageLimit stli;

    protected StageLimitTable(Page p, PackData.UserPack pack) {
        super(p);
        pac = pack;
        ini();
    }

    @Override
    protected void resized(int x, int y) {
        int w = 1400 / 8;
        int r = 4;

        set(bank, x, y, 0, 0, w, 50);
        set(jban, x, y, w, 0, w, 50);
        set(cres, x, y, w * 2, 0, w, 50);
        set(jcre, x, y, w * 3, 0, w, 50);

        set(racost, x, y, 0, 50, w, 50);
        set(racool, x, y, 0, 150, w, 50);
        for (int i = 0; i < rarity.length; i++) {
            set(jcost[i], x, y, w * ((i % r) + 1), 50 * ((i / r) + 1), w, 50);
            set(jcool[i], x, y, w * ((i % r) + 1), 50 * ((i / r) + 3), w, 50);
        }
        set(cdst, x, y, 0, 250, w, 50);

        set(jsco, x, y, (int) (w * 5.5), 0, w * 2, 250);
        set(banc, x, y, w * 6, 250, w, 50);
    }

    private void ini() {
        add(bank);
        reg(jban);
        add(cres);
        reg(jcre);
        add(jsco);
        add(banc);
        add(racool);
        add(racost);
        add(cdst);

        for (int i = 0; i < rarity.length; i++) {
            reg(jcool[i] = new JTF());
            reg(jcost[i] = new JTF());
        }

        jlco.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlco.setCheck(i -> stli != null && stli.bannedCatCombo.contains(i));

        addListeners();
    }

    public void setData(Stage st) {
        if (st == null) {
            abler(false);
            return;
        }
        setStageLimit(st.lim.stageLimit == null ? st.lim.stageLimit = new StageLimit() : st.lim.stageLimit);
    }

    public void setStageLimit(StageLimit sl) {
        if (sl == null) {
            abler(false);
            return;
        }
        stli = sl;
        for (int i = 0; i < rarity.length; i++) {
            jcost[i].setText(rarity[i] + ": " + stli.costMultiplier[i] + "%");
            jcool[i].setText(rarity[i] + ": " + stli.cooldownMultiplier[i] + "%");
        }
        jban.setText(stli.maxMoney + "");
        jcre.setText(stli.globalCooldown + "");
        cdst.setSelected(stli.coolStart);
        jlco.repaint();

        abler(true);
    }

    private void abler(boolean b) {
        for (int i = 0; i < rarity.length; i++) {
            jcost[i].setEnabled(b);
            jcool[i].setEnabled(b);
        }
        jban.setEnabled(b);
        jcre.setEnabled(b);
        jlco.setEnabled(b);
        cdst.setEnabled(b);
        banc.setEnabled(b && jlco.getSelectedIndex() != -1);
    }

    private void reg(JTF jtf) { // using "reg" for "register" because "set" is already used for ui
        add(jtf);

        jtf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (getFront().isAdj())
                    return;
                input(jtf, jtf.getText());
                getFront().callBack(stli);
            }
        });
    }

    private void input(JTF jtf, String text) {
        if (jtf == jban) {
            stli.maxMoney = Math.max(CommonStatic.parseIntN(text), 0);
        } else if (jtf == jcre)
            stli.globalCooldown = Math.max(CommonStatic.parseIntN(text), 0);
        else {
            for (int i = 0; i < rarity.length; i++) {
                if (jcost[i] == jtf) {
                    stli.costMultiplier[i] = Math.max(CommonStatic.parseIntN(text), 0);
                    break;
                } else if (jcool[i] == jtf) {
                    stli.cooldownMultiplier[i] = Math.max(CommonStatic.parseIntN(text), 0);
                    break;
                }
            }
        }
    }

    private void addListeners() {
        jlco.addListSelectionListener(x -> {
            banc.setEnabled(jlco.getSelectedIndex() != -1);
            banc.setText(MainLocale.PAGE, "ban" + (!stli.bannedCatCombo.contains(jlco.getSelectedIndex()) ? "0" : "1"));
        });

        banc.setLnr(x -> {
            if (stli == null || jlco.getSelectedIndex() == -1)
                return;

            if (stli.bannedCatCombo.contains(jlco.getSelectedIndex())) {
                stli.bannedCatCombo.remove(jlco.getSelectedIndex());
                banc.setText(MainLocale.PAGE, "ban0");
            } else {
                stli.bannedCatCombo.add(jlco.getSelectedIndex());
                banc.setText(MainLocale.PAGE, "ban1");
            }

            jlco.repaint();
        });

        cdst.setLnr(x -> {
           if (stli == null)
               return;
           stli.coolStart = cdst.isSelected();
        });
    }

    @Override
    protected JButton getBackButton() {
        return null;
    }
}
