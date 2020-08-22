package page.pack

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
import common.util.stage.EStage
import common.util.stage.Music
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCMusic
import io.BCPlayer
import page.JBTN
import page.JL
import page.Page
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.awt.Desktop
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import java.io.IOException
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class MusicEditPage(p: Page?, ac: UserPack?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val jlst: JList<Music> = JList<Music>()
    private val jspst: JScrollPane = JScrollPane(jlst)
    private val relo: JBTN = JBTN(0, "read list")
    private val play: JBTN = JBTN(0, "start")
    private val show: JBTN = JBTN(0, "show")
    private val pack: UserPack?
    private var sele: Music? = null
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jspst, x, y, 50, 100, 300, 1000)
        Page.Companion.set(relo, x, y, 400, 100, 200, 50)
        Page.Companion.set(play, x, y, 400, 200, 200, 50)
        Page.Companion.set(show, x, y, 400, 300, 200, 50)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (BCMusic.BG != null && BCMusic.BG.isPlaying()) {
                    BCMusic.BG.stop()
                    BCMusic.clear()
                }
                changePanel(front)
            }
        })
        relo.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                pack.loadMusics()
                setList()
            }
        })
        show.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val f = File("./res/img/$pack/music/")
                f.mkdirs()
                try {
                    Desktop.getDesktop().open(f)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        })
        play.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (sele == null) return
                BCMusic.setBG(sele, 0)
            }
        })
        jlst.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (isAdj || arg0.getValueIsAdjusting()) return
                sele = jlst.getSelectedValue()
            }
        })
    }

    private fun ini() {
        add(back)
        add(jspst)
        add(show)
        add(relo)
        add(play)
        setList()
        addListeners()
    }

    private fun setList() {
        change(this, { p: MusicEditPage? ->
            var ind: Int = jlst.getSelectedIndex()
            val arr: Array<Music> = pack.musics.getList().toTypedArray()
            jlst.setListData(arr)
            if (ind < 0) ind = 0
            if (ind >= arr.size) ind = arr.size - 1
            jlst.setSelectedIndex(ind)
            if (ind >= 0) sele = arr[ind]
        })
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        pack = ac
        ini()
        resized()
    }
}
