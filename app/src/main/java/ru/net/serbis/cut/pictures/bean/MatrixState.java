package ru.net.serbis.cut.pictures.bean;

import android.graphics.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.view.*;

public class MatrixState
{
    private FileImageView view;
    private Matrix matrix = new Matrix();
    private Matrix stored = new Matrix();
    private PointF start = new PointF();
    private MatrixMode mode = MatrixMode.NONE;
    private float space;
    private int rotate;
    private boolean mirror;

    public MatrixState(FileImageView view)
    {
        this.view = view;
    }

    public Matrix getMatrix()
    {
        return matrix;
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

    public void apply()
    {
        view.setImageMatrix(matrix);
        view.setScaleView(getScale());
        view.setStateView(getMatrixAsString());
    }

    public void reset()
    {
        cancel();
        space = 0;
        rotate = 0;
        mirror = false;
        start.set(new PointF());
        matrix.reset();
        apply();
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
            setScale(scale, scale, start.x, start.y);
        }
    }

    public String getMatrixAsString()
    {
        StringBuilder text = new StringBuilder();
        for (float value : getValues())
        {
            text.append(String.format("%.2f, ", value));
        }
        return text.toString();
    }

    private float[] getValues()
    {
        float[] values = new float[9];
        matrix.getValues(values);
        return values;
    }

    public float getScale()
    {
        return getScale(getValues());
    }

    private float getScale(float[] values)
    {
        return Math.max(Math.abs(values[Matrix.MSKEW_X]), Math.abs(values[Matrix.MSCALE_X]));
    }

    public void setScale(float sx, float sy, float px, float py)
    {
        matrix.postScale(sx, sy, px, py);
    }

    public void rotate(LinearLayout parent)
    {
        rotate = rotate == 270 ? 0 : rotate + 90;
        rotate(parent, 90);
    }
    
    public void rotate(LinearLayout parent, int degrees)
    {
        float x = parent.getWidth()/2f;
        float y = parent.getHeight()/2f;
        matrix.postRotate(degrees, x, y);
    }

    public void fitWidth(LinearLayout parent)
    {
        Rect rect = view.getDrawable().getBounds();
        float imageWidth = rect.width();
        if (rotate == 90 || rotate == 270)
        {
            imageWidth = rect.height();
        }
        float scale = parent.getWidth() / imageWidth;
        scale /= getScale();
        setScale(scale, scale, 0, 0);
    }
    
    public void toCenter()
    {
        float[] values = getValues();
        float x = - values[Matrix.MTRANS_X];
        float y = - values[Matrix.MTRANS_Y];
        float scale = getScale(values);
        Rect rect = view.getDrawable().getBounds();
        float dx = rect.width() * scale;
        float dy = rect.height() * scale;
        switch (rotate)
        {
            case 0:
                x += mirror ? dx : 0;
                break;
            case 90:
                x += dy;
                y += mirror ? dx : 0;
                break;
            case 180:
                x += mirror ? 0 : dx;
                y += dy;
                break;
            case 270:
                y += mirror ? 0 : dx;
                break;
        }
        matrix.postTranslate(x, y);
    }

    public void mirror(LinearLayout parent)
    {
        mirror = !mirror;
        float x = parent.getWidth()/2f;
        float y = parent.getHeight()/2f;
        setScale(-1, 1, x, y);
    }
}
