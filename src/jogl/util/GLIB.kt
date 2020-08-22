package jogl.util

import common.CommonStatic
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
import common.system.fake.FakeImage
import common.system.fake.ImageBuilder
import common.system.files.FileData
import common.system.files.VFile
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
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.function.Supplier

class GLIB : ImageBuilder() {
    @Throws(IOException::class)
    override fun build(o: Any?): FakeImage? {
        if (o == null) return null
        if (o is FakeImage) return o as FakeImage?
        if (CommonStatic.getConfig().icon) return FIBI.Companion.builder.build(o)
        if (o is FileData) return AmbImage(Supplier { o.stream })
        if (o is VFile<*>) return AmbImage(Supplier<InputStream> { (o as VFile<*>).getData().getStream() })
        if (o is ByteArray) return AmbImage(Supplier<InputStream> { ByteArrayInputStream(o as ByteArray?) })
        if (o is Supplier<*>) return AmbImage(o as Supplier<InputStream?>?)
        if (o is BufferedImage) return AmbImage(o as BufferedImage?)
        if (o is File) return AmbImage(o as File?)
        throw IOException("cannot parse input with class " + o.javaClass)
    }

    @Throws(IOException::class)
    override fun write(o: FakeImage, fmt: String?, out: Any?): Boolean {
        return FIBI.Companion.builder.write(o, fmt, out)
    }
}
