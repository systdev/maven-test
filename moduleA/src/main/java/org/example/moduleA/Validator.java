package org.example.moduleA;

public class Validator {
    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");
        return atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < email.length() - 1;
    }

    public boolean isValidAge(int age) {
        return age >= 0 && age <= 150;
    }

    public boolean isValidPhoneNumber(String phone) {
        if (phone == null) {
            return false;
        }
        return phone.length() == 10 && phone.matches("\\d+");
    }

    public boolean isNotNullOrEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public boolean isPositive(int number) {
        return number > 0;
    }
}
