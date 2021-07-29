package page.info.filter;

import page.Page;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import static utilpc.Interpret.*;

public class EnemyEditBox extends Page {

	private static final long serialVersionUID = 1L;

	private final boolean editable;

	private final int[] traitIndex = {0, 1, 2, 3, 4, 5, 6, 12, 7, 8};

	private final Vector<String> vt = new Vector<>();
	private final Vector<String> va = new Vector<>();
	private final AttList trait = new AttList(3, 0, 1, 2, 3, 4, 5, 6, 12, 7, 8);
	private final AttList abis = new AttList(-1, EABIIND.length);
	private final JScrollPane jt = new JScrollPane(trait);
	private final JScrollPane jab = new JScrollPane(abis);

	private boolean changing = false;

	public EnemyEditBox(Page p, boolean edit) {
		super(p);
		editable = edit;
		ini();
	}

	public void setData(int[] vals) {
		changing = true;
		trait.clearSelection();
		for (int i = 0; i < traitIndex.length; i++)
			if (((vals[0] >> traitIndex[i]) & 1) > 0)
				trait.addSelectionInterval(i, i);
		abis.clearSelection();
		for (int i = 0; i < EABIIND.length; i++) {
			int ind = EABIIND[i];
			if (ind < 100 ? ((vals[1] >> ind) & 1) > 0 : ((vals[2] >> (ind - 100 - IMUSFT)) & 1) > 0)
				abis.addSelectionInterval(i, i);
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
		for (int i = 0; i < traitIndex.length; i++)
			if (trait.isSelectedIndex(i))
				ans[0] |= 1 << traitIndex[i];

		for (int i = 0; i < EABIIND.length; i++)
			if (abis.isSelectedIndex(i))
				if (EABIIND[i] < 100)
					ans[1] |= 1 << EABIIND[i];
				else
					ans[2] |= 1 << (EABIIND[i] - 100 - IMUSFT);
		getFront().callBack(ans);
	}

	private void ini() {
		ArrayList<String> traitData = new ArrayList<>();

		for(int i : traitIndex)
			traitData.add(TRAIT[i]);

		vt.addAll(traitData);

		va.addAll(Arrays.asList(EABI).subList(0, EABIIND.length));

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
		jl.addListSelectionListener(arg0 -> {
			if (!changing && !jl.getValueIsAdjusting())
				confirm();
		});
	}

}
