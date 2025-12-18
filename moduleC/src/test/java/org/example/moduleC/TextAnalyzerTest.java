package org.example.moduleC;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TextAnalyzer类的单元测试
 */
class TextAnalyzerTest {

    private TextAnalyzer analyzer = new TextAnalyzer();

    /**
     * 测试统计单词数
     */
    @Test
    void testCountWords() {
        assertEquals(0, analyzer.countWords(null));
        assertEquals(0, analyzer.countWords(""));
        assertEquals(0, analyzer.countWords("   "));
        assertEquals(1, analyzer.countWords("hello"));
        assertEquals(3, analyzer.countWords("hello world test"));
        assertEquals(3, analyzer.countWords("  hello   world  test  "));
    }

    /**
     * 测试统计字符数
     */
    @Test
    void testCountCharacters() {
        assertEquals(0, analyzer.countCharacters(null));
        assertEquals(5, analyzer.countCharacters("hello"));
        assertEquals(2, analyzer.countCharacters("你好"));
        assertEquals(0, analyzer.countCharacters(""));
    }

    /**
     * 测试统计元音字母数
     */
    @Test
    void testCountVowels() {
        assertEquals(0, analyzer.countVowels(null));
        assertEquals(0, analyzer.countVowels(""));
        assertEquals(2, analyzer.countVowels("hello"));
        assertEquals(5, analyzer.countVowels("AEIOU"));
        assertEquals(5, analyzer.countVowels("aeiou"));
        assertEquals(3, analyzer.countVowels("Hello World"));
    }

    /**
     * 测试文本反转
     */
    @Test
    void testReverseText() {
        assertNull(analyzer.reverseText(null));
        assertEquals("", analyzer.reverseText(""));
        assertEquals("olleh", analyzer.reverseText("hello"));
        assertEquals("dlroW olleH", analyzer.reverseText("Hello World"));
    }

    /**
     * 测试移除空格
     */
    @Test
    void testRemoveSpaces() {
        assertNull(analyzer.removeSpaces(null));
        assertEquals("", analyzer.removeSpaces(""));
        assertEquals("helloworld", analyzer.removeSpaces("hello world"));
        assertEquals("test", analyzer.removeSpaces("  test  "));
        assertEquals("helloworld", analyzer.removeSpaces("hello   world"));
    }
}
