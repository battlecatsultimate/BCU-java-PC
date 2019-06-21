package page.info.filter;

import static common.util.Interpret.ATKCONF;
import static common.util.Interpret.RARITY;
import static common.util.Interpret.SABIS;
import static common.util.Interpret.SPROC;
import static common.util.Interpret.TRAIT;
import static common.util.Interpret.isType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import common.util.entity.data.MaskUnit;
import common.util.pack.Pack;
import common.util.unit.Form;
import common.util.unit.Unit;
import common.util.unit.UnitStore;
import main.MainBCU;
import page.JTG;
import page.Page;
import utilpc.UtilPC;

public abstract class UnitFilterBox extends Page {

	private static final long serialVersionUID = 1L;

	public static UnitFilterBox getNew(Page p, Pack pack) {
		if (MainBCU.FILTER_TYPE == 0)
			return new UFBButton(p, pack);
		if (MainBCU.FILTER_TYPE == 1)
			return new UFBList(p, pack);
		return null;
	}

	protected Pack pac;

	protected UnitFilterBox(Page p, Pack pack) {
		super(p);
		pac = pack;
	}

	public abstract int[] getSizer();

}

class UFBButton extends UnitFilterBox {

	private static final long serialVersionUID = 1L;

	private final JTG[] orop = new JTG[3];
	private final JTG[] rare = new JTG[RARITY.length];
	private final JTG[] trait = new JTG[9];
	private final JTG[] abis = new JTG[SABIS.length];
	private final JTG[] proc = new JTG[SPROC.length];
	private final JTG[] atkt = new JTG[ATKCONF.length];

	protected UFBButton(Page p, Pack pack) {
		super(p, pack);

		ini();
		confirm();
	}

	@Override
	public int[] getSizer() {
		return new int[] { 2000, 400, 1, 400 };
	}

	@Override
	protected void resized(int x, int y) {
		JTG[][] btns = new JTG[][] { rare, trait, abis, proc, atkt };
		AttList.btnDealer(x, y, btns, orop, -1, 0, 1, -1, 2);
	}

	private void confirm() {
		List<Form> ans = new ArrayList<>();
		for (Unit u : UnitStore.getAll(pac, true))
			for (Form f : u.forms) {
				MaskUnit du = f.maxu();
				int t = du.getType();
				int a = du.getAbi();
				boolean b0 = rare[u.rarity].isSelected();
				boolean b1 = !orop[0].isSelected();
				for (int i = 0; i < trait.length; i++)
					if (trait[i].isSelected())
						if (orop[0].isSelected())
							b1 |= ((t >> i) & 1) == 1;
						else
							b1 &= ((t >> i) & 1) == 1;
				boolean b2 = !orop[1].isSelected();
				for (int i = 0; i < abis.length; i++)
					if (abis[i].isSelected()) {
						boolean bind = ((a >> i) & 1) == 1;
						if (orop[1].isSelected())
							b2 |= bind;
						else
							b2 &= bind;
					}
				for (int i = 0; i < proc.length; i++)
					if (proc[i].isSelected())
						if (orop[1].isSelected())
							b2 |= du.getAllProc(i)[0] > 0;
						else
							b2 &= du.getAllProc(i)[0] > 0;
				boolean b3 = !orop[2].isSelected();
				for (int i = 0; i < atkt.length; i++)
					if (atkt[i].isSelected())
						if (orop[2].isSelected())
							b3 |= isType(du, i);
						else
							b3 &= isType(du, i);
				b0 = nonSele(rare) | b0;
				b1 = nonSele(trait) | b1;
				b2 = nonSele(abis) & nonSele(proc) | b2;
				b3 = nonSele(atkt) | b3;
				if (b0 & b1 & b2 & b3)
					ans.add(f);
			}
		getFront().callBack(ans);
	}

	private void ini() {
		for (int i = 0; i < orop.length; i++)
			set(orop[i] = new JTG(0, "orop"));
		for (int i = 0; i < rare.length; i++)
			set(rare[i] = new JTG(RARITY[i]));
		for (int i = 0; i < trait.length; i++) {
			set(trait[i] = new JTG(TRAIT[i]));
			BufferedImage v = UtilPC.getIcon(3, i);
			if (v == null)
				continue;
			trait[i].setIcon(new ImageIcon(v));
		}
		for (int i = 0; i < abis.length; i++) {
			set(abis[i] = new JTG(SABIS[i]));
			BufferedImage v = UtilPC.getIcon(0, i);
			if (v == null)
				continue;
			abis[i].setIcon(new ImageIcon(v));
		}
		for (int i = 0; i < proc.length; i++) {
			set(proc[i] = new JTG(SPROC[i]));
			BufferedImage v = UtilPC.getIcon(1, i);
			if (v == null)
				continue;
			proc[i].setIcon(new ImageIcon(v));
		}
		for (int i = 0; i < atkt.length; i++) {
			set(atkt[i] = new JTG(ATKCONF[i]));
			BufferedImage v = UtilPC.getIcon(2, i);
			if (v == null)
				continue;
			atkt[i].setIcon(new ImageIcon(v));
		}
	}

