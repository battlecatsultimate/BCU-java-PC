package page.support

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
