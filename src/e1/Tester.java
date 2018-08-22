package e1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Tester
{

    static final int         NUMBER_OF_TURNSTILES = 40;
    static       Turnstile[] turnStiles           = new Turnstile[NUMBER_OF_TURNSTILES];

    public static void main(String[] args) throws InterruptedException
    {
        TurnstileCounter sharedCounter = new TurnstileCounter();
        ReentrantLock    lock          = new ReentrantLock();

        for (int i = 0; i < NUMBER_OF_TURNSTILES; i++) {
            turnStiles[i] = new Turnstile(sharedCounter, lock);
        }

        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < NUMBER_OF_TURNSTILES; i++) {
            es.execute(turnStiles[i]);
        }

        es.shutdown();
        es.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("All turnstiles are done");
        System.out.println(sharedCounter.getValue());
    }
}
