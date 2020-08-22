package page.basis

import common.util.unit.Combo
import utilpc.Interpret
import java.awt.Component
import javax.swing.DefaultListCellRenderer
import javax.swing.JLabel
import javax.swing.JList

internal class ComboList : JList<Combo?>() {
    protected fun setList(lf: List<Combo?>) {
        setListData(lf.toTypedArray())
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        setCellRenderer(object : DefaultListCellRenderer() {
            private static
            val serialVersionUID = 1L
            override fun getListCellRendererComponent(l: JList<*>?, o: Any?, ind: Int, s: Boolean, f: Boolean): Component {
                val c: Combo? = o as Combo?
                val jl = super.getListCellRendererComponent(l, o, ind, s, f) as JLabel
                jl.text = Interpret.comboInfo(c)
                return jl
            }
        })
    }
}
