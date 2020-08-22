package page

import common.CommonStatic
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
import main.MainBCU
import page.JL
import page.anim.AnimBox
import page.basis.ComboListTable
import page.battle.BattleInfoPage
import page.info.HeadTable
import page.info.StageTable
import page.info.edit.StageEditPage
import page.info.filter.EnemyListTable
import page.info.filter.UnitListTable
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import utilpc.Interpret
import java.io.PrintStream
import java.util.*

// FIXME include page.pack import page.pack.EREditPage;
@Strictfp
class MainLocale(str: String) {
    companion object {
        val NAMP: MutableMap<String, MainLocale> = TreeMap<String, MainLocale>()
        val TMAP: MutableMap<String, TTT> = TreeMap<String, TTT>()
        val LOC_NAME = arrayOf("English", "\u4E2D\u6587", "\uD55C\uAD6D\uC5B4", "Japanese")
        val RENN = arrayOf("page", "info", "internet", "util")
        private val RENS: Array<ResourceBundle?> = arrayOfNulls<ResourceBundle>(4)
        var exLang = false
        var exTTT = false
        fun addTTT(loc: String, page: String, text: String, cont: String): TTT? {
            var ttt = TMAP[loc]
            if (ttt == null) TMAP[loc] = TTT().also { ttt = it }
            if (text == "*") ttt!!.tttp[page] = cont else if (page == "*") ttt!!.ttts[text] = cont else {
                if (!ttt!!.ttt.containsKey(page)) ttt!!.ttt[page] = TreeMap<String, String>()
                ttt!!.ttt[page]!![text] = cont
            }
            return ttt
        }

        fun getLoc(loc: Int, key: String?): String? {
            if (loc >= 0 && loc < 4) {
                val loci = RENN[loc] + "_"
                val locl = loci + langCode()
                if (NAMP.containsKey(locl) && NAMP[locl]!!.contains(key)) {
                    var str = NAMP[loci + langCode()]!![key]
                    if (str == "(null)") str = RENS[loc].getString(key)
                    val strs: Array<String?> = str!!.split("#").toTypedArray()
                    if (strs.size == 1) return str
                    run {
                        var i = 1
                        while (i < strs.size) {
                            strs[i] = getLoc(loc, strs[i])
                            i += 2
                        }
                    }
                    var ans: String? = ""
                    for (i in strs.indices) ans += strs[i]
                    return ans
                }
                return try {
                    if (exLang) "[$loci$key]" else RENS[loc].getString(key)
                } catch (e: MissingResourceException) {
                    key
                }
            }
            return key
        }

        fun getLoc(loc: Int, vararg strs: String?): Array<String?> {
            val ans = arrayOfNulls<String>(strs.size)
            for (i in ans.indices) ans[i] = getLoc(loc, strs[i])
            return ans
        }

        fun getLoc(loc: Int, pre: String, max: Int): Array<String?> {
            val ans = arrayOfNulls<String>(max)
            for (i in ans.indices) ans[i] = getLoc(loc, pre + i)
            return ans
        }

        fun saveWorks() {
            for (loc in CommonStatic.Lang.Companion.LOC_CODE) {
                val ttt = TMAP[loc]
                if (ttt != null && ttt.edited) ttt.write(BCUWriter.newFile("./lib/lang/$loc/tutorial.txt"))
                for (i in 0..3) {
                    val ml = NAMP[RENN[i] + "_" + loc]
                    if (ml != null && ml.edited) ml.write(BCUWriter.newFile("./lib/lang/" + loc + "/" + RENN[i] + ".properties"))
                }
            }
        }

        fun getTTT(page: String, text: String): String? {
            var loc = langCode()
            var ans: String? = null
            if (TMAP.containsKey(loc)) ans = TMAP[loc]!!.getTTT(page, text)
            if (ans != null) return ans
            if (exTTT) return "[" + page + "_" + text + "]"
            loc = CommonStatic.Lang.Companion.LOC_CODE.get(0)
            return if (TMAP.containsKey(loc)) TMAP[loc]!!.getTTT(page, text) else null
        }

        fun redefine() {
            Interpret.redefine()
            EnemyListTable.Companion.redefine()
            UnitListTable.Companion.redefine()
            ComboListTable.Companion.redefine()
            StageTable.Companion.redefine()
            HeadTable.Companion.redefine()
            BattleInfoPage.Companion.redefine()
            StageEditPage.Companion.redefine()
            // FIXME inlucde page.pack EREditPage.redefine();
        }

        fun setLoc(i: Int, key: String?, value: String) {
            if (i < 0) return
            val loc = RENN[i] + "_" + langCode()
            var ml = NAMP[loc]
            if (ml == null) NAMP[loc] = MainLocale(loc).also { ml = it }
            ml!!.res[key] = value
            ml!!.edited = true
        }

        fun setTTT(loc: String, info: String, str: String) {
            addTTT(langCode(), loc, info, str)!!.edited = true
        }

        private fun langCode(): String {
            return CommonStatic.Lang.Companion.LOC_CODE.get(CommonStatic.getConfig().lang)
        }

        init {
            for (i in 0..3) RENS[i] = ResourceBundle.getBundle((if (MainBCU.WRITE) "src/" else "") + "page/" + RENN[i], Locale.ENGLISH)
        }
    }

    val res: MutableMap<String?, String> = TreeMap<String, String>()
    var edited = false
    fun write(ps: PrintStream) {
        for ((key, value) in res) ps.println(key.toString() + "\t" + value)
    }

    private operator fun contains(str: String?): Boolean {
        return res.containsKey(str)
    }

    private operator fun get(str: String?): String? {
        return if (res.containsKey(str)) res[str] else "!$str!"
    }

    init {
        NAMP[str] = this
    }
}

class TTT {
    val tttp: MutableMap<String, String> = TreeMap<String, String>()
    val ttts: MutableMap<String, String> = TreeMap<String, String>()
    val ttt: MutableMap<String, MutableMap<String, String>?> = TreeMap<String, Map<String, String>>()
    var edited = false
    fun write(ps: PrintStream?) {
        if (ps == null) return
        for ((key, value) in tttp) ps.println("$key\t*\t$value")
        for ((key, value) in ttts) ps.println("*\t$key\t$value")
        for ((key, value) in ttt) for ((key1, value1) in value!!) ps.println(key + "\t" + key1 + "\t" + value1)
    }

    fun getTTT(page: String, text: String): String? {
        if (ttt[page] != null) {
            val strs = ttt[page]!![text]
            if (strs != null) return strs
        }
        val strt = ttts[text]
        return strt ?: tttp[page]
    }
}
