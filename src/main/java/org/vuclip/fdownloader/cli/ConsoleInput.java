package org.vuclip.fdownloader.cli;

import org.vuclip.fdownloader.DownloadJob;
import org.vuclip.fdownloader.DownloadManager;
import org.vuclip.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInput {
    private final String RESUME_MESSAGE = "Enter '" + RESUME + "' to resume";
    static final char PAUSE = 'p';
    private static final char RESUME = 'r';

    public void listen(DownloadJob job, DownloadManager downloadManager) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        char[] buffer = new char[1];

        while (!(job.getDownloadState().isComplete()) || job.getDownloadState().hasFailed()) {
            if (reader.ready() && reader.read(buffer) > 0) {
                char action = buffer[0];
                if (action == PAUSE && !downloadManager.isPaused(job)) {
                    downloadManager.pause(job);
                    Utils.printToConsole(RESUME_MESSAGE);
                } else if (action == RESUME && downloadManager.isPaused(job)) {
                    downloadManager.resume(job);
                }
            }
        }
        reader.close();
        System.out.println("Shutting down");
        if (!downloadManager.isShutdown()) {
            downloadManager.shutdownNow();
        }
        if (job.getDownloadState().hasFailed()) {
            System.exit(1);
        }
    }
}
