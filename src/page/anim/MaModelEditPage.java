package page.anim;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import page.JBTN;
import page.Page;
import page.support.AnimLCR;
import util.anim.AnimC;
import util.anim.EAnimS;
import util.anim.ImgCut;
import util.anim.MaAnim;
import util.anim.MaModel;
import util.anim.Part;
import util.unit.DIYAnim;

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
	private final EditHead aep;
	private Point p = null;

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
			if (o != null && o.equals("review"))
				setTree(mmet.mm);
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
		set(revt, x, y, 1300, 500, 200, 50);
		set(addl, x, y, 1550, 500, 200, 50);
		set(reml, x, y, 1800, 500, 200, 50);
		set(rema, x, y, 2050, 500, 200, 50);
		aep.componentResized(x, y);
		mmet.setRowHeight(size(x, y, 50));
		sb.paint(sb.getGraphics());
		mb.paint(mb.getGraphics());
	}

	private void addListeners() {

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		jlu.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (isAdjusting() || jlu.getValueIsAdjusting())
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
				if (isAdjusting() || lsm.getValueIsAdjusting())
					return;
				change(lsm.getLeadSelectionIndex(), ind -> setB(ind));
			}

		});

		addl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
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
					mmet.anim.unSave();
					callBack(null);
					resized();
					lsm.setSelectionInterval(ind, ind);
					setB(ind);
					int h = mmet.getRowHeight();
					mmet.scrollRectToVisible(new Rectangle(0, h * ind, 1, h));
				});
			}

		});

		reml.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				change(0, o -> {
					MaModel mm = mmet.mm;
					int[] rows = mmet.getSelectedRows();
					int[][] data = mm.parts;
					mm.n -= rows.length;
					int[] inds = new int[data.length];
					int[] move = new int[mm.n];
					for (int i = 0; i < rows.length; i++)
						data[rows[i]] = null;
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
					mmet.anim.unSave();
					callBack(null);
					if (ind >= mm.n)
						ind--;
					lsm.setSelectionInterval(ind, ind);
					setB(ind);
				});
			}

		});

		rema.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				change(0, o -> {
					MaModel mm = mmet.mm;
					int[] rows = mmet.getSelectedRows();
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
					mmet.anim.unSave();
					callBack(null);
					int ind = rows[0];
					if (ind >= mm.n)
						ind = mm.n - 1;
					lsm.setSelectionInterval(ind, ind);
					setB(ind);
				});
			}

		});

		revt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mmet.anim.revert();
				callBack(null);
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
		add(sb);
		add(mb);
		jlu.setCellRenderer(new AnimLCR());
		jtr.setExpandsSelectedPaths(true);
		addl.setEnabled(false);
		reml.setEnabled(false);
		rema.setEnabled(false);
		reml.setForeground(Color.RED);
		rema.setForeground(Color.RED);
		addListeners();
	}

	private void setA(DIYAnim da) {
		if (da != null && da.getAnimC().mismatch)
			da = null;
		aep.setAnim(da);
		setTree(da == null ? null : da.anim.mamodel);
		if (da == null) {
			mmet.setMaModel(null);
			mb.setEntity(null);
			sb.setAnim(null);
			jlp.setListData(new String[0]);
			setB(-1);
			return;
		}
		AnimC anim = da.getAnimC();
		addl.setEnabled(anim != null);
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

	private void setTree(MaModel dat) {
		change(dat, mm -> {
			if (mm == null) {
				jtr.setModel(new DefaultTreeModel(null));
				return;
			}
			DefaultMutableTreeNode[] data = new DefaultMutableTreeNode[mm.n];
			DefaultMutableTreeNode top = new DefaultMutableTreeNode("MaModel");
<<<<<<< HEAD
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
=======
			int c=0;
			while(c<mm.n) {
				for (int i = 0; i < mm.n; i++)
					if(data[i]==null){
					int[] line = mm.parts[i];
					DefaultMutableTreeNode pre=(line[0] == -1 ? top : data[line[0]]);
					if(pre==null)
						continue;
					data[i] = new DefaultMutableTreeNode(i + " - " + mm.strs0[i]);
					pre.add(data[i]);
					c++;
				}
>>>>>>> branch 'master' of https://github.com/lcy0x1/BCU.git
			}
			jtr.setModel(new DefaultTreeModel(top));
		});
		for (int i = 0; i < jtr.getRowCount(); i++)
			jtr.expandRow(i);
	}

}
