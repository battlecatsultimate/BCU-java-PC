package page.anim;

import common.CommonStatic;
import common.util.anim.AnimCE;
import common.util.anim.MaModel;
import page.Page;
import page.support.AnimTable;
import page.support.AnimTableTH;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.EventObject;

class MaModelEditTable extends AnimTable<int[]> {

	private static final long serialVersionUID = 1L;

	private static final String[] strs = new String[] { "id", "parent", "img", "z-order", "pos-x", "pos-y", "pivot-x",
			"pivot-y", "scale-x", "scale-y", "angle", "opacity", "glow", "name" };

	protected AnimCE anim;
	protected MaModel mm;

	private final Page page;

	protected MaModelEditTable(Page p) {
		super(strs);

		selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		setTransferHandler(new AnimTableTH<>(this, 1));
		page = p;
	}

	@Override
	public boolean editCellAt(int row, int column, EventObject e) {
		boolean result = super.editCellAt(row, column, e);
		final Component editor = getEditorComponent();
		if (!(editor instanceof JTextComponent))
			return result;
		JTextComponent jtc = (JTextComponent) editor;
		if (e instanceof KeyEvent)
			jtc.selectAll();
		return result;
	}

	@Override
	public Class<?> getColumnClass(int c) {
		if (lnk[c] == 13)
			return String.class;
		return Integer.class;
	}

	@Override
	public int getColumnCount() {
		return strs.length;
	}

	@Override
	public String getColumnName(int c) {
		return strs[lnk[c]];
	}

	@Override
	public int getRowCount() {
		if (mm == null)
			return 0;
		return mm.n;
	}

	@Override
	public int[][] getSelected() {
		int[] rows = getSelectedRows();
		int[][] ps = new int[rows.length][];
		for (int i = 0; i < rows.length; i++) {
			ps[i] = mm.parts[rows[i]].clone();
			for (int j = 0; j < i; j++)
				if (ps[i][0] == rows[j])
					ps[i][0] = -j - 10;
		}
		return ps;
	}

	@Override
	public Object getValueAt(int r, int c) {
		if (mm == null || r < 0 || c < 0 || r >= mm.n || c >= strs.length)
			return null;
		if (lnk[c] == 0)
			return r;
		if (lnk[c] == 1)
			return mm.parts[r][0];
		if (lnk[c] == 13)
			return mm.strs0[r];
		return mm.parts[r][lnk[c]];
	}

	@Override
	public boolean insert(int dst, int[][] data, int[] rows) {
		String[] names = new String[data.length];

		if(rows == null) {
			Arrays.fill(names, "copied");
		} else {
			for(int i = 0; i < names.length; i++) {
				if(i >= rows.length || rows[i] >= mm.strs0.length)
					names[i] = "copied";
				else {
					if(mm.strs0[rows[i]].endsWith("_copied")) {
						names[i] = mm.strs0[rows[i]]+"0";
					} else if(mm.strs0[rows[i]].matches("(.+)?_copied\\d?$")) {
						String[] split = mm.strs0[rows[i]].split("_copied");

						int value = CommonStatic.safeParseInt(split[1]) + 1;

						names[i] = mm.strs0[rows[i]].replaceAll("_copied\\d?$", "_copied"+value);
					} else {
						names[i] = mm.strs0[rows[i]]+"_copied";
					}
				}
			}
		}

		int[] inds = new int[mm.n];
		int[] move = new int[mm.n + data.length];
		int ind = 0;
		for (int i = 0; i < mm.n; i++) {
			if (i == dst)
				for (int j = 0; j < data.length; j++) {
					move[ind] = mm.n + j;
					ind++;
				}
			inds[i] = ind;
			move[ind] = i;
			ind++;
		}
		if (mm.n == dst)
			for (int j = 0; j < data.length; j++) {
				move[ind] = mm.n + j;
				ind++;
			}
		anim.reorderModel(inds);
		mm.parts = Arrays.copyOf(mm.parts, mm.n + data.length);
		mm.strs0 = Arrays.copyOf(mm.strs0, mm.n + data.length);
		for (int i = 0; i < data.length; i++) {
			mm.parts[mm.n + i] = data[i];
			int par = data[i][0];
			if (par <= -10)
				data[i][0] = dst - par - 10;
			mm.strs0[mm.n + i] = names[i];
		}
		mm.n = mm.n + data.length;
		mm.reorder(move);
		mm.check(anim);
		anim.unSave("mamodel paste");
		page.callBack(new int[] { dst, dst + data.length - 1 });
		return true;
	}

	@Override
	public boolean isCellEditable(int r, int c) {
		return lnk[c] != 0;
	}

	@Override
	public boolean reorder(int dst, int[] ori) {
		int[] inds = new int[mm.n];
		int[] move = new int[mm.n];
		int[] orid = new int[mm.n];
		for (int val : ori)
			orid[val] = -1;
		int ind = 0, fin = 0;
		for (int i = 0; i <= mm.n; i++) {
			if (i == dst) {
				fin = ind;
				for (int k : ori) {
					move[ind] = k;
					inds[k] = ind;
					ind++;
				}
			}
			if (i != mm.n && orid[i] != -1) {
				move[ind] = i;
				inds[i] = ind;
				ind++;
			}
		}

		anim.reorderModel(inds);
		mm.reorder(move);
		anim.unSave("mamodel reorder");
		page.callBack(new int[] { fin, fin + ori.length - 1 });
		return true;
	}

	@Override
	public synchronized void setValueAt(Object val, int r, int c) {
		if (mm == null)
			return;
		c = lnk[c];
		if (c == 13)
			mm.strs0[r] = ((String) val).trim();
		else {
			int v = (int) val;
			if (c == 1 && v < -1)
				v = -1;
			if (c == 2)
				if (v < 0)
					v = 0;
				else if (v >= anim.imgcut.n)
					v = anim.imgcut.n - 1;
			if (c == 1)
				c--;
			if (r >= mm.n)
				return;
			mm.parts[r][c] = v;
			mm.parts[0][0] = -1;
		}
		if (c == 0)
			mm.check(anim);
		anim.unSave("mamodel edit");
		page.callBack(null);
	}

	protected void setMaModel(AnimCE au) {
		if (cellEditor != null)
			cellEditor.stopCellEditing();
		anim = au;
		mm = au == null ? null : au.mamodel;
	}

}
