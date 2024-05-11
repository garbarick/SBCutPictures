package ru.net.serbis.cut.pictures.activity;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.task.*;
import ru.net.serbis.cut.pictures.util.*;
import ru.net.serbis.cut.pictures.view.*;

public class Main extends Activity implements TaskCallback<List<File>>, View.OnClickListener
{
    private LinearLayout main;
    private FileImageView img;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle state)
    {
        super.onCreate(state);
        SysTool.get().initPermissions(this);
        setContentView(R.layout.main);

        main = UITool.get().findView(this, R.id.main);
        img = UITool.get().findView(this, R.id.img);
        progress = UITool.get().findView(this, R.id.progress);
        UITool.get().initButtons(this, this, R.id.settings, R.id.previous, R.id.next);

        img.post(
            new Runnable() 
            {
                @Override
                public void run()
                {
                    img.init(Main.this);
                    String dir = IOTool.get().getDownloadPath();
                    UITool.get().disableAll(main);
                    new FileListLoaderTask(Main.this).execute(dir);
                }
            }
        );
    }

    @Override
    public void progress(int progress)
    {
        this.progress.setProgress(progress);
    }

    @Override
    public void onResult(List<File> result, TaskError error)
    {
        UITool.get().enableAll(main);
        UITool.get().toast(error);
        if (result == null)
        {
            return;
        }
        img.setFiles(result);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.settings:
                break;
            case R.id.previous:
                img.previous();
                break;
            case R.id.next:
                img.next();
                break;
        }
    }
}
