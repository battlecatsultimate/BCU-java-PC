package main

import main.Timer
import page.MainFrame
import javax.swing.SwingUtilities

@Strictfp
class Timer : Thread() {
    private var thr: Inv? = null
    override fun run() {
        while (true) {
            val m = System.currentTimeMillis()
            Timer.Companion.state = 0
            SwingUtilities.invokeLater(Inv().also { thr = it })
            try {
                var end = false
                while (!end) {
                    synchronized(thr!!) { end = Timer.Companion.state == 1 }
                    if (!end) sleep(1)
                }
                thr!!.join()
                Timer.Companion.delay = (System.currentTimeMillis() - m).toInt()
                Timer.Companion.inter = (Timer.Companion.inter * 9 + 100 * Timer.Companion.delay / Timer.Companion.p) / 10
                val sle = if (Timer.Companion.delay >= Timer.Companion.p) 1 else Timer.Companion.p - Timer.Companion.delay
                sleep(sle.toLong())
            } catch (e: InterruptedException) {
                return
            }
        }
    }

    companion object {
        var p = 33
        var inter = 0
        var state = 0
        private const val delay = 0
    }
}

@Strictfp
internal class Inv : Thread() {
    override fun run() {
        MainFrame.Companion.timer(-1)
        synchronized(this) { Timer.Companion.state = 1 }
    }
}
