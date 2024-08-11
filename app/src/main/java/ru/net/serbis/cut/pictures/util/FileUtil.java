package ru.net.serbis.cut.pictures.util;

import java.io.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.utils.*;

public class FileUtil
{
    private static final FileUtil instance = new FileUtil();

    public static FileUtil get()
    {
        return instance;
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

    public void moveFile(File from, File to) throws Exception
    {
        IOTool.get().copy(new FileInputStream(from), new FileOutputStream(to), true, true, 10240);
        to.setLastModified(from.lastModified());
        from.delete();
    }

    public boolean moveFileQuietly(File from, File to)
    {
        try
        {
            to.getParentFile().mkdirs();
            moveFile(from, to);
            return true;
        }
        catch (Exception e)
        {
            Log.error(this, e);
            UITool.get().toast(Constants.ERROR_MOVE_FILE, e.getMessage());
            return false;
        }
    }
}
