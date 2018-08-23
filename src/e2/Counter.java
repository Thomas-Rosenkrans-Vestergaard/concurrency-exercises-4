package e2;

public class Counter
{

    private int consumed;
    private int produced;

    public synchronized int readConsumed()
    {
        return consumed;
    }

    public synchronized int incrementConsumed()
    {
        return ++consumed;
    }

    public synchronized int readProduced()
    {
        return produced;
    }

    public synchronized void setProduced(int n)
    {
        produced = n;
    }
}
