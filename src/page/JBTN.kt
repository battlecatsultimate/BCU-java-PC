package page

import common.util.lang.LocaleCenter
import page.MainFrame
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.function.Consumer
import java.util.function.Supplier
import javax.swing.JButton

class JBTN() : JButton(), LocComp {
    private val lsc: LocSubComp

    constructor(binder: LocaleCenter.Binder?) : this() {
        lsc.init(binder)
    }

    constructor(i: Int, str: String) : this() {
        lsc.init(i, str)
    }

    constructor(str: String) : this(-1, str) {}

    override fun getLSC(): LocSubComp {
        return lsc
    }

    fun setLnr(c: Consumer<ActionEvent?>) {
        addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                c.accept(e)
            }
        })
    }

    fun setLnr(s: Supplier<Page?>) {
        setLnr { e: ActionEvent? -> MainFrame.Companion.changePanel(s.get()) }
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        lsc = LocSubComp(this)
    }
}
