package org.example.moduleC;

import org.junit.Test;
import static org.junit.Assert.*;

public class TextAnalyzerTest {

    private TextAnalyzer textAnalyzer = new TextAnalyzer();

    @Test
    public void testCountWords() {
        assertEquals(3, textAnalyzer.countWords("hello world test"));
        assertEquals(1, textAnalyzer.countWords("single"));
        assertEquals(0, textAnalyzer.countWords(""));
        assertEquals(0, textAnalyzer.countWords("   "));
        assertEquals(0, textAnalyzer.countWords(null));
    }

    @Test
    public void testCountCharacters() {
        assertEquals(5, textAnalyzer.countCharacters("hello"));
        assertEquals(0, textAnalyzer.countCharacters(""));
        assertEquals(0, textAnalyzer.countCharacters(null));
        assertEquals(11, textAnalyzer.countCharacters("hello world"));
    }

    @Test
    public void testCountVowels() {
        assertEquals(2, textAnalyzer.countVowels("hello"));
        assertEquals(5, textAnalyzer.countVowels("education"));
        assertEquals(0, textAnalyzer.countVowels("rhythm"));
        assertEquals(5, textAnalyzer.countVowels("AEIOU"));
        assertEquals(0, textAnalyzer.countVowels(null));
    }

    @Test
    public void testReverseText() {
        assertEquals("olleh", textAnalyzer.reverseText("hello"));
        assertEquals("world", textAnalyzer.reverseText("dlrow"));
        assertEquals("a", textAnalyzer.reverseText("a"));
        assertEquals("", textAnalyzer.reverseText(""));
        assertNull(textAnalyzer.reverseText(null));
    }

    @Test
    public void testRemoveSpaces() {
        assertEquals("helloworld", textAnalyzer.removeSpaces("hello world"));
        assertEquals("test", textAnalyzer.removeSpaces("  test  "));
        assertEquals("onetwothree", textAnalyzer.removeSpaces("one two three"));
        assertEquals("", textAnalyzer.removeSpaces(""));
        assertNull(textAnalyzer.removeSpaces(null));
    }
}
