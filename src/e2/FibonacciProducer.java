package e2;

import java.util.concurrent.BlockingQueue;

public class FibonacciProducer implements Runnable
{

    private final BlockingQueue<Long> input;
    private final BlockingQueue<Long> output;

    public FibonacciProducer(BlockingQueue<Long> input, BlockingQueue<Long> output)
    {
        this.input = input;
        this.output = output;
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
