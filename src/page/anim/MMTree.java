package page.anim;

import java.util.function.IntPredicate;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import common.util.anim.AnimC;
import common.util.anim.MaModel;

class MMTree implements TreeExpansionListener {

	protected final AnimC anim;
	protected final TreeCont tc;
	private final MaModel mm;
	private JTree jtr;
	private DefaultMutableTreeNode top;
	private DefaultMutableTreeNode[] data;
	private boolean adj;

	protected MMTree(TreeCont cont, AnimC am, JTree tree) {
		tc = cont;
		anim = am;
		mm = am.mamodel;
		jtr = tree;
	}

	@Override
	public void treeCollapsed(TreeExpansionEvent arg0) {
		Object n = arg0.getPath().getLastPathComponent();
		int i = indexOf(n);
		if (i < 0)
			return;
		mm.status.put(mm.parts[i], 1);
		anim.updateStatus();
		if (tc != null)
			tc.collapse();
	}

	@Override
	public void treeExpanded(TreeExpansionEvent arg0) {
		Object n = arg0.getPath().getLastPathComponent();
		int i = indexOf(n);
		if (i < 0)
			return;
		mm.status.put(mm.parts[i], 1);
		anim.updateStatus();
		if (tc != null)
			tc.expand();
	}

	protected int indexOf(Object o) {
		for (int i = 0; i < data.length; i++)
			if (data[i] == o)
				return i;
		return -1;
	}

	protected void nav(int p, IntPredicate f) {
		for (int i = 0; i < mm.n; i++)
			if (mm.parts[i][0] == p && f.test(i))
				nav(i, f);
	}

	protected void renew() {
		data = new DefaultMutableTreeNode[mm.n];
		top = new DefaultMutableTreeNode("MaModel");
		int c = 0;
		while (c < mm.n) {
			for (int i = 0; i < mm.n; i++)
				if (data[i] == null) {
					int[] line = mm.parts[i];
					DefaultMutableTreeNode pre = (line[0] == -1 ? top : data[line[0]]);
					if (pre == null)
						continue;
					data[i] = new DefaultMutableTreeNode(i + " - " + mm.strs0[i]);
					pre.add(data[i]);
					c++;
				}
		}
		jtr.setModel(new DefaultTreeModel(top));
		nav(-1, i -> {
			Integer s = mm.status.get(mm.parts[i]);
			TreePath tp = new TreePath(data[i].getPath());
			if (s == null || s == 0) {
				jtr.expandPath(tp);
				return true;
			}
			jtr.collapsePath(tp);
			return false;
		});
		jtr.addTreeExpansionListener(this);
	}

	protected void select(int i) {
		if (adj || i >= data.length || i < 0)
			return;
		TreePath tp = new TreePath(data[i].getPath());
		jtr.setSelectionPath(tp);
		jtr.scrollPathToVisible(tp);
	}

	protected void setAdjusting(boolean b) {
		adj = b;
	}

}

interface TreeCont {

	public void collapse();

	public void expand();

}
