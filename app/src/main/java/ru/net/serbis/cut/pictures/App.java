package ru.net.serbis.cut.pictures;

import android.app.*;
import ru.net.serbis.cut.pictures.activity.*;
import ru.net.serbis.utils.*;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        UITool.get().set(this);
        SysTool.get().set(this);
        Strings.get().set(this);
        Preferences.get().set(this);
        Preferences.get().setApp(Constants.APP);
        ExceptionHandler.get().set(this);
        ExceptionHandler.get().setErrorActivity(ExceptionReport.class);
    }
}
