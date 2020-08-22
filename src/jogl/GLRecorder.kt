package jogl

import common.battle.data.DataEntity
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
import page.RetFunc
import page.anim.AnimBox
import page.awt.RecdThread
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
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
