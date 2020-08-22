package page.support

import common.io.OutStream
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
import io.BCUWriter
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
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class Exporter : JFileChooser {
    var file: File? = null

    constructor(bimg: BufferedImage?, t: Int) {
        val filter = FileNameExtensionFilter("PNG Images", "png")
        setCurrentDirectory(curs[t])
        setFileFilter(filter)
        setDragEnabled(true)
        val returnVal: Int = showSaveDialog(null)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = getSelectedFile()
            curs[t] = getCurrentDirectory()
            BCUWriter.writeImage(bimg, file)
        }
    }

    constructor(t: Int) {
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
        setCurrentDirectory(curs[t])
        setDragEnabled(true)
        val returnVal: Int = showSaveDialog(null)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = getSelectedFile()
            curs[t] = getCurrentDirectory()
            if (!file!!.isDirectory) file = file!!.parentFile
        }
    }

    constructor(os: OutStream?, t: Int) {
        setDragEnabled(true)
        setCurrentDirectory(curs[t])
        val returnVal: Int = showSaveDialog(null)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = getSelectedFile()
            curs[t] = getCurrentDirectory()
            BCUWriter.writeBytes(os, file!!.path)
        }
    }

    companion object {
        private const val serialVersionUID = 1L
        const val EXP_DEF = 0
        const val EXP_IMG = 1
        const val EXP_BAC = 2
        const val EXP_ERR = 3
        const val EXP_RES = 4
        val curs = arrayOfNulls<File>(5)
    }
}
