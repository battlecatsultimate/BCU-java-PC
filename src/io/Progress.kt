package io

import com.google.api.client.googleapis.media.MediaHttpDownloader
import com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadState
import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener
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
import io.Progress
import page.JL
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.util.function.Consumer

class Progress(private val c: Consumer<Progress>?) : MediaHttpDownloaderProgressListener {
    var state = 0
    var prog = 0.0
    var tot: Long = 0
    var cur: Long = 0
    override fun progressChanged(downloader: MediaHttpDownloader) {
        when (downloader.getDownloadState()) {
            DownloadState.MEDIA_COMPLETE -> state = Progress.Companion.DONE
            DownloadState.MEDIA_IN_PROGRESS -> state = Progress.Companion.CURR
            else -> {
            }
        }
        prog = downloader.getProgress()
        cur = downloader.getNumBytesDownloaded()
        tot = if (prog == 0.0) -1 else (cur / prog).toLong()
        update()
    }

    protected fun update() {
        c?.accept(this)
    }

    companion object {
        const val WAIT = 0
        const val CURR = 1
        const val DONE = 2
    }

    init {
        update()
    }
}
