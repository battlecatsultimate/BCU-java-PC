package page.basis

import common.CommonStatic
import common.battle.LineUp
import common.system.P
import common.system.SymCoord
import common.system.VImg
import common.system.fake.FakeGraphics
import common.system.fake.FakeImage
import common.util.Res
import common.util.unit.Combo
import common.util.unit.Form
import common.util.unit.Unit
import page.Page
import utilpc.PP
import utilpc.awt.FG2D
import java.awt.Canvas
import java.awt.Graphics
import java.awt.Point
import java.util.*

class LineUpBox(private val page: Page) : Canvas() {
    private var backup = arrayOfNulls<Form>(5)
    private var lu: LineUp? = null
    private var pt = 0
    private var time = 0
    private var sc: Combo? = null
    private var relative: PP? = null
    private var mouse: PP? = null
    var sf: Form? = null
    override fun paint(g: Graphics) {
        val slot: Array<VImg> = CommonStatic.getBCAssets().slot
        val bimg = createImage(600, 300) ?: return
        val gra: FakeGraphics = FG2D(bimg.graphics)
        for (i in 0..2) for (j in 0..4) {
            val f = getForm(i, j)
            var img: VImg
            img = if (f == null) slot[0] else f.anim.uni
            if (sf == null || sf !== f || relative == null) gra.drawImage(img.getImg(), 120 * j.toDouble(), 100 * i.toDouble())
            if (f == null) continue
            if (time == 0 && sc != null) for (fc in sc.units) if (LineUp.Companion.eq(f.uid, fc[0]) && f.fid >= fc[1]) gra.drawImage(slot[2].getImg(), 120 * j.toDouble(), 100 * i.toDouble())
            if (time == 1 && sf != null && f.uid === sf!!.uid && relative == null) gra.drawImage(slot[1].getImg(), 120 * j.toDouble(), 100 * i.toDouble())
            if (sf == null || sf !== f || relative == null) Res.getCost(lu.getLv(f.unit).getLvs().get(0), true,
                    SymCoord(gra, 1, 120 * j, 100 * i + img.getImg().getHeight(), 2))
        }
        if (relative != null && sf != null) {
            val p: Point = relative.sf(mouse).toPoint()
            val uni: FakeImage = sf!!.anim.uni.img
            gra.drawImage(uni, p.x.toDouble(), p.y.toDouble())
            Res.getCost(lu.getLv(sf!!.unit).getLvs().get(0), true, SymCoord(gra, 1, p.x, p.y + uni.getHeight(), 2))
        }
        g.drawImage(bimg, 0, 0, width, height, null)
        pt++
        if (pt == 5) time = 1 - time
        pt %= 5
    }

    fun setLU(l: LineUp?) {
        lu = l
        backup = arrayOfNulls(5)
    }

    fun adjForm() {
        if (sf == null) return
        val i: Int = getPos(sf)
        if (getForm(i)!!.uid === sf!!.uid) {
            val ufs = sf!!.unit.forms
            sf = ufs[(getForm(i)!!.fid + 1) % ufs.size]
            setForm(i, sf!!)
            lu.renew()
            page.callBack(null)
        }
    }

    fun click(p: Point) {
        var p = p
        p = getPos(p)
        select(getForm(p.y, p.x))
    }

    fun drag(p: Point?) {
        if (relative == null || sf == null) return
        mouse = PP(p).divide(getScale())
        val ori: Int = getPos(sf)
        val pf = getPos(mouse)
        val fin = pf.x + pf.y * 5
        if (ori != fin) jump(ori, fin)
    }

    fun press(p: Point) {
        click(p)
        val ul: PP = PP(getPos(p)).times(P(120, 100))
        relative = ul.sf(PP(p).divide(getScale()).also { mouse = it })
    }

    fun release(p: Point?) {
        relative = null
        mouse = null
    }

    fun select(c: Combo?) {
        sc = c
        time = 0
        paint(graphics)
    }

    fun select(f: Form?) {
        sf = f
        if (f == null) return
        if (getPos(f) == -1) {
            var b = false
            for (i in 0..4) if (backup[i] == null) {
                backup[i] = f
                b = true
                break
            }
            if (!b) backup[4] = f
        }
        time = 1
        paint(graphics)
        page.callBack(f)
    }

    fun setLv(lv: IntArray) {
        if (lv.size == 0 || sf == null) return
        lu.setLv(sf!!.unit, sf!!.regulateLv(lv, lu.getLv(sf!!.unit).getLvs()))
    }

    fun setPos(pos: Int) {
        if (sf == null || getPos(sf) == -1) return
        val p: Int = getPos(sf)
        jump(p, pos)
    }

    fun updateLU() {
        val su: MutableSet<Unit> = TreeSet<Unit>()
        for (i in 0..9) if (getForm(i) != null) su.add(getForm(i)!!.unit)
        for (i in 0..4) if (backup[i] != null && su.contains(backup[i]!!.unit)) backup[i] = null
    }

    private fun getForm(pos: Int): Form? {
        return if (pos < 10) lu.fs.get(pos / 5).get(pos % 5) else backup[pos % 5]
    }

    private fun getForm(i: Int, j: Int): Form {
        return if (i < 2) lu.fs.get(i).get(j) else backup[j]!!
    }

    private fun getPos(f: Form?): Int {
        if (f == null) return -1
        for (i in 0..14) if (getForm(i) != null && getForm(i)!!.uid === f.uid) return i
        return -1
    }

    private fun getPos(p: Point): Point {
        val siz = PP(size)
        val ans: PP = PP(p).times(P(5, 3)).divide(siz)
        ans.limit(PP(4, 2))
        return ans.toPoint()
    }

    private fun getPos(p: PP?): Point {
        val ans: PP = p.copy().times(P(5, 3)).divide(P(600, 300))
        ans.limit(P(4, 2))
        return ans.toPoint()
    }

    private fun getScale(): P {
        return PP(size).divide(P(600, 300))
    }

    private fun jump(ior: Int, ifi: Int) {
        var ifi = ifi
        var f = getForm(ior)
        if (ior > ifi) for (i in ifi..ior) f = setForm(i, f!!) else {
            if (ifi > 9) {
                f = setForm(ifi, f!!)
                if (ior > 9) setForm(ior, f!!) else for (i in 0..4) if (backup[i] == null) {
                    setForm(10 + i, f!!)
                    break
                }
                ifi = 9
                f = null
            }
            for (i in ifi downTo ior) f = setForm(i, f!!)
        }
        lu.arrange()
        lu.renew()
        page.callBack(null)
    }

    private fun setForm(pos: Int, f: Form): Form? {
        val ans = getForm(pos)
        if (pos < 10) lu.fs.get(pos / 5).get(pos % 5) = f else backup[pos % 5] = f
        return ans
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ignoreRepaint = true
    }
}
