package ru.net.serbis.cut.pictures.task;

import android.content.*;

public class TaskError
{
    private int code;
    private String message;

    public TaskError(int code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public TaskError(Context context, int code, int message)
    {
        this(code, context.getResources().getString(message));
    }

    public int getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return message;
    }
}
