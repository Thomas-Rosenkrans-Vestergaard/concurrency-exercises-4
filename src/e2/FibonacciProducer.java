package e2;

import java.util.concurrent.BlockingQueue;

public class FibonacciProducer implements Runnable
{

    private final BlockingQueue<Long> input;
    private final BlockingQueue<Long> output;
    private final Counter             counter;

    public FibonacciProducer(BlockingQueue<Long> input, BlockingQueue<Long> output, Counter counter)
    {
        this.input = input;
        this.output = output;
        this.counter = counter;
    }

    @Override public void run()
    {
        while (true) {
            Long next = input.poll();
            if (next == null)
                return;

            send(fibonacci(next));
        }
    }

    private void send(Long number)
    {
        try {
            output.put(number);
        } catch (InterruptedException e) {
            send(number);
        }
    }

    private long fibonacci(long n)
    {
        if (n < 2)
            return n;

        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
