package ru.net.serbis.cut.pictures.param;

import android.view.*;

public abstract class TextParam<V extends View> extends Param<String, V>
{
    public TextParam(int nameId, String value)
    {
        super(nameId, value);
    }

    public TextParam(String paramName, String value, boolean stored)
    {
        super(paramName, value, stored);
    }

    @Override
    public String typeToString(String value)
    {
        return value;
    }

    @Override
    public String stringToType(String value)
    {
        return value;
    }
}
