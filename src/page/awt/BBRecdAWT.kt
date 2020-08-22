package page.awt

import common.battle.BattleField
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
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.anim.AnimBox
import page.awt.RecdThread
import page.battle.BBRecd
import page.battle.BattleBox.OuterBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
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
