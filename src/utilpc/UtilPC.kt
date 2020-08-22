package utilpc

import com.google.common.io.Files
import common.CommonStatic
import common.CommonStatic.ImgReader
import common.CommonStatic.Itf
import common.io.InStream
import common.io.OutStream
import common.pack.Context
import common.pack.Context.ErrType
import common.pack.Context.SupExc
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.system.VImg
import common.system.fake.FakeImage
import common.system.files.FDByte
import common.system.files.VFile
import common.util.Data
import common.util.anim.ImgCut
import common.util.anim.MaAnim
import common.util.anim.MaModel
import common.util.pack.Background
import common.util.stage.Music
import common.util.unit.Form
import io.BCMusic
import io.BCUReader
import io.BCUWriter
import page.LoadPage
import utilpc.awt.FG2D
import java.awt.Graphics2D
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*
import java.util.function.Function
import javax.imageio.ImageIO
import javax.swing.ImageIcon

object UtilPC {
    fun getBg(bg: Background, w: Int, h: Int): ImageIcon {
        val r = h / 1100.0
        val fw = (768 * r).toInt()
        val fh = (510 * r).toInt()
        bg.check()
        val temp = BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
        val g: Graphics2D = temp.graphics as Graphics2D
        val fg = FG2D(g)
        if (bg.top && bg.parts.size > Background.Companion.TOP) {
            var i = 0
            while (i * fw < w) {
                fg.drawImage(bg.parts[Background.Companion.TOP], fw * i.toDouble(), 0.0, fw.toDouble(), fh.toDouble())
                i++
            }
        } else {
            fg.gradRect(0, 0, w, fh, 0, 0, bg.cs[0], 0, fh, bg.cs[1])
        }
        var i = 0
        while (i * fw < w) {
            fg.drawImage(bg.parts[Background.Companion.BG], fw * i.toDouble(), fh.toDouble(), fw.toDouble(), fh.toDouble())
            i++
        }
        fg.gradRect(0, fh * 2, w, h - fh * 2, 0, fh * 2, bg.cs[2], 0, fh * 3, bg.cs[3])
        g.dispose()
        return ImageIcon(temp)
    }

    fun getIcon(type: Int, id: Int): BufferedImage? {
        var type = type
        var id = id
        type += id / 100
        id %= 100
        return if (CommonStatic.getBCAssets().icon.get(type).get(id) == null) null else CommonStatic.getBCAssets().icon.get(type).get(id).getImg().bimg() as BufferedImage
    }

    fun getIcon(v: VImg): ImageIcon? {
        v.check()
        return if (v.bimg == null || v.bimg.bimg() == null) null else ImageIcon(v.bimg.bimg() as Image)
    }

    fun lvText(f: Form, lvs: IntArray): Array<String> {
        val pc: PCoin? = f.pCoin
        return if (pc == null) arrayOf("Lv." + lvs[0], "") else {
            var lab: String = Interpret.PCTX.get(pc.info.get(0).get(0))
            var str = "Lv." + lvs[0] + ", {"
            for (i in 1..4) {
                str += lvs[i].toString() + ","
                lab += ", " + Interpret.PCTX.get(pc.info.get(i).get(0))
            }
            str += lvs[5].toString() + "}"
            arrayOf(str, lab)
        }
    }

    class PCItr : Itf {
        private class MusicReader(private val pid: Int, private val mid: Int) : ImgReader {
            override fun readFile(`is`: InStream): File? {
                val bs: ByteArray = `is`.subStream().nextBytesI()
                val path = "./pack/music/" + Data.Companion.hex(pid) + "/" + Data.Companion.trio(mid) + ".ogg"
                val f: File = CommonStatic.def.route(path)
                Data.Companion.err(Context.RunExc { Context.Companion.check(f) })
                try {
                    Files.write(bs, f)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                return f
            }

            override fun readImg(str: String?): FakeImage? {
                return null
            }

            override fun readImgOptional(str: String?): VImg? {
                return null
            }
        }

