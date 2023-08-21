package page

import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JProgressBar

class LoadPage : Page(null) {
    private val jl = JLabel()
    private val jpb = JProgressBar()
    private var temp: String? = null

    init {
        lp = this
        add(jl)
        add(jpb)
        resized(true)
    }

    fun accept(dl: Double) {
        jpb.maximum = 1000
        jpb.value = (dl * 1000).toInt()
    }

    override fun getBackButton(): JButton? {
        return null
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        set(jl, x, y, 100, 500, 2000, 50)
        set(jpb, x, y, 100, 600, 2100, 50)
    }

    override fun timer(t: Int) {
        if (temp != null) {
            jl.text = temp
            temp = null
            jpb.value = 0
        }
    }

    private fun set(str: String) {
        temp = str
    }

    companion object {
        private const val serialVersionUID = 1L
        @JvmField
		var lp: LoadPage? = null

        @JvmStatic
		fun prog(i: Double) {
            lp!!.jpb.maximum = 1000
            lp!!.jpb.value = (i * 1000).toInt()
        }

        @JvmStatic
		fun prog(str: String?) {
            lp?.set(str ?: "unknown")
        }
    }
}
