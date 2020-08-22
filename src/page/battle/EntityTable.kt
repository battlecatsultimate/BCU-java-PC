package page.battle

import common.battle.entity.Entity
import common.io.assets.Admin
import common.io.assets.Admin.StaticPermitted
import common.io.assets.AssetLoader
import common.io.assets.AssetLoader.AssetHeader
import common.io.assets.AssetLoader.AssetHeader.AssetEntry
import common.io.json.JsonEncoder
import common.io.json.Test
import common.io.json.Test.JsonTest_0.JsonD
import common.io.json.Test.JsonTest_2
import common.pack.PackData
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
import common.util.unit.Form
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.MainLocale
import page.anim.AnimBox
import page.support.EnemyTCR
import page.support.ListJtfPolicy
import page.support.SortTable
import page.support.UnitTCR
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter

internal class EntityTable(private val dir: Int) : SortTable<Entity?>() {
    companion object {
        private const val serialVersionUID = 1L
        private var title: Array<String>
        fun redefine() {
            title = MainLocale.Companion.getLoc(1, "u", 3)
        }

        init {
            redefine()
        }
    }

    override fun getColumnClass(c: Int): Class<*> {
        return if (lnk.get(c) == 1) if (dir == 1) Enemy::class.java else Form::class.java else Any::class.java
    }

    protected override fun compare(e0: Entity, e1: Entity, c: Int): Int {
        return if (c == 1) getID(e0)!!.compareTo(getID(e1)) else java.lang.Long.compare(get(e0, c) as Long, get(e1, c) as Long)
    }

    protected override operator fun get(t: Entity, c: Int): Any? {
        return if (c == 0) t.health else if (c == 1) t.data.pack else if (c == 2) t.atk.toLong() else null
    }

    protected override fun getTit(): Array<String> {
        return title
    }

    private fun getID(e: Entity): PackData.Identifier<*>? {
        if (e.data is MaskUnit) return (e.data as MaskUnit).getPack().uid
        return if (e.data is MaskEnemy) (e.data as MaskEnemy).getPack().id else null
    }

    init {
        if (dir == 1) setDefaultRenderer(Enemy::class.java, EnemyTCR()) else setDefaultRenderer(Form::class.java, UnitTCR(lnk))
        sign = -1
    }
}
