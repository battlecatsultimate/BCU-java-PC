package page.support

import java.awt.Cursor
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.awt.datatransfer.UnsupportedFlavorException
import java.awt.dnd.DragSource
import java.io.IOException
import javax.swing.DropMode
import javax.swing.JComponent
import javax.swing.JTable
import javax.swing.TransferHandler

class InTableTH(jtable: JTable) : TransferHandler(), Transferable {
    private val adf: DataFlavor
    private val table: JTable
    private var editing = 0
    override fun canImport(info: TransferSupport): Boolean {
        var b = info.getComponent() === table
        b = b and info.isDrop()
        b = b and (info.getDataFlavors().get(0) === adf)
        table.setCursor(if (b) DragSource.DefaultMoveDrop else DragSource.DefaultMoveNoDrop)
        return b
    }

    override fun getSourceActions(c: JComponent): Int {
        return TransferHandler.COPY_OR_MOVE
    }

    @Throws(UnsupportedFlavorException::class, IOException::class)
    override fun getTransferData(arg0: DataFlavor): Any {
        return null
    }

    override fun getTransferDataFlavors(): Array<DataFlavor> {
        return arrayOf<DataFlavor>(adf)
    }

    override fun importData(info: TransferSupport): Boolean {
        if (!info.isDrop()) return false
        val target: JTable = info.getComponent() as JTable
        val dl: JTable.DropLocation = info.getDropLocation() as JTable.DropLocation
        var fin: Int = dl.getRow()
        val max: Int = table.getModel().getRowCount()
        if (fin < 0 || fin > max) fin = max
        target.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))
        try {
            val ori = editing
            if (ori != -1 && ori != fin) {
                (table.getModel() as Reorderable).reorder(ori, fin)
                if (fin > ori) fin--
                target.getSelectionModel().addSelectionInterval(fin, fin)
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun isDataFlavorSupported(arg0: DataFlavor): Boolean {
        return arg0.getHumanPresentableName() == adf.getHumanPresentableName()
    }

    protected override fun createTransferable(c: JComponent): Transferable {
        assert(c === table)
        editing = table.getSelectedRow()
        return this
    }

    protected override fun exportDone(c: JComponent, t: Transferable, act: Int) {
        if (act == TransferHandler.MOVE || act == TransferHandler.NONE) table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        table = jtable
        adf = DataFlavor(Int::class.java, "")
        table.setDragEnabled(true)
        table.setDropMode(DropMode.INSERT_ROWS)
    }
}
