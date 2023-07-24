package page

import common.util.lang.LocaleCenter
import io.BCMusic
import main.MainBCU
import java.awt.event.ActionEvent
import java.util.function.Consumer
import java.util.function.Supplier
import javax.swing.JButton

class JBTN() : JButton(), LocComp {
    private val lsc: LocSubComp = LocSubComp(this)

    constructor(binder: LocaleCenter.Binder) : this() {
        lsc.init(binder)
    }

    constructor(i: Int, str: String) : this() {
        lsc.init(i, str)
    }

    constructor(str: String) : this(-1, str)

    override fun getLSC(): LocSubComp {
        return lsc
    }

    fun setLnr(c: Consumer<ActionEvent>) {
        addActionListener { e: ActionEvent ->
            if (MainBCU.buttonSound) BCMusic.clickSound()
            c.accept(e)
        }
    }

    fun setLnr(s: Supplier<Page>) {
        setLnr { _: ActionEvent -> MainFrame.changePanel(s.get()) }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
