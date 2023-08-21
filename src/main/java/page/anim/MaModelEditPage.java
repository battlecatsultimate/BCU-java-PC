package page.anim;

import common.CommonStatic;
import common.system.P;
import common.util.anim.*;
import page.JBTN;
import page.MenuBarHandler;
import page.Page;
import page.support.AnimTreeRenderer;
import page.support.TreeNodeExpander;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Arrays;

public class MaModelEditPage extends Page implements AbEditPage {

	private static final long serialVersionUID = 1L;

	private static final double res = 0.95;

	private final JBTN back = new JBTN(0, "back");
	private final JTree jlt = new JTree();
	private final AnimGroupTree agt = new AnimGroupTree(jlt);
	private final JScrollPane jspu = new JScrollPane(jlt);
	private final JList<String> jlp = new JList<>();
	private final JScrollPane jspp = new JScrollPane(jlp);
	private final JTree jtr = new JTree();
	private final JScrollPane jsptr = new JScrollPane(jtr);
	private final MaModelEditTable mmet = new MaModelEditTable(this);
	private final JScrollPane jspmm = new JScrollPane(mmet);
	private final SpriteBox sb = new SpriteBox(this);
	private final ModelBox mb = ModelBox.getInstance();
	private final JBTN revt = new JBTN(0, "revt");
	private final JBTN addl = new JBTN(0, "addl");
	private final JBTN reml = new JBTN(0, "reml");
	private final JBTN rema = new JBTN(0, "rema");
	private final JBTN sort = new JBTN(0, "sort");
	private final JBTN camres = new JBTN(0, "rescam");
	private final JBTN zomres = new JBTN(0, "reszom");
	private final JBTN zenm = new JBTN(0, "zenmode");
	private final EditHead aep;
	private Point p = null;
	private MMTree mmt;
	boolean dragged = false;

	public MaModelEditPage(Page p) {
		super(p);

		aep = new EditHead(this, 2);
		ini();
		resized(true);
		agt.renewNodes();
	}

