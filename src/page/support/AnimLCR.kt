package page.support

import common.system.VImg
import common.util.Animable
import common.util.anim.AnimU
import common.util.unit.AbEnemy
import main.MainBCU
import utilpc.Theme
import utilpc.UtilPC
import java.awt.Component
import javax.swing.DefaultListCellRenderer
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.SwingConstants

class AnimLCR : DefaultListCellRenderer() {
    override fun getListCellRendererComponent(l: JList<*>?, o: Any, ind: Int, s: Boolean, f: Boolean): Component {
        val jl = super.getListCellRendererComponent(l, o, ind, s, f) as JLabel
        jl.text = o.toString()
        jl.icon = null
        jl.horizontalTextPosition = SwingConstants.RIGHT
        if (s && MainBCU.nimbus) {
            jl.setBackground(if (MainBCU.light) Theme.LIGHT.NIMBUS_SELECT_BG else Theme.DARK.NIMBUS_SELECT_BG)
        }
        val v: VImg?
        v = if (o is Animable<*, *>) (o as Animable<out AnimU<*>?, *>).anim.getEdi() else if (o is AbEnemy) (o as AbEnemy).getIcon() else null
        if (v == null) return jl
        jl.icon = UtilPC.getIcon(v)
        return jl
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
