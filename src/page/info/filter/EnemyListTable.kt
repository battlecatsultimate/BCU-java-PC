package page.info.filter

import common.battle.Basis
import common.battle.BasisSet
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
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.Enemy
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.MainFrame
import page.Page
import page.anim.AnimBox
import page.info.EnemyInfoPage
import page.support.EnemyTCR
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.Point

class EnemyListTable(private val page: Page) : SortTable<Enemy?>() {
    companion object {
        private const val serialVersionUID = 1L
        private var tit: Array<String>
        fun redefine() {
            tit = arrayOf("ID", "", "HP", "HB", "atk", Page.Companion.get(1, "range"), Page.Companion.get(1, "atkf"),
                    Page.Companion.get(1, "speed"), Page.Companion.get(1, "drop"), Page.Companion.get(1, "preaa"), "hp/dps", "HP/HB/dps")
        }

        init {
            redefine()
        }
    }

    private val b: Basis = BasisSet.Companion.current()
    override fun getColumnClass(c: Int): Class<*> {
        var c = c
        c = lnk.get(c)
        return if (c == 1) Enemy::class.java else String::class.java
    }

    fun clicked(p: Point) {
        if (list == null) return
        var c: Int = getColumnModel().getColumnIndexAtX(p.x)
        c = lnk.get(c)
        val r: Int = p.y / getRowHeight()
        if (r < 0 || r >= list.size || c != 1) return
        val e: Enemy = list.get(r)
        MainFrame.Companion.changePanel(EnemyInfoPage(page, e))
    }

    protected override fun compare(e0: Enemy, e1: Enemy, c: Int): Int {
        var c = c
        if (c == 1) c--
        if (c == 0) {
            return e0.getID().compareTo(e1.getID())
        }
        val i0 = get(e0, c) as Int
        val i1 = get(e1, c) as Int
        return if (i0 > i1) 1 else if (i0 == i1) 0 else -1
    }

    protected override operator fun get(e: Enemy, c: Int): Any? {
        return if (c == 0) e.id else if (c == 1) e else if (c == 2) e.de.getHp() else if (c == 3) e.de.getHb() else if (c == 4) e.de.allAtk() else if (c == 5) e.de.getRange() else if (c == 6) e.de.getItv() else if (c == 7) e.de.getSpeed() else if (c == 8) (e.de.getDrop() * b.t().getDropMulti()) else if (c == 9) e.de.rawAtkData().get(0).get(1) else if (c == 10) if (e.de.allAtk() == 0) Int.MAX_VALUE else (e.de.getHp() as Long * e.de.getItv() / e.de.allAtk()) else if (c == 11) if (e.de.getHb() < 2) get(e, 10) as Int else get(e, 10) as Int / e.de.getHb() else null
    }

    protected override fun getTit(): Array<String> {
        return tit
    }

    init {
        setDefaultRenderer(Enemy::class.java, EnemyTCR())
    }
}
