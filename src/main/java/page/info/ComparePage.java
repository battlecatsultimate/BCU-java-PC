package page.info;

import common.CommonStatic;
import common.battle.BasisSet;
import common.battle.data.MaskEnemy;
import common.battle.data.MaskEntity;
import common.battle.data.MaskUnit;
import common.util.Data;
import common.util.unit.EForm;
import common.util.unit.Form;
import common.util.unit.Level;
import page.*;
import page.info.filter.EnemyFindPage;
import page.info.filter.UnitFindPage;
import utilpc.Interpret;
import utilpc.UtilPC;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;

public class ComparePage extends Page {

    private static final long serialVersionUID = 1L;

    private final JL[] names = new JL[3];
    private final JTF[] level = new JTF[names.length];
    private final EntityAbilities[] abilities = new EntityAbilities[names.length];
    private final JScrollPane[] abilityPanes = new JScrollPane[names.length];

    private final JL[][] main = new JL[10][names.length + 1]; // stats on both
    private final JL[][] seco = new JL[1][names.length + 1]; // stats after others
    private final JL[][] unit = new JL[2][names.length + 1]; // stats on unit
    private final JL[][] enem = new JL[2][names.length + 1]; // stats on enemy

    private final JCB[] boxes = new JCB[main.length + unit.length + enem.length + seco.length + 1];

    private final JBTN back = new JBTN(0, "back");

    private final JBTN[][] sele = new JBTN[names.length][2];

    private final MaskEntity[] maskEntities = new MaskEntity[names.length];
    private final int[][] maskEntityLvl = new int[names.length][6];

    private EnemyFindPage efp = null;
    private UnitFindPage ufp = null;
    private int s = -1;

    private boolean resize = true;

    private final BasisSet b = BasisSet.current();

    public ComparePage(Page p) {
        super(p);

        ini();
        resized();
    }

    private void ini() {
        add(back);

        for (int i = 0; i < sele.length; i++) {
            add(sele[i][0] = new JBTN(0, "veif"));
            add(sele[i][1] = new JBTN(0, "vuif"));
        }

        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new JCB();
            boxes[i].setSelected(true);
            add(boxes[i]);
        }

        for (int i = 0; i < names.length; i++) {
            add(names[i] = new JL("-"));
        }

        for (int i = 0; i < level.length; i++) {
            JTF jtf = new JTF("-");
            jtf.setEnabled(false);
            add(level[i] = jtf);
        }

        for (int i = 0; i < abilityPanes.length; i++) {
            JScrollPane p = new JScrollPane();
            p.setEnabled(false);
            add(abilityPanes[i] = p);
        }

        for (int i = 0; i < main.length; i++) {
            for (int j = 0; j < main[i].length; j++) {
                main[i][j] = new JL("-");
                if (j == 0)
                    main[i][j].setHorizontalAlignment(SwingConstants.CENTER);

                add(main[i][j]);
            }
        }

        for (int i = 0; i < unit.length; i++) {
            for (int j = 0; j < unit[i].length; j++) {
                unit[i][j] = new JL("-");
                if (j == 0)
                    unit[i][j].setHorizontalAlignment(SwingConstants.CENTER);

                add(unit[i][j]);
            }
        }

        for (int i = 0; i < enem.length; i++) {
            for (int j = 0; j < enem[i].length; j++) {
                enem[i][j] = new JL("-");
                if (j == 0)
                    enem[i][j].setHorizontalAlignment(SwingConstants.CENTER);

                add(enem[i][j]);
            }
        }

        for (int i = 0; i < seco.length; i++) {
            for (int j = 0; j < seco[i].length; j++) {
                seco[i][j] = new JL("-");
                if (j == 0)
                    seco[i][j].setHorizontalAlignment(SwingConstants.CENTER);

                add(seco[i][j]);
            }
        }

