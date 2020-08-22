package page.anim

import common.CommonStatic
import common.CommonStatic.BCAuxAssets
import common.system.fake.FakeGraphics
import common.system.fake.FakeImage
import page.view.ViewBox
import page.view.ViewBox.Controller
import utilpc.PP
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage

interface IconBox : ViewBox {
    object IBConf {
        val line = IntArray(4)
        var mode = 0
        var type = 0
        var glow = 0
    }

    class IBCtrl : Controller() {
        protected var ctrl = false
        protected var drag = false
        @Synchronized
        override fun mouseDragged(e: MouseEvent) {
            if (!drag) {
                super.mouseDragged(e)
                return
            }
            val t = e.point
            IBConf.line[if (ctrl) 2 else 0] += t.x - p.x
            IBConf.line[if (ctrl) 3 else 1] += t.y - p.y
            p = t
        }

        @Synchronized
        override fun mousePressed(e: MouseEvent) {
            super.mousePressed(e)
            if (cont.isBlank() && !PP(p).out(IBConf.line, 1.0, -5.0)) drag = true
        }

        @Synchronized
        override fun mouseReleased(e: MouseEvent?) {
            drag = false
            super.mouseReleased(e)
        }

        fun postdraw(gra: FakeGraphics) {
            if (cont.isBlank()) {
                val aux: BCAuxAssets = CommonStatic.getBCAssets()
                val t = if (IBConf.mode == 0) if (IBConf.type == 1) 1 else 0 else 3
                var bimg: FakeImage = aux.ico.get(IBConf.mode).get(t).getImg()
                val bw: Int = bimg.getWidth()
                val bh: Int = bimg.getHeight()
                val r = Math.min(1.0 * IBConf.line[2] / bw, 1.0 * IBConf.line[3] / bh)
                gra.setColor(FakeGraphics.Companion.BLACK)
                gra.drawRect(IBConf.line[0] - 1, IBConf.line[1] - 1, IBConf.line[2] + 1, IBConf.line[3] + 1)
                if (IBConf.glow == 1) {
                    gra.setComposite(FakeGraphics.Companion.BLEND, 256, 1)
                    bimg = aux.ico.get(0).get(4).getImg()
                    gra.drawImage(bimg, IBConf.line[0], IBConf.line[1], (bw * r) as Int.toDouble(), (bh * r) as Int.toDouble())
                    gra.setComposite(FakeGraphics.Companion.DEF, 0, 0)
                }
                if (IBConf.mode == 0 && IBConf.type > 1) {
                    bimg = aux.ico.get(0).get(5).getImg()
                    gra.drawImage(bimg, IBConf.line[0], IBConf.line[1], (bw * r) as Int.toDouble(), (bh * r) as Int.toDouble())
                } else {
                    bimg = aux.ico.get(IBConf.mode).get(t).getImg()
                    gra.drawImage(bimg, IBConf.line[0], IBConf.line[1], (bw * r) as Int.toDouble(), (bh * r) as Int.toDouble())
                }
            }
        }

        fun predraw(gra: FakeGraphics) {
            if (cont.isBlank()) {
                val aux: BCAuxAssets = CommonStatic.getBCAssets()
                if (IBConf.mode == 0 && IBConf.type > 1 || IBConf.mode == 1) {
                    var bimg: FakeImage = aux.ico.get(IBConf.mode).get(IBConf.type).getImg()
                    val bw: Int = bimg.getWidth()
                    val bh: Int = bimg.getHeight()
                    val r = Math.min(1.0 * IBConf.line[2] / bw, 1.0 * IBConf.line[3] / bh)
                    gra.drawImage(bimg, IBConf.line[0], IBConf.line[1], bw * r, bh * r)
                    if (IBConf.glow == 1) {
                        gra.setComposite(FakeGraphics.Companion.BLEND, 256, -1)
                        bimg = aux.ico.get(0).get(4).getImg()
                        gra.drawImage(bimg, IBConf.line[0], IBConf.line[1], bw * r, bh * r)
                        gra.setComposite(FakeGraphics.Companion.DEF, 0, 0)
                    }
                }
            }
        }

        @Synchronized
        fun keyPressed(ke: KeyEvent) {
            if (ke.keyCode == KeyEvent.VK_CONTROL) ctrl = true
        }

        @Synchronized
        fun keyReleased(ke: KeyEvent) {
            if (ke.keyCode == KeyEvent.VK_CONTROL) ctrl = false
        }
    }

    fun changeType()
    val clip: BufferedImage?
    val ctrl: IBCtrl
    fun keyPressed(ke: KeyEvent) {
        ctrl.keyPressed(ke)
    }

    fun keyReleased(ke: KeyEvent) {
        ctrl.keyReleased(ke)
    }

    fun setBlank(selected: Boolean)
}
