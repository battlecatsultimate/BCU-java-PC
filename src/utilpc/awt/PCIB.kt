package utilpc.awt

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
import common.system.fake.FakeImage
import common.system.fake.ImageBuilder
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import utilpc.awt.FIBI
import java.io.IOException

class PCIB : ImageBuilder() {
    @Throws(IOException::class)
    override fun build(o: Any?): FakeImage? {
        if (o == null) return null
        return if (o is FakeImage) o as FakeImage? else FIBI.Companion.builder.build(o)
    }

    @Throws(IOException::class)
    override fun write(o: FakeImage, fmt: String?, out: Any?): Boolean {
        return FIBI.Companion.builder.write(o, fmt, out)
    }
}
