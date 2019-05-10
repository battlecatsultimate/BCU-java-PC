package page.anim;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import io.Reader;
import main.Opts;
import page.JBTN;
import page.JL;
import page.JTF;
import page.JTG;
import page.Page;
import util.anim.AnimC;
import util.anim.EPart;
import util.anim.MaAnim;
import util.anim.Part;

public class AdvAnimEditPage extends Page implements TreeCont {

	private static final long serialVersionUID = 1L;

	private static final double res = 0.95;

	private static final String[] mod = new String[] { "0 parent", "1 id", "2 sprite", "3 z-order", "4 pos-x",
			"5 pos-y", "6 pivot-x", "7 pivot-y", "8 scale", "9 scale-x", "10 scale-y", "11 angle", "12 opacity",
			"13 horizontal flip", "14 vertical flip", "50 extend" };

	private final JBTN back = new JBTN(0, "back");
	private final JTree jlm = new JTree();
	private final JScrollPane jspm = new JScrollPane(jlm);
	private final JList<String> jlv = new JList<>(mod);
	private final JScrollPane jspv = new JScrollPane(jlv);
	private final MaAnimEditTable maet = new MaAnimEditTable(this);
	private final JScrollPane jspma = new JScrollPane(maet);
	private final PartEditTable mpet = new PartEditTable(this);
	private final JScrollPane jspmp = new JScrollPane(mpet);
	private final JTG jtb = new JTG(0, "pause");
	private final JBTN nex = new JBTN(0, "nextf");
	private final JSlider jtl = new JSlider();
	private final AnimBox ab = new AnimBox();
	private final JBTN addp = new JBTN(0, "add");
	private final JBTN remp = new JBTN(0, "rem");
	private final JBTN addl = new JBTN(0, "addl");
	private final JBTN reml = new JBTN(0, "reml");
	private final JBTN same = new JBTN(0, "same");
	private final JBTN clea = new JBTN(0, "clean");
	private final JBTN sort = new JBTN(0, "sort");
	private final JBTN keep = new JBTN(0, "keep");// TODO
	private final JBTN appl = new JBTN(0, "apply");// TODO
	private final JBTN show = new JBTN(0, "show");// TODO
	private final JBTN time = new JBTN(0, "time");// TODO
	private final JL lkip = new JL();
	private final JL inft = new JL();
	private final JL inff = new JL();
	private final JL infv = new JL();
	private final JL infm = new JL();
	private final JL lmul = new JL("</>");
	private final JTF tmul = new JTF();
	private final AnimC ac;
	private final int animID;
	private final MMTree mmt;
	private Point p = null;
	private boolean pause;
	private Part[] keeps;

	public AdvAnimEditPage(Page p, AnimC anim, int id) {
		super(p);
		ac = anim;
		animID = id;
		mmt = new MMTree(this, ac, jlm);
		ini();
		resized();
	}

	@Override
	public void callBack(Object o) {
		if (o != null && o instanceof int[])
			change((int[]) o, rs -> {
				if (rs[0] == 0) {
					maet.setRowSelectionInterval(rs[1], rs[2]);
					setC(rs[1]);
				} else {
					mpet.setRowSelectionInterval(rs[1], rs[2]);
					setD(rs[1]);
				}
			});
		int time = ab.ent == null ? 0 : ab.ent.ind();
		ab.setEntity(ac.getEAnim(animID));
		ab.ent.setTime(time);
	}

	@Override
	public void collapse() {
		selectTree(false);
	}

	@Override
	public void expand() {
		selectTree(false);
	}

