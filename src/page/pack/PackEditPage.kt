package page.pack

import common.pack.Context.SupExc
import common.pack.PackData.UserPack
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.pack.UserProfile
import common.util.Data
import common.util.anim.AnimCE
import common.util.stage.MapColc
import common.util.stage.StageMap
import common.util.unit.AbEnemy
import common.util.unit.Enemy
import main.Opts
import page.JBTN
import page.JL
import page.JTF
import page.Page
import page.info.StageViewPage
import page.info.edit.EnemyEditPage
import page.info.edit.StageEditPage
import page.support.AnimLCR
import page.support.ReorderList
import page.support.ReorderListener
import page.view.BGViewPage
import page.view.CastleViewPage
import page.view.EnemyViewPage
import page.view.MusicPage
import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class PackEditPage(p: Page?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val vpack: Vector<UserPack?> = Vector<UserPack>(UserProfile.Companion.getUserPacks())
    private val jlp: JList<UserPack> = JList<UserPack>(vpack)
    private val jspp: JScrollPane = JScrollPane(jlp)
    private val jle: JList<Enemy> = JList<Enemy>()
    private val jspe: JScrollPane = JScrollPane(jle)
    private val jld: JList<AnimCE> = JList<AnimCE>(Vector<AnimCE>(AnimCE.Companion.map().values))
    private val jspd: JScrollPane = JScrollPane(jld)
    private val jls: ReorderList<StageMap> = ReorderList<StageMap>()
    private val jsps: JScrollPane = JScrollPane(jls)
    private val jlr: JList<UserPack> = JList<UserPack>()
    private val jspr: JScrollPane = JScrollPane(jlr)
    private val jlt: JList<UserPack> = JList<UserPack>(vpack)
    private val jspt: JScrollPane = JScrollPane(jlt)
    private val addp: JBTN = JBTN(0, "add")
    private val remp: JBTN = JBTN(0, "rem")
    private val adde: JBTN = JBTN(0, "add")
    private val reme: JBTN = JBTN(0, "rem")
    private val adds: JBTN = JBTN(0, "add")
    private val rems: JBTN = JBTN(0, "rem")
    private val addr: JBTN = JBTN(0, "add")
    private val remr: JBTN = JBTN(0, "rem")
    private val edit: JBTN = JBTN(0, "edit")
    private val sdiy: JBTN = JBTN(0, "sdiy")
    private val vene: JBTN = JBTN(0, "vene")
    private val extr: JBTN = JBTN(0, "extr")
    private val vcas: JBTN = JBTN(0, "vcas")
    private val vbgr: JBTN = JBTN(0, "vbgr")
    private val vrcg: JBTN = JBTN(0, "recg")
    private val vrlr: JBTN = JBTN(0, "relr")
    private val cunt: JBTN = JBTN(0, "cunt")
    private val ener: JBTN = JBTN(0, "ener")
    private val vmsc: JBTN = JBTN(0, "vmsc")
    private val unpk: JBTN = JBTN(0, "unpack")
    private val recd: JBTN = JBTN(0, "replay")
    private val jtfp: JTF = JTF()
    private val jtfe: JTF = JTF()
    private val jtfs: JTF = JTF()
    private val lbp: JL = JL(0, "pack")
    private val lbe: JL = JL(0, "enemy")
    private val lbd: JL = JL(0, "seleanim")
    private val lbs: JL = JL(0, "stage")
    private val lbr: JL = JL(0, "parent")
    private val lbt: JL = JL(0, "selepar")
    private var pac: UserPack? = null
    private var ene: Enemy? = null
    private var sm: StageMap? = null
    private var changing = false
    override fun renew() {
        setPack(pac)
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        var w = 50
        val dw = 150
        Page.Companion.set(lbp, x, y, w, 100, 400, 50)
        Page.Companion.set(jspp, x, y, w, 150, 400, 600)
        Page.Companion.set(addp, x, y, w, 800, 200, 50)
        Page.Companion.set(remp, x, y, w + 200, 800, 200, 50)
        Page.Companion.set(jtfp, x, y, w, 850, 400, 50)
        Page.Companion.set(extr, x, y, w, 950, 200, 50)
        Page.Companion.set(unpk, x, y, w + 200, 950, 200, 50)
        w += 450
        Page.Companion.set(lbe, x, y, w, 100, 300, 50)
        Page.Companion.set(jspe, x, y, w, 150, 300, 600)
        Page.Companion.set(adde, x, y, w, 800, 150, 50)
        Page.Companion.set(reme, x, y, w + dw, 800, 150, 50)
        Page.Companion.set(jtfe, x, y, w, 850, 300, 50)
        Page.Companion.set(edit, x, y, w, 950, 300, 50)
        Page.Companion.set(vene, x, y, w, 1050, 300, 50)
        Page.Companion.set(ener, x, y, w, 1150, 300, 50)
        w += 300
        Page.Companion.set(lbd, x, y, w, 100, 300, 50)
        Page.Companion.set(jspd, x, y, w, 150, 300, 600)
        w += 50
        Page.Companion.set(vbgr, x, y, w, 850, 250, 50)
        Page.Companion.set(vcas, x, y, w, 950, 250, 50)
        Page.Companion.set(vrcg, x, y, w, 1050, 250, 50)
        Page.Companion.set(vrlr, x, y, w, 1150, 250, 50)
        w += 300
        Page.Companion.set(lbs, x, y, w, 100, 300, 50)
        Page.Companion.set(jsps, x, y, w, 150, 300, 600)
        Page.Companion.set(adds, x, y, w, 800, 150, 50)
        Page.Companion.set(rems, x, y, w + dw, 800, 150, 50)
        Page.Companion.set(jtfs, x, y, w, 850, 300, 50)
        Page.Companion.set(sdiy, x, y, w, 950, 300, 50)
        Page.Companion.set(cunt, x, y, w, 1050, 300, 50)
        Page.Companion.set(vmsc, x, y, w, 1150, 300, 50)
        w += 350
        Page.Companion.set(lbr, x, y, w, 100, 350, 50)
        Page.Companion.set(jspr, x, y, w, 150, 350, 600)
        Page.Companion.set(addr, x, y, w, 800, 175, 50)
        Page.Companion.set(remr, x, y, w + 175, 800, 175, 50)
        Page.Companion.set(recd, x, y, w, 950, 300, 50)
        w += 350
        Page.Companion.set(lbt, x, y, w, 100, 350, 50)
        Page.Companion.set(jspt, x, y, w, 150, 350, 600)
    }

    private fun addListeners() {
        back.setLnr(Consumer { x: ActionEvent? -> changePanel(front) })
        recd.setLnr(Consumer { x: ActionEvent? -> changePanel(RecdPackPage(this, pac)) })
        vcas.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (pac != null && pac.editable) changePanel(CastleEditPage(getThis(), pac)) else if (pac != null) changePanel(CastleViewPage(getThis(), pac.castles))
            }
        })
        vbgr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (pac != null && pac.editable) changePanel(BGEditPage(getThis(), pac)) else if (pac != null) changePanel(BGViewPage(getThis(), pac.getID()))
            }
        })
        vrcg.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (pac != null && pac.editable) changePanel(CGLREditPage(getThis(), pac)) else changePanel(CharaGroupPage(getThis(), pac, true))
            }
        })
        vrlr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (pac != null && pac.editable) changePanel(CGLREditPage(getThis(), pac)) else changePanel(LvRestrictPage(getThis(), pac, true))
            }
        })
        jld.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (jld.valueIsAdjusting) return
                adde.isEnabled = pac != null && jld.selectedValue != null && pac.editable
            }
        })
    }

    private fun `addListeners$1`() {
        addp.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val str: String = Opts.read("pack ID = ") // FIXME
                pac = Data.Companion.err<UserPack>(SupExc<UserPack> { UserProfile.Companion.initJsonPack(str) })
                vpack.add(pac)
                jlp.setListData(vpack)
                jlt.setListData(vpack)
                jlp.setSelectedValue(pac, true)
                setPack(pac)
                changing = false
            }
        })
        remp.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (!Opts.conf()) return
                changing = true
                var ind: Int = jlp.selectedIndex
                UserProfile.Companion.remove(pac)
                pac.delete()
                vpack.remove(pac)
                jlp.setListData(vpack)
                jlt.setListData(vpack)
                if (ind > 0) ind--
                jlp.selectedIndex = ind
                setPack(jlp.selectedValue)
                changing = false
            }
        })
        jlp.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jlp.valueIsAdjusting) return
                changing = true
                setPack(jlp.selectedValue)
                changing = false
            }
        })
        jtfp.addFocusListener(object : FocusAdapter() {
            override fun focusLost(fe: FocusEvent?) {
                val str: String = jtfp.text.trim { it <= ' ' }
                if (pac.desc.name == str) return
                pac.desc.name = str
            }
        })
        extr.setLnr(Consumer { x: ActionEvent? -> })
        unpk.setLnr(Consumer { x: ActionEvent? ->
            val str: String = Opts.read("password: ") // FIXME
            Data.Companion.err<Workspace>(SupExc<Workspace> { (pac.source as ZipSource).unzip(str) })
            unpk.isEnabled = false
            extr.isEnabled = true
        })
    }

    private fun `addListeners$2`() {
        jle.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (changing || jle.valueIsAdjusting) return
                changing = true
                setEnemy(jle.selectedValue)
                changing = false
            }
        })
        adde.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val ce = CustomEnemy()
                val anim: AnimCE = jld.selectedValue
                val e = Enemy(pac.getNextID<Enemy, AbEnemy>(Enemy::class.java), anim, ce)
                pac.enemies.add(e)
                jle.setListData(pac.enemies.getList().toTypedArray())
                jle.setSelectedValue(e, true)
                setEnemy(e)
                changing = false
            }
        })
        reme.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (!Opts.conf()) return
                changing = true
                var ind: Int = jle.selectedIndex
                pac.enemies.remove(ene)
                jle.setListData(pac.enemies.getList().toTypedArray())
                if (ind >= 0) ind--
                jle.selectedIndex = ind
                setEnemy(jle.selectedValue)
                changing = false
            }
        })
        edit.setLnr(Supplier<Page> { EnemyEditPage(getThis(), ene) })
        jtfe.setLnr(Consumer<FocusEvent> { e: FocusEvent? -> ene.name = jtfe.text.trim { it <= ' ' } })
        vene.setLnr(Supplier<Page> { EnemyViewPage(getThis(), pac.getID()) })
        ener.setLnr(Supplier<Page> { EREditPage(getThis(), pac) })
    }

    private fun `addListeners$3`() {
        sdiy.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (pac.editable) changePanel(StageEditPage(getThis(), pac.mc, pac)) else {
                    val lmc: List<MapColc> = Arrays.asList(*arrayOf<MapColc>(pac.mc))
                    changePanel(StageViewPage(getThis(), lmc))
                }
            }
        })
        cunt.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(UnitManagePage(getThis(), pac))
            }
        })
        vmsc.setLnr(Supplier<Page> { if (pac.editable) MusicEditPage(getThis(), pac) else MusicPage(getThis(), pac.musics.getList()) })
        jls.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jls.valueIsAdjusting) return
                changing = true
                setMap(jls.selectedValue)
                changing = false
            }
        })
        jls.list = object : ReorderListener<StageMap?> {
            override fun reordered(ori: Int, fin: Int) {
                val lsm: MutableList<StageMap> = ArrayList<StageMap>()
                for (sm in pac.mc.maps) lsm.add(sm)
                val sm: StageMap = lsm.removeAt(ori)
                lsm.add(fin, sm)
                pac.mc.maps = lsm.toTypedArray()
                changing = false
            }

            override fun reordering() {
                changing = true
            }
        }
        jtfs.setLnr(Consumer<FocusEvent> { x: FocusEvent? -> if (sm != null) sm.name = jtfs.text.trim { it <= ' ' } })
        adds.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val map = StageMap(pac.mc)
                val maps: Array<StageMap> = pac.mc.maps
                pac.mc.maps = Arrays.copyOf(maps, maps.size + 1)
                pac.mc.maps.get(maps.size) = map
                jls.setListData(pac.mc.maps)
                jls.setSelectedValue(map, true)
                setMap(map)
                changing = false
            }
        })
        rems.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (!Opts.conf()) return
                changing = true
                var ind: Int = jls.selectedIndex
                val n: Int = pac.mc.maps.size
                val maps: Array<StageMap?> = arrayOfNulls<StageMap>(n - 1)
                for (i in 0 until ind) maps[i] = pac.mc.maps.get(i)
                for (i in ind + 1 until n) maps[i - 1] = pac.mc.maps.get(i)
                pac.mc.maps = maps
                jls.setListData(maps)
                if (ind >= 0) ind--
                jls.selectedIndex = ind
                setMap(jls.selectedValue)
                changing = false
            }
        })
    }

    private fun `addListeners$4`() {
        jlr.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (changing || arg0.valueIsAdjusting) return
                changing = true
                setRely(jlr.selectedValue)
                changing = false
            }
        })
        jlt.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (changing || arg0.valueIsAdjusting) return
                checkAddr()
            }
        })
        addr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val rel: UserPack = jlt.selectedValue
                pac.desc.dependency.add(rel.getID())
                for (id in rel.desc.dependency) if (!pac.desc.dependency.contains(id)) pac.desc.dependency.add(id)
                updateJlr()
                jlr.setSelectedValue(rel, true)
                setRely(rel)
                changing = false
            }
        })
        remr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val ind: Int = jlr.selectedIndex - 1
                val rel: UserPack = jlr.selectedValue
                if (pac.relyOn(rel.getID())) if (Opts.conf("this action cannot be undone. Are you sure to remove "
                                + "all elements in this pack from the selected parent?")) pac.forceRemoveParent(rel.getID())
                pac.desc.dependency.remove(rel.getID())
                updateJlr()
                jlr.selectedIndex = ind
                setRely(jlr.selectedValue)
                changing = false
            }
        })
    }

    private fun checkAddr() {
        if (pac == null) {
            addr.isEnabled = false
            return
        }
        val rel: UserPack = jlt.selectedValue
        var b: Boolean = pac.editable
        b = b and (rel != null && !pac.desc.dependency.contains(rel.getID()))
        b = b and (rel !== pac)
        if (b) for (id in rel.desc.dependency) if (id == pac.getID()) b = false
        addr.isEnabled = b
    }

    private fun ini() {
        add(back)
        add(jspp)
        add(jspe)
        add(jspd)
        add(addp)
        add(remp)
        add(jtfp)
        add(adde)
        add(reme)
        add(jtfe)
        add(edit)
        add(sdiy)
        add(jsps)
        add(adds)
        add(rems)
        add(jtfs)
        add(extr)
        add(jspr)
        add(jspt)
        add(addr)
        add(remr)
        add(vene)
        add(lbp)
        add(lbe)
        add(lbd)
        add(lbs)
        add(lbr)
        add(lbt)
        add(cunt)
        add(vcas)
        add(vrcg)
        add(vrlr)
        add(vbgr)
        add(ener)
        add(vmsc)
        add(unpk)
        add(recd)
        jle.cellRenderer = AnimLCR()
        jld.setCellRenderer(AnimLCR())
        setPack(null)
        addListeners()
        `addListeners$1`()
        `addListeners$2`()
        `addListeners$3`()
        `addListeners$4`()
    }

    private fun setEnemy(e: Enemy?) {
        ene = e
        val b = e != null && pac.editable
        edit.isEnabled = e != null && e.de is CustomEnemy
        jtfe.isEnabled = b
        reme.isEnabled = b
        if (b) {
            jtfe.text = e.name
            reme.isEnabled = e.findApp(pac.mc).size == 0
        }
    }

    private fun setMap(map: StageMap?) {
        sm = map
        rems.isEnabled = sm != null && pac.editable
        jtfs.isEnabled = sm != null && pac.editable
        if (sm != null) jtfs.text = map.name
    }

    private fun setPack(pack: UserPack?) {
        pac = pack
        val b = pac != null && pac.editable
        remp.isEnabled = pac != null
        jtfp.isEnabled = b
        adde.isEnabled = b && jld.selectedValue != null
        adds.isEnabled = b
        extr.isEnabled = pac != null
        vcas.isEnabled = pac != null
        vbgr.isEnabled = pac != null
        vene.isEnabled = pac != null
        vmsc.isEnabled = pac != null
        recd.isEnabled = pac != null
        val canUnpack = pac != null && !pac.editable
        val canExport = pac != null && pac.editable
        unpk.isEnabled = canUnpack
        extr.isEnabled = canExport
        if (b) jtfp.text = pack.desc.name
        if (pac == null) {
            jle.setListData(arrayOfNulls<Enemy>(0))
            jlr.setListData(arrayOfNulls<UserPack>(0))
        } else {
            jle.setListData(pac.enemies.getList().toTypedArray())
            jle.clearSelection()
            updateJlr()
            jlr.clearSelection()
        }
        checkAddr()
        val b0 = pac != null
        sdiy.isEnabled = b0
        if (b0) {
            jls.setListData(pac.mc.maps)
            jls.clearSelection()
        } else jls.setListData(arrayOfNulls<StageMap>(0))
        setRely(null)
        setMap(null)
        setEnemy(null)
    }

    private fun setRely(rel: UserPack?) {
        if (pac == null || rel == null) {
            remr.isEnabled = false
            return
        }
        val re: Boolean = pac.relyOn(rel.getID())
        remr.setText(0, if (re) "rema" else "rem")
        remr.foreground = if (re) Color.RED else Color.BLACK
        remr.isEnabled = true
    }

    private fun updateJlr() {
        val rel: Array<UserPack?> = arrayOfNulls<UserPack>(pac.desc.dependency.size)
        for (i in pac.desc.dependency.indices) rel[i] = UserProfile.Companion.getUserPack(pac.desc.dependency.get(i))
        jlr.setListData(rel)
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
    }
}
