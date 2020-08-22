package page.battle

import common.battle.*
import common.battle.entity.AbEntity
import common.battle.entity.Entity
import common.util.stage.Recd
import common.util.stage.Stage
import io.BCMusic
import main.Opts
import page.JBTN
import page.JTG
import page.KeyHandler
import page.Page
import page.awt.BBBuilder
import page.battle.BattleBox.OuterBox
import page.battle.ComingTable
import page.battle.EntityTable
import java.awt.Canvas
import java.awt.event.*
import java.util.*
import java.util.function.Consumer
import javax.swing.JLabel
import javax.swing.JScrollPane
import javax.swing.JSlider
import javax.swing.event.ChangeListener

class BattleInfoPage : KeyHandler, OuterBox {
    private val back: JBTN = JBTN(0, "back")
    private val paus: JBTN = JBTN(0, "pause")
    private val next: JBTN = JBTN(0, "nextf")
    private val rply: JBTN = JBTN()
    private val ut: EntityTable = EntityTable(-1)
    private val ct: ComingTable = ComingTable(this)
    private val et: EntityTable = EntityTable(1)
    private val eup: JScrollPane = JScrollPane(ut)
    private val eep: JScrollPane = JScrollPane(et)
    private val ctp: JScrollPane = JScrollPane(ct)
    private val ebase = JLabel()
    private val ubase = JLabel()
    private val timer = JLabel()
    private val ecount = JLabel()
    private val ucount = JLabel()
    private val stream = JLabel()
    private val jtb: JTG = JTG(0, "larges")
    private val jsl: JSlider = JSlider()
    private val bb: BattleBox
    private val basis: BattleField
    private var pause = false
    private var recd: Recd? = null
    private var spe = 0
    private var upd = 0

    constructor(p: Page?, rec: Recd, conf: Int) : super(p) {
        recd = rec
        basis = SBRply(rec)
        bb = if (conf and 1 == 0) BBBuilder.Companion.def.getDef(this, basis) else BBBuilder.Companion.def.getRply(this, basis, rec.name, conf and 4 != 0)
        jtb.setSelected(conf and 2 != 0)
        jtb.setEnabled(conf and 1 == 0)
        ct.setData(basis.sb.st)
        if (recd.name.length > 0) jsl.setMaximum((basis as SBRply).size())
        ini()
        rply.setText(0, if (recd.name.length == 0) "save" else "start")
        resized()
    }

    protected constructor(p: Page?, rpl: SBRply) : super(p) {
        val ctrl: SBCtrl = rpl.transform(this)
        bb = BBBuilder.Companion.def.getCtrl(this, ctrl)
        pause = true
        basis = ctrl
        ct.setData(basis.sb.st)
        ini()
        rply.setText(0, "rply")
        resized()
        current = this
    }

    constructor(p: Page?, st: Stage?, star: Int, bl: BasisLU, ints: IntArray?) : super(p) {
        val seed = Random().nextLong()
        val sb = SBCtrl(this, st, star, bl.copy(), ints, seed)
        bb = BBBuilder.Companion.def.getCtrl(this, sb)
        basis = sb
        ct.setData(basis.sb.st)
        jtb.setSelected(DEF_LARGE)
        ini()
        rply.setText(0, "rply")
        resized()
        current = this
    }

    override fun callBack(o: Any?) {
        changePanel(front)
    }

    override fun getSpeed(): Int {
        return spe
    }

    @Synchronized
    override fun keyTyped(e: KeyEvent) {
        if (spe > -5 && e.keyChar == ',') {
            spe--
            bb.reset()
        }
        if (spe < (if (basis is SBCtrl) 5 else 7) && e.keyChar == '.') {
            spe++
            bb.reset()
        }
    }

    override fun mouseClicked(e: MouseEvent) {
        if (e.source === bb) bb.click(e.point, e.button)
    }

    override fun mouseDragged(e: MouseEvent) {
        if (e.source === bb) bb.drag(e.point)
    }

    override fun mousePressed(e: MouseEvent) {
        if (e.source === bb) bb.press(e.point)
    }

    override fun mouseReleased(e: MouseEvent) {
        if (e.source === bb) bb.release(e.point)
    }

    override fun mouseWheel(e: MouseEvent) {
        if (e.source === bb) bb.wheeled(e.point, (e as MouseWheelEvent).getWheelRotation())
    }

    override fun renew() {
        if (basis.sb.getEBHP() * 100 < basis.sb.st.mush) BCMusic.play(basis.sb.st.mus1, basis.sb.st.loop1) else BCMusic.play(basis.sb.st.mus0, basis.sb.st.loop0)
    }

