package ru.net.serbis.cut.pictures.util;

import android.os.*;
import java.io.*;
import ru.net.serbis.cut.pictures.*;

public class IOTool
{
    private static final IOTool instance = new IOTool();

    public static IOTool get()
    {
        return instance;
    }

    public void close(Object o)
    {
        try
        {
            if (o == null)
            {
                return;
            }
            if (o instanceof Closeable)
            {
                ((Closeable)o).close();
            }
            else if (o instanceof AutoCloseable)
            {
                ((AutoCloseable)o).close();
            }
        }
        catch (Exception e)
        {}
    }

    public void copy(InputStream is, OutputStream os, boolean closeIn, boolean closeOut, int bufferSize) throws Exception
    {
        try
        {
            byte[] buf = new byte[bufferSize];
            int len;
            while ((len = is.read(buf)) > 0)
            {
                os.write(buf, 0, len);
            }
        }
        finally
        {
            if (closeIn)
            {
                close(is);
            }
            if (closeOut)
            {
                close(os);
            }
        }
    }
    
    public boolean copyQuietly(InputStream is, OutputStream os, boolean closeIn, boolean closeOut, int bufferSize)
    {
        try
        {
            copy(is, os, closeIn, closeOut, bufferSize);
            return true;
        }
        catch (Exception e)
        {
            Log.error(new IOTool(), e);
            return false;
        }
    }

    public File getDownloadFile()
    {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    public String getDownloadPath()
    {
        return getDownloadFile().getAbsolutePath();
    }

    public File getDownloadFile(String path)
    {
        return new File(getDownloadFile(), path);
    }
    
    public String getDownloadPath(String path)
    {
        return getDownloadFile(path).getAbsolutePath();
    }

    public String getExternalFile(String path)
    {
        File dir = Environment.getExternalStorageDirectory();
        return new File(dir, path).getAbsolutePath();
    }

    public String getExt(String fileName)
    {
        String ext = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0)
        {
            ext = fileName.substring(i + 1).toLowerCase();
        }
        return ext;
    }
    
    public boolean checkExt(String fileName)
    {
        return Constants.EXTENSIONS.contains(getExt(fileName));
	}
    
    public boolean checkExt(File file)
    {
        return checkExt(file.getName());
	}

    public void moveFile(File from, File to, int bufferSize) throws Exception
    {
        copy(new FileInputStream(from), new FileOutputStream(to), true, true, bufferSize);
        from.delete();
    }

    public boolean moveFileQuietly(File from, File to, int bufferSize)
    {
        try
        {
            moveFile(from, to, bufferSize);
            return true;
        }
        catch (Exception e)
        {
            Log.error(new IOTool(), e);
            return false;
        }
    }
}
