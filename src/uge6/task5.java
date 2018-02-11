package uge6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class task5 {
    public static void main(String[] args) {
        final long attempts = 100_000_000L;

        Map<Integer, Integer> occurrences = new HashMap<>();
        // Do 100 million attempts
        for(long i = 0; i < attempts; i++) {
            ArrayList<Integer> numbers = task4.generateRandomArray(12);
            int count = countCycles(numbers);
            Integer solutionCount = occurrences.getOrDefault(count, 0);
            solutionCount++;
            occurrences.put(count, solutionCount);
        }
        System.out.println(occurrences);

        long totalCycles = 0;
        // Average
        for (Map.Entry<Integer, Integer> entry : occurrences.entrySet()) {
            totalCycles += (long)entry.getKey() * (long)entry.getValue();
        }

        System.out.println("Average cycles: " + ((double)totalCycles / (double)attempts));
    }

    public static int countCycles(ArrayList<Integer> numbers) {
        // Keep track of which indices we already checked
        boolean[] checked = new boolean[numbers.size()];

        int count = 0;
        for(int i = 0; i < numbers.size(); i++) {
            if(!checked[i]) {
                findCycleFrom(checked, numbers, i);
                count++;
            }
        }
        return count;
    }

    public static void findCycleFrom(boolean[] checked, ArrayList<Integer> numbers, final int startIndex) {
        int index = startIndex;
        index = numbers.get(index);
        while(index != startIndex) {
            checked[index] = true;
            index = numbers.get(index);
        }
    }
}
