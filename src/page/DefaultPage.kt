package page

import page.Page
import java.awt.event.ActionEvent
import java.util.function.Consumer

class DefaultPage protected constructor(p: Page?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
    }

    private fun addListeners() {
        back.setLnr(Consumer { e: ActionEvent? -> changePanel(front) })
    }

    private fun ini() {
        add(back)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
        resized()
    }
}
