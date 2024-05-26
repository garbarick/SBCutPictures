package ru.net.serbis.cut.pictures.task;

import ru.net.serbis.cut.pictures.util.*;

public class TaskError
{
    private int code;
    private String message;

    public TaskError(int code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public TaskError(int code, int message)
    {
        this(code, Strings.get().get(message));
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
