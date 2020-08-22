package page.support

import common.io.OutStream
import io.BCUWriter
import java.awt.image.BufferedImage
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class Exporter : JFileChooser {
    var file: File? = null

    constructor(bimg: BufferedImage?, t: Int) {
        val filter = FileNameExtensionFilter("PNG Images", "png")
        currentDirectory = curs[t]
        fileFilter = filter
        dragEnabled = true
        val returnVal: Int = showSaveDialog(null)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = selectedFile
            curs[t] = currentDirectory
            BCUWriter.writeImage(bimg, file)
        }
    }

    constructor(t: Int) {
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        currentDirectory = curs[t]
        dragEnabled = true
        val returnVal: Int = showSaveDialog(null)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = selectedFile
            curs[t] = currentDirectory
            if (!file!!.isDirectory) file = file!!.parentFile
        }
    }

    constructor(os: OutStream?, t: Int) {
        dragEnabled = true
        currentDirectory = curs[t]
        val returnVal: Int = showSaveDialog(null)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = selectedFile
            curs[t] = currentDirectory
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
