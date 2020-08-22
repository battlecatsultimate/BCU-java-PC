package page.awt;

import common.CommonStatic;
import common.CommonStatic.Config;
import common.system.fake.FakeGraphics;
import common.system.fake.FakeImage;
import common.system.fake.FakeTransform;
import page.anim.IconBox;

import java.awt.image.BufferedImage;

import static page.anim.IconBox.IBConf.*;

class IconBoxDef extends ViewBoxDef implements IconBox {

    private static final long serialVersionUID = 1L;

    protected IconBoxDef() {
        super(new IBCtrl());
        setFocusable(true);
        glow = 0;
        changeType();
    }

    @Override
    public void changeType() {
        FakeImage bimg = CommonStatic.getBCAssets().ico[mode][type].getImg();
        line[2] = bimg.getWidth();
        line[3] = bimg.getHeight();
    }

    @Override
    public synchronized void draw(FakeGraphics gra) {
        Config cfg = CommonStatic.getConfig();
        boolean b = cfg.ref;
        cfg.ref = false;
        getCtrl().predraw(gra);
        FakeTransform at = gra.getTransform();
        super.draw(gra);
        gra.setTransform(at);
        cfg.ref = b;
        getCtrl().postdraw(gra);
    }

    @Override
    public BufferedImage getClip() {
        FakeImage bimg = CommonStatic.getBCAssets().ico[mode][type].getImg();
        int bw = bimg.getWidth();
        int bh = bimg.getHeight();
        double r = Math.min(1.0 * line[2] / bw, 1.0 * line[3] / bh);
        BufferedImage clip = prev.getSubimage(line[0], line[1], (int) (bw * r), (int) (bh * r));
        BufferedImage ans = new BufferedImage(bw, bh, BufferedImage.TYPE_3BYTE_BGR);
        ans.getGraphics().drawImage(clip, 0, 0, bw, bh, null);
        return ans;
    }

    @Override
    public IBCtrl getCtrl() {
        return (IBCtrl) ctrl;
    }

    @Override
    public void setBlank(boolean selected) {
        blank = selected;
    }

}
