package page.anim

import common.io.assets.Admin
import common.io.assets.Admin.StaticPermitted
import common.io.assets.AssetLoader
import common.io.assets.AssetLoader.AssetHeader
import common.io.assets.AssetLoader.AssetHeader.AssetEntry
import common.io.json.JsonEncoder
import common.io.json.Test
import common.io.json.Test.JsonTest_0.JsonD
import common.io.json.Test.JsonTest_2
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.pack.Source.SourceAnimLoader
import common.pack.Source.SourceAnimSaver
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JBTN
import page.JL
import page.Page
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
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
        jslh.addChangeListener(ChangeListener { setColor(jslh.getValue(), s, b) })
        jsls.addChangeListener(ChangeListener { setColor(h, jsls.getValue(), b) })
        jslb.addChangeListener(ChangeListener { setColor(h, s, jslb.getValue()) })
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
        disp.setBorder(BorderFactory.createEtchedBorder())
        jslh.setMajorTickSpacing(30)
        jslh.setMinorTickSpacing(10)
        jslh.setPaintTicks(true)
        jsls.setMajorTickSpacing(20)
        jsls.setMinorTickSpacing(5)
        jsls.setPaintTicks(true)
        jslb.setMajorTickSpacing(20)
        jslb.setMinorTickSpacing(5)
        jslb.setPaintTicks(true)
        setColor(h, s, b)
        addListeners()
    }

    private fun setColor(hval: Int, sval: Int, bval: Int) {
        h = hval
        s = sval
        b = bval
        curr = Algorithm.shift(base, h / 360f.toDouble(), s / 100f.toDouble(), b / 100f.toDouble())
        disp.setIcon(ImageIcon(curr))
        jlh.setText("hue: $h")
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
