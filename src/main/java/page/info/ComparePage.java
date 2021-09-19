package page.info;

import common.battle.BasisSet;
import common.battle.data.MaskEnemy;
import common.battle.data.MaskEntity;
import common.battle.data.MaskUnit;
import common.util.Data;
import common.util.unit.EForm;
import common.util.unit.Enemy;
import common.util.unit.Form;
import common.util.unit.Level;
import page.*;
import page.info.filter.EnemyFindPage;
import page.info.filter.UnitFindPage;
import utilpc.UtilPC;

import javax.swing.*;

public class ComparePage extends Page {

    private static final long serialVersionUID = 1L;

    private final JL[] names = new JL[2];

    private final JL[][] main = new JL[10][3]; // comparing both
    private final JL[][] unit = new JL[2][3]; // comparing unit
    private final JL[][] enem = new JL[2][3]; // comparing enemy

    private final JCB[] boxes = new JCB[main.length + unit.length + enem.length];

    private final JBTN back = new JBTN(0, "back");

    private final JBTN sl1e = new JBTN(0, "veif");
    private final JBTN sl1u = new JBTN(0, "vuif");
    private final JBTN sl2e = new JBTN(0, "veif");
    private final JBTN sl2u = new JBTN(0, "vuif");

    private final MaskEntity[] maskEntities = new MaskEntity[2];

    private EnemyFindPage efp = null;
    private UnitFindPage ufp = null;
    private int s = -1;

    private final BasisSet b = BasisSet.current();

    public ComparePage(Page p) {
        super(p);

        ini();
        resized();
    }

    private void ini() {
        add(back);

        add(sl1e);
        add(sl1u);
        add(sl2e);
        add(sl2u);

        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new JCB();
            boxes[i].setSelected(true);
            add(boxes[i]);
        }

        for (int i = 0; i < names.length; i++) {
            add(names[i] = new JL("-"));
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
        boxes[1 + main.length + unit.length].setText(MainLocale.INFO, "drop");

        addListeners();
    }

    private void addListeners() {
        back.addActionListener(x -> changePanel(getFront()));

        sl1e.addActionListener(x -> {
            changePanel(efp = new EnemyFindPage(getThis()));
            s = 0;
        });
        sl1u.addActionListener(x -> {
            changePanel(ufp = new UnitFindPage(getThis()));
            s = 0;
        });
        sl2e.addActionListener(x -> {
            changePanel(efp = new EnemyFindPage(getThis()));
            s = 1;
        });
        sl2u.addActionListener(x -> {
            changePanel(ufp = new UnitFindPage(getThis()));
            s = 1;
        });
    }

