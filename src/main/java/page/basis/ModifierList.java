package page.basis;

import common.battle.BasisLU;
import common.battle.BasisSet;
import common.util.unit.Combo;
import page.MainLocale;
import utilpc.Interpret;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModifierList extends JList<Object> {
    private BasisSet lineup;
    private List<Combo> combos;

    private static final long serialVersionUID = 1L;

    protected ModifierList() {
        setCellRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> l, Object o, int ind, boolean s, boolean f) {
                JLabel jl = (JLabel) super.getListCellRendererComponent(l, o, ind, s, f);
                if (o instanceof Combo) {
                    Combo c = (Combo) o;
                    jl.setText(Interpret.lvl[c.lv] + " Combo: " + Interpret.comboInfo(c, lineup));
                } else {
                    jl.setText(o.toString());
                    jl.setForeground(Color.BLUE);
                    if (getSelectedIndex() == ind)
                        jl.setForeground(Color.WHITE);
                }
                return jl;
            }
        });

        reset();
    }

    protected void reset() {
        if (lineup == null) {
            setListData(new Object[0]);
            return;
        }

        List<Object> list = new ArrayList<>();
        BasisLU lu = lineup.sele;
        int[] lvls = lu.nyc;
        if (lvls[1] > 0 && lu.t().deco[lvls[1] - 1] > 0)
            list.add("Lv. " + lu.t().deco[lvls[1] - 1] + " "
                    + MainLocale.getLoc(MainLocale.UTIL, "t" + (lvls[1] + 43)) + ": "
                    + Interpret.deco(lvls[1] - 1, lineup));
        if (lvls[2] > 0 && lu.t().base[lvls[2] - 1] > 0)
            list.add("Lv. " + lu.t().base[lvls[2] - 1] + " "
                    + MainLocale.getLoc(MainLocale.UTIL, "t" + (lvls[2] + 36)) + ": "
                    + Interpret.base(lvls[2] - 1, lineup));
        list.addAll(combos);

        setListData(list.toArray(new Object[0]));
    }

    protected void setComboList(List<Combo> lf) {
        combos = lf;
        reset();
    }

    public void setBasis(BasisSet b) {
        lineup = b;
        reset();
    }
}