        private class PCAL : AnimLoader {
            private var name: String? = null
            private var num: FakeImage? = null
            private var imgcut: ImgCut
            private var mamodel: MaModel
            private var anims: Array<MaAnim?>
            private var uni: VImg? = null
            private var edi: VImg? = null

            constructor(`is`: InStream) {
                name = "local animation"
                try {
                    num = FakeImage.Companion.read(`is`.nextBytesI())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                imgcut = ImgCut.Companion.newIns(FDByte(`is`.nextBytesI()))
                mamodel = MaModel.Companion.newIns(FDByte(`is`.nextBytesI()))
                val n: Int = `is`.nextInt()
                anims = arrayOfNulls<MaAnim>(n)
                for (i in 0 until n) anims[i] = MaAnim.Companion.newIns(FDByte(`is`.nextBytesI()))
                if (!`is`.end()) {
                    val vimg = VImg(`is`.nextBytesI())
                    if (vimg.getImg().getHeight() == 32) edi = vimg else uni = vimg
                }
                if (!`is`.end()) uni = VImg(`is`.nextBytesI())
            }

            constructor(`is`: InStream, r: ImgReader) {
                `is`.nextString()
                num = r.readImg(`is`.nextString())
                edi = r.readImgOptional(`is`.nextString())
                uni = r.readImgOptional(`is`.nextString())
                imgcut = ImgCut.Companion.newIns(FDByte(`is`.nextBytesI()))
                mamodel = MaModel.Companion.newIns(FDByte(`is`.nextBytesI()))
                val n: Int = `is`.nextInt()
                anims = arrayOfNulls<MaAnim>(n)
                for (i in 0 until n) anims[i] = MaAnim.Companion.newIns(FDByte(`is`.nextBytesI()))
            }

            override fun getEdi(): VImg? {
                return edi
            }

            override fun getIC(): ImgCut {
                return imgcut
            }

            override fun getMA(): Array<MaAnim?> {
                return anims
            }

            override fun getMM(): MaModel {
                return mamodel
            }

            override fun getName(): ResourceLocation {
                return ResourceLocation(null, name)
            }

            override fun getNum(): FakeImage? {
                return num
            }

            override fun getStatus(): Int {
                return 1
            }

            override fun getUni(): VImg? {
                return uni
            }
        }

        override fun exit(save: Boolean) {
            BCUWriter.logClose(save)
            System.exit(0)
        }

        override fun getMusicLength(f: Music): Long {
            return if (f.data == null) {
                -1
            } else try {
                val otr = OggTimeReader(f)
                otr.getTime()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                -1
            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
        }

        override fun getMusicReader(pid: Int, mid: Int): ImgReader? {
            return MusicReader(pid, mid)
        }

        override fun getReader(f: File?): ImgReader? {
            return null
        }

        override fun loadAnim(`is`: InStream, r: ImgReader?): AnimLoader {
            return if (r == null) PCAL(`is`) else PCAL(`is`, r)
        }

        override fun prog(str: String?) {
            LoadPage.Companion.prog(str)
        }

        override fun readBytes(fi: File?): InStream {
            return BCUReader.readBytes(fi)
        }

        override fun readReal(fi: File?): VImg? {
            return try {
                VImg(ImageIO.read(fi))
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }

        override fun <T> readSave(path: String, func: Function<Queue<String?>?, T>): T {
            return CommonStatic.ctx.noticeErr(SupExc<T> { func.apply(VFile.Companion.getFile(path).getData().readLine()) }, ErrType.ERROR,
                    "failed to read $path")
        }

        override fun route(path: String?): File {
            return File(path)
        }

        override fun setSE(ind: Int) {
            BCMusic.setSE(ind)
        }

        override fun writeBytes(os: OutStream?, path: String?): Boolean {
            return BCUWriter.writeBytes(os, path)
        }

        override fun writeErrorLog(e: Exception?) {}
    }
}