    private void reset() {
        s = -1;

        for (int i = 0; i < maskEntities.length; i++) {
            MaskEntity m = maskEntities[i];
            int index = i + 1;
            if (m == null) {
                names[i].setIcon(null);
                names[i].setText("-");
                for (JL[] jls : main)
                    jls[index].setText("-");
                for (JL[] jls : unit)
                    jls[index].setText("-");
                continue;
            }

            int hp = m.getHp();
            int atk = 0;
            int[][] atkData = m.rawAtkData();
            StringBuilder atkString = new StringBuilder();
            StringBuilder preString = new StringBuilder();

            if (m instanceof MaskEnemy) {
                MaskEnemy enemy = (MaskEnemy) m;
                for (int[] atkDatum : atkData) {
                    if (atkString.length() > 0) {
                        atkString.append(" / ");
                        preString.append(" / ");
                    }

                    int a = Math.round(atkDatum[0]);
                    atkString.append(a);
                    preString.append(atkDatum[1]).append("f");
                }

                main[0][index].setText(hp + "");
                main[4][index].setText((int) (m.allAtk() * 30.0 / m.getItv()) + "");

                enem[0][index].setText(Math.floor(enemy.getDrop() * b.t().getDropMulti()) / 100 + "");
                enem[1][index].setText(enemy.getShield() + "");

                for (JL[] jls : unit)
                    jls[index].setText("-");
            } else if (m instanceof MaskUnit) {
                Form f = ((MaskUnit) m).getPack();
                int[] multi = f.unit.getPrefLvs();
                EForm ef = new EForm(f, multi);

                double mul = f.unit.lv.getMult(multi[0]);
                double atkLv = b.t().getAtkMulti();
                double defLv = b.t().getDefMulti();

                MaskEnemy e = maskEntities[index % 2] instanceof MaskEnemy
                        ? (MaskEnemy) maskEntities[index % 2]
                        : null;
                int overlap = e != null ? e.getType() & f.du.getType() : 0;
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

                    if (overlap > 0 && (f.du.getAbi() & checkAttack) > 0) {
                        int effectiveDMG = a;
                        if ((f.du.getAbi() & Data.AB_MASSIVES) > 0)
                            effectiveDMG *= b.t().getMASSIVESATK(overlap);
                        if ((f.du.getAbi() & Data.AB_MASSIVE) > 0)
                            effectiveDMG *= b.t().getMASSIVEATK(overlap);
                        if ((f.du.getAbi() & Data.AB_GOOD) > 0) {
                            effectiveDMG *= b.t().getGOODATK(overlap);
                        }
                        atkString.append(" (").append(effectiveDMG).append(")");
                    }
                }

                unit[0][index].setText(b.t().getFinRes(f.du.getRespawn()) + "f");
                unit[1][index].setText(ef.getPrice(1) + "");

                for (JL[] jls : enem)
                    jls[index].setText("-");

                if (overlap > 0 && (f.du.getAbi() & checkHealth) > 0) {
                    int effectiveHP = hp;

                    if ((f.du.getAbi() & Data.AB_RESISTS) > 0)
                        effectiveHP /= b.t().getRESISTSDEF(overlap);
                    if ((f.du.getAbi() & Data.AB_RESIST) > 0)
                        effectiveHP /= b.t().getRESISTDEF(overlap, f.du, new Level(multi));
                    if ((f.du.getAbi() & Data.AB_GOOD) > 0) {
                        effectiveHP /= b.t().getGOODDEF(overlap, f.du, new Level(multi));
                    }

                    main[0][index].setText(hp + " (" + effectiveHP + ")");
                } else {
                    main[0][index].setText(hp + "");
                }
                if (overlap > 0 && (f.du.getAbi() & checkAttack) > 0) {
                    int effectiveDMG = atk;
                    if ((f.du.getAbi() & Data.AB_MASSIVE) > 0)
                        effectiveDMG *= b.t().getMASSIVEATK(overlap);
                    if ((f.du.getAbi() & Data.AB_GOOD) > 0) {
                        effectiveDMG *= b.t().getGOODATK(overlap);
                    }
                    main[4][index].setText((int) (atk * 30.0 / m.getItv())
                            + " (" + (int) (effectiveDMG * 30.0 / m.getItv()) + ")");
                } else {
                    main[4][index].setText((int) (atk * 30.0 / m.getItv()) + "");
                }
            }

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
        }
    }

    @Override
    protected void renew() {
        if (s == -1)
            return;

        if (efp != null) {
            Enemy e = efp.getSelected();
            maskEntities[s] = e == null ? null : e.de;
            efp = null;
        } else if (ufp != null) {
            Form f = ufp.getForm();
            maskEntities[s] = f == null ? null : f.du;
            ufp = null;
        }

        reset();
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);

        set(back, x, y, 0, 0, 200, 50);

        int width = 650;

        set(sl1e, x, y, 475, 50, 200, 50);
        set(sl1u, x, y, 475, 100, 200, 50);
        set(sl2e, x, y, 475 + width, 50, 200, 50);
        set(sl2u, x, y, 475 + width, 100, 200, 50);

        int posY = 150;

        for (JCheckBox b : boxes) {
            posY += 50;
            set(b, x, y, 300 + ((main[0].length - 1) * width), posY, 200, 50);
        }

        for (int i = 0; i < names.length; i++)
            set(names[i], x, y, 250 + (i * width), 150, 650, 50);

        posY = 150;
        for (int i = 0; i < main.length; i++) {
            JL[] d = main[i];
            if (!boxes[i].isSelected()) {
                for (JL ex : d)
                    set(ex, x, y, 0, 0, 0, 0);
                continue;
            }

            int posX = 50;
            posY += 50;
            for (int j = 0; j < d.length; j++) {
                set(d[j], x, y, posX, posY, j == 0 ? 200 : width, 50);
                if (j == 0)
                    posX += 200;
                else
                    posX += width;
            }
        }

        for (int i = 0; i < unit.length; i++) {
            JL[] d = unit[i];
            if (!boxes[i + main.length].isSelected()) {
                for (JL ex : d)
                    set(ex, x, y, 0, 0, 0, 0);
                continue;
            }
            int posX = 50;
            posY += 50;
            for (int j = 0; j < d.length; j++) {
                set(d[j], x, y, posX, posY, j == 0 ? 200 : width, 50);
                if (j == 0)
                    posX += 200;
                else
                    posX += width;
            }
        }

        for (int i = 0; i < enem.length; i++) {
            JL[] d = enem[i];
            if (!boxes[i + main.length + unit.length].isSelected()) {
                for (JL ex : d)
                    set(ex, x, y, 0, 0, 0, 0);
                continue;
            }
            int posX = 50;
            posY += 50;
            for (int j = 0; j < d.length; j++) {
                set(d[j], x, y, posX, posY, j == 0 ? 200 : width, 50);
                if (j == 0)
                    posX += 200;
                else
                    posX += width;
            }
        }
    }
}
