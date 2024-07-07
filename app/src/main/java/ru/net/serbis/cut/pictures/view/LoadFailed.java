package ru.net.serbis.cut.pictures.view;

import android.graphics.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.util.*;

public class LoadFailed
{
    private Paint paint = new Paint();
    private String text;
    private Rect rect = new Rect();

    public LoadFailed()
    {
        text = Strings.get().get(R.string.load_failed);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(100);
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), rect);
    }

    public void draw(Canvas canvas, RectF parent)
    {
        float x = parent.width() / 2f - rect.width() / 2f - rect.left;
        float y = parent.height() / 2f + rect.height() / 2f - rect.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
