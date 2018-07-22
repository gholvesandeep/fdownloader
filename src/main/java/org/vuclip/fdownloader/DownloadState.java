package org.vuclip.fdownloader;

public class DownloadState {
    /**
     * Total size of the file.
     */
    private long totalSize;

    /**
     * Downloaded size of the file
     */
    private long downloadedSize;

    /**
     * true, if download operation failed. Otherwise false.
     */
    private boolean failed;

    /**
     * Constructs an download state with totalSize of the file, and current download state in downloadedSize of
     * the file.
     * <p>
     * @param totalSize total size of the download file.
     * @param downloadedSize downloaded size of the download file
     */
    public DownloadState(long totalSize, long downloadedSize) {
        this.totalSize = totalSize;
        this.downloadedSize = downloadedSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public long getDownloadedSize() {
        return downloadedSize;
    }

    public boolean isComplete() {
        return getTotalSize() > 0 && getDownloadedSize() >= getTotalSize();
    }

    public boolean hasFailed() {
        return this.failed;
    }

    public void failed() {
        this.failed = true;
    }

}
