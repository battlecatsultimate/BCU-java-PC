package page.support;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragSource;
import java.io.IOException;

import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

public class InTableTH extends TransferHandler implements Transferable {

	private static final long serialVersionUID = 1L;

	private final DataFlavor adf;
	private final JTable table;

	private int editing;

	public InTableTH(JTable jtable) {
		table = jtable;
		adf = new DataFlavor(Integer.class, "");
		table.setDragEnabled(true);
		table.setDropMode(DropMode.INSERT_ROWS);
	}

	@Override
	public boolean canImport(TransferSupport info) {
		boolean b = info.getComponent() == table;
		b &= info.isDrop();
		b &= info.getDataFlavors()[0] == adf;
		table.setCursor(b ? DragSource.DefaultMoveDrop : DragSource.DefaultMoveNoDrop);
		return b;
	}

	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.COPY_OR_MOVE;
	}

	@Override
	public Object getTransferData(DataFlavor arg0) throws UnsupportedFlavorException, IOException {
		return null;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { adf };
	}

	@Override
	public boolean importData(TransferSupport info) {
		if (!info.isDrop())
			return false;
		JTable target = (JTable) info.getComponent();
		JTable.DropLocation dl = (JTable.DropLocation) info.getDropLocation();
		int fin = dl.getRow();
		int max = table.getModel().getRowCount();
		if (fin < 0 || fin > max)
			fin = max;
		target.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		try {
			int ori = editing;
			if (ori != -1 && ori != fin) {
				((Reorderable) table.getModel()).reorder(ori, fin);
				if (fin > ori)
					fin--;
				target.getSelectionModel().addSelectionInterval(fin, fin);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor arg0) {
		return arg0.getHumanPresentableName().equals(adf.getHumanPresentableName());
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		assert (c == table);
		editing = table.getSelectedRow();
		return this;
	}

	@Override
	protected void exportDone(JComponent c, Transferable t, int act) {
		if (act == TransferHandler.MOVE || act == TransferHandler.NONE)
			table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

}