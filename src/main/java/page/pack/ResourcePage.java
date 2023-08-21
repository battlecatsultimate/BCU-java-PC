package page.pack;

import common.system.VImg;
import common.system.files.VFile;
import common.system.files.VFileRoot;
import io.BCUWriter;
import page.JBTN;
import page.JL;
import page.Page;
import page.support.Exporter;
import utilpc.UtilPC;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.io.File;

public class ResourcePage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN rept = new JBTN(0, "extract");
	private final JLabel jln = new JLabel();
	private final JTextArea jt = new JTextArea();
	private final JTree jls = new JTree();
	private final JScrollPane jsps = new JScrollPane(jls);
	private final JScrollPane jspf = new JScrollPane(jt);
	private final JScrollPane jspi = new JScrollPane(jln);

	private final JL assetsLabel = new JL("assets");
	private final JL contentLabel = new JL("content");
	private final JL imageLabel = new JL("image");

	private VFile sel;
	private boolean changing;

	public ResourcePage(Page p) {
		super(p);

		ini();
		resized(true);
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jsps, x, y, 50, 150, 400, 800);
		set(jspf, x, y, 450, 150, 700, 800);
		set(jspi, x, y, 1150, 150, 700, 800);
		set(rept, x, y, 50, 950, 400, 50);

		set(assetsLabel, x, y, 50, 100, 400, 50);
		set(contentLabel, x, y, 450, 100, 700, 50);
		set(imageLabel, x, y, 1150, 100, 700, 50);
	}

	private void addListeners() {

		back.addActionListener(arg0 -> changePanel(getFront()));

		rept.addActionListener(arg0 -> {
			File f = new Exporter(Exporter.EXP_RES).file;
			if (f != null)
				filemove(f.getPath() + "/", sel);
		});

		jls.addTreeSelectionListener(arg0 -> {
			if (changing)
				return;
			Object obj = null;
			TreePath tp = jls.getSelectionPath();
			if (tp != null) {
				obj = tp.getLastPathComponent();
				if (obj != null)
					obj = ((DefaultMutableTreeNode) obj).getUserObject();
				sel = obj instanceof VFile ? (VFile) obj : null;
			} else {
				sel = null;
			}
			if (sel != null && sel.getName().contains(".")) {
				if (sel.getName().endsWith(".png")) {
					jln.setIcon(UtilPC.getIcon(new VImg(sel)));
					jt.setText(null);
				} else {
					jln.setIcon(null);
					StringBuilder txt = new StringBuilder();
					for (String str : sel.getData().readLine())
						txt.append(str).append("\n");
					jt.setText(txt.toString());
				}
			} else {
				jln.setIcon(null);
				jt.setText(null);
			}
			setSele();
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
		jln.setVerticalAlignment(SwingConstants.TOP);
		jt.setEditable(false);
		add(back);
		add(jsps);
		add(jspf);
		add(jspi);
		add(rept);
		add(assetsLabel);
		add(contentLabel);
		add(imageLabel);
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
