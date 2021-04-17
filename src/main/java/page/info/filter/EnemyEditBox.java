package page.info.filter;

import common.battle.data.CustomEnemy;
import common.pack.Identifier;
import common.pack.PackData.UserPack;
import common.pack.UserProfile;
import common.util.unit.Trait;
import page.Page;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import static utilpc.Interpret.*;

public class EnemyEditBox extends Page {

	private static final long serialVersionUID = 1L;

	private final boolean editable;

	private final Vector<String> vt = new Vector<>();
	private final Vector<String> va = new Vector<>();
	private final AttList abis = new AttList(-1, EABIIND.length);
	private final AttList trait = new AttList(3, 0);
	private final JScrollPane jt = new JScrollPane(trait);
	private final JScrollPane jab = new JScrollPane(abis);

	private boolean changing = false;
	private final List<Trait> traitList;
	private final CustomEnemy ce;

	public EnemyEditBox(Page p, UserPack pack, CustomEnemy cen) {
		super(p);
		editable = pack.editable;
		traitList = new ArrayList<>(pack.traits.getList());
		Collection<UserPack> pacs = UserProfile.getUserPacks();
		for (UserPack pacc : pacs) {
			if (!pacc.desc.id.equals("000000") && (pack.desc.dependency.contains(pacc.desc.id)))
				traitList.addAll(pacc.traits.getList());
		}
		ce = cen;
		ini();
	}

	public void diyIni(ArrayList<Identifier<Trait>> cts) {
		changing = true;
		for (int k = 0; k < traitList.size(); k++)
			if (cts.contains(traitList.get(k).id))
				trait.addSelectionInterval(k + 9, k + 9);
			else
				trait.removeSelectionInterval(k + 9, k + 9);
	}

	public void setData(int[] vals) {
		changing = true;
		int[] sel = trait.getSelectedIndices();
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
		for (int area : sel)
			if (area >= 9)
				trait.addSelectionInterval(area, area);
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
		for (int i = 0; i < traitList.size(); i++)
			if (trait.isSelectedIndex(i + 9)) {
				if (!ce.customTraits.contains(traitList.get(i).id)) {
					ce.customTraits.add(traitList.get(i).id);
					ce.nullFixer.add(traitList.get(i).id.toString());
				}
			} else {
				ce.customTraits.remove(traitList.get(i).id);
				ce.nullFixer.remove(traitList.get(i).id.toString());
			}
		getFront().callBack(ans);
	}

	private void ini() {
		for (int i = 0; i < 9; i++)
			vt.add(TRAIT[i]);
		for (Trait diyTrait : traitList)
			vt.add(diyTrait.name);
		for (int i = 0; i < EABIIND.length; i++)
			va.add(EABI[i]);
		customTraitsIco(trait,traitList);
		trait.setListData(vt);
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

		jl.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!changing && !jl.getValueIsAdjusting())
					confirm();
			}
		});
	}
	protected static void customTraitsIco(AttList trait, List<Trait> diyTraits) {
		trait.diyTraitIcons(trait, diyTraits, false);
	}
}
