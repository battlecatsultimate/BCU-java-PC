package page.anim

import common.system.VImg
import common.util.anim.AnimCE
import common.util.anim.AnimU.UType
import main.Opts
import page.JBTN
import page.JTG
import page.Page
import page.anim.IconBox.IBConf
import page.awt.BBBuilder
import page.support.AnimLCR
import page.view.AbViewPage
import utilpc.UtilPC
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.util.*
import java.util.function.Consumer
import javax.swing.JComboBox
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class DIYViewPage : AbViewPage, AbEditPage {
    private val jlu: JList<AnimCE> = JList<AnimCE>()
    private val jspu: JScrollPane = JScrollPane(jlu)
    private val aep: EditHead
    private val ics: JTG = JTG(0, "icon")
    private val icc: JBTN = JBTN(0, "confirm")
    private val jcb: JComboBox<String> = JComboBox<String>(icos)
    private val uni = JLabel()
    private val ib: IconBox

    constructor(p: Page?) : super(p, BBBuilder.Companion.def.getIconBox()) {
        ib = vb as IconBox
        aep = EditHead(this, 0)
        ini()
        resized()
    }

    constructor(p: Page?, ac: AnimCE) : super(p, BBBuilder.Companion.def.getIconBox()) {
        ib = vb as IconBox
        aep = EditHead(this, 0)
        if (!ac.inPool()) aep.focus = ac
        ini()
        resized()
    }

    constructor(p: Page?, bar: EditHead) : super(p, BBBuilder.Companion.def.getIconBox()) {
        ib = vb as IconBox
        aep = bar
        ini()
        resized()
    }

    override fun setSelection(ac: AnimCE?) {
        jlu.setSelectedValue(ac, true)
    }

    override fun enabler(b: Boolean) {
        super.enabler(b)
        ics.isEnabled = ics.isSelected || b && pause
        icc.isEnabled = ics.isSelected
        jlu.isEnabled = b
    }

    protected override fun keyPressed(ke: KeyEvent) {
        if (ke.source === ib) ib.keyPressed(ke)
    }

    protected override fun keyReleased(ke: KeyEvent) {
        if (ke.source === ib) ib.keyReleased(ke)
    }

    protected override fun renew() {
        if (aep.focus == null) jlu.setListData(Vector<AnimCE>(AnimCE.Companion.map().values)) else jlu.setListData(arrayOf<AnimCE?>(aep.focus))
        jlu.selectedIndex = 0
    }

    override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(aep, x, y, 550, 0, 1750, 50)
        Page.Companion.set(jspu, x, y, 50, 100, 300, 1100)
        Page.Companion.set(ics, x, y, 1000, 1050, 200, 50)
        Page.Companion.set(uni, x, y, 750, 500, 200, 200)
        Page.Companion.set(jcb, x, y, 750, 750, 200, 50)
        Page.Companion.set(icc, x, y, 1000, 1150, 200, 50)
    }

    override fun updateChoice() {
        val e: AnimCE = jlu.selectedValue
        aep.setAnim(e)
        uni.icon = if (e == null) null else UtilPC.getIcon(e.getUni())
        if (e == null) return
        setAnim<UType>(e)
    }

    private fun addListeners() {
        jlu.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.valueIsAdjusting) return
                updateChoice()
            }
        })
        ics.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                enabler(!ics.isSelected)
                ib.setBlank(ics.isSelected)
            }
        })
        jcb.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val t: Int = jcb.selectedIndex
                IBConf.mode = t / 4
                IBConf.type = t % 4
                IBConf.glow = if (IBConf.type + IBConf.mode > 1) 1 else 0
                ib.changeType()
            }
        })
        icc.setLnr(Consumer { x: ActionEvent? ->
            val clip = VImg(ib.getClip())
            if (IBConf.mode == 0
                    && Opts.conf("are you sure to replace display icon? This action cannot be undone")) {
                val ac: AnimCE = aep.anim
                ac.setEdi(clip)
                ac.saveIcon()
                jlu.repaint()
            }
            if (IBConf.mode == 1
                    && Opts.conf("are you sure to replace battle icon? This action cannot be undone")) {
                val ac: AnimCE = aep.anim
                ac.setUni(VImg(ib.getClip()))
                ac.saveUni()
                uni.icon = UtilPC.getIcon(ac.getUni())
            }
        })
    }

    private fun ini() {
        preini()
        add(aep)
        add(jspu)
        add(ics)
        add(icc)
        add(jcb)
        add(uni)
        jcb.selectedIndex = IBConf.type
        ics.isEnabled = false
        icc.isEnabled = false
        jlu.setCellRenderer(AnimLCR())
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
        private val icos = arrayOf("default", "starred", "EF", "TF", "uni_f", "uni_c", "uni_s")
    }
}
