package page.battle

import common.CommonStatic
import common.CommonStatic.BCAuxAssets
import common.CommonStatic.BattleConst
import common.battle.BattleField
import common.battle.StageBasis
import common.battle.entity.Entity
import common.battle.entity.WaprCont
import common.pack.PackData
import common.system.P
import common.system.SymCoord
import common.system.VImg
import common.system.fake.FakeGraphics
import common.system.fake.FakeImage
import common.system.fake.FakeTransform
import common.util.Data
import common.util.ImgCore
import common.util.Res
import common.util.stage.CastleImg
import common.util.unit.Form
import page.RetFunc
import utilpc.PP
import java.awt.Point

interface BattleBox {
    open class BBPainter(protected val page: OuterBox, bas: BattleField, bb: BattleBox) : BattleConst {
        val bf: BattleField
        var pt = -1
        protected val box: BattleBox
        protected var siz = 0.0
        protected var corr = 0.0
        protected var unir // siz = pix/p;
                = 0.0
        private var sb: StageBasis? = null
        private val maxW: Int
        private val maxH: Int
        private val minH // in p
                : Int
        private var pos = 0
        private var midh = 0
        private var prew = 0
        private var preh // in pix
                = 0
        private var mouse // in pix
                : P? = null
        private val aux: BCAuxAssets = CommonStatic.getBCAssets()
        open fun click(p: Point?, button: Int) {}
        fun draw(g: FakeGraphics) {
            val w = box.getWidth()
            val h = box.getHeight()
            sb = bf.sb
            if (prew != w || preh != h) {
                clear()
                prew = w
                preh = h
            }
            regulate()
            ImgCore.Companion.set(g)
            val rect = P(box.getWidth(), box.getHeight())
            sb.bg.draw(g, rect, pos, midh, siz)
            drawCastle(g)
            drawEntity(g)
            drawBtm(g)
            drawTop(g)
            sb = null
        }

        fun getX(x: Double): Double {
            return (x * BattleConst.Companion.ratio + off) * siz + pos
        }

        fun regulate() {
            val w = box.getWidth()
            val h = box.getHeight()
            if (siz * minH > h * bar / 10) siz = 1.0 * h * bar / 10 / minH
            if (siz * maxH < h) siz = 1.0 * h / maxH
            if (siz * maxW < w) siz = 1.0 * w / maxW
            if (pos > 0) pos = 0
            if (maxW * siz + pos < w) pos = (w - maxW * siz).toInt()
            midh = h * bar / 10
            if (midh > siz * minH * 2) midh = (siz * minH * 2).toInt()
        }

        fun reset() {
            pt = bf.sb.time
            box.reset()
        }

        private fun adjust(w: Int, s: Int) {
            pos += w
            siz *= Math.pow(exp, s.toDouble())
        }

        private fun clear() {
            pt = -1
            siz = 0.0
            pos = 0
            midh = 0
        }

        @Synchronized
        fun drag(p: Point) {
            if (mouse != null) {
                val temp: P = PP(p)
                adjust((temp.x - mouse.x) as Int, 0)
                mouse.setTo(temp)
                reset()
            }
        }

