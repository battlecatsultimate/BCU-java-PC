package page.support

import page.JTF
import java.awt.Component
import java.awt.Containerimport
import java.awt.FocusTraversalPolicy
import java.util.*

com.google.api.client.json.jackson2.JacksonFactory
class ListJtfPolicy : FocusTraversalPolicy() {
    private val list: MutableList<JTF> = ArrayList<JTF>()
    private var end = false
    fun add(jtf: JTF) {
        if (!end) list.add(jtf)
    }

    fun end() {
        end = true
    }

    override fun getComponentAfter(cont: Container, comp: Component): Component {
        var ind = list.indexOf(comp)
        if (ind == -1) return list[0]
        if (ind + 1 >= list.size) ind = 0 else ind++
        val jtf: JTF = list[ind]
        return if (jtf.isEnabled) jtf else getComponentAfter(cont, jtf)
    }

    override fun getComponentBefore(cont: Container, comp: Component): Component {
        var ind = list.indexOf(comp)
        if (ind == -1) return list[0]
        if (ind == 0) ind = list.size - 1 else ind--
        val jtf: JTF = list[ind]
        return if (jtf.isEnabled) jtf else getComponentAfter(cont, jtf)
    }

    override fun getDefaultComponent(cont: Container): Component {
        return null
    }

    override fun getFirstComponent(cont: Container): Component {
        return list[0]
    }

    override fun getLastComponent(aContainer: Container): Component {
        return list[list.size - 1]
    }
}
