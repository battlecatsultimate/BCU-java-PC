package page.support

import common.system.Copable
import java.awt.Cursor
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.awt.datatransfer.UnsupportedFlavorException
import java.awt.dnd.DragSource
import java.io.IOException
import javax.swing.DropMode
import javax.swing.JComponent
import javax.swing.JList
import javax.swing.TransferHandler

class InListTH<T> : TransferHandler, Transferable {
    private val adf: DataFlavor
    private val list: ReorderList<T>
    private var obj = 0
    private var editing = -1

    constructor(jlist: ReorderList<T>) {
        list = jlist
        adf = DataFlavor(Int::class.java, "")
        list.dragEnabled = true
        list.dropMode = DropMode.INSERT
    }

    constructor(jlist: ReorderList<T>, cls: Class<*>?, fla: String?) {
        list = jlist
        adf = DataFlavor(cls, fla)
        list.dragEnabled = true
        list.dropMode = DropMode.INSERT
    }

    override fun canImport(info: TransferSupport): Boolean {
        var b = info.component === list
        b = b and info.isDrop
        b = b and (info.dataFlavors.get(0) === adf)
        list.cursor = if (b) DragSource.DefaultMoveDrop else DragSource.DefaultMoveNoDrop
        return b
    }

    override fun getSourceActions(c: JComponent): Int {
        return TransferHandler.COPY_OR_MOVE
    }

    @Throws(UnsupportedFlavorException::class, IOException::class)
    override fun getTransferData(arg0: DataFlavor): T {
        return list.copymap.get(obj)
    }

    override fun getTransferDataFlavors(): Array<DataFlavor> {
        return arrayOf<DataFlavor>(adf)
    }

    override fun importData(info: TransferSupport): Boolean {
        val target: ReorderList<T> = info.component
        assert(target === list)
        if (info.isDrop) {
            val dl: JList.DropLocation = info.dropLocation as JList.DropLocation
            var fin: Int = dl.index
            val max: Int = list.model.size
            if (fin < 0 || fin > max) fin = max
            target.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))
            try {
                val ori = editing
                if (ori != -1 && ori != fin) {
                    list.reorder(ori, fin)
                    if (fin > ori) fin--
                    target.selectedIndex = fin
                    return true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            if (!list.copable) return false
            val t: Copable<T> = list.copymap.get(obj) as Copable<T>
            return list.add(t.copy())
        }
        return false
    }

    override fun isDataFlavorSupported(arg0: DataFlavor): Boolean {
        return arg0.humanPresentableName == adf.humanPresentableName
    }

    override fun createTransferable(c: JComponent): Transferable {
        assert(c === list)
        val t: T = list.selectedValue
        if (list.copable) list.copymap.put(t.hashCode().also { obj = it }, t)
        editing = list.selectedIndex
        return this
    }

    override fun exportDone(c: JComponent, t: Transferable, act: Int) {
        if (act == TransferHandler.MOVE || act == TransferHandler.NONE) list.cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
