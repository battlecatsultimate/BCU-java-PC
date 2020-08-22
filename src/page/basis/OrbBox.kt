package page.basis

import common.CommonStatic
import common.system.fake.FakeGraphics
import utilpc.awt.FG2D
import java.awt.Canvasimport
import java.awt.Graphics
import java.awt.image.BufferedImage

class OrbBox(private var orbs: IntArray) : Canvas() {
    fun changeOrb(orbs: IntArray) {
        this.orbs = orbs
    }

    @Synchronized
    override fun paint(g: Graphics) {
        if (orbs.size == 0) {
            g.clearRect(0, 0, width, height)
            return
        }
        val a = Math.min(width, height).toDouble()
        val w = width.toDouble()
        val h = height.toDouble()
        val img: BufferedImage = createImage(a.toInt(), a.toInt()) as BufferedImage
        val f = FG2D(img.graphics)
        f.drawImage(CommonStatic.getBCAssets().TRAITS.get(Orb.Companion.reverse(orbs[1])), 0.0, 0.0, a, a)
        f.setComposite(FakeGraphics.Companion.TRANS, 204, 0)
        f.drawImage(CommonStatic.getBCAssets().TYPES.get(orbs[0]), 0.0, 0.0, a, a)
        f.setComposite(FakeGraphics.Companion.DEF, 0, 0)
        f.drawImage(CommonStatic.getBCAssets().GRADES.get(orbs[2]), 0.0, 0.0, a, a)
        g.drawImage(img, ((w - a) / 2) as Int, ((h - a) / 2) as Int, null)
        g.dispose()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ignoreRepaint = true
    }
}
