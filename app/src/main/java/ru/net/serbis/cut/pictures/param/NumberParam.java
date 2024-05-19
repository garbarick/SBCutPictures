package ru.net.serbis.cut.pictures.param;

import android.view.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.util.*;

public class NumberParam extends Param<Integer, EditText>
{
    public NumberParam(int nameId, Integer defaultValue)
    {
        super(nameId, defaultValue);
    }

    public NumberParam(String paramName, Integer defaultValue, boolean stored)
    {
        super(paramName, defaultValue, stored);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.param_number;
    }

    @Override
    public void initViewValue(View parent)
    {
        EditText view = getViewValue(parent);
        setValue(view, getValue());
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
            return defaultValue;
        }
    }

    @Override
    public void setValue(EditText view, Integer value)
    {
        view.setText(typeToString(value));
    }

    @Override
    public Integer getValue(EditText view)
    {
        return stringToType(view.getText().toString());
    }
}