        main[0][0].setText("HP");
        main[1][0].setText(MainLocale.INFO, "hb");
        main[2][0].setText(MainLocale.INFO, "range");
        main[3][0].setText("atk");
        main[4][0].setText("dps");
        main[5][0].setText(MainLocale.INFO, "preaa");
        main[6][0].setText(MainLocale.INFO, "postaa");
        main[7][0].setText(MainLocale.INFO, "atkf");
        main[8][0].setText("TBA");
        main[9][0].setText(MainLocale.INFO, "speed");

        boxes[0].setText("HP");
        boxes[1].setText(MainLocale.INFO, "hb");
        boxes[2].setText(MainLocale.INFO, "range");
        boxes[3].setText("atk");
        boxes[4].setText("dps");
        boxes[5].setText(MainLocale.INFO, "preaa");
        boxes[6].setText(MainLocale.INFO, "postaa");
        boxes[7].setText(MainLocale.INFO, "atkf");
        boxes[8].setText("TBA");
        boxes[9].setText(MainLocale.INFO, "speed");

        unit[0][0].setText("CD");
        unit[1][0].setText(MainLocale.INFO, "price");

        boxes[main.length].setText("CD");
        boxes[1 + main.length].setText(MainLocale.INFO, "price");

        enem[0][0].setText(MainLocale.INFO, "drop");
        enem[1][0].setText(MainLocale.INFO, "shield");

        boxes[main.length + unit.length].setText(MainLocale.INFO, "drop");
        boxes[1 + main.length + unit.length].setText(MainLocale.INFO, "shield");

        seco[0][0].setText(MainLocale.INFO, "trait");

        boxes[main.length + unit.length + enem.length].setText(MainLocale.INFO, "trait");

        boxes[boxes.length - 1].setText("ability");

