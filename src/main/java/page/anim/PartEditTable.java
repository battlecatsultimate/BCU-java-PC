package page.anim;

import common.util.anim.AnimCE;
import common.util.anim.MaAnim;
import common.util.anim.Part;
import page.Page;
import page.support.AnimTable;
import page.support.AnimTableTH;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

class PartEditTable extends AnimTable<int[]> {

	private static final long serialVersionUID = 1L;

	private static final String[] strs = new String[] { "frame", "value", "easing", "parameter" };

	protected AnimCE anim;
	protected MaAnim ma;
	protected Part part;
	private final Page page;

	protected PartEditTable(Page p) {
		super(strs);

		page = p;
		selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		setTransferHandler(new AnimTableTH<>(this, 3));
	}

	@Override
	public boolean editCellAt(int row, int column, EventObject e) {
		boolean result = super.editCellAt(row, column, e);
		final Component editor = getEditorComponent();
		if (!(editor instanceof JTextComponent))
			return result;
		if (e instanceof KeyEvent)
			((JTextComponent) editor).selectAll();
		return result;
	}

	@Override
	public Class<?> getColumnClass(int c) {
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
		if (part == null)
			return 0;
		return part.n;
	}

	@Override
	public int[][] getSelected() {
		int[] rows = getSelectedRows();
		int[][] ps = new int[rows.length][];
		for (int i = 0; i < rows.length; i++)
			ps[i] = part.moves[rows[i]].clone();
		return ps;
	}

	@Override
	public Object getValueAt(int r, int c) {
		if (part == null || r < 0 || c < 0 || r >= part.n || c >= strs.length)
			return null;
		if (lnk[c] == 0)
			return part.moves[r][0] - part.off;
		return part.moves[r][lnk[c]];
	}

	@Override
	public boolean insert(int dst, int[][] data, int[] rows) {
		List<int[]> l = new ArrayList<>();
		for (int[] p : part.moves)
			if (p != null)
				l.add(p);
		for (int i = 0; i < data.length; i++)
			l.add(i + dst, data[i]);
		part.moves = l.toArray(new int[0][]);
		part.n = part.moves.length;
		part.validate();
		part.check(anim);
		anim.unSave("maanim paste line");
		page.callBack(new int[] { 1, dst, dst + data.length - 1 });
		return true;
	}

	@Override
	public boolean isCellEditable(int r, int c) {
		return true;
	}

	@Override
	public boolean reorder(int dst, int[] ori) {
		List<int[]> l = new ArrayList<>();
		List<int[]> ab = new ArrayList<>();
		for (int row : ori) {
			ab.add(part.moves[row]);
			part.moves[row] = null;
		}
		for (int i = 0; i < dst; i++)
			if (part.moves[i] != null)
				l.add(part.moves[i]);
		int ind = l.size();
		l.addAll(ab);
		for (int i = dst; i < part.n; i++)
			if (part.moves[i] != null)
				l.add(part.moves[i]);
		part.moves = l.toArray(new int[0][]);
		part.validate();
		part.check(anim);
		anim.unSave("maanim reorder line");
		page.callBack(new int[] { 1, ind, ind + ori.length - 1 });
		return true;
	}

	@Override
	public synchronized void setValueAt(Object val, int r, int c) {
		if (part == null || r >= part.n)
			return;
		c = lnk[c];
		int v = (int) val;
		int m = part.ints[1];
		if (c == 1) {
			if ((m < 4 || m > 11) && v < 0)
				v = 0;
			if (m == 0) {
				if (v >= anim.mamodel.n)
					v = anim.mamodel.n - 1;
				if (v == part.ints[0])
					v = -1;
			}
			if (m == 2 && v >= anim.imgcut.n)
				v = anim.imgcut.n - 1;
			if (m == 12 && v > anim.mamodel.ints[2])
				v = anim.mamodel.ints[2];
		}
		if (c == 0)
			v += part.off;
		part.moves[r][c] = v;
		part.validate();
		ma.validate();
		anim.unSave("maanim edit line");
		page.callBack(null);
	}

	protected void setAnim(AnimCE au, MaAnim maa, Part p) {
		if (cellEditor != null)
			cellEditor.stopCellEditing();
		anim = au;
		ma = maa;
		part = p;
	}

}