	public MaModelEditPage(Page p, EditHead bar) {
		super(p);

		aep = bar;
		ini();
		resized(true);
		agt.renewNodes();
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	public void callBack(Object obj) {
		change(obj, o -> {
			if (o instanceof int[]) {
				int[] rs = (int[]) o;
				mmet.setRowSelectionInterval(rs[0], rs[1]);
				setB(rs[0]);
			}
			if (mb.getEntity() != null)
				mb.getEntity().organize();
			if (o instanceof SpriteBox && mmet.anim != null) {
				String[] name = new String[mmet.anim.imgcut.n];
				for (int i = 0; i < name.length; i++)
					name[i] = i + " " + mmet.anim.imgcut.strs[i];
				jlp.setListData(name);

				if (sb.sele >= 0) {
					jlp.getSelectionModel().setSelectionInterval(sb.sele, sb.sele);
					int[] selected = mmet.getSelectedRows();
					int[][] cells = mmet.mm.parts;
					for (int i : selected)
						cells[i][2] = sb.sele;
					mmet.anim.unSave("mamodel sprite select");
				} else {
					jlp.clearSelection();
				}
			}
			setTree(mmet.anim);
		});

	}

	private void selectAnimNode(AnimCE ac) {
		DefaultMutableTreeNode selectedNode = agt.findAnimNode(ac, null);

		if(selectedNode != null) {
			agt.expandCurrentAnimNode(selectedNode);
			jlt.setSelectionPath(new TreePath(selectedNode.getPath()));
		} else {
			jlt.clearSelection();
		}
	}


	@Override
	public void setSelection(AnimCE anim) {
		change(anim, ac -> {
			selectAnimNode(ac);
			setA(ac);
		});
	}

	private P realScale(int[] part, boolean ignoreFirst) { // this is kinda finicky, but it works enough
		P scale = ignoreFirst ? new P(1.0, 1.0) : new P(part[8] / 1000.0, part[9] / 1000.0);
		if (part[0] != -1)
			scale.times(realScale(mmet.mm.parts[part[0]], false));
		return scale;
	}

	private int realAngle(int[] part, boolean ignoreFirst) {
		int angle = ignoreFirst ? 0 : part[10];
		if (part[0] != -1)
			angle += realAngle(mmet.mm.parts[part[0]], false);
		return angle;
	}

	@Override
	protected void mouseDragged(MouseEvent e) {
		if (p == null)
			return;
		int[] rows = mmet.getSelectedRows();
		if (rows.length == 0 || e.isShiftDown()) {
			mb.ori.x += p.x - e.getX();
			mb.ori.y += p.y - e.getY();
			p = e.getPoint();
		} else {
			dragged = true;
			int[][] parts = mmet.mm.parts;
			Point p0 = mb.getPoint(p);
			Point p1 = mb.getPoint(p = e.getPoint());
			int modifiers = e.getModifiers();
			int modifier = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
			boolean isCtrlDown = (modifiers & modifier) != 0; // note: do NOT use for right mouse check
			int[] part;

			if (SwingUtilities.isRightMouseButton(e)) {
				for (int i : rows) {
					part = parts[i];
					int x = getRootPane().getWidth();
					int y = getRootPane().getHeight() - MenuBarHandler.getBar().getHeight();
					Point p2 = mb.getPoint(new Point(size(x, y, 400), size(x, y, 250))); // pivot placeholder
					double sA = Math.atan2(p0.y - p2.y, p0.x - p2.x);
					double sB = Math.atan2(p1.y - p2.y, p1.x - p2.x);
					part[10] += (sB - sA) * 1800 / Math.PI;
					part[10] %= 3600;
				}
			} else {
				if (isCtrlDown) {
					for (int i : rows) {
						part = parts[i];
						P scale = realScale(part, false);
						double angle = realAngle(part, false) / 1800.0 * Math.PI; // TODO adjust speed according to angle
						double sin = Math.sin(angle);
						double cos = Math.cos(angle);
						int x = i != 0 ? p0.x - p1.x : p1.x - p0.x;
						int y = i != 0 ? p0.y - p1.y : p1.y - p0.y;
						part[6] += ((x * cos) + (y * sin)) / scale.x;
						part[7] += ((y * cos) - (x * sin)) / scale.y;
					}
				} else {
					for (int i : rows) {
						part = parts[i];
						P scale = realScale(part, true);
						double angle = realAngle(part, true) / 1800.0 * Math.PI;
						double sin = Math.sin(angle);
						double cos = Math.cos(angle);
						int x = p1.x - p0.x;
						int y = p1.y - p0.y;
						part[4] += ((x * cos) + (y * sin)) / (scale.x);
						part[5] += ((y * cos) - (x * sin)) / (scale.y);
					}
				}
			}
			mb.getEntity().organize();
		}
	}

	@Override
	protected void mousePressed(MouseEvent e) {
		if (!(e.getSource() instanceof ModelBox))
			return;
		p = e.getPoint();
	}

	@Override
	protected void mouseReleased(MouseEvent e) {
		p = null;
		if (dragged) {
			mmet.anim.unSave("mamodel drag");
			dragged = false;
		}
	}

	@Override
	protected void mouseWheel(MouseEvent e) {
		if (!(e.getSource() instanceof ModelBox))
			return;
		MouseWheelEvent mwe = (MouseWheelEvent) e;
		double d = mwe.getPreciseWheelRotation();
		mb.setSiz(mb.getSiz() * Math.pow(res, d));
	}

	@Override
	protected void renew() {
		change(this, page -> {
			TreePath path = jlt.getSelectionPath();

			if(path == null)
				return;

			if(!(path.getLastPathComponent() instanceof DefaultMutableTreeNode))
				return;

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

			if(!(node.getUserObject() instanceof AnimCE))
				return;

			AnimCE da = (AnimCE) node.getUserObject();

			if (aep.focus == null) {
				agt.renewNodes();
			} else {
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("Animation");

				root.add(new DefaultMutableTreeNode(aep.focus));

				jlt.setModel(new DefaultTreeModel(root));
			}
			if (da != null) {
				int row = mmet.getSelectedRow();
				setA(da);
				selectAnimNode(da);
				if (row >= 0 && row < mmet.mm.parts.length) {
					setB(row);
					mmet.setRowSelectionInterval(row, row);
				}
			} else
				setA(null);
			callBack(null);
		});
	}

	@Override
	public synchronized void timer(int t) {
		resized(false);
		sb.paint(sb.getGraphics());
		mb.draw();
	}

	@Override
	protected synchronized void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(aep, x, y, 750, 0, 1500, 50);
		set(back, x, y, 0, 0, 200, 50);

		if (aep.isZenMode()) {
			set(jspu, x, y, 50, 100, 300, 500);
			set(camres, x, y, 350, 50, 200, 50);
			set(zomres, x, y, 560, 50, 200, 50);
			set((Component) mb, x, y, 350, 100, 750, 450);
			set(jspp, x, y, 1100, 100, 300, 450);
			set(sb, x, y, 1400, 100, 850, 450);

			set(jsptr, x, y, 50, 600, 300, 650);
			set(jspmm, x, y, 350, 600, 1900, 650);

			set(addl, x, y, 350, 550, 200, 50);
			set(reml, x, y, 550, 550, 200, 50);
			set(rema, x, y, 750, 550, 200, 50);
			set(sort, x, y, 1850, 550, 200, 50);
			set(revt, x, y, 2050, 550, 200, 50);
		} else {
			set(jspu, x, y, 50, 100, 300, 500);
			set(camres, x, y, 350, 50, 200, 50);
			set(zomres, x, y, 560, 50, 200, 50);
			set((Component) mb, x, y, 350, 100, 750, 450);
			set(jspp, x, y, 1100, 100, 300, 450);
			set(sb, x, y, 1400, 100, 850, 450);

			set(jsptr, x, y, 50, 600, 300, 650);
			set(jspmm, x, y, 350, 600, 1900, 650);

			set(addl, x, y, 350, 550, 200, 50);
			set(reml, x, y, 550, 550, 200, 50);
			set(rema, x, y, 750, 550, 200, 50);
			set(sort, x, y, 1850, 550, 200, 50);
			set(revt, x, y, 2050, 550, 200, 50);
		}
		SwingUtilities.invokeLater(() -> jlt.setUI(new TreeNodeExpander(jlt)));
		aep.componentResized(x, y);
		mmet.setRowHeight(size(x, y, 50));
	}

