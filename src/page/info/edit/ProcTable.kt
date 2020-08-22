package page.info.edit

import common.util.Data.Proc
import common.util.lang.Formatter
import page.JTF
import page.Page
import page.info.edit.SwingEditor.SwingEG
import page.support.ListJtfPolicy
import java.awt.Component
import java.util.function.Consumer
import javax.swing.JComponent

abstract class ProcTable protected constructor(p: Page?, protected val inds: IntArray, private val editable: Boolean, private val isUnit: Boolean) : Page(p) {
    internal class AtkProcTable(p: Page?, edit: Boolean, unit: Boolean) : ProcTable(p, INDS, edit, unit) {
        public override fun resized(x: Int, y: Int) {
            var h = 0
            for (i in group.indices) {
                val c = if (i < SEC) 0 else 400
                Page.Companion.set(group[i].jlm, x, y, c, h, 350, 50)
                h += 50
                for (j in group[i].list.indices) {
                    group[i].list.get(j).resize(x, y, c, h, 350, 50)
                    h += 50
                }
                if (i == SEC - 1) h = 0
            }
        }

        companion object {
            private const val serialVersionUID = 1L
            private const val SEC = 16
            private val INDS = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 20, 27, 29, 31, 32, 21, 22, 23, 24, 25,
                    26, 35, 36)
        }
    }

    internal class MainProcTable(p: Page?, edit: Boolean, unit: Boolean) : ProcTable(p, INDS, edit, unit) {
        override fun resized(x: Int, y: Int) {
            preferredSize = Page.Companion.size(x, y, 300, 1000).toDimension()
            var h = 0
            for (i in INDS.indices) {
                Page.Companion.set(group[i].jlm, x, y, 0, h, 300, 50)
                h += 50
                for (j in group[i].list.indices) {
                    group[i].list.get(j).resize(x, y, 0, h, 300, 50)
                    h += 50
                }
            }
        }

        companion object {
            private const val serialVersionUID = 1L
            private val INDS = intArrayOf(9, 10, 11, 12, 28, 30)
        }
    }

    protected val ljp: ListJtfPolicy = ListJtfPolicy()
    protected val group: Array<SwingEG?>
    private var proc: Proc? = null
    override fun add(comp: Component): Component {
        val ret = super.add(comp)
        if (comp is JTF) ljp.add(comp)
        return ret
    }

    fun setData(ints: Proc?) {
        proc = ints
        for (i in inds.indices) group[i].setData(proc.getArr(inds[i]))
    }

    private fun ini() {
        val ctx = Formatter.Context(!isUnit, false)
        for (i in group.indices) {
            group[i] = SwingEG(inds[i], editable, Runnable { front.callBack(null) }, ctx)
            add(group[i].jlm)
            for (j in group[i].list.indices) {
                group[i].list.get(j).add(Consumer<JComponent> { comp: JComponent? -> this.add(comp) })
            }
        }
        focusTraversalPolicy = ljp
        isFocusCycleRoot = true
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        group = arrayOfNulls<SwingEG>(inds.size)
        ini()
    }
}
