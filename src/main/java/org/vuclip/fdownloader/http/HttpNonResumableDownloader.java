package org.vuclip.fdownloader.http;

import org.vuclip.fdownloader.DownloadJob;

import java.io.FileOutputStream;
import java.net.HttpURLConnection;

public class HttpNonResumableDownloader extends AbstractHttpDownloader {
    @Override
    protected void downloadFromUrl(DownloadJob entry, String url) {
        throw new UnsupportedOperationException("HTTP Non-Resumable downloads are not yet supported");
    }
}
