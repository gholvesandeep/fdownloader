package org.vuclip.fdownloader;

public interface DownloaderFactory {
    Downloader getInstance() throws Exception;
}
