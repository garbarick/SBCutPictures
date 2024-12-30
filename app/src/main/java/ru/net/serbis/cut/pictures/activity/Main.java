package ru.net.serbis.cut.pictures.activity;

import android.app.*;
import android.content.res.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.param.*;
import ru.net.serbis.cut.pictures.task.*;
import ru.net.serbis.cut.pictures.view.*;
import ru.net.serbis.utils.*;
import ru.net.serbis.utils.adapter.*;
import ru.net.serbis.utils.bean.*;
import ru.net.serbis.utils.dialog.*;

import ru.net.serbis.cut.pictures.R;

public class Main extends Activity implements TaskCallback<List<File>>, View.OnClickListener, PopupMenu.OnMenuItemClickListener
{
    private ViewsHolder holder = new ViewsHolder();

    @Override
    protected void onCreate(Bundle state)
    {
        super.onCreate(state);
        new Permissions().initPermissions(this);
        setContentView(getLayout());

        holder.init(this);
        UITool.get().initAllButtons(holder.main, this);
        holder.categoryMenu.setOnMenuItemClickListener(this);

        holder.imgView.post(
            new Runnable() 
            {
                @Override
                public void run()
                {
                    holder.imgView.init(holder);
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
        holder.imgView.clear();
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
        holder.imgView.setFiles(result);
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
            update();
        }

        @Override
        public void reset(ParamsAdapter adapter)
        {
            super.reset(adapter);
            update();
        }
    }

    private void update()
    {
        holder.updateCategories();
        initImg();
        holder.frameView.updateColor();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.settings:
                new Settings(this).show();
                break;
            case R.id.rotate_right:
                holder.imgView.rotateRight();
                break;
            case R.id.rotate_left:
                holder.imgView.rotateLeft();
                break;
            case R.id.mirror_x:
                holder.imgView.mirrorX();
                break;
            case R.id.mirror_y:
                holder.imgView.mirrorY();
                break;
            case R.id.fit_width:
                holder.imgView.fitWidth(true, false);
                break;
            case R.id.fit_height:
                holder.imgView.fitHeight(false, true);
                break;
            case R.id.previous:
                holder.imgView.previous();
                break;
            case R.id.next:
                holder.imgView.next();
                break;
            case R.id.save:
                holder.imgView.save();
                break;
            case R.id.save_as:
                holder.imgView.saveAs();
                break;
            case R.id.delete:
                holder.imgView.delete();
                break;
            case R.id.undo:
                holder.imgView.undo();
                break;
            case R.id.cleanup_backup:
                holder.imgView.cleanupBackup();
                break;
            case R.id.category:
                holder.categoryMenu.show();
                break;
            case R.id.info:
                new InfoDialog(this, R.layout.buttons);
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