	public void selectTree(boolean bv) {
		if (isAdj())
			return;
		boolean exp = jlm.isExpanded(jlm.getSelectionPath());
		Object o = jlm.getLastSelectedPathComponent();
		if (o == null)
			return;
		String str = o.toString();
		int ind = Reader.parseIntN(str.split(" - ")[0]);

		List<Integer> ses = new ArrayList<>();
		for (int i = 0; i < maet.ma.n; i++) {
			Part p = maet.ma.parts[i];
			if (p.ints[0] == ind && (!bv || jlv.isSelectedIndex(p.ints[1])))
				ses.add(i);
		}
		if (!exp)
			mmt.nav(ind, xnd -> {
				for (int i = 0; i < maet.ma.n; i++) {
					Part p = maet.ma.parts[i];
					if (p.ints[0] == xnd && (!bv || jlv.isSelectedIndex(p.ints[1])))
						ses.add(i);
				}
				return true;
			});
		mmt.setAdjusting(true);
		setCs(ses);
		mmt.setAdjusting(false);
		ab.setSele(ind);
	}

	@Override
	protected void mouseDragged(MouseEvent e) {
		if (p == null)
			return;
		ab.ori.x += p.x - e.getX();
		ab.ori.y += p.y - e.getY();
		p = e.getPoint();
	}

	@Override
	protected void mousePressed(MouseEvent e) {
		if (!(e.getSource() instanceof AnimBox))
			return;
		p = e.getPoint();
	}

	@Override
	protected void mouseReleased(MouseEvent e) {
		p = null;
	}

	@Override
	protected void mouseWheel(MouseEvent e) {
		if (!(e.getSource() instanceof AnimBox))
			return;
		MouseWheelEvent mwe = (MouseWheelEvent) e;
		double d = mwe.getPreciseWheelRotation();
		ab.siz *= Math.pow(res, d);
	}

