package page.basis;

import common.battle.BasisLU;
import common.battle.BasisSet;
import common.pack.Identifier;
import common.pack.UserProfile;
import common.util.unit.Combo;
import main.MainBCU;
import page.MainLocale;
import utilpc.Interpret;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModifierList extends JList<Object> {
    private BasisSet lineup;
    private List<Combo> combos;
    private Set<Integer> banned;

    private static final long serialVersionUID = 1L;

    static {
        ComboListTable.redefine();
    }

    public ModifierList() { // todo: include orb modifiers
        super();
        setCellRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> l, Object o, int ind, boolean s, boolean f) {
                JLabel jl = (JLabel) super.getListCellRendererComponent(l, o, ind, s, f);
                if (o instanceof Combo) {
                    Combo c = (Combo) o;
                    if (banned != null && banned.contains(c.type) || (!c.id.pack.equals(Identifier.DEF) && !UserProfile.getUserPack(c.id.pack).useCombos)) {
                        jl.setText("<html><strike>" + Interpret.lvl[c.lv] + " Combo: " + Interpret.comboInfo(c, lineup, true) + "</strike></html>");
                        jl.setForeground(getSelectedIndex() == ind ? Color.WHITE : Color.GRAY);
                        jl.setToolTipText(null);
                    } else {
                        jl.setText(Interpret.lvl[c.lv] + " Combo: " + Interpret.comboInfo(c, lineup, true));
                        jl.setToolTipText(c.group != null ? Interpret.getGroupTooltip(c.group) : null);
                    }
                } else {
                    jl.setText(o.toString());
                    jl.setForeground(getSelectedIndex() == ind ? Color.WHITE : MainBCU.light ? Color.BLUE : Color.CYAN);
                    jl.setToolTipText(null);
                }
                return jl;
            }
        });

        reset();
    }

    protected void reset() {
        List<Object> list = new ArrayList<>();

        if (lineup != null) {
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
        }

        if (combos != null)
            list.addAll(combos);

        setListData(list.toArray(new Object[0]));
    }

    public void setComboList(List<Combo> lf) {
        combos = lf;
        reset();
    }

    public void setBanned(Set<Integer> lb) {
        banned = lb;
        reset();
    }

    public void setBasis(BasisSet b) {
        lineup = b;
        reset();
    }
}
