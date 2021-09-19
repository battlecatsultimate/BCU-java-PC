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
import java.util.Arrays;

public class ComparePage extends Page {

    private static final long serialVersionUID = 1L;

    private final JL[] names = new JL[2];

    private final JL[][] main = new JL[3][3]; // comparing any two entities
    private final JL[][] unit = new JL[2][3]; // comparing for two units
    // private final JL[][] enem = new JL[1][1]; // comparing for two enemies

    private final JCB[] boxes = new JCB[main.length + unit.length];

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

        main[0][0].setText("HP");
        main[1][0].setText("atk");
        main[2][0].setText("dps");
        boxes[0].setText("HP");
        boxes[1].setText("atk");
        boxes[2].setText("dps");

        unit[0][0].setText("CD");
        unit[1][0].setText(MainLocale.INFO, "price");
        boxes[3].setText("CD");
        boxes[4].setText(MainLocale.INFO, "price");

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

            if (m instanceof MaskEnemy) {
                atk = m.allAtk();
                for (int[] atkDatum : atkData) {
                    if (atkString.length() > 0)
                        atkString.append(" / ");

                    int a = Math.round(atkDatum[0]);
                    atkString.append(a);
                }

                main[0][index].setText(hp + "");
            } else if (m instanceof MaskUnit) {
                Form f = ((MaskUnit) m).getPack();
                int[] multi = f.unit.getPrefLvs();
                EForm ef = new EForm(f, multi);

                double mul = f.unit.lv.getMult(multi[0]);
                double atkLv = b.t().getAtkMulti();
                double defLv = b.t().getDefMulti();

                hp = (int) (Math.round(hp * mul) * defLv);
                if (f.getPCoin() != null)
                    hp = (int) (hp * f.getPCoin().getHPMultiplication(multi));

                for (int[] atkDatum : atkData) {
                    if (atkString.length() > 0)
                        atkString.append(" / ");

                    int a = (int) (Math.round(atkDatum[0] * mul) * atkLv);
                    if (f.getPCoin() != null)
                        a = (int) (a * f.getPCoin().getAtkMultiplication(multi));

                    atkString.append(a);
                    atk += a;
                }

                unit[0][index].setText(b.t().getFinRes(f.du.getRespawn()) + "f");
                unit[1][index].setText(ef.getPrice(1) + "");

                if (maskEntities[index % 2] instanceof MaskEnemy) {
                    MaskEnemy e = (MaskEnemy) maskEntities[index % 2];
                    int overlap = e.getType() & f.du.getType();
                    if (overlap > 0) {
                        int effective = hp;
                        if ((f.du.getAbi() & Data.AB_GOOD) > 0)
                            effective /= b.t().getGOODDEF(overlap, f.du, new Level(multi));
                        if ((f.du.getAbi() & Data.AB_RESIST) > 0)
                            effective /= b.t().getRESISTDEF(overlap, f.du, new Level(multi));
                        main[0][index].setText(hp + " (" + effective + ")");
                    }
                } else {
                    main[0][index].setText(hp + "");
                }
            }

            names[i].setIcon(UtilPC.getIcon(m.getPack().anim.getEdi()));
            names[i].setText(m.getPack().toString());

            main[1][index].setText(atkString + "");
            main[2][index].setText((int) (atk * 30.0 / m.getItv()) + "");
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

        set(sl1e, x, y, 325, 50, 200, 50);
        set(sl1u, x, y, 325, 100, 200, 50);
        set(sl2e, x, y, 650, 50, 200, 50);
        set(sl2u, x, y, 650, 100, 200, 50);

        int posY = 150;

        for (JCheckBox b : boxes) {
            posY += 50;
            set(b, x, y, 300 + ((main[0].length - 1) * 350), posY, 200, 50);
        }

        for (int i = 0; i < names.length; i++)
            set(names[i], x, y, 250 + (i * 350), 150, 350, 50);

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
                set(d[j], x, y, posX, posY, j == 0 ? 200 : 350, 50);
                if (j == 0)
                    posX += 200;
                else
                    posX += 350;
            }
        }

        if (!Arrays.stream(maskEntities).allMatch(m -> m instanceof MaskUnit)) {
            for (JL[] d : unit)
                for (JL jl : d)
                    set(jl, x, y, 0, 0, 0, 0);
        } else {
            for (int i = 0; i < unit.length; i++) {
                JL[] d = unit[i];
                if (!boxes[i].isSelected())
                    for (JL ex : d) {

                    }
                int posX = 50;
                posY += 50;
                for (int j = 0; j < d.length; j++) {
                    set(d[j], x, y, posX, posY, j == 0 ? 200 : 350, 50);
                    if (j == 0)
                        posX += 200;
                    else
                        posX += 350;
                }
            }
        }
    }
}
