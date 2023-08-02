package page

import main.MainBCU
import java.awt.*
import java.awt.event.*
import java.util.function.Consumer
import javax.swing.JTextField


class JTF @JvmOverloads constructor(tos: String = "") : JTextField(tos), CustomComp {
    private var hint: String? = null
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

    fun setTypeLnr(c: Consumer<ComponentEvent>) {
        if (MainBCU.searchPerKey) {
            addKeyListener(object: KeyAdapter() {
                override fun keyTyped(e: KeyEvent?) {
                    c.accept(e as ComponentEvent)
                }
            })
        } else {
            addFocusListener(object: FocusAdapter() {
                override fun focusLost(e: FocusEvent?) {
                    c.accept(e as ComponentEvent)
                }
            })
        }
    }

    fun setHint(h: String?) {
        hint = h
    }

    override fun paint(g: Graphics) {
        super.paint(g)
        if (text.isEmpty() && hint != null && !isFocusOwner) {
            val h = height
            (g as Graphics2D).setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            )
            val ins: Insets = insets
            val fm = g.getFontMetrics()
            val c0 = background.rgb
            val c1 = foreground.rgb
            val m = -0x1010102
            val c2 = (c0 and m ushr 1) + (c1 and m ushr 1)
            g.setColor(Color(c2, true))
            g.drawString(hint, ins.left, h / 2 + fm.ascent / 2 - 2)
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
