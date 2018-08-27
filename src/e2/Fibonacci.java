package e2;

import java.util.Arrays;
import java.util.concurrent.*;

public class Fibonacci
{
    public static void main(String[] args) throws Exception
    {
        long start = System.nanoTime();

        BlockingQueue<Long> inputs = new ArrayBlockingQueue<Long>(20);
        inputs.addAll(Arrays.<Long>asList(4l, 5l, 8l, 12l, 21l, 22l, 34l, 35l, 36l, 37l, 42l, 50l));
        fibonacci(inputs, 4);

        long end = System.nanoTime();

        System.out.println("Took " + TimeUnit.NANOSECONDS.toSeconds(end - start) + " seconds");
    }

    private static void fibonacci(BlockingQueue<Long> inputs, int producers) throws Exception
    {
        Counter counter = new Counter();
        counter.setProduced(inputs.size());
        BlockingQueue<Long> tunnel = new ArrayBlockingQueue<Long>(inputs.size());

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int x = 1; x <= producers; x++)
            executorService.execute(new FibonacciProducer(inputs, tunnel, counter));

        FibonacciConsumer consumer = new FibonacciConsumer(tunnel, counter);
        executorService.execute(consumer);

        try {
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.MINUTES);
            System.out.println("Sum: " + consumer.getSum());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

