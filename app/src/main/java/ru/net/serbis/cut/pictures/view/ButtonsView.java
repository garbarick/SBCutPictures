package ru.net.serbis.cut.pictures.view;

import android.content.*;
import android.content.res.*;
import android.util.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.util.*;

public class ButtonsView extends LinearLayout
{
    public ButtonsView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray values = context.obtainStyledAttributes(attrs, new int[] {
            android.R.attr.layout
        });
        int layout = values.getResourceId(0, 0);
        inflate(context, layout, this);
        LinearLayout buttons = UITool.get().findView(this, R.id.buttons);
        buttons.setOrientation(getOrientation());
    }
}
