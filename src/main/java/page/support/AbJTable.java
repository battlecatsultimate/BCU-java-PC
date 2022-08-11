package page.support;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public abstract class AbJTable extends JTable implements TableModel {

	private static final long serialVersionUID = 1L;

	protected final int[] lnk;
	protected final String[] title;

	private final TModel tm = new TModel(this);

	protected AbJTable(@Nonnull String[] title) {
		this.title = title;

		lnk = new int[getColumnCount()];

		for (int i = 0; i < lnk.length; i++)
			lnk[i] = i;

		setColumnModel(tm);
		setModel(this);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	@Override
	public void addTableModelListener(TableModelListener arg0) {
	}

	@Override
	public abstract Class<?> getColumnClass(int c);

	@Override
	public int getColumnCount() {
		return title.length;
	}

	@Override
	public abstract String getColumnName(int c);

	@Override
	public abstract int getRowCount();

	@Override
	public abstract Object getValueAt(int r, int c);

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
	}

	protected void swap(int c, int nc) {
		int t = lnk[nc];
		lnk[nc] = lnk[c];
		lnk[c] = t;
	}

}

class TModel extends DefaultTableColumnModel {

	private static final long serialVersionUID = 1L;

	private final AbJTable t;

	protected TModel(AbJTable table) {
		t = table;
	}

	@Override
	public void moveColumn(int c, int nc) {
		c = Math.max(c, 0);
		c = Math.min(c, getColumnCount() - 1);
		nc = Math.max(nc, 0);
		nc = Math.min(nc, getColumnCount() - 1);

		if (c == nc)
			return;
		t.swap(c, nc);
		super.moveColumn(c, nc);

	}

	@Override
	public void removeColumn(TableColumn tc) {
	}

}
