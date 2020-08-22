package page.support

import common.io.assets.Admin
import common.io.assets.Admin.StaticPermitted
import common.io.assets.AssetLoader
import common.io.assets.AssetLoader.AssetHeader
import common.io.assets.AssetLoader.AssetHeader.AssetEntry
import common.io.json.JsonEncoder
import common.io.json.Test
import common.io.json.Test.JsonTest_0.JsonD
import common.io.json.Test.JsonTest_2
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.pack.Source.SourceAnimLoader
import common.pack.Source.SourceAnimSaver
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.anim.AnimBox
import page.support.AnimTransfer
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
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
