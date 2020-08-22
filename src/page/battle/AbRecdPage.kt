package page.battle

import common.CommonStatic
import common.battle.BasisSet
import common.util.stage.MapColc
import common.util.stage.Recd
import common.util.stage.Stage
import main.Opts
import page.*
import page.basis.BasisPage
import page.info.StageViewPage
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.FocusEvent
import java.util.function.Consumer
import javax.swing.BorderFactory
import javax.swing.JLabel

abstract class AbRecdPage(p: Page?, protected val editable: Boolean) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val rply: JBTN = JBTN(0, "rply")
    private val recd: JBTN = JBTN(-1, "mp4")
    private val vsta: JBTN = JBTN(0, "vsta")
    private val jlu: JBTN = JBTN(0, "line")
    private val seed: JTF = JTF()
    private val larg: JTG = JTG(0, "larges")
    private val imgs: JBTN = JBTN(-1, "PNG")
    private val len = JLabel()
    private val ista: JL = JL()
    private val imap: JL = JL()
    private var svp: StageViewPage? = null
    private var bp: BasisPage? = null
    abstract fun getSelection(): Recd
    protected fun preini() {
        add(back)
        add(rply)
        add(len)
        add(recd)
        add(larg)
        add(imgs)
        add(vsta)
        add(seed)
        add(jlu)
        add(imap)
        add(ista)
        len.border = BorderFactory.createEtchedBorder()
        addListeners()
    }

    override fun renew() {
        setList()
        if (editable && svp != null) {
            val r: Recd = getSelection()
            val ns: Stage = svp.getStage()
            if (r != null && ns != null && ns !== r.st && Opts.conf("are you sure to change stage?")) {
                r.st = svp.getStage()
                r.avail = true
                r.marked = true
            }
        }
        if (editable && bp != null) {
            val r: Recd = getSelection()
            if (r != null && Opts.conf("are you sure to change lineup?")) {
                r.lu = BasisSet.Companion.current().sele.copy()
                r.marked = true
            }
        }
        bp = null
        svp = null
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(rply, x, y, 600, 100, 300, 50)
        Page.Companion.set(imap, x, y, 950, 100, 300, 50)
        Page.Companion.set(ista, x, y, 1300, 100, 300, 50)
        Page.Companion.set(recd, x, y, 600, 200, 300, 50)
        Page.Companion.set(larg, x, y, 950, 200, 300, 50)
        Page.Companion.set(imgs, x, y, 1300, 200, 300, 50)
        Page.Companion.set(len, x, y, 600, 300, 300, 50)
        Page.Companion.set(vsta, x, y, 600, 600, 300, 50)
        Page.Companion.set(jlu, x, y, 950, 600, 300, 50)
        Page.Companion.set(seed, x, y, 950, 300, 500, 50)
    }

    protected abstract fun setList()
    protected open fun setRecd(r: Recd?) {
        rply.isEnabled = r != null && r.avail
        recd.isEnabled = r != null && r.avail
        imgs.isEnabled = r != null && r.avail
        seed.isEditable = editable && r != null
        vsta.isEnabled = r != null
        ista.setText(if (r == null || r.st == null) "(unavailable)" else r.st.toString())
        imap.setText(if (r == null || r.st == null) "(unavailable)" else r.st.map.toString())
        jlu.isEnabled = r != null
        if (r == null) {
            len.text = ""
            seed.text = ""
        } else {
            seed.text = "seed: " + r.seed
            len.text = "length: " + r.getLen() + " frame"
        }
    }

    private fun addListeners() {
        back.setLnr(Consumer { x: ActionEvent? -> changePanel(front) })
        vsta.setLnr(Consumer { x: ActionEvent? -> changePanel(StageViewPage(getThis(), MapColc.Companion.values(), getSelection().st).also { svp = it }) })
        jlu.setLnr(Consumer { x: ActionEvent? -> changePanel(BasisPage(getThis()).also { bp = it }) })
        rply.setLnr(Consumer { x: ActionEvent? -> changePanel(BattleInfoPage(getThis(), getSelection(), 0)) })
        recd.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val r: Recd = getSelection()
                var conf = 1
                if (larg.isSelected) conf = conf or 2
                changePanel(BattleInfoPage(getThis(), r, conf))
            }
        })
        imgs.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val r: Recd = getSelection()
                var conf = 5
                if (larg.isSelected) conf = conf or 2
                changePanel(BattleInfoPage(getThis(), r, conf))
            }
        })
        seed.setLnr(Consumer<FocusEvent> { x: FocusEvent? ->
            if (isAdj) return@setLnr
            val r: Recd = getSelection()
            r.seed = CommonStatic.parseLongN(seed.text)
            r.marked = true
            setRecd(r)
        })
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
