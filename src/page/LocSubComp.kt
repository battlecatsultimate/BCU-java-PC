package page

import common.util.lang.LocaleCenter
import page.MainLocale
import page.Page
import utilpc.PP
import java.awt.BorderLayout
import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

class LocSubComp(comp: LocComp) {
    internal class LocBinder(private val par: LocSubComp, private val loc: Int, private val info: String) : LocaleCenter.Binder {
        override fun getNameID(): String? {
            return if (loc < 0) null else MainLocale.Companion.RENN.get(loc).toString() + "_" + info
        }

        override fun getNameValue(): String? {
            return MainLocale.Companion.getLoc(loc, info)
        }

        override fun getTooltipID(): String? {
            return if (par.page == null) null else par.page!!.javaClass.simpleName + "_" + info
        }

        override fun getToolTipValue(): String? {
            return if (par.page == null) null else MainLocale.Companion.getTTT(par.page!!.javaClass.simpleName, info)
        }

        override fun refresh(): LocaleCenter.Binder {
            return this
        }

        override fun setNameValue(str: String) {
            MainLocale.Companion.setLoc(loc, info, str)
        }

        override fun setToolTipValue(str: String) {
            if (par.page == null) return
            MainLocale.Companion.setTTT(par.page!!.javaClass.simpleName, info, str)
        }
    }

    protected val lc: LocComp
    var binder: LocaleCenter.Binder? = null
    var page: Page? = null
    fun update() {
        if (binder == null) return
        lc.setText(binder!!.nameValue)
        if (binder!!.toolTipValue != null) lc.setToolTipText(binder!!.toolTipValue)
    }

    fun added(p: Page?) {
        page = p
        update()
    }

    fun init(b: LocaleCenter.Binder?) {
        binder = b
        update()
    }

    fun init(i: Int, str: String) {
        init(LocBinder(this, i, str))
    }

    fun reLoc() {
        binder = binder!!.refresh()
        update()
    }

    init {
        lc = comp
        lc.addMouseListener(LSCPop(this))
    }
}

internal class LSCPop(private val lsc: LocSubComp) : MouseAdapter() {
    override fun mouseClicked(arg0: MouseEvent) {
        if (arg0.button == MouseEvent.BUTTON3 && lsc.page != null) {
            val panel = JPanel()
            val size: PP = PP(lsc.page!!.getRootPage().size).times(0.25)
            panel.setPreferredSize(size.toDimension())
            panel.setLayout(BorderLayout())
            val top = JPanel(GridLayout(2, 2))
            val id1 = JL(lsc.binder!!.tooltipID)
            val id0 = JL(lsc.binder!!.nameID)
            top.add(JLabel("tooltip ID to edit: "))
            top.add(id1)
            top.add(JLabel("name ID to edit: "))
            top.add(id0)
            panel.add(top, BorderLayout.PAGE_START)
            val jtf = JTF(lsc.binder!!.nameValue)
            panel.add(jtf, BorderLayout.PAGE_END)
            val jta = JTextPane()
            jta.setText(lsc.binder!!.toolTipValue)
            panel.add(JScrollPane(jta), BorderLayout.CENTER)
            if (lsc.binder!!.nameID == null) {
                id0.setEnabled(false)
                jtf.setEnabled(false)
            }
            if (lsc.binder!!.tooltipID == null) {
                id1.setEnabled(false)
                jta.setEnabled(false)
            }
            val type: Int = JOptionPane.OK_CANCEL_OPTION
            val ok: Int = JOptionPane.OK_OPTION
            val res: Int = JOptionPane.showConfirmDialog(null, panel, "", type)
            val str: String = jtf.getText()
            val ttt: String = jta.getText()
            if (res == ok && str != null && str != lsc.binder!!.nameValue) {
                lsc.binder!!.nameValue = str
                Page.Companion.renewLoc(lsc.page)
            }
            if (res == ok && ttt != null && ttt != lsc.binder!!.toolTipValue) {
                lsc.binder!!.toolTipValue = ttt
                Page.Companion.renewLoc(lsc.page)
            }
        }
    }
}