        addListeners();
    }

    private void addListeners() {
        back.addActionListener(x -> changePanel(getFront()));

        for (int i = 0; i < sele.length; i++) {
            int finalI = i;
            sele[i][0].addActionListener(x -> {
                changePanel(efp = new EnemyFindPage(getThis()));
                s = finalI;
            });
            sele[i][1].addActionListener(x -> {
                changePanel(ufp = new UnitFindPage(getThis()));
                s = finalI;
            });
        }

        for (int i = 0; i < level.length; i++) {
            JTF jtf = level[i];
            int finalI = i;
            jtf.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    int[] data = CommonStatic.parseIntsN(jtf.getText().trim().replace("%", ""));

                    if (maskEntities[finalI] instanceof MaskEnemy) {
                        if (data.length == 1) {
                            if (data[0] != -1)
                                maskEntityLvl[finalI][0] = maskEntityLvl[finalI][1] = data[0];

                            jtf.setText(CommonStatic.toArrayFormat(data[0], data[0]) + "%");
                        } else if (data.length == 2) {
                            if (data[0] != -1)
                                maskEntityLvl[finalI][0] = data[0];
                            if (data[1] != -1)
                                maskEntityLvl[finalI][1] = data[1];

                            jtf.setText(CommonStatic.toArrayFormat(data[0], data[1]) + "%");
                        } else {
                            jtf.setText(CommonStatic.toArrayFormat(maskEntityLvl[finalI][0], maskEntityLvl[finalI][1]) + "%");
                        }
                    } else {
                        Form f = ((MaskUnit) maskEntities[finalI]).getPack();
                        maskEntityLvl[finalI] = f.regulateLv(data, maskEntityLvl[finalI]);
                        String[] strs = UtilPC.lvText(f, maskEntityLvl[finalI]);
                        jtf.setText(strs[0]);
                    }


                    reset();
                }
            });
        }
    }

    private void reset() {
        s = -1;

        for (int i = 0; i < maskEntities.length; i++) {
            MaskEntity m = maskEntities[i];
            int index = i + 1;
            if (m == null) {
                abilityPanes[i].setViewportView(null);
                abilityPanes[i].setEnabled(false);
                names[i].setIcon(null);
                names[i].setText("-");
                level[i].setEnabled(false);
                level[i].setText("-");
                for (JL[] jls : main)
                    jls[index].setText("-");
                for (JL[] jls : unit)
                    jls[index].setText("-");
                for (JL[] jls : enem)
                    jls[index].setText("-");
                for (JL[] jls : seco)
                    jls[index].setText("-");
                continue;
            }

            boolean state = level[i].isEnabled();

            int hp = m.getHp();
            int atk = 0;
            int[][] atkData = m.rawAtkData();
            StringBuilder atkString = new StringBuilder();
            StringBuilder preString = new StringBuilder();

            if (m instanceof MaskEnemy) {
                MaskEnemy enemy = (MaskEnemy) m;

                int[] multi = state ? maskEntityLvl[i] : (maskEntityLvl[i] = new int[]{100, 100, 0, 0, 0, 0});
                double mul = (multi[0] * enemy.multi(b)) / 100.0;
                double mula = (multi[1] * enemy.multi(b)) / 100.0;

                abilityPanes[i].setViewportView(abilities[i] = new EntityAbilities(getFront(), m, multi));

                for (int[] atkDatum : atkData) {
                    if (atkString.length() > 0) {
                        atkString.append(" / ");
                        preString.append(" / ");
                    }

                    atkString.append(Math.round(atkDatum[0] * mula));
                    preString.append(atkDatum[1]).append("f");
                }

                main[0][index].setText((int) (hp * mul) + "");
                main[4][index].setText((int) (m.allAtk() * mula * 30.0 / m.getItv()) + "");

                enem[0][index].setText(Math.floor(enemy.getDrop() * b.t().getDropMulti()) / 100 + "");
                enem[1][index].setText(enemy.getShield() + "");

                for (JL[] jls : unit)
                    jls[index].setText("-");
            } else if (m instanceof MaskUnit) {
                int[] multi = state
                        ? maskEntityLvl[i]
                        : (maskEntityLvl[i] = ((MaskUnit) m).getPack().unit.getPrefLvs());
                MaskUnit mu = ((MaskUnit) m).getPack().getPCoin() != null
                        ? ((MaskUnit) m).getPack().getPCoin().improve(multi)
                        : ((MaskUnit) m);
                Form f = mu.getPack();
                EForm ef = new EForm(f, multi);

                abilityPanes[i].setViewportView(abilities[i] = new EntityAbilities(getFront(), m, multi));

                double mul = f.unit.lv.getMult(multi[0]);
                double atkLv = b.t().getAtkMulti();
                double defLv = b.t().getDefMulti();

                MaskEnemy e = (MaskEnemy) Arrays.stream(maskEntities)
                        .filter(c -> c instanceof MaskEnemy)
                        .findFirst()
                        .orElse(null);

                int overlap = e != null ? e.getType() & mu.getType() : 0;
                int checkHealth = (Data.AB_GOOD | Data.AB_RESIST | Data.AB_RESISTS);
                int checkAttack = (Data.AB_GOOD | Data.AB_MASSIVE | Data.AB_MASSIVES);

                hp = (int) (Math.round(hp * mul) * defLv);
                if (f.getPCoin() != null)
                    hp = (int) (hp * f.getPCoin().getHPMultiplication(multi));

                for (int[] atkDatum : atkData) {
                    if (atkString.length() > 0) {
                        atkString.append(" / ");
                        preString.append(" / ");
                    }

                    int a = (int) (Math.round(atkDatum[0] * mul) * atkLv);
                    if (f.getPCoin() != null)
                        a = (int) (a * f.getPCoin().getAtkMultiplication(multi));

                    atkString.append(a);
                    preString.append(atkDatum[1]).append("f");
                    atk += a;

                    if (overlap > 0 && (mu.getAbi() & checkAttack) > 0) {
                        int effectiveDMG = a;
                        if ((mu.getAbi() & Data.AB_MASSIVES) > 0)
                            effectiveDMG *= b.t().getMASSIVESATK(overlap);
                        if ((mu.getAbi() & Data.AB_MASSIVE) > 0)
                            effectiveDMG *= b.t().getMASSIVEATK(overlap);
                        if ((mu.getAbi() & Data.AB_GOOD) > 0) {
                            effectiveDMG *= b.t().getGOODATK(overlap);
                        }
                        atkString.append(" (").append(effectiveDMG).append(")");
                    }
                }

                unit[0][index].setText(b.t().getFinRes(mu.getRespawn()) + "f");
                unit[1][index].setText(ef.getPrice(1) + "");

                for (JL[] jls : enem)
                    jls[index].setText("-");

                if (overlap > 0 && (mu.getAbi() & checkHealth) > 0) {
                    int effectiveHP = hp;

                    if ((mu.getAbi() & Data.AB_RESISTS) > 0)
                        effectiveHP /= b.t().getRESISTSDEF(overlap);
                    if ((mu.getAbi() & Data.AB_RESIST) > 0)
                        effectiveHP /= b.t().getRESISTDEF(overlap, mu, new Level(multi));
                    if ((mu.getAbi() & Data.AB_GOOD) > 0) {
                        effectiveHP /= b.t().getGOODDEF(overlap, mu, new Level(multi));
                    }

                    main[0][index].setText(hp + " (" + effectiveHP + ")");
                } else {
                    main[0][index].setText(hp + "");
                }
                if (overlap > 0 && (mu.getAbi() & checkAttack) > 0) {
                    int effectiveDMG = atk;
                    if ((mu.getAbi() & Data.AB_MASSIVE) > 0)
                        effectiveDMG *= b.t().getMASSIVEATK(overlap);
                    if ((mu.getAbi() & Data.AB_GOOD) > 0) {
                        effectiveDMG *= b.t().getGOODATK(overlap);
                    }
                    main[4][index].setText((int) (atk * 30.0 / m.getItv())
                            + " (" + (int) (effectiveDMG * 30.0 / m.getItv()) + ")");
                } else {
                    main[4][index].setText((int) (atk * 30.0 / m.getItv()) + "");
                }
            }

            level[i].setEnabled(true);

            names[i].setIcon(UtilPC.getIcon(m.getPack().anim.getEdi()));
            names[i].setText(m.getPack().toString());

            main[1][index].setText(m.getHb() + "");
            main[2][index].setText(m.getRange() + "");
            main[3][index].setText(atkString.toString());
            main[5][index].setText(preString.toString());
            main[6][index].setText(m.getPost() + "f");
            main[7][index].setText(m.getItv() + "f");
            main[8][index].setText(m.getTBA() + "f");
            main[9][index].setText(m.getSpeed() + "");

            seco[0][index].setText(Interpret.getTrait(m.getType(), m instanceof MaskEnemy ? ((MaskEnemy) m).getStar() : 0));
        }

        requireResize();
    }

    @Override
    protected void renew() {
        if (s == -1)
            return;

        MaskEntity ent = (efp != null && efp.getSelected() != null)
                ? efp.getSelected().de
                : (ufp != null && ufp.getForm() != null)
                ? ufp.getForm().du
                : null;

        if (ent instanceof MaskEnemy) {
            int[] data = maskEntities[s] instanceof MaskEnemy
                    ? CommonStatic.parseIntsN(level[s].getText().trim().replace("%", ""))
                    : new int[]{100, 100};
            if (data.length == 1) {
                if (data[0] != -1)
                    maskEntityLvl[s][0] = maskEntityLvl[s][1] = data[0];

                level[s].setText(CommonStatic.toArrayFormat(data[0], data[0]) + "%");
            } else if (data.length == 2) {
                if (data[0] != -1)
                    maskEntityLvl[s][0] = data[0];
                if (data[1] != -1)
                    maskEntityLvl[s][1] = data[1];

                level[s].setText(CommonStatic.toArrayFormat(data[0], data[1]) + "%");
            } else {
                level[s].setText(CommonStatic.toArrayFormat(maskEntityLvl[s][0], maskEntityLvl[s][1]) + "%");
            }
        } else if (ent != null) {
            Form f = ((MaskUnit) ent).getPack();
            int[] data = maskEntities[s] instanceof MaskUnit
                    ? CommonStatic.parseIntsN(level[s].getText())
                    : f.unit.getPrefLvs();
            maskEntityLvl[s] = f.regulateLv(data, maskEntityLvl[s]);
            String[] strs = UtilPC.lvText(f, maskEntityLvl[s]);
            level[s].setText(strs[0]);
        }

        maskEntities[s] = ent;
        efp = null;
        ufp = null;
        reset();
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);

        set(back, x, y, 0, 0, 200, 50);

        int width = 600;

        for (int i = 0; i < sele.length; i++) {
            int w = (width * 3 / 4) + width * i;
            set(sele[i][0], x, y, w, 50, 200, 50);
            set(sele[i][1], x, y, w, 100, 200, 50);
        }

        int posY = 200;

        for (JCB b : boxes) {
            set(b, x, y, 275 + ((main[0].length - 1) * width), posY, 200, 50);
            posY += 50;
        }

        for (int i = 0; i < names.length; i++)
            set(names[i], x, y, 250 + (i * width), 200, width, 50);
        for (int i = 0; i < level.length; i++)
            set(level[i], x, y, 250 + (i * width), 150, width, 50);

        posY = 250;

        for (int i = 0; i < main.length; i++) { // 9
            JL[] d = main[i];
            if (!boxes[i].isSelected()) {
                for (JL ex : d)
                    set(ex, x, y, 0, 0, 0, 0);
                continue;
            }

            int posX = 50;
            for (int j = 0; j < d.length; j++) {
                set(d[j], x, y, posX, posY, j == 0 ? 200 : width, 50);
                if (j == 0)
                    posX += 200;
                else
                    posX += width;
            }
            posY += 50;
        }

        for (int i = 0; i < unit.length; i++) {
            JL[] d = unit[i];
            if (!boxes[i + main.length].isSelected()) {
                for (JL ex : d)
                    set(ex, x, y, 0, 0, 0, 0);
                continue;
            }
            int posX = 50;
            for (int j = 0; j < d.length; j++) {
                set(d[j], x, y, posX, posY, j == 0 ? 200 : width, 50);
                if (j == 0)
                    posX += 200;
                else
                    posX += width;
            }
            posY += 50;
        }

        for (int i = 0; i < enem.length; i++) {
            JL[] d = enem[i];
            if (!boxes[i + main.length + unit.length].isSelected()) {
                for (JL ex : d)
                    set(ex, x, y, 0, 0, 0, 0);
                continue;
            }
            int posX = 50;
            for (int j = 0; j < d.length; j++) {
                set(d[j], x, y, posX, posY, j == 0 ? 200 : width, 50);
                if (j == 0)
                    posX += 200;
                else
                    posX += width;
            }
            posY += 50;
        }

        for (int i = 0; i < seco.length; i++) {
            JL[] d = seco[i];
            if (!boxes[i + main.length + unit.length + enem.length].isSelected()) {
                for (JL ex : d)
                    set(ex, x, y, 0, 0, 0, 0);
                continue;
            }
            int posX = 50;
            for (int j = 0; j < d.length; j++) {
                set(d[j], x, y, posX, posY, j == 0 ? 200 : width, 50);
                if (j == 0)
                    posX += 200;
                else
                    posX += width;
            }
            posY += 50;
        }

        int posX = 250;
        for (int i = 0; i < abilityPanes.length; i++) {
            JScrollPane pane = abilityPanes[i];
            if (!boxes[boxes.length - 1].isSelected()) {
                set(pane, x, y, 0, 0, 0, 0);
                continue;
            }

            if (resize) {
                EntityAbilities e = abilities[i];
                if (e != null) {
                    e.setPreferredSize(size(x, y, e.getPWidth(), e.getPHeight()).toDimension());
                    pane.getVerticalScrollBar().setUnitIncrement(size(x, y, 50));
                    pane.revalidate();
                }
            }

            set(pane, x, y, posX, posY, width, 200);
            posX += 600;
        }

        resize = false;
    }

    public void requireResize() {
        resize = true;
    }
}
