package io;

import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener;

import java.util.function.Consumer;

public class Progress implements MediaHttpDownloaderProgressListener {

    public static final int WAIT = 0, CURR = 1, DONE = 2;

    public int state = 0;
    public double prog = 0;
    public long tot, cur;

    private final Consumer<Progress> c;

    protected Progress(Consumer<Progress> cons) {
        c = cons;
        update();
    }

    @Override
    public void progressChanged(MediaHttpDownloader downloader) {
        switch (downloader.getDownloadState()) {
            case MEDIA_COMPLETE:
                state = DONE;
                break;
            case MEDIA_IN_PROGRESS:
                state = CURR;
                break;
            default:
                break;
        }
        prog = downloader.getProgress();
        cur = downloader.getNumBytesDownloaded();
        tot = prog == 0 ? -1 : (long) (cur / prog);
        update();
    }

    protected void update() {
        if (c != null)
            c.accept(this);
    }

}
