package page.anim

import page.JBTN
import page.JL
import page.Page
import utilpc.Algorithm
import java.awt.event.ActionEvent
import java.awt.image.BufferedImage
import java.util.function.Consumer
import javax.swing.BorderFactory
import javax.swing.ImageIcon
import javax.swing.JScrollPane
import javax.swing.JSlider
import javax.swing.event.ChangeListener

class SpriteEditPage(p: Page?, bimg: BufferedImage?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val disp: JL = JL()
    private val jsp: JScrollPane = JScrollPane(disp)
    private val jlh: JL = JL()
    private val jls: JL = JL()
    private val jlb: JL = JL()
    private val jslh: JSlider = JSlider(-180, 180, 0)
    private val jsls: JSlider = JSlider(-100, 100, 0)
    private val jslb: JSlider = JSlider(-100, 100, 0)
    private val base: BufferedImage?
    private var curr: BufferedImage?
    private var h = 0
    private var s = 0
    private var b = 0
    fun getEdit(): BufferedImage? {
        return curr
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jsp, x, y, 0, 50, 2000, 1000)
        Page.Companion.set(jslh, x, y, 0, 1100, 300, 100)
        Page.Companion.set(jsls, x, y, 300, 1100, 300, 100)
        Page.Companion.set(jslb, x, y, 600, 1100, 300, 100)
        Page.Companion.set(jlh, x, y, 0, 1050, 300, 100)
        Page.Companion.set(jls, x, y, 300, 1050, 300, 100)
        Page.Companion.set(jlb, x, y, 600, 1050, 300, 100)
    }

    private fun addListeners() {
        back.setLnr(Consumer { e: ActionEvent? -> changePanel(front) })
        jslh.addChangeListener(ChangeListener { setColor(jslh.value, s, b) })
        jsls.addChangeListener(ChangeListener { setColor(h, jsls.value, b) })
        jslb.addChangeListener(ChangeListener { setColor(h, s, jslb.value) })
    }

    private fun ini() {
        add(back)
        add(jsp)
        add(jslh)
        add(jsls)
        add(jslb)
        add(jlh)
        add(jls)
        add(jlb)
        disp.border = BorderFactory.createEtchedBorder()
        jslh.majorTickSpacing = 30
        jslh.minorTickSpacing = 10
        jslh.paintTicks = true
        jsls.majorTickSpacing = 20
        jsls.minorTickSpacing = 5
        jsls.paintTicks = true
        jslb.majorTickSpacing = 20
        jslb.minorTickSpacing = 5
        jslb.paintTicks = true
        setColor(h, s, b)
        addListeners()
    }

    private fun setColor(hval: Int, sval: Int, bval: Int) {
        h = hval
        s = sval
        b = bval
        curr = Algorithm.shift(base, h / 360f.toDouble(), s / 100f.toDouble(), b / 100f.toDouble())
        disp.icon = ImageIcon(curr)
        jlh.text = "hue: $h"
        jls.setText("saturation:$s")
        jlb.setText("brightness:$b")
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        base = bimg
        curr = base
        ini()
        resized()
    }
}
