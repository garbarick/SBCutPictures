package ru.net.serbis.cut.pictures.activity;

import android.app.*;
import ru.net.serbis.utils.activity.*;

public class ExceptionReport extends ErrorActivity
{
    @Override
    protected Class<? extends Activity> getActivityClass()
    {
        return Main.class;
    }
}
