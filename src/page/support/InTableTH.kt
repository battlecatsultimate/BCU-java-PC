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
        var b = info.component === table
        b = b and info.isDrop
        b = b and (info.dataFlavors.get(0) === adf)
        table.cursor = if (b) DragSource.DefaultMoveDrop else DragSource.DefaultMoveNoDrop
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
        if (!info.isDrop) return false
        val target: JTable = info.component as JTable
        val dl: JTable.DropLocation = info.dropLocation as JTable.DropLocation
        var fin: Int = dl.row
        val max: Int = table.model.rowCount
        if (fin < 0 || fin > max) fin = max
        target.cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)
        try {
            val ori = editing
            if (ori != -1 && ori != fin) {
                (table.model as Reorderable).reorder(ori, fin)
                if (fin > ori) fin--
                target.selectionModel.addSelectionInterval(fin, fin)
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun isDataFlavorSupported(arg0: DataFlavor): Boolean {
        return arg0.humanPresentableName == adf.humanPresentableName
    }

    override fun createTransferable(c: JComponent): Transferable {
        assert(c === table)
        editing = table.selectedRow
        return this
    }

    override fun exportDone(c: JComponent, t: Transferable, act: Int) {
        if (act == TransferHandler.MOVE || act == TransferHandler.NONE) table.cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        table = jtable
        adf = DataFlavor(Int::class.java, "")
        table.dragEnabled = true
        table.dropMode = DropMode.INSERT_ROWS
    }
}
