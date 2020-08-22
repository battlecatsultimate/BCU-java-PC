package page.anim

import common.CommonStatic
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
import common.util.anim.AnimU.UType
import common.util.anim.EPart
import common.util.anim.MaAnim
import common.util.anim.Part
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import main.Opts
import page.JBTN
import page.JL
import page.JTF
import page.JTG
import page.Page
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.Point
import java.awt.Rectangle
import java.awt.event.*
import java.util.*
import java.util.function.Consumer
import java.util.function.IntPredicate
import javax.swing.*
import javax.swing.event.*

class AdvAnimEditPage(p: Page?, anim: AnimCE?, id: UType) : Page(p), TreeCont {
    private val back: JBTN = JBTN(0, "back")
    private val jlm: JTree = JTree()
    private val jspm: JScrollPane = JScrollPane(jlm)
    private val jlv: JList<String> = JList<String>(mod)
    private val jspv: JScrollPane = JScrollPane(jlv)
    private val maet: MaAnimEditTable = MaAnimEditTable(this)
    private val jspma: JScrollPane = JScrollPane(maet)
    private val mpet: PartEditTable = PartEditTable(this)
    private val jspmp: JScrollPane = JScrollPane(mpet)
    private val jtb: JTG = JTG(0, "pause")
    private val nex: JBTN = JBTN(0, "nextf")
    private val jtl: JSlider = JSlider()
    private val ab: AnimBox = AnimBox()
    private val addp: JBTN = JBTN(0, "add")
    private val remp: JBTN = JBTN(0, "rem")
    private val addl: JBTN = JBTN(0, "addl")
    private val reml: JBTN = JBTN(0, "reml")
    private val same: JBTN = JBTN(0, "same")
    private val clea: JBTN = JBTN(0, "clean")
    private val sort: JBTN = JBTN(0, "sort")
    private val keep: JBTN = JBTN(0, "keep") // TODO
    private val appl: JBTN = JBTN(0, "apply") // TODO
    private val show: JBTN = JBTN(0, "show") // TODO
    private val time: JBTN = JBTN(0, "time") // TODO
    private val lkip: JL = JL()
    private val inft: JL = JL()
    private val inff: JL = JL()
    private val infv: JL = JL()
    private val infm: JL = JL()
    private val lmul: JL = JL("</>")
    private val tmul: JTF = JTF()
    private val ac: AnimCE?
    private val animID: UType
    private val mmt: MMTree
    private var p: Point? = null
    private var pause = false
    private var keeps: Array<Part?>?
    override fun callBack(o: Any?) {
        if (o != null && o is IntArray) change(o as IntArray?, { rs: IntArray ->
            if (rs[0] == 0) {
                maet.setRowSelectionInterval(rs[1], rs[2])
                setC(rs[1])
            } else {
                mpet.setRowSelectionInterval(rs[1], rs[2])
                setD(rs[1])
            }
        })
        val time = if (ab.ent == null) 0 else ab.ent.ind()
        ab.setEntity(ac.getEAnim(animID))
        ab.ent.setTime(time)
    }

    override fun collapse() {
        selectTree(false)
    }

    override fun expand() {
        selectTree(false)
    }

    fun selectTree(bv: Boolean) {
        if (isAdj) return
        val exp: Boolean = jlm.isExpanded(jlm.getSelectionPath())
        val o: Any = jlm.getLastSelectedPathComponent() ?: return
        val str = o.toString()
        val ind: Int = CommonStatic.parseIntN(str.split(" - ").toTypedArray()[0])
        val ses: MutableList<Int> = ArrayList()
        for (i in 0 until maet.ma.n) {
            val p: Part = maet.ma.parts.get(i)
            if (p.ints[0] == ind && (!bv || jlv.isSelectedIndex(p.ints[1]))) ses.add(i)
        }
        if (!exp) mmt.nav(ind, IntPredicate { xnd: Int ->
            for (i in 0 until maet.ma.n) {
                val p: Part = maet.ma.parts.get(i)
                if (p.ints[0] == xnd && (!bv || jlv.isSelectedIndex(p.ints[1]))) ses.add(i)
            }
            true
        })
        mmt.setAdjusting(true)
        setCs(ses)
        mmt.setAdjusting(false)
        ab.setSele(ind)
    }

