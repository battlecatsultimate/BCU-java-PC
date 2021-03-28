package page.info;

import common.CommonStatic;
import common.battle.BasisSet;
import common.system.Node;
import main.MainBCU;
import page.JTF;
import page.JTG;
import page.Page;
import page.basis.BasisPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import static utilpc.Interpret.*;

public class TreaTable extends Page {

	private static final long serialVersionUID = 1L;

	public static String tos(int v, int ind) {
		if (v == -1)
			return "---";
		if (ind > 0 && ind < 9 || ind == -1)
			return "Lv." + v;
		if (ind > 29 || ind == -6)
			return "Lv." + v;
		return v + "%";
	}

	private final JLabel[] jln = new JLabel[TREA.length];

	private final JTF[] jtf = new JTF[TREA.length];
	private final JTG[] jlb = new JTG[TCOLP.length];
	private final JTF[] jcf = new JTF[TCOLP.length];

	private final List<Node<Integer>> lncs = new ArrayList<>();

	private Node<Integer> nc, cur, colp;

	private BasisSet b;

	public TreaTable(Page p, BasisSet bas) {
		super(p);

		b = bas;
		ini();
		reset();
	}

	@Override
	public boolean hasFocus() {
		if (super.hasFocus())
			return true;
		for (JTF c : jcf)
			if (c.hasFocus())
				return true;
		for (JTF c : jtf)
			if (c.hasFocus())
				return true;
		return false;
	}

	public void setBasis(BasisSet bas) {
		b = bas;
		reset();
	}

	@Override
	public void resized(int x, int y) {
		int i = 0;
		for (Node<Integer> n = nc; n != null; n = n.next) {
			if (n.val > 0) {
				int ind = n.val - 1;
				set(jln[ind], x, y, 0, 50 * i, 200, 50);
				set(jtf[ind], x, y, 200, 50 * i, 200, 50);
			} else {
				int ind = -n.val - 1;
				set(jlb[ind], x, y, 0, 50 * i, 200, 50);
				set(jcf[ind], x, y, 200, 50 * i, 200, 50);
			}
			i++;
		}
	}

	private void close(Node<Integer> n) {
		n.removes();
		List<Integer> l = n.sides();
		for (int s : l) {
			remove(jln[s - 1]);
			remove(jtf[s - 1]);
		}
	}

	private void expand(Node<Integer> n) {
		List<Integer> l = n.sides();
		for (int s : l) {
			add(jln[s - 1]);
			add(jtf[s - 1]);
		}
		n.adds();
	}

	private void ini() {
		int j = 0, k = 0;
		for (int i = 0; i < TREA.length; i++) {
			if (j < TCOLP.length && i == TCOLP[j][0]) {
				k = TCOLP[j][1];
				add(jlb[j] = new JTG(TCTX[j]));
				add(jcf[j] = new JTF());
				lncs.add(colp = new Node<>(-j - 1));
				if (nc == null)
					cur = nc = colp;
				else
					cur = cur.add(colp);
				int J = j;
				j++;

				jlb[J].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Node<Integer> n = lncs.get(J);
						if (jlb[J].isSelected()) {
							for (int s = 0; s < TCTX.length; s++)
								if (s != J && jlb[s].isSelected())
									jlb[s].doClick();
							expand(n);
						} else {
							close(n);
						}
						if (front instanceof BasisPage)
							((BasisPage) front).requireResize();
					}

				});

				jcf[J].addFocusListener(new FocusAdapter() {

					@Override
					public void focusLost(FocusEvent e) {
						int ans = getComp(J, b.t());
						int val = Math.abs(CommonStatic.parseIntN(jcf[J].getText()));
						setComp(J, val == 0 ? ans : val, b);
						reset();
						getFront().callBack(null);
					}

				});
			}

			int ind = TIND[i];
			int I = i;
			jln[i] = new JLabel(TREA[ind]);
			jtf[i] = new JTF(tos(getValue(ind, b.t()), i));
			Node<Integer> temp = new Node<>(i + 1);
			if (nc == null)
				cur = nc = temp;
			else
				cur = cur.add(temp);
			if (k <= 0) {
				add(jln[i]);
				add(jtf[i]);
			} else
				jln[i].setForeground(MainBCU.light ? Color.BLUE : new Color(84, 110, 122));
			if (--k == 0)
				cur = colp.side(cur);

			jln[i].setBorder(BorderFactory.createEtchedBorder());

			jtf[i].addFocusListener(new FocusAdapter() {

				@Override
				public void focusLost(FocusEvent e) {
					int ans = getValue(ind, b.t());
					int val = Math.abs(CommonStatic.parseIntN(jtf[I].getText()));
					setValue(ind, val == 0 ? ans : val, b);
					reset();
					getFront().callBack(null);
				}

			});

		}

	}

	private void reset() {
		for (int i = 0; i < TREA.length; i++)
			jtf[i].setText(tos(getValue(TIND[i], b.t()), i + 1));
		for (int i = 0; i < TCTX.length; i++)
			jcf[i].setText(tos(getComp(i, b.t()), -i - 1));
	}

	public int getPWidth() {
		return 200;
	}

	public int getPHeight() {
		return nc.len() * 31;
	}
}
