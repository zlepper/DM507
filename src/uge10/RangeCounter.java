package uge10;

import java.util.function.BiFunction;

public class RangeCounter {
    /**
     * @param A - The input array
     * @return a function that can be called to get the
     */
    static BiFunction<Integer, Integer, Integer> preprocess(int[] A) {
        int[] C = new int[getMaxValue(A) + 1];
        for (int j = 0; j < A.length; j++) {
            C[A[j]]++;
        }

        for (int j = 1; j < C.length; j++) {
            C[j] = C[j] + C[j - 1];
        }

        return (a, b) -> {
            if (a == 0) {
                return C[b];
            }
            return C[b] - C[a - 1];
        };
    }

    public static void main(String[] args) {
        int[] input = new int[]{1, 1, 2, 2, 3, 3, 5, 6, 7, 1, 1, 2};
        BiFunction<Integer, Integer, Integer> f = preprocess(input);

        System.out.printf("[0..2] = %d\n", f.apply(0, 2));
        System.out.printf("[0..1] = %d\n", f.apply(0, 1));
    }


    private static int getMaxValue(int[] input) {
        int max = Integer.MIN_VALUE;
        for (int i : input) {
            max = Math.max(i, max);
        }
        return max;
    }
}
