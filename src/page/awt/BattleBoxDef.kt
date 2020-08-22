package page.awt

import common.battle.BattleField
import common.battle.SBCtrl
import common.system.fake.FakeGraphics
import main.Timer
import page.battle.BBCtrl
import page.battle.BattleBox
import page.battle.BattleBox.BBPainter
import page.battle.BattleBox.OuterBox
import utilpc.awt.FG2D
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage

internal open class BattleBoxDef(bip: OuterBox?, bas: BattleField?, p: Int) : Canvas(), BattleBox {
    val bbp: BBPainter
    protected var prev: BufferedImage? = null
    val painter: BBPainter
        get() = bbp

    override fun paint() {
        paint(graphics)
    }

    override fun paint(g: Graphics) {
        synchronized(bbp) {
            val w = width
            val h = height
            if (w * h == 0) return
            if (bbp.pt < bbp.bf.sb.time || prev == null) {
                bbp.pt = bbp.bf.sb.time
                prev = image
            }
            if (prev == null) return
            g.drawImage(prev, 0, 0, null)
            g.color = Color.ORANGE
            g.drawString("Time cost: " + Timer.Companion.inter + "%, " + bbp.pt, 20, 20)
            g.dispose()
        }
    }

    override fun reset() {
        prev = null
    }

    protected open val image: BufferedImage?
        protected get() {
            val w = width
            val h = height
            val img: BufferedImage = createImage(w, h) as BufferedImage ?: return null
            val gra: Graphics2D = img.getGraphics() as Graphics2D
            val g: FakeGraphics = FG2D(gra)
            bbp.draw(g)
            gra.dispose()
            return img
        }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        bbp = if (p == 0) BBPainter(bip, bas, this) else BBCtrl(bip, bas as SBCtrl?, this)
        ignoreRepaint = true
    }
}
