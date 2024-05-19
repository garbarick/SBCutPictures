package ru.net.serbis.cut.pictures.param;

import android.view.*;

public abstract class TextParam<V extends View> extends Param<String, V>
{
    public TextParam(int nameId, String defaultValue)
    {
        super(nameId, defaultValue);
    }

    public TextParam(String paramName, String defaultValue, boolean stored)
    {
        super(paramName, defaultValue, stored);
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
