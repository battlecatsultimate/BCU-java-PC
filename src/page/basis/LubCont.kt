package page.basis

import common.system.Node
import common.util.unit.Unit
import page.Page
import page.info.UnitInfoPage
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent

abstract class LubCont protected constructor(p: Page?) : Page(p) {
    protected abstract fun getLub(): LineUpBox
    override fun keyTyped(e: KeyEvent) {
        if (Character.isDigit(e.keyChar)) {
            var i = (e.keyChar.toString() + "").toInt()
            if (i == 0) i = 9 else i--
            getLub().setPos(i)
        } else if (e.keyChar == 'd') {
            getLub().setPos(10)
        }
    }

    override fun mouseClicked(e: MouseEvent) {
        if (e.source === getLub()) if (e.button == MouseEvent.BUTTON1) getLub().click(e.point) else if (getLub().sf != null) {
            val n = Node<Unit>(getLub().sf!!.unit)
            changePanel(UnitInfoPage(this, n))
        }
    }

    override fun mouseDragged(e: MouseEvent) {
        if (e.source === getLub()) getLub().drag(e.point)
    }

    override fun mousePressed(e: MouseEvent) {
        if (e.source === getLub()) getLub().press(e.point)
    }

    override fun mouseReleased(e: MouseEvent) {
        if (e.source === getLub()) getLub().release(e.point)
    }

    override fun timer(t: Int) {
        getLub().paint(getLub().graphics)
        resized()
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
