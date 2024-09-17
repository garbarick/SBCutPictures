package ru.net.serbis.cut.pictures.param;

import android.app.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.utils.*;

import ru.net.serbis.cut.pictures.R;

public abstract class Param<T, V extends View>
{
    protected String name;
    protected String paramName;
    protected int nameId;
    protected T value;
    protected Activity context;
    protected boolean stored;

    public Param(int nameId, String paramName, T value, boolean stored)
    {
        this.nameId = nameId;
        this.name = paramName;
        this.paramName = paramName;
        this.value = value;
        this.stored = stored;
    }

    public Param(int nameId, String paramName, T value)
    {
        this(nameId, paramName, value, true);
    }

    public Param(int nameId, T value)
    {
        this(nameId, null, value);
    }

    public Param(String paramName, T value, boolean stored)
    {
        this(0, paramName, value, stored);
    }

    public String getName()
    {
        return name;
    }

    public abstract int getLayoutId();

    public void initName()
    {
        name = Strings.get().get(nameId);
        if (paramName == null)
        {
            paramName = name;
        }
    }

    public void initNameView(View parent)
    {
        TextView view = UITool.get().findView(parent, R.id.name);
        view.setText(name);
    }

    public abstract void initViewValue(View parent);

    public V getViewValue(View parent)
    {
        return UITool.get().findView(parent, R.id.value);
    }

    public void saveValue(T value)
    {
        if (stored)
        {
            String data = value == null ? null : typeToString(value);
            Preferences.get().setString(paramName, data);
            return;
        }
        this.value = value;
    }

    public abstract String typeToString(T value);

    public T getValue()
    {
        if (stored)
        {
            return stringToType(Preferences.get().getString(paramName, typeToString(value)));
        }
        return value;
    }

    public abstract T stringToType(String value);

    public void saveViewValue(V view)
    {
        T value = getValue(view);
        saveValue(value);
    }

    public abstract void setValue(V view, T value);
    public abstract T getValue(V view);

    public void setContext(Activity context)
    {
        this.context = context;
    }
}
