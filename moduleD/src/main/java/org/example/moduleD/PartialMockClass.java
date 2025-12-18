package org.example.moduleD;

/**
 * Class for demonstrating partial mocking with PowerMock
 */
public class PartialMockClass {

    public String publicMethod1() {
        return "Public Method 1";
    }

    public String publicMethod2() {
        return "Public Method 2";
    }

    public String publicMethod3() {
        return "Public Method 3: " + privateMethod();
    }

    private String privateMethod() {
        return "Private Method";
    }

    public int calculate(int a, int b) {
        return multiply(a, b) + add(a, b);
    }

    private int multiply(int a, int b) {
        return a * b;
    }

    private int add(int a, int b) {
        return a + b;
    }
}
