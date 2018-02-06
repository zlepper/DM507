package lektion1;

import java.util.ArrayList;
import java.util.Collections;

public class task4 {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = generateRandomArray(10);
        System.out.println(numbers);
    }

    public static ArrayList<Integer> generateRandomArray(int size) {
        ArrayList<Integer> numbers = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers;
    }
}
