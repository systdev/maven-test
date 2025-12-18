package org.example.moduleB;

public class MathUtils {
    public int power(int base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Negative exponent not supported");
        }
        if (exponent == 0) {
            return 1;
        }
        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    public int factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative number not supported");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public double sqrt(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of negative number");
        }
        if (number == 0 || number == 1) {
            return number;
        }
        double guess = number / 2;
        for (int i = 0; i < 10; i++) {
            guess = (guess + number / guess) / 2;
        }
        return guess;
    }
}