	@Override
	protected synchronized void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);

		set(addp, x, y, 300, 750, 200, 50);
		set(remp, x, y, 300, 800, 200, 50);
		set(lmul, x, y, 300, 650, 200, 50);
		set(tmul, x, y, 300, 700, 200, 50);
		set(jspv, x, y, 300, 850, 200, 450);
		set(jspma, x, y, 500, 650, 900, 650);
		set(jspmp, x, y, 1400, 650, 900, 650);
		set(jspm, x, y, 0, 50, 300, 1250);
		set(ab, x, y, 300, 50, 700, 500);
		set(addl, x, y, 2100, 550, 200, 50);
		set(reml, x, y, 2100, 600, 200, 50);
		set(jtl, x, y, 300, 550, 900, 100);
		set(jtb, x, y, 1200, 550, 200, 50);
		set(nex, x, y, 1200, 600, 200, 50);
		set(inft, x, y, 1400, 550, 250, 50);
		set(inff, x, y, 1650, 550, 250, 50);
		set(infv, x, y, 1400, 600, 250, 50);
		set(infm, x, y, 1650, 600, 250, 50);
		// 1300 50 1000 500
		set(same, x, y, 1300, 50, 200, 50);
		set(sort, x, y, 1300, 100, 200, 50);
		set(clea, x, y, 1300, 150, 200, 50);
		set(time, x, y, 1300, 200, 200, 50);
		set(lkip, x, y, 1500, 50, 200, 50);
		set(keep, x, y, 1500, 100, 200, 50);
		set(appl, x, y, 1500, 150, 200, 50);
		set(show, x, y, 1500, 200, 200, 50);

		maet.setRowHeight(size(x, y, 50));
		mpet.setRowHeight(size(x, y, 50));
		ab.paint(ab.getGraphics());
	}

	@Override
	protected void timer(int t) {
		if (!pause)
			eupdate();
		if (ab.ent != null && mpet.part != null) {
			Part p = mpet.part;
			EPart ep = ab.ent.ent[p.ints[0]];
			inft.setText("frame: " + ab.ent.ind());
			inff.setText("part frame: " + (p.frame - p.off));
			infv.setText("actual value: " + ep.getVal(p.ints[1]));
			infm.setText("part value: " + p.vd);
		} else {
			inft.setText("");
			inff.setText("");
			infv.setText("");
			infm.setText("");
		}
		resized();
	}

	private void addListeners$0() {

		back.setLnr(x -> changePanel(getFront()));

		jlm.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				selectTree(false);
			}

		});

		jlv.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				selectTree(true);
			}

		});

	}

	private void addListeners$4() {

		tmul.setLnr(x -> {
			double d = Reader.parseIntN(tmul.getText()) * 0.01;
			if (!Opts.conf("times animation length by " + d))
				return;
			for (Part p : maet.ma.parts) {
				for (int[] line : p.moves)
					line[0] *= d;
				p.off *= d;
				p.validate();
			}
			maet.ma.validate();
			maet.anim.unSave("maanim multiply");
		});

		same.setLnr(x -> change(0, z -> setCs(findRep(mpet.part))));

		sort.setLnr(x -> Arrays.sort(maet.ma.parts));

		clea.setLnr(x -> {
			for (Part p : maet.getSelected())
				clean(p);
		});

		time.setLnr(x -> {
			int[] times = getTimeLine(maet.getSelected());
			String str = "";
			for (int i : times)
				str += i == 0 ? "-" : "X";
			System.out.println(str);// TODO
		});
	}

	private void addListeners$5() {

		keep.setLnr(x -> {
			keeps = maet.getSelected();
			lkip.setText("keep " + keeps.length + " item");
		});

		show.setLnr(x -> change(0, z -> {
			if (keeps == null || keeps.length == 0)
				return;
			List<Integer> ses = new ArrayList<>();
			for (int i = 0; i < keeps.length; i++) {
				ses.add(-1);
				for (int j = 0; j < maet.ma.parts.length; j++)
					if (keeps[i] == maet.ma.parts[j]) {
						ses.set(i, j);
						break;
					}
			}
			setCs(ses);
		}));

		appl.setLnr(x -> {
			if (mpet.part == null || keeps == null || keeps.length == 0)
				return;
			Part p = mpet.part;
			if (Opts.conf("applying data of this part to " + keeps.length + " parts?"))
				for (Part pi : keeps)
					if (pi != p) {
						pi.n = p.n;
						pi.fir = p.fir;
						pi.off = p.off;
						pi.max = p.max;
						pi.ints[2] = p.ints[2];
						pi.moves = new int[pi.n][];
						for (int i = 0; i < p.n; i++)
							pi.moves[i] = p.moves[i].clone();
						pi.validate();
					}
		});

	}

	private void addLnr$Anim() {

		jtb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pause = jtb.isSelected();
				jtl.setEnabled(pause && ab.ent != null);
			}
		});

		nex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				eupdate();
			}
		});

		jtl.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (isAdj() || !pause)
					return;
				ab.ent.setTime(jtl.getValue());
			}
		});

	}

	private void addLnr$C() {
		ListSelectionModel lsm = maet.getSelectionModel();

		lsm.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (isAdj() || lsm.getValueIsAdjusting())
					return;
				int[] inds = maet.getSelectedRows();
				List<Integer> l = new ArrayList<>();
				for (int i : inds)
					l.add(i);
				setCs(l);
			}

		});

		addp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				change(true);
				int ind = maet.getSelectedRow() + 1;
				MaAnim ma = maet.ma;
				Part[] data = ma.parts;
				ma.parts = new Part[++ma.n];
				for (int i = 0; i < ind; i++)
					ma.parts[i] = data[i];
				for (int i = ind; i < data.length; i++)
					ma.parts[i + 1] = data[i];
				Part np = new Part();
				np.validate();
				ma.parts[ind] = np;
				ma.validate();
				maet.anim.unSave("maanim add part");
				callBack(null);
				resized();
				lsm.setSelectionInterval(ind, ind);
				setC(ind);
				int h = mpet.getRowHeight();
				mpet.scrollRectToVisible(new Rectangle(0, h * ind, 1, h));
				change(false);
			}

		});

		remp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				change(true);
				MaAnim ma = maet.ma;
				int[] rows = maet.getSelectedRows();
				Part[] data = ma.parts;
				for (int row : rows)
					data[row] = null;
				ma.n -= rows.length;
				ma.parts = new Part[ma.n];
				int ind = 0;
				for (int i = 0; i < data.length; i++)
					if (data[i] != null)
						ma.parts[ind++] = data[i];
				ind = rows[rows.length - 1];
				ma.validate();
				maet.anim.unSave("maanim remove part");
				callBack(null);
				if (ind >= ma.n)
					ind = ma.n - 1;
				lsm.setSelectionInterval(ind, ind);
				setC(ind);
				change(false);
			}

		});

	}

	private void addLnr$D() {
		ListSelectionModel lsm = mpet.getSelectionModel();

		lsm.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (isAdj() || lsm.getValueIsAdjusting())
					return;
				setD(lsm.getLeadSelectionIndex());
			}

		});

		addl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Part p = mpet.part;
				int[][] data = p.moves;
				p.moves = new int[++p.n][];
				for (int i = 0; i < data.length; i++)
					p.moves[i] = data[i];
				p.moves[p.n - 1] = new int[4];
				p.validate();
				maet.ma.validate();
				callBack(null);
				maet.anim.unSave("maanim add line");
				resized();
				change(p.n - 1, i -> lsm.setSelectionInterval(i, i));
				setD(p.n - 1);
				int h = mpet.getRowHeight();
				mpet.scrollRectToVisible(new Rectangle(0, h * (p.n - 1), 1, h));
			}

		});

		reml.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int[] inds = mpet.getSelectedRows();
				if (inds.length == 0)
					return;
				Part p = mpet.part;
				List<int[]> l = new ArrayList<>();
				int j = 0;
				for (int i = 0; i < p.n; i++)
					if (j >= inds.length || i != inds[j])
						l.add(p.moves[i]);
					else
						j++;
				p.moves = l.toArray(new int[0][]);
				p.n = l.size();
				p.validate();
				maet.ma.validate();
				callBack(null);
				maet.anim.unSave("maanim remove line");
				int ind = inds[0];
				if (ind >= p.n)
					ind--;
				change(ind, i -> lsm.setSelectionInterval(i, i));
				setD(ind);
			}

		});
	}

	private void clean(Part p) {
		if (p.off != 0 || p.ints[2] != 1 || p.n < 4)
			return;
		for (int i = 2; i < p.n - 1; i++) {
			boolean suc = true;
			for (int j = 0; j < p.n; j++) {
				boolean mat = true;
				int[] i0 = p.moves[j];
				int[] i1 = p.moves[j % i];
				mat &= i0[1] == i1[1];
				mat &= i0[2] == i1[2];
				mat &= i0[3] == i1[3];
				if (j > 0)
					mat &= i0[0] - p.moves[j - 1][0] == p.moves[(j - 1) % i + 1][0] - p.moves[(j - 1) % i][0];
				if (!mat) {
					suc = false;
					break;
				}
			}
			if (suc) {
				p.moves = Arrays.copyOf(p.moves, p.n = i + 1);
				p.ints[2] = -1;
				p.validate();
				return;
			}
		}
	}

	private void eupdate() {
		ab.update();
		if (ab.ent != null)
			change(0, x -> jtl.setValue(ab.ent.ind()));
	}

	private List<Integer> findRep(Part p) {
		if (p == null)
			return null;
		List<Integer> ans = new ArrayList<>();
		for (int i = 0; i < maet.ma.n; i++) {
			Part pi = maet.ma.parts[i];
			if (p.ints[1] != pi.ints[1])
				continue;
			if (p.ints[2] != pi.ints[2])
				continue;
			if (p.n != pi.n)
				continue;
			if (p.ints[2] != pi.ints[2])
				continue;
			boolean pass = true;
			for (int j = 0; j < p.n; j++)
				for (int k = 0; k < 4; k++)
					if (p.moves[j][k] != pi.moves[j][k])
						pass = false;
			if (pass)
				ans.add(i);
		}
		return ans;

	}

	private int[] getTimeLine(Part[] ps) {
		int maxs = 0;
		for (Part p : ps) {
			maxs = Math.max(maxs, p.max + 1);
			if (p.off > 0)
				return null;
		}
		int[] ans = new int[maxs];
		for (Part p : ps)
			for (int[] m : p.moves)
				ans[m[0]]++;
		return ans;
	}

	private void ini() {
		add(back);
		add(jspm);
		add(jspv);
		add(jspma);
		add(jspmp);
		add(addp);
		add(remp);
		add(addl);
		add(reml);
		add(jtb);
		add(jtl);
		add(nex);
		add(ab);
		add(inft);
		add(inff);
		add(infv);
		add(infm);
		add(lmul);
		add(tmul);
		add(same);
		add(sort);
		add(keep);
		add(clea);
		add(appl);
		add(show);
		add(lkip);
		add(time);
		setA();

		addListeners$0();
		addLnr$C();
		addLnr$D();
		addLnr$Anim();
		addListeners$4();
		addListeners$5();
	}

	private void setA() {
		mmt.renew();
		int row = maet.getSelectedRow();
		maet.setAnim(ac, ac.anims[animID]);
		ab.setEntity(ac.getEAnim(animID));
		if (row >= maet.getRowCount()) {
			maet.clearSelection();
			row = -1;
		}
		setC(row);
		jtl.setPaintTicks(true);
		jtl.setPaintLabels(true);
		jtl.setMinimum(0);
		jtl.setMaximum(ab.ent.len());
		jtl.setLabelTable(null);
		if (ab.ent.len() <= 50) {
			jtl.setMajorTickSpacing(5);
			jtl.setMinorTickSpacing(1);
		} else if (ab.ent.len() <= 200) {
			jtl.setMajorTickSpacing(10);
			jtl.setMinorTickSpacing(2);
		} else if (ab.ent.len() <= 1000) {
			jtl.setMajorTickSpacing(50);
			jtl.setMinorTickSpacing(10);
		} else if (ab.ent.len() <= 5000) {
			jtl.setMajorTickSpacing(250);
			jtl.setMinorTickSpacing(50);
		} else {
			jtl.setMajorTickSpacing(1000);
			jtl.setMinorTickSpacing(200);
		}

	}

	private void setC(int ind) {
		Part p = ind < 0 || ind >= maet.ma.parts.length ? null : maet.ma.parts[ind];

		remp.setEnabled(ind >= 0);
		addl.setEnabled(ind >= 0);
		same.setEnabled(ind >= 0);
		clea.setEnabled(ind >= 0);
		ab.setSele(p == null ? -1 : p.ints[0]);
		change(true);
		mpet.setAnim(maet.anim, maet.ma, p);
		mpet.clearSelection();
		if (ind >= 0) {
			int par = p.ints[0];
			mmt.select(par);
			jlv.setSelectedIndex(mpet.part.ints[1]);
			if (maet.getSelectedRow() != ind) {
				maet.setRowSelectionInterval(ind, ind);
				maet.scrollRectToVisible(maet.getCellRect(ind, 0, true));
			}
			ab.setSele(par);
		} else
			maet.clearSelection();
		change(false);
		setD(-1);
	}

	private void setCs(Iterable<Integer> is) {
		change(true);
		boolean setted = false;
		maet.clearSelection();
		for (int i : is)
			if (i >= 0) {
				if (!setted) {
					setC(i);
					setted = true;
				}
				maet.addRowSelectionInterval(i, i);
				int v = maet.ma.parts[i].ints[1];
				jlv.addSelectionInterval(v, v);
			}
		if (!setted)
			setC(-1);
		change(false);
	}

	private void setD(int ind) {
		reml.setEnabled(ind >= 0);
	}

}
