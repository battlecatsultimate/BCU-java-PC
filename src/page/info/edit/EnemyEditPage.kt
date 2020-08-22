package page.info.edit

import common.pack.UserProfile
import common.util.unit.Enemy
import page.JBTN
import page.JL
import page.JTF
import page.Page
import page.info.StageFilterPage
import page.info.filter.EnemyEditBox
import utilpc.Interpret
import java.awt.event.ActionEvent
import java.util.function.Consumer

class EnemyEditPage(p: Page?, e: Enemy) : EntityEditPage(p, e.id.pack, e.de as CustomEntity, UserProfile.Companion.getUserPack(e.id.pack).editable, true) {
    private val ldr: JL = JL(1, "drop")
    private val fdr: JTF = JTF()
    private val fsr: JTF = JTF()
    private val vene: JBTN = JBTN(0, "vene")
    private val appr: JBTN = JBTN(0, "stage")
    private val impt: JBTN = JBTN(0, "import")
    private val vuni: JBTN = JBTN(0, "unit")
    private val eeb: EnemyEditBox
    private val ene: Enemy
    private val ce: CustomEnemy
    protected override fun getInput(jtf: JTF, v: Int) {
        var v = v
        if (jtf === fdr) {
            val act = (v / bas.t().getDropMulti()) as Int
            ce.drop = act
        }
        if (jtf === fsr) {
            if (v < 0) v = 0
            if (v > 4) v = 1
            ce.star = v
        }
    }

    protected override fun ini() {
        set(ldr)
        set(fdr)
        set(fsr)
        super.ini()
        add(eeb)
        add(vene)
        add(appr)
        add(impt)
        add(vuni)
        appr.setLnr(Consumer { x: ActionEvent? -> changePanel(StageFilterPage(getThis(), ene.findApp())) })
        subListener(impt, vuni, vene, ene)
    }

    protected override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(ldr, x, y, 50, 350, 100, 50)
        Page.Companion.set(fdr, x, y, 150, 350, 200, 50)
        Page.Companion.set(eeb, x, y, 350, 50, 200, 1100)
        Page.Companion.set(fsr, x, y, 350, 1200, 200, 50)
        Page.Companion.set(vene, x, y, 900, 1200, 200, 50)
        Page.Companion.set(appr, x, y, 1100, 1200, 200, 50)
        Page.Companion.set(impt, x, y, 1350, 1050, 200, 50)
        Page.Companion.set(vuni, x, y, 1350, 1100, 200, 50)
        eeb.resized()
    }

    protected override fun setData(data: CustomEntity) {
        super.setData(data)
        fsr.setText("star: " + ce.star)
        fdr.setText("" + (ce.getDrop() * bas.t().getDropMulti()) as Int)
        var imu = 0
        for (i in Interpret.EABIIND.indices) if (Interpret.EABIIND.get(i) > 100) {
            val id: Int = Interpret.EABIIND.get(i) - 100
            if (ce.getProc().getArr(id).exists()) imu = imu or (1 shl id - Interpret.IMUSFT)
        }
        eeb.setData(intArrayOf(ce.type, ce.abi, imu))
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ene = e
        ce = ene.de as CustomEnemy
        eeb = EnemyEditBox(this, editable)
        ini()
        setData(e.de as CustomEnemy)
        resized()
    }
}
