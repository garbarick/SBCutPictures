package ru.net.serbis.cut.pictures.param;

public class Position
{
    public int get(int count)
    {
        int pos = Params.POS.getValue();
        if (pos < 0 || pos + 1 > count)
        {
            Params.POS.saveValue(0);
        }
        return Params.POS.getValue();
    }

    public void next(int count)
    {
        int pos = Params.POS.getValue() + 1;
        if (pos >= count)
        {
            pos = 0;
        }
        Params.POS.saveValue(pos);
    }

    public void previous(int count)
    {
        int pos = Params.POS.getValue() - 1;
        if (pos < 0)
        {
            pos = count - 1;
        }
        Params.POS.saveValue(Math.max(0, pos));
    }
}
