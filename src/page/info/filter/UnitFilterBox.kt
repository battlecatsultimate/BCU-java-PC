package page.info.filter

import common.battle.data.MaskUnit
import common.pack.UserProfile
import common.util.lang.MultiLangCont
import common.util.unit.Form
import common.util.unit.Unit
import main.MainBCU
import page.JTG
import page.Page
import page.info.filter.AttList
import utilpc.Interpret
import utilpc.UtilPC
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.image.BufferedImage
import java.util.*
import javax.swing.*
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

abstract class UnitFilterBox protected constructor(p: Page?, protected var pac: String?) : Page(p) {
    var name: String? = ""
    abstract fun getSizer(): IntArray

    companion object {
        private const val serialVersionUID = 1L
        fun getNew(p: Page?, pack: String?): UnitFilterBox? {
            if (MainBCU.FILTER_TYPE == 0) return UFBButton(p, pack)
            return if (MainBCU.FILTER_TYPE == 1) UFBList(p, pack) else null
        }
    }
}

internal class UFBButton(p: Page?, pack: String?) : UnitFilterBox(p, pack) {
    private val orop: Array<JTG?> = arrayOfNulls<JTG>(3)
    private val rare: Array<JTG?> = arrayOfNulls<JTG>(Interpret.RARITY.size)
    private val trait: Array<JTG?> = arrayOfNulls<JTG>(9)
    private val abis: Array<JTG?> = arrayOfNulls<JTG>(Interpret.SABIS.size)
    private val proc: Array<JTG?> = arrayOfNulls<JTG>(Interpret.SPROC.size)
    private val atkt: Array<JTG?> = arrayOfNulls<JTG>(Interpret.ATKCONF.size)
    override fun callBack(o: Any?) {
        confirm()
    }

    override fun getSizer(): IntArray {
        return intArrayOf(2000, 400, 1, 400)
    }

    override fun resized(x: Int, y: Int) {
        val btns: Array<Array<JTG?>> = arrayOf<Array<JTG?>>(rare, trait, abis, proc, atkt)
        AttList.Companion.btnDealer(x, y, btns, orop, -1, 0, 1, -1, 2)
    }

    private fun confirm() {
        val ans: MutableList<Form> = ArrayList()
        for (u in UserProfile.Companion.getAll<Unit>(pac, Unit::class.java)) for (f in u.forms) {
            val du: MaskUnit = f.maxu()
            val t: Int = du.getType()
            val a: Int = du.getAbi()
            var b0: Boolean = rare[u.rarity].isSelected()
            var b1: Boolean = !orop[0].isSelected()
            for (i in trait.indices) if (trait[i].isSelected()) b1 = if (orop[0].isSelected()) b1 or (t shr i and 1 == 1) else b1 and (t shr i and 1 == 1)
            var b2: Boolean = !orop[1].isSelected()
            for (i in abis.indices) if (abis[i].isSelected()) {
                val bind = a shr i and 1 == 1
                b2 = if (orop[1].isSelected()) b2 or bind else b2 and bind
            }
            for (i in proc.indices) if (proc[i].isSelected()) b2 = if (orop[1].isSelected()) b2 or du.getAllProc().getArr(i).exists() else b2 and du.getAllProc().getArr(i).exists()
            var b3: Boolean = !orop[2].isSelected()
            for (i in atkt.indices) if (atkt[i].isSelected()) b3 = if (orop[2].isSelected()) b3 or Interpret.isType(du, i) else b3 and Interpret.isType(du, i)
            var b4 = true
            var fname: String = MultiLangCont.Companion.getStatic().FNAME.getCont(f)
            if (fname == null) fname = f.name
            if (fname == null) fname = ""
            if (name != null) b4 = fname.toLowerCase().contains(name!!.toLowerCase())
            b0 = nonSele(rare) or b0
            b1 = nonSele(trait) or b1
            b2 = nonSele(abis) and nonSele(proc) or b2
            b3 = nonSele(atkt) or b3
            if (b0 and b1 and b2 and b3 and b4) ans.add(f)
        }
        front.callBack(ans)
    }

