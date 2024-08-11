package ru.net.serbis.cut.pictures.param;

import android.view.*;
import ru.net.serbis.utils.*;

public abstract class NumberParam<V extends View> extends Param<Integer, V>
{
    public NumberParam(int nameId, Integer value)
    {
        super(nameId, value);
    }

    public NumberParam(int nameId, Integer value, boolean stored)
    {
        super(nameId, null, value, stored);
    }

    @Override
    public String typeToString(Integer value)
    {
        return value.toString();
    }

    @Override
    public Integer stringToType(String value)
    {
        try
        {
            return Integer.valueOf(value);
        }
        catch (Exception e)
        {
            Log.error(this, e);
            return this.value;
        }
    }
}
