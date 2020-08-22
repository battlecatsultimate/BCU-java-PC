package page.info.edit

import common.pack.PackData.UserPack
import common.util.unit.Form
import page.JBTN
import page.JL
import page.JTF
import page.Page
import page.info.filter.UnitEditBox
import utilpc.Interpret

class FormEditPage(p: Page?, pac: UserPack, private val form: Form) : EntityEditPage(p, pac.desc.id, form.du as CustomEntity, pac.editable, false) {
    private val llv: JL = JL(1, "Lv")
    private val ldr: JL = JL(1, "price")
    private val lrs: JL = JL(1, "CD")
    private val fdr: JTF = JTF()
    private val flv: JTF = JTF()
    private val frs: JTF = JTF()
    private val vuni: JBTN = JBTN(0, "vuni")
    private val impt: JBTN = JBTN(0, "import")
    private val vene: JBTN = JBTN(0, "enemy")
    private val ldps: JL = JL("DPS")
    private val vdps: JL = JL()
    private val ueb: UnitEditBox
    private val cu: CustomUnit
    private var lv: Int
    protected override fun getAtk(): Double {
        val mul = form.unit.lv.getMult(lv)
        val atk: Double = bas.t().getAtkMulti()
        return mul * atk
    }

    protected override fun getDef(): Double {
        val mul = form.unit.lv.getMult(lv)
        val def: Double = bas.t().getDefMulti()
        return mul * def
    }

    protected override fun getInput(jtf: JTF, v: Int) {
        var v = v
        if (jtf === fdr) cu.price = (v / 1.5).toInt()
        if (jtf === flv) {
            if (v <= 0) v = 1
            lv = v
        }
        if (jtf === frs) {
            if (v <= 60) v = 60
            cu.resp = bas.t().getRevRes(v)
        }
    }

    protected override fun ini() {
        set(ldr)
        set(llv)
        set(lrs)
        set(flv)
        set(fdr)
        set(frs)
        super.ini()
        add(ueb)
        add(vuni)
        add(impt)
        add(vene)
        set(ldps)
        set(vdps)
        subListener(vene, impt, vuni, form.unit)
    }

    protected override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(llv, x, y, 50, 50, 100, 50)
        Page.Companion.set(flv, x, y, 150, 50, 200, 50)
        Page.Companion.set(ldr, x, y, 50, 350, 100, 50)
        Page.Companion.set(fdr, x, y, 150, 350, 200, 50)
        Page.Companion.set(lrs, x, y, 50, 400, 100, 50)
        Page.Companion.set(frs, x, y, 150, 400, 200, 50)
        Page.Companion.set(ueb, x, y, 350, 50, 200, 1200)
        Page.Companion.set(ldps, x, y, 900, 1150, 200, 50)
        Page.Companion.set(vdps, x, y, 1100, 1150, 200, 50)
        Page.Companion.set(vuni, x, y, 1350, 1050, 200, 50)
        Page.Companion.set(impt, x, y, 1350, 1100, 200, 50)
        Page.Companion.set(vene, x, y, 1350, 1150, 200, 50)
        ueb.resized()
    }

    protected override fun setData(data: CustomEntity) {
        super.setData(data)
        flv.setText("" + lv)
        frs.setText("" + bas.t().getFinRes(cu.getRespawn()))
        fdr.setText("" + (cu.getPrice() * 1.5) as Int)
        var imu = 0
        for (i in Interpret.ABIIND.indices) {
            val id: Int = Interpret.ABIIND.get(i) - 100
            if (cu.getProc().getArr(id).exists()) imu = imu or (1 shl id - Interpret.IMUSFT)
        }
        ueb.setData(intArrayOf(cu.type, cu.abi, imu))
        vdps.setText("" + (cu.allAtk() * 30 * getAtk() / cu.getItv()) as Int)
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        cu = form.du as CustomUnit
        lv = form.unit.prefLv
        ueb = UnitEditBox(this, pac.editable)
        ini()
        setData(form.du as CustomUnit)
        resized()
    }
}