    private fun ini() {
        for (i in orop.indices) set(JTG(0, "orop").also { orop[i] = it })
        for (i in rare.indices) set(JTG(Interpret.RARITY.get(i)).also { rare[i] = it })
        for (i in trait.indices) {
            set(JTG(Interpret.TRAIT.get(i)).also { trait[i] = it })
            val v: BufferedImage = UtilPC.getIcon(3, i) ?: continue
            trait[i].setIcon(ImageIcon(v))
        }
        for (i in abis.indices) {
            set(JTG(Interpret.SABIS.get(i)).also { abis[i] = it })
            val v: BufferedImage = UtilPC.getIcon(0, i) ?: continue
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

internal class UFBList(p: Page?, pack: String?) : UnitFilterBox(p, pack) {
    private val orop: Array<JTG?> = arrayOfNulls<JTG>(3)
    private val rare: JList<String> = JList<String>(Interpret.RARITY)
    private val vt = Vector<String>()
    private val va = Vector<String>()
    private val trait = AttList(3, 0)
    private val abis = AttList(0, 0)
    private val atkt = AttList(2, 0)
    private val jr: JScrollPane = JScrollPane(rare)
    private val jt: JScrollPane = JScrollPane(trait)
    private val jab: JScrollPane = JScrollPane(abis)
    private val jat: JScrollPane = JScrollPane(atkt)
    override fun callBack(o: Any?) {
        confirm()
    }

    override fun getSizer(): IntArray {
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
        val ans: MutableList<Form> = ArrayList()
        for (u in UserProfile.Companion.getAll<Unit>(pac, Unit::class.java)) for (f in u.forms) {
            val du: MaskUnit = f.maxu()
            val t: Int = du.getType()
            val a: Int = du.getAbi()
            var b0: Boolean = rare.isSelectedIndex(u.rarity)
            var b1: Boolean = !orop[0].isSelected()
            for (i in trait.getSelectedIndices()) b1 = if (orop[0].isSelected()) b1 or (t shr i and 1 == 1) else b1 and (t shr i and 1 == 1)
            var b2: Boolean = !orop[1].isSelected()
            val len: Int = Interpret.SABIS.size
            for (i in abis.getSelectedIndices()) b2 = if (i < len) {
                val bind = a shr i and 1 == 1
                if (orop[1].isSelected()) b2 or bind else b2 and bind
            } else if (orop[1].isSelected()) b2 or du.getAllProc().getArr(i - len).exists() else b2 and du.getAllProc().getArr(i - len).exists()
            var b3: Boolean = !orop[2].isSelected()
            for (i in atkt.getSelectedIndices()) b3 = if (orop[2].isSelected()) b3 or Interpret.isType(du, i) else b3 and Interpret.isType(du, i)
            var b4 = true
            var fname: String = MultiLangCont.Companion.getStatic().FNAME.getCont(f)
            if (fname == null) fname = f.name
            if (fname == null) fname = ""
            if (name != null) b4 = fname.toLowerCase().contains(name!!.toLowerCase())
            b0 = rare.getSelectedIndex() == -1 or b0
            b1 = trait.getSelectedIndex() == -1 or b1
            b2 = abis.getSelectedIndex() == -1 or b2
            b3 = atkt.getSelectedIndex() == -1 or b3
            if (b0 and b1 and b2 and b3 and b4) ans.add(f)
        }
        front.callBack(ans)
    }

    private fun ini() {
        for (i in orop.indices) set(JTG(Page.Companion.get(0, "orop")).also { orop[i] = it })
        for (i in 0..8) vt.add(Interpret.TRAIT.get(i))
        for (i in Interpret.SABIS.indices) va.add(Interpret.SABIS.get(i))
        for (i in Interpret.SPROC.indices) va.add(Interpret.SPROC.get(i))
        trait.setListData(vt)
        abis.setListData(va)
        atkt.setListData(Interpret.ATKCONF)
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
        val m: Int = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        jl.setSelectionMode(m)
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
