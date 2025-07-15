package page.pack;

import common.system.VImg;
import common.system.files.VFile;
import common.system.files.VFileRoot;
import io.BCUWriter;
import page.JBTN;
import page.JL;
import page.JTG;
import page.Page;
import page.support.Exporter;
import utilpc.UtilPC;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Queue;

public class ResourcePage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN rept = new JBTN(0, "extract");
	private final JLabel jln = new JLabel();
	private final JTextPane jt = new JTextPane();
	private final JTree jls = new JTree();
	private final JScrollPane jsps = new JScrollPane(jls);
	private final JScrollPane jspf = new JScrollPane(jt);
	private final JScrollPane jspi = new JScrollPane(jln);
	private final JTG tabl = new JTG(0, "table");

	private final JL assetsLabel = new JL("assets");
	private final JL contentLabel = new JL("content");
	private final JL imageLabel = new JL("image");

	private VFile sel;
	private boolean changing;

	public ResourcePage(Page p) {
		super(p);

		ini();
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
		set(tabl, x, y, 450, 950, 200, 50);
		set(rept, x, y, 50, 950, 400, 50);

		set(assetsLabel, x, y, 50, 100, 400, 50);

		if (sel != null && sel.getName().endsWith(".png")) {
			set(contentLabel, x, y, 450, 100, 0, 0);
			set(jspf, x, y, 450, 150, 0, 0);
			set(imageLabel, x, y, 450, 100, 1400, 50);
			set(jspi, x, y, 450, 150, 1400, 800);
		} else {
			set(contentLabel, x, y, 450, 100, 1400, 50);
			set(jspf, x, y, 450, 150, 1400, 800);
			set(imageLabel, x, y, 450, 100, 0, 0);
			set(jspi, x, y, 450, 150, 0, 0);
		}

		jspf.getHorizontalScrollBar().setUnitIncrement(size(x, y, 15));
		jspi.getHorizontalScrollBar().setUnitIncrement(size(x, y, 20));
		jspi.getVerticalScrollBar().setUnitIncrement(size(x, y, 20));
	}

	private void addListeners() {

		back.addActionListener(arg0 -> changePanel(getFront()));

		rept.addActionListener(arg0 -> {
			File f = new Exporter(Exporter.EXP_RES).file;
			if (f != null) {
				String name = f.getName();
				if (sel.getName().contains(".")) {
					String suffix = "." + sel.getName().split("\\.")[1];
					if (!name.endsWith(suffix))
						name += suffix;
				}
				filemove(f.getParentFile() + "/", sel, name);
			}
		});

		jls.addTreeSelectionListener(arg0 -> {
			if (changing)
				return;
			changing = true;
			Object obj;
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
					renderText(null);
				}
			} else {
				jln.setIcon(null);
				jt.setText(null);
			}
			setSele();
			changing = false;
		});

		tabl.setLnr(this::renderText);
	}

	private void renderText(ActionEvent e) {
		if (sel == null)
			return;
		if (tabl.isSelected() && sel.getName().endsWith(".csv")) { // todo: make table reading better
			StringBuilder txt = new StringBuilder("<html><table border=\"1\"><tr>");
			Queue<String> queue = sel.getData().readLine();

			for (String header : queue.poll().split(", *"))
				txt.append("<th>").append(header).append("</th>");
			txt.append("</tr><tr>");

			for (String str : queue) {
				txt.append("<tr><td>")
						.append(String.join("</td><td>", str.split(", *")))
						.append("</td></tr>");
			}

			jt.setText(txt.append("</table></html>").toString());
		} else {
			StringBuilder txt = new StringBuilder();
			for (String str : sel.getData().readLine())
				txt.append(str).append("\n");
			jt.setText(txt.toString());
		}
	}

	private void addTree(DefaultMutableTreeNode par, VFile vf) {
		for (VFile c : vf.list()) {
			DefaultMutableTreeNode cur = new DefaultMutableTreeNode(c);
			par.add(cur);
			if (c.list() != null)
				addTree(cur, c);
		}
	}

	private void filemove(String dst, VFile src, String name) {
		if (src.list() != null)
			for (VFile c : src.list())
				filemove(dst + src.getName() + "/", c, c.getName());
		else
			BCUWriter.writeBytes(src.getData().getBytes(), dst + name);
	}

	private void ini() {
		jln.setVerticalAlignment(SwingConstants.TOP);
		jt.setEditable(false);
		tabl.setEnabled(false);
		add(back);
		add(jsps);
		add(jspf);
		add(jspi);
		add(rept);
		add(tabl);
		add(assetsLabel);
		add(contentLabel);
		add(imageLabel);
		setSele();
		setTree(VFile.getBCFileTree());
		addListeners();
		jt.setContentType("text/html");
	}

	private void setSele() {
		rept.setEnabled(sel != null);
		tabl.setEnabled(sel != null && sel.getName().endsWith(".csv"));
		fireDimensionChanged();
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
