package page.info.edit

import common.CommonStatic
import common.util.stage.SCDef
import common.util.stage.SCGroup
import common.util.stage.Stage
import common.util.unit.AbEnemy
import page.JBTN
import page.JTF
import page.Page
import page.support.AnimLCR
import java.awt.event.ActionEvent
import java.awt.event.FocusEvent
import java.util.function.Consumer
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class AdvStEditPage(p: Page?, private val st: Stage?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val sdef: JTF = JTF()
    private val smax: JTF = JTF()
    private val jls: JList<SCGroup> = JList<SCGroup>()
    private val jsps: JScrollPane = JScrollPane(jls)
    private val jle: JList<AbEnemy> = JList<AbEnemy>()
    private val jspe: JScrollPane = JScrollPane(jle)
    private val sget: SCGroupEditTable
    private val jspt: JScrollPane
    private val addg: JBTN = JBTN(0, "add")
    private val remg: JBTN = JBTN(0, "rem")
    private val addt: JBTN = JBTN(0, "addl")
    private val remt: JBTN = JBTN(0, "reml")
    private val data: SCDef
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jsps, x, y, 50, 100, 300, 800)
        Page.Companion.set(addg, x, y, 50, 900, 150, 50)
        Page.Companion.set(remg, x, y, 200, 900, 150, 50)
        Page.Companion.set(smax, x, y, 50, 950, 300, 50)
        Page.Companion.set(jspe, x, y, 400, 100, 300, 800)
        Page.Companion.set(sdef, x, y, 400, 900, 300, 50)
        Page.Companion.set(jspt, x, y, 750, 150, 400, 800)
        Page.Companion.set(addt, x, y, 750, 100, 200, 50)
        Page.Companion.set(remt, x, y, 950, 100, 200, 50)
        sget.setRowHeight(Page.Companion.size(x, y, 50))
    }

    private fun `addListeners$0`() {
        back.setLnr(Consumer { e: ActionEvent? -> changePanel(front) })
        jls.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (isAdj || jls.getValueIsAdjusting()) return
                setSCG(jls.getSelectedValue())
            }
        })
        addg.setLnr(Consumer { e: ActionEvent? ->
            val ind: Int = data.sub.nextInd()
            val scg = SCGroup(ind, st!!.max)
            data.sub.add(scg)
            setListG()
            setSCG(scg)
        })
        remg.setLnr(Consumer { e: ActionEvent? ->
            var ind: Int = jls.getSelectedIndex()
            data.sub.remove(jls.getSelectedValue())
            if (ind > data.sub.size()) ind--
            setListG()
            jls.setSelectedIndex(ind)
        })
        smax.setLnr(Consumer<FocusEvent> { e: FocusEvent? ->
            val `val`: Int = CommonStatic.parseIntN(smax.getText())
            val scg: SCGroup = jls.getSelectedValue()
            if (`val` > 0) scg.setMax(`val`, -1)
            setSCG(scg)
        })
        sdef.setLnr(Consumer<FocusEvent> { e: FocusEvent? ->
            val i: Int = CommonStatic.parseIntN(sdef.getText())
            if (i >= 0) data.sdef = i
            setListG()
        })
    }

    private fun `addListeners$1`() {
        addt.setLnr(Consumer { e: ActionEvent? -> sget.addLine(jle.getSelectedValue()) })
        remt.setLnr(Consumer { e: ActionEvent? -> sget.remLine() })
    }

    private fun ini() {
        val aes: Array<AbEnemy> = data.getSummon().toTypedArray()
        add(back)
        add(jsps)
        add(addg)
        add(remg)
        add(smax)
        if (aes.size > 0) {
            add(sdef)
            add(jspe)
            add(jspt)
            add(addt)
            add(remt)
        }
        jle.setCellRenderer(AnimLCR())
        jle.setListData(aes)
        sdef.setText("default: " + data.sdef)
        setListG()
        `addListeners$0`()
        `addListeners$1`()
    }

    private fun setListG() {
        var scg: SCGroup? = jls.getSelectedValue()
        val l: List<SCGroup> = data.sub.getList()
        change(0, { n: Int? ->
            jls.clearSelection()
            jls.setListData(l.toTypedArray())
        })
        sdef.setText("default: " + data.sdef)
        if (scg != null && !l.contains(scg)) scg = null
        setSCG(scg)
    }

    private fun setSCG(scg: SCGroup?) {
        if (jls.getSelectedValue() !== scg) change<SCGroup>(scg, Consumer<SCGroup> { s: SCGroup? -> jls.setSelectedValue(s, true) })
        remg.setEnabled(scg != null)
        smax.setEnabled(scg != null)
        smax.setText(if (scg != null) "max: " + scg.getMax(0) else "")
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        data = st!!.data
        sget = SCGroupEditTable(data)
        jspt = JScrollPane(sget)
        ini()
        resized()
    }
}
