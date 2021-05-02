package page.support;

import common.system.VImg;
import common.util.AnimGroup;
import common.util.anim.AnimCE;
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

        if(!(value instanceof DefaultMutableTreeNode))
            return comp;

        if(((DefaultMutableTreeNode) value).getUserObject() instanceof String) {
            ArrayList<AnimCE> anims = AnimGroup.workspaceGroup.groups.get((String) ((DefaultMutableTreeNode) value).getUserObject());

            if(anims == null || anims.isEmpty()) {
                JLabel jl = (JLabel) comp;

                jl.setIcon(UIManager.getIcon("Tree.closedIcon"));

                return comp;
            }
        }

        if(!(((DefaultMutableTreeNode) value).getUserObject() instanceof AnimCE))
            return comp;

        JLabel jl = (JLabel) comp;

        jl.setIcon(null);

        AnimCE anim = (AnimCE) ((DefaultMutableTreeNode) value).getUserObject();

        if(anim != null && anim.getEdi() != null) {
            VImg edi = anim.getEdi();

            jl.setIcon(UtilPC.getIcon(edi));
        }

        return jl;
    }
}
