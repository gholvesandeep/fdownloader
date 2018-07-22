package org.vuclip.fdownloader.cmd;

public class Command {
    private String url;
    private String outputFile;

    private Command(Builder builder) {
        this.url = builder.url;
        this.outputFile = builder.outputFile;
    }

    public String getUrl() {
        return url;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public static class Builder {
        private String url;
        private String outputFile;

        public Builder(final String url) {
            this.url = url;
        }

        public Builder withOutputFile(final String filePath) {
            this.outputFile = filePath;
            return this;
        }

        public Command build() {
            return new Command(this);
        }
    }
}
