package ru.net.serbis.cut.pictures.activity;

import android.app.*;
import android.content.res.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.adapter.*;
import ru.net.serbis.cut.pictures.dialog.*;
import ru.net.serbis.cut.pictures.param.*;
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
        setContentView(getLayout());

        main = UITool.get().findView(this, R.id.main);
        img = UITool.get().findView(this, R.id.img);
        progress = UITool.get().findView(this, R.id.progress);
        UITool.get().initAllButtons(main, this);

        img.post(
            new Runnable() 
            {
                @Override
                public void run()
                {
                    img.init(Main.this);
                    initImg();
                }
            }
        );
    }

    private int getLayout()
    {
        if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation)
        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            return R.layout.main_landscape;
        }
        return R.layout.main_portrait;
    }

    private void initImg()
    {
        img.clear();
        String dir = Params.SOURCE_FOLDER.getValue();
        UITool.get().disableAll(main);
        new FileListLoaderTask(this).execute(dir);
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

    private class Settings extends ParamsDialog
    {
        private Settings(Activity context)
        {
            super(context, R.string.settings, Params.PARAMS);
        }

        @Override
        public void ok(ParamsAdapter adapter)
        {
            super.ok(adapter);
            initImg();
        }

        @Override
        public void reset(ParamsAdapter adapter)
        {
            super.reset(adapter);
            initImg();
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.settings:
                new Settings(this).show();
                break;
            case R.id.rotate:
                img.rotate();
                break;
            case R.id.mirror:
                img.mirror();
                break;
            case R.id.fit_width:
                img.fitWidth(true, false);
                break;
            case R.id.previous:
                img.previous();
                break;
            case R.id.next:
                img.next();
                break;
            case R.id.save:
                img.save();
                break;
            default:
                UITool.get().notImplementedYet();
                break;
        }
    }
}
