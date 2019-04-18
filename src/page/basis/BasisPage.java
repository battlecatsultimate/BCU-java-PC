package page.basis;

import static util.basis.BasisSet.current;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import io.Reader;
import page.JBTN;
import page.JTF;
import page.Page;
import page.info.TreaTable;
import page.support.ReorderList;
import page.support.ReorderListener;
import page.support.UnitLCR;
import util.Interpret;
import util.basis.BasisLU;
import util.basis.BasisSet;
import util.basis.Combo;
import util.basis.LineUp;
import util.pack.NyCastle;
import util.system.Node;
import util.unit.Form;
import util.unit.Unit;;

public class BasisPage extends LubCont {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN unit = new JBTN(0, "vuif");
	private final JBTN setc = new JBTN(0, "set0");
	private final JBTN bsadd = new JBTN(0, "add");
	private final JBTN bsrem = new JBTN(0, "rem");
	private final JBTN bscop = new JBTN(0, "copy");
	private final JBTN badd = new JBTN(0, "add");
	private final JBTN brem = new JBTN(0, "rem");
	private final JBTN form = new JBTN(0, "form");
	private final JTF bsjtf = new JTF();
	private final JTF bjtf = new JTF();
	private final JTF lvjtf = new JTF();
	private final JLabel pcoin = new JLabel();
	private final Vector<BasisSet> vbs = new Vector<>(BasisSet.list);
	private final ReorderList<BasisSet> jlbs = new ReorderList<>(vbs);
	private final JScrollPane jspbs = new JScrollPane(jlbs);
	private final Vector<BasisLU> vb = new Vector<>();
	private final ReorderList<BasisLU> jlb = new ReorderList<>(vb, BasisLU.class, "lineup");
	private final JScrollPane jspb = new JScrollPane(jlb);
	private final JList<String> jlcs = new JList<>(Interpret.COMF);
	private final JScrollPane jspcs = new JScrollPane(jlcs);
	private final JList<String> jlcl = new JList<>();
	private final JScrollPane jspcl = new JScrollPane(jlcl);
	private final ComboListTable jlc = new ComboListTable(this, lu());
	private final JScrollPane jspc = new JScrollPane(jlc);
	private final ComboList jlcn = new ComboList();
	private final JScrollPane jspcn = new JScrollPane(jlcn);
	private final LineUpBox lub = new LineUpBox(this);
	private final JList<Form> ul = new JList<>();
	private final JScrollPane jspul = new JScrollPane(ul);
	private final NyCasBox ncb = new NyCasBox();
	private final JBTN[] jbcs = new JBTN[3];

	private boolean changing = false, outside = false;

	private UnitFLUPage ufp;

	private final TreaTable trea = new TreaTable(this, BasisSet.current);

	public BasisPage(Page p) {
		super(p);

		ini();
		resized();
	}

	@Override
	public void callBack(Object o) {
		if (o == null)
			changeLU();
		else if (o instanceof Form) {
			Form f = (Form) o;
			Unit u = f.unit;
			setLvs(f);
			List<Combo> lc = u.allCombo();
			if (lc.size() == 0)
				return;
			outside = true;
			changing = true;
			jlc.setList(lc);
			jlc.getSelectionModel().setSelectionInterval(0, 0);
			setC(0);
			changing = false;
		}

	}

	@Override
	protected LineUpBox getLub() {
		return lub;
	}

	@Override
	protected void keyTyped(KeyEvent e) {
		if (trea.hasFocus())
			return;
		if (lvjtf.hasFocus())
			return;
		if (bjtf.hasFocus())
			return;
		if (bsjtf.hasFocus())
			return;
		super.keyTyped(e);
		e.consume();
	}

	@Override
	protected void mouseClicked(MouseEvent e) {
		if (e.getSource() == jlc)
			jlc.clicked(e.getPoint());
		super.mouseClicked(e);
	}

