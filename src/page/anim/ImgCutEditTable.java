package page.anim;

import common.util.anim.AnimCE;
import common.util.anim.ImgCut;
import page.support.AbJTable;

import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.EventObject;

class ImgCutEditTable extends AbJTable {

	private static final long serialVersionUID = 1L;

	private static final String[] strs = new String[] { "id", "x", "y", "w", "h", "name" };

	protected AnimCE anim;

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
		if (lnk[c] == 5)
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
		if (anim == null || anim.imgcut == null)
			return 0;
		return anim.imgcut.n;
	}

	@Override
	public Object getValueAt(int r, int c) {
		if (anim == null || anim.imgcut == null || r < 0 || c < 0 || r >= anim.imgcut.n || c >= strs.length)
			return null;
		if (lnk[c] == 0)
			return r;
		if (lnk[c] == 5)
			return anim.imgcut.strs[r];
		return anim.imgcut.cuts[r][lnk[c] - 1];
	}

	@Override
	public boolean isCellEditable(int r, int c) {
		return lnk[c] != 0;
	}

	@Override
	public synchronized void setValueAt(Object val, int r, int c) {
		if (anim == null || anim.imgcut == null)
			return;
		c = lnk[c];
		if (c == 5) {
			anim.imgcut.strs[r] = ((String) val).trim();
			anim.unSave("imgcut edit name");
			return;
		}
		int v = (int) val;
		if (v < 0)
			v = 0;
		if (c > 2 && v == 0)
			v = 1;
		anim.imgcut.cuts[r][c - 1] = v;
		anim.unSave("imgcut edit data");
		anim.ICedited();
	}

	protected void setCut(AnimCE au) {
		if (cellEditor != null)
			cellEditor.stopCellEditing();
		anim = au;
	}

}
