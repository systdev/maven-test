package org.example.moduleD;

/**
 * Singleton class for PowerMock testing
 */
public class SingletonClass {

    private static SingletonClass instance;

    private int counter = 0;
    private String message = "Default";

    private SingletonClass() {
        // Private constructor
    }

    public static SingletonClass getInstance() {
        if (instance == null) {
            instance = new SingletonClass();
        }
        return instance;
    }

    public void increment() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void reset() {
        counter = 0;
        message = "Default";
    }
}
