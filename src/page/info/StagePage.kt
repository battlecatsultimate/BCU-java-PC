package page.info

import common.util.stage.Stage
import page.JBTN
import page.Page
import page.battle.BattleSetupPage
import java.awt.Rectangle
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import javax.swing.JScrollPane

open class StagePage(p: Page?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    protected val strt: JBTN = JBTN(0, "start")
    private val jt: StageTable = StageTable(this)
    private val jspjt: JScrollPane = JScrollPane(jt)
    private val info = HeadTable(this)
    private val jspinfo: JScrollPane = JScrollPane(info)
    protected var stage: Stage? = null
    fun getStage(): Stage? {
        return stage
    }

    override fun mouseClicked(e: MouseEvent) {
        if (e.source === jt) jt.clicked(e.point)
        if (e.source === info) info.clicked(e.point)
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jspinfo, x, y, 800, 50, 1400, 250)
        Page.Companion.set(jspjt, x, y, 800, 350, 1400, 850)
        jt.rowHeight = Page.Companion.size(x, y, 50)
        info.rowHeight = Page.Companion.size(x, y, 50)
    }

    protected open fun setData(st: Stage?) {
        stage = st
        strt.isEnabled = st != null
        info.setData(st)
        jt.setData(st)
        jspjt.scrollRectToVisible(Rectangle(0, 0, 1, 1))
        resized()
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        strt.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (stage == null) return
                changePanel(BattleSetupPage(getThis(), stage))
            }
        })
    }

    private fun ini() {
        add(back)
        add(jspjt)
        add(jspinfo)
        add(strt)
        strt.isEnabled = false
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
    }
}
