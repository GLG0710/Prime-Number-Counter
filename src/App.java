public class App {
    public static void main(String[] args) throws Exception {
        singleThreadMethod(2_100_400_600);
        IO.println("");
        multiThreadMethod(2_100_400_600);
        
    }

    private static void singleThreadMethod(int number) {
        Runtime runtime = Runtime.getRuntime();
        SingleThread singleThread = new SingleThread();

        System.gc();

        long memoryStart = runtime.totalMemory() - runtime.freeMemory();

        long timeStart = System.nanoTime();
        long count = singleThread.countPrimes(number);
        long timeFinal = System.nanoTime();

        long memoryFinal = runtime.totalMemory() - runtime.freeMemory();

        System.out.println(count + " prime numbers");
        System.out.println(
            "Single thread processing time: "
            + (timeFinal - timeStart) + " ns"
        );
        System.out.println(
            "Single thread processing memory: "
            + (memoryFinal - memoryStart) + " bytes"
        );
    }

    private static void multiThreadMethod(int number) {
        Runtime runtime = Runtime.getRuntime();
        MultiThread multiThread = new MultiThread();

        System.gc();

        long memoryStart = runtime.totalMemory() - runtime.freeMemory();

        long timeStart = System.nanoTime();
        long count = multiThread.countPrimes(number);
        long timeFinal = System.nanoTime();

        long memoryFinal = runtime.totalMemory() - runtime.freeMemory();

        System.out.println(count + " prime numbers");
        System.out.println(
            "Multi thread processing time: "
            + (timeFinal - timeStart) + " ns"
        );
        System.out.println(
            "Multi thread processing memory: "
            + (memoryFinal - memoryStart) + " bytes"
        );
    }
}
