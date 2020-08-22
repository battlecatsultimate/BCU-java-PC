package page

import common.util.lang.LocaleCenter
import javax.swing.BorderFactory
import javax.swing.JLabel

class JL() : JLabel(), LocComp {
    private val lsc: LocSubComp

    constructor(binder: LocaleCenter.Binder?) : this() {
        lsc.init(binder)
    }

    constructor(i: Int, str: String) : this() {
        lsc.init(i, str)
    }

    constructor(str: String) : this(-1, str)

    override fun getLSC(): LocSubComp {
        return lsc
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        lsc = LocSubComp(this)
        border = BorderFactory.createEtchedBorder()
    }
}
