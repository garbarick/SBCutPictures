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

public class Main extends Activity implements TaskCallback<List<File>>, View.OnClickListener, PopupMenu.OnMenuItemClickListener
{
    private ViewsHolder holder = new ViewsHolder();

    @Override
    protected void onCreate(Bundle state)
    {
        super.onCreate(state);
        SysTool.get().initPermissions(this);
        setContentView(getLayout());

        holder.init(this);
        UITool.get().initAllButtons(holder.main, this);
        holder.categoryMenu.setOnMenuItemClickListener(this);

        holder.img.post(
            new Runnable() 
            {
                @Override
                public void run()
                {
                    holder.img.init(holder);
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
        holder.img.clear();
        String dir = Params.SOURCE_FOLDER.getValue();
        UITool.get().disableAll(holder.main);
        new FileListLoaderTask(this).execute(dir);
    }

    @Override
    public void progress(int progress)
    {
        holder.progress.setProgress(progress);
    }

    @Override
    public void onResult(List<File> result, TaskError error)
    {
        UITool.get().enableAll(holder.main);
        UITool.get().toast(error);
        if (result == null)
        {
            return;
        }
        holder.img.setFiles(result);
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
            holder.updateCategories();
            initImg();
        }

        @Override
        public void reset(ParamsAdapter adapter)
        {
            super.reset(adapter);
            holder.updateCategories();
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
                holder.img.rotate();
                break;
            case R.id.mirror:
                holder.img.mirror();
                break;
            case R.id.fit_width:
                holder.img.fitWidth(true, false);
                break;
            case R.id.previous:
                holder.img.previous();
                break;
            case R.id.next:
                holder.img.next();
                break;
            case R.id.save:
                holder.img.save();
                break;
            case R.id.save_as:
                holder.img.saveAs();
                break;
            case R.id.delete:
                holder.img.delete();
                break;
            case R.id.undo:
                holder.img.undo();
                break;
            case R.id.cleanup_backup:
                holder.img.cleanupBackup();
                break;
            case R.id.category:
                holder.categoryMenu.show();
                break;
            default:
                UITool.get().notImplementedYet();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        holder.checkCategory(item);
        return true;
    }
}
