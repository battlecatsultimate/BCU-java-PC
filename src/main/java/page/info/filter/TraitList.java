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
import java.util.Vector;

public class TraitList extends JList<Trait> {

    private static final long serialVersionUID = 1L;
    public final Vector<Trait> list = new Vector<>();
    public final boolean id;

    //editing is used to set whether the page using this is Trait Edit Page or not. May be used to add BC Traits to list at some point
    public TraitList(boolean editing) {
        id = editing;

        if (MainBCU.nimbus)
            setSelectionBackground(MainBCU.light ? Theme.LIGHT.NIMBUS_SELECT_BG : Theme.DARK.NIMBUS_SELECT_BG);

        setCellRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;
            @Override
            public Component getListCellRendererComponent(JList<?> l, Object o, int ind, boolean s, boolean f) {
                JLabel jl = (JLabel) super.getListCellRendererComponent(l, o, ind, s, f);
                Trait trait = (Trait)o;
                if (trait.BCTrait) {
                    jl.setText(Interpret.TRAIT[trait.id.id]);
                    jl.setIcon(UtilPC.createIcon(3, trait.id.id));
                } else {
                    if (!editing)
                        jl.setText(trait.name);
                    if (trait.icon != null)
                        jl.setIcon(new ImageIcon((BufferedImage)trait.icon.getImg().bimg()));
                    else
                        jl.setIcon(new ImageIcon((BufferedImage) CommonStatic.getBCAssets().dummyTrait.getImg().bimg()));
                }
                return jl;
            }
        });
    }

    public void setListData() {
        setListData(list);
    }
}