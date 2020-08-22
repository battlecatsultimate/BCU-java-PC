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
import common.util.anim.AnimCE
import common.util.anim.ImgCut
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.Page
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import utilpc.PP
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.image.BufferedImage

internal class SpriteBox(private val page: Page) : Canvas() {
    private var anim: AnimCE? = null
    private var c: Point? = null
    private var skip = 0
    private var ctrl = false
    private var drag = false
    var sele = -1
    @Synchronized
    override fun paint(g: Graphics) {
        val bimg: BufferedImage = image ?: return
        g.drawImage(bimg, 0, 0, null)
        g.dispose()
    }

    @Synchronized
    fun setAnim(anim: AnimCE?) {
        if (this.anim !== anim) {
            this.anim = anim
            sele = -1
        } else if (anim != null && sele >= anim.imgcut.n) sele = -1
    }

    @get:Synchronized
    protected val image: BufferedImage?
        protected get() {
            val w = width
            val h = height
            val img: BufferedImage = createImage(w, h) as BufferedImage ?: return null
            val gra: Graphics2D = img.getGraphics() as Graphics2D
            if (anim != null) {
                val spr: BufferedImage = anim.getNum().bimg() as BufferedImage
                val aw: Int = spr.getWidth()
                val ah: Int = spr.getHeight()
                val r = Math.min(1.0 * w / aw, 1.0 * h / ah)
                val rw = (r * aw).toInt()
                val rh = (r * ah).toInt()
                gra.setColor(Color.LIGHT_GRAY)
                run {
                    var i = 0
                    while (i < rw) {
                        var j = i / 20 % 2 * 20
                        while (j < rh) {
                            gra.fillRect(i, j, 20, 20)
                            j += 40
                        }
                        i += 20
                    }
                }
                gra.drawImage(spr, 0, 0, rw, rh, null)
                val ic: ImgCut = anim.imgcut
                for (i in 0 until ic.n) {
                    val `val`: IntArray = ic.cuts.get(i)
                    val sx = (`val`[0] * r - 1).toInt()
                    val sy = (`val`[1] * r - 1).toInt()
                    val sw = (`val`[2] * r + 2).toInt()
                    val sh = (`val`[3] * r + 2).toInt()
                    if (i == sele) {
                        gra.setColor(Color.RED)
                        gra.fillRect(sx - 5, sy - 5, sw + 5, 5)
                        gra.fillRect(sx - 5, sy, 5, sh + 5)
                        gra.fillRect(sx + sw, sy - 5, 5, sh + 5)
                        gra.fillRect(sx, sy + sh, sw + 5, 5)
                    } else {
                        gra.setColor(Color.BLACK)
                        gra.drawRect(sx, sy, sw, sh)
                    }
                }
            }
            gra.dispose()
            return img
        }

    @Synchronized
    fun keyPressed(ke: KeyEvent) {
        if (ke.keyCode == KeyEvent.VK_CONTROL) ctrl = true
    }

    @Synchronized
    fun keyReleased(ke: KeyEvent) {
        if (ke.keyCode == KeyEvent.VK_CONTROL) ctrl = false
    }

    @Synchronized
    fun keyTyped(ke: KeyEvent) {
        if (ke.keyCode == KeyEvent.VK_TAB) {
            skip++
            sele = findSprite(c)
        }
    }

    @Synchronized
    fun mouseDragged(p: Point) {
        if (sele == -1) return
        drag = true
        val p0 = getPoint(c)
        val p1 = getPoint(p.also { c = it })
        val line: IntArray = anim.imgcut.cuts.get(sele)
        line[if (ctrl) 2 else 0] += p1.x - p0.x
        line[if (ctrl) 3 else 1] += p1.y - p0.y
    }

    @Synchronized
    fun mousePressed(p: Point?) {
        c = p
    }

    @Synchronized
    fun mouseReleased(p: Point) {
        if (drag && sele >= 0) {
            anim.ICedited()
            anim.unSave("imgcut drag")
            drag = false
        } else {
            skip = 0
            sele = findSprite(p.also { c = it })
            page.callBack(null)
        }
    }

    private fun findSprite(p: Point?): Int {
        if (anim == null) return -1
        val w = width
        val h = height
        val spr: BufferedImage = anim.getNum().bimg() as BufferedImage
        val aw: Int = spr.getWidth()
        val ah: Int = spr.getHeight()
        val r = Math.min(1.0 * w / aw, 1.0 * h / ah)
        val ic: ImgCut = anim.imgcut
        var ski = skip
        var fir = -1
        var sel = -1
        for (i in 0 until ic.n) if (!PP(p).out(ic.cuts.get(i), r, -5.0)) {
            if (fir == -1) fir = i
            if (ski == 0) sel = i else ski--
        }
        if (fir >= 0 && sel == -1) {
            skip = 0
            sel = fir
        }
        return sel
    }

    private fun getPoint(p: Point?): Point {
        val w = width
        val h = height
        val spr: BufferedImage = anim.getNum().bimg() as BufferedImage
        val aw: Int = spr.getWidth()
        val ah: Int = spr.getHeight()
        val r = Math.min(1.0 * w / aw, 1.0 * h / ah)
        return Point((p!!.x / r).toInt(), (p.y / r).toInt())
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ignoreRepaint = true
        isFocusable = true
        focusTraversalKeysEnabled = false
    }
}
