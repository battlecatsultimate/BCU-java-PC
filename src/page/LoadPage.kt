package page

import io.Progress
import page.Page
import java.util.function.Consumer
import javax.swing.JLabel
import javax.swing.JProgressBar

class LoadPage : Page(null), Consumer<Progress?> {
    private val jl = JLabel()
    private val jpb: JProgressBar = JProgressBar()
    private var temp: String? = null
    override fun accept(dl: Progress) {
        jpb.setMaximum(1000)
        jpb.setValue((dl.prog * 1000).toInt())
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(jl, x, y, 100, 500, 2000, 50)
        Page.Companion.set(jpb, x, y, 100, 600, 2100, 50)
    }

    override fun timer(t: Int) {
        if (temp != null) {
            jl.text = temp
            temp = null
            jpb.setValue(0)
        }
    }

    private fun set(str: String) {
        temp = str
    }

    companion object {
        private const val serialVersionUID = 1L
        var lp: LoadPage?
        fun prog(str: String) {
            if (lp != null) lp!!.set(str)
        }
    }

    init {
        lp = this
        add(jl)
        add(jpb)
        resized()
    }
}
