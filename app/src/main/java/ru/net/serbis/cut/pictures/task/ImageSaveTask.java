package ru.net.serbis.cut.pictures.task;

import android.graphics.*;
import android.os.*;
import java.io.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.bean.*;
import ru.net.serbis.cut.pictures.util.*;
import ru.net.serbis.utils.*;
import ru.net.serbis.utils.bean.*;

public class ImageSaveTask extends AsyncTask<File, Void, File>
{
    private MatrixState state;
    private RectF rect;
    private TaskCallback<File> callback;
    private TaskError error;

    public ImageSaveTask(MatrixState state, RectF rect, TaskCallback<File> callback)
    {
        this.state = state;
        this.rect = rect;
        this.callback = callback;
    }

    @Override
    protected File doInBackground(File ... files)
    {
        File file = files[0];
        try
        {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            return new ImageSaver(bitmap, state, rect).save();
        }
        catch (Exception e)
        {
            Log.error(this, e);
            error = new TaskError(Constants.ERROR_SAVE_FILE, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(File result)
    {
        callback.onResult(result, error);
    }
}
