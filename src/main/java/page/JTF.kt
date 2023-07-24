package page

import java.awt.KeyboardFocusManager
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.function.Consumer
import javax.swing.JTextField

class JTF @JvmOverloads constructor(tos: String = "") : JTextField(tos), CustomComp {
    init {
        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(ke: KeyEvent) {
                if (ke.keyCode == KeyEvent.VK_ENTER) {
                    transferFocus()
                    if (isFocusOwner) KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner()
                }
            }
        })
    }

    fun setLnr(c: Consumer<FocusEvent?>) {
        addFocusListener(object : FocusAdapter() {
            override fun focusLost(e: FocusEvent) {
                c.accept(e)
            }
        })
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
