package ru.net.serbis.cut.pictures.task;

import android.os.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.bean.*;
import ru.net.serbis.cut.pictures.util.*;
import ru.net.serbis.cut.pictures.sort.*;

public class FileListLoaderTask extends AsyncTask<String, Integer, List<File>>
{
    private TaskCallback<List<File>> callback;
    private TaskError error;
    private Progress progress = new Progress();

    public FileListLoaderTask(TaskCallback<List<File>> callback)
    {
        this.callback = callback;
    }

    @Override
    protected List<File> doInBackground(String ... params)
    {
        try
        {
            String dir = params[0];
            publishProgress(0);
            return loadLocalFiles(dir);
        }
        catch (Exception e)
        {
            Log.error(this, e);
            error = new TaskError(Constants.ERROR_LOAD_FILE_LIST, e.getMessage());
            return null;
        }
        finally
        {
            publishProgress(0);
        }
    }

    private List<File> loadLocalFiles(String dir)
    {
        List<File> result = loadLocalFiles(new File(dir));
        Collections.sort(result, new ImageSorter());
        UITool.get().toast(Strings.get().get(R.string.found_files, result.size()));
        return result;
    }

    private List<File> loadLocalFiles(File dir)
    {
        final List<File> result = new ArrayList<File>();
        File[] files = dir.listFiles(new FileFilter()
            {
                public boolean accept(File file)
                {
                    if (file.isDirectory())
                    {
                        result.addAll(loadLocalFiles(file));
                        return false;
                    }
                    if (IOTool.get().checkExt(file))
                    {
                        return true;
                    }
                    return false;
                }
            }
        );
        if (files != null)
        {
            progress.count(files.length);
            for (File file : files)
            {
                result.add(file);
                publishProgress(progress.progress());
            }
        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Integer[] progress)
    {
        callback.progress(progress[0]);
    }

    @Override
    protected void onPostExecute(List<File> result)
    {
        callback.onResult(result, error);
    }
}
