package page.info.filter

import common.battle.Basis
import common.battle.BasisSet
import common.system.Node
import common.util.unit.Enemy
import common.util.unit.Form
import common.util.unit.Unit
import page.MainFrame
import page.Page
import page.info.UnitInfoPage
import page.support.SortTable
import page.support.UnitTCR
import java.awt.Point

class UnitListTable(private val page: Page) : SortTable<Form?>() {
    companion object {
        private const val serialVersionUID = 1L
        private var tit: Array<String>
        fun redefine() {
            tit = arrayOf("ID", "name", Page.Companion.get(1, "pref"), "HP", "HB", "atk", Page.Companion.get(1, "range"),
                    Page.Companion.get(1, "speed"), "dps", Page.Companion.get(1, "preaa"), "CD", Page.Companion.get(1, "price"), Page.Companion.get(1, "atkf"))
        }

        init {
            redefine()
        }
    }

    fun clicked(p: Point) {
        if (list == null) return
        var c: Int = getColumnModel().getColumnIndexAtX(p.x)
        c = lnk.get(c)
        val r: Int = p.y / getRowHeight()
        if (r < 0 || r >= list.size || c != 1) return
        val e: Form = list.get(r)
        val n: Node<Unit> = Node.Companion.getList(list, e)
        MainFrame.Companion.changePanel(UnitInfoPage(page, n))
    }

    override fun getColumnClass(c: Int): Class<*> {
        var c = c
        c = lnk.get(c)
        return if (c == 1) Enemy::class.java else String::class.java
    }

    protected override fun compare(e0: Form, e1: Form, c: Int): Int {
        if (c == 0) {
            val `val` = e0.uid.compareTo(e1.uid)
            return if (`val` != 0) `val` else Integer.compare(e0.fid, e1.fid)
        }
        if (c == 1) return e0.toString().compareTo(e1.toString())
        val i0 = get(e0, c) as Int
        val i1 = get(e1, c) as Int
        return if (i0 > i1) 1 else if (i0 == i1) 0 else -1
    }

    protected override operator fun get(e: Form, c: Int): Any? {
        val b: Basis = BasisSet.Companion.current()
        val du: MaskUnit = e.maxu()
        val mul = e.unit.lv.getMult(e.unit.prefLv)
        val atk: Double = b.t().getAtkMulti()
        val def: Double = b.t().getDefMulti()
        val itv: Int = du.getItv()
        return if (c == 0) e.uid.toString() + "-" + e.fid else if (c == 1) e else if (c == 2) e.unit.prefLv else if (c == 3) (du.getHp() * mul * def) else if (c == 4) du.getHb() else if (c == 5) (du.allAtk() * mul * atk) else if (c == 6) du.getRange() else if (c == 7) du.getSpeed() else if (c == 8) (du.allAtk() * mul * atk * 30 / itv) else if (c == 9) du.rawAtkData().get(0).get(1) else if (c == 10) b.t().getFinRes(du.getRespawn()) else if (c == 11) e.getDefaultPrice(1) else if (c == 12) du.getItv() else null
    }

    override fun getTit(): Array<String> {
        return tit
    }

    init {
        setDefaultRenderer(Enemy::class.java, UnitTCR(lnk))
    }
}
