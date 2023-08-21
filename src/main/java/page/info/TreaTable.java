package page.info;

import common.CommonStatic;
import common.battle.BasisLU;
import common.battle.BasisSet;
import common.system.Node;
import common.util.Data;
import main.MainBCU;
import page.JTF;
import page.JTG;
import page.Page;

import javax.swing.*;
import java.awt.*;
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

	public TreaTable(Page p) {
		super(p);

		ini();
		reset();
	}

	@Override
	protected JButton getBackButton() {
		return null;
	}

	@Override
	public void callBack(Object o) {
		if (o == null)
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

				jlb[J].addActionListener(arg0 -> {
					Node<Integer> n = lncs.get(J);
					if (jlb[J].isSelected()) {
						for (int s = 0; s < TCTX.length; s++)
							if (s != J && jlb[s].isSelected())
								jlb[s].doClick();
						expand(n);
					} else {
						close(n);
					}

					front.resized(true);
				});

				jcf[J].addFocusListener(new FocusAdapter() {

					@Override
					public void focusLost(FocusEvent e) {
						int val = Math.abs(CommonStatic.parseIntN(jcf[J].getText()));
						setComp(J, val, BasisSet.current());
						reset();
						getFront().callBack(null);
					}

				});
			}

			int ind = TIND[i];
			int I = i;
			jln[i] = new JLabel(TREA[ind]);
			jtf[i] = new JTF(tos(getValue(ind, BasisSet.current().t()), i));
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
					int val = Math.abs(CommonStatic.parseIntN(jtf[I].getText()));
					setValue(ind, val, BasisSet.current());
					reset();
					getFront().callBack(null);
				}

			});
		}
	}

	private void reset() {
		BasisLU b = BasisSet.current().sele;
		for (int i = 0; i < TREA.length; i++)
			jtf[i].setText(tos(getValue(TIND[i], b.t()), i + 1));
		for (int i = 0; i < TCTX.length; i++)
			jcf[i].setText(tos(getComp(i, b.t()), -i - 1));

		int slowTime = (int) (b.t().getCannonMagnification(1, Data.BASE_SLOW_TIME) * (100 + b.getInc(C_SLOW)) / 100.0);

		int ironWallHP = (int) (5 * b.t().getCannonMagnification(BASE_WALL, BASE_WALL_MAGNIFICATION) / 100);
		int ironWallTime = (int) b.t().getCannonMagnification(2, Data.BASE_WALL_ALIVE_TIME);

		int freezeTime = (int) (b.t().getCannonMagnification(3, Data.BASE_TIME) * (100 + b.getInc(C_STOP)) / 100.0);
		int freezeAttack = (int) (b.t().getCannonMagnification(3, Data.BASE_ATK_MAGNIFICATION));

		int waterAttack = (int) (b.t().getCannonMagnification(4, Data.BASE_HEALTH_PERCENTAGE));

		int undergroundAttack = (int) (b.t().getCannonMagnification(5, Data.BASE_HOLY_ATK_UNDERGROUND) * 100.0);
		int surfaceAttack = (int) (b.t().getCannonMagnification(5, Data.BASE_HOLY_ATK_SURFACE) * 100.0);
		int holyFreezeTime = (int) (b.t().getCannonMagnification(5, Data.BASE_TIME) * (100 + b.getInc(C_STOP)) / 100.0);

		int breakerBlastAttack = (int) (b.t().getCannonMagnification(6, Data.BASE_ATK_MAGNIFICATION));
		int breakerBlastPiercing = (int) b.t().getCannonMagnification(6, Data.BASE_RANGE);

		int curseTime = (int) b.t().getCannonMagnification(7, Data.BASE_CURSE_TIME);

		jtf[30].setToolTipText("This cannon will slow enemies for " + slowTime + " frames.");
		jtf[31].setToolTipText("<html>This cannon has multiple properties.<br>" +
				"The Iron Wall will have " + ironWallHP + " health.<br>" +
				"The Iron Wall will last " + ironWallTime + " frames after its enter animation.</html>");
		jtf[32].setToolTipText("<html>This cannon has multiple properties.<br>"+
				"It will freeze enemies for " + freezeTime + " frames.<br>" +
				"It will deal " + freezeAttack + "% of base cannon damage.</html>");
		jtf[33].setToolTipText("This cannon will deal " + waterAttack + "% of remaining HP to metals, while dealing 0.1% of remaining HP to non-metals.");
		jtf[34].setToolTipText("<html>This cannon has multiple properties.<br>" +
				"It will deal " + undergroundAttack + "% of maximum HP to burrowing zombies.<br>" +
				"It will deal " + surfaceAttack + "% of maximum HP is dealt to non-burrowing zombies.<br>" +
				"It will freeze enemies for " + holyFreezeTime + " frames.</html>");
		jtf[35].setToolTipText("<html>This cannon has multiple properties.<br>" +
				"It will knock back enemies with " + breakerBlastPiercing + " piercing range from the frontmost enemy.<br>" +
				"It will deal " + breakerBlastAttack + "% of base cannon damage.</html>");
		jtf[36].setToolTipText("This cannon will curse enemies for " + curseTime + " frames.");
	}

	public int getPWidth() {
		return 400;
	}

	public int getPHeight() {
		return nc.len() * 50;
	}
}
