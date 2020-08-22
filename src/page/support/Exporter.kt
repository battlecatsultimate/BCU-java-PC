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
