package page.pack

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
import common.system.VImg
import common.util.Data
import common.util.stage.CastleImg
import common.util.stage.CastleList
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JBTN
import page.JL
import page.Page
import page.anim.AnimBox
import page.support.Exporter
import page.support.Importer
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import utilpc.UtilPC
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.OutputStream
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class CastleEditPage(p: Page?, ac: UserPack) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val jlst: JList<CastleImg> = JList<CastleImg>()
    private val jspst: JScrollPane = JScrollPane(jlst)
    private val jl = JLabel()
    private val addc: JBTN = JBTN(0, "add")
    private val remc: JBTN = JBTN(0, "rem")
    private val impc: JBTN = JBTN(0, "import")
    private val expc: JBTN = JBTN(0, "export")
    private val pack: UserPack
    private val cas: CastleList
    private var changing = false
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jspst, x, y, 50, 100, 300, 1000)
        Page.Companion.set(addc, x, y, 400, 100, 200, 50)
        Page.Companion.set(impc, x, y, 400, 200, 200, 50)
        Page.Companion.set(expc, x, y, 400, 300, 200, 50)
        Page.Companion.set(remc, x, y, 400, 400, 200, 50)
        Page.Companion.set(jl, x, y, 800, 50, 1000, 1000)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        jlst.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (changing || arg0.getValueIsAdjusting()) return
                val img: CastleImg = jlst.getSelectedValue()
                var ic: ImageIcon? = null
                if (img != null) {
                    val s: VImg = img.img
                    if (s != null) ic = UtilPC.getIcon(s)
                }
                jl.icon = ic
            }
        })
        addc.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                getFile("Choose your file", null)
            }
        })
        impc.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val img: CastleImg = jlst.getSelectedValue()
                if (img != null) getFile("Choose your file", img)
            }
        })
        expc.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val img: CastleImg = jlst.getSelectedValue()
                if (img != null) {
                    val s: VImg = img.img
                    if (s != null) Exporter(s.getImg().bimg() as BufferedImage, Exporter.Companion.EXP_IMG)
                }
            }
        })
        remc.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val img: CastleImg = jlst.getSelectedValue()
                if (img != null) {
                    cas.remove(img)
                    (pack.source as Workspace).getCasFile(img.getID()).delete()
                    changing = true
                    setList()
                    changing = false
                }
            }
        })
    }

    private fun getFile(str: String, vimg: CastleImg?) {
        var vimg: CastleImg? = vimg
        val bimg: BufferedImage = Importer(str).getImg() ?: return
        if (bimg.getWidth() != 128 && bimg.getHeight() != 256) {
            getFile("Wrong img size. Img size: w=128, h=256", vimg)
            return
        }
        if (vimg == null) cas.add(CastleImg(pack.getNextID<CastleImg, CastleImg>(CastleImg::class.java), VImg(bimg)).also { vimg = it }) else vimg.img.setImg(bimg)
        try {
            val os: OutputStream = (pack.source as Workspace).writeFile("castles/" + Data.Companion.trio(vimg.id.id) + ".png")
            ImageIO.write(bimg, "PNG", os)
            os.close()
        } catch (e: IOException) {
            e.printStackTrace()
            getFile("Failed to save file", vimg)
            return
        }
        changing = true
        setList()
        changing = false
    }

    private fun ini() {
        add(back)
        add(jspst)
        add(jl)
        add(addc)
        add(remc)
        add(impc)
        add(expc)
        setList()
        addListeners()
    }

    private fun setList() {
        var ind: Int = jlst.getSelectedIndex()
        jlst.setListData(cas.getList().toTypedArray())
        if (ind < 0) ind = 0
        if (ind >= cas.size()) ind = cas.size() - 1
        jlst.setSelectedIndex(ind)
        val img: CastleImg = jlst.getSelectedValue()
        jl.icon = if (img == null) null else UtilPC.getIcon(img.img)
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        pack = ac
        cas = ac.castles
        ini()
        resized()
    }
}
