package ru.net.serbis.cut.pictures;

import android.app.*;
import ru.net.serbis.cut.pictures.handler.*;
import ru.net.serbis.cut.pictures.util.*;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        UITool.get().set(this);
        SysTool.get().set(this);
        Strings.get().set(this);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
    }
}
