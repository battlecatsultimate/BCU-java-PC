package page.info

import common.util.stage.CastleList
import common.util.stage.CharaGroup
import common.util.stage.LvRestrict
import common.util.stage.Stage
import page.MainFrame
import page.MainLocale
import page.Page
import page.info.HeadTable
import page.pack.CharaGroupPage
import page.pack.LvRestrictPage
import page.support.AbJTable
import page.view.BGViewPage
import page.view.CastleViewPage
import page.view.MusicPage
import java.awt.Point
import java.text.DecimalFormat

class HeadTable(private val page: Page) : AbJTable() {
    companion object {
        private const val serialVersionUID = 1L
        private val infs: Array<String>
        private val limits: Array<String>
        private val rarity: Array<String>
        fun redefine() {
            HeadTable.Companion.infs = Page.Companion.get(1, "ht0", 6)
            HeadTable.Companion.limits = Page.Companion.get(1, "ht1", 7)
            HeadTable.Companion.rarity = arrayOf("N", "EX", "R", "SR", "UR", "LR")
        }

        init {
            HeadTable.Companion.redefine()
        }
    }

    private var data: Array<Array<Any>>?
    private var sta: Stage? = null
    override fun getColumnClass(c: Int): Class<*> {
        return String::class.java
    }

    override fun getColumnCount(): Int {
        return 8
    }

    override fun getColumnName(arg0: Int): String {
        return ""
    }

    override fun getRowCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    override fun getValueAt(r: Int, c: Int): Any {
        return if (data == null || r < 0 || c < 0 || r >= data!!.size || c >= data!![r].length) null else data!![r][c]
    }

    fun clicked(p: Point) {
        if (data == null) return
        val c: Int = getColumnModel().getColumnIndexAtX(p.x)
        val r: Int = p.y / getRowHeight()
        if (r == 1 && c == 5) MainFrame.Companion.changePanel(MusicPage(page, sta!!.mus0))
        if (r == 1 && c == 7) MainFrame.Companion.changePanel(MusicPage(page, sta!!.mus1))
        if (r == 2 && c == 1) MainFrame.Companion.changePanel(BGViewPage(page, null, sta!!.bg))
        if (r == 2 && c == 3) MainFrame.Companion.changePanel(CastleViewPage(page, CastleList.Companion.from(sta), sta!!.castle))
        if (r == 2 && c == 5 && data!![r][c] != null && data!![r][c] is LvRestrict) MainFrame.Companion.changePanel(LvRestrictPage(page, data!![r][c] as LvRestrict))
        if (r == 2 && c == 7 && data!![r][c] != null) MainFrame.Companion.changePanel(CharaGroupPage(page, data!![r][c] as CharaGroup))
    }

    fun setData(st: Stage) {
        sta = st
        val lstr = Array<Array<Any>>(5) { arrayOfNulls(8) }
        val tit: Array<Any>
        val bas: Array<Any>
        val img: Array<Any>
        val rar: Array<Any>
        val reg: Array<Any>
        tit = lstr[0]
        bas = lstr[1]
        img = lstr[2]
        rar = lstr[3]
        reg = lstr[4]
        tit[0] = "ID:"
        tit[1] = st.map.mc.toString() + "-" + st.map.id + "-" + st.id()
        val star: String = page.Page.Companion.get(1, "star")
        for (i in st.map.stars.indices) tit[2 + i] = (i + 1).toString() + star + ": " + st.map.stars[i] + "%"
        bas[0] = page.info.HeadTable.Companion.infs.get(0)
        bas[1] = st.health
        bas[2] = page.info.HeadTable.Companion.infs.get(1).toString() + ": " + st.len
        bas[3] = page.info.HeadTable.Companion.infs.get(2).toString() + ": " + st.max
        bas[4] = page.Page.Companion.get(1, "mus") + ":"
        bas[5] = st.mus0
        bas[6] = "<" + st.mush + "%:"
        bas[7] = st.mus1
        img[0] = page.info.HeadTable.Companion.infs.get(4)
        img[1] = st.bg
        img[2] = page.info.HeadTable.Companion.infs.get(5)
        img[3] = st.castle
        img[4] = MainLocale.Companion.getLoc(1, "lop")
        img[5] = convertTime(st.loop0)
        img[6] = MainLocale.Companion.getLoc(1, "lop1")
        img[7] = convertTime(st.loop1)
        val lim = st.getLim(0)
        if (lim != null) {
            if (lim.rare != 0) {
                rar[0] = page.info.HeadTable.Companion.limits.get(0)
                var j = 1
                for (i in page.info.HeadTable.Companion.rarity.indices) if (lim.rare shr i and 1 > 0) rar[j++] = page.info.HeadTable.Companion.rarity.get(i)
            }
            if (lim.lvr != null) {
                img[4] = page.info.HeadTable.Companion.limits.get(6)
                img[5] = lim.lvr
            }
            if (lim.group != null) {
                img[6] = page.info.HeadTable.Companion.limits.get(5)
                img[7] = lim.group
            }
            if (lim.min + lim.max + lim.max + lim.line + lim.num > 0) {
                var i = 0
                if (lim.min > 0) {
                    reg[0] = page.info.HeadTable.Companion.limits.get(3)
                    reg[1] = "" + lim.min
                    i = 2
                }
                if (lim.max > 0) {
                    reg[i] = page.info.HeadTable.Companion.limits.get(4)
                    reg[i + 1] = "" + lim.max
                    i += 2
                }
                if (lim.num > 0) {
                    reg[i] = page.info.HeadTable.Companion.limits.get(1)
                    reg[i + 1] = "" + lim.num
                    i += 2
                }
                if (lim.line > 0) reg[i] = page.info.HeadTable.Companion.limits.get(2)
            }
        }
        data = lstr
    }

    private fun convertTime(milli: Long): String {
        var min = milli / 60 / 1000
        var time = milli - min.toDouble() * 60000
        time /= 1000.0
        val df = DecimalFormat("#.###")
        var s: Double = df.format(time).toDouble()
        if (s >= 60) {
            s -= 60.0
            min += 1
        }
        return if (s < 10) {
            min.toString() + ":" + "0" + df.format(s)
        } else {
            min.toString() + ":" + df.format(s)
        }
    }
}
