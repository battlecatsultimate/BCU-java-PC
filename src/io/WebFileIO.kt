package io

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.media.MediaHttpDownloader
import com.google.api.client.http.*
import com.google.api.client.util.ExponentialBackOff
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
import common.pack.Context
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.pack.Source.SourceAnimLoader
import common.pack.Source.SourceAnimSaver
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.util.Data
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import main.Opts
import main.Printer
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import page.JL
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.function.Consumer

open class WebFileIO {
    companion object {
        const val SMOOTH = 1 shl 17
        const val FAST = 1 shl 23
        const val MAX = 1 shl 25
        private var transport: HttpTransport? = null
        fun download(size: Int, url: String, file: File?, c: Consumer<Progress?>?): Boolean {
            Data.Companion.err(Context.RunExc { Context.Companion.check(file) })
            return try {
                if (transport == null) transport = GoogleNetHttpTransport.newTrustedTransport()
                val out: OutputStream = FileOutputStream(file)
                val gurl = GenericUrl(url)
                val downloader = MediaHttpDownloader(transport, io.Handler())
                downloader.setChunkSize(size)
                downloader.setProgressListener(Progress(c))
                downloader.download(gurl, out)
                out.close()
                Printer.p("WebFileIO", 55, "download success: $url")
                true
            } catch (e: Exception) {
                e.printStackTrace()
                Opts.dloadErr(url)
                false
            }
        }

        fun download(url: String, file: File?): Boolean {
            return download(MAX, url, file, null)
        }

        fun download(url: String, file: File?, c: Consumer<Progress?>?): Boolean {
            return download(FAST, url, file, c)
        }

        @Throws(IOException::class)
        fun upload(file: File?, url: String?): Boolean {
            val client: CloseableHttpClient = HttpClients.createDefault()
            val post = HttpPost(url)
            val fileBody = FileBody(file, ContentType.DEFAULT_BINARY)
            val builder: MultipartEntityBuilder = MultipartEntityBuilder.create()
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
            builder.addPart("catFile", fileBody)
            val reqEntity: HttpEntity = builder.build()
            post.setEntity(reqEntity)
            val response: HttpResponse = client.execute(post)
            val statusCode = response.statusLine.statusCode
            if (statusCode == 200) return true
            System.err.println("statusCode: $statusCode")
            val respEntity: HttpEntity = response.entity
            val responseString: String = EntityUtils.toString(respEntity, "UTF-8")
            System.err.println("response body: ")
            System.err.println(responseString)
            return false
        }
    }
}

internal class Handler : HttpRequestInitializer {
    @Throws(IOException::class)
    override fun initialize(request: HttpRequest) {
        request.unsuccessfulResponseHandler = HttpBackOffUnsuccessfulResponseHandler(ExponentialBackOff())
        request.ioExceptionHandler = HttpBackOffIOExceptionHandler(ExponentialBackOff())
    }
}