	protected void addLine() {
		change(0, o -> {
			int ind = Math.max(1, mmet.getSelectedRow() + 1);
			MaModel mm = mmet.mm;
			int[] inds = new int[mm.n];
			for (int i = 0; i < mm.n; i++)
				inds[i] = i < ind ? i : i + 1;
			mmet.anim.reorderModel(inds);
			mm.n++;
			int[] move = new int[mm.n];
			for (int i = 0; i < mm.n; i++)
				move[i] = i < ind ? i : i - 1;
			mm.reorder(move);
			int[] newl = new int[14];
			newl[2] = Math.max(jlp.getSelectedIndex(), 0);
			newl[8] = newl[9] = newl[11] = 1000;
			mm.parts[ind] = newl;
			for (MaAnim ma : mmet.anim.anims)
				for (Part part : ma.parts)
					if (part.ints[1] == 0)
						for (int[] ints : part.moves)
							if (ints[1] > ind)
								ints[1]++;
			mmet.anim.unSave("mamodel add line");
			callBack(null);
			resized(true);
			mmet.setRowSelectionInterval(ind, ind);
			setB(ind);
			int h = mmet.getRowHeight();
			mmet.scrollRectToVisible(new Rectangle(0, h * ind, 1, h));
		});
	}

	private void addListeners$0() {

		back.setLnr(x -> changePanel(getFront()));

		camres.setLnr(x -> {
			mb.ori.x = 0;
			mb.ori.y = 0;
		});

		zomres.setLnr(x -> mb.setSiz(0.5));

		jlt.addTreeSelectionListener(a -> {
			if(isAdj())
				return;

			TreePath path = jlt.getSelectionPath();

			if(path == null || !(path.getLastPathComponent() instanceof DefaultMutableTreeNode))
				return;

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

			if(!(node.getUserObject() instanceof AnimCE))
				return;

			change((AnimCE) node.getUserObject(), this::setA);
		});

		jlp.addListSelectionListener(arg0 -> {
			if (isAdj() || arg0.getValueIsAdjusting())
				return;
			sb.setSprite(jlp.getSelectedIndex(), false);
		}); // TODO: fix when selecting multiple lines >:(

		ListSelectionModel lsm = mmet.getSelectionModel();

		lsm.addListSelectionListener(arg0 -> {
			if (isAdj() || lsm.getValueIsAdjusting())
				return;
			change(lsm.getLeadSelectionIndex(), this::setB);
		});

		addl.setLnr(x -> addLine());

		reml.setLnr(x -> removeLine());

		rema.setLnr(x -> removeTree());

	}

