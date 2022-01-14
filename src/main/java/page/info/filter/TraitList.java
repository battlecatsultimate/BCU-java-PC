package page.info.filter;

import common.CommonStatic;
import common.util.unit.Trait;
import main.MainBCU;
import utilpc.Interpret;
import utilpc.Theme;
import utilpc.UtilPC;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TraitList extends JList<Trait> {

    private static final long serialVersionUID = 1L;

    public TraitList() {
        if (MainBCU.nimbus)
            setSelectionBackground(MainBCU.light ? Theme.LIGHT.NIMBUS_SELECT_BG : Theme.DARK.NIMBUS_SELECT_BG);
    }

    public void setTraitIcons() {
        setCellRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;
            @Override
            public Component getListCellRendererComponent(JList<?> l, Object o, int ind, boolean s, boolean f) {
                JLabel jl = (JLabel) super.getListCellRendererComponent(l, o, ind, s, f);
                Trait trait = (Trait)o;
                if (trait.BCTrait)
                    jl.setIcon(UtilPC.createIcon(3, ind));
                else if (trait.icon != null)
                    jl.setIcon(new ImageIcon((BufferedImage)trait.icon.getImg().bimg()));
                else
                    jl.setIcon(new ImageIcon((BufferedImage) CommonStatic.getBCAssets().dummyTrait.getImg().bimg()));
                return jl;
            }
        });
    }
}