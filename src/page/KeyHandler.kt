package page

import common.CommonStatic.FakeKey
import page.Pageimport
import java.awt.event.KeyEvent
import java.util.*

abstract class KeyHandler protected constructor(p: Page?) : Page(p), FakeKey {
    protected var press: MutableMap<Int, Int> = TreeMap<Int, Int>()
    protected var slots = arrayOf(intArrayOf(KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_T), intArrayOf(KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_G), intArrayOf(KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_B))
    protected var act = intArrayOf(KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3)
    protected var lock = intArrayOf(KeyEvent.VK_SHIFT)
    @Synchronized
    override fun pressed(type: Int, slot: Int): Boolean {
        val key = (if (type == -2) lock else if (type == -1) act else slots[type])[slot]
        return press.containsKey(key) && press[key] != 2
    }

    @Synchronized
    override fun remove(type: Int, slot: Int) {
        val key = (if (type == -2) lock else if (type == -1) act else slots[type])[slot]
        press[key] = 2
    }

    @Synchronized
    override fun keyPressed(e: KeyEvent) {
        press[e.keyCode] = 0
    }

    @Synchronized
    override fun keyReleased(e: KeyEvent) {
        val `val` = e.keyCode
        if (press.containsKey(`val`) && press[`val`] != 2) press[e.keyCode] = 1
    }

    @Synchronized
    protected fun updateKey() {
        for (`val` in press.keys) if (press[`val`] == 1) press[`val`] = 2
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
