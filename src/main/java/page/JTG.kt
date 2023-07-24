package page

import common.util.lang.LocaleCenter
import java.awt.event.ActionEvent
import java.util.function.Consumer
import javax.swing.JToggleButton

class JTG() : JToggleButton(), LocComp {
    private val lsc: LocSubComp = LocSubComp(this)

    constructor(binder: LocaleCenter.Binder?) : this() {
        lsc.init(binder)
    }

    constructor(i: Int, str: String?) : this() {
        lsc.init(i, str)
    }

    constructor(str: String?) : this(-1, str)

    override fun getLSC(): LocSubComp {
        return lsc
    }

    fun setLnr(c: Consumer<ActionEvent?>) {
        addActionListener { e -> c.accept(e) }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
