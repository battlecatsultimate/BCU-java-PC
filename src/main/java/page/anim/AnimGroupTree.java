package page.anim;

import common.util.AnimGroup;
import common.util.anim.AnimCE;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;

public class AnimGroupTree implements TreeExpansionListener {
    private final JTree animTree;
    private final HashMap<String, Boolean> groupExpanded = new HashMap<>();

    private DefaultMutableTreeNode nodes = new DefaultMutableTreeNode("Animation");

    public AnimGroupTree(JTree animTree) {
        this.animTree = animTree;
    }

    public void renewNodes() {
        nodes.removeAllChildren();

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

        ArrayList<AnimCE> baseGroup = AnimGroup.workspaceGroup.groups.get("");

        if(baseGroup != null && !baseGroup.isEmpty()) {
            for(AnimCE anim : baseGroup) {
                DefaultMutableTreeNode animNode = new DefaultMutableTreeNode(anim);

                nodes.add(animNode);
            }
        }

        animTree.setModel(new DefaultTreeModel(nodes));
    }

    public void applyNewNodes() {
        if(!(animTree.getModel().getRoot() instanceof DefaultMutableTreeNode))
            return;

        nodes = (DefaultMutableTreeNode) animTree.getModel().getRoot();

        handleAnimGroup(nodes, true);

        renewNodes();

        Enumeration<?> enumeration = nodes.children();

        while(enumeration.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement();

            if(node.getUserObject() instanceof String) {
                if(groupExpanded.containsKey((String) node.getUserObject())) {
                    animTree.expandPath(new TreePath(node.getPath()));
                }
            }
        }
    }

    public void handleAnimGroup(DefaultMutableTreeNode root, boolean initial) {
        if(initial) {
            AnimGroup.workspaceGroup.groups.clear();

            AnimGroup.workspaceGroup.groups.put("", new ArrayList<>());
        }

        Enumeration<?> enumeration = root.children();

        while(enumeration.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement();

            if(node.getUserObject() instanceof String) {
                if(node.getChildCount() > 0)
                    handleAnimGroup(node, false);
                else
                    AnimGroup.workspaceGroup.groups.put((String) node.getUserObject(), new ArrayList<>());
            } else if(node.getUserObject() instanceof AnimCE) {
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

                if(!parent.isRoot()) {
                    if(parent.getUserObject() instanceof String) {
                        String id = (String) parent.getUserObject();

                        ArrayList<AnimCE> anim = AnimGroup.workspaceGroup.groups.get(id);

                        if(anim == null)
                            anim = new ArrayList<>();

                        AnimCE a = (AnimCE) node.getUserObject();

                        if(!anim.contains(a)) {
                            a.group = id;

                            anim.add(a);

                            AnimGroup.workspaceGroup.groups.put(id, anim);
                        }
                    } else {
                        throw new IllegalStateException("Parent node must be String : "+parent.getUserObject().getClass().getName());
                    }
                } else {
                    ArrayList<AnimCE> anim = AnimGroup.workspaceGroup.groups.get("");

                    AnimCE a = (AnimCE) node.getUserObject();

                    if(!anim.contains(a)) {
                        a.group = "";

                        anim.add(a);

                        AnimGroup.workspaceGroup.groups.put("", anim);
                    }
                }
            }
        }

        for(ArrayList<AnimCE> anims : AnimGroup.workspaceGroup.groups.values()) {
            anims.sort(Comparator.comparing(a -> a.id.id));
        }
    }

    public DefaultMutableTreeNode findAnimNode(AnimCE anim, DefaultMutableTreeNode nodes) {
        DefaultMutableTreeNode node;

        if(nodes == null)
            nodes = this.nodes;

        Enumeration<?> e = nodes.breadthFirstEnumeration();

        while(e.hasMoreElements()) {
            node = (DefaultMutableTreeNode) e.nextElement();

            if(node.getUserObject() instanceof String) {
                DefaultMutableTreeNode trial = findAnimNode(anim, node);

                if(trial != null)
                    return trial;
            } else if(node.getUserObject() instanceof AnimCE && node.getUserObject() == anim)
                return node;
        }

        return null;
    }

    public void removeGroup(String groupName) {
        if(groupName.equals(""))
            return;

        groupExpanded.remove(groupName);

        ArrayList<AnimCE> anims = AnimGroup.workspaceGroup.groups.get(groupName);

        if(anims == null)
            return;

        ArrayList<AnimCE> base = AnimGroup.workspaceGroup.groups.get("");

        for(AnimCE anim : anims) {
            anim.group = "";

            base.add(anim);
        }

        base.sort(Comparator.comparing(a -> a.id.id));

        AnimGroup.workspaceGroup.groups.remove(groupName);
        AnimGroup.workspaceGroup.groups.put("", base);

        renewNodes();
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

    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();

        if(node.getUserObject() instanceof String) {
            groupExpanded.put((String) node.getUserObject(), true);
        }
    }

    @Override
    public void treeCollapsed(TreeExpansionEvent event) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();

        if(node.getUserObject() instanceof String) {
            groupExpanded.put((String) node.getUserObject(), false);
        }
    }
}