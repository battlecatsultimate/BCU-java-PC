package page

import common.util.lang.LocaleCenter
import javax.swing.BorderFactory
import utilpc.OutlineText
import java.awt.Graphics
import javax.swing.JLabel

class JL() : JLabel(), LocComp {
    private val lsc: LocSubComp = LocSubComp(this)

    init {
        border = BorderFactory.createEtchedBorder()
    }

    constructor(binder: LocaleCenter.Binder) : this() {
        lsc.init(binder)
    }

    constructor(i: Int, str: String?) : this() {
        lsc.init(i, str)
    }

    constructor(str: String?) : this(-1, str)

    override fun getLSC(): LocSubComp {
        return lsc
    }

    override fun paintComponent(g: Graphics) {
        if (!OutlineText.paint(this, g))
            super.paintComponent(g)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
