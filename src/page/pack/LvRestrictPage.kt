package page.pack

import common.pack.PackData.UserPack
import common.pack.UserProfile
import common.util.Data
import common.util.stage.CharaGroup
import common.util.stage.LvRestrict
import common.util.unit.Unit
import page.JBTN
import page.Page
import page.support.UnitLCR
import utilpc.Interpret
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class LvRestrictPage(p: Page?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val cglr: JBTN = JBTN(0, "edit")
    private val jlpk: JList<UserPack> = JList<UserPack>(UserProfile.Companion.getUserPacks().toTypedArray())
    private val jllr: JList<LvRestrict> = JList<LvRestrict>()
    private val jlcg: JList<CharaGroup> = JList<CharaGroup>()
    private val jlus: JList<Unit> = JList<Unit>()
    private val jsppk: JScrollPane = JScrollPane(jlpk)
    private val jsplr: JScrollPane = JScrollPane(jllr)
    private val jspcg: JScrollPane = JScrollPane(jlcg)
    private val jspus: JScrollPane = JScrollPane(jlus)
    private val lsb = JLabel()
    private val lal = JLabel()
    private val lra = arrayOfNulls<JLabel>(Data.Companion.RARITY_TOT)
    private var changing = false
    private var pack: UserPack? = null
    var lr: LvRestrict? = null
    var cg: CharaGroup? = null

    constructor(p: Page?, lvr: LvRestrict?) : this(p) {
        lr = lvr
        pack = lr.getCont() as UserPack
        jlpk.setSelectedValue(pack, true)
        jllr.setSelectedValue(lr, true)
    }

    constructor(p: Page?, pac: UserPack?, b: Boolean) : this(p) {
        jlpk.setSelectedValue(pac, true)
        jlpk.setEnabled(b)
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jsppk, x, y, 50, 100, 400, 800)
        Page.Companion.set(cglr, x, y, 50, 950, 400, 50)
        Page.Companion.set(jsplr, x, y, 500, 100, 300, 800)
        Page.Companion.set(jspcg, x, y, 850, 100, 300, 800)
        Page.Companion.set(jspus, x, y, 1200, 100, 300, 800)
        Page.Companion.set(lal, x, y, 1550, 100, 400, 50)
        Page.Companion.set(lsb, x, y, 1550, 550, 400, 50)
        for (i in lra.indices) Page.Companion.set(lra[i], x, y, 1550, 200 + 50 * i, 400, 50)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        cglr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(CGLREditPage(getThis(), pack))
            }
        })
        jlpk.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jlpk.getValueIsAdjusting()) return
                changing = true
                pack = jlpk.getSelectedValue()
                updatePack()
                changing = false
            }
        })
        jllr.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jllr.getValueIsAdjusting()) return
                changing = true
                lr = jllr.getSelectedValue()
                updateLR()
                changing = false
            }
        })
        jlcg.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jlcg.getValueIsAdjusting()) return
                changing = true
                cg = jlcg.getSelectedValue()
                updateCG()
                changing = false
            }
        })
    }

    private fun ini() {
        add(back)
        add(jsppk)
        add(jsplr)
        add(jspcg)
        add(jspus)
        add(cglr)
        add(lsb)
        add(lal)
        lsb.border = BorderFactory.createEtchedBorder()
        lal.border = BorderFactory.createEtchedBorder()
        for (i in lra.indices) {
            add(JLabel().also { lra[i] = it })
            lra[i]!!.border = BorderFactory.createEtchedBorder()
        }
        jlus.setCellRenderer(UnitLCR())
        updatePack()
        addListeners()
    }

    private operator fun set(jl: JLabel?, str: String, lvs: IntArray?) {
        var str: String? = str
        if (lvs != null) {
            str += "Lv." + lvs[0] + " {"
            for (i in 1..4) str += lvs[i].toString() + ","
            str += lvs[5].toString() + "}"
        }
        jl!!.text = str
    }

    private fun updateCG() {
        jlus.setEnabled(lr != null)
        if (cg == null) {
            set(lsb, "group: ", null)
            jlus.setListData(arrayOfNulls<Unit>(0))
        } else {
            set(lsb, "group: ", lr.res.get(cg))
            jlus.setListData(cg.set.toTypedArray())
        }
    }

    private fun updateLR() {
        jllr.setSelectedValue(lr, true)
        jlus.setEnabled(lr != null)
        if (lr == null) {
            set(lal, "all: ", null)
            for (i in lra.indices) set(lra[i], Interpret.RARITY.get(i).toString() + ": ", null)
            jlcg.setListData(arrayOfNulls<CharaGroup>(0))
            cg = null
        } else {
            set(lal, "all: ", lr.all)
            for (i in lra.indices) set(lra[i], Interpret.RARITY.get(i).toString() + ": ", lr.rares.get(i))
            val scg: Set<CharaGroup> = lr.res.keys
            if (cg != null && !scg.contains(cg)) cg = null
            jlcg.setListData(scg.toTypedArray())
        }
        updateCG()
    }

    private fun updatePack() {
        jllr.setEnabled(pack != null)
        cglr.setEnabled(pack != null && pack.editable)
        var clr: Collection<LvRestrict?>? = null
        if (pack == null) jllr.setListData(arrayOfNulls<LvRestrict>(0)) else clr = pack.lvrs.getList()
        if (clr != null) {
            jllr.setListData(clr.toTypedArray())
            if (lr != null && !clr.contains(lr)) lr = null
        } else lr = null
        updateLR()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
        resized()
    }
}
