package io.drive

import com.google.api.client.googleapis.media.MediaHttpDownloader
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import com.google.api.services.drive.model.FileList
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
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.io.*

//This class is for testing, so you can ignore it
object DriveDownload {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val service: Drive = DriveUtil.getDriveService()
        val flist: FileList = service.files().list().execute()
        val files: List<File> = flist.getFiles()
        if (files == null || files.size == 0) {
            println("No Files Found")
        } else {
            var version = ""
            var id = ""
            var name = ""
            for (f in files) {
                if (f.name == "Description.txt") {
                    println("Found")
                    val `is`: InputStream = service.getRequestFactory()
                            .buildDeleteRequest(service.files().get(f.id).buildHttpRequestUrl()).execute()
                            .getContent()
                    val bis = BufferedInputStream(`is`)
                    val buffer = ByteArray(4096)
                    while (bis.read(buffer) != -1) {
                        version += String(buffer, "utf-8")
                    }
                    println(version)
                }
            }
            for (f in files) {
                if (f.name == "BCU_Android_$version.apk") {
                    name = f.name
                    id = f.id
                }
            }
            val f = java.io.File("C:/Users/user/Desktop/Utility/Credentials/api/$name")
            if (!f.exists()) {
                f.createNewFile()
            }
            val os: OutputStream = FileOutputStream(f)
            val d: MediaHttpDownloader = service.files().get(id).getMediaHttpDownloader()
            d.download(service.files().get(id).buildHttpRequestUrl(), os)
            while (d.getDownloadState() != MediaHttpDownloader.DownloadState.MEDIA_COMPLETE) {
                println("Download : " + d.getProgress() * 1000)
            }
            println(d.getProgress())
            println("END??")
        }
    }
}
