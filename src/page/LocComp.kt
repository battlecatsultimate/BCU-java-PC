package page

import page.Pageimport
import java.awt.event.MouseListener

interface LocComp : CustomComp {
    override fun added(p: Page?) {
        getLSC().added(p)
    }

    fun addMouseListener(ml: MouseListener?)
    fun getLSC(): LocSubComp
    fun getText(): String?
    fun getToolTipText(): String?
    fun reLoc() {
        getLSC().reLoc()
    }

    fun setText(i: Int, str: String) {
        getLSC().init(i, str)
    }

    fun setText(t: String?)
    fun setToolTipText(ttt: String?)
}
