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

    private static String[] rarityTxt;

    static {
        redefine();
    }

    protected static void redefine() {
        rarityTxt = new String[] { "N", "EX", "R", "SR", "UR", "LR" };
    }

    private final JL mone = new JL(MainLocale.INFO, "ht20");
    private final JL cano = new JL(MainLocale.INFO, "ht26");
    private final JL cres = new JL(MainLocale.INFO, "ht21");
    private final JL cost = new JL(MainLocale.INFO, "ht23");
    private final JL uspe = new JL(MainLocale.INFO, "ht24");
    private final JL espe = new JL(MainLocale.INFO, "ht25");
    private final JL racost = new JL(MainLocale.INFO, "price");
    private final JL racool = new JL(MainLocale.INFO, "cdo");
    private final JL ralimi = new JL(MainLocale.INFO, "ht11");
    private final JL radupe = new JL(MainLocale.INFO, "ht27");
    private final JTF jmon = new JTF();
    private final JTF jcan = new JTF();
    private final JTF jcre = new JTF();
    private final JTF jcos = new JTF();
    private final JTF jusp = new JTF();
    private final JTF jesp = new JTF();
    private final JTF[] jcool = new JTF[rarityTxt.length];
    private final JTF[] jcost = new JTF[rarityTxt.length];
    private final JTF[] jlimi = new JTF[rarityTxt.length];
    private final JTF[] jdupe = new JTF[rarityTxt.length];
    private final JTG cdst = new JTG(MainLocale.INFO, "ht22");

    private final CrossList<String> jlco = new CrossList<>(Interpret.getComboFilter(0));
    private final CrossList<String> jlorb = new CrossList<>(Interpret.ORB);
    private final JScrollPane jsco = new JScrollPane(jlco);
    private final JScrollPane jsorb = new JScrollPane(jlorb);
    private final JBTN banc = new JBTN(MainLocale.PAGE, "ban0");
    private final JBTN bano = new JBTN(MainLocale.PAGE, "ban0");

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

        set(mone, x, y, 0, 0, w, 50);
        set(jmon, x, y, w, 0, w, 50);
        set(cano, x, y, w * 2, 0, w, 50);
        set(jcan, x, y, w * 3, 0, w, 50);

        set(cost, x, y, 0, 50, w, 50);
        set(jcos, x, y, w, 50, w, 50);
        set(cres, x, y, w * 2, 50, w, 50);
        set(jcre, x, y, w * 3, 50, w, 50);
        set(cdst, x, y, w * 4, 50, w, 50);

        set(uspe, x, y, 0, 100, w, 50);
        set(jusp, x, y, w, 100, w, 50);
        set(espe, x, y, w * 2, 100, w, 50);
        set(jesp, x, y, w * 3, 100, w, 50);

        set(racost, x, y, 0, 150, w, 50);
        set(racool, x, y, 0, 250, w, 50);
        set(ralimi, x, y, 0, 350, w, 50);
        set(radupe, x, y, 0, 450, w, 50);
        for (int i = 0; i < rarityTxt.length; i++) {
            int wid = w * ((i % r) + 1);
            set(jcost[i], x, y, wid, 50 * ((i / r) + 3), w, 50);
            set(jcool[i], x, y, wid, 50 * ((i / r) + 5), w, 50);
            set(jlimi[i], x, y, wid, 50 * ((i / r) + 7), w, 50);
            set(jdupe[i], x, y, wid, 50 * ((i / r) + 9), w, 50);
        }

        set(jsco, x, y, (int) (w * 5.5), 0, w * 2, 250);
        set(banc, x, y, w * 6, 250, w, 50);

        set(jsorb, x, y, (int) (w * 5.5), 350, w * 2, 250);
        set(bano, x, y, w * 6, 600, w, 50);
    }

    private void ini() {
        add(mone);
        reg(jmon);
        add(cano);
        reg(jcan);

        add(cost);
        reg(jcos);
        add(cres);
        reg(jcre);

        add(uspe);
        reg(jusp);
        add(espe);
        reg(jesp);
        jusp.setToolTipText("<html>Use \"=\" to set speed equal to value (ex. =10)<br>Use \"x\" to multiply speed by value (ex. x10)");
        jesp.setToolTipText("<html>Use \"=\" to set speed equal to value (ex. =10)<br>Use \"x\" to multiply speed by value (ex. x10)");

        add(jsco);
        add(jsorb);
        add(banc);
        add(bano);
        add(racool);
        add(racost);
        add(ralimi);
        add(radupe);
        add(cdst);

        for (int i = 0; i < rarityTxt.length; i++) {
            reg(jcool[i] = new JTF());
            reg(jcost[i] = new JTF());
            reg(jlimi[i] = new JTF());
            reg(jdupe[i] = new JTF());
        }

        jlco.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlco.setCheck(i -> stli != null && stli.bannedCatCombo.contains(i));
        jlorb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlorb.setCheck(i -> stli != null && stli.bannedOrb.contains(i));

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
        for (int i = 0; i < rarityTxt.length; i++) {
            String r = rarityTxt[i] + ": ";
            jcost[i].setText(r + stli.costMultiplier[i] + "%");
            jcool[i].setText(r + stli.cooldownMultiplier[i] + "%");
            jlimi[i].setText(r + (stli.rarityDeployLimit[i] == -1 ? "--" : stli.rarityDeployLimit[i]));
            jdupe[i].setText(r + (stli.deployDuplicationTimes[i] == 0 ? "--" : stli.deployDuplicationTimes[i] + ", " + stli.deployDuplicationDelay[i] + "f"));
        }
        jmon.setText(stli.maxMoney == 0 ? "--" : stli.maxMoney + "");
        jcan.setText(stli.cannonMultiplier + "%");
        jcos.setText(stli.globalCost == -1 ? "--" : stli.globalCost + "");
        jcre.setText(stli.globalCooldown == 0 ? "--" : stli.globalCooldown + "");
        StageLimit.SpeedOverrideMode uniMode = stli.unitSpeedOverrideMode;
        if (stli.unitSpeedOverride == -1)
            jusp.setText("--");
        else
            jusp.setText(uniMode.getPre() + stli.unitSpeedOverride + uniMode.getPost());
        StageLimit.SpeedOverrideMode eneMode = stli.enemySpeedOverrideMode;
        if (stli.enemySpeedOverride == -1)
            jesp.setText("--");
        else
            jesp.setText(eneMode.getPre() + stli.enemySpeedOverride + eneMode.getPost());
        cdst.setSelected(stli.coolStart);
        jlco.repaint();
        jlorb.repaint();

        abler(true);
    }

    private void abler(boolean b) {
        for (int i = 0; i < rarityTxt.length; i++) {
            jcost[i].setEnabled(b);
            jcool[i].setEnabled(b);
            jlimi[i].setEnabled(b);
            jdupe[i].setEnabled(b);
        }

        jmon.setEnabled(b);
        jcan.setEnabled(b);
        jcre.setEnabled(b);
        jcos.setEnabled(b);
        jusp.setEnabled(b);
        jesp.setEnabled(b);

        jlco.setEnabled(b);
        jlorb.setEnabled(b);
        cdst.setEnabled(b);
        banc.setEnabled(b && jlco.getSelectedIndex() != -1);
        bano.setEnabled(b && jlorb.getSelectedIndex() != -1);
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
        if (jtf == jmon)
            stli.maxMoney = Math.max(CommonStatic.parseIntN(text), 0);
        else if (jtf == jcan)
            stli.cannonMultiplier = Math.max(CommonStatic.parseIntN(text), 0);
        else if (jtf == jcos)
            stli.globalCost = Math.max(CommonStatic.parseIntN(text), -1);
        else if (jtf == jcre)
            stli.globalCooldown = Math.max(CommonStatic.parseIntN(text), 0);
        else if (jtf == jusp) {
            stli.unitSpeedOverride = Math.max(CommonStatic.parseIntN(text), -1);
            if (text.startsWith("x") || text.startsWith("*"))
                stli.unitSpeedOverrideMode = StageLimit.SpeedOverrideMode.MULTIPLY;
            else
                stli.unitSpeedOverrideMode = StageLimit.SpeedOverrideMode.SET;
        }
        else if (jtf == jesp) {
            stli.enemySpeedOverride = Math.max(CommonStatic.parseIntN(text), -1);
            if (text.startsWith("x") || text.startsWith("*"))
                stli.enemySpeedOverrideMode = StageLimit.SpeedOverrideMode.MULTIPLY;
            else
                stli.enemySpeedOverrideMode = StageLimit.SpeedOverrideMode.SET;
        }

        else {
            for (int i = 0; i < rarityTxt.length; i++) {
                if (jcost[i] == jtf) {
                    stli.costMultiplier[i] = Math.max(CommonStatic.parseIntN(text), 0);
                    break;
                } else if (jcool[i] == jtf) {
                    stli.cooldownMultiplier[i] = Math.max(CommonStatic.parseIntN(text), 0);
                    break;
                } else if (jlimi[i] == jtf) {
                    stli.rarityDeployLimit[i] = Math.max(CommonStatic.parseIntN(text), -1);
                } else if (jdupe[i] == jtf) {
                    int[] nums = CommonStatic.parseIntsN(text);
                    if (nums.length == 0) {
                        stli.deployDuplicationDelay[i] = stli.deployDuplicationTimes[i] = 0;
                    } else if (nums.length == 1) {
                        stli.deployDuplicationTimes[i] = nums[0];
                        stli.deployDuplicationDelay[i] = 60;
                    } else {
                        stli.deployDuplicationTimes[i] = nums[0];
                        stli.deployDuplicationDelay[i] = nums[1];
                    }
                    break;
                }
            }
        }
    }

    private void addListeners() {
        jlco.addListSelectionListener(x -> {
            banc.setEnabled(jlco.getSelectedIndex() != -1);
            banc.setText(MainLocale.PAGE, stli.bannedCatCombo.contains(jlco.getSelectedIndex()) ? "ban1" : "ban0");
        });

        jlorb.addListSelectionListener(x -> {
            bano.setEnabled(jlorb.getSelectedIndex() != -1);
            bano.setText(MainLocale.PAGE, stli.bannedOrb.contains(jlorb.getSelectedIndex()) ? "ban1" : "ban0");
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

        bano.setLnr(x -> {
            if (stli == null || jlorb.getSelectedIndex() == -1)
                return;

            if (stli.bannedOrb.contains(jlorb.getSelectedIndex())) {
                stli.bannedOrb.remove(jlorb.getSelectedIndex());
                bano.setText(MainLocale.PAGE, "ban0");
            } else {
                stli.bannedOrb.add(jlorb.getSelectedIndex());
                bano.setText(MainLocale.PAGE, "ban1");
            }

            jlorb.repaint();
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

    public int getPWidth() {
        return (int) (1400 / 7.5);
    }

    public int getPHeight() {
        return 700;
    }
}
