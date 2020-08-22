package page.info.filter

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
import common.pack.UserProfile
import common.util.lang.MultiLangCont
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.Enemy
import common.util.unit.UnitLevel
import io.BCPlayer
import main.MainBCU
import page.JL
import page.JTG
import page.Page
import page.anim.AnimBox
import page.info.filter.AttList
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import utilpc.Interpret
import utilpc.UtilPC
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.image.BufferedImage
import java.util.*
import javax.swing.*
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

abstract class EnemyFilterBox protected constructor(p: Page?, protected var pac: String?) : Page(p) {
    var name: String? = ""
    abstract fun getSizer(): IntArray

    companion object {
        private const val serialVersionUID = 1L
        fun getNew(p: Page?, pack: String?): EnemyFilterBox? {
            if (MainBCU.FILTER_TYPE == 0) return EFBButton(p, pack)
            return if (MainBCU.FILTER_TYPE == 1) EFBList(p, pack) else null
        }
    }
}

internal class EFBButton(p: Page?, pack: String?) : EnemyFilterBox(p, pack) {
    private val orop: Array<JTG?> = arrayOfNulls<JTG>(3)
    private val rare: Array<JTG?> = arrayOfNulls<JTG>(Interpret.ERARE.size)
    private val trait: Array<JTG?> = arrayOfNulls<JTG>(Interpret.TRAIT.size)
    private val abis: Array<JTG?> = arrayOfNulls<JTG>(Interpret.EFILTER)
    private val proc: Array<JTG?> = arrayOfNulls<JTG>(Interpret.SPROC.size)
    private val atkt: Array<JTG?> = arrayOfNulls<JTG>(Interpret.ATKCONF.size)
    override fun callBack(o: Any?) {
        confirm()
    }

    protected override fun getSizer(): IntArray {
        return intArrayOf(2000, 400, 1, 400)
    }

    override fun resized(x: Int, y: Int) {
        val btns: Array<Array<JTG?>> = arrayOf<Array<JTG?>>(rare, trait, abis, proc, atkt)
        AttList.Companion.btnDealer(x, y, btns, orop, -1, 0, 1, -1, 2)
    }

    private fun confirm() {
        val ans: MutableList<Enemy> = ArrayList<Enemy>()
        for (e in UserProfile.Companion.getAll<Enemy>(pac, Enemy::class.java)) {
            val t: Int = e.de.getType()
            val a: Int = e.de.getAbi()
            var b0 = false
            for (i in rare.indices) if (rare[i].isSelected()) b0 = b0 or Interpret.isER(e, i)
            var b1: Boolean = !orop[0].isSelected()
            for (i in trait.indices) if (trait[i].isSelected()) b1 = if (orop[0].isSelected()) b1 or (t shr i and 1 == 1) else b1 and (t shr i and 1 == 1)
            var b2: Boolean = !orop[1].isSelected()
            for (i in abis.indices) if (abis[i].isSelected()) {
                val bind = a shr Interpret.EABIIND.get(i) and 1 == 1
                b2 = if (orop[1].isSelected()) b2 or bind else b2 and bind
            }
            for (i in proc.indices) if (proc[i].isSelected()) b2 = if (orop[1].isSelected()) b2 or e.de.getAllProc().getArr(i).exists() else b2 and e.de.getAllProc().getArr(i).exists()
            var b3: Boolean = !orop[2].isSelected()
            for (i in atkt.indices) if (atkt[i].isSelected()) b3 = if (orop[2].isSelected()) b3 or Interpret.isType(e.de, i) else b3 and Interpret.isType(e.de, i)
            var b4 = true
            var ename: String
            ename = MultiLangCont.Companion.getStatic().ENAME.getCont(e)
            if (ename == null) ename = e.name
            if (ename == null) ename = ""
            if (name != null) {
                b4 = ename.toLowerCase().contains(name!!.toLowerCase())
            }
            b0 = nonSele(rare) or b0
            b1 = nonSele(trait) or b1
            b2 = nonSele(abis) and nonSele(proc) or b2
            b3 = nonSele(atkt) or b3
            if (b0 and b1 and b2 and b3 and b4) ans.add(e)
        }
        front.callBack(ans)
    }

    private fun ini() {
        for (i in orop.indices) set(JTG(0, "orop").also { orop[i] = it })
        for (i in rare.indices) set(JTG(Interpret.ERARE.get(i)).also { rare[i] = it })
        for (i in trait.indices) {
            set(JTG(Interpret.TRAIT.get(i)).also { trait[i] = it })
            val v: BufferedImage = UtilPC.getIcon(3, i) ?: continue
            trait[i].setIcon(ImageIcon(v))
        }
        for (i in abis.indices) {
            set(JTG(Interpret.EABI.get(i)).also { abis[i] = it })
            val v: BufferedImage = UtilPC.getIcon(0, Interpret.EABIIND.get(i)) ?: continue
            abis[i].setIcon(ImageIcon(v))
        }
        for (i in proc.indices) {
            set(JTG(Interpret.SPROC.get(i)).also { proc[i] = it })
            val v: BufferedImage = UtilPC.getIcon(1, i) ?: continue
            proc[i].setIcon(ImageIcon(v))
        }
        for (i in atkt.indices) {
            set(JTG(Interpret.ATKCONF.get(i)).also { atkt[i] = it })
            val v: BufferedImage = UtilPC.getIcon(2, i) ?: continue
            atkt[i].setIcon(ImageIcon(v))
        }
    }

