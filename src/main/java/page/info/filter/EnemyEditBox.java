package page.info.filter;

import common.battle.data.CustomEnemy;
import common.pack.PackData.UserPack;
import common.pack.UserProfile;
import common.util.unit.Trait;
import page.Page;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import static utilpc.Interpret.*;

public class EnemyEditBox extends Page {

	private static final long serialVersionUID = 1L;

	private final boolean editable;

	private final Vector<String> va = new Vector<>();
	private final AttList abis = new AttList(-1, EABIIND.length);
	private final TraitList trait = new TraitList(false);
	private final JScrollPane jt;
	private final JScrollPane jab = new JScrollPane(abis);

	private boolean changing = false;
	private final CustomEnemy ce;

	public EnemyEditBox(Page p, UserPack pack, CustomEnemy cen) {
		super(p);
		editable = pack.editable;
		trait.list.addAll(UserProfile.getBCData().traits.getList().subList(TRAIT_RED,TRAIT_EVA));
		trait.list.add(UserProfile.getBCData().traits.get(TRAIT_BARON));
		trait.list.add(UserProfile.getBCData().traits.get(TRAIT_BEAST));
		trait.list.addAll(pack.traits.getList());
		for (UserPack pacc : UserProfile.getUserPacks())
			if (pack.desc.dependency.contains(pacc.desc.id))
				trait.list.addAll(pacc.traits.getList());
		jt = new JScrollPane(trait);
		ce = cen;
		ini();
	}

	public void setData(int val, ArrayList<Trait> cts) {
		changing = true;
		for (int k = 0; k < trait.list.size(); k++)
			if (cts.contains(trait.list.get(k)))
				trait.addSelectionInterval(k, k);
			else
				trait.removeSelectionInterval(k, k);
		int[] sel = trait.getSelectedIndices();
		trait.clearSelection();
		abis.clearSelection();
		for (int i = 0; i < EABIIND.length; i++) {
			int ind = EABIIND[i];
			if (((val >> ind) & 1) > 0)
				abis.addSelectionInterval(i, i);
		}
		for (int area : sel)
			trait.addSelectionInterval(area, area);
		changing = false;
	}

	@Override
	protected void resized(int x, int y) {
		set(jt, x, y, 0, 0, 300, 500);
		set(jab, x, y, 300, 0, 300, 500);
	}

	private void confirm() {
		int[] ans = new int[2];
		for (int i = 0; i < EABIIND.length; i++)
			if (abis.isSelectedIndex(i))
				ans[0] |= 1 << EABIIND[i];
		for (int i = 0; i < trait.list.size(); i++)
			if (trait.isSelectedIndex(i)) {
				if (!ce.traits.contains(trait.list.get(i))) {
					ce.traits.add(trait.list.get(i));
				}
			} else
				ce.traits.remove(trait.list.get(i));
		getFront().callBack(ans);
	}

	private void ini() {
		va.addAll(Arrays.asList(EABI).subList(0, EABIIND.length));
		trait.setListData();
		abis.setListData(va);
		int m = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
		trait.setSelectionMode(m);
		abis.setSelectionMode(m);
		set(trait);
		set(abis);
		add(jt);
		add(jab);
		trait.setEnabled(editable);
		abis.setEnabled(editable);
	}

	private void set(JList<?> jl) {

		jl.addListSelectionListener(arg0 -> {
			if (!changing && !jl.getValueIsAdjusting())
				confirm();
		});
	}
}
