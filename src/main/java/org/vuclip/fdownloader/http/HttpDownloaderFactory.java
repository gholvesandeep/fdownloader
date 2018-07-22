package org.vuclip.fdownloader.http;

import org.vuclip.fdownloader.Downloader;
import org.vuclip.fdownloader.DownloaderFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloaderFactory implements DownloaderFactory {
    private static final String BYTES = "bytes";
    private static final String ACCEPT_RANGES = "Accept-Ranges";

    private final String sourceUrl;

    public HttpDownloaderFactory(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Override
    public Downloader getInstance() throws IOException {
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(sourceUrl);
            connection = (HttpURLConnection) url.openConnection();
            boolean supportRanges = BYTES.equalsIgnoreCase(connection.getHeaderField(ACCEPT_RANGES));
            if (supportRanges) {
                return new HttpResumableDownloader();
            } else {
                return new HttpNonResumableDownloader();
            }
        } finally {
            if (null != connection)
                connection.disconnect();
        }
    }
}
