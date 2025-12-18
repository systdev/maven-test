package org.example.moduleC;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
    public List<Integer> findDuplicates(List<Integer> numbers) {
        List<Integer> duplicates = new ArrayList<>();
        if (numbers == null || numbers.size() <= 1) {
            return duplicates;
        }
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers.get(i).equals(numbers.get(j)) && !duplicates.contains(numbers.get(i))) {
                    duplicates.add(numbers.get(i));
                }
            }
        }
        return duplicates;
    }

    public List<Integer> sortList(List<Integer> numbers) {
        if (numbers == null) {
            return new ArrayList<>();
        }
        List<Integer> sorted = new ArrayList<>(numbers);
        for (int i = 0; i < sorted.size() - 1; i++) {
            for (int j = 0; j < sorted.size() - i - 1; j++) {
                if (sorted.get(j) > sorted.get(j + 1)) {
                    int temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }
            }
        }
        return sorted;
    }

    public int sumList(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }
        int sum = 0;
        for (Integer num : numbers) {
            sum += num;
        }
        return sum;
    }

    public boolean containsDuplicate(List<Integer> numbers) {
        if (numbers == null || numbers.size() <= 1) {
            return false;
        }
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers.get(i).equals(numbers.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }
}
