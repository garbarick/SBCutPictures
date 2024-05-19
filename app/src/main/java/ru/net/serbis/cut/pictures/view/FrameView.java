package ru.net.serbis.cut.pictures.view;

import android.content.*;
import android.util.*;
import android.widget.*;
import android.graphics.*;
import ru.net.serbis.cut.pictures.util.*;
import ru.net.serbis.cut.pictures.param.*;

public class FrameView extends LinearLayout
{
    private Paint paint = new Paint();
    
    public FrameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(4);
        setWillNotDraw(false);
    }

    @Override
    public void onDrawForeground(Canvas canvas)
    {
        super.onDrawForeground(canvas);

        float width = getWidth() - 2;
        float cutWidth = Params.CUT_WIDTH.getValue();
        float cutHeight = Params.CUT_HEIGHT.getValue();
        float height = width * cutHeight / cutWidth;

        canvas.drawRect(1, 1, width, height, paint);
    }
}
