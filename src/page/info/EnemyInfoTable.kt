package page.info

import common.CommonStatic
import common.battle.BasisSet
import common.util.unit.Enemy
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

class EnemyInfoTable(p: Page?, de: Enemy, mul: Int, mula: Int) : Page(p) {
    private val main: Array<Array<JL?>> = Array<Array<JL?>>(4) { arrayOfNulls<JL>(8) }
    private val atks: Array<Array<JL?>>
    private val abis: Array<JLabel?>
    private val proc: Array<JLabel?>
    private val jtf: JTF = JTF()
    private val b: BasisSet
    private val e: Enemy
    private var multi: Int
    private var mulatk: Int
    fun reset() {
        val mul: Double = multi * e.de.multi(b) / 100
        val mula: Double = mulatk * e.de.multi(b) / 100
        main[1][3].setText("" + (e.de.getHp() * mul) as Int)
        main[2][3].setText("" + (e.de.allAtk() * mula * 30 / e.de.getItv()) as Int)
        val atkData: Array<IntArray> = e.de.rawAtkData()
        for (i in atks.indices) atks[i][1].setText("" + (atkData[i][0] * mula).toInt())
    }

    override fun resized(x: Int, y: Int) {
        for (i in 0..3) for (j in 0..7) if (i * j != 1 && (i != 0 || j < 4)) Page.Companion.set(main[i][j], x, y, 200 * j, 50 * i, 200, 50)
        Page.Companion.set(jtf, x, y, 200, 50, 200, 50)
        Page.Companion.set(main[0][4], x, y, 800, 0, 800, 50)
        var h = 200
        for (i in atks.indices) for (j in 0..7) Page.Companion.set(atks[i][j], x, y, 200 * j, h + 50 * i, 200, 50)
        h += atks.size * 50
        for (i in abis.indices) Page.Companion.set(abis[i], x, y, 0, h + 50 * i, 1200, 50)
        h += abis.size * 50
        for (i in proc.indices) Page.Companion.set(proc[i], x, y, 0, h + 50 * i, 1200, 50)
    }

    private fun addListeners() {
        jtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(e: FocusEvent?) {
                val data: IntArray = CommonStatic.parseIntsN(jtf.getText().trim { it <= ' ' }.replace("%", ""))
                if (data.size == 1) {
                    if (data[0] != -1) {
                        mulatk = data[0]
                        multi = mulatk
                    }
                    jtf.setText(CommonStatic.toArrayFormat(multi, mulatk) + "%")
                } else if (data.size == 2) {
                    if (data[0] != -1) {
                        multi = data[0]
                    }
                    if (data[1] != -1) {
                        mulatk = data[1]
                    }
                    jtf.setText(CommonStatic.toArrayFormat(multi, mulatk) + "%")
                } else {
                    jtf.setText(CommonStatic.toArrayFormat(multi, mulatk) + "%")
                }
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
        for (i in atks.indices) for (j in 0..7) {
            add(JL().also { atks[i][j] = it })
            atks[i][j].setBorder(BorderFactory.createEtchedBorder())
            if (j % 2 == 0) atks[i][j].setHorizontalAlignment(SwingConstants.CENTER)
        }
        add(jtf)
        jtf.setText(CommonStatic.toArrayFormat(multi, mulatk) + "%")
        var itv: Int = e.de.getItv()
        main[0][0].setText("ID")
        main[0][1].setText("" + e.id)
        if (e.anim.getEdi() != null && e.anim.getEdi().getImg() != null) main[0][2].setIcon(UtilPC.getIcon(e.anim.getEdi()))
        main[0][3].setText(1, "trait")
        main[0][4].setText(Interpret.getTrait(e.de.getType(), e.de.getStar()))
        main[1][0].setText(1, "mult")
        main[1][2].setText("HP")
        main[1][4].setText("HB")
        main[1][5].setText("" + e.de.getHb())
        main[1][6].setText(1, "drop")
        main[1][7].setText("" + (e.de.getDrop() * b.t().getDropMulti()) as Int)
        main[2][0].setText(1, "range")
        main[2][1].setText("" + e.de.getRange())
        main[2][2].setText("dps")
        main[2][4].setText(1, "speed")
        main[2][5].setText("" + e.de.getSpeed())
        main[2][6].setText(1, "atkf")
        main[2][7].setText(itv.toString() + "f")
        main[3][0].setText(1, "isr")
        main[3][1].setText("" + e.de.isRange())
        main[3][2].setText(1, "shield")
        main[3][3].setText("" + e.de.getShield())
        main[3][4].setText(1, "TBA")
        main[3][5].setText(e.de.getTBA().toString() + "f")
        main[3][6].setText(1, "postaa")
        val atkData: Array<IntArray> = e.de.rawAtkData()
        for (i in atks.indices) {
            atks[i][0].setText("atk")
            atks[i][2].setText(1, "preaa")
            atks[i][3].setText(atkData[i][1].toString() + "f")
            atks[i][4].setText(1, "use")
            atks[i][5].setText("" + (atkData[i][2] == 1))
            itv -= atkData[i][1]
        }
        main[3][7].setText(itv - e.de.getTBA().toString() + "f")
        reset()
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        b = BasisSet.Companion.current()
        e = de
        multi = mul
        mulatk = mula
        atks = Array<Array<JL?>>(e.de.rawAtkData().size) { arrayOfNulls<JL>(8) }
        var ls: List<String?> = Interpret.getAbi(e.de)
        abis = arrayOfNulls(ls.size)
        for (i in ls.indices) {
            add(JLabel(ls[i]).also { abis[i] = it })
            abis[i]!!.border = BorderFactory.createEtchedBorder()
        }
        ls = Interpret.getProc(e.de)
        proc = arrayOfNulls(ls.size)
        for (i in ls.indices) {
            add(JLabel(ls[i]).also { proc[i] = it })
            proc[i]!!.border = BorderFactory.createEtchedBorder()
        }
        ini()
    }
}
