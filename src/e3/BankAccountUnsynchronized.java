package e3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// NOT THREADSAFE
public class BankAccountUnsynchronized
{

    private double balance;
    private final Lock lock = new ReentrantLock();

    public BankAccountUnsynchronized()
    {
        balance = 0;
    }

    public /*synchronized*/ void deposit(double amount)
    {
        try {
            if (lock.tryLock(100, TimeUnit.MILLISECONDS))
                balance += amount;
        } catch (InterruptedException e) {
            deposit(amount);
        } finally {
            lock.unlock();
        }
    }

    public /*synchronized*/ void withdraw(double amount)
    {
        try {
            if (lock.tryLock(100, TimeUnit.MILLISECONDS))
                balance -= amount;
        } catch (InterruptedException e) {
            withdraw(amount);
        } finally {
            lock.unlock();
        }
    }

    public double getBalance()
    {
        return balance;
    }
}