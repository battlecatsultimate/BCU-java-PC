package io

import com.google.gson.JsonObject
import common.CommonStatic
import common.battle.BasisSet
import common.io.DataIO
import common.io.OutStream
import common.io.json.JsonEncoder
import common.pack.Context
import common.pack.Context.ErrType
import common.util.Data
import main.MainBCU
import main.Opts
import main.Printer
import page.MainFrame
import page.MainLocale
import page.battle.BattleInfoPage
import page.support.Exporter
import page.support.Importer
import page.view.ViewBox.Conf
import res.AnimatedGifEncoder
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.*
import java.nio.file.Files
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO

object BCUWriter : DataIO {
    private var log: File? = null
    private var ph: File? = null
    private var ps: WriteStream? = null
    fun logClose(save: Boolean) {
        writeOptions()
        if (save && MainBCU.loaded && MainBCU.trueRun) {
            writeData()
        }
        if (ps!!.writed) {
            ps!!.println("version: " + Data.Companion.revVer(MainBCU.ver))
        }
        ps!!.close()
        ph!!.deleteOnExit()
        if (log!!.length() == 0L) log!!.deleteOnExit()
    }

    fun logPrepare() {
        val str: String = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        log = File("./logs/$str.log")
        ph = File("./logs/placeholder")
        if (!log!!.parentFile.exists()) log!!.parentFile.mkdirs()
        try {
            if (!log!!.exists()) log!!.createNewFile()
            ps = io.WriteStream(log)
        } catch (e: IOException) {
            e.printStackTrace()
            Opts.pop(Opts.SECTY)
            System.exit(0)
        }
        try {
            if (ph!!.exists()) {
                if (!Opts.conf("<html>" + "Another BCU is running in this folder or last BCU doesn't close properly. "
                                + "<br> Are you sure to run? It might damage your save.</html>")) {
                    System.exit(0)
                }
            }
            ph!!.createNewFile()
        } catch (e: IOException) {
        }
    }

    fun logSetup() {
        if (MainBCU.WRITE) {
            System.setErr(ps)
            System.setOut(ps)
        }
    }

    fun newFile(str: String?): PrintStream? {
        val f = File(str)
        var out: PrintStream? = null
        try {
            Context.Companion.check(f)
            out = PrintStream(f, "UTF-8")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return out
    }

    fun writeBytes(bs: ByteArray?, path: String?): Boolean {
        val f = File(path)
        var fos: FileOutputStream? = null
        try {
            Context.Companion.check(f)
            fos = FileOutputStream(f)
            fos.write(bs)
            fos.close()
            return true
        } catch (e: IOException) {
            Printer.w(130, "IOE!!!")
            if (fos != null) try {
                fos.close()
            } catch (e1: IOException) {
                Printer.w(131, "cannot close fos")
                e1.printStackTrace()
            }
            e.printStackTrace()
        } finally {
            if (fos != null) try {
                fos.close()
            } catch (e1: IOException) {
                Printer.w(139, "finally cannot close fos neither!")
                e1.printStackTrace()
            }
        }
        return false
    }

    fun writeBytes(os: OutStream, path: String?): Boolean {
        os.terminate()
        val md5: ByteArray = os.MD5()
        val f = File(path)
        var suc: Boolean
        if (!writeFile(os, f, md5).also { suc = it }) {
            ps!!.println("failed to write file: " + f.path)
            if (Opts.writeErr0(f.path)) if (!writeFile(os, f, md5).also { suc = it }) if (Opts.writeErr1(f.path)) Exporter(os, Exporter.Companion.EXP_ERR)
        }
        return suc
    }

    fun writeData() {
        // FIXME write data
        BasisSet.Companion.write()
        writeOptions()
    }

    fun writeGIF(age: AnimatedGifEncoder, path: String?) {
        var path = path
        if (path == null) path = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val f = File("./img/$path.gif")
        if (!f.parentFile.exists()) f.parentFile.mkdirs()
        age.start("./img/$path.gif")
    }

    fun writeImage(bimg: BufferedImage?, f: File): Boolean {
        if (bimg == null) return false
        var suc: Boolean = Data.Companion.err(Context.RunExc { Context.Companion.check(f) })
        if (suc) suc = try {
            ImageIO.write(bimg, "PNG", f)
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
        if (!suc) {
            ps!!.println("failed to write image: " + f.path)
            if (Opts.writeErr1(f.path)) Exporter(bimg, Exporter.Companion.EXP_ERR)
        }
        return suc
    }

    private fun writeFile(os: OutStream, f: File, md5: ByteArray): Boolean {
        var suc: Boolean = Data.Companion.err(Context.RunExc { Context.Companion.check(f) })
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(f)
            os.flush(fos)
            fos.close()
        } catch (e: IOException) {
            suc = false
            e.printStackTrace()
            Printer.w(130, "IOE!!!")
            Opts.ioErr("failed to write file $f")
            if (fos != null) try {
                fos.close()
            } catch (e1: IOException) {
                e1.printStackTrace()
            }
            e.printStackTrace()
        } finally {
            if (fos != null) try {
                fos.close()
            } catch (e1: IOException) {
                e1.printStackTrace()
            }
        }
        try {
            val cont = Files.readAllBytes(f.toPath())
            val nmd: ByteArray = MessageDigest.getInstance("MD5").digest(cont)
            suc = suc and Arrays.equals(md5, nmd)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return suc
    }

    private fun writeOptions() {
        val f = File("./user/config.json")
        val jo: JsonObject = JsonEncoder.Companion.encode(CommonStatic.getConfig()).getAsJsonObject()
        val r: Rectangle = MainFrame.Companion.crect
        jo.add("crect", JsonEncoder.Companion.encode(intArrayOf(r.x, r.y, r.width, r.height)))
        jo.addProperty("preload", MainBCU.preload)
        jo.addProperty("transparent", Conf.white)
        jo.addProperty("JOGL", MainBCU.USE_JOGL)
        jo.addProperty("filter", MainBCU.FILTER_TYPE)
        jo.addProperty("play_sound", BCMusic.play)
        jo.addProperty("volume_BG", BCMusic.VOL_BG)
        jo.addProperty("volume_SE", BCMusic.VOL_SE)
        jo.addProperty("edit_lang", MainLocale.Companion.exLang)
        jo.addProperty("edit_tooltip", MainLocale.Companion.exTTT)
        jo.addProperty("large_screen", BattleInfoPage.Companion.DEF_LARGE)
        jo.addProperty("style_light", MainBCU.light)
        jo.addProperty("style_nimbus", MainBCU.nimbus)
        val exp = arrayOfNulls<String>(Exporter.Companion.curs.size)
        for (i in exp.indices) exp[i] = if (Exporter.Companion.curs.get(i) == null) null else Exporter.Companion.curs.get(i).toString()
        val imp = arrayOfNulls<String>(Importer.Companion.curs.size)
        for (i in imp.indices) imp[i] = if (Importer.Companion.curs.get(i) == null) null else Importer.Companion.curs.get(i).toString()
        jo.add("export_paths", JsonEncoder.Companion.encode(exp))
        jo.add("import_paths", JsonEncoder.Companion.encode(imp))
        try {
            FileWriter(f).use { w ->
                w.write(jo.toString())
                w.close()
            }
        } catch (e: Exception) {
            CommonStatic.ctx.noticeErr(e, ErrType.ERROR, "failed to write config")
        }
    }
}

internal class WriteStream(file: File?) : PrintStream(file) {
    var writed = false
    override fun println(str: Any) {
        super.println(str)
        writed = true
    }

    override fun println(str: String) {
        super.println(str)
        writed = true
    }
}
