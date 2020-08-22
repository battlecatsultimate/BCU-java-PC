package page.view

import common.pack.PackData
import common.pack.UserProfile
import common.util.pack.Background
import page.JBTN
import page.Page
import page.SupPage
import utilpc.UtilPC
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class BGViewPage(p: Page?, pac: String?) : Page(p), SupPage<Background?> {
    private val back: JBTN = JBTN(0, "back")
    private val jlst: JList<Background> = JList<Background>()
    private val jspst: JScrollPane = JScrollPane(jlst)
    private val jl = JLabel()

    constructor(front: Page?, pac: String?, bg: PackData.Identifier<Background?>) : this(front, pac) {
        jlst.setSelectedValue(bg.get(), false)
    }

    override fun getSelected(): Background? {
        return jlst.getSelectedValue()
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jspst, x, y, 50, 100, 300, 1100)
        Page.Companion.set(jl, x, y, 400, 50, 1800, 1100)
        val s: Background = jlst.getSelectedValue() ?: return
        jl.icon = UtilPC.getBg(s, jl.width, jl.height)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        jlst.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.getValueIsAdjusting()) return
                val s: Background = jlst.getSelectedValue() ?: return
                jl.icon = UtilPC.getBg(s, jl.width, jl.height)
            }
        })
    }

    private fun ini() {
        add(back)
        add(jspst)
        add(jl)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        jlst.setListData(Vector<Background>(UserProfile.Companion.getAll<Background>(pac, Background::class.java)))
        ini()
        resized()
    }
}
