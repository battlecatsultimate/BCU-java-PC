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
        var b = info.getComponent() === table
        b = b and info.isDataFlavorSupported(AnimTransfer.Companion.DFS.get(type))
        table.setCursor(if (b) DragSource.DefaultMoveDrop else DragSource.DefaultMoveNoDrop)
        return b
    }

    override fun getSourceActions(c: JComponent): Int {
        return TransferHandler.COPY_OR_MOVE
    }

    override fun importData(info: TransferSupport): Boolean {
        if (!canImport(info)) return false
        try {
            return if (info.isDrop()) {
                val row: Int = (info.getDropLocation() as JTable.DropLocation).getRow()
                table.reorder(row, rows)
            } else {
                val data = info.getTransferable().getTransferData(AnimTransfer.Companion.DFS.get(type)) as Array<T>
                val row: Int = table.getSelectedRow() + 1
                table.insert(row, data)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    protected override fun createTransferable(c: JComponent): Transferable {
        assert(c === table)
        rows = table.getSelectedRows()
        return AnimTransfer<T>(type, table.getSelected())
    }

    protected override fun exportDone(c: JComponent, t: Transferable, act: Int) {
        table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        table = jtable
        table.setDragEnabled(true)
        table.setDropMode(DropMode.INSERT_ROWS)
        type = t
    }
}
