package page.info.filter;

import common.battle.data.CustomEnemy;
import common.pack.PackData.UserPack;
import common.util.unit.CustomTrait;
import page.Page;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;
import java.util.Vector;

import static utilpc.Interpret.*;

public class EnemyEditBox extends Page {

	private static final long serialVersionUID = 1L;

	private final boolean editable;

	private final Vector<String> vt = new Vector<>();
	private final Vector<String> va = new Vector<>();
	private final AttList trait = new AttList(3, 0);
	private final AttList abis = new AttList(-1, EABIIND.length);
	private final JScrollPane jt = new JScrollPane(trait);
	private final JScrollPane jab = new JScrollPane(abis);

	private boolean changing = false;
	private final List<CustomTrait> diyTraits;
	private final CustomEnemy ce;

	public EnemyEditBox(Page p, UserPack pack, CustomEnemy cen) {
		super(p);
		editable = pack.editable;
		diyTraits = pack.diyTrait.getList();
		ce = cen;
		ini();
	}

	public void setData(int[] vals) {
		changing = true;
		trait.clearSelection();
		for (int i = 0; i < 9; i++)
			if (((vals[0] >> i) & 1) > 0)
				trait.addSelectionInterval(i, i);
		abis.clearSelection();
		for (int i = 0; i < EABIIND.length; i++) {
			int ind = EABIIND[i];
			if (ind < 100 ? ((vals[1] >> ind) & 1) > 0 : ((vals[2] >> (ind - 100 - IMUSFT)) & 1) > 0)
				abis.addSelectionInterval(i, i);
		}
		for (int k = 0; k < diyTraits.size(); k++) {
			if (ce.customTraits.contains(diyTraits.get(k).id))
				trait.addSelectionInterval(k + 9, k + 9);
		}
		changing = false;
	}

	@Override
	protected void resized(int x, int y) {
		set(jt, x, y, 0, 0, 300, 500);
		set(jab, x, y, 300, 0, 300, 500);
	}

	private void confirm() {
		int[] ans = new int[3];
		for (int i = 0; i < 9; i++)
			if (trait.isSelectedIndex(i))
				ans[0] |= 1 << i;

		for (int i = 0; i < EABIIND.length; i++)
			if (abis.isSelectedIndex(i))
				if (EABIIND[i] < 100)
					ans[1] |= 1 << EABIIND[i];
				else
					ans[2] |= 1 << (EABIIND[i] - 100 - IMUSFT);
		getFront().callBack(ans);
	}

	public void cconfirm() {
		for (int i = 0; i < diyTraits.size(); i++)
			if (trait.isSelectedIndex(i + 9)) {
				if (!ce.customTraits.contains(diyTraits.get(i).id))
					ce.customTraits.add(diyTraits.get(i).id);
			} else ce.customTraits.remove(diyTraits.get(i).id);
	}

	private void ini() {
		for (int i = 0; i < 9; i++)
			vt.add(TRAIT[i]);
		for (int k = 0; k < diyTraits.size(); k++)
			vt.add(diyTraits.get(k).name);
		for (int i = 0; i < EABIIND.length; i++)
			va.add(EABI[i]);
		trait.setListData(vt);
		abis.setListData(va);
		int m = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
		inidiytraitcheck();
		trait.setSelectionMode(m);
		abis.setSelectionMode(m);
		set(trait);
		set(abis);
		add(jt);
		add(jab);
		trait.setEnabled(editable);
		abis.setEnabled(editable);
	}

	private void inidiytraitcheck() {
		for (int k = 0; k < diyTraits.size(); k++) {
			if (ce.customTraits.contains(diyTraits.get(k).id))
				trait.addSelectionInterval(k + 9, k + 9);
		}
	};

	private void set(JList<?> jl) {

		jl.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!changing && !jl.getValueIsAdjusting())
					confirm();
			}
		});
	}

}
