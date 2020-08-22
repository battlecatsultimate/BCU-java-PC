package jogl

import page.RetFunc
import page.awt.RecdThread
import java.awt.image.BufferedImage
import java.util.*

abstract class GLRecorder protected constructor(scr: GLCstd) {
    protected val screen: GLCstd
    abstract fun end()
    abstract fun quit()
    abstract fun remain(): Int
    abstract fun start()
    abstract fun update()

    companion object {
        fun getIns(scr: GLCstd, path: String?, type: Int, ob: RetFunc?): GLRecorder {
            return GLRecdBImg(scr, path, type, ob)
        }
    }

    init {
        screen = scr
    }
}

internal class GLRecdBImg : GLRecorder {
    private val qb: Queue<BufferedImage?>
    private val th: RecdThread

    constructor(scr: GLCstd, lb: Queue<BufferedImage?>, rt: RecdThread) : super(scr) {
        qb = lb
        th = rt
    }

    constructor(scr: GLCstd, path: String?, type: Int, ob: RetFunc?) : super(scr) {
        qb = ArrayDeque<BufferedImage?>()
        th = RecdThread.Companion.getIns(ob, qb, path, type)
    }

    override fun end() {
        synchronized(th) { th.end = true }
    }

    override fun quit() {
        synchronized(th) { th.quit = true }
    }

    override fun remain(): Int {
        var size: Int
        synchronized(qb) { size = qb.size }
        return size
    }

    override fun start() {
        th.start()
    }

    override fun update() {
        synchronized(qb) { qb.add(screen.getScreen()) }
    }
}
