package org.vuclip.client;

import org.vuclip.fdownloader.DownloadJob;
import org.vuclip.fdownloader.DownloadManager;
import org.vuclip.fdownloader.cli.ConsoleDisplay;
import org.vuclip.fdownloader.cli.ConsoleInput;
import org.vuclip.fdownloader.cmd.Command;
import org.vuclip.fdownloader.cmd.CommandParser;
import org.vuclip.utils.Utils;

public class App {
    public static void main(String[] args) throws Exception {
        // Command Parser
        CommandParser cmdParser = new CommandParser(args);
        // Actual command (source URL and destination path)
        Command command = cmdParser.getCommand();
        ConsoleInput input = new ConsoleInput();
        Utils.printToConsole(String.format("Download operation, [source=%s, target=%s]",
                command.getUrl(), command.getOutputFile()));

        DownloadJob downloadJob = new DownloadJob(command.getUrl(), command.getOutputFile());
        ConsoleDisplay display = new ConsoleDisplay(downloadJob);

        DownloadManager downloadManager = DownloadManager.getInstance();
        downloadManager.download(downloadJob);

        input.listen(downloadJob, downloadManager);
    }
}
