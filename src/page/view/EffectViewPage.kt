package page.view

import common.CommonStatic
import common.pack.UserProfile
import common.util.anim.AnimI
import page.Page
import java.util.*
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class EffectViewPage(p: Page?) : AbViewPage(p) {
    private val jlu: JList<AnimI<*, *>> = JList<AnimI<*, *>>()
    private val jspu: JScrollPane = JScrollPane(jlu)
    override fun resized(x: Int, y: Int) {
        super.resized(x, y)
        Page.Companion.set(jspu, x, y, 50, 100, 300, 1100)
    }

    override fun updateChoice() {
        val u: AnimI<*, *> = jlu.selectedValue ?: return
        setAnim(u)
    }

    private fun addListeners() {
        jlu.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (arg0.valueIsAdjusting) return
                updateChoice()
            }
        })
    }

    private fun ini() {
        preini()
        add(jspu)
        addListeners()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        val va: Vector<AnimI<*, *>> = Vector<AnimI<*, *>>()
        for (a in CommonStatic.getBCAssets().effas.values()) va.add(a)
        for (a in CommonStatic.getBCAssets().atks) va.add(a)
        va.addAll(UserProfile.Companion.getBCData().souls.getList())
        jlu.setListData(va)
        ini()
        resized()
    }
}
