package page.anim

import common.CommonStatic
import common.util.anim.AnimCE
import common.util.anim.EAnimS
import common.util.anim.ImgCut
import common.util.anim.MaModel
import page.JBTN
import page.Page
import page.support.AnimLCR
import java.awt.Color
import java.awt.Point
import java.awt.Rectangle
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent
import java.util.*
import java.util.function.Consumer
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.JTree
import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener
import javax.swing.event.TreeSelectionEvent
import javax.swing.event.TreeSelectionListener
import javax.swing.tree.DefaultTreeModel

class MaModelEditPage : Page, AbEditPage {
    private val back: JBTN = JBTN(0, "back")
    private val jlu: JList<AnimCE> = JList<AnimCE>()
    private val jspu: JScrollPane = JScrollPane(jlu)
    private val jlp: JList<String> = JList<String>()
    private val jspp: JScrollPane = JScrollPane(jlp)
    private val jtr: JTree = JTree()
    private val jsptr: JScrollPane = JScrollPane(jtr)
    private val mmet: MaModelEditTable = MaModelEditTable(this)
    private val jspmm: JScrollPane = JScrollPane(mmet)
    private val sb: SpriteBox = SpriteBox(this)
    private val mb: ModelBox = ModelBox()
    private val revt: JBTN = JBTN(0, "revt")
    private val addl: JBTN = JBTN(0, "addl")
    private val reml: JBTN = JBTN(0, "reml")
    private val rema: JBTN = JBTN(0, "rema")
    private val sort: JBTN = JBTN(0, "sort")
    private val aep: EditHead
    private var p: Point? = null
    private var mmt: MMTree? = null

    constructor(p: Page?) : super(p) {
        aep = EditHead(this, 2)
        ini()
        resized()
    }

    constructor(p: Page?, bar: EditHead) : super(p) {
        aep = bar
        ini()
        resized()
    }

    override fun callBack(obj: Any?) {
        change(obj, { o: Any? ->
            if (o != null && o is IntArray) {
                val rs = o
                mmet.setRowSelectionInterval(rs[0], rs[1])
                setB(rs[0])
            }
            if (mb.getEnt() != null) mb.getEnt().organize()
            setTree(mmet.anim)
        })
    }

    override fun setSelection(anim: AnimCE?) {
        change<AnimCE>(anim, Consumer<AnimCE> { ac: AnimCE? ->
            jlu.setSelectedValue(ac, true)
            setA(ac)
        })
    }

    override fun mouseDragged(e: MouseEvent) {
        if (p == null) return
        mb.ori.x += p!!.x - e.x.toDouble()
        mb.ori.y += p!!.y - e.y.toDouble()
        p = e.point
    }

    override fun mousePressed(e: MouseEvent) {
        if (e.source !is ModelBox) return
        p = e.point
    }

    override fun mouseReleased(e: MouseEvent) {
        p = null
    }

    override fun mouseWheel(e: MouseEvent) {
        if (e.source !is ModelBox) return
        val mwe: MouseWheelEvent = e as MouseWheelEvent
        val d: Double = mwe.getPreciseWheelRotation()
        mb.siz *= Math.pow(res, d)
    }

    override fun renew() {
        change(this, { page: MaModelEditPage? ->
            val da: AnimCE = jlu.getSelectedValue()
            val vec: Vector<AnimCE?> = Vector<AnimCE?>()
            if (aep.focus == null) vec.addAll(AnimCE.Companion.map().values) else vec.add(aep.focus)
            jlu.setListData(vec)
            if (da != null && vec.contains(da)) {
                val row: Int = mmet.getSelectedRow()
                setA(da)
                jlu.setSelectedValue(da, true)
                if (row >= 0 && row < mmet.mm.parts.size) {
                    setB(row)
                    mmet.setRowSelectionInterval(row, row)
                }
            } else setA(null)
            callBack(null)
        })
    }

