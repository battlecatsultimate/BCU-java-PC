package page.info.edit

import common.CommonStatic
import common.battle.Basis
import common.battle.BasisSet
import common.pack.IndexContainer.Indexable
import common.pack.PackData
import common.pack.UserProfile
import common.util.Animable
import common.util.Data
import common.util.Data.Proc.IMU
import common.util.anim.AnimCE
import common.util.anim.AnimU
import common.util.anim.AnimU.UType
import common.util.lang.Editors
import common.util.pack.Background
import common.util.pack.Soul
import common.util.pack.Soul.SoulType
import common.util.unit.AbEnemy
import common.util.unit.Enemy
import common.util.unit.Form
import common.util.unit.Unit
import main.Opts
import page.*
import page.anim.DIYViewPage
import page.info.edit.ProcTable.MainProcTable
import page.info.edit.SwingEditor.EditCtrl
import page.info.edit.SwingEditor.IdEditor
import page.info.filter.EnemyFindPage
import page.info.filter.UnitFindPage
import page.support.ListJtfPolicy
import page.support.ReorderList
import page.support.ReorderListener
import page.view.BGViewPage
import page.view.EnemyViewPage
import page.view.UnitViewPage
import utilpc.Interpret
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.FocusEvent
import java.util.*
import java.util.function.Consumer
import javax.swing.DefaultComboBoxModel
import javax.swing.JComboBox
import javax.swing.JScrollPane
import javax.swing.SwingConstants
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