    @Synchronized
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jtb, x, y, 2100, 0, 200, 50)
        if (jtb.isSelected()) {
            Page.Companion.set(paus, x, y, 700, 0, 200, 50)
            Page.Companion.set(rply, x, y, 900, 0, 200, 50)
            Page.Companion.set(stream, x, y, 900, 0, 400, 50)
            Page.Companion.set(next, x, y, 1100, 0, 200, 50)
            Page.Companion.set(ebase, x, y, 240, 0, 600, 50)
            Page.Companion.set(timer, x, y, 1300, 0, 200, 50)
            Page.Companion.set(ubase, x, y, 1540, 0, 200, 50)
            Page.Companion.set(bb as Canvas, x, y, 50, 50, 1920, 1200)
            Page.Companion.set(ctp, x, y, 0, 0, 0, 0)
            Page.Companion.set(eep, x, y, 50, 100, 0, 0)
            Page.Companion.set(eup, x, y, 50, 400, 0, 0)
            Page.Companion.set(ecount, x, y, 50, 50, 0, 0)
            Page.Companion.set(ucount, x, y, 50, 350, 0, 0)
            Page.Companion.set(jsl, x, y, 0, 0, 0, 0)
        } else {
            Page.Companion.set(ctp, x, y, 50, 850, 1200, 400)
            Page.Companion.set(eep, x, y, 50, 100, 600, 700)
            Page.Companion.set(bb as Canvas, x, y, 700, 300, 800, 500)
            Page.Companion.set(paus, x, y, 700, 200, 200, 50)
            Page.Companion.set(rply, x, y, 1000, 200, 200, 50)
            Page.Companion.set(stream, x, y, 900, 200, 400, 50)
            Page.Companion.set(next, x, y, 1300, 200, 200, 50)
            Page.Companion.set(eup, x, y, 1650, 100, 600, 1100)
            Page.Companion.set(ebase, x, y, 700, 250, 400, 50)
            Page.Companion.set(timer, x, y, 1100, 250, 200, 50)
            Page.Companion.set(ubase, x, y, 1300, 250, 200, 50)
            Page.Companion.set(ecount, x, y, 50, 50, 600, 50)
            Page.Companion.set(ucount, x, y, 1650, 50, 600, 50)
            Page.Companion.set(jsl, x, y, 700, 800, 800, 50)
        }
        ct.setRowHeight(Page.Companion.size(x, y, 50))
        et.setRowHeight(Page.Companion.size(x, y, 50))
        ut.setRowHeight(Page.Companion.size(x, y, 50))
    }

    @Synchronized
    override fun timer(t: Int) {
        val sb: StageBasis = basis.sb
        if (!pause) {
            upd++
            if (spe < 0) if (upd % (1 - spe) != 0) return
            basis.update()
            updateKey()
            if (spe > 0) {
                var i = 0
                while (i < Math.pow(2.0, spe.toDouble())) {
                    basis.update()
                    i++
                }
            }
            ct.update(sb.est)
            val le: MutableList<Entity> = ArrayList()
            val lu: List<Entity> = ArrayList()
            for (e in sb.le) (if (e.dire == 1) le else lu).add(e)
            et.setList(le)
            ut.setList(lu)
            BCMusic.flush(spe < 3)
        }
        if (basis is SBRply && recd.name.length > 0) change<SBRply>(basis as SBRply, Consumer<SBRply> { b: SBRply -> jsl.setValue(b.prog()) })
        bb.paint()
        val eba: AbEntity = sb.ebase
        val h: Long = eba.health
        val mh: Long = eba.maxH
        ebase.text = "HP: " + h + "/" + mh + ", " + 10000 * h / mh / 100.0 + "%"
        ubase.text = "HP: " + sb.ubase.health
        timer.text = sb.time.toString() + "f"
        ecount.text = sb.entityCount(1).toString() + "/" + sb.st.max
        ucount.text = sb.entityCount(-1).toString() + "/" + sb.max_num
        resized()
        if (sb.getEBHP() * 100 <= sb.st.mush && BCMusic.music !== sb.st.mus1) BCMusic.play(sb.st.mus1, sb.st.loop1)
        if (bb is BBRecd) {
            stream.text = "frame left: " + bb.info()
        }
    }

    private fun addListeners() {
        jtb.setLnr(Consumer { x: ActionEvent? ->
            remove(bb as Canvas)
            resized()
            add(bb as Canvas)
            DEF_LARGE = jtb.isSelected()
        })
        back.setLnr(Consumer { x: ActionEvent? ->
            BCMusic.stopAll()
            if (bb is BBRecd) {
                val bbr: BBRecd = bb
                if (Opts.conf("Do you want to save this video?")) {
                    bbr.end()
                    return@setLnr
                } else {
                    bbr.quit()
                }
            }
            changePanel(front)
        })
        rply.setLnr(Consumer { x: ActionEvent? ->
            if (basis is SBCtrl) changePanel(BattleInfoPage(getThis(), (basis as SBCtrl).getData(), 0))
            if (basis is SBRply) if (recd.name.length == 0) changePanel(RecdSavePage(getThis(), recd)) else changePanel(BattleInfoPage(this, basis as SBRply))
        })
        paus.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                pause = !pause
                jsl.setEnabled(pause)
            }
        })
        next.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                pause = false
                timer(0)
                pause = true
            }
        })
        jsl.addChangeListener(ChangeListener {
            if (jsl.getValueIsAdjusting() || isAdj || basis !is SBRply) return@ChangeListener
            (basis as SBRply).restoreTo(jsl.getValue())
            bb.reset()
        })
    }

    private fun ini() {
        add(back)
        add(eup)
        add(eep)
        add(ctp)
        add(bb as Canvas)
        add(paus)
        add(next)
        add(ebase)
        add(ubase)
        add(timer)
        add(ecount)
        add(ucount)
        add(jtb)
        if (bb is BBRecd) add(stream) else {
            add(rply)
            if (recd != null && recd.name.length > 0) {
                add(jsl)
                jsl.setEnabled(pause)
            }
        }
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
        var DEF_LARGE = false
        var current: BattleInfoPage? = null
        fun redefine() {
            ComingTable.Companion.redefine()
            EntityTable.Companion.redefine()
        }
    }
}
