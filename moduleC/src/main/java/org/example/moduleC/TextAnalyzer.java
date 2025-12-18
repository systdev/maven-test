package org.example.moduleC;

public class TextAnalyzer {
    public int countWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        String[] words = text.trim().split("\\s+");
        return words.length;
    }

    public int countCharacters(String text) {
        if (text == null) {
            return 0;
        }
        return text.length();
    }

    public int countVowels(String text) {
        if (text == null) {
            return 0;
        }
        String lowerText = text.toLowerCase();
        int count = 0;
        for (char c : lowerText.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count++;
            }
        }
        return count;
    }

    public String reverseText(String text) {
        if (text == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(text);
        return sb.reverse().toString();
    }

    public String removeSpaces(String text) {
        if (text == null) {
            return null;
        }
        return text.replaceAll("\\s+", "");
    }
}
