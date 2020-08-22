package page.support

import common.util.unit.Form
import common.util.unit.Unit
import main.MainBCU
import utilpc.Theme
import utilpc.UtilPC
import java.awt.Component
import javax.swing.DefaultListCellRenderer
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.SwingConstants

class UnitLCR : DefaultListCellRenderer() {
    override fun getListCellRendererComponent(l: JList<*>?, o: Any, ind: Int, s: Boolean, f: Boolean): Component {
        val form: Form
        form = if (o is Unit) o.forms[0] else o as Form
        val jl = super.getListCellRendererComponent(l, o, ind, s, f) as JLabel
        jl.text = o.toString()
        jl.icon = null
        jl.horizontalTextPosition = SwingConstants.RIGHT
        val v = form.anim.edi ?: return jl
        jl.icon = UtilPC.getIcon(v)
        if (s && MainBCU.nimbus) {
            jl.background = if (MainBCU.light) Theme.LIGHT.NIMBUS_SELECT_BG else Theme.DARK.NIMBUS_SELECT_BG
        }
        return jl
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
