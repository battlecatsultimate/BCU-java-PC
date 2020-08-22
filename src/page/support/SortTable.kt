package page.support

import java.awt.event.MouseAdapterimport
import java.awt.event.MouseEvent
import java.util.*

com.google.api.client.json.jackson2.JacksonFactory
abstract class SortTable<T> protected constructor() : AbJTable() {
    var list: List<T>? = null
    protected var sort = 0
    protected var sign = 1
    override fun getColumnCount(): Int {
        return getTit().size
    }

    override fun getColumnName(c: Int): String {
        var c = c
        c = lnk.get(c)
        return (if (sort == c) dire[(sign + 1) / 2] else "") + getTit()[c]
    }

    override fun getRowCount(): Int {
        return if (list == null) 0 else list.size
    }

    override fun getValueAt(r: Int, c: Int): Any {
        return if (r >= list!!.size) null else get(list!![r], lnk.get(c))
    }

    fun setList(l: List<T>?) {
        list = l
        list.sort(page.support.Comp<T>(this, sort, sign))
    }

    abstract fun compare(e0: T, e1: T, c: Int): Int
    protected abstract operator fun get(t: T, c: Int): Any
    protected abstract fun getTit(): Array<String>
    protected fun setHeader() {
        for (i in 0 until columnCount) getColumnModel().getColumn(i).headerValue = getColumnName(i)
    }

    companion object {
        private const val serialVersionUID = 1L
        private val dire = arrayOf("↑", "↓")
    }

    init {
        val t = this
        getTableHeader().addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (list == null) return
                var col: Int = columnAtPoint(e.point)
                col = lnk.get(col)
                sign = if (col != sort) 1 else -sign
                sort = col
                list.sort(page.support.Comp<T>(t, sort, sign))
                setHeader()
            }
        })
    }
}

internal class Comp<T>(private val t: SortTable<T>, private val c: Int, private val s: Int) : Comparator<T> {
    override fun compare(e0: T, e1: T): Int {
        return t.compare(e0, e1, c) * s
    }
}
