package uge8;

import utilities.Benchmark;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {

    public static void sort(Integer[] A) {
        mergeSort(A, 0, A.length);
    }

    private static void mergeSort(Integer[] A, int start, int end) {
        if (start < end - 1) {
            int mid = (start + end) / 2;
            mergeSort(A, start, mid);
            mergeSort(A, mid, end);
            merge(A, start, mid, end);
        }
    }

    private static void merge(Integer[] A, int start, int mid, int end) {
        Integer[] left = Arrays.copyOfRange(A, start, mid);
        Integer[] right = Arrays.copyOfRange(A, mid, end);

        for (int k = start, i = 0, j = 0; k < end; k++) {
            if (right.length == j) {
                A[k] = left[i];
                i++;
            } else if (left.length == i) {
                A[k] = right[j];
                j++;
            } else if (left[i] <= right[j]) {
                A[k] = left[i];
                i++;
            } else {
                A[k] = right[j];
                j++;
            }
        }
    }

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        long seed = System.currentTimeMillis();
        Benchmark<Integer> b = new Benchmark<>("Merge sort", count -> {
            Random random = new Random(seed);
            Integer[] data = new Integer[count];
            for (int i = 0; i < count; i++) {
                data[i] = random.nextInt(count);
            }
            return data;
        }, data -> {
            sort(data);
            return null;
        }, (time, count) -> time / count * log2(count));

        b.timeResults(new int[]{1_000, 5_000, 10_000, 50_000, 100_000, 500_000, 1_000_000}, 3);
    }

    private static double log2(double v) {
        return Math.log(v) / Math.log(2);
    }

}
