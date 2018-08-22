package e2;

import java.util.Arrays;
import java.util.concurrent.*;

public class Fibonacci
{
    public static void main(String[] args) throws Exception
    {
        BlockingQueue<Long> inputs = new ArrayBlockingQueue<Long>(20);
        inputs.addAll(Arrays.<Long>asList(4l, 5l, 8l, 12l, 21l, 22l, 34l, 35l, 36l, 37l, 42l));
        fibonacci(inputs, 4);
    }

    private static void fibonacci(BlockingQueue<Long> inputs, int producers) throws Exception
    {
        BlockingQueue<Long> tunnel = new ArrayBlockingQueue<Long>(inputs.size());

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int x = 1; x <= producers; x++)
            executorService.execute(new FibonacciProducer(inputs, tunnel));

        FibonacciConsumer consumer = new FibonacciConsumer(tunnel);
        executorService.execute(consumer);

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            System.out.println("Sum: " + consumer.getSum());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
