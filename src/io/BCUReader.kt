package io

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import common.CommonStatic
import common.battle.data.DataEntity
import common.io.DataIO
import common.io.InStream
import common.io.assets.Admin
import common.io.assets.Admin.StaticPermitted
import common.io.assets.AssetLoader
import common.io.assets.AssetLoader.AssetHeader
import common.io.assets.AssetLoader.AssetHeader.AssetEntry
import common.io.json.JsonDecoder
import common.io.json.JsonEncoder
import common.io.json.Test
import common.io.json.Test.JsonTest_0.JsonD
import common.io.json.Test.JsonTest_2
import common.pack.Context.ErrType
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.pack.Source.SourceAnimLoader
import common.pack.Source.SourceAnimSaver
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.pack.UserProfile
import common.util.lang.MultiLangCont
import common.util.stage.EStage
import common.util.stage.MapColc
import common.util.stage.MapColc.DefMapColc
import common.util.stage.Stage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.Enemy
import common.util.unit.UnitLevel
import io.BCPlayer
import main.MainBCU
import main.Opts
import page.JL
import page.LoadPage
import page.MainFrame
import page.MainLocale
import page.anim.AnimBox
import page.battle.BattleInfoPage
import page.support.Exporter
import page.support.Importer
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.Rectangle
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.nio.file.Files
import java.util.*

object BCUReader : DataIO {
    val `data$1`: Unit
        get() {
            readLang()
            BCMusic.preload()
        }

    fun readBytes(file: File): InStream? {
        try {
            val bs = Files.readAllBytes(file.toPath())
            return InStream.Companion.getIns(bs)
        } catch (e: IOException) {
            Opts.ioErr("failed to read file $file")
            e.printStackTrace()
        }
        return null
    }

    fun readInfo() {
        val f = File("./user/config.json")
        if (f.exists()) {
            try {
                FileReader(f).use { r ->
                    val je: JsonElement = JsonParser.parseReader(r)
                    r.close()
                    val cfg: CommonStatic.Config = CommonStatic.getConfig()
                    JsonDecoder.Companion.inject<CommonStatic.Config>(je, CommonStatic.Config::class.java, cfg)
                    val jo: JsonObject = je.getAsJsonObject()
                    val rect: IntArray = JsonDecoder.Companion.decode<IntArray>(jo.get("crect"), IntArray::class.java)
                    MainFrame.Companion.crect = Rectangle(rect[0], rect[1], rect[2], rect[3])
                    MainBCU.preload = jo.get("preload").getAsBoolean()
                    Conf.white = jo.get("transparent").getAsBoolean()
                    MainBCU.USE_JOGL = jo.get("JOGL").getAsBoolean()
                    MainBCU.FILTER_TYPE = jo.get("filter").getAsInt()
                    BCMusic.play = jo.get("play_sound").getAsBoolean()
                    BCMusic.VOL_BG = jo.get("volume_BG").getAsInt()
                    BCMusic.VOL_SE = jo.get("volume_SE").getAsInt()
                    MainLocale.Companion.exLang = jo.get("edit_lang").getAsBoolean()
                    MainLocale.Companion.exTTT = jo.get("edit_tooltip").getAsBoolean()
                    BattleInfoPage.Companion.DEF_LARGE = jo.get("large_screen").getAsBoolean()
                    MainBCU.light = jo.get("style_light").getAsBoolean()
                    MainBCU.nimbus = jo.get("style_nimbus").getAsBoolean()
                    val exp: Array<String?> = JsonDecoder.Companion.decode<Array<String>>(jo.get("export_paths"), Array<String>::class.java)
                    val imp: Array<String?> = JsonDecoder.Companion.decode<Array<String>>(jo.get("import_paths"), Array<String>::class.java)
                    for (i in Exporter.Companion.curs.indices) Exporter.Companion.curs.get(i) = if (exp[i] == null) null else File(exp[i])
                    for (i in Importer.Companion.curs.indices) Importer.Companion.curs.get(i) = if (imp[i] == null) null else File(imp[i])
                }
            } catch (e: Exception) {
                CommonStatic.ctx.noticeErr(e, ErrType.WARN, "failed to read config")
            }
        }
    }

