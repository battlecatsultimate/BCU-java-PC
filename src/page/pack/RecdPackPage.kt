package page.pack

import common.pack.PackData.UserPack
import common.util.stage.Recd
import main.MainBCU
import page.JTF
import page.Page
import page.battle.AbRecdPage
import java.awt.event.FocusEvent
import java.io.File
import java.util.function.Consumer
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class RecdPackPage(p: Page?, pack: UserPack?) : AbRecdPage(p, pack.editable) {
    private val rena: JTF = JTF()
    private val jlr: JList<Recd> = JList<Recd>()
    private val jspr: JScrollPane = JScrollPane(jlr)
    private val pac: UserPack?
    override fun getSelection(): Recd {
        return jlr.selectedValue
    }

    override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(jspr, x, y, 50, 100, 500, 1100)
        Page.Companion.set(rena, x, y, 600, 500, 300, 50)
    }

    override fun setList() {
        change(true)
        val r: Recd = jlr.selectedValue
        jlr.setListData(pac.getReplays().toTypedArray())
        jlr.setSelectedValue(r, true)
        setRecd(r)
        change(false)
    }

    override fun setRecd(r: Recd?) {
        super.setRecd(r)
        rena.isEditable = r != null
        rena.text = if (r == null) "" else r.name
    }

    private fun addListeners() {
        jlr.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (isAdj() || jlr.valueIsAdjusting) return
                setRecd(jlr.selectedValue)
            }
        })
        rena.setLnr(Consumer<FocusEvent> { x: FocusEvent? ->
            if (isAdj() || jlr.valueIsAdjusting) return@setLnr
            val r: Recd = jlr.selectedValue ?: return@setLnr
            val f = File("./replay/" + r.name + ".replay")
            if (f.exists()) {
                var str: String = MainBCU.validate(rena.text.trim { it <= ' ' })
                str = Recd.Companion.getAvailable(str)
                if (f.renameTo(File("./replay/$str.replay"))) {
                    Recd.Companion.map.remove(r.name)
                    r.name = str
                    Recd.Companion.map.put(r.name, r)
                }
            }
            rena.text = r.name
        })
    }

    private fun ini() {
        add(jspr)
        add(rena)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        pac = pack
        preini()
        ini()
        resized()
    }
}
