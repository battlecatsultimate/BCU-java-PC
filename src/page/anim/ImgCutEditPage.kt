package page.anim

import common.CommonStatic
import common.pack.Source.Workspace
import common.pack.UserProfile
import common.system.VImg
import common.system.fake.FakeImage
import common.system.fake.FakeImage.Marker
import common.util.anim.AnimCE
import common.util.anim.ImgCut
import main.MainBCU
import main.Opts
import page.JBTN
import page.JTF
import page.Page
import page.support.AnimLCR
import page.support.Exporter
import page.support.Importer
import utilpc.Algorithm
import utilpc.Algorithm.SRResult
import utilpc.ReColor
import utilpc.UtilPC
import java.awt.Graphics
import java.awt.Rectangle
import java.awt.event.*
import java.awt.image.BufferedImage
import java.io.IOException
import java.util.*
import java.util.function.Consumer
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class ImgCutEditPage : Page, AbEditPage {
    private val jtf: JTF = JTF()
    private val resz: JTF = JTF("resize to: _%")
    private val back: JBTN = JBTN(0, "back")
    private val add: JBTN = JBTN(0, "add")
    private val rem: JBTN = JBTN(0, "rem")
    private val copy: JBTN = JBTN(0, "copy")
    private val addl: JBTN = JBTN(0, "addl")
    private val reml: JBTN = JBTN(0, "reml")
    private val relo: JBTN = JBTN(0, "relo")
    private val save: JBTN = JBTN(0, "saveimg")
    private val swcl: JBTN = JBTN(0, "swcl")
    private val impt: JBTN = JBTN(0, "import")
    private val expt: JBTN = JBTN(0, "export")
    private val ico: JBTN = JBTN(0, "icon")
    private val loca: JBTN = JBTN(0, "localize")
    private val merg: JBTN = JBTN(0, "merge")
    private val spri: JBTN = JBTN(0, "sprite")
    private val icon = JLabel()
    private val jlu: JList<AnimCE> = JList<AnimCE>()
    private val jspu: JScrollPane = JScrollPane(jlu)
    private val jlf: JList<String> = JList<String>(ReColor.strs)
    private val jspf: JScrollPane = JScrollPane(jlf)
    private val jlt: JList<String> = JList<String>(ReColor.strf)
    private val jspt: JScrollPane = JScrollPane(jlt)
    private val icet: ImgCutEditTable = ImgCutEditTable()
    private val jspic: JScrollPane = JScrollPane(icet)
    private val sb: SpriteBox = SpriteBox(this)
    private val aep: EditHead
    private var sep: SpriteEditPage? = null
    private var changing = false

    constructor(p: Page?) : super(p) {
        aep = EditHead(this, 1)
        if (aep.focus == null) jlu.setListData(Vector<AnimCE>(AnimCE.Companion.map().values)) else jlu.setListData(arrayOf<AnimCE?>(aep.focus))
        ini()
        resized()
    }

    constructor(p: Page?, bar: EditHead) : super(p) {
        aep = bar
        if (aep.focus == null) jlu.setListData(Vector<AnimCE>(Workspace.Companion.loadAnimations(null))) else jlu.setListData(arrayOf<AnimCE?>(aep.focus))
        ini()
        resized()
    }

    override fun callBack(o: Any?) {
        changing = true
        if (sb.sele >= 0) {
            icet.selectionModel.setSelectionInterval(sb.sele, sb.sele)
            val h: Int = icet.rowHeight
            icet.scrollRectToVisible(Rectangle(0, h * sb.sele, 1, h))
        } else icet.clearSelection()
        setB(sb.sele)
        changing = false
    }

    override fun setSelection(ac: AnimCE?) {
        changing = true
        jlu.setSelectedValue(ac, true)
        setA(ac)
        changing = false
    }

    override fun keyPressed(ke: KeyEvent) {
        if (ke.source === sb) sb.keyPressed(ke)
    }

    override fun keyReleased(ke: KeyEvent) {
        if (ke.source === sb) sb.keyReleased(ke)
    }

    override fun keyTyped(ke: KeyEvent) {
        if (ke.source === sb) sb.keyTyped(ke)
    }

    override fun mouseDragged(me: MouseEvent) {
        if (me.source === sb) sb.mouseDragged(me.point)
    }

    override fun mousePressed(me: MouseEvent) {
        if (me.source === sb) sb.mousePressed(me.point)
    }

    override fun mouseReleased(me: MouseEvent) {
        if (me.source === sb) sb.mouseReleased(me.point)
    }

    override fun renew() {
        if (sep != null && Opts.conf("Do you want to save edited sprite?")) try {
            icet.anim.setNum(FakeImage.Companion.read(sep.getEdit()))
            icet.anim.saveImg()
            icet.anim.reloImg()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        sep = null
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(aep, x, y, 550, 0, 1750, 50)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(relo, x, y, 250, 0, 200, 50)
        Page.Companion.set(jspu, x, y, 0, 50, 300, 500)
        Page.Companion.set(add, x, y, 350, 200, 200, 50)
        Page.Companion.set(rem, x, y, 600, 200, 200, 50)
        Page.Companion.set(impt, x, y, 350, 250, 200, 50)
        Page.Companion.set(expt, x, y, 600, 250, 200, 50)
        Page.Companion.set(resz, x, y, 350, 300, 200, 50)
        Page.Companion.set(loca, x, y, 600, 300, 200, 50)
        Page.Companion.set(merg, x, y, 350, 350, 200, 50)
        Page.Companion.set(spri, x, y, 600, 350, 200, 50)
        Page.Companion.set(jtf, x, y, 350, 100, 200, 50)
        Page.Companion.set(copy, x, y, 600, 100, 200, 50)
        Page.Companion.set(addl, x, y, 350, 500, 200, 50)
        Page.Companion.set(reml, x, y, 600, 500, 200, 50)
        Page.Companion.set(jspic, x, y, 50, 600, 800, 650)
        Page.Companion.set(sb, x, y, 900, 100, 1400, 900)
        Page.Companion.set(jspf, x, y, 900, 1050, 200, 200)
        Page.Companion.set(jspt, x, y, 1150, 1050, 200, 200)
        Page.Companion.set(swcl, x, y, 1400, 1050, 200, 50)
        Page.Companion.set(save, x, y, 1400, 1150, 200, 50)
        Page.Companion.set(ico, x, y, 1650, 1050, 200, 50)
        Page.Companion.set(icon, x, y, 1650, 1100, 400, 100)
        aep.componentResized(x, y)
        icet.rowHeight = Page.Companion.size(x, y, 50)
        sb.paint(sb.graphics)
    }

    private fun `addListeners$0`() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        add.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val bimg: BufferedImage = Importer("Add your sprite").getImg() ?: return
                changing = true
                val str: String = AnimCE.Companion.getAvailable("new anim")
                val ac = AnimCE(str)
                try {
                    ac.setNum(FakeImage.Companion.read(bimg))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                ac.saveImg()
                ac.createNew()
                val v: Vector<AnimCE> = Vector<AnimCE>(AnimCE.Companion.map().values)
                jlu.setListData(v)
                jlu.setSelectedValue(ac, true)
                setA(ac)
                changing = false
            }
        })
        impt.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val bimg: BufferedImage = Importer("Update your sprite").getImg()
                if (bimg != null) {
                    val ac: AnimCE = icet.anim
                    try {
                        ac.setNum(FakeImage.Companion.read(bimg))
                        ac.saveImg()
                        ac.reloImg()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })
        expt.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                Exporter(icet.anim.getNum().bimg() as BufferedImage, Exporter.Companion.EXP_IMG)
            }
        })
        jlu.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jlu.valueIsAdjusting) return
                changing = true
                setA(jlu.selectedValue)
                changing = false
            }
        })
        jtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(arg0: FocusEvent?) {
                changing = true
                var str: String = jtf.text.trim { it <= ' ' }
                str = MainBCU.validate(str)
                if (str.length == 0 || icet.anim == null || icet.anim.id.id == str) {
                    if (icet.anim != null) jtf.text = icet.anim.id.id
                    return
                }
                val rep: AnimCE = AnimCE.Companion.map().get(str)
                if (rep != null && Opts.conf(
                                "Do you want to replace animation? This action cannot be undone. The animation which originally keeps this name will be replaced by selected animation.")) {
                    icet.anim.renameTo(str)
                    for (pack in UserProfile.Companion.getUserPacks()) for (e in pack.enemies.getList()) if (e.anim === rep) e.anim = icet.anim
                    val v: Vector<AnimCE> = Vector<AnimCE>(AnimCE.Companion.map().values)
                    jlu.setListData(v)
                    jlu.setSelectedValue(rep, true)
                    setA(rep)
                } else {
                    str = AnimCE.Companion.getAvailable(str)
                    icet.anim.renameTo(str)
                    jtf.text = str
                }
                changing = false
            }
        })
        copy.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                var str: String = icet.anim.id.id
                str = AnimCE.Companion.getAvailable(str)
                val ac = AnimCE(str, icet.anim)
                ac.setEdi(icet.anim.getEdi())
                ac.setUni(icet.anim.getUni())
                val v: Vector<AnimCE> = Vector<AnimCE>(AnimCE.Companion.map().values)
                jlu.setListData(v)
                jlu.setSelectedValue(ac, true)
                setA(ac)
                changing = false
            }
        })
        rem.setLnr(Consumer { x: ActionEvent? ->
            if (!Opts.conf()) return@setLnr
            changing = true
            var ind: Int = jlu.selectedIndex
            val ac: AnimCE = icet.anim
            ac.delete()
            val v: Vector<AnimCE> = Vector<AnimCE>(AnimCE.Companion.map().values)
            jlu.setListData(v)
            if (ind >= v.size) ind--
            jlu.selectedIndex = ind
            setA(if (ind < 0) null else v[ind])
            changing = false
        }
        )
        loca.setLnr(Consumer { x: ActionEvent? ->
            if (!Opts.conf()) return@setLnr
            changing = true
            var ind: Int = jlu.selectedIndex
            val ac: AnimCE = icet.anim
            ac.check()
            ac.delete()
            val v: Vector<AnimCE> = Vector<AnimCE>(AnimCE.Companion.map().values)
            jlu.setListData(v)
            if (ind >= v.size) ind--
            jlu.selectedIndex = ind
            setA(v[ind])
            changing = false
        }
        )
        relo.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (icet.anim == null) return
                icet.anim.reloImg()
                icet.anim.ICedited()
            }
        })
        save.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                icet.anim.saveImg()
            }
        })
        ico.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val bimg: BufferedImage = Importer("select enemy icon").getImg() ?: return
                icet.anim.setEdi(VImg(bimg))
                icet.anim.saveIcon()
                if (icet.anim.getEdi() != null) icon.icon = UtilPC.getIcon(icet.anim.getEdi())
            }
        })
    }

    private fun `addListeners$1`() {
        val lsm: ListSelectionModel = icet.selectionModel
        lsm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || lsm.valueIsAdjusting) return
                changing = true
                setB(lsm.leadSelectionIndex)
                changing = false
            }
        })
        addl.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val ic: ImgCut = icet.ic
                val data: Array<IntArray> = ic.cuts
                val name: Array<String> = ic.strs
                ic.cuts = arrayOfNulls<IntArray>(++ic.n)
                ic.strs = arrayOfNulls<String>(ic.n)
                for (i in data.indices) {
                    ic.cuts.get(i) = data[i]
                    ic.strs.get(i) = name[i]
                }
                val ind: Int = icet.selectedRow
                if (ind >= 0) ic.cuts.get(ic.n - 1) = ic.cuts.get(ind).clone() else ic.cuts.get(ic.n - 1) = intArrayOf(0, 0, 1, 1)
                ic.strs.get(ic.n - 1) = ""
                icet.anim.unSave("imgcut add line")
                resized()
                lsm.setSelectionInterval(ic.n - 1, ic.n - 1)
                val h: Int = icet.rowHeight
                icet.scrollRectToVisible(Rectangle(0, h * (ic.n - 1), 1, h))
                setB(ic.n - 1)
                changing = false
            }
        })
        reml.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val ic: ImgCut = icet.ic
                var ind: Int = sb.sele
                val data: Array<IntArray> = ic.cuts
                val name: Array<String> = ic.strs
                ic.cuts = arrayOfNulls<IntArray>(--ic.n)
                ic.strs = arrayOfNulls<String>(ic.n)
                for (i in 0 until ind) {
                    ic.cuts.get(i) = data[i]
                    ic.strs.get(i) = name[i]
                }
                for (i in ind + 1 until data.size) {
                    ic.cuts.get(i - 1) = data[i]
                    ic.strs.get(i - 1) = name[i]
                }
                for (ints in icet.anim.mamodel.parts) if (ints[2] > ind) ints[2]--
                for (ma in icet.anim.anims) for (part in ma.parts) if (part.ints[1] == 2) for (ints in part.moves) if (ints[1] > ind) ints[1]--
                icet.anim.ICedited()
                icet.anim.unSave("imgcut remove line")
                if (ind >= ic.n) ind--
                lsm.setSelectionInterval(ind, ind)
                setB(ind)
                changing = false
            }
        })
        swcl.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val ind: Int = sb.sele
                var data: IntArray? = null
                if (ind >= 0) {
                    val ic: ImgCut = icet.ic
                    data = ic.cuts.get(ind)
                }
                ReColor.transcolor(icet.anim.getNum().bimg() as BufferedImage, data, jlf.selectedIndex,
                        jlt.selectedIndex)
                icet.anim.getNum().mark(Marker.RECOLORED)
                icet.anim.ICedited()
            }
        })
        jlf.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (jlf.selectedIndex == -1) jlf.selectedIndex = 0
            }
        })
        jlt.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (jlt.selectedIndex == -1) jlt.selectedIndex = 0
            }
        })
        resz.setLnr(Consumer<FocusEvent> { x: FocusEvent? ->
            val d: Double = CommonStatic.parseIntN(resz.text) * 0.01
            if (Opts.conf("do you want to resize sprite to $d%?")) {
                icet.anim.resize(d)
                icet.anim.ICedited()
                icet.anim.unSave("resized")
            }
            resz.text = "resize to: _%"
        })
        merg.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                changing = true
                val str: String = AnimCE.Companion.getAvailable("merged")
                val list: Array<AnimCE> = jlu.selectedValuesList.toTypedArray()
                val rect = Array(list.size) { IntArray(2) }
                for (i in list.indices) {
                    rect[i][0] = list[i].getNum().getWidth()
                    rect[i][1] = list[i].getNum().getHeight()
                }
                val ans: SRResult = Algorithm.stackRect(rect)
                val cen: AnimCE = list[ans.center]
                val ac = AnimCE(str, cen)
                val bimg = BufferedImage(ans.w, ans.h, BufferedImage.TYPE_INT_ARGB)
                val g: Graphics = bimg.graphics
                for (i in list.indices) {
                    val b: BufferedImage = list[i].getNum().bimg() as BufferedImage
                    val x: Int = ans.pos.get(i).get(0)
                    val y: Int = ans.pos.get(i).get(1)
                    g.drawImage(b, x, y, null)
                    if (i != ans.center) ac.merge(list[i], x, y)
                }
                try {
                    ac.setNum(FakeImage.Companion.read(bimg))
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
                ac.saveImg()
                ac.reloImg()
                ac.unSave("merge")
                val v: Vector<AnimCE> = Vector<AnimCE>(AnimCE.Companion.map().values)
                jlu.setListData(v)
                jlu.setSelectedValue(ac, true)
                setA(ac)
                changing = false
            }
        })
        spri.setLnr(Consumer { x: ActionEvent? -> changePanel(SpriteEditPage(this, icet.anim.getNum().bimg() as BufferedImage).also { sep = it }) })
    }

    private fun ini() {
        add(aep)
        add(resz)
        add(back)
        add(relo)
        add(save)
        add(swcl)
        add(jspu)
        add(jspic)
        add(add)
        add(rem)
        add(copy)
        add(addl)
        add(reml)
        add(jtf)
        add(sb)
        add(jspf)
        add(jspt)
        add(impt)
        add(expt)
        add(icon)
        add(loca)
        add(ico)
        add(merg)
        add(spri)
        add.isEnabled = aep.focus == null
        jtf.isEnabled = aep.focus == null
        relo.isEnabled = aep.focus == null
        swcl.isEnabled = aep.focus == null
        jlu.cellRenderer = AnimLCR()
        setA(null)
        jlf.selectedIndex = 0
        jlt.selectedIndex = 1
        `addListeners$0`()
        `addListeners$1`()
    }

    private fun setA(anim: AnimCE?) {
        val boo = changing
        changing = true
        aep.setAnim(anim)
        addl.isEnabled = anim != null
        swcl.isEnabled = anim != null
        save.isEnabled = anim != null
        resz.isEditable = anim != null
        icet.setCut(anim)
        sb.setAnim(anim)
        if (sb.sele == -1) icet.clearSelection()
        jtf.isEnabled = anim != null
        jtf.text = if (anim == null) "" else anim.id.id
        val del = anim != null && anim.deletable()
        rem.isEnabled = aep.focus == null && anim != null && del
        loca.isEnabled = aep.focus == null && anim != null && !del && !anim.inPool()
        copy.isEnabled = aep.focus == null && anim != null
        impt.isEnabled = anim != null
        expt.isEnabled = anim != null
        spri.isEnabled = anim != null
        merg.isEnabled = jlu.selectedValuesList.size > 1
        if (anim != null && anim.getEdi() != null) icon.icon = UtilPC.getIcon(anim.getEdi())
        setB(sb.sele)
        changing = boo
    }

    private fun setB(row: Int) {
        sb.sele = icet.selectedRow
        reml.isEnabled = sb.sele != -1
        if (sb.sele >= 0) {
            for (ints in icet.anim.mamodel.parts) if (ints[2] == sb.sele) reml.isEnabled = false
            for (ma in icet.anim.anims) for (part in ma.parts) if (part.ints[1] == 2) for (ints in part.moves) if (ints[1] == sb.sele) reml.isEnabled = false
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
