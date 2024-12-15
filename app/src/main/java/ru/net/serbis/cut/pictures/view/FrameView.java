package ru.net.serbis.cut.pictures.view;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.param.*;

public class FrameView extends FrameLayout
{
    private Paint paint = new Paint();
    private LoadFailed failedView = new LoadFailed();
    private boolean failed;

    public FrameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        paint.setStyle(Paint.Style.STROKE);
        updateColor();
        paint.setStrokeWidth(4);

        setWillNotDraw(false);
    }

    public void updateColor()
    {
        paint.setColor(Params.FRAME_COLOR.getValue());
    }

    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);

        RectF rect = getFramRect(1);
        canvas.drawRect(rect, paint);
        if (failed)
        {
            failedView.draw(canvas, rect);
        }
    }

    public RectF getFramRect(int border)
    {
        float width = getWidth() - 2 * border;
        float cutWidth = Params.CUT_WIDTH.getValue();
        float cutHeight = Params.CUT_HEIGHT.getValue();
        float height = width * cutHeight / cutWidth;
        return new RectF(border, border, width, height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float width = getWidth();
        float height = getHeight();
        float cutWidth = Params.CUT_WIDTH.getValue();
        float cutHeight = Params.CUT_HEIGHT.getValue();
        float frameHeight = width * cutHeight / cutWidth;
        if (frameHeight > height)
        {
            float frameWidth = height * cutWidth / cutHeight;
            getLayoutParams().width = (int) frameWidth;
        }
    }

    public void failed(Bitmap bitmap)
    {
        failed = bitmap == null;
    }
}
