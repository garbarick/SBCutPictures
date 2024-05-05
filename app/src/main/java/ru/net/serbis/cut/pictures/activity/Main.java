package ru.net.serbis.cut.pictures.activity;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.task.*;
import ru.net.serbis.cut.pictures.util.*;

public class Main extends Activity implements TaskCallback<List<File>>, View.OnClickListener
{
    private LinearLayout main;
    private TextView fileName;
    private ImageView img;
    private ProgressBar progress;
    private List<File> files = new ArrayList<File>();
    private int position;

    @Override
    protected void onCreate(Bundle state)
    {
        super.onCreate(state);
        SysTool.get().initPermissions(this);
        setContentView(R.layout.main);

        main = UITool.get().findView(this, R.id.main);
        fileName = UITool.get().findView(this, R.id.file);
        img = UITool.get().findView(this, R.id.img);
        progress = UITool.get().findView(this, R.id.progress);
        UITool.get().initButtons(this, this, R.id.settings, R.id.previous, R.id.next);

        String dir = IOTool.get().getDownloadPath();
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
        files.clear();
        files.addAll(result);

        position = 0;
        setFile();
    }

    private void setFile()
    {
        img.setImageBitmap(null);
        if (files.isEmpty())
        {
            return;
        }
        File file = files.get(position);
        fileName.setText(file.getAbsolutePath());
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        img.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.settings:
                break;
            case R.id.previous:
                position --;
                position = Math.max(0, position);
                setFile();
                break;
            case R.id.next:
                position ++;
                position = Math.min(files.size() - 1, position);
                setFile();
                break;
        }
    }
}
