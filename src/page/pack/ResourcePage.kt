package page.pack

import common.system.files.VFile
import common.system.files.VFileRoot
import io.BCUWriter
import page.JBTN
import page.Page
import page.support.Exporter
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import javax.swing.JLabel
import javax.swing.JScrollPane
import javax.swing.JTree
import javax.swing.event.TreeSelectionEvent
import javax.swing.event.TreeSelectionListener
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreePath

class ResourcePage(p: Page?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val rept: JBTN = JBTN(0, "extract")
    private val jln = JLabel()
    private val jls: JTree = JTree()
    private val jsps: JScrollPane = JScrollPane(jls)
    private var sel: VFile<*>? = null
    private val changing = false
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jln, x, y, 50, 50, 750, 50)
        Page.Companion.set(jsps, x, y, 50, 100, 400, 800)
        Page.Companion.set(rept, x, y, 500, 300, 200, 50)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        rept.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val f: File = Exporter(Exporter.Companion.EXP_RES).file
                if (f != null) filemove(f.path + "/", sel)
            }
        })
        jls.addTreeSelectionListener(object : TreeSelectionListener {
            override fun valueChanged(arg0: TreeSelectionEvent?) {
                if (changing) return
                var obj: Any? = null
                val tp: TreePath = jls.getSelectionPath()
                if (tp != null) {
                    obj = tp.lastPathComponent
                    if (obj != null) obj = (obj as DefaultMutableTreeNode).getUserObject()
                    sel = if (obj is VFile<*>) obj as VFile<*>? else null
                } else sel = null
                setSele()
            }
        })
    }

    private fun addTree(par: DefaultMutableTreeNode, vf: VFile<*>) {
        for (c in vf.list()) {
            val cur = DefaultMutableTreeNode(c)
            par.add(cur)
            if (c.list() != null) addTree(cur, c)
        }
    }

    private fun filemove(dst: String, src: VFile<*>?) {
        if (src.list() != null) for (c in src.list()) filemove(dst + src.getName() + "/", c) else BCUWriter.writeBytes(src.getData().getBytes(), dst + src.getName())
    }

    private fun ini() {
        add(back)
        add(jsps)
        add(rept)
        add(jln)
        setSele()
        setTree(VFile.Companion.getBCFileTree())
        addListeners()
    }

    private fun setSele() {
        rept.setEnabled(sel != null)
    }

    private fun setTree(vfr: VFileRoot<*>?) {
        if (vfr == null) {
            jls.setModel(DefaultTreeModel(null))
            return
        }
        val top = DefaultMutableTreeNode("resources/")
        addTree(top, vfr)
        jls.setModel(DefaultTreeModel(top))
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
        resized()
    }
}
