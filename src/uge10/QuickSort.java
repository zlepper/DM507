package uge10;

import utilities.Benchmark;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    public static void sort(Integer[] array, int start, int end) {
        if (start < end) {
            int middle = partition(array, start, end);
            sort(array, start, middle - 1);
            sort(array, middle + 1, end);
        }
    }

    private static int partition(Integer[] array, int start, int end) {
        int pivot = array[end];
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, end);
        return i + 1;
    }

    private static void swap(Integer[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] numbers = new Integer[]{-1, 4, 3, 2, 8, 4, 5, 5, 6, 4, 2, 5, 56, 64, 4, 6, 7, 8};
        sort(numbers, 0, numbers.length - 1);
        System.out.println(Arrays.asList(numbers));

        benchmark();
        benchmarkJava();
    }

    private static void benchmark() {
        long seed = System.currentTimeMillis();
        Benchmark<Integer> b = new Benchmark<>("Quick sort", count -> {
            Random random = new Random(seed);
            Integer[] data = new Integer[count];
            for (int i = 0; i < count; i++) {
                data[i] = random.nextInt(count);
            }
            return data;
        }, data -> {
            sort(data, 0, data.length - 1);

            return null;
        }, (time, count) -> time / count * log2(count));

        b.timeResults(new int[]{1_000, 5_000, 10_000, 50_000, 100_000, 500_000, 1_000_000}, 3);
    }


    private static double log2(double v) {
        return Math.log(v) / Math.log(2);
    }


    private static void benchmarkJava() {
        long seed = System.currentTimeMillis();
        Benchmark<Integer> b = new Benchmark<>("Build in quick sort", count -> {
            Random random = new Random(seed);
            Integer[] data = new Integer[count];
            for (int i = 0; i < count; i++) {
                data[i] = random.nextInt(count);
            }
            return data;
        }, data -> {
            Arrays.sort(data);
            return null;
        }, (time, count) -> time / count * log2(count));

        b.timeResults(new int[]{1_000, 5_000, 10_000, 50_000, 100_000, 500_000, 1_000_000}, 3);
    }
}
