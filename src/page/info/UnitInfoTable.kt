package page.info

import common.CommonStatic
import common.battle.BasisSet
import common.util.Data
import common.util.unit.EForm
import common.util.unit.Form
import page.JL
import page.JTF
import page.Page
import utilpc.Interpret
import utilpc.UtilPC
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.SwingConstants

class UnitInfoTable(p: Page?, de: Form) : Page(p) {
    private val main: Array<Array<JL?>> = Array<Array<JL?>>(4) { arrayOfNulls<JL>(8) }
    private val atks: Array<JL?>
    private val proc: Array<JLabel?>
    private val jtf: JTF = JTF()
    private var pcoin: JLabel? = null
    private val b: BasisSet
    private val f: Form
    private var multi: IntArray
    fun getH(): Int {
        return (5 + (proc.size + 1) / 2) * 50
    }

    fun reset() {
        val ef = EForm(f, *multi)
        val mul = f.unit.lv.getMult(multi[0])
        val atk: Double = b.t().getAtkMulti()
        val def: Double = b.t().getDefMulti()
        main[1][3].setText((ef.du.getHp() * mul * def) as Int.toString() + " / " + ef.du.getHb())
        main[2][3].setText("" + (ef.du.allAtk() * mul * atk * 30 / ef.du.getItv()) as Int)
        main[2][5].setText("" + (ef.du.getSpeed() * (1 + b.getInc(Data.Companion.C_SPE) * 0.01)) as Int)
        main[1][5].setText(b.t().getFinRes(ef.du.getRespawn()).toString() + "f")
        main[1][7].setText("" + ef.getPrice(1))
        main[0][4].setText(Interpret.getTrait(ef.du.getType(), 0))
        val atkData: Array<IntArray> = ef.du.rawAtkData()
        var satk = ""
        for (i in atkData.indices) {
            if (satk.length > 0) satk += " / "
            satk += (atkData[i][0] * mul * b.t().getAtkMulti()) as Int
        }
        atks[1].setText(satk)
        val ls: MutableList<String> = Interpret.getAbi(ef.du)
        ls.addAll(Interpret.getProc(ef.du, b.t(), ef.du.getType()))
        for (l in proc) if (l !== pcoin) l!!.text = ""
        for (i in ls.indices) proc[i]!!.text = ls[i]
    }

    override fun resized(x: Int, y: Int) {
        for (i in 0..3) for (j in 0..7) if ((i != 0 || j < 4) && (i != 1 || j > 1)) Page.Companion.set(main[i][j], x, y, 200 * j, 50 * i, 200, 50)
        Page.Companion.set(jtf, x, y, 100, 50, 300, 50)
        Page.Companion.set(main[1][0], x, y, 0, 50, 100, 50)
        Page.Companion.set(main[0][4], x, y, 800, 0, 800, 50)
        Page.Companion.set(atks[0], x, y, 0, 200, 200, 50)
        Page.Companion.set(atks[1], x, y, 200, 200, 400, 50)
        Page.Companion.set(atks[2], x, y, 600, 200, 200, 50)
        Page.Companion.set(atks[3], x, y, 800, 200, 400, 50)
        Page.Companion.set(atks[4], x, y, 1200, 200, 200, 50)
        Page.Companion.set(atks[5], x, y, 1400, 200, 200, 50)
        val h = 250
        for (i in proc.indices) Page.Companion.set(proc[i], x, y, i % 2 * 800, h + 50 * (i / 2), 800, 50)
    }

    private fun addListeners() {
        jtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(e: FocusEvent?) {
                multi = f.regulateLv(CommonStatic.parseIntsN(jtf.getText()), multi)
                val strs: Array<String> = UtilPC.lvText(f, multi)
                jtf.setText(strs[0])
                if (pcoin != null) pcoin.setText(strs[1])
                reset()
            }
        })
    }

    private fun ini() {
        for (i in 0..3) for (j in 0..7) if (i * j != 1 && (i != 0 || j < 5)) {
            add(JL().also { main[i][j] = it })
            main[i][j].setBorder(BorderFactory.createEtchedBorder())
            if (i != 0 && j % 2 == 0 || i == 0 && j < 4) main[i][j].setHorizontalAlignment(SwingConstants.CENTER)
        }
        for (i in 0..5) {
            add(JL().also { atks[i] = it })
            atks[i].setBorder(BorderFactory.createEtchedBorder())
            if (i % 2 == 0) atks[i].setHorizontalAlignment(SwingConstants.CENTER)
        }
        add(jtf)
        val strs: Array<String> = UtilPC.lvText(f, multi)
        jtf.setText(strs[0])
        if (pcoin != null) {
            add(pcoin)
            pcoin.setText(strs[1])
        }
        main[0][0].setText("ID")
        main[0][1].setText(f.uid.toString() + "-" + f.fid)
        if (f.anim.edi != null && f.anim.edi.img != null) main[0][2].setIcon(UtilPC.getIcon(f.anim.edi))
        main[0][3].setText(1, "trait")
        main[1][0].setText(Interpret.RARITY.get(f.unit.rarity))
        main[1][2].setText("HP / HB")
        main[1][4].setText("CD")
        main[1][6].setText(1, "price")
        main[2][0].setText(1, "range")
        main[2][1].setText("" + f.du.range)
        main[2][2].setText("dps")
        main[2][4].setText(1, "speed")
        main[2][6].setText(1, "atkf")
        main[2][7].setText(f.du.itv.toString() + "f")
        main[3][0].setText(1, "isr")
        main[3][1].setText("" + f.du.isRange)
        main[3][2].setText(1, "shield")
        main[3][3].setText("" + f.du.shield)
        main[3][4].setText(1, "TBA")
        main[3][5].setText(f.du.tba.toString() + "f")
        main[3][6].setText(1, "postaa")
        main[3][7].setText(f.du.post.toString() + "f")
        atks[0].setText("atk")
        atks[2].setText(1, "preaa")
        atks[4].setText(1, "use")
        val atkData = f.du.rawAtkData()
        var pre = ""
        var use = ""
        for (i in atkData.indices) {
            if (pre.length > 0) pre += " / "
            if (use.length > 0) use += " / "
            pre += atkData[i][1].toString() + "f"
            use += atkData[i][2] == 1
        }
        atks[3].setText(pre)
        atks[5].setText(use)
        reset()
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        b = BasisSet.Companion.current()
        f = de
        multi = de.unit.prefLvs
        atks = arrayOfNulls<JL>(6)
        val du: MaskUnit = f.maxu()
        val ls: MutableList<String> = Interpret.getAbi(du)
        ls.addAll(Interpret.getProc(du, b.t(), du.getType()))
        val pc = de.pCoin != null
        if (pc) ls.add("")
        proc = arrayOfNulls(ls.size)
        for (i in ls.indices) {
            add(JLabel(ls[i]).also { proc[i] = it })
            proc[i]!!.border = BorderFactory.createEtchedBorder()
        }
        pcoin = if (pc) proc[ls.size - 1] else null
        ini()
    }
}
