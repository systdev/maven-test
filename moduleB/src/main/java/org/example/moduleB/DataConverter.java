package org.example.moduleB;

public class DataConverter {
    public int stringToInt(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double stringToDouble(String str) {
        if (str == null || str.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(str.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public boolean stringToBoolean(String str) {
        if (str == null) {
            return false;
        }
        return str.equalsIgnoreCase("true") ||
               str.equalsIgnoreCase("yes") ||
               str.equals("1");
    }

    public String intToBinary(int number) {
        if (number == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        int n = Math.abs(number);
        while (n > 0) {
            sb.append(n % 2);
            n = n / 2;
        }
        return sb.reverse().toString();
    }

    public String intToHex(int number) {
        if (number == 0) {
            return "0";
        }
        return Integer.toHexString(number).toUpperCase();
    }
}
