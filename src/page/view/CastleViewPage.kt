package page.view

import common.pack.PackData
import common.util.stage.CastleImg
import common.util.stage.CastleList
import page.JBTN
import page.Page
import utilpc.UtilPC
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class CastleViewPage @JvmOverloads constructor(p: Page?, list: Collection<CastleList>? = CastleList.Companion.map().values) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val jlsm: JList<CastleList> = JList<CastleList>()
    private val jspsm: JScrollPane = JScrollPane(jlsm)
    private val jlst: JList<CastleImg> = JList<CastleImg>()
    private val jspst: JScrollPane = JScrollPane(jlst)
    private val jl = JLabel()

    constructor(p: Page?, sele: CastleList?) : this(p) {
        jlsm.setSelectedValue(sele, true)
    }

    constructor(p: Page?, defcas: Collection<CastleList?>?, id: PackData.Identifier<CastleImg?>) : this(p, defcas) {
        val abcas: CastleList = CastleList.Companion.map().get(id.pack)
        jlsm.setSelectedValue(abcas, true)
        jlst.setSelectedValue(abcas.get(id.id), true)
    }

    fun getVal(): PackData.Identifier<CastleImg>? {
        val img: CastleImg = jlst.selectedValue
        return if (img == null) null else img.getID()
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jspsm, x, y, 50, 100, 300, 1100)
        Page.Companion.set(jspst, x, y, 400, 550, 300, 650)
        Page.Companion.set(jl, x, y, 800, 50, 1000, 1000)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        jlsm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.valueIsAdjusting) return
                val sm: CastleList = jlsm.selectedValue ?: return
                jlst.setListData(Vector<CastleImg>(sm.getList()))
                jlst.selectedIndex = 0
            }
        })
        jlst.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.valueIsAdjusting) return
                val s: CastleImg = jlst.selectedValue
                if (s == null) jl.icon = null else jl.icon = UtilPC.getIcon(s.img)
            }
        })
    }

    private fun ini() {
        add(back)
        add(jspsm)
        add(jspst)
        add(jl)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        val vec: Vector<CastleList> = Vector<CastleList>()
        vec.addAll(list!!)
        jlsm.setListData(vec)
        ini()
        resized()
    }
}
