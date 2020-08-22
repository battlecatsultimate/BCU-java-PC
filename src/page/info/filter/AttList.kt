package page.info.filter

import main.MainBCU
import page.JTG
import page.Page
import utilpc.Interpret
import utilpc.Theme
import utilpc.UtilPC
import java.awt.Component
import java.awt.image.BufferedImage
import javax.swing.DefaultListCellRenderer
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JList

internal class AttList(type: Int, para: Int) : JList<String?>() {
    companion object {
        private const val serialVersionUID = 1L
        fun btnDealer(x: Int, y: Int, btns: Array<Array<JTG>>, orop: Array<JTG?>, vararg ord: Int) {
            var h = 0
            var or = 0
            for (sub in btns) {
                var w = 0
                var j = 0
                if (ord[or] >= 0) Page.Companion.set(orop[ord[or]], x, y, 0, h, 200, 50)
                while (j < sub.size) {
                    if (sub[j].text != "(null)") {
                        Page.Companion.set(sub[j], x, y, 250 + w % 10 * 175, h + w / 10 * 50, 175, 50)
                        w++
                    }
                    j++
                }
                h += (w - 1) / 10 * 50 + 50
                or++
            }
        }
    }

    init {
        if (MainBCU.nimbus) {
            selectionBackground = if (MainBCU.light) Theme.LIGHT.NIMBUS_SELECT_BG else Theme.DARK.NIMBUS_SELECT_BG
        }
        cellRenderer = object : DefaultListCellRenderer() {
            private static
            val serialVersionUID = 1L
            override fun getListCellRendererComponent(l: JList<*>?, o: Any?, ind: Int, s: Boolean, f: Boolean): Component {
                val jl = super.getListCellRendererComponent(l, o, ind, s, f) as JLabel
                val v: BufferedImage
                v = if (type == -1) {
                    if (ind < para) UtilPC.getIcon(0, Interpret.EABIIND.get(ind)) else UtilPC.getIcon(1, ind - para)
                } else if (type == 0) {
                    val len: Int = Interpret.SABIS.size
                    if (para == 0) {
                        if (ind < len) UtilPC.getIcon(0, ind) else UtilPC.getIcon(1, ind - len)
                    } else {
                        if (ind < len) UtilPC.getIcon(0, ind) else UtilPC.getIcon(0, Interpret.ABIIND.get(ind - len))
                    }
                } else UtilPC.getIcon(type, ind)
                if (v == null) return jl
                jl.icon = ImageIcon(v)
                return jl
            }
        }
    }
}
