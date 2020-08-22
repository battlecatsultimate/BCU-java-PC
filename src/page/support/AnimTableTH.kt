package page.support

import page.support.AnimTransfer
import java.awt.Cursor
import java.awt.datatransfer.Transferable
import java.awt.dnd.DragSource
import javax.swing.DropMode
import javax.swing.JComponent
import javax.swing.JTable
import javax.swing.TransferHandler

class AnimTableTH<T>(jtable: AnimTable<T>, t: Int) : TransferHandler() {
    private val type: Int
    private val table: AnimTable<T>
    private var rows: IntArray
    override fun canImport(info: TransferSupport): Boolean {
        var b = info.component === table
        b = b and info.isDataFlavorSupported(AnimTransfer.Companion.DFS.get(type))
        table.cursor = if (b) DragSource.DefaultMoveDrop else DragSource.DefaultMoveNoDrop
        return b
    }

    override fun getSourceActions(c: JComponent): Int {
        return TransferHandler.COPY_OR_MOVE
    }

    override fun importData(info: TransferSupport): Boolean {
        if (!canImport(info)) return false
        try {
            return if (info.isDrop) {
                val row: Int = (info.dropLocation as JTable.DropLocation).row
                table.reorder(row, rows)
            } else {
                val data = info.transferable.getTransferData(AnimTransfer.Companion.DFS.get(type)) as Array<T>
                val row: Int = table.selectedRow + 1
                table.insert(row, data)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun createTransferable(c: JComponent): Transferable {
        assert(c === table)
        rows = table.selectedRows
        return AnimTransfer<T>(type, table.getSelected())
    }

    override fun exportDone(c: JComponent, t: Transferable, act: Int) {
        table.cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        table = jtable
        table.dragEnabled = true
        table.dropMode = DropMode.INSERT_ROWS
        type = t
    }
}
