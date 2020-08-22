package page.awt

import common.battle.BattleField
import page.awt.RecdThread
import page.battle.BBRecd
import page.battle.BattleBox.OuterBox
import java.awt.image.BufferedImage
import java.util.*

internal class BBRecdAWT(bip: OuterBox?, bas: BattleField?, out: String, img: Boolean) : BattleBoxDef(bip, bas, 0), BBRecd {
    private val th: RecdThread?
    private val qb: Queue<BufferedImage?> = ArrayDeque<BufferedImage?>()
    private var time = -1
    override fun end() {
        synchronized(th!!) { th!!.end = true }
    }

    override fun info(): String {
        var size: Int
        synchronized(qb) { size = qb.size }
        return "" + size
    }

    override fun quit() {
        synchronized(th!!) { th!!.quit = true }
    }

    protected override val image: BufferedImage?
        protected get() {
            val bimg: BufferedImage = super.getImage()
            if (bbp.bf.sb.time > time) synchronized(qb) {
                qb.add(bimg)
                time = bbp.bf.sb.time
            }
            return bimg
        }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        th = RecdThread.Companion.getIns(bip, qb, out, if (img) RecdThread.Companion.PNG else RecdThread.Companion.MP4)
        th!!.start()
    }
}
