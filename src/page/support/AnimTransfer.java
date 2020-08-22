package page.support;

import common.util.anim.Part;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class AnimTransfer<T> implements Transferable {

    public static final DataFlavor ADF = new DataFlavor(Part.class, "MaAnim");
    public static final DataFlavor PDF = new DataFlavor(int[].class, "Part");
    public static final DataFlavor MDF = new DataFlavor(int[].class, "MaModel");

    public static final DataFlavor[] DFS = new DataFlavor[]{null, MDF, ADF, PDF};

    private final int type;
    private final T[] data;

    protected AnimTransfer(int t, T[] p) {
        type = t;
        data = p;
    }

    @Override
    public Object getTransferData(DataFlavor arg0) throws UnsupportedFlavorException, IOException {
        return data;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DFS[type]};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor arg0) {
        return arg0 == DFS[type];
    }

}
