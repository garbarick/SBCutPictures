package ru.net.serbis.cut.pictures.param;

import android.text.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.dialog.*;
import ru.net.serbis.cut.pictures.listener.*;

public class FileParam extends TextViewParam
{
    private boolean onlyFolder;
    private boolean onlyFile;
    private String ext;

    public FileParam(int nameId, String defaultValue, boolean onlyFolder, boolean onlyFile, String ext)
    {
        super(nameId, defaultValue);
        this.onlyFolder = onlyFolder;
        this.onlyFile = onlyFile;
        this.ext = ext;
    }

    public FileParam(int nameId, String defaultValue, boolean onlyFolder, boolean onlyFile)
    {
        this(nameId, defaultValue, onlyFolder, onlyFile, null);
    }

    @Override
    public void initViewValue(View parent)
    {
        super.initViewValue(parent);

        TextView view = getViewValue(parent);
        view.setOnClickListener(
            new ViewOnClickListener<TextView>()
            {
                @Override
                public void onClickView(final TextView view)
                {
                    String path = getValue(view);
                    File dir = TextUtils.isEmpty(path) ? null : new File(path);
                    dir.mkdirs();
                    new FileChooser(context, R.string.choose_folder, onlyFolder, onlyFile, dir, ext)
                    {
                        @Override
                        public void onChoose(String path)
                        {
                            FileParam.this.onChoose(view, path);
                        }
                    };
                }
            }
        );
    }

    protected void onChoose(TextView view, String path)
    {
        view.setText(path);
    }
}
