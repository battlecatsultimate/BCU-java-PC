package page.anim

import common.util.anim.AnimCE
import common.util.anim.MaModel
import java.util.function.IntPredicate
import javax.swing.JTree
import javax.swing.event.TreeExpansionEvent
import javax.swing.event.TreeExpansionListener
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreePath

internal class MMTree(protected val tc: TreeCont?, am: AnimCE?, tree: JTree) : TreeExpansionListener {
    val anim: AnimCE?
    private val mm: MaModel
    private val jtr: JTree
    private var top: DefaultMutableTreeNode? = null
    private var data: Array<DefaultMutableTreeNode?>
    private var adj = false
    override fun treeCollapsed(arg0: TreeExpansionEvent) {
        val n: Any = arg0.getPath().getLastPathComponent()
        val i = indexOf(n)
        if (i < 0) return
        mm.status.put(mm.parts.get(i), 1)
        anim.updateStatus()
        tc?.collapse()
    }

    override fun treeExpanded(arg0: TreeExpansionEvent) {
        val n: Any = arg0.getPath().getLastPathComponent()
        val i = indexOf(n)
        if (i < 0) return
        mm.status.put(mm.parts.get(i), 1)
        anim.updateStatus()
        tc?.expand()
    }

    protected fun indexOf(o: Any): Int {
        for (i in data.indices) if (data[i] === o) return i
        return -1
    }

    fun nav(p: Int, f: IntPredicate) {
        for (i in 0 until mm.n) if (mm.parts.get(i).get(0) == p && f.test(i)) nav(i, f)
    }

    fun renew() {
        data = arrayOfNulls<DefaultMutableTreeNode>(mm.n)
        top = DefaultMutableTreeNode("MaModel")
        var c = 0
        while (c < mm.n) {
            for (i in 0 until mm.n) if (data[i] == null) {
                val line: IntArray = mm.parts.get(i)
                val pre: DefaultMutableTreeNode = (if (line[0] == -1) top else data[line[0]]) ?: continue
                data[i] = DefaultMutableTreeNode(i.toString() + " - " + mm.strs0.get(i))
                pre.add(data[i])
                c++
            }
        }
        jtr.setModel(DefaultTreeModel(top))
        nav(-1, IntPredicate { i: Int ->
            val s: Int = mm.status.get(mm.parts.get(i))
            val tp = TreePath(data[i].getPath())
            if (s == null || s == 0) {
                jtr.expandPath(tp)
                return@nav true
            }
            jtr.collapsePath(tp)
            false
        })
        jtr.addTreeExpansionListener(this)
    }

    fun select(i: Int) {
        if (adj || i >= data.size || i < 0) return
        val tp = TreePath(data[i].getPath())
        jtr.setSelectionPath(tp)
        jtr.scrollPathToVisible(tp)
    }

    fun setAdjusting(b: Boolean) {
        adj = b
    }

    init {
        anim = am
        mm = am.mamodel
        jtr = tree
    }
}

internal interface TreeCont {
    fun collapse()
    fun expand()
}