    private fun nonSele(jtbs: Array<JTG?>): Boolean {
        var n = 0
        for (i in jtbs.indices) if (jtbs[i].isSelected()) n++
        return n == 0
    }

    private fun set(b: AbstractButton) {
        add(b)
        b.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                confirm()
            }
        })
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
        confirm()
    }
}

internal class EFBList(p: Page?, pack: String?) : EnemyFilterBox(p, pack) {
    private val orop: Array<JTG?> = arrayOfNulls<JTG>(3)
    private val rare: JList<String> = JList<String>(Interpret.ERARE)
    private val vt = Vector<String>()
    private val va = Vector<String>()
    private val trait = AttList(3, 0)
    private val abis = AttList(-1, Interpret.EFILTER)
    private val atkt = AttList(2, 0)
    private val jr: JScrollPane = JScrollPane(rare)
    private val jt: JScrollPane = JScrollPane(trait)
    private val jab: JScrollPane = JScrollPane(abis)
    private val jat: JScrollPane = JScrollPane(atkt)
    override fun callBack(o: Any?) {
        confirm()
    }

    protected override fun getSizer(): IntArray {
        return intArrayOf(450, 1150, 0, 500)
    }

    override fun resized(x: Int, y: Int) {
        Page.Companion.set(orop[0], x, y, 0, 350, 200, 50)
        Page.Companion.set(orop[1], x, y, 250, 0, 200, 50)
        Page.Companion.set(orop[2], x, y, 0, 800, 200, 50)
        Page.Companion.set(jr, x, y, 0, 50, 200, 250)
        Page.Companion.set(jt, x, y, 0, 400, 200, 350)
        Page.Companion.set(jab, x, y, 250, 50, 200, 1100)
        Page.Companion.set(jat, x, y, 0, 850, 200, 300)
    }

    private fun confirm() {
        val ans: MutableList<Enemy> = ArrayList<Enemy>()
        for (e in UserProfile.Companion.getAll<Enemy>(pac, Enemy::class.java)) {
            val t: Int = e.de.getType()
            val a: Int = e.de.getAbi()
            var b0: Boolean = Interpret.isER(e, rare.getSelectedIndex())
            var b1: Boolean = !orop[0].isSelected()
            for (i in trait.getSelectedIndices()) b1 = if (orop[0].isSelected()) b1 or (t shr i and 1 == 1) else b1 and (t shr i and 1 == 1)
            var b2: Boolean = !orop[1].isSelected()
            val len: Int = Interpret.EFILTER
            for (i in abis.getSelectedIndices()) b2 = if (i < len) {
                val bind = a shr Interpret.EABIIND.get(i) and 1 == 1
                if (orop[1].isSelected()) b2 or bind else b2 and bind
            } else if (orop[1].isSelected()) b2 or e.de.getAllProc().getArr(i - len).exists() else b2 and e.de.getAllProc().getArr(i - len).exists()
            var b3: Boolean = !orop[2].isSelected()
            for (i in atkt.getSelectedIndices()) b3 = if (orop[2].isSelected()) b3 or Interpret.isType(e.de, i) else b3 and Interpret.isType(e.de, i)
            var b4 = true
            var ename: String
            ename = MultiLangCont.Companion.getStatic().ENAME.getCont(e)
            if (ename == null) ename = e.name
            if (ename == null) ename = ""
            if (name != null) {
                b4 = ename.toLowerCase().contains(name!!.toLowerCase())
            }
            b0 = rare.getSelectedIndex() == -1 or b0
            b1 = trait.getSelectedIndex() == -1 or b1
            b2 = abis.getSelectedIndex() == -1 or b2
            b3 = atkt.getSelectedIndex() == -1 or b3
            if (b0 and b1 and b2 and b3 and b4) ans.add(e)
        }
        front.callBack(ans)
    }

    private fun ini() {
        for (i in orop.indices) set(JTG(Page.Companion.get(0, "orop")).also { orop[i] = it })
        for (i in Interpret.TRAIT.indices) vt.add(Interpret.TRAIT.get(i))
        for (i in 0 until Interpret.EFILTER) va.add(Interpret.EABI.get(i))
        for (i in Interpret.SPROC.indices) va.add(Interpret.SPROC.get(i))
        trait.setListData(vt)
        abis.setListData(va)
        atkt.setListData(Interpret.ATKCONF)
        rare.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
        val m: Int = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        trait.setSelectionMode(m)
        abis.setSelectionMode(m)
        atkt.setSelectionMode(m)
        set(rare)
        set(trait)
        set(abis)
        set(atkt)
        add(jr)
        add(jt)
        add(jab)
        add(jat)
    }

    private fun set(b: AbstractButton) {
        add(b)
        b.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                confirm()
            }
        })
    }

    private fun set(jl: JList<*>) {
        jl.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                confirm()
            }
        })
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
        confirm()
    }
}
