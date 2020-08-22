package io

import common.CommonStatic
import common.util.Data
import io.WebFileIO
import main.MainBCU
import main.Opts
import org.json.JSONArray
import org.json.JSONObject
import page.LoadPage
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object BCJSON : WebFileIO {
    const val WEBSITE = "https://battlecatsultimate.github.io/bcu-page"
    const val GITRES = "https://github.com/battlecatsultimate/bcu-resources/raw/master/resources/"
    var ID = 0
    var cal_ver = 0
    private const val req = WEBSITE + "/api/"
    private const val path = "./assets/"
    private const val DOWNLOAD_LIBS = true
    private val cals: Array<String?>
    fun checkDownload() {
        LoadPage.Companion.prog("check download")
        var f: File
        var data: JSONObject? = null
        try {
            data = BCJSON["getUpdate.json"]
        } catch (e: IOException) {
            e.printStackTrace()
        }
        checkAssets(data)
        if (DOWNLOAD_LIBS) checkLibs(data)
        if (data != null) {
            LoadPage.Companion.prog("check jar update...")
            val upds: JSONArray = data.getJSONArray("pc-jars")
            var lf: JSONArray? = null
            var lt: JSONArray? = upds.get(0) as JSONArray
            if (lt.getInt(2) == 1) lt = null
            for (i in 0 until upds.length()) {
                val v: JSONArray = upds.get(0) as JSONArray
                if (v.getInt(2) == 1) {
                    lf = v
                    break
                }
            }
            if (lt != null && MainBCU.ver < lt.getString(0).toInt()) {
                if (Opts.updateCheck("JAR", lt.getString(1))) {
                    val ver: Int = lt.getString(0).toInt()
                    val name = "BCU-" + Data.Companion.revVer(ver) + ".jar"
                    if (WebFileIO.Companion.download(GITRES + "jar/" + name, File("./$name"), LoadPage.Companion.lp)) CommonStatic.def.exit(false) else Opts.dloadErr(name)
                }
            }
            if (lf != null && MainBCU.ver < lf.getString(0).toInt()) {
                if (Opts.updateCheck("JAR", lf.getString(1))) {
                    val ver: Int = lf.getString(0).toInt()
                    val name = "BCU-" + Data.Companion.revVer(ver) + ".jar"
                    if (WebFileIO.Companion.download(GITRES + "jar/" + name, File("./$name"), LoadPage.Companion.lp)) CommonStatic.def.exit(false) else Opts.dloadErr(name)
                }
            }
            LoadPage.Companion.prog("check text update...")
            for (i in cals.indices) if (!File(path + cals[i]).also { f = it }.exists() && !WebFileIO.Companion.download(GITRES + cals[i], f, null)) Opts.dloadErr(cals[i])
            if (cal_ver < data.getInt("cal")) {
                if (Opts.updateCheck("text", "")) {
                    for (i in cals.indices) if (!WebFileIO.Companion.download(GITRES + cals[i], File(path + cals[i]), null)) Opts.dloadErr(cals[i])
                    cal_ver = data.getInt("cal")
                }
            }
            LoadPage.Companion.prog("check music update...")
            val music: Int = data.getInt("music")
            val mus = BooleanArray(music)
            val fs = arrayOfNulls<File>(music)
            var down = false
            for (i in 0 until music) {
                mus[i] = !File(path + "music/" + Data.Companion.trio(i) + ".ogg").also { fs[i] = it }.exists()
                down = down or mus[i]
            }
            if (down && Opts.updateCheck("music", "")) for (i in 0 until music) if (mus[i]) {
                LoadPage.Companion.prog("download musics: " + i + "/" + mus.size)
                if (!WebFileIO.Companion.download(GITRES + "music/" + Data.Companion.trio(i) + ".ogg", fs[i], LoadPage.Companion.lp)) Opts.dloadErr("music #$i")
            }
        }
        var need = ZipLib.info == null
        f = File(path + "calendar/")
        if (!f.exists().let { need = need or it; need }) f.mkdirs()
        for (i in cals.indices) need = need or !File(path + cals[i]).exists()
        if (need) {
            Opts.pop(Opts.REQITN)
            CommonStatic.def.exit(false)
        }
    }

    private fun checkAssets(lib: JSONObject?) {
        if (lib != null && lib.length() > 1) {
            val libmap: MutableMap<String, String> = TreeMap<String, String>()
            val ja: JSONArray = lib.getJSONArray("pc-assets")
            val n: Int = ja.length()
            for (i in 0 until n) {
                val ent: JSONArray = ja.getJSONArray(i)
                if (ent.getString(2).toInt() <= MainBCU.ver) libmap[ent.getString(0)] = ent.getString(1)
            }
            var libs: MutableList<String>
            libs = if (ZipLib.info != null) ZipLib.info.update(libmap.keys) else ArrayList(libmap.keys)
            var updated = false
            while (libs.size > 0) {
                val str = libs[0]
                libs.remove(str)
                val desc = libmap[str]
                libmap.remove(str)
                if (!Arrays.asList<Any>(ZipLib.LIBREQS).contains(str)) if (!Opts.conf("do you want to download lib update $str? $desc")) continue
                LoadPage.Companion.prog("downloading asset: $str.zip")
                val temp = File(path + if (ZipLib.lib == null) "assets.zip" else "temp.zip")
                val downl: Boolean = WebFileIO.Companion.download(GITRES + "assets/" + str + ".zip", temp, LoadPage.Companion.lp)
                if (downl) {
                    if (ZipLib.info == null) ZipLib.init() else ZipLib.merge(temp)
                    libs = ZipLib.info.update(libmap.keys)
                }
                updated = true
            }
            if (updated) {
                try {
                    ZipLib.lib.close()
                } catch (e: IOException) {
                    Opts.ioErr("failed to save downloads")
                    e.printStackTrace()
                }
                ZipLib.init()
            }
        }
        ZipLib.check()
    }

    private fun checkLibs(lib: JSONObject?) {
        if (lib != null && lib.length() > 1) {
            val list: MutableList<String> = ArrayList()
            val ja: JSONArray = lib.getJSONArray("pc-libs")
            for (i in 0 until ja.length()) list.add(ja.getString(i))
            val flib = File("./BCU_lib/")
            if (!flib.exists()) flib.mkdirs()
            for (fi in flib.listFiles()) list.remove(fi.name)
            for (str in list) {
                LoadPage.Companion.prog("download $str")
                val temp = File("./BCU_lib/$str")
                WebFileIO.Companion.download(GITRES + "jar/BCU_lib/" + str, temp, LoadPage.Companion.lp)
            }
        }
    }

    @Throws(IOException::class)
    private operator fun get(app: String): JSONObject {
        val url = URL(req + app)
        val conn = url.openConnection() as HttpURLConnection
        conn.connectTimeout = 5000
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
        conn.doOutput = true
        conn.doInput = true
        conn.requestMethod = "GET"
        val `in` = conn.inputStream
        val isr = InputStreamReader(`in`, "UTF-8")
        val result = readAll(BufferedReader(isr))
        if (!MainBCU.WRITE) println("result: $result")
        if (!result.startsWith("{")) throw IOException(result)
        val ans = JSONObject(result)
        `in`.close()
        conn.disconnect()
        return ans
    }

    private fun process(str: String): String {
        var str = str
        str = str.replace("\\'".toRegex(), "\\\\'")
        return str
    }

    @Throws(IOException::class)
    private fun read(json: String, app: String): JSONObject {
        val url = URL(req + app)
        val conn = url.openConnection() as HttpURLConnection
        conn.connectTimeout = 5000
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
        conn.doOutput = true
        conn.doInput = true
        conn.requestMethod = "GET"
        val os = conn.outputStream
        os.write(json.toByteArray(charset("UTF-8")))
        os.close()
        val `in` = conn.inputStream
        val isr = InputStreamReader(`in`, "UTF-8")
        val result = readAll(BufferedReader(isr))
        if (!MainBCU.WRITE) println("result: $result")
        if (!result.startsWith("{")) throw IOException(result)
        val ans = JSONObject(result)
        `in`.close()
        conn.disconnect()
        return ans
    }

    @Throws(IOException::class)
    private fun readAll(rd: Reader): String {
        val sb = StringBuilder()
        var cp: Int
        while (rd.read().also { cp = it } != -1) {
            sb.append(cp.toChar())
        }
        return sb.toString()
    }

    init {
        cals = arrayOfNulls(33)
        val cal = "calendar/"
        cals[0] = io.cal + "event ID.txt"
        cals[1] = io.cal + "gacha ID.txt"
        cals[2] = io.cal + "item ID.txt"
        cals[3] = io.cal + "group event.txt"
        cals[4] = io.cal + "group hour.txt"
        for (i in 0..3) {
            val lang = "lang/" + CommonStatic.Lang.Companion.LOC_CODE.get(i) + "/"
            cals[i * 7 + 5] = io.lang + "util.properties"
            cals[i * 7 + 6] = io.lang + "page.properties"
            cals[i * 7 + 7] = io.lang + "info.properties"
            cals[i * 7 + 8] = io.lang + "internet.properties"
            cals[i * 7 + 9] = io.lang + "StageName.txt"
            cals[i * 7 + 10] = io.lang + "UnitName.txt"
            cals[i * 7 + 11] = io.lang + "EnemyName.txt"
            // TODO tutorial
        }
    }
}
