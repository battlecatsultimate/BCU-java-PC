package page.info.edit

import common.CommonStatic
import common.battle.data.DataEntity
import common.io.assets.Admin
import common.io.assets.Admin.StaticPermitted
import common.io.assets.AssetLoader
import common.io.assets.AssetLoader.AssetHeader
import common.io.assets.AssetLoader.AssetHeader.AssetEntry
import common.io.json.JsonEncoder
import common.io.json.Test
import common.io.json.Test.JsonTest_0.JsonD
import common.io.json.Test.JsonTest_2
import common.pack.PackData.UserPack
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.pack.Source.SourceAnimLoader
import common.pack.Source.SourceAnimSaver
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.util.Data
import common.util.stage.EStage
import common.util.stage.SCDef
import common.util.stage.SCGroup
import common.util.stage.Stage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.AbEnemy
import common.util.unit.EneRand
import common.util.unit.Enemy
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.MainFrame
import page.Page
import page.anim.AnimBox
import page.info.EnemyInfoPage
import page.pack.EREditPage
import page.support.AbJTable
import page.support.EnemyTCR
import page.support.InTableTH
import page.support.ListJtfPolicy
import page.support.Reorderable
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.Component
import java.awt.Point
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.text.JTextComponent

internal class StageEditTable(private val page: Page, pac: UserPack) : AbJTable(), Reorderable {
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

    private var stage: SCDef? = null
    private val pack: UserPack
    private var changing = false
    override fun editCellAt(r: Int, c: Int, e: EventObject): Boolean {
        val result: Boolean = super.editCellAt(r, c, e)
        val editor: Component = getEditorComponent()
        if (editor == null || editor !is JTextComponent) return result
        val jtf: JTextComponent = editor as JTextComponent
        if (e is KeyEvent) jtf.selectAll()
        if (lnk.get(c) == 1 && jtf.getText().length > 0) jtf.setText((get(r, c) as AbEnemy?).getID().toString() + "")
        return result
    }

    override fun getColumnClass(c: Int): Class<*> {
        return if (lnk.get(c) == 1) Int::class.java else String::class.java
    }

    override fun getColumnCount(): Int {
        return title.size
    }

    override fun getColumnName(c: Int): String {
        return title[lnk.get(c)]
    }

    @Synchronized
    override fun getRowCount(): Int {
        return if (stage == null) 0 else stage.datas.size
    }

    override fun getToolTipText(e: MouseEvent): String {
        return if (columnAtPoint(e.point) == 2) {
            "{hp, atk}"
        } else {
            null
        }
    }

    @Synchronized
    override fun getValueAt(r: Int, c: Int): Any {
        return if (stage == null || r < 0 || c < 0 || r >= stage.datas.size || c > lnk.size) null else get(r, lnk.get(c))!!
    }

    override fun isCellEditable(r: Int, c: Int): Boolean {
        return true
    }

    @Synchronized
    override fun reorder(ori: Int, fin: Int) {
        var fin = fin
        if (fin > ori) fin--
        if (fin == ori) return
        val info = stage.datas
        val ior = info.size - ori - 1
        val ifi = info.size - fin - 1
        val temp = info[ior]
        if (ior < ifi) for (i in ior until ifi) info[i] = info[i + 1] else for (i in ior downTo ifi + 1) info[i] = info[i - 1]
        info[ifi] = temp
    }

    @Synchronized
    override fun setValueAt(arg0: Any, r: Int, c: Int) {
        var c = c
        if (stage == null) return
        if (r >= getRowCount()) return
        c = lnk.get(c)
        if (c > 3) {
            val `is`: IntArray = CommonStatic.parseIntsN(arg0 as String)
            if (`is`.size == 0) return
            if (`is`.size == 1) set(r, c, `is`[0], -1) else set(r, c, `is`[0], `is`[1])
        } else if (c == 0) {
            val i = (arg0 as String).length
            set(r, c, if (i > 0) 1 else 0, 0)
        } else if (c == 2) {
            val data: IntArray = CommonStatic.parseIntsN(arg0 as String)
            if (data.size == 0) {
                return
            } else if (data.size == 1) {
                set(r, c, data[0], -1)
            } else {
                set(r, c, data[0], data[1])
            }
        } else {
            val i = if (arg0 is Int) arg0 else CommonStatic.parseIntN(arg0 as String)
            set(r, c, i, 0)
        }
    }

    @Synchronized
    fun addLine(enemy: AbEnemy?): Int {
        if (stage == null) return -1
        var ind: Int = getSelectedRow()
        if (ind == -1) ind = 0
        val info = stage.datas
        val len = info.size
        var sind = len - ind - 1
        val ans = arrayOfNulls<SCDef.Line>(len + 1)
        if (sind >= 0) {
            for (i in 0 until sind) ans[i] = info[i]
            for (i in sind + 1 until len + 1) ans[i] = info[i - 1]
        } else sind = 0
        if (enemy == null && sind < info.size && getSelectedRow() >= 0) ans[sind] = info[sind].clone() else {
            ans[sind] = SCDef.Line()
            ans[sind]!!.enemy = if (enemy == null) null else enemy.getID()
            ans[sind]!!.number = 1
            ans[sind]!!.castle_0 = 100
            ans[sind]!!.layer_0 = 9
            ans[sind]!!.multiple = 100
            ans[sind]!!.mult_atk = 100
        }
        stage.datas = ans
        ind++
        if (ind >= ans.size) ind = ans.size - 1
        for (i in ans.indices) if (ans[i] == null) ans[i] = SCDef.Line()
        return ind
    }

