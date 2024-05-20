package ru.net.serbis.cut.pictures.param;

import android.view.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.*;

public class TextViewParam extends TextParam<TextView>
{
    public TextViewParam(int nameId, String value)
    {
        super(nameId, value);
    }

    public TextViewParam(String paramName, String value, boolean stored)
    {
        super(paramName, value, stored);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.param_text;
    }

    @Override
    public void initViewValue(View parent)
    {
        TextView view = getViewValue(parent);
        setValue(view, getValue());
    }

    @Override
    public void setValue(TextView view, String value)
    {
        view.setText(value);
    }

    @Override
    public String getValue(TextView view)
    {
        return view.getText().toString();
    }
}
