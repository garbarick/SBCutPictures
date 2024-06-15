package ru.net.serbis.cut.pictures.view;

import android.content.*;
import android.content.res.*;
import android.util.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.util.*;

public class ButtonsView extends GridLayout
{
    public ButtonsView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray values = context.obtainStyledAttributes(attrs, new int[] {
            android.R.attr.layout,
            android.R.attr.columnCount
        });
        int layout = values.getResourceId(0, 0);
        int columnCount = values.getInt(1, 0);
        inflate(context, layout, this);
        GridLayout buttons = UITool.get().findView(this, R.id.buttons);
        buttons.setColumnCount(columnCount);
    }
}
