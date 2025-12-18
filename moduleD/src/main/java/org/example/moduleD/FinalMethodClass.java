package org.example.moduleD;

/**
 * Class with final methods for PowerMock testing
 */
public class FinalMethodClass {

    public final String getName() {
        return "Final Name";
    }

    public final int calculate(int x) {
        return x * x;
    }

    public final String formatText(String text) {
        if (text == null) {
            return "NULL";
        }
        return "[ " + text + " ]";
    }

    public final double multiply(double a, double b) {
        return a * b;
    }
}
