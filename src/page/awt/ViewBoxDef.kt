package page.awt

import common.CommonStatic
import common.system.fake.FakeGraphics
import common.util.anim.EAnimI
import main.Timer
import page.JTG
import page.view.ViewBox
import page.view.ViewBox.*
import utilpc.awt.FG2D
import java.awt.*
import java.awt.image.BufferedImage
import java.util.*

internal open class ViewBoxDef protected constructor(c: Controller) : Canvas(), ViewBox, VBExporter {
    protected var prev: BufferedImage? = null
    var isBlank = false
        protected set
    private var ent: EAnimI? = null
    protected var ctrl: Controller
    private var lbimg: Queue<BufferedImage>? = null
    private var loader: ViewBox.Loader? = null

    constructor() : this(Controller()) {}

    open fun draw(gra: FakeGraphics) {
        val w = width
        val h = height
        gra.translate(w / 2.toDouble(), h * 3 / 4.toDouble())
        if (ent != null) ent.draw(gra, ctrl.ori.copy().times(-1.0), ctrl.siz)
    }

    override fun end(btn: JTG?) {
        if (lbimg == null) return
        loader!!.finish(btn)
        lbimg = null
        loader = null
    }

    override fun getCtrl(): Controller {
        return ctrl
    }

    override fun getEnt(): EAnimI? {
        return ent
    }

    val exp: VBExporter?
        get() = this

    override fun getPrev(): BufferedImage? {
        return prev
    }

    override fun paint() {
        paint(graphics)
    }

    @Synchronized
    override fun paint(g: Graphics) {
        prev = image
        if (prev == null) return
        val w = width
        val h = height
        if (Conf.white) {
            val img: BufferedImage = createImage(w, h) as BufferedImage
            val gra: Graphics2D = img.getGraphics() as Graphics2D
            gra.setColor(Color.WHITE)
            gra.fillRect(0, 0, w, h)
            gra.drawImage(prev, 0, 0, null)
            g.drawImage(img, 0, 0, null)
            gra.dispose()
        } else g.drawImage(prev, 0, 0, null)
        if (CommonStatic.getConfig().ref) {
            g.color = Color.ORANGE
            g.drawString("Time cost: " + Timer.Companion.inter + "%", 20, 20)
        }
        g.dispose()
        if (lbimg != null) lbimg!!.add(prev)
    }

    override fun setEntity(ieAnim: EAnimI?) {
        ent = ieAnim
    }

    override fun start(): ViewBox.Loader? {
        if (ent == null) return null
        lbimg = ArrayDeque<BufferedImage>()
        loader = ViewBox.Loader(lbimg)
        loader!!.start()
        return loader
    }

    @Synchronized
    override fun update() {
        if (ent != null) ent.update(true)
    }

    @get:Synchronized
    protected val image: BufferedImage
        protected get() {
            val w = width
            val h = height
            val img: BufferedImage
            val gra: Graphics2D
            if (!isBlank && Conf.white) {
                img = BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR)
                gra = img.getGraphics() as Graphics2D
            } else {
                img = createImage(w, h) as BufferedImage
                gra = img.getGraphics() as Graphics2D
                if (!isBlank) {
                    val gdt = GradientPaint(w / 2, 0, ViewBox.Companion.c0, w / 2, h / 2, ViewBox.Companion.c1, true)
                    val p: Paint = gra.getPaint()
                    gra.setPaint(gdt)
                    gra.fillRect(0, 0, w, h)
                    gra.setPaint(p)
                }
            }
            draw(FG2D(gra))
            gra.dispose()
            return img
        }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ctrl = c
        c.setCont(this)
        ignoreRepaint = true
    }
}
