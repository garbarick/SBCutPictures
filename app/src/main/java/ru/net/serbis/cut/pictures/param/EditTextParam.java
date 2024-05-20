package ru.net.serbis.cut.pictures.param;

import android.view.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.*;

public class EditTextParam extends TextParam<EditText>
{
    public EditTextParam(int nameId, String value)
    {
        super(nameId, value);
    }

    public EditTextParam(String paramName, String value, boolean stored)
    {
        super(paramName, value, stored);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.param_edit;
    }

    @Override
    public void initViewValue(View parent)
    {
        EditText view = getViewValue(parent);
        setValue(view, getValue());
    }

    @Override
    public void setValue(EditText view, String value)
    {
        view.setText(value);
    }

    @Override
    public String getValue(EditText view)
    {
        return view.getText().toString();
    }
}
