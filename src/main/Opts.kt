package main

import page.Page
import javax.swing.JOptionPane

object Opts {
    const val MEMORY = 1001
    const val SECTY = 1002
    const val REQITN = 1003
    const val INSTALL = 1004
    private var nshowi = false
    private var nshowu = false
    fun animErr(f: String) {
        if (nshowi) return
        nshowi = !warning("error in reading file $f, Click Cancel to supress this popup?", "IO error")
    }

    fun backupErr(t: String) {
        pop("failed to $t backup", "backup access error")
    }

    fun conf(): Boolean {
        return warning(Page.Companion.get(0, "w0"), "confirmation")
    }

    fun conf(text: String): Boolean {
        return warning(text, "confirmation")
    }

    fun dloadErr(text: String) {
        pop("failed to download $text", "download error")
    }

    fun ioErr(text: String?) {
        pop(text, "IO error")
    }

    fun loadErr(text: String?) {
        pop(text, "loading error")
    }

    fun packConf(text: String): Boolean {
        return warning(text, "pack conflict")
    }

    fun pop(id: Int, vararg `is`: String) {
        if (id == MEMORY) pop("not enough memory. Current memory: " + `is`[0] + "MB.", "not enough memory")
        if (id == SECTY) pop("Failed to access files. Please move BCU folder to another place", "file permission error")
        if (id == REQITN) pop("failed to connect to internet while download is necessary", "download error")
        if (id == INSTALL) pop("<html>BCU library is not properly installed.<br>Download and run BCU-Installer to install BCU library</html>",
                "library not installed")
    }

    fun pop(text: String?, title: String?) {
        val opt: Int = JOptionPane.PLAIN_MESSAGE
        JOptionPane.showMessageDialog(null, text, title, opt)
    }

    fun read(string: String?): String {
        return JOptionPane.showInputDialog(null, string, "")
    }

    fun recdErr(name: String, suf: String) {
        pop("replay $name uses unavailable $suf", "replay read error")
    }

    fun servErr(text: String?) {
        pop(text, "server error")
    }

    fun success(text: String?) {
        pop(text, "success")
    }

    fun unitErr(f: String) {
        if (nshowu) return
        nshowu = !warning("$f, Click Cancel to supress this popup?", "can't find unit")
    }

    fun updateCheck(s: String, p: String): Boolean {
        return warning("$s update available. do you want to update? $p", "update check")
    }

    fun verErr(o: String, v: String) {
        pop(o + " version is too old, use BCU " + v + " or " + (if (o == "BCU") "newer" else "older")
                + " version to open it", "version error")
    }

    fun writeErr0(f: String): Boolean {
        return warning("failed to write file: $f do you want to retry?", "IO error")
    }

    fun writeErr1(f: String): Boolean {
        return warning("failed to write file: $f do you want to save it in another place?", "IO error")
    }

    private fun warning(text: String, title: String): Boolean {
        val opt: Int = JOptionPane.OK_CANCEL_OPTION
        val `val`: Int = JOptionPane.showConfirmDialog(null, text, title, opt)
        return `val` == JOptionPane.OK_OPTION
    }
}
