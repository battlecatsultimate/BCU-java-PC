package page.pack

import common.pack.PackData.UserPack
import common.pack.UserProfile
import common.util.unit.AbEnemy
import common.util.unit.EneRand
import main.Opts
import page.JBTN
import page.JTF
import page.JTG
import page.Page
import page.info.filter.EnemyFindPage
import page.pack.EREditTable
import page.support.AnimLCR
import java.awt.Rectangle
import java.awt.event.*
import java.util.function.Consumer
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class EREditPage(p: Page?, pac: UserPack?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val veif: JBTN = JBTN(0, "veif")
    private val jt: EREditTable = EREditTable(this)
    private val jspjt: JScrollPane = JScrollPane(jt)
    private val jlst: JList<EneRand> = JList<EneRand>()
    private val jspst: JScrollPane = JScrollPane(jlst)
    private val adds: JBTN = JBTN(0, "add")
    private val rems: JBTN = JBTN(0, "rem")
    private val addl: JBTN = JBTN(0, "addl")
    private val reml: JBTN = JBTN(0, "reml")
    private val jle: JList<AbEnemy> = JList<AbEnemy>()
    private val jspe: JScrollPane = JScrollPane(jle)
    private val name: JTF = JTF()
    private val type: Array<JTG?> = arrayOfNulls<JTG>(3)
    private val pack: UserPack?
    private var efp: EnemyFindPage? = null
    private var rand: EneRand? = null

    constructor(page: Page?, pac: UserPack?, e: EneRand?) : this(page, pac) {
        jle.setSelectedValue(e, true)
    }

    override fun mouseClicked(e: MouseEvent) {
        if (e.source === jt && !e.isControlDown) jt.clicked(e.point)
    }

    override fun renew() {
        if (efp != null && efp.getList() != null) jle.setListData(efp.getList().toTypedArray())
    }

    @Synchronized
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jspst, x, y, 500, 150, 400, 800)
        Page.Companion.set(adds, x, y, 500, 1000, 200, 50)
        Page.Companion.set(rems, x, y, 700, 1000, 200, 50)
        Page.Companion.set(name, x, y, 500, 1100, 400, 50)
        Page.Companion.set(veif, x, y, 950, 100, 400, 50)
        Page.Companion.set(jspe, x, y, 950, 150, 400, 1100)
        Page.Companion.set(jspjt, x, y, 1400, 450, 850, 800)
        for (i in 0..2) Page.Companion.set(type[i], x, y, 1550 + 250 * i, 250, 200, 50)
        Page.Companion.set(addl, x, y, 1800, 350, 200, 50)
        Page.Companion.set(reml, x, y, 2050, 350, 200, 50)
        jt.setRowHeight(Page.Companion.size(x, y, 50))
        jle.setFixedCellHeight(Page.Companion.size(x, y, 50))
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        addl.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val ind: Int = jt.addLine(jle.getSelectedValue())
                setER(rand)
                if (ind < 0) jt.clearSelection() else jt.addRowSelectionInterval(ind, ind)
            }
        })
        reml.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val ind: Int = jt.remLine()
                setER(rand)
                if (ind < 0) jt.clearSelection() else jt.addRowSelectionInterval(ind, ind)
            }
        })
        veif.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (efp == null) efp = EnemyFindPage(getThis(), pack.desc.id)
                changePanel(efp)
            }
        })
        jlst.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (isAdj || arg0.getValueIsAdjusting()) return
                setER(jlst.getSelectedValue())
            }
        })
        adds.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                rand = EneRand(pack.getNextID<EneRand, AbEnemy>(EneRand::class.java))
                pack.randEnemies.add(rand)
                change<Any>(null, { p: Any? ->
                    jlst.setListData(pack.randEnemies.getList().toTypedArray())
                    jlst.setSelectedValue(rand, true)
                    setER(rand)
                })
            }
        })
        rems.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (!Opts.conf()) return
                var ind: Int = jlst.getSelectedIndex() - 1
                if (ind < 0) ind = -1
                pack.randEnemies.remove(rand)
                change(ind, { IND: Int ->
                    val l: List<EneRand> = pack.randEnemies.getList()
                    jlst.setListData(l.toTypedArray())
                    if (IND < l.size) jlst.setSelectedIndex(IND) else jlst.setSelectedIndex(l.size - 1)
                    setER(jlst.getSelectedValue())
                })
            }
        })
        name.addFocusListener(object : FocusAdapter() {
            override fun focusLost(fe: FocusEvent?) {
                if (rand == null) return
                rand.name = name.getText().trim { it <= ' ' }
                setER(rand)
            }
        })
        for (i in 0..2) {
            type[i].addActionListener(object : ActionListener {
                override fun actionPerformed(arg0: ActionEvent?) {
                    if (isAdj || rand == null) return
                    rand.type = i
                    setER(rand)
                }
            })
        }
    }

    private fun ini() {
        add(back)
        add(veif)
        add(adds)
        add(rems)
        add(jspjt)
        add(jspst)
        add(addl)
        add(reml)
        add(jspe)
        add(name)
        for (i in 0..2) add(JTG(1, "ert$i").also { type[i] = it })
        setES()
        jle.setCellRenderer(AnimLCR())
        addListeners()
    }

    private fun setER(er: EneRand?) {
        change<EneRand>(er, Consumer<EneRand> { st: EneRand? ->
            val b = st != null && pack.editable
            rems.setEnabled(b)
            addl.setEnabled(b)
            reml.setEnabled(b)
            name.setEnabled(b)
            jt.setEnabled(b)
            for (btn in type) btn.setEnabled(b)
            rand = st
            jt.setData(st)
            name.setText(if (st == null) "" else rand.name)
            val t = if (st == null) -1 else st.type
            for (i in 0..2) type[i].setSelected(i == t)
            jspjt.scrollRectToVisible(Rectangle(0, 0, 1, 1))
        })
        resized()
    }

    private fun setES() {
        if (pack == null) {
            jlst.setListData(arrayOfNulls<EneRand>(0))
            setER(null)
            adds.setEnabled(false)
            return
        }
        adds.setEnabled(pack.editable)
        val l: List<EneRand> = pack.randEnemies.getList()
        jlst.setListData(l.toTypedArray())
        if (l.size == 0) {
            jlst.clearSelection()
            setER(null)
            return
        }
        jlst.setSelectedIndex(0)
        setER(pack.randEnemies.getList().get(0))
    }

    companion object {
        private const val serialVersionUID = 1L
        fun redefine() {
            EREditTable.Companion.redefine()
        }
    }

    init {
        pack = pac
        jle.setListData(UserProfile.Companion.getAll<AbEnemy>(pack.desc.id, AbEnemy::class.java).toTypedArray())
        ini()
        resized()
    }
}
