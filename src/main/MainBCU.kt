package main

import common.CommonStatic
import common.battle.BasisSet
import common.io.PackLoader.ZipDesc.FileDesc
import common.io.assets.Admin
import common.io.assets.AssetLoader
import common.pack.Context
import common.pack.Context.ErrType
import common.pack.Source.Workspace
import common.pack.UserProfile
import common.system.fake.ImageBuilder
import common.util.Data
import io.BCUReader
import io.BCUWriter
import jogl.GLBBB
import jogl.util.GLIB
import page.LoadPage
import page.MainFrame
import page.MainPage
import page.Page
import page.awt.AWTBBB
import page.awt.BBBuilder
import utilpc.Theme
import utilpc.UtilPC.PCItr
import utilpc.awt.PCIB
import java.awt.Color
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.function.Consumer

object MainBCU {
    const val ver = 50000
    var FILTER_TYPE = 0
    val WRITE = !File("./.gitignore").exists()
    var preload = false
    var trueRun = true
    var loaded = false
    var USE_JOGL = true
    var light = false
    var nimbus = true
    val time: String
        get() = SimpleDateFormat("yyyyMMddHHmmss").format(Date())

    @JvmStatic
    fun main(args: Array<String>) {
        trueRun = true
        val mem = Runtime.getRuntime().maxMemory()
        if (mem shr 28 == 0L) {
            Opts.pop(Opts.MEMORY, "" + (mem shr 20))
            System.exit(0)
        }
        UserProfile.Companion.profile()
        CommonStatic.def = PCItr()
        CommonStatic.ctx = AdminContext()
        BCUWriter.logPrepare()
        BCUWriter.logSetup()
        BCUReader.readInfo()
        ImageBuilder.Companion.builder = if (USE_JOGL) GLIB() else PCIB()
        BBBuilder.Companion.def = if (USE_JOGL) GLBBB() else AWTBBB.Companion.INS
        if (nimbus) {
            if (light) {
                Theme.LIGHT.setTheme()
                Page.Companion.BGCOLOR = Color(255, 255, 255)
            } else {
                Theme.DARK.setTheme()
                Page.Companion.BGCOLOR = Color(40, 40, 40)
            }
        } else {
            Page.Companion.BGCOLOR = Color(255, 255, 255)
        }
        MainFrame(Data.Companion.revVer(ver)).initialize()
        Timer().start()
        CommonStatic.ctx.initProfile()
        // AssetLoader.previewAssets();
        // BCJSON.checkDownload();
        BCUReader.`getData$1`()
        loaded = true
        MainFrame.Companion.changePanel(MainPage())
    }

    fun validate(str: String): String {
        var str = str
        val chs = charArrayOf('.', '/', '\\', ':', '*', '?', '"', '<', '>', '|')
        for (c in chs) str = str.replace(c, '#')
        return str
    }

    class AdminContext : Context {
        override fun confirmDelete(): Boolean {
            println("skip delete confirmation")
            return true
        }

        override fun getAssetFile(string: String): File {
            return File("./assets/$string")
        }

        override fun getLangFile(file: String): File {
            return File("./assets/lang/en/$file")
        }

        override fun getPackFolder(): File {
            return File("./packs")
        }

        override fun getUserFile(string: String): File {
            return File("./user/$string")
        }

        override fun getWorkspaceFile(relativePath: String): File {
            return File("./workspace/$relativePath")
        }

        override fun initProfile() {
            LoadPage.Companion.prog("read assets")
            AssetLoader.load()
            LoadPage.Companion.prog("read BC data")
            UserProfile.Companion.getBCData().load(Consumer { str: String? -> LoadPage.Companion.prog(str) })
            LoadPage.Companion.prog("read basis")
            BasisSet.Companion.read()
            LoadPage.Companion.prog("read local animations")
            Workspace.Companion.loadAnimations(null)
            // UserProfile.loadPacks(); TODO
            LoadPage.Companion.prog("finish reading")
        }

        override fun noticeErr(e: Exception, t: ErrType, str: String?) {
            printErr(t, str)
            e.printStackTrace(if (t == ErrType.INFO) System.out else System.err)
        }

        override fun preload(desc: FileDesc): Boolean {
            return Admin.preload(desc)
        }

        override fun printErr(t: ErrType, str: String?) {
            (if (t == ErrType.INFO) System.out else System.err).println(str)
        }
    }
}