	private void addListeners$1() {

		revt.setLnr(x -> {
			mmet.anim.revert();
			callBack(null);
		});

		jtr.addTreeSelectionListener(arg0 -> {
			if (isAdj())
				return;
			Object o = jtr.getLastSelectedPathComponent();
			if (o == null)
				return;
			String str = o.toString();
			int ind = CommonStatic.parseIntN(str.split(" - ")[0]);
			if (ind == -1)
				return;
			setB(ind);
		});

		sort.addActionListener(new ActionListener() {

			private int p = 0;
			private int[] move, inds;
			private int[][] parts;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				p = 0;
				parts = mmet.mm.parts;
				int n = parts.length;
				move = new int[n];
				inds = new int[n];
				for (int i = 0; i < parts.length; i++)
					if (parts[i][0] == -1)
						add(i);
				mmet.anim.reorderModel(inds);
				mmet.mm.reorder(move);
				mmet.anim.unSave("sort");
			}

			private void add(int n) {
				inds[n] = p;
				move[p++] = n;
				for (int i = 0; i < parts.length; i++)
					if (parts[i][0] == n)
						add(i);
			}

		});

	}

	private void ini() {
		add(aep);
		add(back);
		add(camres);
		add(zomres);
		add(jspu);
		add(jspp);
		add(jspmm);
		add(revt);
		add(addl);
		add(reml);
		add(rema);
		add(jsptr);
		add(sort);
		add(sb);
		add((Canvas) mb);
		jlt.setCellRenderer(new AnimTreeRenderer());
		SwingUtilities.invokeLater(() -> jlt.setUI(new TreeNodeExpander(jlt)));
		jtr.setExpandsSelectedPaths(true);
		reml.setForeground(Color.RED);
		rema.setForeground(Color.RED);
		setA(null);
		addListeners$0();
		addListeners$1();
	}

	private void removeLine() {
		int[] rows = mmet.getSelectedRows();
		if (rows.length == 0)
			return;
		if (rows[0] == 0)
			rows = Arrays.copyOfRange(rows, 1, rows.length);
		change(rows, row -> {
			MaModel mm = mmet.mm;
			int[][] data = mm.parts;
			mm.n -= row.length;
			int[] inds = new int[data.length];
			int[] move = new int[mm.n];
			for (int j : row)
				data[j] = null;
			int ind = 0;
			for (int i = 0; i < data.length; i++)
				if (data[i] != null) {
					move[ind] = i;
					inds[i] = ind;
					ind++;
				} else
					inds[i] = -1;
			for (MaAnim ma : mmet.anim.anims)
				for (Part part : ma.parts)
					if (part.ints[1] == 0)
						for (int[] ints : part.moves)
							if (ints[1] > ind)
								ints[1]--;

			mmet.anim.reorderModel(inds);
			mm.reorder(move);
			mmet.anim.unSave("mamodel remove line");
			callBack(null);
			ind = row[0];
			if (ind >= mm.n)
				ind--;
			mmet.setRowSelectionInterval(ind, ind);
			setB(ind);
		});
	}

	private void removeTree() {
		change(0, o -> {
			MaModel mm = mmet.mm;
			int[] rows = mmet.getSelectedRows();

			if (rows[0] == 0)
				return;

			boolean[] bs = new boolean[mm.n];
			int total = rows.length;
			for (int ind : rows)
				bs[ind] = true;
			total += mm.getChild(bs);
			mm.clearAnim(bs, mmet.anim.anims);
			int[] inds = new int[mm.n];
			int[] move = new int[mm.n - total];
			int j = 0;
			for (int i = 0; i < mm.n; i++)
				if (!bs[i]) {
					move[j] = i;
					inds[i] = j;
					j++;
				}
			mmet.anim.reorderModel(inds);
			mm.n -= total;
			mm.reorder(move);
			mmet.anim.unSave("mamodel remove tree");
			callBack(null);
			int ind = rows[0];
			if (ind >= mm.n)
				ind = mm.n - 1;
			if (ind >= 0)
				mmet.setRowSelectionInterval(ind, ind);
			setB(ind);
		});
	}

	private void setA(AnimCE anim) {
		aep.setAnim(anim);
		setTree(anim);
		addl.setEnabled(anim != null);
		sort.setEnabled(anim != null);
		revt.setEnabled(anim != null);
		if (anim == null) {
			mmet.setMaModel(null);
			mb.setEntity(null);
			sb.setAnim(null);
			jlp.setListData(new String[0]);
			setB(-1);
			return;
		}
		mmet.setMaModel(anim);
		mb.setEntity(new EAnimS(anim, anim.mamodel));
		ImgCut ic = anim.imgcut;
		String[] name = new String[ic.n];
		for (int i = 0; i < ic.n; i++)
			name[i] = i + " " + ic.strs[i];
		jlp.setListData(name);
		sb.setAnim(anim);
		mmet.clearSelection();
		setB(-1);
	}

	private void setB(int ind) {
		if (mb.getEntity() != null)
			mb.getEntity().sele = ind;
		reml.setEnabled(ind != -1);
		rema.setEnabled(ind != -1);
		if (ind < 0 || mmet.mm == null)
			return;
		if (mmet.getSelectedRow() != ind)
			change(ind, i -> {
				mmet.setRowSelectionInterval(i, i);
				mmet.scrollRectToVisible(mmet.getCellRect(i, 0, true));
			});
		if (mmt != null)
			change(ind, i -> mmt.select(i));
		int val = mmet.mm.parts[ind][2];
		jlp.setSelectedIndex(val);
		for (int row : mmet.getSelectedRows()) {
			for (int[] ints : mmet.mm.parts)
				if (ints[0] == row) {
					reml.setEnabled(false);
					reml.setToolTipText("part is parent of another");
				}
			for (MaAnim ma : mmet.anim.anims)
				for (Part p : ma.parts)
					if (p.ints[0] == row) {
						reml.setEnabled(false);
						reml.setToolTipText("part is used in animation");
					}
		}
	}

	private void setTree(AnimCE dat) {
		change(dat, anim -> {
			if (anim == null) {
				jtr.setModel(new DefaultTreeModel(null));
				mmt = null;
				return;
			}
			if (mmt == null || mmt.anim != anim)
				mmt = new MMTree(null, anim, jtr);
			mmt.renew();
		});
	}

}