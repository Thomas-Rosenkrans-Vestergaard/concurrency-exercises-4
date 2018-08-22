package e1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Turnstile implements Runnable
{
    private final TurnstileCounter counter;
    private       int              count;
    private final Lock             lock;

    final int COUNT_MAX = 1000;

    Turnstile(TurnstileCounter c, Lock lock)
    {
        counter = c;
        this.lock = lock;
    }

    public int getCount()
    {
        return count;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < COUNT_MAX; i++) {
            try {
                if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                    counter.incr();
                    count++;
                }
            } catch (InterruptedException e) {
                i--;
            } finally {
                lock.unlock();
            }
        }
    }

}
