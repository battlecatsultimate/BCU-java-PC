package page.support

import common.util.unit.Form
import utilpc.UtilPC
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.SwingConstants
import javax.swing.table.DefaultTableCellRenderer

class UnitTCR(private val lnk: IntArray?) : DefaultTableCellRenderer() {
    override fun getTableCellRendererComponent(t: JTable, v: Any, s: Boolean, f: Boolean, r: Int, c: Int): Component {
        var c = c
        val comp: Component = super.getTableCellRendererComponent(t, v, s, f, r, c)
        if (lnk != null) c = lnk[c]
        if (c != 1) return comp
        val jl = comp as JLabel
        val e = v as Form
        jl.text = e.toString()
        jl.icon = null
        jl.horizontalTextPosition = SwingConstants.RIGHT
        val vimg = e.anim.edi ?: return jl
        jl.icon = UtilPC.getIcon(vimg)
        return jl
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
