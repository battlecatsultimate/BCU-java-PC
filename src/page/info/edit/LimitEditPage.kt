package page.info.edit

import common.CommonStatic
import common.util.stage.Limit
import common.util.stage.MapColc.PackMapColc
import common.util.stage.Stage
import page.JBTN
import page.JTF
import page.Page
import java.awt.event.ActionEvent
import java.awt.event.FocusEvent
import java.util.function.Consumer
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class LimitEditPage(p: Page?, private val st: Stage?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val star: JTF = JTF()
    private val stag: JTF = JTF()
    private val jll: JList<Limit> = JList<Limit>()
    private val jspl: JScrollPane = JScrollPane(jll)
    private val addl: JBTN = JBTN(0, "add")
    private val reml: JBTN = JBTN(0, "rem")
    private val lt: LimitTable
    override fun callBack(o: Any?) {
        setLimit(jll.selectedValue)
    }

    override fun renew() {
        lt.renew()
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jspl, x, y, 50, 100, 400, 800)
        Page.Companion.set(addl, x, y, 50, 900, 200, 50)
        Page.Companion.set(reml, x, y, 250, 900, 200, 50)
        Page.Companion.set(stag, x, y, 50, 950, 200, 50)
        Page.Companion.set(star, x, y, 250, 950, 200, 50)
        Page.Companion.set(lt, x, y, 500, 100, 1400, 100)
    }

    private fun `addListeners$0`() {
        back.setLnr(Consumer { e: ActionEvent? -> changePanel(front) })
        star.setLnr(Consumer<FocusEvent> { e: FocusEvent? ->
            if (isAdj) return@setLnr
            val l: Limit = jll.selectedValue
            var n: Int = CommonStatic.parseIntN(star.text) - 1
            if (n < 0) n = -1
            if (n > 3) n = 0
            if (l != null) l.star = n
            setLimit(l)
        })
        stag.setLnr(Consumer<FocusEvent> { e: FocusEvent? ->
            if (isAdj) return@setLnr
            val l: Limit = jll.selectedValue
            var n: Int = CommonStatic.parseIntN(stag.text)
            if (n < 0) n = -1
            if (n >= st!!.map.list.size) n = 0
            if (l != null) l.sid = n
            setLimit(l)
        })
        addl.setLnr(Consumer { e: ActionEvent? ->
            st!!.map.lim.add(Limit())
            setListL()
        })
        reml.setLnr(Consumer { e: ActionEvent? ->
            st!!.map.lim.remove(jll.selectedValue)
            setListL()
        })
        jll.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (isAdj || jll.valueIsAdjusting) return
                setLimit(jll.selectedValue)
            }
        })
    }

    private fun ini() {
        add(back)
        add(jspl)
        add(addl)
        add(reml)
        add(star)
        add(stag)
        add(lt)
        setListL()
        `addListeners$0`()
    }

    private fun setLimit(l: Limit?) {
        reml.isEnabled = l != null
        star.isEditable = l != null
        stag.isEditable = l != null
        star.text = if (l == null) "" else if (l.star == -1) "all stars" else (l.star + 1).toString() + " star"
        stag.text = if (l == null) "" else if (l.sid == -1) "all stages" else l.sid.toString() + " - " + st!!.map.list[l.sid]
        lt.setLimit(l)
    }

    private fun setListL() {
        var l: Limit? = jll.selectedValue
        change<Array<Limit>>(st!!.map.lim.toTypedArray(), Consumer<Array<Limit?>> { x: Array<Limit?>? -> jll.setListData(x) })
        if (!st.map.lim.contains(l)) l = null
        setLimit(l)
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        lt = LimitTable(this, this, (st!!.map.mc as PackMapColc).pack)
        ini()
        resized()
    }
}
