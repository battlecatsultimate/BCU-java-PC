package page.anim;

import common.util.AnimGroup;
import common.util.anim.AnimCE;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.Enumeration;

public class AnimGroupTree implements TreeExpansionListener {
    private final JTree animTree;

    private final DefaultMutableTreeNode nodes = new DefaultMutableTreeNode("Animation");

    public AnimGroupTree(JTree animTree) {
        this.animTree = animTree;
    }

    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        Object o = event.getPath().getLastPathComponent();

        if(!(o instanceof DefaultMutableTreeNode))
            return;

        Object userObject = ((DefaultMutableTreeNode) o).getUserObject();

        if(userObject instanceof String) {
            System.out.println("OHOH");
        }
    }

    @Override
    public void treeCollapsed(TreeExpansionEvent event) {
        Object o = event.getPath().getLastPathComponent();

        if(!(o instanceof DefaultMutableTreeNode))
            return;

        Object userObject = ((DefaultMutableTreeNode) o).getUserObject();

        if(userObject instanceof String) {
            System.out.println("OHOH");
        }
    }

    public void renewNodes() {
        nodes.removeAllChildren();

        ArrayList<AnimCE> baseGroup = AnimGroup.workspaceGroup.groups.get("");

        if(baseGroup != null && !baseGroup.isEmpty()) {
            for(AnimCE anim : baseGroup) {
                DefaultMutableTreeNode animNode = new DefaultMutableTreeNode(anim);

                nodes.add(animNode);
            }
        }

        for(String key : AnimGroup.workspaceGroup.groups.keySet()) {
            if(key.equals(""))
                continue;

            DefaultMutableTreeNode container = new DefaultMutableTreeNode(key);

            ArrayList<AnimCE> anims = AnimGroup.workspaceGroup.groups.get(key);

            if(anims == null)
                continue;

            for(AnimCE anim : anims) {
                DefaultMutableTreeNode animNode = new DefaultMutableTreeNode(anim);

                container.add(animNode);
            }

            nodes.add(container);
        }

        animTree.setModel(new DefaultTreeModel(nodes));
    }

    public DefaultMutableTreeNode findAnimNode(AnimCE anim) {
        DefaultMutableTreeNode node;

        Enumeration<?> e = nodes.breadthFirstEnumeration();

        while(e.hasMoreElements()) {
            node = (DefaultMutableTreeNode) e.nextElement();

            if(node.getUserObject() instanceof AnimCE && node.getUserObject() == anim)
                return node;
        }

        return null;
    }

    public DefaultMutableTreeNode getVeryFirstAnimNode() {
        DefaultMutableTreeNode node;

        Enumeration<?> e = nodes.breadthFirstEnumeration();

        while(e.hasMoreElements()) {
            node = (DefaultMutableTreeNode) e.nextElement();

            if(node.getUserObject() instanceof AnimCE)
                return node;
        }

        return null;
    }
}