	private boolean nonSele(JTG[] jtbs) {
		int n = 0;
		for (int i = 0; i < jtbs.length; i++)
			if (jtbs[i].isSelected())
				n++;
		return n == 0;
	}

	private void set(AbstractButton b) {
		add(b);
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				confirm();
			}

		});
	}

}

class UFBList extends UnitFilterBox {

	private static final long serialVersionUID = 1L;

	private final JTG[] orop = new JTG[3];
	private final JList<String> rare = new JList<>(RARITY);
	private final Vector<String> vt = new Vector<>();
	private final Vector<String> va = new Vector<>();
	private final AttList trait = new AttList(3, 0);
	private final AttList abis = new AttList(0, 0);
	private final AttList atkt = new AttList(2, 0);
	private final JScrollPane jr = new JScrollPane(rare);
	private final JScrollPane jt = new JScrollPane(trait);
	private final JScrollPane jab = new JScrollPane(abis);
	private final JScrollPane jat = new JScrollPane(atkt);

	protected UFBList(Page p, Pack pack) {
		super(p, pack);

		ini();
		confirm();
	}

	@Override
	public int[] getSizer() {
		return new int[] { 450, 1150, 0, 500 };
	}

	@Override
	protected void resized(int x, int y) {
		set(orop[0], x, y, 0, 350, 200, 50);
		set(orop[1], x, y, 250, 0, 200, 50);
		set(orop[2], x, y, 0, 800, 200, 50);

		set(jr, x, y, 0, 50, 200, 250);
		set(jt, x, y, 0, 400, 200, 350);
		set(jab, x, y, 250, 50, 200, 1100);
		set(jat, x, y, 0, 850, 200, 300);
	}

	private void confirm() {
		List<Form> ans = new ArrayList<>();
		for (Unit u : UnitStore.getAll(pac, true))
			for (Form f : u.forms) {
				MaskUnit du = f.maxu();
				int t = du.getType();
				int a = du.getAbi();
				boolean b0 = rare.isSelectedIndex(u.rarity);
				boolean b1 = !orop[0].isSelected();
				for (int i : trait.getSelectedIndices())
					if (orop[0].isSelected())
						b1 |= ((t >> i) & 1) == 1;
					else
						b1 &= ((t >> i) & 1) == 1;
				boolean b2 = !orop[1].isSelected();
				int len = SABIS.length;
				for (int i : abis.getSelectedIndices())
					if (i < len) {
						boolean bind = ((a >> i) & 1) == 1;
						if (orop[1].isSelected())
							b2 |= bind;
						else
							b2 &= bind;
					} else if (orop[1].isSelected())
						b2 |= du.getAllProc(i - len)[0] > 0;
					else
						b2 &= du.getAllProc(i - len)[0] > 0;
				boolean b3 = !orop[2].isSelected();
				for (int i : atkt.getSelectedIndices())
					if (orop[2].isSelected())
						b3 |= isType(du, i);
					else
						b3 &= isType(du, i);
				b0 = rare.getSelectedIndex() == -1 | b0;
				b1 = trait.getSelectedIndex() == -1 | b1;
				b2 = abis.getSelectedIndex() == -1 | b2;
				b3 = atkt.getSelectedIndex() == -1 | b3;
				if (b0 & b1 & b2 & b3)
					ans.add(f);
			}
		getFront().callBack(ans);
	}

	private void ini() {
		for (int i = 0; i < orop.length; i++)
			set(orop[i] = new JTG(get(0, "orop")));
		for (int i = 0; i < 9; i++)
			vt.add(TRAIT[i]);
		for (int i = 0; i < SABIS.length; i++)
			va.add(SABIS[i]);
		for (int i = 0; i < SPROC.length; i++)
			va.add(SPROC[i]);
		trait.setListData(vt);
		abis.setListData(va);
		atkt.setListData(ATKCONF);
		set(rare);
		set(trait);
		set(abis);
		set(atkt);
		add(jr);
		add(jt);
		add(jab);
		add(jat);
	}

	private void set(AbstractButton b) {
		add(b);
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				confirm();
			}

		});
	}

	private void set(JList<?> jl) {
		int m = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
		jl.setSelectionMode(m);

		jl.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				confirm();
			}

		});
	}

}
