package org.example.moduleA;

public class ArrayHelper {
    public int sum(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        return sum;
    }

    public int findMax(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        int max = numbers[0];
        for (int num : numbers) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    public int findMin(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        int min = numbers[0];
        for (int num : numbers) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

    public double average(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }
        return (double) sum(numbers) / numbers.length;
    }
}
