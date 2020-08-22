package page.battle

import common.battle.BasisLU
import common.battle.BasisSet
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
import common.util.stage.RandStage
import common.util.stage.Stage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JBTN
import page.JL
import page.JTG
import page.Page
import page.anim.AnimBox
import page.basis.BasisPage
import page.basis.LineUpBox
import page.basis.LubCont
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class BattleSetupPage(p: Page?, private val st: Stage, private vararg val conf: Int) : LubCont(p) {
    private val back: JBTN = JBTN(0, "back")
    private val strt: JBTN = JBTN(0, "start")
    private val tmax: JBTN = JBTN(0, "tomax")
    private val rich: JTG = JTG(0, "rich")
    private val snip: JTG = JTG(0, "sniper")
    private val jls: JList<String> = JList<String>()
    private val jsps: JScrollPane = JScrollPane(jls)
    private val jl = JLabel()
    private val jlu: JBTN = JBTN(0, "line")
    private val lub: LineUpBox = LineUpBox(this)
    protected override fun getLub(): LineUpBox {
        return lub
    }

    protected override fun renew() {
        val b: BasisSet = BasisSet.Companion.current()
        jl.text = b.toString() + "-" + b.sele
        if (st.lim != null && st.lim.lvr != null) strt.setEnabled(st.lim.lvr.isValid(b.sele.lu)) else tmax.setEnabled(false)
        lub.setLU(b.sele.lu)
    }

    protected override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jsps, x, y, 50, 100, 200, 200)
        Page.Companion.set(jl, x, y, 50, 350, 200, 50)
        Page.Companion.set(jlu, x, y, 50, 400, 200, 50)
        Page.Companion.set(strt, x, y, 50, 500, 200, 50)
        Page.Companion.set(rich, x, y, 300, 100, 200, 50)
        Page.Companion.set(snip, x, y, 300, 200, 200, 50)
        Page.Companion.set(tmax, x, y, 300, 500, 200, 50)
        Page.Companion.set(lub, x, y, 550, 50, 600, 300)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(getFront())
            }
        })
        jls.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.getValueIsAdjusting()) return
                if (jls.getSelectedIndex() == -1) jls.setSelectedIndex(0)
            }
        })
        jlu.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(BasisPage(getThis()))
            }
        })
        strt.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                var star: Int = jls.getSelectedIndex()
                val ints = IntArray(1)
                if (rich.isSelected()) ints[0] = ints[0] or 1
                if (snip.isSelected()) ints[0] = ints[0] or 2
                var b: BasisLU = BasisSet.Companion.current().sele
                if (conf.size == 1 && conf[0] == 0) {
                    b = RandStage.getLU(star)
                    star = 0
                }
                changePanel(BattleInfoPage(getThis(), st, star, b, ints))
            }
        })
        tmax.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                st.lim.lvr.validate(BasisSet.Companion.current().sele.lu)
                renew()
            }
        })
    }

    private fun ini() {
        add(back)
        add(jsps)
        add(jl)
        add(jlu)
        add(strt)
        add(rich)
        add(snip)
        add(tmax)
        add(lub)
        if (conf.size == 0) {
            val tit = arrayOfNulls<String>(st.map.stars.size)
            val star: String = Page.Companion.get(1, "star")
            for (i in st.map.stars.indices) tit[i] = (i + 1).toString() + star + ": " + st.map.stars[i] + "%"
            jls.setListData(tit)
        } else if (conf.size == 1 && conf[0] == 0) {
            val tit = arrayOfNulls<String>(5)
            val star: String = Page.Companion.get(1, "attempt")
            for (i in 0..4) tit[i] = star + (i + 1)
            jls.setListData(tit)
        }
        jls.setSelectedIndex(0)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
        resized()
    }
}
