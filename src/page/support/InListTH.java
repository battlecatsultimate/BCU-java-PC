package page.support;

import common.system.Copable;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragSource;
import java.io.IOException;

public class InListTH<T> extends TransferHandler implements Transferable {

    private static final long serialVersionUID = 1L;

    private final DataFlavor adf;
    private final ReorderList<T> list;

    private int obj;
    private int editing = -1;

    public InListTH(ReorderList<T> jlist) {
        list = jlist;
        adf = new DataFlavor(Integer.class, "");
        list.setDragEnabled(true);
        list.setDropMode(DropMode.INSERT);
    }

    public InListTH(ReorderList<T> jlist, Class<?> cls, String fla) {
        list = jlist;
        adf = new DataFlavor(cls, fla);
        list.setDragEnabled(true);
        list.setDropMode(DropMode.INSERT);
    }

    @Override
    public boolean canImport(TransferSupport info) {
        boolean b = info.getComponent() == list;
        b &= info.isDrop();
        b &= info.getDataFlavors()[0] == adf;
        list.setCursor(b ? DragSource.DefaultMoveDrop : DragSource.DefaultMoveNoDrop);
        return b;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY_OR_MOVE;
    }

    @Override
    public T getTransferData(DataFlavor arg0) throws UnsupportedFlavorException, IOException {
        return list.copymap.get(obj);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{adf};
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean importData(TransferSupport info) {
        ReorderList<T> target = (ReorderList<T>) info.getComponent();
        assert target == list;
        if (info.isDrop()) {
            JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
            int fin = dl.getIndex();
            int max = list.getModel().getSize();
            if (fin < 0 || fin > max)
                fin = max;
            target.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            try {
                int ori = editing;
                if (ori != -1 && ori != fin) {
                    list.reorder(ori, fin);
                    if (fin > ori)
                        fin--;
                    target.setSelectedIndex(fin);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (!list.copable)
                return false;
            Copable<T> t = (Copable<T>) list.copymap.get(obj);
            if (t == null)
                return false;
            return list.add(t.copy());
        }
        return false;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor arg0) {
        return arg0.getHumanPresentableName().equals(adf.getHumanPresentableName());
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        assert (c == list);
        T t = list.getSelectedValue();
        if (list.copable)
            list.copymap.put(obj = t.hashCode(), t);
        editing = list.getSelectedIndex();
        return this;
    }

    @Override
    protected void exportDone(JComponent c, Transferable t, int act) {
        if (act == TransferHandler.MOVE || act == TransferHandler.NONE)
            list.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

}