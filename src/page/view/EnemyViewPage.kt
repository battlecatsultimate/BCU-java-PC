package page.view

import common.io.assets.Admin
import common.io.assets.Admin.StaticPermitted
import common.io.assets.AssetLoader
import common.io.assets.AssetLoader.AssetHeader
import common.io.assets.AssetLoader.AssetHeader.AssetEntry
import common.io.json.JsonEncoder
import common.io.json.Test
import common.io.json.Test.JsonTest_0.JsonD
import common.io.json.Test.JsonTest_2
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.pack.Source.SourceAnimLoader
import common.pack.Source.SourceAnimSaver
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.pack.UserProfile
import common.util.anim.AnimU.UType
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.Enemy
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JBTN
import page.JL
import page.Page
import page.anim.AnimBox
import page.info.EnemyInfoPage
import page.info.edit.EnemyEditPage
import page.support.AnimLCR
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
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

    protected override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(jspu, x, y, 50, 100, 300, 1100)
        Page.Companion.set(stat, x, y, 400, 1000, 300, 50)
        Page.Companion.set(source, x, y, 0, 50, 600, 50)
        jlu.setFixedCellHeight(Page.Companion.size(x, y, 50))
    }

    protected override fun updateChoice() {
        val u: Enemy = jlu.getSelectedValue() ?: return
        setAnim<UType>(u.anim)
    }

    private fun addListeners() {
        jlu.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.getValueIsAdjusting()) return
                updateChoice()
            }
        })
        stat.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                val ene: Enemy = jlu.getSelectedValue() ?: return
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