        private fun drawBtm(g: FakeGraphics) {
            val w = box.getWidth()
            val h = box.getHeight()
            var cw = 0
            val time: Int = sb.time / 5 % 2
            var mtype = if (sb.mon < sb.next_lv) 0 else if (time == 0) 1 else 2
            if (sb.work_lv == 8) mtype = 2
            val left: FakeImage = aux.battle.get(0).get(mtype).getImg()
            val ctype = if (sb.can == sb.max_can && time == 0) 1 else 0
            val right: FakeImage = aux.battle.get(1).get(ctype).getImg()
            cw += left.getWidth()
            cw += right.getWidth()
            cw += aux.slot.get(0).getImg().getWidth() * 5
            val r = 1.0 * w / cw
            val avah = h * (10 - bar) / 10.toDouble()
            var hr: Double = avah / left.getHeight()
            hr = Math.min(r, hr)
            corr = hr
            var ih = (hr * left.getHeight()) as Int
            var iw = (hr * left.getWidth()) as Int
            g.drawImage(left, 0.0, h - ih.toDouble(), iw.toDouble(), ih.toDouble())
            iw = (hr * right.getWidth()) as Int
            ih = (hr * right.getHeight()) as Int
            g.drawImage(right, w - iw.toDouble(), h - ih.toDouble(), iw.toDouble(), ih.toDouble())
            Res.getCost(sb.next_lv, mtype > 0, SymCoord(g, hr, hr * 5, h - hr * 5, 2))
            Res.getWorkerLv(sb.work_lv, mtype > 0, SymCoord(g, hr, hr * 5, h - hr * 130, 0))
            var hi = h
            var marg = 0.0
            if (ctype == 0) for (i in 0 until 10 * sb.can / sb.max_can) {
                val img: FakeImage = aux.battle.get(1).get(2 + i).getImg()
                iw = (hr * img.getWidth()) as Int
                ih = (hr * img.getHeight()) as Int
                marg += hr * img.getHeight() - ih
                if (marg > 0.5) {
                    marg--
                    ih++
                }
                hi -= ih
                g.drawImage(img, w - iw.toDouble(), hi.toDouble(), iw.toDouble(), ih.toDouble())
            }
            hr = avah / 2 / aux.slot.get(0).getImg().getHeight()
            hr = Math.min(r, hr)
            for (i in 0..9) {
                val f: Form = sb.b.lu.fs.get(i / 5).get(i % 5)
                val img: FakeImage = if (f == null) aux.slot.get(0).getImg() else f.anim.uni.img
                iw = (hr * img.getWidth()) as Int
                ih = (hr * img.getHeight()) as Int
                var x = (w - iw * 5) / 2 + iw * (i % 5)
                var y = h - ih * (2 - i / 5)
                g.drawImage(img, x.toDouble(), y.toDouble(), iw.toDouble(), ih.toDouble())
                if (f == null) continue
                val pri: Int = sb.elu.price.get(i / 5).get(i % 5)
                if (pri == -1) g.colRect(x, y, iw, ih, 255, 0, 0, 100)
                val cool: Int = sb.elu.cool.get(i / 5).get(i % 5)
                val b = pri > sb.mon || cool > 0
                if (b) g.colRect(x, y, iw, ih, 0, 0, 0, 100)
                if (sb.locks.get(i / 5).get(i % 5)) g.colRect(x, y, iw, ih, 0, 255, 0, 100)
                if (cool > 0) {
                    val dw = (hr * 10).toInt()
                    val dh = (hr * 12).toInt()
                    val cd: Double = 1.0 * cool / sb.elu.maxC.get(i / 5).get(i % 5)
                    val xw = (cd * (iw - dw * 2)).toInt()
                    g.colRect(x + iw - dw - xw, y + ih - dh * 2, xw, dh, 0, 0, 0, -1)
                    g.colRect(x + dw, y + ih - dh * 2, iw - dw * 2 - xw, dh, 100, 212, 255, -1)
                } else Res.getCost(pri, !b, SymCoord(g, hr, iw.let { x += it; x }, ih.let { y += it; y }, 3))
            }
            unir = hr
        }

        private fun drawCastle(gra: FakeGraphics) {
            val at: FakeTransform = gra.getTransform()
            val drawCast = sb.ebase is Entity
            var posy = (midh - road_h * siz).toInt()
            var posx = ((800 * BattleConst.Companion.ratio + off) * siz + pos) as Int
            if (!drawCast) {
                val cind: PackData.Identifier<CastleImg> = sb.st.castle
                val cast: VImg = cind.get().img
                val bimg: FakeImage = cast.getImg()
                val bw = (bimg.getWidth() * siz) as Int
                val bh = (bimg.getHeight() * siz) as Int
                gra.drawImage(bimg, posx - bw.toDouble(), posy - bh.toDouble(), bw.toDouble(), bh.toDouble())
            } else (sb.ebase as Entity).anim.draw(gra, P(posx, posy), siz * sprite)
            gra.setTransform(at)
            (posx -= castw * siz / 2).toInt()
            posy -= casth * siz.toInt()
            Res.getBase(sb.ebase, SymCoord(gra, siz, posx, posy, 0))
            posx = (((sb.st.len - 800) * BattleConst.Companion.ratio + off) * siz + pos)
            drawNyCast(gra, (midh - road_h * siz).toInt(), posx, siz, sb.nyc)
            (posx += castw * siz / 2).toInt()
            Res.getBase(sb.ubase, SymCoord(gra, siz, posx, posy, 1))
        }

