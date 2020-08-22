package page.support

import common.util.anim.Part
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.awt.datatransfer.UnsupportedFlavorException
import java.io.IOException

class AnimTransfer<T>(private val type: Int, private val data: Array<T>) : Transferable {
    @Throws(UnsupportedFlavorException::class, IOException::class)
    override fun getTransferData(arg0: DataFlavor): Any {
        return data
    }

    override fun getTransferDataFlavors(): Array<DataFlavor> {
        return arrayOf<DataFlavor>(DFS[type])
    }

    override fun isDataFlavorSupported(arg0: DataFlavor): Boolean {
        return arg0 === DFS[type]
    }

    companion object {
        val ADF: DataFlavor = DataFlavor(Part::class.java, "MaAnim")
        val PDF: DataFlavor = DataFlavor(IntArray::class.java, "Part")
        val MDF: DataFlavor = DataFlavor(IntArray::class.java, "MaModel")
        val DFS: Array<DataFlavor> = arrayOf(null, MDF, ADF, PDF)
    }
}
