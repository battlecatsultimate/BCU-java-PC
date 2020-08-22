package page.view

import common.pack.PackData
import common.pack.UserProfile
import common.util.stage.Music
import io.BCMusic
import page.JBTN
import page.Page
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JList
import javax.swing.JScrollPane

class MusicPage @JvmOverloads constructor(p: Page?, mus: Collection<Music?> = UserProfile.Companion.getBCData().musics.getList()) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val strt: JBTN = JBTN(0, "start")
    private val jlf: JList<Music> = JList<Music>()
    private val jsp: JScrollPane = JScrollPane(jlf)

    constructor(p: Page?, id: PackData.Identifier<Music?>) : this(p, id.pack) {
        jlf.setSelectedValue(id.get(), true)
    }

    constructor(p: Page?, pack: String?) : this(p, UserProfile.Companion.getAll<Music>(pack, Music::class.java))

    fun getSelected(): PackData.Identifier<Music> {
        return jlf.selectedValue.getID()
    }

    override fun exit() {
        BCMusic.stopAll()
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jsp, x, y, 50, 100, 300, 800)
        Page.Companion.set(strt, x, y, 400, 100, 200, 50)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                BCMusic.clear()
                changePanel(front)
            }
        })
        strt.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (jlf.selectedValue == null) return
                BCMusic.setBG(jlf.selectedValue, 0)
            }
        })
    }

    private fun ini() {
        add(back)
        add(strt)
        add(jsp)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        jlf.setListData(mus.toTypedArray())
        ini()
        resized()
    }
}
