package org.vuclip.fdownloader.http;

import org.vuclip.fdownloader.DownloadJob;
import org.vuclip.fdownloader.DownloadState;
import org.vuclip.fdownloader.Downloader;

public abstract class AbstractHttpDownloader implements Downloader {
    static final int BUFFER_SIZE = 4096;

    @Override
    public void download(DownloadJob job) throws Exception {
        downloadFromUrl(job, job.getSourceUrl());
    }

    void notifyProgress(DownloadJob job, long sizeDownloaded, long totalSize) {
        // FIXME: Lots of 'DownloadState' objects getting created with this approach.
        job.updateState(new DownloadState(totalSize, sizeDownloaded));
    }

    protected abstract void downloadFromUrl(DownloadJob entry, String url) throws Exception;
}
