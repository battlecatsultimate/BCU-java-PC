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
import common.util.anim.Part
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
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
