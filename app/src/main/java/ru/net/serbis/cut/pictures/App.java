package ru.net.serbis.cut.pictures;

import android.app.*;
import ru.net.serbis.cut.pictures.handler.*;
import ru.net.serbis.cut.pictures.param.*;
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
        initParams();
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
    }

    private void initParams()
    {
        for (Param param : Reflection.get().getValues(Params.class, Param.class).values())
        {
            param.initName();
        }
    }
}
