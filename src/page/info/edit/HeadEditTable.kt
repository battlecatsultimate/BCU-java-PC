package page.info.edit

import common.CommonStatic
import common.pack.PackData
import common.pack.PackData.UserPack
import common.util.pack.Background
import common.util.stage.*
import page.*
import page.view.BGViewPage
import page.view.CastleViewPage
import page.view.MusicPage
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.text.DecimalFormat
import java.util.*
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.SwingConstants

internal class HeadEditTable(p: Page, pack: UserPack) : Page(p) {
    private val hea: JL = JL(1, "ht00")
    private val len: JL = JL(1, "ht01")
    private val mus: JBTN = JBTN(1, "mus")
    private val max: JL = JL(1, "ht02")
    private val bg: JBTN = JBTN(1, "ht04")
    private val cas: JBTN = JBTN(1, "ht05")
    private val name: JTF = JTF()
    private val jhea: JTF = JTF()
    private val jlen: JTF = JTF()
    private val jbg: JTF = JTF()
    private val jcas: JTF = JTF()
    private val jm0: JTF = JTF()
    private val jmh: JTF = JTF()
    private val jm1: JTF = JTF()
    private val con: JTG = JTG(1, "ht03")
    private val star: Array<JTF?> = arrayOfNulls<JTF>(4)
    private val jmax: JTF = JTF()
    private val loop: JL = JL(1, "lop")
    private val loop1: JL = JL(1, "lop1")
    private val lop: JTF = JTF()
    private val lop1: JTF = JTF()
    private val lt: LimitTable
    private var sta: Stage? = null
    private val pac: UserPack
    private var bvp: BGViewPage? = null
    private var cvp: CastleViewPage? = null
    private var mp: MusicPage? = null
    private var musl = 0
    override fun callBack(o: Any?) {
        setData(sta)
    }

    override fun renew() {
        lt.renew()
        if (bvp != null) {
            val `val`: PackData.Identifier<Background> = bvp.getSelected().id
            jbg.text = `val`.toString()
            sta!!.bg = `val`
        }
        if (cvp != null) {
            val `val`: PackData.Identifier<CastleImg> = cvp.getVal() ?: return
            jcas.text = `val`.toString()
            sta!!.castle = `val`
        }
        if (mp != null) {
            val jtf: JTF = if (musl == 0) jm0 else jm1
            val `val`: PackData.Identifier<Music> = mp.getSelected()
            jtf.text = "" + `val`
            if (jtf === jm0) {
                sta!!.mus0 = `val`
                if (sta!!.mus0 != null) {
                    lop.isEnabled = true
                    getMusTime(sta!!.mus0, lop)
                } else {
                    lop.text = "00:00.000"
                    sta!!.loop0 = 0
                    lop.isEnabled = false
                }
            }
            if (jtf === jm1) {
                sta!!.mus1 = `val`
                if (sta!!.mus1 != null) {
                    lop1.isEnabled = true
                    getMusTime(sta!!.mus1, lop1)
                } else {
                    lop1.text = "00:00.000"
                    sta!!.loop1 = 0
                    lop1.isEnabled = false
                }
            }
        }
        bvp = null
        cvp = null
        mp = null
    }

    override fun resized(x: Int, y: Int) {
        val w = 1400 / 8
        Page.Companion.set(name, x, y, 0, 0, w * 2, 50)
        Page.Companion.set(hea, x, y, 0, 50, w, 50)
        Page.Companion.set(jhea, x, y, w, 50, w, 50)
        Page.Companion.set(len, x, y, w * 2, 50, w, 50)
        Page.Companion.set(jlen, x, y, w * 3, 50, w, 50)
        Page.Companion.set(max, x, y, w * 4, 50, w, 50)
        Page.Companion.set(jmax, x, y, w * 5, 50, w, 50)
        Page.Companion.set(con, x, y, w * 6, 50, w, 50)
        Page.Companion.set(bg, x, y, 0, 100, w, 50)
        Page.Companion.set(jbg, x, y, w, 100, w, 50)
        Page.Companion.set(cas, x, y, w * 2, 100, w, 50)
        Page.Companion.set(jcas, x, y, w * 3, 100, w, 50)
        Page.Companion.set(mus, x, y, 0, 150, w, 50)
        Page.Companion.set(jm0, x, y, w, 150, w, 50)
        Page.Companion.set(loop, x, y, w * 2, 150, w, 50)
        Page.Companion.set(lop, x, y, w * 3, 150, w, 50)
        Page.Companion.set(jmh, x, y, w * 4, 150, w, 50)
        Page.Companion.set(jm1, x, y, w * 5, 150, w, 50)
        Page.Companion.set(loop1, x, y, w * 6, 150, w, 50)
        Page.Companion.set(lop1, x, y, w * 7, 150, w, 50)
        for (i in 0..3) Page.Companion.set(star[i], x, y, w * (2 + i), 0, w, 50)
        Page.Companion.set(lt, x, y, 0, 200, 1400, 100)
        lt.componentResized(x, y)
    }

