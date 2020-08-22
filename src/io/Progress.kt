package io

import com.google.api.client.googleapis.media.MediaHttpDownloader
import com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadState
import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener
import io.Progress
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
