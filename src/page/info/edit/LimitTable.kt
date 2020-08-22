package page.info.edit

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
import common.pack.PackData.UserPack
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.pack.Source.SourceAnimLoader
import common.pack.Source.SourceAnimSaver
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.util.stage.EStage
import common.util.stage.Limit
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JBTN
import page.JL
import page.JTF
import page.JTG
import page.Page
import page.anim.AnimBox
import page.pack.CharaGroupPage
import page.pack.LvRestrictPage
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.SwingConstants

class LimitTable(private val main: Page, private val par: Page, p: UserPack) : Page(null) {
    companion object {
        private const val serialVersionUID = 1L
        private var limits: Array<String>
        private var rarity: Array<String>
        fun redefine() {
            limits = Page.Companion.get(1, "ht1", 7)
            rarity = arrayOf("N", "EX", "R", "SR", "UR", "LR")
        }

        init {
            redefine()
        }
    }

    private val jcmin: JTF = JTF()
    private val jnum: JTF = JTF()
    private val jcmax: JTF = JTF()
    private val jcg: JTF = JTF()
    private val jlr: JTF = JTF()
    private val cgb: JBTN = JBTN(1, "ht15")
    private val lrb: JBTN = JBTN(1, "ht16")
    private val one: JTG = JTG(1, "ht12")
    private val rar: JL = JL(1, "ht10")
    private val brars: Array<JTG?> = arrayOfNulls<JTG>(6)
    private val pac: UserPack
    private var cgp: CharaGroupPage? = null
    private var lrp: LvRestrictPage? = null
    private var lim: Limit? = null
    fun abler(b: Boolean) {
        jcmin.setEnabled(b)
        jnum.setEnabled(b)
        jcmax.setEnabled(b)
        one.setEnabled(b)
        cgb.setEnabled(b)
        jcg.setEnabled(b)
        lrb.setEnabled(b)
        jlr.setEnabled(b)
        for (jtb in brars) jtb.setEnabled(b)
    }

    public override fun renew() {
        if (cgp != null) {
            jcg.setText("" + cgp.cg)
            input(jcg, if (cgp.cg == null) "-1" else cgp.cg.toString())
        }
        if (lrp != null) {
            jlr.setText("" + lrp.lr)
            input(jlr, if (lrp.lr == null) "-1" else lrp.lr.toString())
        }
        cgp = null
        lrp = null
    }

    override fun resized(x: Int, y: Int) {
        val w = 1400 / 8
        Page.Companion.set(rar, x, y, 0, 0, w, 50)
        for (i in brars.indices) Page.Companion.set(brars[i], x, y, w + w * i, 0, w, 50)
        Page.Companion.set(cgb, x, y, w * 4, 50, w, 50)
        Page.Companion.set(jcg, x, y, w * 5, 50, w, 50)
        Page.Companion.set(lrb, x, y, w * 6, 50, w, 50)
        Page.Companion.set(jlr, x, y, w * 7, 50, w, 50)
        Page.Companion.set(jcmin, x, y, 0, 50, w, 50)
        Page.Companion.set(jcmax, x, y, w, 50, w, 50)
        Page.Companion.set(jnum, x, y, w * 2, 50, w, 50)
        Page.Companion.set(one, x, y, w * 3, 50, w, 50)
    }

    fun setLimit(l: Limit?) {
        lim = l
        if (l == null) {
            for (i in brars.indices) brars[i].setSelected(false)
            jcmax.setText(limits[4] + ": ")
            jcmin.setText(limits[3] + ": ")
            jnum.setText(limits[1] + ": ")
            jcg.setText("")
            jlr.setText("")
            one.setSelected(false)
            abler(false)
            return
        }
        abler(true)
        if (lim!!.rare > 0) {
            for (i in brars.indices) brars[i].setSelected(lim!!.rare shr i and 1 > 0)
        } else {
            for (i in brars.indices) brars[i].setSelected(true)
        }
        jcmax.setText(limits[4] + ": " + lim!!.max)
        jcmin.setText(limits[3] + ": " + lim!!.min)
        jnum.setText(limits[1] + ": " + lim!!.num)
        jcg.setText("" + lim!!.group)
        jlr.setText("" + lim!!.lvr)
        one.setSelected(lim!!.line == 1)
    }

    private fun addListeners() {
        one.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                lim!!.line = if (one.isSelected()) 1 else 0
            }
        })
        for (i in brars.indices) {
            brars[i].addActionListener(object : ActionListener {
                override fun actionPerformed(e: ActionEvent?) {
                    if (par.isAdj) return
                    lim!!.rare = lim!!.rare xor (1 shl i)
                    par.callBack(lim)
                }
            })
        }
        cgb.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                cgp = CharaGroupPage(main, pac, false)
                changePanel(cgp)
            }
        })
        lrb.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                lrp = LvRestrictPage(main, pac, false)
                changePanel(lrp)
            }
        })
    }

    private fun ini() {
        set(rar)
        add(cgb)
        add(lrb)
        add(one)
        set(jcmin)
        set(jcmax)
        set(jnum)
        set(jcg)
        set(jlr)
        for (i in brars.indices) {
            add(JTG(rarity[i]).also { brars[i] = it })
            brars[i].setSelected(true)
        }
        addListeners()
    }

    private fun input(jtf: JTF, str: String) {
        val `val`: Int = CommonStatic.parseIntN(str)
        if (jtf === jcmax) {
            if (`val` < 0) return
            lim!!.max = `val`
        }
        if (jtf === jcmin) {
            if (`val` < 0) return
            lim!!.min = `val`
        }
        if (jtf === jnum) {
            if (`val` < 0 || `val` > 50) return
            lim!!.num = `val`
        }
        if (jtf === jcg) lim!!.group = pac.groups.get(`val`)
        if (jtf === jlr) lim!!.lvr = pac.lvrs.get(`val`)
    }

    private fun set(jl: JLabel) {
        jl.horizontalAlignment = SwingConstants.CENTER
        jl.border = BorderFactory.createEtchedBorder()
        add(jl)
    }

    private fun set(jtf: JTF) {
        add(jtf)
        jtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(fe: FocusEvent?) {
                if (par.isAdj) return
                input(jtf, jtf.getText())
                par.callBack(lim)
            }
        })
    }

    init {
        pac = p
        ini()
    }
}
