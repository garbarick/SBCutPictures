package ru.net.serbis.cut.pictures.param;

import android.view.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.*;

public class EditNumberParam extends NumberParam<EditText>
{
    public EditNumberParam(int nameId, Integer value)
    {
        super(nameId, value);
    }

    public EditNumberParam(int nameId, Integer value, boolean stored)
    {
        super(nameId, value);
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
