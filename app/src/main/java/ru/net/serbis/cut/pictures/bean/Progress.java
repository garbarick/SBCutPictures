package ru.net.serbis.cut.pictures.bean;

public class Progress
{
    private int count;
    private int current;

    public void count(int count)
    {
        this.count += count;
    }

    public int progress()
    {
        return getPercent(count, ++current);
    }

    private int getPercent(long max, long cur)
    {
        Double percent = 100.0 / max * cur;
        return percent.intValue();
    }
}