abstract class EntityEditPage(p: Page?, pac: String, e: CustomEntity, edit: Boolean, isEnemy: Boolean) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val lhp: JL = JL(1, "HP")
    private val lhb: JL = JL(1, "HB")
    private val lsp: JL = JL(1, "speed")
    private val lra: JL = JL(1, "range")
    private val lwd: JL = JL(1, "width")
    private val lsh: JL = JL(1, "shield")
    private val ltb: JL = JL(1, "TBA")
    private val lbs: JL = JL(1, "tbase")
    private val ltp: JL = JL(1, "type")
    private val lct: JL = JL(1, "count")
    private val fhp: JTF = JTF()
    private val fhb: JTF = JTF()
    private val fsp: JTF = JTF()
    private val fra: JTF = JTF()
    private val fwd: JTF = JTF()
    private val fsh: JTF = JTF()
    private val ftb: JTF = JTF()
    private val fbs: JTF = JTF()
    private val ftp: JTF = JTF()
    private val fct: JTF = JTF()
    private val jli: ReorderList<String> = ReorderList<String>()
    private val jspi: JScrollPane = JScrollPane(jli)
    private val add: JBTN = JBTN(0, "add")
    private val rem: JBTN = JBTN(0, "rem")
    private val copy: JBTN = JBTN(0, "copy")
    private val link: JBTN = JBTN(0, "link")
    private val comm: JTG = JTG(1, "common")
    private val atkn: JTF = JTF()
    private val lpst: JL = JL(1, "postaa")
    private val vpst: JL = JL()
    private val litv: JL = JL(1, "atkf")
    private val lrev: JL = JL(1, "post-HB")
    private val lres: JL = JL(1, "post-death")
    private val vrev: JL = JL()
    private val vres: JL = JL()
    private val vitv: JL = JL()
    private val jcba: JComboBox<AnimCE> = JComboBox<AnimCE>()
    private val jcbs: JComboBox<Soul> = JComboBox<Soul>()
    private val ljp: ListJtfPolicy = ListJtfPolicy()
    private val aet: AtkEditTable
    private val mpt: MainProcTable
    private val jspm: JScrollPane
    private val ce: CustomEntity
    private val pack: String
    private var changing = false
    private var efp: EnemyFindPage? = null
    private var ufp: UnitFindPage? = null
    private var sup: SupPage<out Indexable<*, *>>? = null
    private var supEditor: IdEditor<*>? = null
    protected val editable: Boolean
    protected val bas: Basis = BasisSet.Companion.current()
    override fun callBack(o: Any?) {
        if (o is IntArray) {
            val vals = o
            if (vals.size == 3) {
                ce.type = vals[0]
                ce.abi = vals[1]
                for (i in Interpret.ABIIND.indices) {
                    val id: Int = Interpret.ABIIND.get(i) - 100
                    if (vals[2] and (1 shl id - Interpret.IMUSFT) > 0) (ce.getProc().getArr(id) as IMU).mult = 100 else (ce.getProc().getArr(id) as IMU).mult = 0
                }
                ce.loop = if (ce.abi and Data.Companion.AB_GLASS > 0) 1 else -1
            }
        }
        setData(ce)
    }

    fun getBGSup(): SupPage<Background> {
        return BGViewPage(this, pack)
    }

    fun getEnemySup(): SupPage<AbEnemy> {
        return EnemyFindPage(this, pack) // FIXME
    }

    fun getUnitSup(): SupPage<Unit> {
        return UnitFindPage(this, pack)
    }

    fun <T : Indexable<*, T>?> putWait(editor: IdEditor<T>?, sup: SupPage<T>) {
        supEditor = editor
        this.sup = sup
    }

    protected open fun getAtk(): Double {
        return 1
    }

    protected open fun getDef(): Double {
        return 1
    }

    protected abstract fun getInput(jtf: JTF, v: Int)
    protected open fun ini() {
        set(lhp)
        set(lhb)
        set(lsp)
        set(lwd)
        set(lsh)
        set(lra)
        set(ltb)
        set(lbs)
        set(ltp)
        set(lct)
        set(fhp)
        set(fhb)
        set(fsp)
        set(fsh)
        set(fwd)
        set(fra)
        set(ftb)
        set(fbs)
        set(ftp)
        set(fct)
        ljp.end()
        add(jspi)
        add(aet)
        add(jspm)
        add(add)
        add(rem)
        add(copy)
        add(link)
        add(back)
        set(atkn)
        set(lpst)
        set(vpst)
        set(litv)
        set(vitv)
        set(lrev)
        set(lres)
        set(vrev)
        set(vres)
        add(comm)
        add(jcbs)
        val vec: Vector<Soul?> = Vector<Soul?>()
        vec.add(null)
        vec.addAll(UserProfile.Companion.getAll<Soul>(pack, Soul::class.java))
        jcbs.setModel(DefaultComboBoxModel<Soul>(vec))
        if (editable) {
            add(jcba)
            val vda: Vector<AnimCE> = Vector<AnimCE>()
            val ac: AnimCE = ce.getPack().anim as AnimCE
            if (!ac.inPool()) vda.add(ac)
            vda.addAll(AnimCE.Companion.map().values)
            jcba.setModel(DefaultComboBoxModel<AnimCE>(vda))
        }
        focusTraversalPolicy = ljp
        isFocusCycleRoot = true
        addListeners()
        atkn.setToolTipText("<html>use name \"revenge\" for attack during HB animation<br>"
                + "use name \"resurrection\" for attack during death animation</html>")
        ftp.setToolTipText(
                "<html>" + "+1 for normal attack<br>" + "+2 to attack kb<br>" + "+4 to attack underground<br>"
                        + "+8 to attack corpse<br>" + "+16 to attack soul<br>" + "+32 to attack ghost</html>")
        add.setEnabled(editable)
        rem.setEnabled(editable)
        copy.setEnabled(editable)
        link.setEnabled(editable)
        atkn.setEnabled(editable)
        comm.setEnabled(editable)
        jcbs.setEnabled(editable)
    }

    override fun renew() {
        if (efp != null && efp.getSelected() != null && Opts.conf("do you want to overwrite stats? This operation cannot be undone")) {
            val e: Enemy = efp.getSelected()
            ce.importData(e.de)
            setData(ce)
        }
        if (ufp != null && ufp.getForm() != null && Opts.conf("do you want to overwrite stats? This operation cannot be undone")) {
            val f: Form = ufp.getForm()
            ce.importData(f.du)
            setData(ce)
        }
        if (sup != null && supEditor != null) {
            val obj: Indexable<*, *> = sup.getSelected()
            supEditor.callback(if (obj == null) null else obj.getID())
        }
        efp = null
        ufp = null
        sup = null
        supEditor = null
    }

    override fun resized(x: Int, y: Int) {
        setSize(x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(lhp, x, y, 50, 100, 100, 50)
        Page.Companion.set(fhp, x, y, 150, 100, 200, 50)
        Page.Companion.set(lhb, x, y, 50, 150, 100, 50)
        Page.Companion.set(fhb, x, y, 150, 150, 200, 50)
        Page.Companion.set(lsp, x, y, 50, 200, 100, 50)
        Page.Companion.set(fsp, x, y, 150, 200, 200, 50)
        Page.Companion.set(lsh, x, y, 50, 250, 100, 50)
        Page.Companion.set(fsh, x, y, 150, 250, 200, 50)
        Page.Companion.set(lwd, x, y, 50, 300, 100, 50)
        Page.Companion.set(fwd, x, y, 150, 300, 200, 50)
        Page.Companion.set(jspm, x, y, 0, 450, 350, 800)
        mpt.componentResized(x, y)
        Page.Companion.set(jspi, x, y, 550, 50, 300, 350)
        Page.Companion.set(add, x, y, 550, 400, 150, 50)
        Page.Companion.set(rem, x, y, 700, 400, 150, 50)
        Page.Companion.set(copy, x, y, 550, 450, 150, 50)
        Page.Companion.set(link, x, y, 700, 450, 150, 50)
        Page.Companion.set(comm, x, y, 550, 500, 300, 50)
        Page.Companion.set(atkn, x, y, 550, 550, 300, 50)
        Page.Companion.set(lra, x, y, 550, 650, 100, 50)
        Page.Companion.set(fra, x, y, 650, 650, 200, 50)
        Page.Companion.set(ltb, x, y, 550, 700, 100, 50)
        Page.Companion.set(ftb, x, y, 650, 700, 200, 50)
        Page.Companion.set(lbs, x, y, 550, 750, 100, 50)
        Page.Companion.set(fbs, x, y, 650, 750, 200, 50)
        Page.Companion.set(ltp, x, y, 550, 800, 100, 50)
        Page.Companion.set(ftp, x, y, 650, 800, 200, 50)
        Page.Companion.set(lct, x, y, 550, 850, 100, 50)
        Page.Companion.set(fct, x, y, 650, 850, 200, 50)
        Page.Companion.set(aet, x, y, 900, 50, 1400, 1000)
        Page.Companion.set(lpst, x, y, 900, 1050, 200, 50)
        Page.Companion.set(vpst, x, y, 1100, 1050, 200, 50)
        Page.Companion.set(litv, x, y, 900, 1100, 200, 50)
        Page.Companion.set(vitv, x, y, 1100, 1100, 200, 50)
        Page.Companion.set(jcba, x, y, 900, 1150, 400, 50)
        Page.Companion.set(lrev, x, y, 1600, 1050, 200, 50)
        Page.Companion.set(vrev, x, y, 1800, 1050, 100, 50)
        Page.Companion.set(lres, x, y, 1600, 1100, 200, 50)
        Page.Companion.set(vres, x, y, 1800, 1100, 100, 50)
        Page.Companion.set(jcbs, x, y, 1600, 1150, 300, 50)
    }

    protected fun set(jl: JL) {
        jl.setHorizontalAlignment(SwingConstants.CENTER)
        add(jl)
    }

    protected fun set(jtf: JTF) {
        jtf.setEditable(editable)
        add(jtf)
        ljp.add(jtf)
        jtf.setLnr(Consumer<FocusEvent> { e: FocusEvent? ->
            input(jtf, jtf.getText().trim { it <= ' ' })
            setData(ce)
        })
    }

    protected open fun setData(data: CustomEntity) {
        changing = true
        fhp.setText("" + (ce.hp * getDef()) as Int)
        fhb.setText("" + ce.hb)
        fsp.setText("" + ce.speed)
        fra.setText("" + ce.range)
        fwd.setText("" + ce.width)
        fsh.setText("" + ce.shield)
        ftb.setText("" + ce.tba)
        fbs.setText("" + ce.base)
        vpst.setText("" + ce.getPost())
        vitv.setText("" + ce.getItv())
        ftp.setText("" + ce.touch)
        fct.setText("" + ce.loop)
        comm.setSelected(data.common)
        mpt.setData(ce.rep.proc)
        val raw: Array<IntArray> = ce.rawAtkData()
        var pre = 0
        var n: Int = ce.atks.size
        if (ce.rev != null) n++
        if (ce.res != null) n++
        val ints = arrayOfNulls<String>(n)
        for (i in ce.atks.indices) {
            ints[i] = i + 1 + " " + ce.atks.get(i).str
            pre += raw[i][1]
            if (pre >= ce.getAnimLen()) ints[i] += " (out of range)"
        }
        var ix: Int = ce.atks.size
        if (ce.rev != null) ints[ix++] = ce.rev.str
        if (ce.res != null) ints[ix++] = ce.res.str
        var ind: Int = jli.getSelectedIndex()
        jli.setListData(ints)
        if (ind < 0) ind = 0
        if (ind >= ints.size) ind = ints.size - 1
        setA(ind)
        jli.setSelectedIndex(ind)
        val ene: Animable<AnimU<*>, UType> = ce.getPack()
        if (editable) jcba.setSelectedItem(ene.anim)
        jcbs.setSelectedItem(PackData.Identifier.Companion.get<Soul>(ce.death))
        vrev.setText(if (ce.rev == null) "x" else Data.Companion.KB_TIME.get(Data.Companion.INT_HB) - ce.rev.pre.toString() + "f")
        val s: Soul = PackData.Identifier.Companion.get<Soul>(ce.death)
        vres.setText(if (ce.res == null) "x" else if (s == null) "-" else s.len(SoulType.DEF) - ce.res.pre.toString() + "f")
        changing = false
    }

    protected fun subListener(e: JBTN, u: JBTN, a: JBTN, o: Any?) {
        e.setLnr(Consumer { x: ActionEvent? -> changePanel(EnemyFindPage(getThis(), null).also { efp = it }) })
        u.setLnr(Consumer { x: ActionEvent? -> changePanel(UnitFindPage(getThis(), null).also { ufp = it }) })
        a.setLnr(Consumer { x: ActionEvent? -> if (editable) changePanel(DIYViewPage(getThis(), jcba.getSelectedItem() as AnimCE)) else if (o is Unit) changePanel(UnitViewPage(getThis(), o as Unit?)) else if (o is Enemy) changePanel(EnemyViewPage(getThis(), o as Enemy?)) })
        e.setEnabled(editable)
        u.setEnabled(editable)
    }

    private fun addListeners() {
        back.setLnr(Consumer { e: ActionEvent? -> changePanel(front) })
        comm.setLnr(Consumer { e: ActionEvent? ->
            ce.common = comm.isSelected()
            setData(ce)
        })
        jli.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (changing || jli.getValueIsAdjusting()) return
                changing = true
                if (jli.getSelectedIndex() == -1) jli.setSelectedIndex(0)
                setA(jli.getSelectedIndex())
                changing = false
            }
        })
        jli.list = object : ReorderListener<String?> {
            override fun reordered(ori: Int, fin: Int) {
                var fin = fin
                if (ori < ce.atks.size) {
                    if (fin >= ce.atks.size) fin = ce.atks.size - 1
                    val l: MutableList<AtkDataModel> = ArrayList<AtkDataModel>()
                    for (adm in ce.atks) l.add(adm)
                    l.add(fin, l.removeAt(ori))
                    ce.atks = l.toTypedArray()
                }
                setData(ce)
                changing = false
            }

            override fun reordering() {
                changing = true
            }
        }
        add.setLnr(Consumer { e: ActionEvent? ->
            changing = true
            val n: Int = ce.atks.size
            var ind: Int = jli.getSelectedIndex()
            if (ind >= ce.atks.size) ind = ce.atks.size - 1
            val datas: Array<AtkDataModel?> = arrayOfNulls<AtkDataModel>(n + 1)
            for (i in 0..ind) datas[i] = ce.atks.get(i)
            ind++
            datas[ind] = AtkDataModel(ce)
            for (i in ind until n) datas[i + 1] = ce.atks.get(i)
            ce.atks = datas
            setData(ce)
            jli.setSelectedIndex(ind)
            setA(ind)
            changing = false
        })
        rem.setLnr(Consumer { e: ActionEvent? -> remAtk(jli.getSelectedIndex()) })
        copy.setLnr(Consumer { e: ActionEvent? ->
            changing = true
            val n: Int = ce.atks.size
            val ind: Int = jli.getSelectedIndex()
            ce.atks = Arrays.copyOf(ce.atks, n + 1)
            ce.atks.get(n) = ce.atks.get(ind).clone()
            setData(ce)
            jli.setSelectedIndex(n)
            setA(n)
            changing = false
        })
        link.setLnr(Consumer { e: ActionEvent? ->
            changing = true
            val n: Int = ce.atks.size
            val ind: Int = jli.getSelectedIndex()
            ce.atks = Arrays.copyOf(ce.atks, n + 1)
            ce.atks.get(n) = ce.atks.get(ind)
            setData(ce)
            jli.setSelectedIndex(n)
            setA(n)
            changing = false
        })
        jcba.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (changing) return
                ce.getPack().anim = jcba.getSelectedItem() as AnimCE
                setData(ce)
            }
        })
        jcbs.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (changing) return
                ce.death = (jcbs.getSelectedItem() as Soul).getID()
                setData(ce)
            }
        })
    }

    private operator fun get(ind: Int): AtkDataModel {
        return if (ind < ce.atks.size) ce.atks.get(ind) else if (ind == ce.atks.size) if (ce.rev == null) ce.res else ce.rev else ce.res
    }

    private fun input(jtf: JTF, text: String) {
        var text = text
        if (jtf === atkn) {
            val adm: AtkDataModel = aet.adm
            if (adm == null || adm.str == text) return
            text = ce.getAvailable(text)
            adm.str = text
            if (text == "revenge") {
                remAtk(adm)
                ce.rev = adm
            }
            if (text == "resurrection") {
                remAtk(adm)
                ce.res = adm
            }
            return
        }
        if (text.length > 0) {
            var v: Int = CommonStatic.parseIntN(text)
            if (jtf === fhp) {
                v /= getDef().toInt()
                if (v <= 0) v = 1
                ce.hp = v
            }
            if (jtf === fhb) {
                if (v <= 0) v = 1
                if (v > ce.hp) v = ce.hp
                ce.hb = v
            }
            if (jtf === fsp) {
                if (v < 0) v = 0
                if (v > 150) v = 150
                ce.speed = v
            }
            if (jtf === fra) {
                if (v <= 0) v = 1
                ce.range = v
            }
            if (jtf === fwd) {
                if (v <= 0) v = 1
                ce.width = v
            }
            if (jtf === fsh) {
                if (v < 0) v = 0
                ce.shield = v
            }
            if (jtf === ftb) {
                if (v < 0) v = 0
                ce.tba = v
            }
            if (jtf === fbs) {
                if (v < 0) v = 0
                ce.base = v
            }
            if (jtf === ftp) {
                if (v < 1) v = 1
                ce.touch = v
            }
            if (jtf === fct) {
                if (v < -1) v = -1
                ce.loop = v
            }
            getInput(jtf, v)
        }
        setData(ce)
    }

    private fun remAtk(adm: AtkDataModel) {
        for (i in ce.atks.indices) if (ce.atks.get(i) === adm) remAtk(i)
    }

    private fun remAtk(ind: Int) {
        var ind = ind
        changing = true
        val n: Int = ce.atks.size
        if (ind >= n) {
            if (ind == n) if (ce.rev != null) ce.rev = null else ce.res = null else ce.res = null
        } else if (n > 1) {
            val datas: Array<AtkDataModel?> = arrayOfNulls<AtkDataModel>(n - 1)
            for (i in 0 until ind) datas[i] = ce.atks.get(i)
            for (i in ind + 1 until n) datas[i - 1] = ce.atks.get(i)
            ce.atks = datas
        }
        setData(ce)
        ind--
        if (ind < 0) ind = 0
        jli.setSelectedIndex(ind)
        setA(ind)
        changing = false
    }

    private fun setA(ind: Int) {
        val adm: AtkDataModel = get(ind)!!
        link.setEnabled(editable && ind < ce.atks.size)
        copy.setEnabled(editable && ind < ce.atks.size)
        atkn.setEnabled(editable && ind < ce.atks.size)
        atkn.setText(adm.str)
        aet.setData(adm, getAtk())
        rem.setEnabled(editable && (ce.atks.size > 1 || ind >= ce.atks.size))
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        Editors.setEditorSupplier(EditCtrl(isEnemy, this))
        pack = pac
        ce = e
        aet = AtkEditTable(this, edit, !isEnemy)
        mpt = MainProcTable(this, edit, !isEnemy)
        jspm = JScrollPane(mpt)
        editable = edit
        if (!editable) jli.setDragEnabled(false)
    }
}
