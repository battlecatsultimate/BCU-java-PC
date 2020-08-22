package page.support;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSource;

import static page.support.AnimTransfer.DFS;

public class AnimTableTH<T> extends TransferHandler {

    private static final long serialVersionUID = 1L;

    private final int type;

    private final AnimTable<T> table;

    private int[] rows;

    public AnimTableTH(AnimTable<T> jtable, int t) {
        table = jtable;
        table.setDragEnabled(true);
        table.setDropMode(DropMode.INSERT_ROWS);
        type = t;
    }

    @Override
    public boolean canImport(TransferSupport info) {
        boolean b = info.getComponent() == table;
        b &= info.isDataFlavorSupported(DFS[type]);
        table.setCursor(b ? DragSource.DefaultMoveDrop : DragSource.DefaultMoveNoDrop);
        return b;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY_OR_MOVE;
    }

    @Override
    public boolean importData(TransferSupport info) {
        if (!canImport(info))
            return false;
        try {
            if (info.isDrop()) {
                int row = ((JTable.DropLocation) info.getDropLocation()).getRow();
                return table.reorder(row, rows);
            } else {
                @SuppressWarnings("unchecked")
                T[] data = (T[]) info.getTransferable().getTransferData(DFS[type]);
                int row = table.getSelectedRow() + 1;
                return table.insert(row, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        assert (c == table);
        rows = table.getSelectedRows();
        return new AnimTransfer<>(type, table.getSelected());
    }

    @Override
    protected void exportDone(JComponent c, Transferable t, int act) {
        table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

    }

}