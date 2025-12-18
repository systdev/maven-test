package org.example.moduleB;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * FileProcessor类的单元测试
 */
public class FileProcessorTest {

    private FileProcessor processor = new FileProcessor();

    /**
     * 测试获取文件扩展名
     */
    @Test
    public void testGetFileExtension() {
        assertEquals("txt", processor.getFileExtension("test.txt"));
        assertEquals("java", processor.getFileExtension("Test.java"));
        assertEquals("", processor.getFileExtension("test"));
        assertEquals("", processor.getFileExtension("test."));
        assertEquals("", processor.getFileExtension(null));
    }

    /**
     * 测试是否为文本文件
     */
    @Test
    public void testIsTextFile() {
        assertEquals(true, processor.isTextFile("test.txt"));
        assertEquals(true, processor.isTextFile("data.csv"));
        assertEquals(true, processor.isTextFile("log.md"));
        assertEquals(false, processor.isTextFile("image.jpg"));
        assertEquals(false, processor.isTextFile(null));
    }

    /**
     * 测试文件名清理
     */
    @Test
    public void testSanitizeFilename() {
        assertEquals("test_file.txt", processor.sanitizeFilename("test file.txt"));
        assertEquals("test_file_.txt", processor.sanitizeFilename("test@file#.txt"));
        assertEquals("test_file.txt", processor.sanitizeFilename("test<>file.txt"));
        assertEquals("", processor.sanitizeFilename(null));
    }

    /**
     * 测试文件名有效性检查
     */
    @Test
    public void testIsValidFilename() {
        assertEquals(true, processor.isValidFilename("test.txt"));
        assertEquals(false, processor.isValidFilename("test<file.txt"));
        assertEquals(false, processor.isValidFilename("test>file.txt"));
        assertEquals(false, processor.isValidFilename("test:file.txt"));
        assertEquals(false, processor.isValidFilename("test\"file.txt"));
        assertEquals(false, processor.isValidFilename("test/file.txt"));
        assertEquals(false, processor.isValidFilename(null));
        assertEquals(false, processor.isValidFilename(""));
    }

    /**
     * 测试统计行数
     */
    @Test
    public void testCountLines() {
        assertEquals(0, processor.countLines(null));
        assertEquals(0, processor.countLines(""));
        assertEquals(1, processor.countLines("single line"));
        assertEquals(3, processor.countLines("line1\nline2\nline3"));
        assertEquals(2, processor.countLines("line1\nline2\n"));
    }
}
