package ru.net.serbis.cut.pictures.bean;

import android.graphics.*;
import android.view.*;
import android.widget.*;

public class MatrixState
{
    private Matrix matrix = new Matrix();
    private Matrix stored = new Matrix();
    private PointF start = new PointF();
    private MatrixMode mode = MatrixMode.NONE;
    private float space;

    public void init(ImageView view)
    {
        matrix = view.getMatrix();
    }

    public void cancel()
    {
        mode = MatrixMode.NONE;
    }

    public void startMove(MotionEvent event)
    {
        stored.set(matrix);
        start.set(event.getX(), event.getY());
        mode = MatrixMode.MOVE;
    }

    public void endMove(MotionEvent event)
    {
        if (mode != MatrixMode.MOVE)
        {
            return;
        }
        matrix.set(stored);
        matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
    }

    public void apply(ImageView view)
    {
        view.setImageMatrix(matrix);
    }

    public void reset(ImageView view)
    {
        cancel();
        space = 0;
        start.set(new PointF());
        matrix.reset();
        apply(view);
    }

    private float getSpace(MotionEvent event)
    {
        if (event.getPointerCount() < 2)
        {
            return 0;
        }
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private boolean enoughSpace(float space)
    {
        return space > 10;
    }

    public void startZoom(MotionEvent event)
    {
        space = getSpace(event);
        if (enoughSpace(space))
        {
            stored.set(matrix);
            averagePoint(event);
            mode = MatrixMode.ZOOM;
        }
    }

    private void averagePoint(MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        start.set(x / 2, y / 2);
    }

    public void endZoom(MotionEvent event)
    {
        if (mode != MatrixMode.ZOOM)
        {
            return;
        }
        float space = getSpace(event);
        if (enoughSpace(space))
        {
            matrix.set(stored);
            float scale = space / this.space;
            matrix.postScale(scale, scale, start.x, start.y);
        }
    }

    public float getScale()
    {
        float[] values = new float[9];
        matrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }
}
