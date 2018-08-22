package e1;

public class TurnstileCounter
{

    static final long DELAY_VAL = 10000;
    long count = 0;

    public long getValue()
    {
        return count;
    }

    public /*synchronized*/ void incr()
    {
        count++;
    }
}