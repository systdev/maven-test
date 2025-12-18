package org.example.moduleA;

public class StringUtils {
    public String toUpperCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase();
    }

    public String toLowerCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }

    public int stringLength(String str) {
        if (str == null) {
            return 0;
        }
        return str.length();
    }

    public String concatenate(String str1, String str2) {
        if (str1 == null) {
            str1 = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        return str1 + str2;
    }

    public boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
