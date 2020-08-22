package page.battle

import common.CommonStatic
import common.CommonStatic.BCAuxAssets
import common.battle.SBCtrl
import common.io.assets.Admin
import common.io.assets.Admin.StaticPermitted
import common.io.assets.AssetLoader
import common.io.assets.AssetLoader.AssetHeader
import common.io.assets.AssetLoader.AssetHeader.AssetEntry
import common.io.json.JsonEncoder
import common.io.json.Test
import common.io.json.Test.JsonTest_0.JsonD
import common.io.json.Test.JsonTest_2
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.pack.Source.SourceAnimLoader
import common.pack.Source.SourceAnimSaver
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.system.P
import common.system.fake.FakeImage
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.Form
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.anim.AnimBox
import page.battle.BattleBox.BBPainter
import page.battle.BattleBox.OuterBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import utilpc.PP
import java.awt.Point
import java.awt.event.MouseEvent

class BBCtrl(bip: OuterBox?, bas: SBCtrl, bb: BattleBox?) : BBPainter(bip, bas, bb) {
    private val sbc: SBCtrl
    @Synchronized
    override fun click(p: Point?, button: Int) {
        val aux: BCAuxAssets = CommonStatic.getBCAssets()
        val w: Int = box.getWidth()
        val h: Int = box.getHeight()
        var hr: Double = unir
        for (i in 0..9) {
            val f: Form = sbc.sb.b.lu.fs.get(i / 5).get(i % 5)
            val img: FakeImage = if (f == null) aux.slot.get(0).getImg() else f.anim.uni.img
            val iw = (hr * img.getWidth()) as Int
            val ih = (hr * img.getHeight()) as Int
            val x = (w - iw * 5) / 2 + iw * (i % 5)
            val y = h - ih * (2 - i / 5)
            if (!PP(p).out(P(x, y), P(x + iw, y + ih), 0.0)) sbc.action.add(i)
            if (button != MouseEvent.BUTTON1) sbc.action.add(10)
        }
        hr = corr
        val left: FakeImage = aux.battle.get(0).get(0).getImg()
        val right: FakeImage = aux.battle.get(1).get(0).getImg()
        var ih = (hr * left.getHeight()) as Int
        var iw = (hr * left.getWidth()) as Int
        if (!PP(p).out(P(0, h - ih), P(iw, h), 0.0)) sbc.action.add(-1)
        iw = (hr * right.getWidth()) as Int
        ih = (hr * right.getHeight()) as Int
        if (!PP(p).out(P(w - iw, h - ih), P(w, h), 0.0)) sbc.action.add(-2)
        if (sbc.sb.conf.get(0) and 2 > 0) {
            val bimg: FakeImage = aux.battle.get(2).get(1).getImg()
            val cw: Int = bimg.getWidth()
            val ch: Int = bimg.getHeight()
            val mh: Int = aux.num.get(0).get(0).getImg().getHeight()
            if (!PP(p).out(P(w - cw, mh), P(w, mh + ch), 0.0)) sbc.action.add(-3)
        }
        reset()
    }

    init {
        sbc = bas
    }
}
