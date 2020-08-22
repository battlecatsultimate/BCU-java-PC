package page.basis

import common.CommonStatic
import common.battle.BasisLU
import common.battle.BasisSet
import common.battle.LineUp
import common.system.Node
import common.util.pack.NyCastle
import common.util.unit.Combo
import common.util.unit.Form
import page.JBTN
import page.JTF
import page.Page
import page.info.TreaTable
import page.support.ReorderList
import page.support.ReorderListener
import page.support.UnitLCR
import utilpc.Interpret
import utilpc.UtilPC
import java.awt.Color
import java.awt.Point
import java.awt.Rectangle
import java.awt.event.*
import java.util.*
import java.util.function.Consumer
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class BasisPage(p: Page?) : LubCont(p) {
    private val back: JBTN = JBTN(0, "back")
    private val unit: JBTN = JBTN(0, "vuif")
    private val setc: JBTN = JBTN(0, "set0")
    private val bsadd: JBTN = JBTN(0, "add")
    private val bsrem: JBTN = JBTN(0, "rem")
    private val bscop: JBTN = JBTN(0, "copy")
    private val badd: JBTN = JBTN(0, "add")
    private val brem: JBTN = JBTN(0, "rem")
    private val form: JBTN = JBTN(0, "form")
    private val bsjtf: JTF = JTF()
    private val bjtf: JTF = JTF()
    private val lvjtf: JTF = JTF()
    private val lvorb: JBTN = JBTN(0, "orb")
    private val pcoin = JLabel()
    private val vbs: Vector<BasisSet> = Vector<BasisSet>(BasisSet.Companion.list())
    private val jlbs: ReorderList<BasisSet> = ReorderList<BasisSet>(vbs)
    private val jspbs: JScrollPane = JScrollPane(jlbs)
    private val vb: Vector<BasisLU> = Vector<BasisLU>()
    private val jlb: ReorderList<BasisLU> = ReorderList<BasisLU>(vb, BasisLU::class.java, "lineup")
    private val jspb: JScrollPane = JScrollPane(jlb)
    private val jlcs: JList<String> = JList<String>(Interpret.COMF)
    private val jspcs: JScrollPane = JScrollPane(jlcs)
    private val jlcl: JList<String> = JList<String>()
    private val jspcl: JScrollPane = JScrollPane(jlcl)
    private val jlc: ComboListTable = ComboListTable(this, lu())
    private val jspc: JScrollPane = JScrollPane(jlc)
    private val jlcn: ComboList = ComboList()
    private val jspcn: JScrollPane = JScrollPane(jlcn)
    private val lub: LineUpBox = LineUpBox(this)
    private val ul: JList<Form> = JList<Form>()
    private val jspul: JScrollPane = JScrollPane(ul)
    private val ncb: NyCasBox = NyCasBox()
    private val jbcs: Array<JBTN?> = arrayOfNulls<JBTN>(3)
    private var changing = false
    private var outside = false
    private var ufp: UnitFLUPage? = null
    private val trea: TreaTable = TreaTable(this, BasisSet.Companion.current())
    override fun callBack(o: Any?) {
        if (o == null) changeLU() else if (o is Form) {
            val f = o
            val u = f.unit
            setLvs(f)
            val lc: List<Combo> = u.allCombo()
            if (lc.size == 0) return
            outside = true
            changing = true
            jlc.setList(lc)
            jlc.selectionModel.setSelectionInterval(0, 0)
            setC(0)
            changing = false
        }
    }

    override fun getLub(): LineUpBox {
        return lub
    }

    protected override fun keyTyped(e: KeyEvent) {
        if (trea.hasFocus()) return
        if (lvjtf.hasFocus()) return
        if (bjtf.hasFocus()) return
        if (bsjtf.hasFocus()) return
        super.keyTyped(e)
        e.consume()
    }

    protected override fun mouseClicked(e: MouseEvent) {
        if (e.source === jlc) jlc.clicked(e.point)
        super.mouseClicked(e)
    }

    protected override fun renew() {
        if (ufp != null) {
            val lf: List<Form> = ufp.getList()
            if (lf != null) ul.setListData(Node.Companion.deRep(lf).toTypedArray())
        }
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jspbs, x, y, 50, 100, 200, 300)
        Page.Companion.set(bsadd, x, y, 50, 450, 200, 50)
        Page.Companion.set(bsrem, x, y, 50, 550, 200, 50)
        Page.Companion.set(bscop, x, y, 50, 650, 200, 50)
        Page.Companion.set(bsjtf, x, y, 50, 750, 200, 50)
        Page.Companion.set(jspb, x, y, 750, 50, 200, 500)
        Page.Companion.set(badd, x, y, 750, 550, 200, 50)
        Page.Companion.set(brem, x, y, 750, 600, 200, 50)
        Page.Companion.set(bjtf, x, y, 750, 700, 200, 50)
        Page.Companion.set(ncb, x, y, 750, 800, 200, 300)
        Page.Companion.set(trea, x, y, 300, 50, 400, 1200)
        Page.Companion.set(lub, x, y, 1000, 100, 600, 300)
        Page.Companion.set(unit, x, y, 1650, 0, 200, 50)
        Page.Companion.set(jspcs, x, y, 1950, 0, 300, 250)
        Page.Companion.set(jspcl, x, y, 1950, 300, 300, 450)
        Page.Companion.set(jspc, x, y, 1050, 800, 1200, 450)
        Page.Companion.set(jspcn, x, y, 1300, 450, 300, 300)
        Page.Companion.set(jspul, x, y, 1650, 50, 200, 700)
        Page.Companion.set(form, x, y, 1000, 400, 200, 50)
        Page.Companion.set(pcoin, x, y, 1000, 0, 600, 50)
        Page.Companion.set(lvjtf, x, y, 1000, 50, 350, 50)
        Page.Companion.set(lvorb, x, y, 1350, 50, 250, 50)
        Page.Companion.set(setc, x, y, 1050, 700, 200, 50)
        for (i in 0..2) Page.Companion.set(jbcs[i], x, y, 750, 1100 + 50 * i, 200, 50)
        jlc.rowHeight = 85
        jlc.columnModel.getColumn(1).preferredWidth = Page.Companion.size(x, y, 300)
    }

    protected override fun timer(t: Int) {
        ncb.paint(ncb.getGraphics())
        super.timer(t)
    }

    private fun `addListeners$0`() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(getFront())
            }
        })
        unit.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                if (ufp == null) ufp = UnitFLUPage(getThis())
                changePanel(ufp)
            }
        })
        ul.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                changing = true
                lub.select(ul.selectedValue)
                changing = false
            }
        })
        form.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                lub.adjForm()
                changeLU()
            }
        })
        lvjtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(arg0: FocusEvent?) {
                val lv: IntArray = CommonStatic.parseIntsN(lvjtf.text)
                lub.setLv(lv)
                if (lub.sf != null) setLvs(lub.sf)
            }
        })
        lvorb.setLnr(Consumer { x: ActionEvent? ->
            if (lub.sf != null) {
                changePanel(LevelEditPage(this, lu().getLv(lub.sf.unit), lub.sf))
            }
        })
        for (i in 0..2) {
            jbcs[i].addActionListener(object : ActionListener {
                override fun actionPerformed(e: ActionEvent?) {
                    BasisSet.Companion.current().sele.nyc.get(i)++
                    BasisSet.Companion.current().sele.nyc.get(i) %= NyCastle.Companion.TOT
                }
            })
        }
    }

    private fun `addListeners$1`() {
        jlbs.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (jlb.valueIsAdjusting || changing) return
                changing = true
                if (jlbs.selectedValue == null) jlbs.setSelectedValue(BasisSet.Companion.current(), true) else setBS(jlbs.selectedValue)
                changing = false
            }
        })
        jlb.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (jlb.valueIsAdjusting || changing) return
                changing = true
                if (jlb.selectedValue == null) jlb.setSelectedValue(BasisSet.Companion.current().sele, true) else setB(jlb.selectedValue)
                changing = false
            }
        })
        jlbs.list = object : ReorderListener<BasisSet?> {
            override fun reordered(ori: Int, fin: Int) {
                changing = false
                val l: List<BasisSet> = BasisSet.Companion.list()
                val b: BasisSet = l.removeAt(ori)
                l.add(fin, b)
            }

            override fun reordering() {
                changing = true
            }
        }
        jlb.list = object : ReorderListener<BasisLU?> {
            override fun add(blu: BasisLU?): Boolean {
                BasisSet.Companion.current().lb.add(blu)
                return true
            }

            override fun reordered(ori: Int, fin: Int) {
                changing = false
                val l: List<BasisLU> = BasisSet.Companion.current().lb
                val b: BasisLU = l.removeAt(ori)
                l.add(fin, b)
            }

            override fun reordering() {
                changing = true
            }
        }
        bsadd.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val b = BasisSet()
                vbs.clear()
                vbs.addAll(BasisSet.Companion.list())
                jlbs.setListData(vbs)
                jlbs.setSelectedValue(b, true)
                setBS(b)
                changing = false
            }
        })
        bsrem.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                BasisSet.Companion.list().remove(BasisSet.Companion.current())
                vbs.clear()
                vbs.addAll(BasisSet.Companion.list())
                jlbs.setListData(vbs)
                val b: BasisSet = BasisSet.Companion.list().get(BasisSet.Companion.list().size - 1)
                jlbs.setSelectedValue(b, true)
                setBS(b)
                changing = false
            }
        })
        bscop.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val b = BasisSet(BasisSet.Companion.current())
                vbs.clear()
                vbs.addAll(BasisSet.Companion.list())
                jlbs.setListData(vbs)
                jlbs.setSelectedValue(b, true)
                setBS(b)
                changing = false
            }
        })
        badd.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val b: BasisLU = BasisSet.Companion.current().add()
                vb.clear()
                vb.addAll(BasisSet.Companion.current().lb)
                jlb.setListData(vb)
                jlb.setSelectedValue(b, true)
                setB(b)
                changing = false
            }
        })
        brem.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val b: BasisLU = BasisSet.Companion.current().remove()
                vb.clear()
                vb.addAll(BasisSet.Companion.current().lb)
                jlb.setListData(vb)
                jlb.setSelectedValue(b, true)
                setB(b)
                changing = false
            }
        })
        bsjtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(arg0: FocusEvent?) {
                val str: String = bsjtf.text.trim { it <= ' ' }
                if (str.length > 0) BasisSet.Companion.current().name = str
                bsjtf.text = BasisSet.Companion.current().name
                jlbs.repaint()
            }
        })
        bjtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(arg0: FocusEvent?) {
                val str: String = bjtf.text.trim { it <= ' ' }
                if (str.length > 0) BasisSet.Companion.current().sele.name = str
                bjtf.text = BasisSet.Companion.current().sele.name
                jlb.repaint()
            }
        })
    }

    private fun `addListeners$2`() {
        jlcs.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent) {
                if (changing || e.valueIsAdjusting) return
                changing = true
                if (jlcs.selectedValue == null) jlcs.selectedIndex = 0
                setCS(jlcs.selectedIndex)
                changing = false
            }
        })
        jlcl.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent) {
                if (changing || e.valueIsAdjusting) return
                changing = true
                setCL(jlcs.selectedIndex)
                changing = false
            }
        })
        jlcn.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent) {
                if (changing || e.valueIsAdjusting) return
                changing = true
                setCN()
                changing = false
            }
        })
        jlc.selectionModel.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (changing || arg0.valueIsAdjusting) return
                changing = true
                setC(jlc.selectedRow)
                changing = false
            }
        })
        setc.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                lu().set(jlc.list.get(jlc.selectedRow).units)
                changeLU()
            }
        })
    }

    private fun changeLU() {
        jlcn.setListData(lu().coms.toTypedArray())
        setCN()
        updateSetC()
        lub.updateLU()
        setLvs(lub.sf)
    }

    private fun ini() {
        add(back)
        add(jspbs)
        add(jspb)
        add(trea)
        add(trea)
        add(bsadd)
        add(bsrem)
        add(bscop)
        add(badd)
        add(brem)
        add(lub)
        add(back)
        add(unit)
        add(jspcs)
        add(jspcl)
        add(jspc)
        add(jspcn)
        add(lub)
        add(jspul)
        add(setc)
        add(bsjtf)
        add(bjtf)
        add(form)
        add(lvjtf)
        add(pcoin)
        add(lvorb)
        add(ncb)
        add(JBTN(0, "ctop").also { jbcs[0] = it })
        add(JBTN(0, "cmid").also { jbcs[1] = it })
        add(JBTN(0, "cbas").also { jbcs[2] = it })
        ul.cellRenderer = UnitLCR()
        val m0: Int = ListSelectionModel.SINGLE_SELECTION
        val m1: Int = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        jlbs.setSelectedValue(BasisSet.Companion.current(), true)
        jlcs.selectedIndex = 0
        jlbs.selectionMode = m0
        jlb.selectionMode = m0
        jlcs.selectionMode = m0
        jlcl.selectionMode = m1
        jlcn.selectionMode = m0
        jlc.selectionModel.selectionMode = m0
        setCS(0)
        setBS(BasisSet.Companion.current())
        lub.setLU(lu())
        bsjtf.text = BasisSet.Companion.current().name
        bjtf.text = BasisSet.Companion.current().sele.name
        changeLU()
        `addListeners$0`()
        `addListeners$1`()
        `addListeners$2`()
        lvorb.isEnabled = lub.sf != null
    }

    private fun lu(): LineUp {
        return BasisSet.Companion.current().sele.lu
    }

    private fun setB(b: BasisLU) {
        BasisSet.Companion.current().sele = b
        lub.setLU(if (b == null) null else b.lu)
        brem.isEnabled = BasisSet.Companion.current().lb.size > 1
        bjtf.text = BasisSet.Companion.current().sele.name
        ncb.set(b.nyc)
        changeLU()
        callBack(lub.sf)
    }

    private fun setBS(bs: BasisSet) {
        BasisSet.Companion.setCurrent(bs)
        vb.clear()
        vb.addAll(bs.lb)
        jlb.setListData(vb)
        val b: BasisLU = bs.sele
        jlb.setSelectedValue(b, true)
        trea.setBasis(bs)
        bsjtf.text = BasisSet.Companion.current().name
        bsrem.isEnabled = BasisSet.Companion.current() !== BasisSet.Companion.def()
        setB(b)
    }

    private fun setC(c: Int) {
        if (outside) {
            jlcs.selectedIndex = 0
            jlcl.setListData(Interpret.getComboFilter(0))
            val row: Int = jlc.list.get(c).type
            jlcl.selectedIndex = row
            val p: Point = jlcl.indexToLocation(row)
            val h: Int = jlcl.indexToLocation(1).y - jlcl.indexToLocation(0).y
            if (p != null) jlcl.scrollRectToVisible(Rectangle(p.x, p.y, 1, h))
        }
        updateSetC()
    }

    private fun setCL(cs: Int) {
        val cls: IntArray = jlcl.selectedIndices
        if (cls.size == 0) {
            val lc: MutableList<Combo> = ArrayList<Combo>()
            for (i in 0 until CommonStatic.getBCAssets().filter.get(cs).length) for (c in CommonStatic.getBCAssets().combos.get(CommonStatic.getBCAssets().filter.get(cs).get(i))) lc.add(c)
            jlc.setList(lc)
        } else {
            val lc: MutableList<Combo> = ArrayList<Combo>()
            for (`val` in cls) for (c in CommonStatic.getBCAssets().combos.get(CommonStatic.getBCAssets().filter.get(cs).get(`val`))) lc.add(c)
            jlc.setList(lc)
        }
        jlc.selectionModel.setSelectionInterval(0, 0)
        outside = false
        setC(0)
    }

    private fun setCN() {
        lub.select(jlcn.selectedValue)
    }

    private fun setCS(cs: Int) {
        jlcl.setListData(Interpret.getComboFilter(cs))
        jlcl.selectedIndex = 0
        setCL(cs)
    }

    private fun setLvs(f: Form?) {
        lvorb.isEnabled = f != null
        if (f == null) {
            lvjtf.text = ""
            pcoin.text = ""
            return
        }
        lvorb.isVisible = f.orbs != null
        val strs: Array<String> = UtilPC.lvText(f, lu().getLv(f.unit).getLvs())
        lvjtf.text = strs[0]
        pcoin.text = strs[1]
    }

    private fun updateSetC() {
        val com: Combo? = if (jlc.list.size > 0) jlc.list.get(jlc.selectedRow) else null
        setc.isEnabled = com != null && !lu().contains(com)
        var b = false
        if (com != null) b = lu().willRem(com)
        setc.foreground = if (b) Color.RED else Color.BLACK
        setc.setText(0, "set" + if (b) "1" else "0")
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
        resized()
    }
}
