package org.vuclip.fdownloader;

import org.vuclip.fdownloader.http.HttpDownloaderFactory;
import org.vuclip.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DownloadManager {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private final Map<DownloadJob, Future> downloads = new HashMap<>();
    private final Map<DownloadJob, Downloader> downloaders = new HashMap<>();

    private static DownloadManager instance = null;

    /**
     * Private Constructor to control instance creation.
     */
    private DownloadManager() {
    }

    /**
     * Single instance of Download Manager
     * @return DownloadManager instance
     */
    public static DownloadManager getInstance() {
        if (instance == null) {
            instance = new DownloadManager();
        }
        return instance;
    }
    /**
     * Start download job
     * @param job Download Job
     */
    public void download(DownloadJob job) throws Exception {
        if (!downloads.containsKey(job)) {
            // TODO: Maybe can be optimized to have pools of Downloaders
            Downloader downloader = getDownloaderFactory(job.getSourceUrl()).getInstance();
            downloaders.put(job, downloader);
            triggerDownload(job);
        }
    }

    /**
     * Unique set of download jobs
     * @return set of download job
     */
    public Set<DownloadJob> getDownloads() {
        return this.downloads.keySet();
    }

    /**
     * Pause a download process
     * @param job download job
     */
    public void pause(DownloadJob job) {
        if (this.downloads.containsKey(job)) {
            this.downloads.get(job).cancel(true);
        }
    }

    /**
     * Resume a download process
     * @param job download job
     */
    public void resume(DownloadJob job) {
        if (this.downloads.containsKey(job)) {
            triggerDownload(job);
        }
    }

    /**
     * Returns 'true' if given download job is paused.
     * @param job Download Job
     * @return true, if its paused otherwise false.
     */
    public boolean isPaused(DownloadJob job) {
        return this.downloads.containsKey(job) && this.downloads.get(job).isCancelled();
    }

    private void triggerDownload(DownloadJob job) {
        this.downloads.put(job, executorService.submit(() -> {
            try {
                downloaders.get(job).download(job);
            } catch (Exception e) {
                onError(job, e.getMessage());
            }
        }));
    }

    private void onError(DownloadJob downloadJob, String message) {
        Utils.printToConsole("Failed to download file. " + message);
        downloadJob.getDownloadState().failed();
    }

    private DownloaderFactory getDownloaderFactory(final String sourceUrl) {
        if (sourceUrl.startsWith("http:")) {
            return new HttpDownloaderFactory(sourceUrl);
        } else if (sourceUrl.startsWith("https:")) {
            throw new UnsupportedOperationException("Downloading of HTTPS resources are not supported yet");
        } else {
            throw new UnsupportedOperationException("Download operation not supported yet for URL: " + sourceUrl);
        }
    }

    public boolean isShutdown() {
        return executorService.isShutdown();
    }

    public void shutdownNow() {
        executorService.shutdownNow();
    }
}
