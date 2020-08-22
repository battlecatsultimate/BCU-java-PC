package page.battle

import common.CommonStatic
import common.util.stage.EStage
import common.util.stage.Stage
import common.util.unit.Enemy
import page.MainFrame
import page.Page
import page.info.EnemyInfoPage
import page.support.AbJTable
import page.support.EnemyTCR
import java.awt.Point

internal class ComingTable(private val page: Page) : AbJTable() {
    companion object {
        private const val serialVersionUID = 1L
        private var title: Array<String>
        fun redefine() {
            title = Page.Companion.get(1, "c", 6)
        }

        init {
            redefine()
        }
    }

    private var data: Array<Array<Any>?>?
    private var link: IntArray
    override fun getColumnClass(c: Int): Class<*> {
        var c = c
        c = lnk.get(c)
        return if (c == 1) Enemy::class.java else Any::class.java
    }

    override fun getColumnCount(): Int {
        return title.size
    }

    override fun getColumnName(arg0: Int): String {
        return title[arg0]
    }

    @Synchronized
    override fun getRowCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    override fun getValueAt(r: Int, c: Int): Any {
        var c = c
        c = lnk.get(c)
        if (data == null || r < 0 || c < 0 || r >= data!!.size || c >= data!![r].length) return null
        if (c == 2) {
            return data!![r]!![c].toString() + "%"
        }
        return if (data!![r]!![c] == null) "" else data!![r]!![c]
    }

    protected fun clicked(p: Point) {
        if (data == null) return
        var c: Int = getColumnModel().getColumnIndexAtX(p.x)
        c = lnk.get(c)
        val r: Int = p.y / getRowHeight()
        if (r < 0 || r >= data!!.size || c != 1) return
        val e: Enemy = data!![r]!![c] as Enemy
        val d: IntArray = CommonStatic.parseIntsN(data!![r]!![2] as String)
        MainFrame.Companion.changePanel(EnemyInfoPage(page, e, d[0], d[1]))
    }

    fun setData(st: Stage) {
        val info = st.data.simple
        data = Array(info.size) { arrayOfNulls(6) }
        link = IntArray(info.size)
        for (i in info.indices) {
            val ind = info.size - i - 1
            link[i] = ind
            data!![link[i]]!![1] = info[i].enemy.get()
            data!![link[i]]!![0] = if (info[i].boss == 1) "boss" else ""
            data!![link[i]]!![2] = CommonStatic.toArrayFormat(info[i].multiple, info[i].mult_atk)
            data!![link[i]]!![3] = if (info[i].number == 0) "infinite" else info[i].number
            if (info[i].castle_0 >= info[i].castle_1) data!![link[i]]!![4] = info[i].castle_0.toString() + "%" else data!![link[i]]!![4] = info[i].castle_0.toString() + "~" + info[i].castle_1 + "%"
        }
    }

    @Synchronized
    fun update(est: EStage) {
        for (i in link.indices) if (link[i] != -1) {
            data!![link[i]]!![5] = est.rem.get(i)
            data!![link[i]]!![3] = if (est.num.get(i) == 0) "infinite" else est.num.get(i)
            if (est.num.get(i) == -1) data!![link[i]] = null
        }
        var sum = 0
        for (i in link.indices.reversed()) {
            if (link[i] == -1) continue
            val dat = data!![link[i]]
            link[i] -= sum
            data!![link[i]] = dat
            if (data!![link[i]] == null) {
                sum++
                link[i] = -1
            }
        }
        val tem = arrayOfNulls<Array<Any>?>(data!!.size - sum)
        for (i in tem.indices) tem[i] = data!![i]
        data = tem
    }

    init {
        setDefaultRenderer(Enemy::class.java, EnemyTCR())
    }
}
