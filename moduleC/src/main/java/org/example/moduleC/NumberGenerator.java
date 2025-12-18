package org.example.moduleC;

public class NumberGenerator {
    public int[] generateRandomNumbers(int count, int min, int max) {
        if (count <= 0 || min > max) {
            return new int[0];
        }
        int[] numbers = new int[count];
        int range = max - min + 1;
        for (int i = 0; i < count; i++) {
            numbers[i] = min + (int)(Math.random() * range);
        }
        return numbers;
    }

    public int generateRandomNumber(int min, int max) {
        if (min > max) {
            return min;
        }
        return min + (int)(Math.random() * (max - min + 1));
    }

    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    public boolean isOdd(int number) {
        return number % 2 != 0;
    }

    public int[] filterEvenNumbers(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return new int[0];
        }
        int count = 0;
        for (int num : numbers) {
            if (isEven(num)) {
                count++;
            }
        }
        int[] evenNumbers = new int[count];
        int index = 0;
        for (int num : numbers) {
            if (isEven(num)) {
                evenNumbers[index++] = num;
            }
        }
        return evenNumbers;
    }
}
