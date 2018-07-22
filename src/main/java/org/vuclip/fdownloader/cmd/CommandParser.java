package org.vuclip.fdownloader.cmd;

import org.apache.commons.io.FilenameUtils;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;

public class CommandParser {
    private static final String DEFAULT_LOCATION = ".";

    private CmdLineParser parser;

    @Option(name = "-o", aliases = {"--output-file"}, usage = "output file location")
    private String outputFilePath;

    // receives other command line parameters than options
    @Argument
    private String downloadFilPath;
    private boolean errorFree;

    public CommandParser(String... args) throws Exception {
        parser = new CmdLineParser(this);
        parser.getProperties().withUsageWidth(120);
        try {
            parser.parseArgument(args);
            errorFree = true;
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            throw new Exception("Failed to parse the command !!! Try again.");
        }
    }

    /**
     * Returns whether the parameters could be parsed without an error.
     *
     * @return true if no error occurred.
     */
    public boolean isErrorFree() {
        return errorFree;
    }

    /**
     * Returns the output file path.
     *
     * @return The output file path.
     */
    private String getOutputFilePath() {
        return outputFilePath;
    }

    public Command getCommand() throws Exception {
        if (!errorFree) {
            parser.printUsage(System.err);
        }
        if (downloadFilPath == null) {
            throw new Exception("URL Missing from arguments");
        }
        String outputFile = outputFilePath != null ? outputFilePath : genrateOutputFilePath();
        return new Command.Builder(downloadFilPath).withOutputFile(outputFile).build();
    }

    private String genrateOutputFilePath() {
        return DEFAULT_LOCATION + File.separator + FilenameUtils.getName(downloadFilPath);
    }

}
