package jogl.util

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
import common.system.fake.FakeImage.Marker
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import jogl.GLStatic
import jogl.util.GLGraphics
import jogl.util.GLImage
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
import java.io.*
import java.util.function.Supplier
import javax.imageio.ImageIO

class AmbImage : FakeImage {
    private var stream: Supplier<InputStream>?
    private var file: File?
    private var par: AmbImage?
    private var cs: IntArray?
    private var force = false
    private var failed = false
    private var bimg: FIBI? = null
    private var gl: GLImage? = null

    constructor(b: BufferedImage?) {
        stream = null
        file = null
        par = null
        cs = null
        bimg = FIBI.Companion.build(b) as FIBI
        force = true
    }

    constructor(f: File?) {
        stream = null
        file = f
        par = null
        cs = null
    }

    constructor(sup: Supplier<InputStream>?) {
        stream = sup
        file = null
        par = null
        cs = null
    }

    private constructor(img: AmbImage, vararg c: Int) {
        stream = null
        file = null
        par = img
        cs = c
    }

    override fun bimg(): BufferedImage? {
        checkBI()
        return if (bimg == null) null else bimg.bimg()
    }

    val height: Int
        get() {
            check()
            return if (bimg != null) bimg.getHeight() else gl.getHeight()
        }

    override fun getRGB(i: Int, j: Int): Int {
        checkBI()
        return bimg.getRGB(i, j)
    }

    override fun getSubimage(i: Int, j: Int, k: Int, l: Int): FakeImage? {
        return AmbImage(this, i, j, k, l)
    }

    val width: Int
        get() {
            check()
            return if (bimg != null) bimg.getWidth() else gl.getWidth()
        }

    override fun gl(): Any? {
        checkGL()
        return gl
    }

    val isValid: Boolean
        get() = true

    override fun mark(str: Marker) {
        if (str == Marker.UNI) forceBI()
        if (str == Marker.BG) {
            checkBI()
            if (bimg.bimg().getWidth() % 4 != 0) force = true
        }
        if (str == Marker.EDI) forceBI()
        if (str == Marker.RECOLOR) checkBI()
        if (str == Marker.RECOLORED) {
            // TODO if graphics is faster?
            val abos = ByteArrayOutputStream()
            try {
                ImageIO.write(bimg(), "PNG", abos)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            force = false
            stream = Supplier { ByteArrayInputStream(abos.toByteArray()) }
            gl = null
        }
    }

    override fun setRGB(i: Int, j: Int, p: Int) {
        forceBI()
        bimg.setRGB(i, j, p)
    }

    override fun unload() {}
    private fun check() {
        if (gl != null || bimg != null) return
        if (GLStatic.ALWAYS_GLIMG || GLGraphics.Companion.count > 0) checkGL()
        if (gl == null) checkBI()
    }

    private fun checkBI() {
        if (bimg != null || failed) return
        try {
            bimg = if (stream != null) FIBI.Companion.builder.build(stream) as FIBI else if (file != null) FIBI.Companion.builder.build(file) as FIBI else {
                par!!.checkBI()
                par!!.bimg.getSubimage(cs!![0], cs!![1], cs!![2], cs!![3])
            }
            if (bimg == null) failed = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun checkGL() {
        if (gl != null) return
        if (force) gl = GLImage.Companion.build(bimg.bimg()) else if (stream != null) gl = GLImage.Companion.build(stream!!.get()) else if (file != null) gl = GLImage.Companion.build(file!!) else {
            par!!.checkGL()
            if (par!!.gl != null) gl = par!!.gl.getSubimage(cs!![0], cs!![1], cs!![2], cs!![3])
        }
    }

    private fun forceBI() {
        checkBI()
        force = true
        gl = null
    }
}
