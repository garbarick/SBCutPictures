package ru.net.serbis.cut.pictures.util;

import android.app.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.task.*;

public class UITool extends Util
{
    private static final UITool instance = new UITool();
    private Handler hadler = new Handler(Looper.getMainLooper());

    public static UITool get()
    {
        return instance;
    }

    public <T> T findView(View view, int id)
    {
        return (T) view.findViewById(id);
    }

    public <T> T findView(Activity view, int id)
    {
        return (T) view.findViewById(id);
    }
    
    public <T> T findView(AlertDialog view, int id)
    {
        return (T) view.findViewById(id);
    }

    public String getEditText(Activity activity, int id)
    {
        EditText text = findView(activity, id);
        return text.getText().toString();
    }

    public void hide(Activity activity, int id)
    {
        View view = findView(activity, id);
        view.setVisibility(View.GONE);
    }

    public void show(Activity activity, int id)
    {
        View view = findView(activity, id);
        view.setVisibility(View.VISIBLE);
    }

    public void enable(Activity activity, int id)
    {
        View view = findView(activity, id);
        enable(view);
    }

    public void enable(View view)
    {
        view.setEnabled(true);
    }

    public void disable(Activity activity, int id)
    {
        View view = findView(activity, id);
        disable(view);
    }

    public void disable(View view)
    {
        view.setEnabled(false);
    }

    public boolean isEnabled(Activity activity, int id)
    {
        View view = findView(activity, id);
        return isEnabled(view);
    }

    public boolean isEnabled(View view)
    {
        return view.isEnabled();
    }

    public void disableAll(View view)
    {
        setEnableAll(view, false);
    }

    public void enableAll(View view)
    {
        setEnableAll(view, true);
    }

    public void setEnableAll(View view, boolean enable)
    {
        view.setEnabled(enable);
        if (view instanceof ViewGroup)
        {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i ++)
            {
                View child = group.getChildAt(i);
                setEnableAll(child, enable);
            }
        }
    }

    public void toast(final String text)
    {
        runOnUiThread(
            new Runnable()
            {
                public void run()
                {
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }
            }
        );
    }
    
    public void toast(int code, String text)
    {
        toast(code + ": " + text);
    }

    public void toast(TaskError error)
    {
        if (error == null)
        {
            return;
        }
        toast(error.getCode(), error.getMessage());
    }

    public void notImplementedYet()
    {
        toast(context.getResources().getString(R.string.error_not_implemented));
    }

    public void initButtons(Activity context, View.OnClickListener tool, int ... ids)
    {
        for (int id : ids)
        {
            View button = findView(context, id);
            button.setOnClickListener(tool);
        }
    }

    public void initAllButtons(ViewGroup view, View.OnClickListener tool)
    {
        for (int i = 0; i < view.getChildCount(); i ++)
        {
            View child = view.getChildAt(i);
            if (child instanceof ViewGroup)
            {
                initAllButtons((ViewGroup) child, tool);
            }
            else if (child instanceof Button ||
                     child instanceof ImageButton)
            {
                child.setOnClickListener(tool);
            }
        }
    }

    public void runOnUiThread(Runnable run)
    {
        hadler.post(run);
    }

    public void setSandwitchView(Button button)
    {
        Drawable sandwitch = context.getResources().getDrawable(R.drawable.sandwitch);
        sandwitch.setBounds(28, 0, 92, 64);
        button.setCompoundDrawables(sandwitch, null, null, null);
    }
}
