package utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Handles running and calculating a benchmark
 * A benchmark in this case means, figuring out how many units a given algoritm
 * can handle in a given time period
 *
 * @param <T>
 */
public class Benchmark<T> {
    public static final long MS_TO_NANO = 1_000_000;

    /**
     * The name of the benchmark
     */
    public final String name;
    /**
     * Executes to create data.
     * Kept separate to avoid influence on benchmark
     */
    private final Function<Integer, T[]> prepare;
    /**
     * The actual algorithm to execute.
     */
    private final Function<T[], Void> executeable;

    public Benchmark(String name, Function<Integer, T[]> prepare, Function<T[], Void> executeable) {
        this.name = name;
        this.prepare = prepare;
        this.executeable = executeable;
    }

    /**
     * Executes enough benchmark to spend the given time on the algorithm
     * Will try to aproximate with 0.1% of the given target time.
     * The actual execution time will often be much more, as time trial
     * and error is involved in figuring out the exact time.
     * @param targetTime - How much time should be spend on a single run of the algorithm.
     *                   Should be provided in nano seconds. Should be at least 100 ms
     */
    public void run(long targetTime) {
        if(targetTime < 100 * MS_TO_NANO) {
            throw new IllegalArgumentException("targetTime was too little");
        }
        int count = findCount(targetTime);
        long time = getAverageTime(count, 3);
        System.out.printf("Benchmark '%s' ran for %dms (target: %dms and required %d values. \n", name, time / MS_TO_NANO, targetTime / MS_TO_NANO, count);
    }

    /**
     * Get the time it takes for the given runs
     * @param values
     * @param numberToAverage
     * @return
     */
    public void timeResults(int[] values, int numberToAverage) {
        long[] times = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            int valueCount = values[i];
            times[i] = getAverageTime(valueCount, numberToAverage);
        }

        System.out.printf("Benchmark '%s' took:\n", name);
        for (int i = 0; i < times.length; i++) {
            long time = times[i];
            int valueCount = values[i];
            System.out.printf("%10d : %11d\n", valueCount, time);
        }

    }

    /**
     * Gets the average time it takes to do the calculations
     * @param valueCount
     * @param numberToAverage
     * @return
     */
    public long getAverageTime(int valueCount, int numberToAverage) {
        long[] times = new long[numberToAverage];
        for(int i = 0; i < times.length; i++) {
            times[i] = doAndTime(valueCount);
        }

        long sum = 0;
        for (long time : times) {
            sum += time;
        }
        return sum/numberToAverage;
    }

    /**
     * Find the number of values needed to use the target time
     * @param targetTime - The target time to hit in ns
     * @return A tuple of the number of values and the time taken
     */
    private Integer findCount(long targetTime) {
        final long maxDistance = targetTime / 1000;
        final long minorDistance = targetTime / 100;
        int count = 1;
        long time = doAndTime(count);
        // Do major aiming
        while(Math.abs(targetTime - time) > minorDistance) {
//            System.out.format("Doing major corrections. Benchmark took '%d'ms, target: '%d'ms, count: '%d'\n", time / MS_TO_NANO, targetTime / MS_TO_NANO, count);
            if(time < targetTime) {
                // We were too fast. Increase count
                count *= targetTime/time > 2 ? 2 : 1.1;
            } else {
                // We were too slow. Decrease count
                count *= 0.99;
            }
            time = doAndTime(count);
        }
        // Do minor aiming
        while(Math.abs(targetTime - time) > maxDistance) {
//            System.out.format("Doing minor corrections. Benchmark took '%d'ns, target: '%d'ns, count: '%d'\n", time, targetTime, count);
            if(time < targetTime) {
                // We were too fast. Increase count
                count *= 1.0001;
            } else {
                // We were too slow. Decrease count
                count *= 0.99999;
            }
            time = doAndTime(count);
        }

        return count;
    }

    public long doAndTime(int count) {
        T[] data = prepare.apply(count);
        return time(data);
    }

    /**
     * Times the executeable execution.
     * Also ensures GC doesn't cause statistical significance.
     * @param data - The data to execute on
     * @return the time taken to execute the run in nano seconds
     */
    private long time(T[] data) {
        // Run the gc so we avoid accidental triggers
        System.gc();
        long before = System.nanoTime();
        executeable.apply(data);
        // Run gc again to ensure we clean any created objects
        // And that the time spend on that is included in the benchmark
        long after = System.nanoTime();
        return after - before;
    }

}
