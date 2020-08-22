package page.info

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
import common.system.Node
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.Unit
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JBTN
import page.JL
import page.Page
import page.anim.AnimBox
import page.basis.BasisPage
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.UnitViewPage
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JPanel
import javax.swing.JScrollPane

class UnitInfoPage private constructor(p: Page, private val n: Node<Unit>, bas: BasisSet) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val anim: JBTN = JBTN(0, "anim")
    private val prev: JBTN = JBTN(0, "prev")
    private val next: JBTN = JBTN(0, "next")
    private val find: JBTN = JBTN(0, "combo")
    private val cont: JPanel = JPanel()
    private val jsp: JScrollPane = JScrollPane(cont)
    private val info: Array<UnitInfoTable?>
    private val trea: TreaTable
    private val b: BasisSet

    constructor(p: Page, de: Node<Unit>) : this(p, de, BasisSet.Companion.current()) {}

    override fun callBack(newParam: Any?) {
        for (uit in info) uit!!.reset()
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(prev, x, y, 300, 0, 200, 50)
        Page.Companion.set(anim, x, y, 600, 0, 200, 50)
        Page.Companion.set(next, x, y, 900, 0, 200, 50)
        Page.Companion.set(find, x, y, 1200, 0, 200, 50)
        var h = 0
        Page.Companion.set(jsp, x, y, 50, 100, 1650, 1150)
        for (i in info.indices) {
            val ih: Int = info[i]!!.getH()
            Page.Companion.set(info[i], x, y, 0, h, 1600, ih)
            info[i]!!.resized()
            h += ih + 50
        }
        cont.setPreferredSize(Page.Companion.size(x, y, 1600, h - 50).toDimension())
        Page.Companion.set(trea, x, y, 1750, 100, 400, 1200)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        prev.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(UnitInfoPage(front, n.prev, b))
            }
        })
        anim.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (front is UnitViewPage) changePanel(front) else changePanel(UnitViewPage(getThis(), n.`val`))
            }
        })
        next.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(UnitInfoPage(front, n.next, b))
            }
        })
        find.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (front is BasisPage) {
                    changePanel(front)
                    front.callBack(n.`val`)
                    return
                }
                var p: Page
                changePanel(BasisPage(getThis()).also { p = it })
                p.callBack(n.`val`)
            }
        })
    }

    private fun ini() {
        add(back)
        add(prev)
        add(anim)
        add(next)
        add(find)
        for (i in info.indices) cont.add(info[i])
        cont.setLayout(null)
        add(jsp)
        add(trea)
        prev.setEnabled(n.prev != null)
        next.setEnabled(n.next != null)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        b = bas
        info = arrayOfNulls<UnitInfoTable>(n.`val`.forms.size)
        for (i in info.indices) info[i] = UnitInfoTable(this, n.`val`.forms[i])
        trea = TreaTable(this, b)
        ini()
        resized()
    }
}