    override fun mouseDragged(e: MouseEvent) {
        if (p == null) return
        ab.ori.x += p!!.x - e.x.toDouble()
        ab.ori.y += p!!.y - e.y.toDouble()
        p = e.point
    }

    override fun mousePressed(e: MouseEvent) {
        if (e.source !is AnimBox) return
        p = e.point
    }

    override fun mouseReleased(e: MouseEvent) {
        p = null
    }

    override fun mouseWheel(e: MouseEvent) {
        if (e.source !is AnimBox) return
        val mwe: MouseWheelEvent = e as MouseWheelEvent
        val d: Double = mwe.getPreciseWheelRotation()
        ab.siz *= Math.pow(res, d)
    }

    @Synchronized
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(addp, x, y, 300, 750, 200, 50)
        Page.Companion.set(remp, x, y, 300, 800, 200, 50)
        Page.Companion.set(lmul, x, y, 300, 650, 200, 50)
        Page.Companion.set(tmul, x, y, 300, 700, 200, 50)
        Page.Companion.set(jspv, x, y, 300, 850, 200, 450)
        Page.Companion.set(jspma, x, y, 500, 650, 900, 650)
        Page.Companion.set(jspmp, x, y, 1400, 650, 900, 650)
        Page.Companion.set(jspm, x, y, 0, 50, 300, 1250)
        Page.Companion.set(ab, x, y, 300, 50, 700, 500)
        Page.Companion.set(addl, x, y, 2100, 550, 200, 50)
        Page.Companion.set(reml, x, y, 2100, 600, 200, 50)
        Page.Companion.set(jtl, x, y, 300, 550, 900, 100)
        Page.Companion.set(jtb, x, y, 1200, 550, 200, 50)
        Page.Companion.set(nex, x, y, 1200, 600, 200, 50)
        Page.Companion.set(inft, x, y, 1400, 550, 250, 50)
        Page.Companion.set(inff, x, y, 1650, 550, 250, 50)
        Page.Companion.set(infv, x, y, 1400, 600, 250, 50)
        Page.Companion.set(infm, x, y, 1650, 600, 250, 50)
        // 1300 50 1000 500
        Page.Companion.set(same, x, y, 1300, 50, 200, 50)
        Page.Companion.set(sort, x, y, 1300, 100, 200, 50)
        Page.Companion.set(clea, x, y, 1300, 150, 200, 50)
        Page.Companion.set(time, x, y, 1300, 200, 200, 50)
        Page.Companion.set(lkip, x, y, 1500, 50, 200, 50)
        Page.Companion.set(keep, x, y, 1500, 100, 200, 50)
        Page.Companion.set(appl, x, y, 1500, 150, 200, 50)
        Page.Companion.set(show, x, y, 1500, 200, 200, 50)
        maet.setRowHeight(Page.Companion.size(x, y, 50))
        mpet.setRowHeight(Page.Companion.size(x, y, 50))
        ab.paint(ab.getGraphics())
    }

    override fun timer(t: Int) {
        if (!pause) eupdate()
        if (ab.ent != null && mpet.part != null) {
            val p: Part = mpet.part
            val ep: EPart = ab.ent.ent.get(p.ints[0])
            inft.setText("frame: " + ab.ent.ind())
            inff.setText("part frame: " + (p.frame - p.off))
            infv.setText("actual value: " + ep.getVal(p.ints[1]))
            infm.setText("part value: " + p.vd)
        } else {
            inft.setText("")
            inff.setText("")
            infv.setText("")
            infm.setText("")
        }
        resized()
    }

    private fun `addListeners$0`() {
        back.setLnr(Consumer { x: ActionEvent? -> changePanel(front) })
        jlm.addTreeSelectionListener(object : TreeSelectionListener {
            override fun valueChanged(arg0: TreeSelectionEvent?) {
                selectTree(false)
            }
        })
        jlv.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                selectTree(true)
            }
        })
    }

    private fun `addListeners$4`() {
        tmul.setLnr(Consumer<FocusEvent> { x: FocusEvent? ->
            val d: Double = CommonStatic.parseIntN(tmul.getText()) * 0.01
            if (!Opts.conf("times animation length by $d")) return@setLnr
            for (p in maet.ma.parts) {
                for (line in p.moves) (line[0] *= d).toInt()
                p.off *= d.toInt()
                p.validate()
            }
            maet.ma.validate()
            maet.anim.unSave("maanim multiply")
        })
        same.setLnr(Consumer { x: ActionEvent? -> change(0, { z: Int? -> setCs(findRep(mpet.part)) }) })
        sort.setLnr(Consumer { x: ActionEvent? -> Arrays.sort(maet.ma.parts) })
        clea.setLnr(Consumer { x: ActionEvent? -> for (p in maet.getSelected()) clean(p) })
        time.setLnr(Consumer { x: ActionEvent? ->
            val times = getTimeLine(maet.getSelected())
            var str = ""
            for (i in times!!) str += if (i == 0) "-" else "X"
            println(str) // TODO
        })
    }

    private fun `addListeners$5`() {
        keep.setLnr(Consumer { x: ActionEvent? ->
            keeps = maet.getSelected()
            lkip.setText("keep " + keeps!!.size + " item")
        })
        show.setLnr(Consumer { x: ActionEvent? ->
            change(0, { z: Int? ->
                if (keeps == null || keeps!!.size == 0) return@change
                val ses: MutableList<Int> = ArrayList()
                for (i in keeps!!.indices) {
                    ses.add(-1)
                    for (j in maet.ma.parts.indices) if (keeps!![i] === maet.ma.parts.get(j)) {
                        ses[i] = j
                        break
                    }
                }
                setCs(ses)
            })
        })
        appl.setLnr(Consumer { x: ActionEvent? ->
            if (mpet.part == null || keeps == null || keeps!!.size == 0) return@setLnr
            val p: Part = mpet.part
            if (Opts.conf("applying data of this part to " + keeps!!.size + " parts?")) for (pi in keeps!!) if (pi !== p) {
                pi!!.n = p.n
                pi.fir = p.fir
                pi.off = p.off
                pi.max = p.max
                pi.ints[2] = p.ints[2]
                pi.moves = arrayOfNulls(pi.n)
                for (i in 0 until p.n) pi.moves[i] = p.moves[i].clone()
                pi.validate()
            }
        })
    }

    private fun `addLnr$Anim`() {
        jtb.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                pause = jtb.isSelected()
                jtl.setEnabled(pause && ab.ent != null)
            }
        })
        nex.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                eupdate()
            }
        })
        jtl.addChangeListener(ChangeListener {
            if (isAdj || !pause) return@ChangeListener
            ab.ent.setTime(jtl.getValue())
        })
    }

    private fun `addLnr$C`() {
        val lsm: ListSelectionModel = maet.getSelectionModel()
        lsm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (isAdj || lsm.getValueIsAdjusting()) return
                val inds: IntArray = maet.getSelectedRows()
                val l: MutableList<Int> = ArrayList()
                for (i in inds) l.add(i)
                setCs(l)
            }
        })
        addp.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                change(true)
                val ind: Int = maet.getSelectedRow() + 1
                val ma: MaAnim = maet.ma
                val data: Array<Part> = ma.parts
                ma.parts = arrayOfNulls<Part>(++ma.n)
                for (i in 0 until ind) ma.parts.get(i) = data[i]
                for (i in ind until data.size) ma.parts.get(i + 1) = data[i]
                val np = Part()
                np.validate()
                ma.parts.get(ind) = np
                ma.validate()
                maet.anim.unSave("maanim add part")
                callBack(null)
                resized()
                lsm.setSelectionInterval(ind, ind)
                setC(ind)
                val h: Int = mpet.getRowHeight()
                mpet.scrollRectToVisible(Rectangle(0, h * ind, 1, h))
                change(false)
            }
        })
        remp.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                change(true)
                val ma: MaAnim = maet.ma
                val rows: IntArray = maet.getSelectedRows()
                val data: Array<Part?> = ma.parts
                for (row in rows) data[row] = null
                ma.n -= rows.size
                ma.parts = arrayOfNulls<Part>(ma.n)
                var ind = 0
                for (i in data.indices) if (data[i] != null) ma.parts.get(ind++) = data[i]
                ind = rows[rows.size - 1]
                ma.validate()
                maet.anim.unSave("maanim remove part")
                callBack(null)
                if (ind >= ma.n) ind = ma.n - 1
                lsm.setSelectionInterval(ind, ind)
                setC(ind)
                change(false)
            }
        })
    }

    private fun `addLnr$D`() {
        val lsm: ListSelectionModel = mpet.getSelectionModel()
        lsm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (isAdj || lsm.getValueIsAdjusting()) return
                setD(lsm.getLeadSelectionIndex())
            }
        })
        addl.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val p: Part = mpet.part
                val data = p.moves
                p.moves = arrayOfNulls(++p.n)
                for (i in data.indices) p.moves[i] = data[i]
                p.moves[p.n - 1] = IntArray(4)
                p.validate()
                maet.ma.validate()
                callBack(null)
                maet.anim.unSave("maanim add line")
                resized()
                change(p.n - 1, { i: Int? -> lsm.setSelectionInterval(i, i) })
                setD(p.n - 1)
                val h: Int = mpet.getRowHeight()
                mpet.scrollRectToVisible(Rectangle(0, h * (p.n - 1), 1, h))
            }
        })
        reml.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val inds: IntArray = mpet.getSelectedRows()
                if (inds.size == 0) return
                val p: Part = mpet.part
                val l: MutableList<IntArray> = ArrayList()
                var j = 0
                for (i in 0 until p.n) if (j >= inds.size || i != inds[j]) l.add(p.moves[i]) else j++
                p.moves = l.toTypedArray()
                p.n = l.size
                p.validate()
                maet.ma.validate()
                callBack(null)
                maet.anim.unSave("maanim remove line")
                var ind = inds[0]
                if (ind >= p.n) ind--
                change(ind, { i: Int? -> lsm.setSelectionInterval(i, i) })
                setD(ind)
            }
        })
    }

    private fun clean(p: Part?) {
        if (p!!.off != 0 || p.ints[2] != 1 || p.n < 4) return
        for (i in 2 until p.n - 1) {
            var suc = true
            for (j in 0 until p.n) {
                var mat = true
                val i0 = p.moves[j]
                val i1 = p.moves[j % i]
                mat = mat and (i0[1] == i1[1])
                mat = mat and (i0[2] == i1[2])
                mat = mat and (i0[3] == i1[3])
                if (j > 0) mat = mat and (i0[0] - p.moves[j - 1][0] == p.moves[(j - 1) % i + 1][0] - p.moves[(j - 1) % i][0])
                if (!mat) {
                    suc = false
                    break
                }
            }
            if (suc) {
                p.moves = Arrays.copyOf<IntArray>(p.moves, i + 1.also { p.n = it })
                p.ints[2] = -1
                p.validate()
                return
            }
        }
    }

    private fun eupdate() {
        ab.update()
        if (ab.ent != null) change(0, { x: Int? -> jtl.setValue(ab.ent.ind()) })
    }

    private fun findRep(p: Part?): List<Int>? {
        if (p == null) return null
        val ans: MutableList<Int> = ArrayList()
        for (i in 0 until maet.ma.n) {
            val pi: Part = maet.ma.parts.get(i)
            if (p.ints[1] != pi.ints[1]) continue
            if (p.ints[2] != pi.ints[2]) continue
            if (p.n != pi.n) continue
            if (p.ints[2] != pi.ints[2]) continue
            var pass = true
            for (j in 0 until p.n) for (k in 0..3) if (p.moves[j][k] != pi.moves[j][k]) pass = false
            if (pass) ans.add(i)
        }
        return ans
    }

    private fun getTimeLine(ps: Array<Part?>): IntArray? {
        var maxs = 0
        for (p in ps) {
            maxs = Math.max(maxs, p!!.max + 1)
            if (p.off > 0) return null
        }
        val ans = IntArray(maxs)
        for (p in ps) for (m in p!!.moves) ans[m[0]]++
        return ans
    }

    private fun ini() {
        add(back)
        add(jspm)
        add(jspv)
        add(jspma)
        add(jspmp)
        add(addp)
        add(remp)
        add(addl)
        add(reml)
        add(jtb)
        add(jtl)
        add(nex)
        add(ab)
        add(inft)
        add(inff)
        add(infv)
        add(infm)
        add(lmul)
        add(tmul)
        add(same)
        add(sort)
        add(keep)
        add(clea)
        add(appl)
        add(show)
        add(lkip)
        add(time)
        setA()
        `addListeners$0`()
        `addLnr$C`()
        `addLnr$D`()
        `addLnr$Anim`()
        `addListeners$4`()
        `addListeners$5`()
    }

    private fun setA() {
        mmt.renew()
        var row: Int = maet.getSelectedRow()
        maet.setAnim(ac, ac.getMaAnim(animID))
        ab.setEntity(ac.getEAnim(animID))
        if (row >= maet.getRowCount()) {
            maet.clearSelection()
            row = -1
        }
        setC(row)
        jtl.setPaintTicks(true)
        jtl.setPaintLabels(true)
        jtl.setMinimum(0)
        jtl.setMaximum(ab.ent.len())
        jtl.setLabelTable(null)
        if (ab.ent.len() <= 50) {
            jtl.setMajorTickSpacing(5)
            jtl.setMinorTickSpacing(1)
        } else if (ab.ent.len() <= 200) {
            jtl.setMajorTickSpacing(10)
            jtl.setMinorTickSpacing(2)
        } else if (ab.ent.len() <= 1000) {
            jtl.setMajorTickSpacing(50)
            jtl.setMinorTickSpacing(10)
        } else if (ab.ent.len() <= 5000) {
            jtl.setMajorTickSpacing(250)
            jtl.setMinorTickSpacing(50)
        } else {
            jtl.setMajorTickSpacing(1000)
            jtl.setMinorTickSpacing(200)
        }
    }

    private fun setC(ind: Int) {
        val p: Part? = if (ind < 0 || ind >= maet.ma.parts.size) null else maet.ma.parts.get(ind)
        remp.setEnabled(ind >= 0)
        addl.setEnabled(ind >= 0)
        same.setEnabled(ind >= 0)
        clea.setEnabled(ind >= 0)
        ab.setSele(p?.ints?.get(0) ?: -1)
        change(true)
        mpet.setAnim(maet.anim, maet.ma, p)
        mpet.clearSelection()
        if (ind >= 0) {
            val par = p!!.ints[0]
            mmt.select(par)
            jlv.setSelectedIndex(mpet.part!!.ints.get(1))
            if (maet.getSelectedRow() != ind) {
                maet.setRowSelectionInterval(ind, ind)
                maet.scrollRectToVisible(maet.getCellRect(ind, 0, true))
            }
            ab.setSele(par)
        } else maet.clearSelection()
        change(false)
        setD(-1)
    }

    private fun setCs(`is`: Iterable<Int>?) {
        change(true)
        var setted = false
        maet.clearSelection()
        for (i in `is`!!) if (i >= 0) {
            if (!setted) {
                setC(i)
                setted = true
            }
            maet.addRowSelectionInterval(i, i)
            val v: Int = maet.ma.parts.get(i).ints.get(1)
            jlv.addSelectionInterval(v, v)
        }
        if (!setted) setC(-1)
        change(false)
    }

    private fun setD(ind: Int) {
        reml.setEnabled(ind >= 0)
    }

    companion object {
        private const val serialVersionUID = 1L
        private const val res = 0.95
        private val mod = arrayOf("0 parent", "1 id", "2 sprite", "3 z-order", "4 pos-x",
                "5 pos-y", "6 pivot-x", "7 pivot-y", "8 scale", "9 scale-x", "10 scale-y", "11 angle", "12 opacity",
                "13 horizontal flip", "14 vertical flip", "50 extend")
    }

    init {
        ac = anim
        animID = id
        mmt = MMTree(this, ac, jlm)
        ini()
        resized()
    }
}