    fun readLang() {
        LoadPage.Companion.prog("reading language information")
        val f = File("./assets/lang/")
        if (!f.exists()) return
        for (fi in f.listFiles()) {
            val ni = fi.name
            if (!fi.isDirectory) continue
            if (ni.length != 2) continue
            for (fl in fi.listFiles()) try {
                val nl = fl.name
                if (nl == "tutorial.txt") {
                    val qs = readLines(fl)
                    for (line in qs!!) {
                        val strs = line.trim { it <= ' ' }.split("\t").toTypedArray()
                        if (strs.size != 3) continue
                        MainLocale.Companion.addTTT(ni, strs[0].trim { it <= ' ' }, strs[1].trim { it <= ' ' }, strs[2].trim { it <= ' ' })
                    }
                    continue
                }
                if (nl == "StageName.txt") {
                    val qs = readLines(fl)
                    if (qs != null) for (str in qs) {
                        val strs = str.trim { it <= ' ' }.split("\t").toTypedArray()
                        if (strs.size == 1) continue
                        val idstr = strs[0].trim { it <= ' ' }
                        val name = strs[strs.size - 1].trim { it <= ' ' }
                        if (idstr.length == 0 || name.length == 0) continue
                        val ids = idstr.split("-").toTypedArray()
                        val id0: Int = CommonStatic.parseIntN(ids[0])
                        val mc: MapColc = DefMapColc.Companion.getMap(id0 * 1000).mc ?: continue
                        if (ids.size == 1) {
                            MultiLangCont.Companion.getStatic().MCNAME.put(ni, mc, name)
                            continue
                        }
                        val id1: Int = CommonStatic.parseIntN(ids[1])
                        if (id1 >= mc.maps.size || id1 < 0) continue
                        val sm: StageMap = mc.maps.get(id1) ?: continue
                        if (ids.size == 2) {
                            MultiLangCont.Companion.getStatic().SMNAME.put(ni, sm, name)
                            continue
                        }
                        val id2: Int = CommonStatic.parseIntN(ids[2])
                        if (id2 >= sm.list.size || id2 < 0) continue
                        val st: Stage = sm.list.get(id2)
                        MultiLangCont.Companion.getStatic().STNAME.put(ni, st, name)
                    }
                    continue
                }
                if (nl == "UnitName.txt") {
                    val qs = readLines(fl)
                    for (str in qs!!) {
                        val strs = str.trim { it <= ' ' }.split("\t").toTypedArray()
                        val u: common.util.unit.Unit = UserProfile.Companion.getBCData().units.get(CommonStatic.parseIntN(strs[0]))
                                ?: continue
                        for (i in 0 until Math.min(u.forms.size, strs.size - 1)) MultiLangCont.Companion.getStatic().FNAME.put(ni, u.forms[i], strs[i + 1].trim { it <= ' ' })
                    }
                    continue
                }
                if (nl == "EnemyName.txt") {
                    val qs = readLines(fl)
                    for (str in qs!!) {
                        val strs = str.trim { it <= ' ' }.split("\t").toTypedArray()
                        val e: Enemy = UserProfile.Companion.getBCData().enemies.get(CommonStatic.parseIntN(strs[0]))
                        if (e == null || strs.size < 2) continue
                        MultiLangCont.Companion.getStatic().ENAME.put(ni, e, strs[1].trim { it <= ' ' })
                    }
                    continue
                }
                if (!nl.endsWith(".properties")) continue
                val ml = MainLocale(nl.split("\\.").toTypedArray()[0] + "_" + ni)
                val qs = readLines(fl)
                for (line in qs!!) {
                    val strs = line.split("=|\t", 2.toBoolean()).toTypedArray()
                    if (strs.size < 2) continue
                    ml.res.put(strs[0], strs[1])
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun readLines(file: File): Queue<String>? {
        return try {
            ArrayDeque(Files.readAllLines(file.toPath()))
        } catch (e: IOException) {
            Opts.ioErr("failed to read file $file")
            e.printStackTrace()
            null
        }
    }
}
