package page.info

import common.util.stage.RandStage
import common.util.stage.Stage
import page.JBTN
import page.Page
import page.battle.BattleSetupPage
import java.awt.event.ActionEvent
import java.util.function.Consumer
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class StageRandPage(p: Page?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val strt: JBTN = JBTN(0, "start")
    private val jls: JList<String> = JList<String>()
    private val jsps: JScrollPane = JScrollPane(jls)
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(strt, x, y, 300, 50, 200, 50)
        Page.Companion.set(jsps, x, y, 50, 100, 200, 600)
    }

    private fun addListeners() {
        back.setLnr(Consumer { e: ActionEvent? -> changePanel(front) })
        jls.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (jls.getValueIsAdjusting()) return
                if (jls.getSelectedValue() == null) jls.setSelectedIndex(0)
            }
        })
        strt.setLnr(Consumer { x: ActionEvent? ->
            val s: Int = jls.getSelectedIndex()
            val sta: Stage = RandStage.getStage(s)
            changePanel(BattleSetupPage(getThis(), sta, 0))
        })
    }

    private fun ini() {
        add(back)
        add(strt)
        add(jsps)
        val `as` = arrayOfNulls<String>(48)
        for (i in 0..47) `as`[i] = "level " + (i + 1)
        jls.setListData(`as`)
        val ts = arrayOfNulls<String>(5)
        for (i in 0..4) ts[i] = "attempt " + (i + 1)
        jls.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
        jls.setSelectedIndex(0)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
        resized()
    }
}
