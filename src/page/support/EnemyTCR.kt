package page.support

import common.system.VImg
import common.util.unit.AbEnemy
import utilpc.UtilPC
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.SwingConstants
import javax.swing.table.DefaultTableCellRenderer

class EnemyTCR : DefaultTableCellRenderer() {
    override fun getTableCellRendererComponent(t: JTable, v: Any, s: Boolean, f: Boolean, r: Int, c: Int): Component {
        val comp: Component = super.getTableCellRendererComponent(t, v, s, f, r, c)
        if (v != null && v !is AbEnemy) return comp
        val jl = comp as JLabel
        val e: AbEnemy = v as AbEnemy
        jl.horizontalTextPosition = SwingConstants.RIGHT
        jl.icon = null
        if (e == null) {
            jl.text = ""
            return jl
        }
        jl.text = e.toString()
        val vimg: VImg = e.getIcon()
        jl.icon = UtilPC.getIcon(vimg)
        return jl
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
