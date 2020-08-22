package page.battle

import common.util.stage.Recd
import main.MainBCU
import page.JBTN
import page.JTF
import page.Page
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent

class RecdSavePage(p: Page?, rec: Recd) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val save: JBTN = JBTN(0, "save")
    private val jtf: JTF = JTF()
    private val recd: Recd
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jtf, x, y, 1000, 500, 300, 50)
        Page.Companion.set(save, x, y, 1000, 600, 300, 50)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        jtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(arg0: FocusEvent?) {
                var str: String = jtf.getText().trim { it <= ' ' }
                str = MainBCU.validate(str)
                if (str.length == 0) str = "new record"
                str = Recd.Companion.getAvailable(str)
                recd.name = str
            }
        })
        save.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                recd.write()
                Recd.Companion.map.put(recd.name, recd)
                changePanel(RecdManagePage(rootPage))
            }
        })
    }

    private fun ini() {
        add(back)
        add(jtf)
        add(save)
        jtf.setText(Recd.Companion.getAvailable("new record"))
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        recd = rec
        ini()
        resized()
    }
}
