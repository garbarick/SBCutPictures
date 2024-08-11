package ru.net.serbis.cut.pictures.task;

import android.os.*;
import java.io.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.bean.*;
import ru.net.serbis.utils.*;
import ru.net.serbis.utils.bean.*;

public class FolderCleanUpTask extends AsyncTask<String, Integer, Boolean>
{
    private TaskCallback<Boolean> callback;
    private TaskError error;
    private Progress progress = new Progress();

    public FolderCleanUpTask(TaskCallback<Boolean> callback)
    {
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(String ... params)
    {
        try
        {
            String dir = params[0];
            publishProgress(0);
            cleaupFiles(dir);
            return true;
        }
        catch (Exception e)
        {
            Log.error(this, e);
            error = new TaskError(Constants.ERROR_DELETE_FILE, e.getMessage());
            return false;
        }
        finally
        {
            publishProgress(0);
        }
    }

    private void cleaupFiles(String dir)
    {
        File[] files = new File(dir).listFiles();
        if (files == null)
        {
            return;
        }
        progress.count(files.length);
        for (File file : files)
        {
            file.delete();
            publishProgress(progress.progress());
        }
        UITool.get().toast(Strings.get().get(R.string.deleted_files, files.length));
    }

    @Override
    protected void onProgressUpdate(Integer[] progress)
    {
        callback.progress(progress[0]);
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        callback.onResult(result, error);
    }
}