        private fun drawEntity(gra: FakeGraphics) {
            val w = box.getWidth()
            val h = box.getHeight()
            val at: FakeTransform = gra.getTransform()
            val psiz = siz * sprite
            CommonStatic.getConfig().battle = true
            for (i in 0..9) {
                val dep = i * DEP
                for (e in sb.le) if (e.layer == i && (sb.s_stop == 0 || e.abi and Data.Companion.AB_TIMEI == 0)) {
                    gra.setTransform(at)
                    val p = getX(e.pos)
                    val y = midh - (road_h - dep) * siz
                    e.anim.draw(gra, P(p, y), psiz)
                    gra.setTransform(at)
                    e.anim.drawEff(gra, P(p, y), siz)
                }
                for (wc in sb.lw) if (wc.layer == i) {
                    gra.setTransform(at)
                    val p: Double = (wc.pos * BattleConst.Companion.ratio + off - wave) * siz + pos
                    val y: Double = midh - (road_h - DEP * wc.layer) * siz
                    wc.draw(gra, P(p, y), psiz)
                }
                for (eac in sb.lea) if (eac.layer == i) {
                    gra.setTransform(at)
                    val p = getX(eac.pos)
                    val y: Double = midh - (road_h - DEP * eac.layer) * siz
                    if (eac is WaprCont) {
                        val dx = if (eac.dire == -1) -27 * siz else -24 * siz
                        eac.draw(gra, P(p + dx, y - 24 * siz), psiz)
                    } else {
                        eac.draw(gra, P(p, y), psiz)
                    }
                }
            }
            gra.setTransform(at)
            val can = cany[sb.canon.id]
            val disp = canx[sb.canon.id]
            var ori: P? = P(getX(sb.ubase.pos) + disp * siz, midh + (can - road_h) * siz)
            sb.canon.drawBase(gra, ori, psiz)
            gra.setTransform(at)
            ori = P(getX(sb.canon.pos), midh - road_h * siz)
            sb.canon.drawAtk(gra, ori, psiz)
            gra.setTransform(at)
            if (sb.sniper != null && sb.sniper.enabled) {
                ori = P(getX(sb.sniper.getPos()), midh - road_h * siz)
                sb.sniper.drawBase(gra, ori, psiz)
                gra.setTransform(at)
            }
            if (sb.s_stop > 0) {
                gra.setComposite(FakeGraphics.Companion.GRAY, 0, 0)
                gra.fillRect(0, 0, w, h)
                for (i in 0..9) {
                    val dep = i * DEP
                    for (e in sb.le) if (e.layer == i && e.abi and Data.Companion.AB_TIMEI > 0) {
                        gra.setTransform(at)
                        val p = getX(e.pos)
                        val y = midh - (road_h - dep) * siz
                        e.anim.draw(gra, P(p, y), psiz)
                        gra.setTransform(at)
                        e.anim.drawEff(gra, P(p, y), siz)
                    }
                }
            }
            gra.setTransform(at)
            CommonStatic.getConfig().battle = false
        }

