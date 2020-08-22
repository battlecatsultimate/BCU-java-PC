package page.awt

import common.CommonStatic
import common.system.fake.FakeGraphics
import common.system.fake.FakeImage
import common.system.fake.FakeTransform
import page.anim.IconBox
import page.anim.IconBox.IBConf
import page.anim.IconBox.IBCtrl
import java.awt.image.BufferedImage

internal class IconBoxDef : ViewBoxDef(IBCtrl()), IconBox {
    override fun changeType() {
        val bimg: FakeImage = CommonStatic.getBCAssets().ico.get(IBConf.mode).get(IBConf.type).getImg()
        IBConf.line.get(2) = bimg.getWidth()
        IBConf.line.get(3) = bimg.getHeight()
    }

    @Synchronized
    override fun draw(gra: FakeGraphics) {
        val cfg: CommonStatic.Config = CommonStatic.getConfig()
        val b: Boolean = cfg.ref
        cfg.ref = false
        getCtrl().predraw(gra)
        val at: FakeTransform = gra.getTransform()
        super.draw(gra)
        gra.setTransform(at)
        cfg.ref = b
        getCtrl().postdraw(gra)
    }

    val clip: BufferedImage?
        get() {
            val bimg: FakeImage = CommonStatic.getBCAssets().ico.get(IBConf.mode).get(IBConf.type).getImg()
            val bw: Int = bimg.getWidth()
            val bh: Int = bimg.getHeight()
            val r: Double = Math.min(1.0 * IBConf.line.get(2) / bw, 1.0 * IBConf.line.get(3) / bh)
            val clip: BufferedImage = prev.getSubimage(IBConf.line.get(0), IBConf.line.get(1), (bw * r).toInt(), (bh * r).toInt())
            val ans = BufferedImage(bw, bh, BufferedImage.TYPE_3BYTE_BGR)
            ans.getGraphics().drawImage(clip, 0, 0, bw, bh, null)
            return ans
        }

    override fun getCtrl(): IBCtrl {
        return ctrl as IBCtrl
    }

    override fun setBlank(selected: Boolean) {
        blank = selected
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        setFocusable(true)
        IBConf.glow = 0
        changeType()
    }
}
