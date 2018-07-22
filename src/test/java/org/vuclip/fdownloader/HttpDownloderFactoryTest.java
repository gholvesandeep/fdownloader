package org.vuclip.fdownloader;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HttpDownloderFactoryTest {

    @Test
    public void downloadHttpsResource_shouldFail() {
        final String url = "https://somefile.zip";
        final String target = "./somefile.zip";
        DownloadManager manager = DownloadManager.getInstance();
        DownloadJob job = new DownloadJob(url, target);
        try {
            manager.download(job);
            Assert.fail("Https resource download should fail as its not yet supported");
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), UnsupportedOperationException.class);
            Assert.assertEquals("Downloading of HTTPS resources are not supported yet", e.getMessage());
        }
    }

    @Test
    public void downloadFtpResource_shouldFail() {
        final String url = "ftp://somefile.zip";
        final String target = "./somefile.zip";
        DownloadManager manager = DownloadManager.getInstance();
        DownloadJob job = new DownloadJob(url, target);
        try {
            manager.download(job);
            Assert.fail("FTP resource download should fail as its not yet supported");
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), UnsupportedOperationException.class);
            Assert.assertEquals("Download operation not supported yet for URL: " + url, e.getMessage());
        }
    }
}
