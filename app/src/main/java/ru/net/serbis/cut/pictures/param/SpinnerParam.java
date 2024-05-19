package ru.net.serbis.cut.pictures.param;

import android.view.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.*;

public abstract class SpinnerParam<T> extends Param<T, Spinner>
{
    protected T[] values;

    public SpinnerParam(int nameId, T defaultValue, T[] values)
    {
        super(nameId, defaultValue);
        this.values = values;
    }

    public SpinnerParam(String paramName, T defaultValue, boolean stored, T[] values)
    {
        super(paramName, defaultValue, stored);
        this.values = values;
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.param_spinner;
    }

    @Override
    public void initViewValue(View parent)
    {
        Spinner view = getViewValue(parent);
        ArrayAdapter<T> adapter = getAdapter();
        view.setAdapter(adapter);
        setValue(view, getValue());
    }

    protected ArrayAdapter<T> getAdapter()
    {
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(context, android.R.layout.simple_spinner_item, this.values);  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @Override
    public void setValue(Spinner view, T value)
    {
        ArrayAdapter<T> adapter = (ArrayAdapter<T>) view.getAdapter();
        view.setSelection(adapter.getPosition(value));
    }

    @Override
    public T getValue(Spinner view)
    {
        return (T) view.getSelectedItem();
    }
}
