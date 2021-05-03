package page.support;

import common.system.VImg;
import common.util.AnimGroup;
import common.util.anim.AnimCE;
import main.MainBCU;
import utilpc.UtilPC;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class AnimTreeRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component comp = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        JLabel jl = (JLabel) comp;

        if(selected)
            if(MainBCU.nimbus) {
                jl.setOpaque(true);
                jl.setBorder(BorderFactory.createLineBorder(UIManager.getColor("nimbusFocus")));
                jl.setBackground(UIManager.getColor("nimbusSelectionBackground"));
            } else {
                jl.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Tree.selectionBorderColor")));
            }
        else {
            if(MainBCU.nimbus)
                jl.setOpaque(false);

            jl.setBorder(null);
        }



        if(!(value instanceof DefaultMutableTreeNode))
            return comp;

        if(((DefaultMutableTreeNode) value).getUserObject() instanceof String) {
            ArrayList<AnimCE> anims = AnimGroup.workspaceGroup.groups.get((String) ((DefaultMutableTreeNode) value).getUserObject());

            if(anims == null || anims.isEmpty()) {
                jl.setIcon(UIManager.getIcon("Tree.closedIcon"));

                return comp;
            }
        }

        if(!(((DefaultMutableTreeNode) value).getUserObject() instanceof AnimCE))
            return comp;

        jl.setIcon(null);

        AnimCE anim = (AnimCE) ((DefaultMutableTreeNode) value).getUserObject();

        if(anim != null && anim.getEdi() != null) {
            VImg edi = anim.getEdi();

            jl.setIcon(UtilPC.getIcon(edi));
        }

        return jl;
    }
}
