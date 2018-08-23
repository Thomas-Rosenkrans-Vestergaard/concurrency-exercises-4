package e2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class FibonacciConsumer implements Runnable
{

    private final BlockingQueue<Long> input;
    private long sum = 0;

    public FibonacciConsumer(BlockingQueue<Long> input)
    {
        this.input = input;
    }

    @Override public void run()
    {
        while (true) {
            try {
                Long number = input.poll(100, TimeUnit.SECONDS);
                if (number == null)
                    return;

                System.out.println(number);
                sum += number;

            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public long getSum()
    {
        return this.sum;
    }
}