	@Override
	protected void renew() {
		if (ufp != null) {
			List<Form> lf = ufp.getList();
			if (lf != null)
				ul.setListData(Node.deRep(lf).toArray(new Form[0]));
		}
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jspbs, x, y, 50, 100, 200, 300);
		set(bsadd, x, y, 50, 450, 200, 50);
		set(bsrem, x, y, 50, 550, 200, 50);
		set(bscop, x, y, 50, 650, 200, 50);
		set(bsjtf, x, y, 50, 750, 200, 50);
		set(jspb, x, y, 750, 50, 200, 500);
		set(badd, x, y, 750, 550, 200, 50);
		set(brem, x, y, 750, 600, 200, 50);
		set(bjtf, x, y, 750, 700, 200, 50);
		set(ncb, x, y, 750, 800, 200, 300);
		set(trea, x, y, 300, 50, 400, 1200);
		set(lub, x, y, 1000, 100, 600, 300);
		set(unit, x, y, 1650, 0, 200, 50);
		set(jspcs, x, y, 1950, 00, 300, 250);
		set(jspcl, x, y, 1950, 300, 300, 450);
		set(jspc, x, y, 1050, 800, 1200, 450);
		set(jspcn, x, y, 1300, 450, 300, 300);
		set(lub, x, y, 1000, 100, 600, 300);
		set(jspul, x, y, 1650, 50, 200, 700);
		set(form, x, y, 1000, 400, 200, 50);
		set(pcoin, x, y, 1000, 0, 600, 50);
		set(lvjtf, x, y, 1000, 50, 350, 50);
		set(setc, x, y, 1050, 700, 200, 50);
		for (int i = 0; i < 3; i++)
			set(jbcs[i], x, y, 750, 1100 + 50 * i, 200, 50);
		jlc.setRowHeight(85);
		jlc.getColumnModel().getColumn(1).setPreferredWidth(size(x, y, 300));
	}

	@Override
	protected void timer(int t) {
		ncb.paint(ncb.getGraphics());
		super.timer(t);
	}

	private void addListeners$0() {

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		unit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ufp == null)
					ufp = new UnitFLUPage(getThis());
				changePanel(ufp);
			}
		});

		ul.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				changing = true;
				lub.select(ul.getSelectedValue());
				changing = false;
			}
		});

		form.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lub.adjForm();
				changeLU();
			}
		});

		lvjtf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				int[] lv = Reader.parseIntsN(lvjtf.getText());
				lub.setLv(lv);
				if (lub.sf != null)
					setLvs(lub.sf);
			}
		});

		for (int i = 0; i < 3; i++) {
			int I = i;

			jbcs[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					current.sele.nyc[I]++;
					current.sele.nyc[I] %= NyCastle.TOT;
				}

			});
		}

	}

	private void addListeners$1() {

		jlbs.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (jlb.getValueIsAdjusting() || changing)
					return;
				changing = true;
				if (jlbs.getSelectedValue() == null)
					jlbs.setSelectedValue(BasisSet.current, true);
				else
					setBS(jlbs.getSelectedValue());
				changing = false;
			}
		});

		jlb.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (jlb.getValueIsAdjusting() || changing)
					return;
				changing = true;
				if (jlb.getSelectedValue() == null)
					jlb.setSelectedValue(BasisSet.current.sele, true);
				else
					setB(jlb.getSelectedValue());
				changing = false;
			}
		});

		jlbs.list = new ReorderListener<BasisSet>() {

			@Override
			public void reordered(int ori, int fin) {
				changing = false;
				List<BasisSet> l = BasisSet.list;
				BasisSet b = l.remove(ori);
				l.add(fin, b);
			}

			@Override
			public void reordering() {
				changing = true;
			}

		};

		jlb.list = new ReorderListener<BasisLU>() {

			@Override
			public boolean add(BasisLU blu) {
				BasisSet.current.lb.add(blu);
				return true;
			}

			@Override
			public void reordered(int ori, int fin) {
				changing = false;
				List<BasisLU> l = BasisSet.current.lb;
				BasisLU b = l.remove(ori);
				l.add(fin, b);
			}

			@Override
			public void reordering() {
				changing = true;
			}

		};

		bsadd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				BasisSet b = new BasisSet();
				vbs.clear();
				vbs.addAll(BasisSet.list);
				jlbs.setListData(vbs);
				jlbs.setSelectedValue(b, true);
				setBS(b);
				changing = false;
			}
		});

		bsrem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				BasisSet.list.remove(current);
				vbs.clear();
				vbs.addAll(BasisSet.list);
				jlbs.setListData(vbs);
				BasisSet b = BasisSet.list.get(BasisSet.list.size() - 1);
				jlbs.setSelectedValue(b, true);
				setBS(b);
				changing = false;
			}
		});

		bscop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				BasisSet b = new BasisSet(current);
				vbs.clear();
				vbs.addAll(BasisSet.list);
				jlbs.setListData(vbs);
				jlbs.setSelectedValue(b, true);
				setBS(b);
				changing = false;
			}
		});

		badd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				BasisLU b = current.add();
				vb.clear();
				vb.addAll(current.lb);
				jlb.setListData(vb);
				jlb.setSelectedValue(b, true);
				setB(b);
				changing = false;
			}
		});

		brem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				BasisLU b = current.remove();
				vb.clear();
				vb.addAll(current.lb);
				jlb.setListData(vb);
				jlb.setSelectedValue(b, true);
				setB(b);
				changing = false;
			}
		});

		bsjtf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String str = bsjtf.getText().trim();
				if (str.length() > 0)
					BasisSet.current.name = str;
				bsjtf.setText(BasisSet.current.name);
				jlbs.repaint();
			}
		});

		bjtf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String str = bjtf.getText().trim();
				if (str.length() > 0)
					BasisSet.current.sele.name = str;
				bjtf.setText(BasisSet.current.sele.name);
				jlb.repaint();
			}
		});

	}

	private void addListeners$2() {
		jlcs.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (changing || e.getValueIsAdjusting())
					return;
				changing = true;
				if (jlcs.getSelectedValue() == null)
					jlcs.setSelectedIndex(0);
				setCS(jlcs.getSelectedIndex());
				changing = false;
			}
		});

		jlcl.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (changing || e.getValueIsAdjusting())
					return;
				changing = true;
				setCL(jlcs.getSelectedIndex());
				changing = false;
			}
		});

		jlcn.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (changing || e.getValueIsAdjusting())
					return;
				changing = true;
				setCN();
				changing = false;
			}
		});

		jlc.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || arg0.getValueIsAdjusting())
					return;
				changing = true;
				setC(jlc.getSelectedRow());
				changing = false;
			}
		});

		setc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lu().set(jlc.list.get(jlc.getSelectedRow()).units);
				changeLU();
			}
		});
	}

	private void changeLU() {
		jlcn.setListData(lu().coms.toArray(new Combo[0]));
		setCN();
		updateSetC();
		lub.updateLU();
		setLvs(lub.sf);
	}

	private void ini() {
		add(back);
		add(jspbs);
		add(jspb);
		add(trea);
		add(trea);
		add(bsadd);
		add(bsrem);
		add(bscop);
		add(badd);
		add(brem);
		add(lub);
		add(back);
		add(unit);
		add(jspcs);
		add(jspcl);
		add(jspc);
		add(jspcn);
		add(lub);
		add(jspul);
		add(setc);
		add(bsjtf);
		add(bjtf);
		add(form);
		add(lvjtf);
		add(pcoin);
		add(ncb);
		add(jbcs[0] = new JBTN(0, "ctop"));
		add(jbcs[1] = new JBTN(0, "cmid"));
		add(jbcs[2] = new JBTN(0, "cbas"));
		ul.setCellRenderer(new UnitLCR());
		int m0 = ListSelectionModel.SINGLE_SELECTION;
		int m1 = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
		jlbs.setSelectedValue(current, true);
		jlcs.setSelectedIndex(0);
		jlbs.setSelectionMode(m0);
		jlb.setSelectionMode(m0);
		jlcs.setSelectionMode(m0);
		jlcl.setSelectionMode(m1);
		jlcn.setSelectionMode(m0);
		jlc.getSelectionModel().setSelectionMode(m0);
		setCS(0);
		setBS(current);
		lub.setLU(lu());
		bsjtf.setText(BasisSet.current.name);
		bjtf.setText(BasisSet.current.sele.name);
		changeLU();
		addListeners$0();
		addListeners$1();
		addListeners$2();
	}

	private LineUp lu() {
		return BasisSet.current.sele.lu;
	}

	private void setB(BasisLU b) {
		current.sele = b;
		lub.setLU(b == null ? null : b.lu);
		brem.setEnabled(current.lb.size() > 1);
		bjtf.setText(BasisSet.current.sele.name);
		ncb.set(b.nyc);
		changeLU();
		callBack(lub.sf);
	}

	private void setBS(BasisSet bs) {
		current = bs;
		vb.clear();
		vb.addAll(bs.lb);
		jlb.setListData(vb);
		BasisLU b = bs.sele;
		jlb.setSelectedValue(b, true);
		trea.setBasis(bs);
		bsjtf.setText(BasisSet.current.name);
		bsrem.setEnabled(current != BasisSet.def);
		setB(b);
	}

	private void setC(int c) {
		if (outside) {
			jlcs.setSelectedIndex(0);
			jlcl.setListData(Interpret.getComboFilter(0));
			int row = jlc.list.get(c).type;
			jlcl.setSelectedIndex(row);
			Point p = jlcl.indexToLocation(row);
			int h = jlcl.indexToLocation(1).y - jlcl.indexToLocation(0).y;
			if (p != null)
				jlcl.scrollRectToVisible(new Rectangle(p.x, p.y, 1, h));
		}
		updateSetC();
	}

	private void setCL(int cs) {
		int[] cls = jlcl.getSelectedIndices();
		if (cls.length == 0) {
			List<Combo> lc = new ArrayList<>();
			for (int i = 0; i < Combo.filter[cs].length; i++)
				for (Combo c : Combo.combos[Combo.filter[cs][i]])
					lc.add(c);
			jlc.setList(lc);
		} else {
			List<Combo> lc = new ArrayList<>();
			for (int val : cls)
				for (Combo c : Combo.combos[Combo.filter[cs][val]])
					lc.add(c);
			jlc.setList(lc);
		}
		jlc.getSelectionModel().setSelectionInterval(0, 0);
		outside = false;
		setC(0);
	}

	private void setCN() {
		lub.select(jlcn.getSelectedValue());
	}

	private void setCS(int cs) {
		jlcl.setListData(Interpret.getComboFilter(cs));
		jlcl.setSelectedIndex(0);
		setCL(cs);
	}

	private void setLvs(Form f) {
		if (f == null) {
			lvjtf.setText("");
			pcoin.setText("");
			return;
		}
		String[] strs = f.lvText(lu().getLv(f.unit));
		lvjtf.setText(strs[0]);
		pcoin.setText(strs[1]);
	}

	private void updateSetC() {
		Combo com = jlc.list.get(jlc.getSelectedRow());
		setc.setEnabled(com != null && !lu().contains(com));
		boolean b = false;
		if (com != null)
			b = lu().willRem(com);
		setc.setForeground(b ? Color.RED : Color.BLACK);
<<<<<<< HEAD
		setc.setText(0, "set" + (b ? "1" : "0"));
=======
		setc.setText(get("set" + (b ? "1" : "0")));
>>>>>>> branch 'master' of https://github.com/lcy0x1/BCU.git

	}

}
