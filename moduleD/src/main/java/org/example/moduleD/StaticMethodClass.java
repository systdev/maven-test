package org.example.moduleD;

/**
 * Class with static methods for PowerMock testing
 */
public class StaticMethodClass {

    public static String getMessage() {
        return "Hello from static method";
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static String formatName(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            return null;
        }
        return firstName.toUpperCase() + " " + lastName.toUpperCase();
    }

    public static int generateRandom() {
        return (int) (Math.random() * 100);
    }
}