    @Synchronized
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(aep, x, y, 550, 0, 1750, 50)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jsptr, x, y, 0, 550, 300, 750)
        Page.Companion.set(jspmm, x, y, 300, 550, 2000, 750)
        Page.Companion.set(jspu, x, y, 0, 50, 300, 500)
        Page.Companion.set(mb, x, y, 300, 50, 700, 500)
        Page.Companion.set(jspp, x, y, 1000, 50, 300, 500)
        Page.Companion.set(sb, x, y, 1300, 50, 950, 400)
        Page.Companion.set(sort, x, y, 1300, 500, 200, 50)
        Page.Companion.set(revt, x, y, 1500, 500, 200, 50)
        Page.Companion.set(addl, x, y, 1700, 500, 200, 50)
        Page.Companion.set(reml, x, y, 1900, 500, 200, 50)
        Page.Companion.set(rema, x, y, 2100, 500, 200, 50)
        aep.componentResized(x, y)
        mmet.setRowHeight(Page.Companion.size(x, y, 50))
        sb.paint(sb.getGraphics())
        mb.paint(mb.getGraphics())
    }

    private fun addLine() {
        change(0, { o: Int? ->
            var ind: Int = mmet.getSelectedRow() + 1
            if (ind == 0) ind++
            val mm: MaModel = mmet.mm
            val inds = IntArray(mm.n)
            for (i in 0 until mm.n) inds[i] = if (i < ind) i else i + 1
            mmet.anim.reorderModel(inds)
            mm.n++
            val move = IntArray(mm.n)
            for (i in 0 until mm.n) move[i] = if (i < ind) i else i - 1
            mm.reorder(move)
            val newl = IntArray(14)
            newl[11] = 1000
            newl[9] = newl[11]
            newl[8] = newl[9]
            mm.parts.get(ind) = newl
            mmet.anim.unSave("mamodel add line")
            callBack(null)
            resized()
            mmet.setRowSelectionInterval(ind, ind)
            setB(ind)
            val h: Int = mmet.getRowHeight()
            mmet.scrollRectToVisible(Rectangle(0, h * ind, 1, h))
        })
    }

    private fun `addListeners$0`() {
        back.setLnr(Consumer { x: ActionEvent? -> changePanel(front) })
        jlu.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (isAdj || jlu.getValueIsAdjusting()) return
                change<AnimCE>(jlu.getSelectedValue(), Consumer<AnimCE> { `val`: AnimCE? -> setA(`val`) })
            }
        })
        jlp.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                sb.sele = jlp.getSelectedIndex()
            }
        })
        val lsm: ListSelectionModel = mmet.getSelectionModel()
        lsm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (isAdj || lsm.getValueIsAdjusting()) return
                change(lsm.getLeadSelectionIndex(), Consumer { ind: Int -> setB(ind) })
            }
        })
        addl.setLnr(Consumer { x: ActionEvent? -> addLine() })
        reml.setLnr(Consumer { x: ActionEvent? -> removeLine() })
        rema.setLnr(Consumer { x: ActionEvent? -> removeTree() })
    }

    private fun `addListeners$1`() {
        revt.setLnr(Consumer { x: ActionEvent? ->
            mmet.anim.revert()
            callBack(null)
        })
        jtr.addTreeSelectionListener(object : TreeSelectionListener {
            override fun valueChanged(arg0: TreeSelectionEvent?) {
                if (isAdj) return
                val o: Any = jtr.getLastSelectedPathComponent() ?: return
                val str = o.toString()
                val ind: Int = CommonStatic.parseIntN(str.split(" - ").toTypedArray()[0])
                if (ind == -1) return
                setB(ind)
            }
        })
        sort.addActionListener(object : ActionListener {
            private var p = 0
            private var move: IntArray
            private var inds: IntArray
            private var parts: Array<IntArray>
            override fun actionPerformed(arg0: ActionEvent?) {
                p = 0
                parts = mmet.mm.parts
                val n = parts.size
                move = IntArray(n)
                inds = IntArray(n)
                for (i in parts.indices) if (parts[i][0] == -1) add(i)
                mmet.anim.reorderModel(inds)
                mmet.mm.reorder(move)
                mmet.anim.unSave("sort")
            }

            private fun add(n: Int) {
                inds[n] = p
                move[p++] = n
                for (i in parts.indices) if (parts[i][0] == n) add(i)
            }
        })
    }

    private fun ini() {
        add(aep)
        add(back)
        add(jspu)
        add(jspp)
        add(jspmm)
        add(revt)
        add(addl)
        add(reml)
        add(rema)
        add(jsptr)
        add(sort)
        add(sb)
        add(mb)
        jlu.setCellRenderer(AnimLCR())
        jtr.setExpandsSelectedPaths(true)
        reml.setForeground(Color.RED)
        rema.setForeground(Color.RED)
        setA(null)
        `addListeners$0`()
        `addListeners$1`()
    }

    private fun removeLine() {
        var rows: IntArray = mmet.getSelectedRows()
        if (rows.size == 0) return
        if (rows[0] == 0) rows = Arrays.copyOfRange(rows, 1, rows.size)
        change(rows, { row: IntArray ->
            val mm: MaModel = mmet.mm
            val data: Array<IntArray?> = mm.parts
            mm.n -= row.size
            val inds = IntArray(data.size)
            val move = IntArray(mm.n)
            for (i in row.indices) data[row[i]] = null
            var ind = 0
            for (i in data.indices) if (data[i] != null) {
                move[ind] = i
                inds[i] = ind
                ind++
            } else inds[i] = -1
            mmet.anim.reorderModel(inds)
            mm.reorder(move)
            mmet.anim.unSave("mamodel remove line")
            callBack(null)
            if (ind >= mm.n) ind--
            mmet.setRowSelectionInterval(ind, ind)
            setB(ind)
        })
    }

    private fun removeTree() {
        change(0, { o: Int? ->
            val mm: MaModel = mmet.mm
            val rows: IntArray = mmet.getSelectedRows()
            if (rows[0] == 0) return@change
            val bs = BooleanArray(mm.n)
            var total = rows.size
            for (ind in rows) bs[ind] = true
            total += mm.getChild(bs)
            mm.clearAnim(bs, mmet.anim.anims)
            val inds = IntArray(mm.n)
            val move = IntArray(mm.n - total)
            var j = 0
            for (i in 0 until mm.n) if (!bs[i]) {
                move[j] = i
                inds[i] = j
                j++
            }
            mmet.anim.reorderModel(inds)
            mm.n -= total
            mm.reorder(move)
            mmet.anim.unSave("mamodel remove tree")
            callBack(null)
            var ind = rows[0]
            if (ind >= mm.n) ind = mm.n - 1
            if (ind >= 0) mmet.setRowSelectionInterval(ind, ind)
            setB(ind)
        })
    }

    private fun setA(anim: AnimCE?) {
        aep.setAnim(anim)
        setTree(anim)
        addl.setEnabled(anim != null)
        sort.setEnabled(anim != null)
        revt.setEnabled(anim != null)
        if (anim == null) {
            mmet.setMaModel(null)
            mb.setEntity(null)
            sb.setAnim(null)
            jlp.setListData(arrayOfNulls<String>(0))
            setB(-1)
            return
        }
        mmet.setMaModel(anim)
        mb.setEntity(EAnimS(anim, anim.mamodel))
        val ic: ImgCut = anim.imgcut
        val name = arrayOfNulls<String>(ic.n)
        for (i in 0 until ic.n) name[i] = i.toString() + " " + ic.strs.get(i)
        jlp.setListData(name)
        sb.setAnim(anim)
        mmet.clearSelection()
        setB(-1)
    }

    private fun setB(ind: Int) {
        if (mb.getEnt() != null) mb.getEnt().sele = ind
        reml.setEnabled(ind != -1)
        rema.setEnabled(ind != -1)
        if (ind < 0 || mmet.mm == null) return
        if (mmet.getSelectedRow() != ind) change(ind, { i: Int? ->
            mmet.setRowSelectionInterval(i, i)
            mmet.scrollRectToVisible(mmet.getCellRect(i, 0, true))
        })
        if (mmt != null) change(ind, { i: Int -> mmt.select(i) })
        val `val`: Int = mmet.mm.parts.get(ind).get(2)
        jlp.setSelectedIndex(`val`)
        for (row in mmet.getSelectedRows()) {
            for (ints in mmet.mm.parts) if (ints[0] == row) reml.setEnabled(false)
            for (ma in mmet.anim.anims) for (p in ma.parts) if (p.ints[0] == row) reml.setEnabled(false)
        }
    }

    private fun setTree(dat: AnimCE?) {
        change<AnimCE>(dat, Consumer<AnimCE> { anim: AnimCE? ->
            if (anim == null) {
                jtr.setModel(DefaultTreeModel(null))
                mmt = null
                return@change
            }
            if (mmt == null || mmt.anim !== anim) mmt = MMTree(null, anim, jtr)
            mmt.renew()
        })
    }

    companion object {
        private const val serialVersionUID = 1L
        private const val res = 0.95
    }
}
