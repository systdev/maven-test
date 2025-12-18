package org.example.moduleD;

/**
 * Class with constructor logic for PowerMock testing
 */
public class ConstructorClass {

    private String name;
    private int age;
    private boolean initialized = false;

    public ConstructorClass(String name) {
        this.name = name;
        this.age = 0;
        initialized = true;
    }

    public ConstructorClass(String name, int age) {
        this.name = name;
        this.age = age;
        initialized = true;
    }

    public ConstructorClass() {
        this.name = "Unknown";
        this.age = 0;
        initialized = true;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public String getDescription() {
        return "Name: " + name + ", Age: " + age;
    }
}
