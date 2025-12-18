package org.example.moduleB;

import org.junit.Test;
import static org.junit.Assert.*;

public class FileProcessorTest {

    private FileProcessor fileProcessor = new FileProcessor();

    @Test
    public void testGetFileExtension() {
        assertEquals("txt", fileProcessor.getFileExtension("file.txt"));
        assertEquals("java", fileProcessor.getFileExtension("Test.java"));
        assertEquals("pdf", fileProcessor.getFileExtension("document.pdf"));
        assertEquals("", fileProcessor.getFileExtension("file"));
        assertEquals("", fileProcessor.getFileExtension(null));
        assertEquals("", fileProcessor.getFileExtension("file."));
    }

    @Test
    public void testIsTextFile() {
        assertTrue(fileProcessor.isTextFile("file.txt"));
        assertTrue(fileProcessor.isTextFile("data.csv"));
        assertTrue(fileProcessor.isTextFile("log.log"));
        assertTrue(fileProcessor.isTextFile("readme.md"));
        assertFalse(fileProcessor.isTextFile("image.jpg"));
        assertFalse(fileProcessor.isTextFile("video.mp4"));
    }

    @Test
    public void testSanitizeFilename() {
        assertEquals("file_name.txt", fileProcessor.sanitizeFilename("file@name.txt"));
        assertEquals("test_file.txt", fileProcessor.sanitizeFilename("test<>file.txt"));
        assertEquals("file.txt", fileProcessor.sanitizeFilename("file.txt"));
        assertEquals("", fileProcessor.sanitizeFilename(null));
    }

    @Test
    public void testIsValidFilename() {
        assertTrue(fileProcessor.isValidFilename("file.txt"));
        assertTrue(fileProcessor.isValidFilename("test_file_123.txt"));
        assertFalse(fileProcessor.isValidFilename("file<>.txt"));
        assertFalse(fileProcessor.isValidFilename("file:.txt"));
        assertFalse(fileProcessor.isValidFilename("file/.txt"));
        assertFalse(fileProcessor.isValidFilename(""));
        assertFalse(fileProcessor.isValidFilename(null));
    }

    @Test
    public void testCountLines() {
        assertEquals(0, fileProcessor.countLines(null));
        assertEquals(0, fileProcessor.countLines(""));
        assertEquals(1, fileProcessor.countLines("single line"));
        assertEquals(2, fileProcessor.countLines("line1\nline2"));
        assertEquals(3, fileProcessor.countLines("line1\nline2\nline3"));
    }
}
