package e2;

import java.util.concurrent.BlockingQueue;

public class FibonacciConsumer implements Runnable
{

    private final BlockingQueue<Long> input;
    private long sum = 0;
    private final Counter counter;

    public FibonacciConsumer(BlockingQueue<Long> input, Counter counter)
    {
        this.input = input;
        this.counter = counter;
    }

    @Override public void run()
    {
        while (true) {
            if (counter.readConsumed() == counter.readProduced())
                return;

            try {
                Long number = input.take();
                sum += number;
                counter.incrementConsumed();
                System.out.println(number);

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
