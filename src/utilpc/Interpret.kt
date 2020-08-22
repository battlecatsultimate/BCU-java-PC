package utilpc

import common.CommonStatic
import common.battle.BasisSet
import common.battle.Treasure
import common.pack.PackData
import common.util.Data
import common.util.Data.Proc.ProcItem
import common.util.lang.Formatter
import common.util.lang.ProcLang
import common.util.stage.MapColc
import common.util.stage.MapColc.DefMapColc
import common.util.unit.Combo
import common.util.unit.Enemy
import page.Page
import java.util.*

object Interpret : Data {
    /** enemy types  */
    var ERARE: Array<String>

    /** unit rarities  */
    var RARITY: Array<String>

    /** enemy traits  */
    var TRAIT: Array<String>

    /** star names  */
    var STAR: Array<String>

    /** ability name  */
    var ABIS: Array<String>

    /** enemy ability name  */
    var EABI: Array<String?>
    var SABIS: Array<String>
    var PROC: Array<String>
    var SPROC: Array<String>
    var TREA: Array<String>
    var TEXT: Array<String>
    var ATKCONF: Array<String>
    var COMF: Array<String>
    var COMN: Array<String>
    var TCTX: Array<String>
    var PCTX: Array<String>

    /** treasure orderer  */
    val TIND = intArrayOf(0, 1, 18, 19, 20, 21, 22, 23, 2, 3, 4, 5, 24, 25, 26, 27, 28, 6, 7, 8, 9, 10, 11,
            12, 13, 14, 15, 16, 17, 29, 30, 31, 32, 33, 34, 35, 36)

    /** treasure grouper  */
    val TCOLP = arrayOf(intArrayOf(0, 6), intArrayOf(8, 6), intArrayOf(14, 3), intArrayOf(17, 4), intArrayOf(21, 3), intArrayOf(29, 8))

    /** treasure max  */
    private val TMAX = intArrayOf(30, 30, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 600, 1500, 100,
            100, 100, 30, 30, 30, 30, 30, 10, 300, 300, 600, 600, 600, 20, 30, 30, 30, 20, 20, 20, 20)

    /** proc data formatter  */
    private val CMP = arrayOf(intArrayOf(0, -1), intArrayOf(0, -1, 1), intArrayOf(0, -1, 1), intArrayOf(0, -1), intArrayOf(0, 2, -1), intArrayOf(0, -1, 3, 1), intArrayOf(0, -1), intArrayOf(0, -1, 1, 4), intArrayOf(0, -1, 1), intArrayOf(5, -1, 7), intArrayOf(0, -1), intArrayOf(-1, 4, 6), intArrayOf(-1, 1, 5, 6), intArrayOf(-1, 7), intArrayOf(-1, 7), intArrayOf(-1, 7), intArrayOf(-1, 7), intArrayOf(-1, 7), intArrayOf(-1, 7), intArrayOf(-1, 7), intArrayOf(0, -1), intArrayOf(0, -1, 1), intArrayOf(0, -1, 1), intArrayOf(0, -1, 4), intArrayOf(0, -1, 1), intArrayOf(0, -1, 1), intArrayOf(0, -1), intArrayOf(0, -1), intArrayOf(-1), intArrayOf(0, -1, 7), intArrayOf(0, -1, 1), intArrayOf(0, -1, 7), intArrayOf(0, -1, 4, 8, 1), intArrayOf(-1), intArrayOf(-1), intArrayOf(0, -1, 1, 3), intArrayOf(0, -1, 1))

    /** combo string component  */
    private val CDP = arrayOf(arrayOf("", "+", "-"), arrayOf("_", "_%", "_f", "Lv._"))