    fun setData(st: Stage?) {
        sta = st
        abler(st != null)
        if (st == null) return
        change(true)
        name.text = st.toString()
        jhea.text = "" + st.health
        jlen.text = "" + st.len
        jbg.text = "" + st.bg
        jcas.text = "" + st.castle
        jm0.text = "" + st.mus0
        jmh.text = "<" + st.mush + "% health:"
        jm1.text = "" + st.mus1
        jmax.text = "" + st.max
        con.isSelected = !st.non_con
        val str: String = Page.Companion.get(1, "star") + ": "
        for (i in 0..3) if (i < st.map.stars.size) star[i].setText(i + 1 + str + st.map.stars[i] + "%") else star[i].setText(i + 1 + str + "/")
        val lim = st.lim
        lt.setLimit(lim)
        change(false)
        lop.text = convertTime(sta!!.loop0)
        lop1.text = convertTime(sta!!.loop1)
        if (sta!!.mus0 != null) {
            lop.isEnabled = true
            getMusTime(sta!!.mus0, lop)
        } else {
            lop.text = "00:00.000"
            lop.toolTipText = "No music"
            sta!!.loop0 = 0
            lop.isEnabled = false
        }
        if (sta!!.mus1 != null) {
            lop1.isEnabled = true
            getMusTime(sta!!.mus1, lop1)
        } else {
            lop1.text = "00:00.000"
            lop1.toolTipText = "No music"
            sta!!.loop1 = 0
            lop1.isEnabled = false
        }
    }

    private fun abler(b: Boolean) {
        bg.isEnabled = b
        cas.isEnabled = b
        name.isEnabled = b
        jhea.isEnabled = b
        jlen.isEnabled = b
        jbg.isEnabled = b
        jcas.isEnabled = b
        jmax.isEnabled = b
        con.isEnabled = b
        mus.isEnabled = b
        jm0.isEnabled = b
        jmh.isEnabled = b
        jm1.isEnabled = b
        for (jtf in star) jtf.setEnabled(b)
        lt.abler(b)
    }

