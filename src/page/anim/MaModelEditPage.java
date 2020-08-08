package page.anim;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

import common.CommonStatic;
import common.util.anim.AnimCE;
import common.util.anim.EAnimS;
import common.util.anim.ImgCut;
import common.util.anim.MaAnim;
import common.util.anim.MaModel;
import common.util.anim.Part;
import common.util.unit.DIYAnim;
import page.JBTN;
import page.Page;
import page.support.AnimLCR;

public class MaModelEditPage extends Page implements AbEditPage {

	private static final long serialVersionUID = 1L;

	private static final double res = 0.95;

	private final JBTN back = new JBTN(0, "back");
	private final JList<DIYAnim> jlu = new JList<>();
	private final JScrollPane jspu = new JScrollPane(jlu);
	private final JList<String> jlp = new JList<>();
	private final JScrollPane jspp = new JScrollPane(jlp);
	private final JTree jtr = new JTree();
	private final JScrollPane jsptr = new JScrollPane(jtr);
	private final MaModelEditTable mmet = new MaModelEditTable(this);
	private final JScrollPane jspmm = new JScrollPane(mmet);
	private final SpriteBox sb = new SpriteBox(this);
	private final ModelBox mb = new ModelBox();
	private final JBTN revt = new JBTN(0, "revt");
	private final JBTN addl = new JBTN(0, "addl");
	private final JBTN reml = new JBTN(0, "reml");
	private final JBTN rema = new JBTN(0, "rema");
	private final JBTN sort = new JBTN(0, "sort");
	private final EditHead aep;
	private Point p = null;
	private MMTree mmt;

	public MaModelEditPage(Page p) {
		super(p);

		aep = new EditHead(this, 2);
		ini();
		resized();
	}

	public MaModelEditPage(Page p, EditHead bar) {
		super(p);

		aep = bar;
		ini();
		resized();
	}

	@Override
	public void callBack(Object obj) {
		change(obj, o -> {
			if (o != null && o instanceof int[]) {
				int[] rs = (int[]) o;
				mmet.setRowSelectionInterval(rs[0], rs[1]);
				setB(rs[0]);
			}
			if (mb.getEnt() != null)
				mb.getEnt().organize();
			setTree(mmet.anim);
		});

	}

	@Override
	public void setSelection(DIYAnim anim) {
		change(anim, ac -> {
			jlu.setSelectedValue(ac, true);
			setA(ac);
		});
	}

	@Override
	protected void mouseDragged(MouseEvent e) {
		if (p == null)
			return;
		mb.ori.x += p.x - e.getX();
		mb.ori.y += p.y - e.getY();
		p = e.getPoint();
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
	}

	@Override
	protected void mouseWheel(MouseEvent e) {
		if (!(e.getSource() instanceof ModelBox))
			return;
		MouseWheelEvent mwe = (MouseWheelEvent) e;
		double d = mwe.getPreciseWheelRotation();
		mb.siz *= Math.pow(res, d);
	}

	@Override
	protected void renew() {
		change(this, page -> {
			DIYAnim da = jlu.getSelectedValue();
			Vector<DIYAnim> vec = new Vector<>();
			if (aep.focus == null)
				vec.addAll(DIYAnim.map.values());
			else
				vec.add(aep.focus);
			jlu.setListData(vec);
			if (da != null && vec.contains(da)) {
				int row = mmet.getSelectedRow();
				setA(da);
				jlu.setSelectedValue(da, true);
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
	protected synchronized void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(aep, x, y, 550, 0, 1750, 50);
		set(back, x, y, 0, 0, 200, 50);
		set(jsptr, x, y, 0, 550, 300, 750);
		set(jspmm, x, y, 300, 550, 2000, 750);
		set(jspu, x, y, 0, 50, 300, 500);
		set(mb, x, y, 300, 50, 700, 500);
		set(jspp, x, y, 1000, 50, 300, 500);
		set(sb, x, y, 1300, 50, 950, 400);
		set(sort, x, y, 1300, 500, 200, 50);
		set(revt, x, y, 1500, 500, 200, 50);
		set(addl, x, y, 1700, 500, 200, 50);
		set(reml, x, y, 1900, 500, 200, 50);
		set(rema, x, y, 2100, 500, 200, 50);
		aep.componentResized(x, y);
		mmet.setRowHeight(size(x, y, 50));
		sb.paint(sb.getGraphics());
		mb.paint(mb.getGraphics());
	}

	private void addLine() {
		change(0, o -> {
			int ind = mmet.getSelectedRow() + 1;
			if (ind == 0)
				ind++;
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
			newl[8] = newl[9] = newl[11] = 1000;
			mm.parts[ind] = newl;
			mmet.anim.unSave("mamodel add line");
			callBack(null);
			resized();
			mmet.setRowSelectionInterval(ind, ind);
			setB(ind);
			int h = mmet.getRowHeight();
			mmet.scrollRectToVisible(new Rectangle(0, h * ind, 1, h));
		});
	}

	private void addListeners$0() {

		back.setLnr(x -> changePanel(getFront()));

		jlu.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (isAdj() || jlu.getValueIsAdjusting())
					return;
				change(jlu.getSelectedValue(), val -> setA(val));
			}

		});

		jlp.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				sb.sele = jlp.getSelectedIndex();
			}

		});

		ListSelectionModel lsm = mmet.getSelectionModel();

		lsm.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (isAdj() || lsm.getValueIsAdjusting())
					return;
				change(lsm.getLeadSelectionIndex(), ind -> setB(ind));
			}

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

		jtr.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
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
			}

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
		add(mb);
		jlu.setCellRenderer(new AnimLCR());
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
			for (int i = 0; i < row.length; i++)
				data[row[i]] = null;
			int ind = 0;
			for (int i = 0; i < data.length; i++)
				if (data[i] != null) {
					move[ind] = i;
					inds[i] = ind;
					ind++;
				} else
					inds[i] = -1;
			mmet.anim.reorderModel(inds);
			mm.reorder(move);
			mmet.anim.unSave("mamodel remove line");
			callBack(null);
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

	private void setA(DIYAnim da) {
		if (da != null && da.getAnimC().mismatch)
			da = null;
		aep.setAnim(da);
		setTree(da == null ? null : da.anim);
		addl.setEnabled(da != null);
		sort.setEnabled(da != null);
		revt.setEnabled(da != null);
		if (da == null) {
			mmet.setMaModel(null);
			mb.setEntity(null);
			sb.setAnim(null);
			jlp.setListData(new String[0]);
			setB(-1);
			return;
		}
		AnimCE anim = da.getAnimC();
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
		if (mb.getEnt() != null)
			mb.getEnt().sele = ind;
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
				if (ints[0] == row)
					reml.setEnabled(false);
			for (MaAnim ma : mmet.anim.anims)
				for (Part p : ma.parts)
					if (p.ints[0] == row)
						reml.setEnabled(false);
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