package page.pack

import common.CommonStatic
import common.pack.FixIndexList
import common.pack.PackData.UserPack
import common.util.Data
import common.util.stage.CharaGroup
import common.util.stage.LvRestrict
import common.util.unit.Unit
import page.JBTN
import page.JTF
import page.Page
import page.info.filter.UnitFindPage
import page.support.UnitLCR
import utilpc.Interpret
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.util.*
import java.util.function.Consumer
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class CGLREditPage(p: Page?, pac: UserPack?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val jlcg: JList<CharaGroup> = JList<CharaGroup>()
    private val jlsb: JList<CharaGroup> = JList<CharaGroup>()
    private val jllr: JList<LvRestrict> = JList<LvRestrict>()
    private val jlus: JList<Unit> = JList<Unit>()
    private val jlua: JList<Unit> = JList<Unit>()
    private val jspcg: JScrollPane = JScrollPane(jlcg)
    private val jspsb: JScrollPane = JScrollPane(jlsb)
    private val jsplr: JScrollPane = JScrollPane(jllr)
    private val jspus: JScrollPane = JScrollPane(jlus)
    private val jspua: JScrollPane = JScrollPane(jlua)
    private val cgt: JBTN = JBTN(0, "include")
    private val addcg: JBTN = JBTN(0, "add")
    private val remcg: JBTN = JBTN(0, "rem")
    private val addus: JBTN = JBTN(0, "add")
    private val remus: JBTN = JBTN(0, "rem")
    private val addlr: JBTN = JBTN(0, "add")
    private val remlr: JBTN = JBTN(0, "rem")
    private val addsb: JBTN = JBTN(0, "add")
    private val remsb: JBTN = JBTN(0, "rem")
    private val jtfsb: JTF = JTF()
    private val jtfal: JTF = JTF()
    private val jtfra: Array<JTF?> = arrayOfNulls<JTF>(Data.Companion.RARITY_TOT)
    private val jtfna: JTF = JTF()
    private val jtflr: JTF = JTF()
    private val vuif: JBTN = JBTN(0, "vuif")
    private val pack: UserPack?
    private val lcg: FixIndexList<CharaGroup>
    private val llr: FixIndexList<LvRestrict>
    private var changing = false
    private var cg: CharaGroup? = null
    private var sb: CharaGroup? = null
    private var lr: LvRestrict? = null
    private var ufp: UnitFindPage? = null
    override fun renew() {
        if (ufp != null && ufp.getList() != null) {
            changing = true
            val list: MutableList<Unit> = ArrayList()
            for (f in ufp.getList()) if (!list.contains(f.unit)) list.add(f.unit)
            jlua.setListData(list.toTypedArray())
            jlua.clearSelection()
            if (list.size > 0) jlua.selectedIndex = 0
            changing = false
        }
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jspcg, x, y, 50, 100, 300, 800)
        Page.Companion.set(addcg, x, y, 50, 950, 150, 50)
        Page.Companion.set(remcg, x, y, 200, 950, 150, 50)
        Page.Companion.set(cgt, x, y, 400, 100, 300, 50)
        Page.Companion.set(jspus, x, y, 400, 200, 300, 700)
        Page.Companion.set(addus, x, y, 400, 950, 150, 50)
        Page.Companion.set(remus, x, y, 550, 950, 150, 50)
        Page.Companion.set(vuif, x, y, 750, 100, 300, 50)
        Page.Companion.set(jspua, x, y, 750, 200, 300, 700)
        Page.Companion.set(jsplr, x, y, 1100, 100, 300, 800)
        Page.Companion.set(addlr, x, y, 1100, 950, 150, 50)
        Page.Companion.set(remlr, x, y, 1250, 950, 150, 50)
        Page.Companion.set(jspsb, x, y, 1450, 100, 300, 800)
        Page.Companion.set(addsb, x, y, 1450, 950, 150, 50)
        Page.Companion.set(remsb, x, y, 1600, 950, 150, 50)
        Page.Companion.set(jtfal, x, y, 1800, 100, 400, 50)
        Page.Companion.set(jtfsb, x, y, 1800, 550, 400, 50)
        Page.Companion.set(jtfna, x, y, 50, 900, 300, 50)
        Page.Companion.set(jtflr, x, y, 1100, 900, 300, 50)
        for (i in jtfra.indices) Page.Companion.set(jtfra[i], x, y, 1800, 200 + 50 * i, 400, 50)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        vuif.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (ufp == null) ufp = UnitFindPage(getThis(), pack.desc.id)
                changePanel(ufp)
            }
        })
    }

    private fun `addListeners$CG`() {
        addcg.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                cg = CharaGroup(pack.getNextID<CharaGroup, CharaGroup>(CharaGroup::class.java))
                lcg.add(cg)
                updateCGL()
                jlcg.setSelectedValue(cg, true)
                changing = false
            }
        })
        remcg.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (cg == null) return
                changing = true
                val list: MutableList<CharaGroup> = lcg.getList()
                var ind = list.indexOf(cg) - 1
                if (ind < 0 && list.size > 1) ind = 0
                list.remove(cg)
                lcg.remove(cg)
                cg = if (ind >= 0) list[ind] else null
                updateCGL()
                changing = false
            }
        })
        jlcg.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jlcg.valueIsAdjusting) return
                changing = true
                cg = jlcg.selectedValue
                updateCG()
                changing = false
            }
        })
        addus.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val u: List<Unit> = jlua.selectedValuesList
                if (cg == null || u.size == 0) return
                changing = true
                cg.set.addAll(u)
                updateCG()
                jlus.setSelectedValue(u[0], true)
                changing = false
            }
        })
        remus.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val u: Unit = jlus.selectedValue
                if (cg == null || u == null) return
                changing = true
                val list: MutableList<Unit> = ArrayList()
                list.addAll(cg.set)
                var ind = list.indexOf(u) - 1
                if (ind < 0 && list.size > 1) ind = 0
                cg.set.remove(u)
                updateCG()
                jlus.selectedIndex = ind
                changing = false
            }
        })
        cgt.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (cg == null) return
                cg.type = 2 - cg.type
                cgt.setText(0, if (cg.type == 0) "include" else "exclude")
            }
        })
        jtfna.setLnr(Consumer<FocusEvent> { x: FocusEvent? ->
            val str: String = jtfna.text
            if (cg.name == str) return@setLnr
            cg.name = str
        })
    }

    private fun `addListeners$LR`() {
        addlr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                lr = LvRestrict(pack.getNextID<LvRestrict, LvRestrict>(LvRestrict::class.java))
                llr.add(lr)
                updateLRL()
                jllr.setSelectedValue(lr, true)
                changing = false
            }
        })
        remlr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (lr == null) return
                changing = true
                val list: MutableList<LvRestrict> = llr.getList()
                var ind = list.indexOf(lr) - 1
                if (ind < 0 && list.size > 1) ind = 0
                list.remove(lr)
                llr.remove(lr)
                lr = if (ind >= 0) list[ind] else null
                updateLRL()
                changing = false
            }
        })
        jllr.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jllr.valueIsAdjusting) return
                changing = true
                lr = jllr.selectedValue
                updateLR()
                changing = false
            }
        })
        addsb.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val lv = intArrayOf(120, 10, 10, 10, 10, 10)
                lr.res.put(cg, lv)
                sb = cg
                updateLR()
                changing = false
            }
        })
        remsb.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (sb == null) return
                changing = true
                var ind: Int = jlsb.selectedIndex
                lr.res.remove(sb)
                updateLR()
                if (lr.res.size >= ind) ind = lr.res.size - 1
                jlsb.selectedIndex = ind
                sb = jlsb.selectedValue
                updateSB()
                changing = false
            }
        })
        jlsb.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jlsb.valueIsAdjusting) return
                changing = true
                sb = jlsb.selectedValue
                updateSB()
                changing = false
            }
        })
        jtflr.setLnr(Consumer<FocusEvent> { x: FocusEvent? ->
            val str: String = jtflr.text
            if (lr.name == str) return@setLnr
            lr.name = str
        })
    }

    private fun ini() {
        add(back)
        add(jspcg)
        add(addcg)
        add(remcg)
        add(jspus)
        add(addus)
        add(remus)
        add(jsplr)
        add(addlr)
        add(remlr)
        add(jspsb)
        add(addsb)
        add(remsb)
        add(vuif)
        add(jspua)
        add(cgt)
        set(jtfsb)
        set(jtfal)
        set(jtfna)
        set(jtflr)
        for (i in jtfra.indices) set(JTF().also { jtfra[i] = it })
        jlus.cellRenderer = UnitLCR()
        jlua.setCellRenderer(UnitLCR())
        updateCGL()
        updateLRL()
        addListeners()
        `addListeners$CG`()
        `addListeners$LR`()
    }

    private fun put(tar: IntArray, `val`: IntArray) {
        for (i in 0 until Math.min(tar.size, `val`.size)) tar[i] = `val`[i]
    }

    private fun set(jtf: JTF) {
        add(jtf)
        jtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(fe: FocusEvent?) {
                val inp: IntArray = CommonStatic.parseIntsN(jtf.text)
                for (i in inp.indices) if (inp[i] < 0) inp[i] = 0
                if (jtf === jtfal) put(lr.all, inp)
                if (jtf === jtfsb) put(lr.res.get(sb), inp)
                for (i in jtfra.indices) if (jtf === jtfra[i]) put(lr.rares.get(i), inp)
                updateSB()
            }
        })
    }

    private operator fun set(jtf: JTF?, str: String, lvs: IntArray?) {
        var str: String? = str
        if (lvs != null) {
            str += "Lv." + lvs[0] + " {"
            for (i in 1..4) str += lvs[i].toString() + ","
            str += lvs[5].toString() + "}"
        }
        jtf.setText(str)
    }

    private fun updateCG() {
        jlus.isEnabled = cg != null
        addus.isEnabled = cg != null
        remus.isEnabled = cg != null
        remcg.isEnabled = cg != null && !cg.used()
        cgt.isEnabled = cg != null
        jtfna.isEnabled = cg != null
        cgt.setText("")
        jtfna.text = ""
        addsb.isEnabled = lr != null && cg != null && !lr.res.containsKey(cg)
        if (cg == null) jlus.setListData(arrayOfNulls<Unit>(0)) else {
            jlus.setListData(cg.set.toTypedArray())
            cgt.setText(0, if (cg.type == 0) "include" else "exclude")
            jtfna.text = cg.name
        }
    }

    private fun updateCGL() {
        jlcg.setListData(lcg.getList().toTypedArray())
        jlcg.setSelectedValue(cg, true)
        updateCG()
    }

    private fun updateLR() {
        remlr.isEnabled = lr != null && !lr.used()
        jlsb.isEnabled = lr != null
        addsb.isEnabled = lr != null && cg != null && !lr.res.containsKey(cg)
        jtflr.isEnabled = lr != null
        jtflr.text = ""
        if (lr == null) jlsb.setListData(arrayOfNulls<CharaGroup>(0)) else {
            jlsb.setListData(lr.res.keys.toTypedArray())
            jtflr.text = lr.name
        }
        if (lr == null || sb == null || !lr.res.containsKey(sb)) sb = null
        jlsb.setSelectedValue(sb, true)
        jtfal.isEnabled = lr != null
        for (jtf in jtfra) jtf.setEnabled(lr != null)
        updateSB()
    }

    private fun updateLRL() {
        jllr.setListData(llr.getList().toTypedArray())
        jllr.setSelectedValue(lr, true)
        updateLR()
    }

    private fun updateSB() {
        jtfsb.isEnabled = sb != null
        if (lr != null) {
            set(jtfal, "all: ", lr.all)
            for (i in jtfra.indices) set(jtfra[i], Interpret.RARITY.get(i).toString() + ": ", lr.rares.get(i))
        } else {
            set(jtfal, "all: ", null)
            for (i in jtfra.indices) set(jtfra[i], Interpret.RARITY.get(i).toString() + ": ", null)
        }
        if (lr == null || sb == null) set(jtfsb, "group: ", null) else set(jtfsb, "group: ", lr.res.get(sb))
        remsb.isEnabled = sb != null
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        pack = pac
        lcg = pack.groups
        llr = pack.lvrs
        ini()
        resized()
    }
}
