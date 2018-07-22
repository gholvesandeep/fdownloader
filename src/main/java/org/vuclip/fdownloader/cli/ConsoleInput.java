package org.vuclip.fdownloader.cli;

import org.vuclip.fdownloader.DownloadJob;
import org.vuclip.fdownloader.DownloadManager;
import org.vuclip.utils.Utils;

import java.util.Scanner;

public class ConsoleInput {
    private final String RESUME_MESSAGE = "Enter '" + RESUME + "' to resume";
    static final char PAUSE = 'p';
    private static final char RESUME = 'r';

    public void listen(DownloadJob job, DownloadManager downloadManager, Runnable runnable) {
        Scanner scanner = new Scanner(System.in);

        while (!(job.getDownloadState().isComplete()) || job.getDownloadState().hasFailed()) {
            if (scanner.hasNext()) {
                char action = scanner.next().charAt(0);
                if (action == PAUSE && !downloadManager.isPaused(job)) {
                    downloadManager.pause(job);
                    Utils.printToConsole(RESUME_MESSAGE);
                } else if (action == RESUME && downloadManager.isPaused(job)) {
                    downloadManager.resume(job);
                }
            }
        }
        scanner.close();
        runnable.run();
        if (job.getDownloadState().hasFailed()) {
            System.exit(1);
        }
    }
}
