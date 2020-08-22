package page.info

import common.battle.BasisSet
import common.util.unit.Enemy
import page.JBTN
import page.Page
import page.view.EnemyViewPage
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JLabel

class EnemyInfoPage @JvmOverloads constructor(p: Page?, de: Enemy, mul: Int = 100, mula: Int = 100) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val anim: JBTN = JBTN(0, "anim")
    private val find: JBTN = JBTN(0, "stage")
    private val source = JLabel("Source of enemy icon: DB")
    private val info: EnemyInfoTable
    private val trea: TreaTable
    private val e: Enemy
    private val b: BasisSet = BasisSet.Companion.current()
    override fun callBack(o: Any?) {
        info.reset()
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(source, x, y, 0, 50, 600, 50)
        Page.Companion.set(anim, x, y, 600, 0, 200, 50)
        Page.Companion.set(find, x, y, 1200, 0, 200, 50)
        Page.Companion.set(info, x, y, 50, 100, 1600, 800)
        Page.Companion.set(trea, x, y, 1700, 100, 400, 1200)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (front is StageFilterPage) changePanel(front.getFront()) else changePanel(front)
            }
        })
        anim.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (front is EnemyViewPage) changePanel(front) else changePanel(EnemyViewPage(getThis(), e))
            }
        })
        find.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(StageFilterPage(getThis(), e.findApp()))
            }
        })
    }

    private fun ini() {
        add(back)
        add(info)
        add(trea)
        add(anim)
        add(find)
        add(source)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        e = de
        info = EnemyInfoTable(this, de, mul, mula)
        trea = TreaTable(this, b)
        ini()
        resized()
    }
}
