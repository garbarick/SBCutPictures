package ru.net.serbis.cut.pictures.util;

import android.graphics.*;
import java.io.*;
import java.text.*;
import java.util.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.bean.*;
import ru.net.serbis.cut.pictures.param.*;

public class ImageSaver
{
    private int targetWidth;
    private int targetHeight;
    private SaveImgFormat format;
    private MatrixState state;
    private Bitmap source;
    private RectF sourceRect;

    public ImageSaver(Bitmap source, MatrixState state, RectF sourceRect)
    {
        targetWidth = Params.CUT_WIDTH.getValue();
        targetHeight = Params.CUT_HEIGHT.getValue();
        format = Params.IMAGE_FORMAT.getValue();
        this.source = source;
        this.state = state;
        this.sourceRect = sourceRect;
    }
    
    public void save()
    {
        Matrix matrix = getMatrix();
        Bitmap bitmap = convert(matrix);
        File file = getFile();
        save(bitmap, file);
    }

    private Bitmap convert(Matrix matrix)
    {
        Bitmap result = Bitmap.createBitmap(targetWidth, targetHeight, source.getConfig());
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        canvas.drawBitmap(source, matrix, paint);
        return result;
    }

    private void save(Bitmap bitmap, File file)
    {
        OutputStream out = null;
        try
        {
            out = new FileOutputStream(file);
            bitmap.compress(format.getFormat(), 100, out);
        }
        catch (Exception e)
        {
            Log.error(this, e);
            UITool.get().toast(Constants.ERROR_SAVE_FILE, e.getMessage());
        }
        finally
        {
            IOTool.get().close(out);
        }
    }

    private Matrix getMatrix()
    {
        Matrix matrix = new Matrix(state.getMatrix());
        float scale = targetWidth / sourceRect.width();
        matrix.postScale(scale, scale);
        return matrix;
    }

    private File getFile()
    {
        String fileName = new SimpleDateFormat(Params.NAME_PATTERN.getValue()).format(new Date()) + "." + format.name();
        return new File(Params.SOURCE_FOLDER.getValue(), fileName);
    }
}
