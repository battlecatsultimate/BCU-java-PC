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
        return jlr.getSelectedValue()
    }

    protected override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(jspr, x, y, 50, 100, 500, 1100)
        Page.Companion.set(rena, x, y, 600, 500, 300, 50)
    }

    protected override fun setList() {
        change(true)
        val r: Recd = jlr.getSelectedValue()
        jlr.setListData(pac.getReplays().toTypedArray())
        jlr.setSelectedValue(r, true)
        setRecd(r)
        change(false)
    }

    protected override fun setRecd(r: Recd?) {
        super.setRecd(r)
        rena.setEditable(r != null)
        rena.setText(if (r == null) "" else r.name)
    }

    private fun addListeners() {
        jlr.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (isAdj() || jlr.getValueIsAdjusting()) return
                setRecd(jlr.getSelectedValue())
            }
        })
        rena.setLnr(Consumer<FocusEvent> { x: FocusEvent? ->
            if (isAdj() || jlr.getValueIsAdjusting()) return@setLnr
            val r: Recd = jlr.getSelectedValue() ?: return@setLnr
            val f = File("./replay/" + r.name + ".replay")
            if (f.exists()) {
                var str: String = MainBCU.validate(rena.getText().trim { it <= ' ' })
                str = Recd.Companion.getAvailable(str)
                if (f.renameTo(File("./replay/$str.replay"))) {
                    Recd.Companion.map.remove(r.name)
                    r.name = str
                    Recd.Companion.map.put(r.name, r)
                }
            }
            rena.setText(r.name)
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
