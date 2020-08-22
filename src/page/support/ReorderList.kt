package page.support

import main.Printer
import java.util.*
import javax.swing.DefaultListModel
import javax.swing.JList

class ReorderList<T> : JList<T>, Reorderable {
    private val model: DefaultListModel<T>
    val copymap: MutableMap<Int, T> = TreeMap<Int, T>()
    var list: ReorderListener<T>? = null
    var copable = false

    constructor() {
        model = DefaultListModel<T>()
        setModel(model)
        try {
            transferHandler = InListTH<T>(this)
            setDragEnabled(true)
        } catch (e: Exception) {
            Printer.e("ReorderList", 24, "cannot drag row")
        }
    }

    constructor(vec: Array<T>) : this() {
        setListData(vec)
    }

    constructor(vec: Vector<T>) : this() {
        setListData(vec)
    }

    constructor(vec: Vector<T>, cls: Class<out T>?, str: String?) {
        model = DefaultListModel<T>()
        setModel(model)
        try {
            transferHandler = InListTH<T>(this, cls, str)
            dragEnabled = true
            copable = true
        } catch (e: Exception) {
            Printer.e("ReorderList", 24, "cannot drag row")
        }
        setListData(vec)
    }

    fun add(t: T?): Boolean {
        if (t == null) return false
        if (list!!.add(t)) model.addElement(t) else return false
        return true
    }

    override fun reorder(ori: Int, fin: Int) {
        var fin = fin
        if (list != null) list.reordering()
        val lm: DefaultListModel<T> = getModel() as DefaultListModel<T>
        val `val`: T = lm.get(ori)
        lm.remove(ori)
        if (fin > ori) fin--
        lm.add(fin, `val`)
        if (list != null) list.reordered(ori, fin)
    }

    override fun setListData(data: Array<T>) {
        model.clear()
        for (t in data) model.addElement(t)
    }

    override fun setListData(data: Vector<out T>) {
        model.clear()
        for (t in data) model.addElement(t)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