    /** combo string formatter  */
    private val CDC = arrayOf(intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 3), intArrayOf(1, 0), intArrayOf(1, 1), intArrayOf(2, 2), intArrayOf(), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(2, 2), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1))
    val EABIIND = intArrayOf(5, 7, 8, 9, 10, 11, 12, 15, 16, 18, 113, 114, 115, 116, 117, 118, 119, 133,
            134)
    val ABIIND = intArrayOf(113, 114, 115, 116, 117, 118, 119, 133, 134)
    const val IMUSFT = 13
    const val EFILTER = 8
    fun allRangeSame(me: MaskEntity): Boolean {
        return if (me is CustomEntity) {
            val near: MutableList<Int> = ArrayList()
            val far: MutableList<Int> = ArrayList()
            for (atk in (me as CustomEntity).atks) {
                near.add(atk.getShortPoint())
                far.add(atk.getLongPoint())
            }
            if (near.isEmpty() && far.isEmpty()) {
                return true
            }
            for (n in near) {
                if (n != near[0]) {
                    return false
                }
            }
            for (f in far) {
                if (f != far[0]) {
                    return false
                }
            }
            true
        } else {
            true
        }
    }

    fun comboInfo(c: Combo): String {
        return combo(c.type, CommonStatic.getBCAssets().values.get(c.type).get(c.lv))
    }

    fun comboInfo(inc: IntArray): Array<String> {
        val ls: MutableList<String> = ArrayList()
        for (i in 0 until Data.Companion.C_TOT) {
            if (inc[i] == 0) continue
            ls.add(combo(i, inc[i]))
        }
        return ls.toTypedArray()
    }

    fun getAbi(me: MaskEntity): List<String> {
        val tb: Int = me.touchBase()
        val ma: MaskAtk
        ma = if (me.getAtkCount() == 1) {
            me.getAtkModel(0)
        } else {
            me.getRepAtk()
        }
        val lds: Int
        val ldr: Int
        if (allRangeSame(me)) {
            lds = me.getAtkModel(0).getShortPoint()
            ldr = me.getAtkModel(0).getLongPoint() - me.getAtkModel(0).getShortPoint()
        } else {
            lds = ma.getShortPoint()
            ldr = ma.getLongPoint() - ma.getShortPoint()
        }
        val l: MutableList<String> = ArrayList()
        if (lds > 0) {
            val p0 = Math.min(lds, lds + ldr)
            val p1 = Math.max(lds, lds + ldr)
            val r = Math.abs(ldr)
            l.add(Page.Companion.get(3, "ld0") + ": " + tb + ", " + Page.Companion.get(3, "ld1") + ": " + p0 + "~" + p1 + ", "
                    + Page.Companion.get(3, "ld2") + ": " + r)
        }
        var imu: String = Page.Companion.get(3, "imu")
        for (i in ABIS.indices) if (me.getAbi() shr i and 1 > 0) if (ABIS[i].startsWith("IMU")) imu += ABIS[i].substring(3) + ", " else l.add(ABIS[i])
        if (imu.length > 10) l.add(imu)
        return l
    }

    fun getComboFilter(n: Int): Array<String?> {
        val res: IntArray = CommonStatic.getBCAssets().filter.get(n)
        val strs = arrayOfNulls<String>(res.size)
        for (i in res.indices) strs[i] = COMN[res[i]]
        return strs
    }

    fun getComp(ind: Int, t: Treasure): Int {
        var ans = -2
        for (i in 0 until TCOLP[ind][1]) {
            val temp = getValue(TIND[i + TCOLP[ind][0]], t)
            if (ans == -2) ans = temp else if (ans != temp) return -1
        }
        return ans
    }

    fun getProc(de: MaskEnemy): List<String> {
        val ctx = Formatter.Context(true, false)
        val l: MutableList<String> = ArrayList()
        val ma: MaskAtk = de.getRepAtk()
        for (i in PROC.indices) {
            val item: ProcItem = ma.getProc().getArr(i)
            if (!item.exists()) continue
            val format: String = ProcLang.Companion.get().get(i).format
            val formatted: String = Formatter.Companion.format(format, item, ctx)
            l.add(formatted)
        }
        return l
    }

    fun getProc(du: MaskEntity, t: Treasure?, trait: Int): List<String> {
        val l: MutableList<String> = ArrayList()
        val ctx = Formatter.Context(false, false)
        val ma: MaskAtk = du.getRepAtk()
        for (i in PROC.indices) {
            val item: ProcItem = ma.getProc().getArr(i)
            if (!item.exists()) continue
            val format: String = ProcLang.Companion.get().get(i).format
            val formatted: String = Formatter.Companion.format(format, item, ctx)
            l.add(formatted)
        }
        return l
    }

    fun getTrait(type: Int, star: Int): String {
        var ans = ""
        for (i in TRAIT.indices) if (type shr i and 1 > 0) ans += TRAIT[i] + ", "
        if (star > 0) ans += STAR[star]
        return ans
    }

    operator fun getValue(ind: Int, t: Treasure): Int {
        if (ind == 0) return t.tech.get(Data.Companion.LV_RES) else if (ind == 1) return t.tech.get(Data.Companion.LV_ACC) else if (ind == 2) return t.trea.get(Data.Companion.T_ATK) else if (ind == 3) return t.trea.get(Data.Companion.T_DEF) else if (ind == 4) return t.trea.get(Data.Companion.T_RES) else if (ind == 5) return t.trea.get(Data.Companion.T_ACC) else if (ind == 6) return t.fruit.get(Data.Companion.T_RED) else if (ind == 7) return t.fruit.get(Data.Companion.T_FLOAT) else if (ind == 8) return t.fruit.get(Data.Companion.T_BLACK) else if (ind == 9) return t.fruit.get(Data.Companion.T_ANGEL) else if (ind == 10) return t.fruit.get(Data.Companion.T_METAL) else if (ind == 11) return t.fruit.get(Data.Companion.T_ZOMBIE) else if (ind == 12) return t.fruit.get(Data.Companion.T_ALIEN) else if (ind == 13) return t.alien else if (ind == 14) return t.star else if (ind == 15) return t.gods.get(0) else if (ind == 16) return t.gods.get(1) else if (ind == 17) return t.gods.get(2) else if (ind == 18) return t.tech.get(Data.Companion.LV_BASE) else if (ind == 19) return t.tech.get(Data.Companion.LV_WORK) else if (ind == 20) return t.tech.get(Data.Companion.LV_WALT) else if (ind == 21) return t.tech.get(Data.Companion.LV_RECH) else if (ind == 22) return t.tech.get(Data.Companion.LV_CATK) else if (ind == 23) return t.tech.get(Data.Companion.LV_CRG) else if (ind == 24) return t.trea.get(Data.Companion.T_WORK) else if (ind == 25) return t.trea.get(Data.Companion.T_WALT) else if (ind == 26) return t.trea.get(Data.Companion.T_RECH) else if (ind == 27) return t.trea.get(Data.Companion.T_CATK) else if (ind == 28) return t.trea.get(Data.Companion.T_BASE) else if (ind == 29) return t.bslv.get(Data.Companion.BASE_H) else if (ind == 30) return t.bslv.get(Data.Companion.BASE_SLOW) else if (ind == 31) return t.bslv.get(Data.Companion.BASE_WALL) else if (ind == 32) return t.bslv.get(Data.Companion.BASE_STOP) else if (ind == 33) return t.bslv.get(Data.Companion.BASE_WATER) else if (ind == 34) return t.bslv.get(Data.Companion.BASE_GROUND) else if (ind == 35) return t.bslv.get(Data.Companion.BASE_BARRIER) else if (ind == 36) return t.bslv.get(Data.Companion.BASE_CURSE)
        return -1
    }

    fun isER(e: Enemy, t: Int): Boolean {
        if (t == 0) return e.inDic
        if (t == 1) return e.de.getStar() == 1
        val lis: List<MapColc> = e.findMap()
        var colab = false
        if (lis.contains(DefMapColc.Companion.getMap("C"))) if (lis.size == 1) colab = true else if (lis.size == 2) colab = lis.contains(DefMapColc.Companion.getMap("R")) || lis.contains(DefMapColc.Companion.getMap("CH"))
        if (t == 2) return !colab
        if (t == 3) return !colab && !e.inDic
        if (t == 4) return colab
        return if (t == 5) e.id.pack != PackData.Identifier.Companion.DEF else false
    }

    fun isType(de: MaskEntity, type: Int): Boolean {
        val raw: Array<IntArray> = de.rawAtkData()
        if (type == 0) return !de.isRange() else if (type == 1) return de.isRange() else if (type == 2) return de.isLD() else if (type == 3) return raw.size > 1 else if (type == 4) return de.isOmni() else if (type == 5) return de.getTBA() + raw[0][1] < de.getItv() / 2
        return false
    }

    fun redefine() {
        ERARE = Page.Companion.get(3, "er", 6)
        RARITY = Page.Companion.get(3, "r", 6)
        TRAIT = Page.Companion.get(3, "c", 12)
        STAR = Page.Companion.get(3, "s", 5)
        ABIS = Page.Companion.get(3, "a", 22)
        SABIS = Page.Companion.get(3, "sa", 22)
        ATKCONF = Page.Companion.get(3, "aa", 6)
        PROC = Page.Companion.get(3, "p", CMP.size)
        SPROC = Page.Companion.get(3, "sp", CMP.size)
        TREA = Page.Companion.get(3, "t", 37)
        TEXT = Page.Companion.get(3, "d", 9)
        COMF = Page.Companion.get(3, "na", 6)
        COMN = Page.Companion.get(3, "nb", 25)
        TCTX = Page.Companion.get(3, "tc", 6)
        PCTX = Page.Companion.get(3, "aq", 52)
        EABI = arrayOfNulls(EABIIND.size)
        for (i in EABI.indices) {
            if (EABIIND[i] < 100) EABI[i] = SABIS[EABIIND[i]] else EABI[i] = SPROC[EABIIND[i] - 100]
        }
    }

    fun setComp(ind: Int, v: Int, b: BasisSet) {
        for (i in 0 until TCOLP[ind][1]) setValue(TIND[i + TCOLP[ind][0]], v, b)
    }

    fun setValue(ind: Int, v: Int, b: BasisSet) {
        setVal(ind, v, b.t())
        for (bl in b.lb) setVal(ind, v, bl.t())
    }

    private fun combo(t: Int, `val`: Int): String {
        val con = CDC[t]
        return COMN[t] + " " + CDP[0][con[0]] + CDP[1][con[1]].replace("_".toRegex(), "" + `val`)
    }

    private fun setVal(ind: Int, v: Int, t: Treasure) {
        var v = v
        if (v < 0) v = 0
        if (v > TMAX[ind]) v = TMAX[ind]
        if (ind == 0) t.tech.get(Data.Companion.LV_RES) = v else if (ind == 1) t.tech.get(Data.Companion.LV_ACC) = v else if (ind == 2) t.trea.get(Data.Companion.T_ATK) = v else if (ind == 3) t.trea.get(Data.Companion.T_DEF) = v else if (ind == 4) t.trea.get(Data.Companion.T_RES) = v else if (ind == 5) t.trea.get(Data.Companion.T_ACC) = v else if (ind == 6) t.fruit.get(Data.Companion.T_RED) = v else if (ind == 7) t.fruit.get(Data.Companion.T_FLOAT) = v else if (ind == 8) t.fruit.get(Data.Companion.T_BLACK) = v else if (ind == 9) t.fruit.get(Data.Companion.T_ANGEL) = v else if (ind == 10) t.fruit.get(Data.Companion.T_METAL) = v else if (ind == 11) t.fruit.get(Data.Companion.T_ZOMBIE) = v else if (ind == 12) t.fruit.get(Data.Companion.T_ALIEN) = v else if (ind == 13) t.alien = v else if (ind == 14) t.star = v else if (ind == 15) t.gods.get(0) = v else if (ind == 16) t.gods.get(1) = v else if (ind == 17) t.gods.get(2) = v else if (ind == 18) t.tech.get(Data.Companion.LV_BASE) = v else if (ind == 19) t.tech.get(Data.Companion.LV_WORK) = v else if (ind == 20) t.tech.get(Data.Companion.LV_WALT) = v else if (ind == 21) t.tech.get(Data.Companion.LV_RECH) = v else if (ind == 22) t.tech.get(Data.Companion.LV_CATK) = v else if (ind == 23) t.tech.get(Data.Companion.LV_CRG) = v else if (ind == 24) t.trea.get(Data.Companion.T_WORK) = v else if (ind == 25) t.trea.get(Data.Companion.T_WALT) = v else if (ind == 26) t.trea.get(Data.Companion.T_RECH) = v else if (ind == 27) t.trea.get(Data.Companion.T_CATK) = v else if (ind == 28) t.trea.get(Data.Companion.T_BASE) = v else if (ind == 29) t.bslv.get(Data.Companion.BASE_H) = v else if (ind == 30) t.bslv.get(Data.Companion.BASE_SLOW) = v else if (ind == 31) t.bslv.get(Data.Companion.BASE_WALL) = v else if (ind == 32) t.bslv.get(Data.Companion.BASE_STOP) = v else if (ind == 33) t.bslv.get(Data.Companion.BASE_WATER) = v else if (ind == 34) t.bslv.get(Data.Companion.BASE_GROUND) = v else if (ind == 35) t.bslv.get(Data.Companion.BASE_BARRIER) = v else if (ind == 36) t.bslv.get(Data.Companion.BASE_CURSE) = v
    }

    init {
        redefine()
    }
}
