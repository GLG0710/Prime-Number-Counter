import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultiThread {

    public long countPrimes(int number) {
        if (number < 2) {
            return 0;
        }

        int limit = (int) Math.sqrt(number);
        boolean[] composite = new boolean[limit + 1];

        for (int i = 2; i <= limit / i; i++) {
            if (!composite[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    composite[j] = true;
                }
            }
        }

        List<Integer> basePrimes = new ArrayList<>();

        for (int i = 2; i <= limit; i++) {
            if (!composite[i]) {
                basePrimes.add(i);
            }
        }

        int total = number - 1;
        int threads = Math.min(Runtime.getRuntime().availableProcessors(), total);
        int tasks = threads * 4;

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        List<Future<Long>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < tasks; i++) {
                int start = (int) (2L + (long) i * total / tasks);
                int end = (int) (2L + (long) (i + 1) * total / tasks - 1);

                Callable<Long> task = () -> {
                    boolean[] segment = new boolean[end - start + 1];

                    for (int p : basePrimes) {
                        long first = Math.max(
                            (long) p * p,
                            ((start + (long) p - 1) / p) * p
                        );

                        for (long j = first; j <= end; j += p) {
                            segment[(int) (j - start)] = true;
                        }
                    }

                    long count = 0;

                    for (boolean isComposite : segment) {
                        if (!isComposite) {
                            count++;
                        }
                    }

                    return count;
                };

                futures.add(executor.submit(task));
            }

            long totalPrimes = 0;

            for (Future<Long> future : futures) {
                totalPrimes += future.get();
            }

            return totalPrimes;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);

        } catch (ExecutionException e) {
            throw new IllegalStateException(e.getCause());

        } finally {
            executor.shutdown();
        }
    }
}