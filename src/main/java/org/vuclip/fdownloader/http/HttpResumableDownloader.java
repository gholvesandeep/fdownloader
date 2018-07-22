package org.vuclip.fdownloader.http;

import org.vuclip.fdownloader.DownloadJob;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpResumableDownloader extends AbstractHttpDownloader {
    private final static String RANGE = "Range";
    @Override
    protected void downloadFromUrl(DownloadJob entry, String url) throws Exception {
        HttpURLConnection connection = null;
        FileOutputStream outputStream = null;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            long totalBytesRead = entry.getFile().length();
            connection.setRequestProperty(RANGE, getRangeHeader(totalBytesRead));
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_PARTIAL) {
                long totalSize = totalBytesRead + connection.getContentLengthLong();
                InputStream inputStream = connection.getInputStream();
                outputStream = new FileOutputStream(entry.getFile(), true);
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = 0;
                while ((bytesRead = inputStream.read(buffer)) > -1 && !Thread.currentThread().isInterrupted()) {
                    outputStream.write(buffer, 0, bytesRead);
                    outputStream.flush();
                    totalBytesRead += bytesRead;
                    this.notifyProgress(entry, totalBytesRead, totalSize);
                }
            } else {
                throw new Exception(responseCode + connection.getHeaderField("Location"));
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // TODO: ?
                }
            }
        }
    }

    private String getRangeHeader(long offset) {
        return "bytes=" + offset + "-";
    }
}
