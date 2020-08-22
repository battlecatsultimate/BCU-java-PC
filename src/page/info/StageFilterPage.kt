package page.info

import common.util.stage.Stage
import page.Page
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class StageFilterPage(p: Page?, ls: List<Stage?>) : StagePage(p) {
    private val jlst: JList<Stage> = JList<Stage>()
    private val jspst: JScrollPane = JScrollPane(jlst)
    protected override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(jspst, x, y, 400, 550, 300, 650)
        Page.Companion.set(strt, x, y, 400, 0, 300, 50)
    }

    private fun addListeners() {
        jlst.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.getValueIsAdjusting()) return
                val s: Stage = jlst.getSelectedValue() ?: return
                setData(s)
            }
        })
    }

    private fun ini() {
        add(jspst)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        jlst.setListData(ls.toTypedArray())
        ini()
        resized()
    }
}
