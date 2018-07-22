package org.vuclip.fdownloader;

import java.io.File;
import java.util.Observable;

/**
 * DownloadJob represents one single instance of Download of given downloadURL and a location where it will be
 * downloaded. It also knows its current download status in {@link DownloadState}
 * <p>
 *
 * @author sgholve
 */
public class DownloadJob extends Observable {
    /**
     * Source URL of a file to be downloaded
     */
    private final String sourceUrl;

    /**
     * Target location of the file on system to be downloaded
     */
    private String targetLocation;

    /**
     * File object of tobe downloaded file.
     */
    private final File file;

    /**
     * DownloadState of the current DownloadJob
     */
    private DownloadState downloadState = new DownloadState(0, 0);

    /**
     * Constructs a download job with download url and target location.
     * @param sourceUrl source url of the download file.
     * @param targetLocation target location of the download file.
     */
    public DownloadJob(String sourceUrl, String targetLocation) {
        this.sourceUrl = sourceUrl;
        this.targetLocation = targetLocation;
        this.file = new File(this.targetLocation + ".tmp");
    }

    /**
     * Source URL of (tobe) Downloaded file.
     * @return Source URL
     */
    public String getSourceUrl() {
        return sourceUrl;
    }

    /**
     * File object representing the actual target file on the system
     * @return File representing target
     */
    public File getFile() {
        return file;
    }

    /**
     * location or filepath where file tobe downloaded.
     * @return target file location.
     */
    public String getTargetLocation() {
        return targetLocation;
    }

    /**
     * Current download state
     * @return download state
     */
    public DownloadState getDownloadState() {
        return downloadState;
    }

    public void updateState(DownloadState state) {
        this.downloadState = state;
        if (this.downloadState.isComplete()) {
            this.file.renameTo(new File(this.targetLocation));
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public int hashCode() {
        int result = sourceUrl.hashCode();
        result = 31 * result + targetLocation.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        DownloadJob that = (DownloadJob)obj;
        return sourceUrl.equals(that.sourceUrl) && targetLocation.equals(that.targetLocation);
    }
}