    private fun addListeners() {
        bg.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                bvp = BGViewPage(front, pac.desc.id, sta!!.bg)
                changePanel(bvp)
            }
        })
        cas.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                cvp = CastleViewPage(front, CastleList.Companion.from(sta), sta!!.castle)
                changePanel(cvp)
            }
        })
        mus.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                mp = MusicPage(front, pac.desc.id)
                changePanel(mp)
            }
        })
        con.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                sta!!.non_con = !con.isSelected
                setData(sta)
            }
        })
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

    private fun getMusTime(mus1: PackData.Identifier<Music>, jtf: JTF) {
        val f: Music? = mus1.get()
        if (f == null || f.data == null) {
            jtf.toolTipText = "Music not found"
            return
        }
        try {
            val duration: Long = CommonStatic.def.getMusicLength(f)
            if (duration == -1L) {
                jtf.toolTipText = "Invalid Format"
            } else if (duration == -2L) {
                jtf.toolTipText = "Unsupported Format"
            } else if (duration == -3L) {
                jtf.toolTipText = "Can't get duration"
            } else if (duration >= 0) {
                jtf.toolTipText = convertTime(duration)
            } else {
                jtf.toolTipText = "Unknown error $duration"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun ini() {
        set(hea)
        set(len)
        set(max)
        add(bg)
        add(cas)
        add(con)
        add(mus)
        set(jhea)
        set(jlen)
        set(jbg)
        set(jcas)
        set(jmax)
        set(name)
        set(jm0)
        set(jmh)
        set(jm1)
        add(lt)
        set(loop)
        set(lop)
        set(loop1)
        set(lop1)
        con.isSelected = true
        for (i in 0..3) set(JTF().also { star[i] = it })
        addListeners()
        abler(false)
    }

    private fun input(jtf: JTF, str: String) {
        var str = str
        if (sta == null) return
        if (jtf === name) {
            str = str.trim { it <= ' ' }
            if (str.length > 0) sta!!.name = str
            for (r in Recd.Companion.map.values) if (r.st === sta) r.marked = true
            return
        }
        var `val`: Int = CommonStatic.parseIntN(str)
        if (jtf === jhea) {
            if (`val` <= 0) return
            sta!!.health = `val`
        }
        if (jtf === jlen) {
            if (`val` > 8000) `val` = 8000
            if (`val` < 2000) `val` = 2000
            sta!!.len = `val`
        }
        if (jtf === jmax) {
            if (`val` <= 0 || `val` > 50) return
            sta!!.max = `val`
        }
        for (i in 0..3) if (jtf === star[i]) {
            val strs = str.split(" ").toTypedArray()
            val vals: IntArray = CommonStatic.parseIntsN(strs[strs.size - 1])
            `val` = if (vals.size == 0) -1 else vals[vals.size - 1]
            val sr = sta!!.map.stars
            if (i == 0 && `val` <= 0) `val` = 100
            if (i < sr.size) if (`val` > 0) sr[i] = `val` else sta!!.map.stars = Arrays.copyOf(sr, i) else if (`val` > 0) {
                val ans = IntArray(i + 1)
                for (j in 0 until i) if (j < sr.size) ans[j] = sr[j] else ans[j] = sr[sr.size - 1]
                ans[i] = `val`
                sta!!.map.stars = ans
            }
        }
        if (jtf === jmh) sta!!.mush = `val`
        if (jtf === lop) {
            val tim = toMilli(jtf.text)
            if (tim != -1L) {
                sta!!.loop0 = tim
            }
            lop.text = convertTime(sta!!.loop0)
        }
        if (jtf === lop1) {
            val tim = toMilli(jtf.text)
            if (tim != -1L) {
                sta!!.loop1 = tim
            }
            lop1.text = convertTime(sta!!.loop1)
        }
    }

    private fun set(jl: JLabel) {
        jl.horizontalAlignment = SwingConstants.CENTER
        jl.border = BorderFactory.createEtchedBorder()
        add(jl)
    }

    private fun set(jtf: JTF) {
        add(jtf)
        jtf.addFocusListener(object : FocusAdapter() {
            override fun focusGained(fe: FocusEvent?) {
                if (isAdj) return
                if (jtf === jm0) musl = 0
                if (jtf === jm1) musl = 1
            }

            override fun focusLost(fe: FocusEvent?) {
                if (isAdj) return
                input(jtf, jtf.text)
                setData(sta)
            }
        })
    }

    private fun toMilli(time: String): Long {
        return try {
            val times: LongArray = CommonStatic.parseLongsN(time)
            for (t in times) {
                if (t < 0) {
                    return -1
                }
            }
            if (times.size == 1) {
                times[0] * 1000
            } else if (times.size == 2) {
                (times[0] * 60 + times[1]) * 1000
            } else if (times.size == 3) {
                if (times[2] < 1000) {
                    (times[0] * 60 + times[1]) * 1000 + times[2]
                } else {
                    val decimal = java.lang.Long.toString(times[2]).substring(0, 3)
                    (times[0] * 60 + times[1]) * 1000 + decimal.toInt()
                }
            } else {
                -1
            }
        } catch (e: Exception) {
            -1
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        pac = pack
        lt = LimitTable(p, this, pac)
        ini()
    }
}
