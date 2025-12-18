package org.example.moduleB;

public class FileProcessor {
    public String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int lastDot = filename.lastIndexOf('.');
        if (lastDot == -1 || lastDot == filename.length() - 1) {
            return "";
        }
        return filename.substring(lastDot + 1);
    }

    public boolean isTextFile(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        return extension.equals("txt") || extension.equals("csv") ||
               extension.equals("log") || extension.equals("md");
    }

    public String sanitizeFilename(String filename) {
        if (filename == null) {
            return "";
        }
        return filename.replaceAll("[^a-zA-Z0-9._-]+", "_");
    }

    public boolean isValidFilename(String filename) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }
        if (filename.length() > 255) {
            return false;
        }
        String[] invalidChars = {"<", ">", ":", "\"", "/", "\\", "|", "?", "*"};
        for (String invalid : invalidChars) {
            if (filename.contains(invalid)) {
                return false;
            }
        }
        return true;
    }

    public int countLines(String content) {
        if (content == null) {
            return 0;
        }
        if (content.isEmpty()) {
            return 0;
        }
        String[] lines = content.split("\n");
        return lines.length;
    }
}
