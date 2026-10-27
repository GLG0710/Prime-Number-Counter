public class SingleThread {

    public long countPrimes(int number) {
        if (number < 2) {
            return 0;
        }

        boolean[] composite = new boolean[number + 1];

        for (int i = 2; i <= number / i; i++) {
            if (!composite[i]) {
                for (int j = i * i; j <= number; j += i) {
                    composite[j] = true;
                }
            }
        }

        long count = 0;

        for (int i = 2; i <= number; i++) {
            if (!composite[i]) {
                count++;
            }
        }

        return count;
    }
}