    @Synchronized
    fun clicked(p: Point) {
        if (stage == null) return
        var c: Int = getColumnModel().getColumnIndexAtX(p.x)
        c = lnk.get(c)
        val r: Int = p.y / getRowHeight()
        val info = stage.datas
        val len = info.size
        if (r < 0 || r >= getRowCount() || c != 1) return
        val ind = len - r - 1
        if (info[ind] == null) return
        val e: AbEnemy? = info[ind]!!.enemy.get()
        if (e != null && e is Enemy) MainFrame.Companion.changePanel(EnemyInfoPage(page, e as Enemy?, info[ind]!!.multiple, info[ind]!!.mult_atk))
        if (e != null && e is EneRand) MainFrame.Companion.changePanel(EREditPage(page, pack, e as EneRand?))
    }

    @Synchronized
    fun remLine(): Int {
        if (stage == null) return -1
        var ind: Int = getSelectedRow()
        val info = stage.datas
        if (info.size == 0) return -1
        if (ind == -1) ind = 0
        val sind = info.size - ind - 1
        val ans = arrayOfNulls<SCDef.Line>(info.size - 1)
        for (i in ans.indices) if (i < sind) ans[i] = info[i] else ans[i] = info[i + 1]
        stage.datas = ans
        if (ans.size > 0) {
            if (ind == 0) ind = 1
            return ind - 1
        }
        return -1
    }

    @Synchronized
    fun setData(st: Stage?) {
        changing = true
        if (cellEditor != null) cellEditor.stopCellEditing()
        changing = false
        stage = if (st == null) null else st.data as SCDef
        clearSelection()
    }

    private operator fun get(r: Int, c: Int): Any? {
        if (r < 0 || r >= stage.datas.size) return null
        val info = stage.datas
        val data = info[info.size - r - 1] ?: return null
        if (c == 0) return if (data.boss == 1) "boss" else "" else if (c == 1) return if (data.enemy == null) null else data.enemy.get() else if (c == 2) return CommonStatic.toArrayFormat(data.multiple, data.mult_atk) + "%" else if (c == 3) return if (data.number == 0) "infinite" else data.number else if (c == 4) return (if (data.castle_0 >= data.castle_1) data.castle_0 else data.castle_0.toString() + "~" + data.castle_1).toString() + "%" else if (c == 5) return if (Math.abs(data.spawn_0) >= Math.abs(data.spawn_1)) data.spawn_0 else data.spawn_0.toString() + "~" + data.spawn_1 else if (c == 6) return if (data.respawn_0 == data.respawn_1) data.respawn_0 else data.respawn_0.toString() + "~" + data.respawn_1 else if (c == 7) return if (data.layer_0 == data.layer_1) data.layer_0 else data.layer_0.toString() + "~" + data.layer_1 else if (c == 8) {
            val g = data.group
            val scg: SCGroup = stage.sub[g]
            return if (scg == null) if (g != 0) Data.Companion.trio(g) + " - invalid" else "" else scg.toString()
        }
        return null
    }

    private operator fun set(r: Int, c: Int, v: Int, para: Int) {
        var v = v
        if (changing) return
        if (r < 0 || r >= stage.datas.size) return
        if (c == 1 && v < 0) return
        if (c != 5 && v < 0) v = 0
        val info = stage.datas
        val data = info[info.size - r - 1]
        if (c == 0) data.boss = v else if (c == 2) {
            data.multiple = if (v == -1) data.multiple else v
            data.mult_atk = if (para == -1) v else para
        } else if (c == 3) data.number = v else if (c == 4) if (para == -1) {
            data.castle_1 = v
            data.castle_0 = data.castle_1
        } else {
            data.castle_0 = Math.min(v, para)
            data.castle_1 = Math.max(v, para)
        } else if (c == 5) if (para == -1) {
            data.spawn_1 = v
            data.spawn_0 = data.spawn_1
        } else {
            if (Math.abs(v) > Math.abs(para)) {
                data.spawn_1 = v
                data.spawn_0 = para
            } else {
                data.spawn_0 = v
                data.spawn_1 = para
            }
        } else if (c == 6) if (para == -1) {
            data.respawn_1 = v
            data.respawn_0 = data.respawn_1
        } else {
            data.respawn_0 = Math.min(v, para)
            data.respawn_1 = Math.max(v, para)
        } else if (c == 7) if (para == -1) {
            data.layer_1 = v
            data.layer_0 = data.layer_1
        } else {
            data.layer_0 = Math.min(v, para)
            data.layer_1 = Math.max(v, para)
        } else if (c == 8) data.group = Math.max(0, v)
    }

    init {
        pack = pac
        setTransferHandler(InTableTH(this))
        setDefaultRenderer(Int::class.java, EnemyTCR())
    }
}