        private fun drawTop(g: FakeGraphics) {
            val w = box.getWidth()
            val p: P = Res.getMoney(sb.mon as Int, sb.max_mon, SymCoord(g, 1, w, 0, 1))
            val ih = p.y as Int
            var n = 0
            var bimg: FakeImage = aux.battle.get(2).get(1).getImg()
            val cw: Int = bimg.getWidth()
            if (sb.conf.get(0) and 2 > 0) {
                bimg = aux.battle.get(2).get(if (sb.sniper.enabled) 2 else 4).getImg()
                g.drawImage(bimg, w - cw.toDouble(), ih.toDouble())
                n++
            }
            bimg = aux.battle.get(2).get(1).getImg()
            if (sb.conf.get(0) and 1 > 0) {
                g.drawImage(bimg, w - cw * (n + 1).toDouble(), ih.toDouble())
                n++
            }
            bimg = aux.battle.get(2).get(if (page.getSpeed() > 0) 0 else 3).getImg()
            for (i in 0 until Math.abs(page.getSpeed())) g.drawImage(bimg, w - cw * (i + 1 + n).toDouble(), ih.toDouble())
        }

        @Synchronized
        fun press(p: Point) {
            mouse = PP(p)
        }

        @Synchronized
        fun release(p: Point) {
            mouse = null
        }

        @Synchronized
        fun wheeled(p: Point, ind: Int) {
            val w = box.getWidth()
            val h = box.getHeight()
            val psiz = siz * Math.pow(exp, ind.toDouble())
            if (psiz * minH > h * bar / 10 || psiz * maxH < h || psiz * maxW < w) return
            val dif = (-((p.x - pos) * (Math.pow(exp, ind.toDouble()) - 1))).toInt()
            adjust(dif, ind)
            reset()
        }

        companion object {
            private const val exp = 0.9
            private const val sprite = 0.8
            private const val road_h = 156 // in p
            private const val off = 200
            private const val DEP = 4
            private const val bar = 8
            private const val wave = 28
            private const val castw = 128
            private const val casth = 256
            private const val c0y = -130
            private const val c1y = -130
            private const val c2y = -258
            private val cany = intArrayOf(-134, -134, -134, -250, -250, -134, -134, -134)
            private val canx = intArrayOf(0, 0, 0, 64, 64, 0, 0, 0)
            fun drawNyCast(gra: FakeGraphics, y: Int, x: Int, siz: Double, inf: IntArray) {
                val aux: BCAuxAssets = CommonStatic.getBCAssets()
                var bimg: FakeImage = aux.main.get(2).get(inf[2]).getImg()
                var bw: Int = bimg.getWidth()
                var bh: Int = bimg.getHeight()
                var cy = (y + c0y * siz).toInt()
                gra.drawImage(bimg, x.toDouble(), cy.toDouble(), (bw * siz) as Int.toDouble(), (bh * siz) as Int.toDouble())
                bimg = aux.main.get(0).get(inf[0]).getImg()
                bw = bimg.getWidth()
                bh = bimg.getHeight()
                cy = (y + c2y * siz).toInt()
                gra.drawImage(bimg, x.toDouble(), cy.toDouble(), (bw * siz) as Int.toDouble(), (bh * siz) as Int.toDouble())
                bimg = aux.main.get(1).get(inf[1]).getImg()
                bw = bimg.getWidth()
                bh = bimg.getHeight()
                cy = (y + c1y * siz).toInt()
                gra.drawImage(bimg, x.toDouble(), cy.toDouble(), (bw * siz) as Int.toDouble(), (bh * siz) as Int.toDouble())
            }
        }

        init {
            bf = bas
            box = bb
            maxW = (bas.sb.st.len * BattleConst.Companion.ratio + off * 2)
            maxH = 510 * 3
            minH = 510
        }
    }

    interface OuterBox : RetFunc {
        fun getSpeed(): Int
    }

    fun click(p: Point?, button: Int) {
        getPainter().click(p, button)
    }

    fun drag(p: Point) {
        getPainter().drag(p)
    }

    fun getHeight(): Int
    fun getPainter(): BBPainter
    fun getWidth(): Int
    fun paint()
    fun press(p: Point) {
        getPainter().press(p)
    }

    fun release(p: Point) {
        getPainter().release(p)
    }

    fun reset()
    fun wheeled(p: Point, ind: Int) {
        getPainter().wheeled(p, ind)
    }
}
