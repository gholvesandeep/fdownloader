package org.vuclip.fdownloader.cli;

import org.vuclip.fdownloader.DownloadJob;
import org.vuclip.fdownloader.DownloadState;
import org.vuclip.utils.Utils;

import java.util.Observable;
import java.util.Observer;

public class ConsoleDisplay implements Observer {
    private final String PAUSE_MESSAGE = "Enter '" + ConsoleInput.PAUSE + "' to pause ";
    private StringBuilder messageBuilder = new StringBuilder();

    public ConsoleDisplay(Observable downloadJob) {
        downloadJob.addObserver(this);
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof DownloadJob) {
            DownloadJob job = (DownloadJob)obs;
            display(job);
            if (job.getDownloadState().isComplete()) {
                Utils.printToConsole("\nFile downloaded successfully at " + job.getTargetLocation());
            }
        }
    }

    /**
     * Display the download status message
     * @param job DownloadJob
     */
    private void display(DownloadJob job) {
        DownloadState state = job.getDownloadState();
        for (int i = 0; i < messageBuilder.length() + 5; i++) {
            print("\b");
        }
        long percentage = state.getDownloadedSize() * 100 / state.getTotalSize();
        messageBuilder = new StringBuilder();
        messageBuilder.append(getBar(percentage));
        messageBuilder.append(getStatus(state));
        messageBuilder.append(PAUSE_MESSAGE);
        print(messageBuilder.toString());
    }

    /**
     * Printing message to console
     * @param s message
     */
    private void print(String s) {
        System.out.print(s);
    }

    /**
     * Get Status message including current downloaded size w.r.t. total Size in KB
     * @param state Download Job State
     * @return Console Message of job status
     */
    private String getStatus(DownloadState state) {
        return Math.round(state.getDownloadedSize() / 1024) + "/" + Math.round(state.getTotalSize() / 1024) + " KB ";
    }

    /**
     * Prepare Progress Bar based on percentage
     * @param percentage current download process completed in percentage
     * @return String representation of progress bar of download process.
     */
    private String getBar(long percentage) {
        StringBuilder sb = new StringBuilder();
        sb.append(percentage);
        sb.append("% [");
        for (int i = 0; i < 100; i++) {
            if (percentage == 100 || i < percentage - 1) {
                sb.append("=");
            } else if (i == percentage - 1) {
                sb.append(">");
            } else {
                sb.append(" ");
            }
        }
        sb.append("] ");
        return sb.toString();
    }
}
