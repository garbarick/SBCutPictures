package ru.net.serbis.cut.pictures.view;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.bean.*;
import ru.net.serbis.cut.pictures.util.*;

public class FileImageView extends ImageView implements View.OnTouchListener
{
    private TextView nameView;
    private TextView widthView;
    private TextView heightView;
    private TextView scaleView;

    private List<File> files = new ArrayList<File>();
    private int position;
    private MatrixState state = new MatrixState();

    public FileImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setOnTouchListener(this);
        setScaleType(ScaleType.MATRIX);
    }

    public boolean onTouch(View view, MotionEvent event)
    {
        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
                state.startMove(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                state.startZoom(event);
                break;
            case MotionEvent.ACTION_UP:
                state.cancel();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                state.endMove(event);
                state.endZoom(event);
                break;
        }
        state.apply(this);
        setScaleView(state.getScale());
        return true;
    }

    public void init(Activity context)
    {
        nameView = UITool.get().findView(context, R.id.name);
        widthView = UITool.get().findView(context, R.id.width);
        heightView = UITool.get().findView(context, R.id.height);
        scaleView = UITool.get().findView(context, R.id.scale);
    }

    public void setFiles(List<File> files)
    {
        this.files.clear();
        this.files.addAll(files);
        position = 0;
        setFile();
    }

    private void clear()
    {
        setImageBitmap(null);
        state.reset(this);
    }

    private void setFile()
    {
        clear();
        if (files.isEmpty())
        {
            return;
        }
        File file = files.get(position);
        nameView.setText(file.getAbsolutePath());
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        setImageBitmap(bitmap);
        widthView.setText("width=" + bitmap.getWidth());
        heightView.setText("height=" + bitmap.getHeight());
        setScaleView(1);
        state.init(this);
    }

    public void next()
    {
        position = Math.min(files.size() - 1, ++ position);
        setFile();
    }

    public void previous()
    {
        position = Math.max(0, -- position);
        setFile();
    }

    private void setScaleView(float scale)
    {
        scaleView.setText(String.format("scale=%.2f", scale));
    }
}
