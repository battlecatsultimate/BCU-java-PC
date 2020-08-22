package utilpc

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
import common.util.stage.Music
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
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer

class OggTimeReader(mus: Music) {
    private val mus: Music
    private val fis: InputStream?
    val canUse: Boolean
    var time = 0
    fun getNextByte(): Int {
        val res = ByteArray(1)
        try {
            fis!!.read(res)
        } catch (e: IOException) {
            e.printStackTrace()
            return -1
        }
        return res[0].toInt()
    }

    fun getNextDouble(): Double {
        val res = ByteArray(8)
        try {
            fis!!.read(res)
        } catch (e: Exception) {
            e.printStackTrace()
            return (-1).toDouble()
        }
        return ByteBuffer.wrap(res).double
    }

    fun getNextInt(): Int {
        val res = ByteArray(4)
        try {
            fis!!.read(res)
        } catch (e: IOException) {
            e.printStackTrace()
            return -1
        }
        val real = ByteArray(4)
        for (i in res.indices.reversed()) {
            real[res.size - 1 - i] = res[i]
        }
        return ByteBuffer.wrap(real).int
    }

    fun getNextString(len: Int): String? {
        val res = ByteArray(len)
        try {
            fis!!.read(res)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return String(res)
    }

    fun getTime(): Long {
        var start = getNextString(4)
        if (start != "OggS") {
            return -1
        }
        skip(1)
        val headerVersion = getNextByte()
        val header: String
        header = when (headerVersion) {
            0x00 -> "Continuation"
            0x02 -> "Begin"
            0x04 -> "End"
            else -> "Unknown"
        }
        if (header == "Unknown") {
            return -1
        }
        skip(20)
        val page = getNextByte()
        skip(page + 1)
        start = getNextString(6)
        if (start != "vorbis") {
            return -2
        }
        skip(13)
        val bitNormal = getNextInt()
        return if (bitNormal > 500000 || bitNormal < 0) {
            -1
        } else mus.data.size() * 8 * 1000 / bitNormal
    }

    fun getTimeWithInfo(): Long {
        var start = getNextString(4)
        if (start != "OggS") {
            return -1
        }
        val ver = getNextByte()
        println("Version : $ver")
        val headerVersion = getNextByte()
        val header: String
        header = when (headerVersion) {
            0x00 -> "Continuation"
            0x02 -> "Begin"
            0x04 -> "End"
            else -> "Unknown"
        }
        println("Header : $header")
        if (header == "Unknown") {
            return -1
        }
        val granule = getNextDouble()
        println("Granule Position : $granule")
        val serial = getNextInt()
        println("Serial : $serial")
        val pageNumber = getNextInt()
        println("Page Number : $pageNumber")
        val checkSum = getNextInt()
        println("Checksum : " + Integer.toHexString(checkSum))
        val page = getNextByte()
        println("Page : $page")
        skip(page)
        val id = getNextByte()
        println("Header ID : $id")
        start = getNextString(6)
        println(start)
        if (start != "vorbis") {
            return -2
        }
        val vorver = getNextInt()
        println("Vorbis Version : $vorver")
        val ch = getNextByte()
        println("Number of Channel : $ch")
        val sample = getNextInt()
        print("Sample Rate : " + sample + "Hz")
        val bitMax = getNextInt()
        println("Max Bitrate : " + bitMax + "bps")
        val bitNormal = getNextInt()
        println("Normal Bitrate : " + bitNormal + "bps")
        val bitMin = getNextInt()
        println("Min Bitrate : " + bitMin + "bps")
        return if (bitNormal > 500000 || bitNormal < 0) {
            -3
        } else mus.data.size() * 8 * 1000 / bitNormal
    }

    fun skip(len: Int) {
        try {
            fis!!.skip(len.toLong())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val MAX_VORBIS_BITRATE = 500000
    }

    init {
        this.mus = mus
        fis = mus.data.getStream()
        canUse = fis != null
    }
}
