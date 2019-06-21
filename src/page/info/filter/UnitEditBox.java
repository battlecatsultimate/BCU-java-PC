package page.info.filter;

import static common.util.Interpret.ABIIND;
import static common.util.Interpret.IMUSFT;
import static common.util.Interpret.SABIS;
import static common.util.Interpret.SPROC;
import static common.util.Interpret.TRAIT;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import page.Page;

public class UnitEditBox extends Page {

	private static final long serialVersionUID = 1L;

	private final boolean editable;

	private final Vector<String> vt = new Vector<>();
	private final Vector<String> va = new Vector<>();
	private final AttList trait = new AttList(3, 0);
	private final AttList abis = new AttList(0, 1);
	private final JScrollPane jt = new JScrollPane(trait);
	private final JScrollPane jab = new JScrollPane(abis);

	private boolean changing = false;

	public UnitEditBox(Page p, boolean edit) {
		super(p);
		editable = edit;
		ini();
	}

	public void setData(int[] vals) {
		changing = true;
		trait.clearSelection();
		abis.clearSelection();
		for (int i = 0; i < 9; i++)
			if (((vals[0] >> i) & 1) > 0)
				trait.addSelectionInterval(i, i);
		for (int i = 0; i < SABIS.length; i++) {
			if (((vals[1] >> i) & 1) > 0)
				abis.addSelectionInterval(i, i);
		}
		int lev = SABIS.length;
		for (int i = 0; i < ABIIND.length; i++) {
			int ind = ABIIND[i];
			if (((vals[2] >> (ind - 100 - IMUSFT)) & 1) > 0)
				abis.addSelectionInterval(lev + i, lev + i);
		}
		changing = false;
	}

	@Override
	protected void resized(int x, int y) {
		set(jt, x, y, 0, 0, 200, 400);
		set(jab, x, y, 0, 450, 200, 750);
	}

	private void confirm() {
		int[] ans = new int[3];
		for (int i = 0; i < 9; i++)
			if (trait.isSelectedIndex(i))
				ans[0] |= 1 << i;
		int lev = SABIS.length;
		for (int i = 0; i < lev; i++)
			if (abis.isSelectedIndex(i))
				ans[1] |= 1 << i;
		for (int i = 0; i < ABIIND.length; i++)
			if (abis.isSelectedIndex(lev + i))
				ans[2] |= 1 << (ABIIND[i] - 100 - IMUSFT);

		getFront().callBack(ans);
	}

	private void ini() {
		for (int i = 0; i < 9; i++)
			vt.add(TRAIT[i]);
		for (int i = 0; i < SABIS.length; i++)
			va.add(SABIS[i]);
		for (int i = 0; i < ABIIND.length; i++)
			va.add(SPROC[ABIIND[i] - 100]);
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

}
