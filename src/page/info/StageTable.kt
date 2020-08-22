package page.info

import common.CommonStatic
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
import common.util.Data
import common.util.stage.EStage
import common.util.stage.SCGroup
import common.util.stage.Stage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.Enemy
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.MainFrame
import page.Page
import page.anim.AnimBox
import page.support.AbJTable
import page.support.EnemyTCR
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.Point
import java.awt.event.MouseEvent

class StageTable(private val page: Page) : AbJTable() {
    companion object {
        private const val serialVersionUID = 1L
        private var title: Array<String>
        fun redefine() {
            title = Page.Companion.get(1, "t", 9)
        }

        init {
            redefine()
        }
    }

    protected var data: Array<Array<Any>>?
    override fun getColumnClass(c: Int): Class<*> {
        return if (c == 1) Enemy::class.java else Any::class.java
    }

    override fun getColumnCount(): Int {
        return title.size
    }

    override fun getColumnName(arg0: Int): String {
        return title[arg0]
    }

    override fun getRowCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    override fun getToolTipText(e: MouseEvent): String {
        return if (columnAtPoint(e.point) == 2) {
            "{hp, atk}"
        } else {
            null
        }
    }

    override fun getValueAt(r: Int, c: Int): Any {
        if (data == null || r < 0 || c < 0 || r >= data!!.size || c >= data!![r].length) return null
        return if (c == 2) data!![r][c].toString() + "%" else data!![r][c]
    }

    fun clicked(p: Point) {
        if (data == null) return
        var c: Int = getColumnModel().getColumnIndexAtX(p.x)
        c = lnk.get(c)
        val r: Int = p.y / getRowHeight()
        if (r < 0 || r >= data!!.size || c != 1) return
        val e: Enemy = data!![r][c] as Enemy
        if (data!![r][2] !is String) return
        val d: IntArray = CommonStatic.parseIntsN(data!![r][2] as String)
        MainFrame.Companion.changePanel(EnemyInfoPage(page, e, d[0], d[1]))
    }

    fun setData(st: Stage?) {
        val info = st!!.data.simple
        data = Array(info.size) { arrayOfNulls(9) }
        for (i in info.indices) {
            val ind = info.size - i - 1
            data!![ind][1] = info[i].enemy.get()
            data!![ind][0] = if (info[i].boss == 1) "boss" else ""
            data!![ind][2] = CommonStatic.toArrayFormat(info[i].multiple, info[i].mult_atk)
            data!![ind][3] = if (info[i].number == 0) "infinite" else info[i].number
            if (info[i].castle_0 >= info[i].castle_1) data!![ind][4] = info[i].castle_0.toString() + "%" else data!![ind][4] = info[i].castle_0.toString() + "~" + info[i].castle_1 + "%"
            if (Math.abs(info[i].spawn_0) >= Math.abs(info[i].spawn_1)) data!![ind][5] = info[i].spawn_0 else data!![ind][5] = info[i].spawn_0.toString() + "~" + info[i].spawn_1
            if (info[i].respawn_0 == info[i].respawn_1) data!![ind][6] = info[i].respawn_0 else data!![ind][6] = info[i].respawn_0.toString() + "~" + info[i].respawn_1
            data!![ind][7] = if (info[i].layer_0 == info[i].layer_1) info[i].layer_0 else info[i].layer_0.toString() + "~" + info[i].layer_1
            val g = info[i].group
            val scg: SCGroup? = st.data.sub[g]
            data!![ind][8] = if (scg == null) if (g != 0) Data.Companion.trio(g) + " - invalid" else "" else scg.toString()
        }
    }

    init {
        setDefaultRenderer(Enemy::class.java, EnemyTCR())
    }
}
