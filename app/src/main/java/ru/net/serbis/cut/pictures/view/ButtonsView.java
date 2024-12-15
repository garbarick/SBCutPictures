package ru.net.serbis.cut.pictures.view;

import android.content.*;
import android.content.res.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.utils.*;

import ru.net.serbis.cut.pictures.R;

public class ButtonsView extends GridLayout
{
    private GridLayout buttons;
    private int columnCount;
    
    public ButtonsView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray values = context.obtainStyledAttributes(attrs, new int[] {
            android.R.attr.layout,
            android.R.attr.columnCount
        });
        int layout = values.getResourceId(0, 0);
        columnCount = values.getInt(1, 0);
        inflate(context, layout, this);
        buttons = UITool.get().findView(this, R.id.buttons);
        buttons.setColumnCount(columnCount);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec)
    {
        int width = MeasureSpec.getSize(widthSpec);
        int height = MeasureSpec.getSize(heightSpec);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1)
        {
            setChildrenSize(width, height);
        }
        super.onMeasure(widthSpec, heightSpec);
    }

    private void setChildrenSize(int width, int height)
    {
        int count = buttons.getChildCount();
        int rowCount = count / columnCount;
        for (int i = 0; i < count; i++)
        {
            View child = buttons.getChildAt(i);
            ViewGroup.LayoutParams params = child.getLayoutParams();
            params.width = width / columnCount;
            params.height = height / rowCount;
        }
    }
}
