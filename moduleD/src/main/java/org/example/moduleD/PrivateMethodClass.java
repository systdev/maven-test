package org.example.moduleD;

/**
 * Class with private methods for PowerMock testing
 */
public class PrivateMethodClass {

    private String name;
    private int value;

    public PrivateMethodClass(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String processData(String input) {
        if (input == null || input.isEmpty()) {
            return "invalid";
        }

        String transformed = transform(input);
        String validated = validate(transformed);
        return validated;
    }

    private String transform(String input) {
        return input.toUpperCase() + "_TRANSFORMED";
    }

    private String validate(String data) {
        if (data.length() > 10) {
            return data.substring(0, 10);
        }
        return data;
    }

    private int calculate(int a, int b) {
        return (a * b) + (a + b);
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
