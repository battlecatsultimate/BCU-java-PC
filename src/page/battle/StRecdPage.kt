package page.battle

import common.util.stage.Recd
import common.util.stage.Stage
import main.Opts
import page.JBTN
import page.JTF
import page.Page
import page.support.ReorderList
import page.support.ReorderListener
import java.awt.event.ActionEvent
import java.awt.event.FocusEvent
import java.util.function.Consumer
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class StRecdPage(p: Page?, protected val st: Stage, edit: Boolean) : AbRecdPage(p, edit) {
    private val list: ReorderList<Recd> = ReorderList<Recd>()
    private val jsp: JScrollPane = JScrollPane(list)
    private val addr: JBTN = JBTN(0, "add")
    private val remr: JBTN = JBTN(0, "rem")
    private val jtf: JTF = JTF()
    private var rmp: RecdManagePage? = null
    override fun getSelection(): Recd {
        return list.getSelectedValue()
    }

    protected override fun renew() {
        super.renew()
        if (rmp != null) {
            val recd: Recd = rmp.getSelection()
            if (recd != null) {
                if (recd.st === st || Opts.conf("stage mismatch. Do you really want to add?")) {
                    st.recd.add(recd)
                    setList()
                }
            }
        }
        rmp = null
    }

    protected override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(jsp, x, y, 50, 100, 500, 1100)
        Page.Companion.set(addr, x, y, 600, 400, 300, 50)
        Page.Companion.set(remr, x, y, 950, 400, 300, 50)
        Page.Companion.set(jtf, x, y, 600, 500, 300, 50)
    }

    protected override fun setList() {
        change(true)
        val r: Recd = list.getSelectedValue()
        list.setListData(st.recd.toTypedArray())
        if (st.recd.contains(r)) list.setSelectedValue(r, true)
        setRecd(list.getSelectedValue())
        addr.setEnabled(editable)
        change(false)
    }

    protected override fun setRecd(r: Recd?) {
        super.setRecd(r)
        remr.setEnabled(editable && r != null)
        jtf.setEditable(editable && r != null)
        jtf.setText(if (r == null) "" else r.name)
    }

    private fun addListeners() {
        addr.setLnr(Consumer { e: ActionEvent? -> changePanel(RecdManagePage(this).also { rmp = it }) })
        remr.setLnr(Consumer { e: ActionEvent? ->
            val recd: Recd = list.getSelectedValue()
            st.recd.remove(recd)
            setList()
        })
        list.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (isAdj() || list.getValueIsAdjusting()) return
                setRecd(list.getSelectedValue())
            }
        })
        list.list = object : ReorderListener<Recd?> {
            override fun reordered(ori: Int, fin: Int) {
                change(false)
                val l: List<Recd> = st.recd
                val sta: Recd = l.removeAt(ori)
                l.add(fin, sta)
            }

            override fun reordering() {
                change(true)
            }
        }
        jtf.setLnr(Consumer<FocusEvent> { x: FocusEvent? ->
            val r: Recd = list.getSelectedValue()
            if (isAdj() || r == null) return@setLnr
            r.name = jtf.getText()
        })
    }

    private fun ini() {
        add(jsp)
        add(addr)
        add(remr)
        add(jtf)
        setList()
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        preini()
        ini()
        resized()
    }
}
