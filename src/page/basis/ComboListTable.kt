package page.basis

import common.battle.LineUp
import common.pack.UserProfile
import common.util.unit.Combo
import common.util.unit.Form
import page.MainLocale
import page.Page
import page.support.SortTable
import utilpc.Interpret
import utilpc.UtilPC
import java.awt.Component
import java.awt.Point
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer

class ComboListTable(private val fr: Page, line: LineUp) : SortTable<Combo?>() {
    companion object {
        private const val serialVersionUID = 1L
        private var tit: Array<String>
        fun redefine() {
            val str: String = MainLocale.Companion.getLoc(1, "unit")
            tit = arrayOf("Lv.", MainLocale.Companion.getLoc(1, "desc"), MainLocale.Companion.getLoc(1, "occu"), "$str 1", "$str 2",
                    "$str 3", "$str 4", "$str 5")
        }

        init {
            redefine()
        }
    }

    private val lu: LineUp
    fun clicked(p: Point) {
        if (list == null) return
        var c: Int = getColumnModel().getColumnIndexAtX(p.x)
        c = lnk.get(c)
        val r: Int = p.y / getRowHeight()
        if (r < 0 || r >= list.size || c < 3) return
        val f = get(list.get(r), c) as Form? ?: return
        fr.callBack(f.unit)
    }

    override fun getColumnClass(c: Int): Class<*> {
        var c = c
        c = lnk.get(c)
        if (c == 1) return Combo::class.java
        return if (c > 2) Form::class.java else String::class.java
    }

    protected override fun compare(e0: Combo, e1: Combo, c: Int): Int {
        if (c == 2) {
            val o0: Int = lu.occupance(e0)
            val o1: Int = lu.occupance(e1)
            return if (o0 > o1) 1 else if (o0 < o1) -1 else 0
        }
        return if (e0.lv > e1.lv) 1 else if (e0.lv < e1.lv) -1 else 0
    }

    protected override operator fun get(t: Combo, c: Int): Any? {
        if (c == 0) return t.lv
        if (c == 1) return t
        if (c == 2) return lu.occupance(t)
        return if (t.units.size > c - 3) UserProfile.Companion.getBCData().units.get(t.units.get(c - 3).get(0)).forms.get(t.units.get(c - 3).get(1)) else null
    }

    override fun getTit(): Array<String> {
        return tit
    }

    init {
        lu = line
        setDefaultRenderer(Combo::class.java, object : DefaultTableCellRenderer() {
            private static
            val serialVersionUID = 1L
            override fun getTableCellRendererComponent(l: JTable?, o: Any?, s: Boolean, f: Boolean, r: Int, c: Int): Component {
                val jl = super.getTableCellRendererComponent(l, c, s, f, r, c) as JLabel
                val com: Combo? = o as Combo?
                jl.text = Interpret.comboInfo(com)
                return jl
            }
        })
        setDefaultRenderer(Form::class.java, object : DefaultTableCellRenderer() {
            private static
            val serialVersionUID = 1L
            override fun getTableCellRendererComponent(l: JTable?, o: Any?, s: Boolean, f: Boolean, r: Int, c: Int): Component {
                val jl = super.getTableCellRendererComponent(l, c, s, f, r, c) as JLabel
                val form = o as Form?
                jl.text = ""
                if (form == null) {
                    jl.icon = null
                    return jl
                }
                jl.icon = UtilPC.getIcon(form.anim.uni)
                return jl
            }
        })
    }
}
