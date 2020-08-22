package page.pack

import common.pack.PackData
import common.pack.PackData.UserPack
import common.pack.UserProfile
import common.util.stage.CharaGroup
import common.util.unit.Unit
import page.JBTN
import page.JL
import page.Page
import page.support.UnitLCR
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class CharaGroupPage(p: Page?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val cglr: JBTN = JBTN(0, "edit")
    private val jlpk: JList<PackData> = JList<PackData>(UserProfile.Companion.getAllPacks().toTypedArray())
    private val jlcg: JList<CharaGroup> = JList<CharaGroup>()
    private val jlus: JList<Unit> = JList<Unit>()
    private val jsppk: JScrollPane = JScrollPane(jlpk)
    private val jspcg: JScrollPane = JScrollPane(jlcg)
    private val jspus: JScrollPane = JScrollPane(jlus)
    private val cgt: JL = JL(0, "include")
    private var changing = false
    private var pack: PackData? = null
    var cg: CharaGroup? = null

    constructor(p: Page?, chg: CharaGroup?) : this(p) {
        cg = chg
        pack = cg.getCont()
        jlpk.setSelectedValue(pack, true)
        jlcg.setSelectedValue(cg, true)
    }

    constructor(p: Page?, pac: PackData?, b: Boolean) : this(p) {
        jlpk.setSelectedValue(pac, true)
        jlpk.isEnabled = b
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jsppk, x, y, 50, 100, 400, 800)
        Page.Companion.set(cglr, x, y, 50, 950, 400, 50)
        Page.Companion.set(jspcg, x, y, 500, 100, 300, 800)
        Page.Companion.set(cgt, x, y, 850, 100, 300, 50)
        Page.Companion.set(jspus, x, y, 850, 150, 300, 750)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        cglr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(CGLREditPage(getThis(), pack as UserPack?))
            }
        })
        jlpk.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jlpk.valueIsAdjusting) return
                changing = true
                pack = jlpk.selectedValue
                updatePack()
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
    }

    private fun ini() {
        add(back)
        add(jsppk)
        add(jspcg)
        add(jspus)
        add(cgt)
        add(cglr)
        jlus.cellRenderer = UnitLCR()
        updatePack()
        addListeners()
    }

    private fun updateCG() {
        jlcg.setSelectedValue(cg, true)
        jlus.isEnabled = cg != null
        if (cg == null) jlus.setListData(arrayOfNulls<Unit>(0)) else {
            jlus.setListData(cg.set.toTypedArray())
            cgt.setText(0, if (cg.type == 0) "include" else "exclude")
        }
    }

    private fun updatePack() {
        jlcg.isEnabled = pack != null
        cglr.isEnabled = pack != null && pack is UserPack && (pack as UserPack).editable
        var ccg: Collection<CharaGroup?>? = null
        if (pack == null) jlcg.setListData(arrayOfNulls<CharaGroup>(0))
        ccg = pack.groups.getList()
        if (ccg != null) {
            jlcg.setListData(ccg.toTypedArray())
            if (cg != null && !ccg.contains(cg)) cg = null
        } else cg = null
        updateCG()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
        resized()
    }
}
