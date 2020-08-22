package page.view

import common.pack.UserProfile
import common.util.anim.AnimU.UType
import common.util.unit.Enemy
import page.JBTN
import page.Page
import page.info.EnemyInfoPage
import page.info.edit.EnemyEditPage
import page.support.AnimLCR
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class EnemyViewPage(p: Page?, pac: String?) : AbViewPage(p) {
    private val jlu: JList<Enemy> = JList<Enemy>()
    private val jspu: JScrollPane = JScrollPane(jlu)
    private val stat: JBTN = JBTN(0, "stat")
    private val source = JLabel("Source of enemy icon: DB")

    constructor(p: Page?, e: Enemy) : this(p, e.getID().pack) {
        jlu.setSelectedValue(e, true)
    }

    override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(jspu, x, y, 50, 100, 300, 1100)
        Page.Companion.set(stat, x, y, 400, 1000, 300, 50)
        Page.Companion.set(source, x, y, 0, 50, 600, 50)
        jlu.fixedCellHeight = Page.Companion.size(x, y, 50)
    }

    override fun updateChoice() {
        val u: Enemy = jlu.selectedValue ?: return
        setAnim<UType>(u.anim)
    }

    private fun addListeners() {
        jlu.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.valueIsAdjusting) return
                updateChoice()
            }
        })
        stat.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                val ene: Enemy = jlu.selectedValue ?: return
                if (ene.de is CustomEnemy) {
                    changePanel(EnemyEditPage(getThis(), ene))
                } else changePanel(EnemyInfoPage(getThis(), ene))
            }
        })
    }

    private fun ini() {
        preini()
        add(jspu)
        add(stat)
        add(source)
        jlu.setCellRenderer(AnimLCR())
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        jlu.setListData(Vector<Enemy>(UserProfile.Companion.getAll<Enemy>(pac, Enemy::class.java)))
        ini()
        resized()
    }
}
