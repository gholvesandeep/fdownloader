package org.vuclip.fdownloader;

/**
 * Interface for Downloader supporting different types of downloaders based on source url
 */
public interface Downloader {
    /**
     * Download operation of the given source and target in {@link DownloadJob}
     * @param job Download job having source and target information along with its current state
     */
    void download(DownloadJob job) throws Exception;
}
