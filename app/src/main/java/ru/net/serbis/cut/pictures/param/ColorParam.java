package ru.net.serbis.cut.pictures.param;

import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.dialog.*;
import ru.net.serbis.cut.pictures.listener.*;

public class ColorParam extends NumberParam<Button>
{
    public ColorParam(int nameId, Integer value)
    {
        super(nameId, value);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.param_button;
    }

    @Override
    public void initViewValue(View parent)
    {
        Button view = getViewValue(parent);
        setValue(view, getValue());
        view.setOnClickListener(
            new ViewOnClickListener<Button>()
            {
                @Override
                public void onClickView(final Button view)
                {
                    new ColorPicker(context, getValue(view))
                    {
                        @Override
                        public void updateValue(Integer value)
                        {
                            setValue(view, value);
                        }
                    };
                }
            }
        );
    }

    @Override
    public void setValue(Button view, Integer value)
    {
        view.setBackgroundColor(value);
    }

    @Override
    public Integer getValue(Button view)
    {
        ColorDrawable color = (ColorDrawable) view.getBackground();
        return color.getColor();
    }
}
