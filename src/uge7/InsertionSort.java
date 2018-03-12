package uge7;

import utilities.Benchmark;

import java.util.Random;

public class InsertionSort {

    public static void sort(Integer[] A) {
        for (int j = 1; j < A.length; j++) {
            int key = A[j];
            int i = j - 1;
            while (i >= 0 && A[i] > key) {
                A[i + 1] = A[i];
                i--;
            }
            A[i + 1] = key;
        }
    }

    public static void main(String[] args) {
        runBestCase();
        runWorstCase();
        runRandomCase();
    }

    private static void runWorstCase() {
        Benchmark<Integer> reversed = new Benchmark<>("Reversed array", (count) -> {
            Integer[] values = new Integer[count];
            for (int i = 0; i < count; i++) {
                values[i] = count - i;
            }
            return values;
        }, data -> {
            sort(data);
            return null;
        }, (time, count) -> time / Math.pow(count, 2));

        reversed.timeResults(new int[]{5_000, 10_000, 20_000, 50_000, 100_000, 150_000}, 3);
    }

    private static void runBestCase() {
        Benchmark<Integer> sorted = new Benchmark<>("Sorted array", (count) -> {
            Integer[] values = new Integer[count];
            for (int i = 0; i < count; i++) {
                values[i] = i;
            }
            return values;
        }, data -> {
            sort(data);
            return null;
        }, (time, count) -> time / Math.pow(count, 2));

        sorted.timeResults(new int[]{5_000, 200_000, 500_000, 1_000_000, 2_000_000, 5_000_000, 10_000_000}, 3);
    }

    private static void runRandomCase() {
        final long seed = System.currentTimeMillis();
        Benchmark<Integer> random = new Benchmark<>("Random array", (count) -> {
            Random r = new Random(seed);
            Integer[] values = new Integer[count];
            for (int i = 0; i < count; i++) {
                values[i] = r.nextInt(count);
            }
            return values;
        }, data -> {
            sort(data);
            return null;
        }, (time, count) -> time / Math.pow(count, 2));

        random.timeResults(new int[]{5_000, 10_000, 20_000, 50_000, 100_000, 150_000}, 3);
    }

}
