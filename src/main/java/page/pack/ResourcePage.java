package page.pack;

import common.system.files.VFile;
import common.system.files.VFileRoot;
import io.BCUWriter;
import page.JBTN;
import page.Page;
import page.support.Exporter;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ResourcePage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN rept = new JBTN(0, "extract");
	private final JLabel jln = new JLabel();
	private final JTree jls = new JTree();
	private final JScrollPane jsps = new JScrollPane(jls);

	private VFile sel;
	private boolean changing;

	public ResourcePage(Page p) {
		super(p);

		ini();
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jln, x, y, 50, 50, 750, 50);
		set(jsps, x, y, 50, 100, 400, 800);
		set(rept, x, y, 500, 300, 200, 50);

	}

	private void addListeners() {

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		rept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File f = new Exporter(Exporter.EXP_RES).file;
				if (f != null)
					filemove(f.getPath() + "/", sel);
			}

		});

		jls.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				if (changing)
					return;
				Object obj = null;
				TreePath tp = jls.getSelectionPath();
				if (tp != null) {
					obj = tp.getLastPathComponent();
					if (obj != null)
						obj = ((DefaultMutableTreeNode) obj).getUserObject();
					sel = obj instanceof VFile ? (VFile) obj : null;
				} else
					sel = null;
				setSele();
			}

		});

	}

	private void addTree(DefaultMutableTreeNode par, VFile vf) {
		for (VFile c : vf.list()) {
			DefaultMutableTreeNode cur = new DefaultMutableTreeNode(c);
			par.add(cur);
			if (c.list() != null)
				addTree(cur, c);
		}
	}

	private void filemove(String dst, VFile src) {
		if (src.list() != null)
			for (VFile c : src.list())
				filemove(dst + src.getName() + "/", c);
		else
			BCUWriter.writeBytes(src.getData().getBytes(), dst + src.getName());
	}

	private void ini() {
		add(back);
		add(jsps);
		add(rept);
		add(jln);
		setSele();
		setTree(VFile.getBCFileTree());
		addListeners();
	}

	private void setSele() {
		rept.setEnabled(sel != null);
	}

	private void setTree(VFileRoot vfr) {
		if (vfr == null) {
			jls.setModel(new DefaultTreeModel(null));
			return;
		}
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("/");
		addTree(top, vfr);
		jls.setModel(new DefaultTreeModel(top));
	}

}
