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
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class Importer(str: String?) : JFileChooser() {
    var file: File? = null
    fun getImg(): BufferedImage? {
        if (file == null) return null
        var bimg: BufferedImage? = null
        bimg = try {
            ImageIO.read(file)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return bimg
    }

    companion object {
        private const val serialVersionUID = 1L
        const val IMP_DEF = 0
        const val IMP_IMG = 1
        val curs = arrayOfNulls<File>(2)
    }

    init {
        val t = IMP_IMG
        setDialogTitle(str)
        val filter = FileNameExtensionFilter("PNG Images", "png")
        setCurrentDirectory(curs[t])
        setFileFilter(filter)
        setDragEnabled(true)
        val returnVal: Int = showOpenDialog(null)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = getSelectedFile()
            curs[t] = getCurrentDirectory()
        }
    }
}
