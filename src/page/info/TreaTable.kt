package page.info

import common.CommonStatic
import common.battle.BasisSet
import common.system.Node
import main.MainBCU
import page.JTF
import page.JTG
import page.Page
import utilpc.Interpret
import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.util.*
import javax.swing.BorderFactory
import javax.swing.JLabel

class TreaTable(p: Page?, bas: BasisSet) : Page(p) {
    private val jln = arrayOfNulls<JLabel>(Interpret.TREA.size)
    private val jtf: Array<JTF?> = arrayOfNulls<JTF>(Interpret.TREA.size)
    private val jlb: Array<JTG?> = arrayOfNulls<JTG>(Interpret.TCOLP.size)
    private val jcf: Array<JTF?> = arrayOfNulls<JTF>(Interpret.TCOLP.size)
    private val lncs: MutableList<Node<Int>> = ArrayList()
    private var nc: Node<Int>? = null
    private var cur: Node<Int>? = null
    private var colp: Node<Int>? = null
    private var b: BasisSet
    override fun hasFocus(): Boolean {
        if (super.hasFocus()) return true
        for (c in jcf) if (c.hasFocus()) return true
        for (c in jtf) if (c.hasFocus()) return true
        return false
    }

    fun setBasis(bas: BasisSet) {
        b = bas
        reset()
    }

    override fun resized(x: Int, y: Int) {
        var i = 0
        var n = nc
        while (n != null) {
            if (n.`val` > 0) {
                val ind = n.`val` - 1
                Page.Companion.set(jln[ind], x, y, 0, 50 * i, 200, 50)
                Page.Companion.set(jtf[ind], x, y, 200, 50 * i, 200, 50)
            } else {
                val ind = -n.`val` - 1
                Page.Companion.set(jlb[ind], x, y, 0, 50 * i, 200, 50)
                Page.Companion.set(jcf[ind], x, y, 200, 50 * i, 200, 50)
            }
            i++
            n = n.next
        }
    }

    private fun close(n: Node<Int>) {
        n.removes()
        val l = n.sides()
        for (s in l) {
            remove(jln[s - 1])
            remove(jtf[s - 1])
        }
    }

    private fun expand(n: Node<Int>) {
        val l = n.sides()
        for (s in l) {
            add(jln[s - 1])
            add(jtf[s - 1])
        }
        n.adds()
    }

    private fun ini() {
        var j = 0
        var k = 0
        for (i in Interpret.TREA.indices) {
            if (j < Interpret.TCOLP.size && i == Interpret.TCOLP.get(j).get(0)) {
                k = Interpret.TCOLP.get(j).get(1)
                add(JTG(Interpret.TCTX.get(j)).also { jlb[j] = it })
                add(JTF().also { jcf[j] = it })
                lncs.add(Node(-j - 1).also { colp = it })
                if (nc == null) {
                    nc = colp
                    cur = nc
                } else cur = cur!!.add(colp)
                val J = j
                j++
                jlb[J].addActionListener(object : ActionListener {
                    override fun actionPerformed(arg0: ActionEvent?) {
                        val n = lncs[J]
                        if (jlb[J].isSelected()) {
                            for (s in Interpret.TCTX.indices) if (s != J && jlb[s].isSelected()) jlb[s].doClick()
                            expand(n)
                        } else close(n)
                    }
                })
                jcf[J].addFocusListener(object : FocusAdapter() {
                    override fun focusLost(e: FocusEvent?) {
                        val ans: Int = Interpret.getComp(J, b.t())
                        val `val`: Int = Math.abs(CommonStatic.parseIntN(jcf[J].getText()))
                        Interpret.setComp(J, if (`val` == -1) ans else `val`, b)
                        reset()
                        front.callBack(null)
                    }
                })
            }
            val ind: Int = Interpret.TIND.get(i)
            val I: Int = i
            jln[i] = JLabel(Interpret.TREA.get(ind))
            jtf[i] = JTF(tos(Interpret.getValue(ind, b.t()), i))
            val temp = Node<Int>(i + 1)
            if (nc == null) {
                nc = temp
                cur = nc
            } else cur = cur!!.add(temp)
            if (k <= 0) {
                add(jln[i])
                add(jtf[i])
            } else jln[i]!!.foreground = if (MainBCU.light) Color.BLUE else Color(84, 110, 122)
            if (--k == 0) cur = colp!!.side(cur)
            jln[i]!!.border = BorderFactory.createEtchedBorder()
            jtf[i].addFocusListener(object : FocusAdapter() {
                override fun focusLost(e: FocusEvent?) {
                    val ans: Int = Interpret.getValue(ind, b.t())
                    val `val`: Int = Math.abs(CommonStatic.parseIntN(jtf[I].getText()))
                    Interpret.setValue(ind, if (`val` == -1) ans else `val`, b)
                    reset()
                    front.callBack(null)
                }
            })
        }
    }

    private fun reset() {
        for (i in Interpret.TREA.indices) jtf[i].setText(tos(Interpret.getValue(Interpret.TIND.get(i), b.t()), i + 1))
        for (i in Interpret.TCTX.indices) jcf[i].setText(tos(Interpret.getComp(i, b.t()), -i - 1))
    }

    companion object {
        private const val serialVersionUID = 1L
        fun tos(v: Int, ind: Int): String {
            if (v == -1) return "---"
            if (ind > 0 && ind < 9 || ind == -1) return "Lv.$v"
            return if (ind > 29 || ind == -6) "Lv.$v" else "$v%"
        }
    }

    init {
        b = bas
        ini()
        reset()
    }
}
