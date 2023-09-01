package page.anim;

import common.CommonStatic;
import common.util.anim.AnimCE;
import common.util.anim.AnimU.UType;
import common.util.anim.EPart;
import common.util.anim.MaAnim;
import common.util.anim.Part;
import main.Opts;
import page.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

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
	private final AnimBox ab = AnimBox.getInstance();
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
	private final JBTN revt = new JBTN(0, "revt");
	private final JL lkip = new JL();
	private final JL inft = new JL();
	private final JL inff = new JL();
	private final JL infv = new JL();
	private final JL infm = new JL();
	private final JTG lmul = new JTG("set speed for selected");
	private final JTF tmul = new JTF();
	private final AnimCE ac;
	private final UType animID;
	private final MMTree mmt;
	private Point p = null;
	private boolean pause, changing;
	private Part[] keeps;

	public AdvAnimEditPage(Page p, AnimCE anim, UType id) {
		super(p);
		ac = anim;
		animID = id;
		mmt = new MMTree(this, ac, jlm);
		ini();
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	public void callBack(Object o) {
		if (o instanceof int[])
			change((int[]) o, rs -> {
				if (rs[0] == 0) {
					maet.setRowSelectionInterval(rs[1], rs[2]);
					setC(rs[1]);
				} else {
					mpet.setRowSelectionInterval(rs[1], rs[2]);
					setD(rs[1]);
				}
			});
		float time = ab.getEntity() == null ? 0 : ab.getEntity().ind();
		ab.setEntity(ac.getEAnim(animID));
		ab.getEntity().setTime(time);
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
		int ind = CommonStatic.parseIntN(str.split(" - ")[0]);

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
		AnimBox.ori.x += p.x - e.getX();
		AnimBox.ori.y += p.y - e.getY();
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
		ab.setSiz(ab.getSiz() * Math.pow(res, d));
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
		set((Canvas) ab, x, y, 300, 50, 700, 500);
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
		set(revt, x, y, 1300, 250, 200, 50);
		set(lkip, x, y, 1500, 50, 200, 50);
		set(keep, x, y, 1500, 100, 200, 50);
		set(appl, x, y, 1500, 150, 200, 50);
		set(show, x, y, 1500, 200, 200, 50);

		maet.setRowHeight(size(x, y, 50));
		mpet.setRowHeight(size(x, y, 50));
	}

	@Override
    public synchronized void onTimer(int t) {
		super.onTimer(t);

		ab.draw();

		if (!pause)
			eupdate();
		if (ab.getEntity() != null && mpet.part != null) {
			Part p = mpet.part;
			EPart ep = ab.getEntity().ent[p.ints[0]];
			inft.setText("frame: " + ab.getEntity().ind());
			inff.setText("part frame: " + (p.frame - p.off));
			infv.setText("actual value: " + ep.getVal(p.ints[1]));
			infm.setText("part value: " + p.vd);
		} else {
			inft.setText("");
			inff.setText("");
			infv.setText("");
			infm.setText("");
		}
	}

	private void addListeners$0() {

		back.setLnr(x -> changePanel(getFront()));

		jlm.addTreeSelectionListener(arg0 -> selectTree(false));

		jlv.addListSelectionListener(arg0 -> selectTree(true));

	}

	private void addListeners$4() {

		tmul.setLnr(x -> {
			if (changing)
				return;
			changing = true;

			double d = CommonStatic.parseIntN(tmul.getText()) * 0.01;
			if(d <= 0) {
				tmul.setText("");
				changing = false;
				return;
			}

			String str = d < 1 ? "Decrease " : "Increase ";
			if (!Opts.conf(str + "animation speed to " + (d * 100) + "%?")) {
				changing = false;
				return;
			}

			if (lmul.isSelected() && maet.getSelected().length > 0) {
				for (Part p : maet.getSelected()) {
					for (int[] line : p.moves)
						line[0] *= d;
					p.off *= d;
					p.validate();
				}
			} else
				for (Part p : maet.ma.parts) {
					for (int[] line : p.moves)
						line[0] *= d;
					p.off *= d;
					p.validate();
				}
			maet.ma.validate();
			maet.anim.unSave("maanim multiply");
			changing = false;
		});

		same.setLnr(x -> change(0, z -> setCs(findRep(mpet.part))));

		sort.setLnr(x -> Arrays.sort(maet.ma.parts));

		clea.setLnr(x -> {
			for (Part p : maet.getSelected())
				clean(p);
		});

		time.setLnr(x -> {
			int[] times = getTimeLine(maet.getSelected());

			if(times == null)
				return;

			StringBuilder str = new StringBuilder();
			for (int i : times)
				str.append(i == 0 ? "-" : "X");
			System.out.println(str);// TODO
		});

		revt.setLnr(x -> {
			MaAnim anim = ac.getMaAnim(animID);
			if (anim == null)
				return;
			if (Arrays.stream(anim.parts).anyMatch(p -> p.ints[0] == 0 && p.ints[1] == 9)) {
				Arrays.stream(anim.parts).filter(p -> p.ints[0] == 0 && p.ints[1] == 9).forEach(p -> Arrays.stream(p.moves)
						.forEach(ints -> ints[1] *= -1));
			} else {
				Part[] data = anim.parts;
				anim.parts = new Part[++anim.n];
				System.arraycopy(data, 0, anim.parts, 0, data.length);
				Part newScalePart = new Part();
				newScalePart.validate();
				newScalePart.ints[1] = 9;
				newScalePart.moves = new int[++newScalePart.n][];
				newScalePart.moves[0] = new int[] { 0, -1000, 0, 0 };
				newScalePart.validate();
				anim.parts[anim.n - 1] = newScalePart;
				anim.validate();
			}
			for (int i = 0; i < ac.mamodel.parts.length; i++) {
				int[] parts = ac.mamodel.parts[i];
				int partID = i;
				int angle = parts[10];
				if (Arrays.stream(anim.parts).anyMatch(p -> p.ints[0] == partID && p.ints[1] == 11)) {
					Arrays.stream(anim.parts)
							.filter(p -> p.ints[0] == partID && p.ints[1] == 11)
							.forEach(p -> Arrays.stream(p.moves).forEach(ints -> {
								ints[1] += angle * 2;
								ints[1] *= -1;
							}));
				} else if (angle != 0) {
					Part[] data = anim.parts; // copy parts
					anim.parts = new Part[++anim.n]; // add slot for new part
					System.arraycopy(data, 0, anim.parts, 0, data.length); // preserve parts
					Part newPart = new Part(); // create part
					newPart.validate(); // validate
					newPart.ints[0] = partID;
					newPart.ints[1] = 11; // set type to angle
					newPart.moves = new int[++newPart.n][]; // add slot for new move
					newPart.moves[0] = new int[] { 0, angle * -2, 0, 0 }; // set angle to negative ma_model angle
					newPart.validate(); // validate part
					anim.parts[anim.n - 1] = newPart; // set part to anim parts
				}
				anim.validate(); // validate animation before next ma_model part check
			}
			maet.anim.unSave("maanim revert");
			callBack(null);
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

		jtb.setLnr(arg0 -> {
			pause = jtb.isSelected();
			jtl.setEnabled(pause && ab.getEntity() != null);
		});

		nex.setLnr(e -> eupdate());

		jtl.addChangeListener(arg0 -> {
			if (isAdj() || !pause)
				return;

			if (CommonStatic.getConfig().performanceModeAnimation) {
				ab.getEntity().setTime(jtl.getValue() / 2f);
			} else {
				ab.getEntity().setTime(jtl.getValue());
			}
		});

	}

	private void addLnr$C() {
		ListSelectionModel lsm = maet.getSelectionModel();

		lsm.addListSelectionListener(e -> {
			if (isAdj() || lsm.getValueIsAdjusting())
				return;
			int[] inds = maet.getSelectedRows();
			List<Integer> l = new ArrayList<>();
			for (int i : inds)
				l.add(i);
			setCs(l);
		});

		addp.addActionListener(arg0 -> {
			change(true);
			int ind = maet.getSelectedRow() + 1;
			MaAnim ma = maet.ma;
			Part[] data = ma.parts;
			ma.parts = new Part[++ma.n];
			if (ind >= 0)
				System.arraycopy(data, 0, ma.parts, 0, ind);
			if (data.length - ind >= 0)
				System.arraycopy(data, ind, ma.parts, ind + 1, data.length - ind);
			Part np = new Part();
			np.validate();
			ma.parts[ind] = np;
			ma.validate();
			maet.anim.unSave("maanim add part");
			callBack(null);
			lsm.setSelectionInterval(ind, ind);
			setC(ind);
			int h = mpet.getRowHeight();
			mpet.scrollRectToVisible(new Rectangle(0, h * ind, 1, h));
			change(false);
		});

		remp.addActionListener(arg0 -> {
			change(true);
			MaAnim ma = maet.ma;
			int[] rows = maet.getSelectedRows();
			Part[] data = ma.parts;
			for (int row : rows)
				data[row] = null;
			ma.n -= rows.length;
			ma.parts = new Part[ma.n];
			int ind = 0;
			for (Part datum : data)
				if (datum != null)
					ma.parts[ind++] = datum;
			ind = rows[rows.length - 1];
			ma.validate();
			maet.anim.unSave("maanim remove part");
			callBack(null);
			if (ind >= ma.n)
				ind = ma.n - 1;
			lsm.setSelectionInterval(ind, ind);
			setC(ind);
			change(false);
		});

	}

	private void addLnr$D() {
		ListSelectionModel lsm = mpet.getSelectionModel();

		lsm.addListSelectionListener(e -> {
			if (isAdj() || lsm.getValueIsAdjusting())
				return;
			setD(lsm.getLeadSelectionIndex());
		});

		addl.addActionListener(arg0 -> {
			Part p = mpet.part;
			int[][] data = p.moves;
			p.moves = new int[++p.n][];
			System.arraycopy(data, 0, p.moves, 0, data.length);
			p.moves[p.n - 1] = new int[4];
			p.validate();
			maet.ma.validate();
			callBack(null);
			maet.anim.unSave("maanim add line");
			change(p.n - 1, i -> lsm.setSelectionInterval(i, i));
			setD(p.n - 1);
			int h = mpet.getRowHeight();
			mpet.scrollRectToVisible(new Rectangle(0, h * (p.n - 1), 1, h));
		});

		reml.addActionListener(arg0 -> {
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
		});
	}

	private void clean(Part p) {
		if (p.off != 0 || p.ints[2] != 1 || p.n < 4)
			return;
		for (int i = 2; i < p.n - 1; i++) {
			boolean suc = true;
			for (int j = 0; j < p.n; j++) {
				boolean mat;
				int[] i0 = p.moves[j];
				int[] i1 = p.moves[j % i];
				mat = i0[1] == i1[1];
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
		if (ab.getEntity() != null) {
			int selection;

			if (CommonStatic.getConfig().performanceModeAnimation) {
				selection = (int) (ab.getEntity().ind() * 2);
			} else {
				selection = (int) ab.getEntity().ind();
			}

			change(0, x -> jtl.setValue(selection));
		}
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
			boolean pass = true;
			for (int j = 0; j < p.n; j++)
				for (int k = 0; k < 4; k++)
					if (p.moves[j][k] != pi.moves[j][k]) {
						pass = false;
						break;
					}
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
		add((Canvas) ab);
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
		add(revt);
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
		maet.setAnim(ac, ac.getMaAnim(animID));
		ab.setEntity(ac.getEAnim(animID));
		if (row >= maet.getRowCount()) {
			maet.clearSelection();
			row = -1;
		}
		setC(row);
		jtl.setPaintTicks(true);
		jtl.setPaintLabels(true);
		jtl.setMinimum(0);

		if (CommonStatic.getConfig().performanceModeAnimation) {
			jtl.setMaximum(ab.getEntity().len() * 2);
		} else {
			jtl.setMaximum(ab.getEntity().len());
		}

		jtl.setLabelTable(null);

		if (ab.getEntity().len() <= 50) {
			jtl.setMajorTickSpacing(5);
			jtl.setMinorTickSpacing(1);
		} else if (ab.getEntity().len() <= 200) {
			jtl.setMajorTickSpacing(10);
			jtl.setMinorTickSpacing(2);
		} else if (ab.getEntity().len() <= 1000) {
			jtl.setMajorTickSpacing(50);
			jtl.setMinorTickSpacing(10);
		} else if (ab.getEntity().len() <= 5000) {
			jtl.setMajorTickSpacing(250);
			jtl.setMinorTickSpacing(50);
		} else {
			jtl.setMajorTickSpacing(1000);
			jtl.setMinorTickSpacing(200);
		}

		if (CommonStatic.getConfig().performanceModeAnimation) {
			Hashtable<Integer, JLabel> labels = new Hashtable<>();

			int f = 0;
			int gap;

			if (ab.getEntity().len() <= 50) {
				gap = 5;
			} else if (ab.getEntity().len() <= 200) {
				gap = 10;
			} else if (ab.getEntity().len() <= 1000) {
				gap = 50;
			} else if (ab.getEntity().len() <= 5000) {
				gap = 250;
			} else {
				gap = 1000;
			}

			while (f <= ab.getEntity().len()) {
				labels.put(f * 2, new JLabel(String.valueOf(f)));

				f += gap;
			}

			jtl.setLabelTable(labels);
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
			if(p != null) {
				int par = p.ints[0];
				mmt.select(par);
				jlv.setSelectedIndex(mpet.part.ints[1]);
				if (maet.getSelectedRow() != ind) {
					maet.setRowSelectionInterval(ind, ind);
					maet.scrollRectToVisible(maet.getCellRect(ind, 0, true));
				}
				ab.setSele(par);
			}
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
