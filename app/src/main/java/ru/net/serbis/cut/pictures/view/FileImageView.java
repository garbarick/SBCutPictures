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
import ru.net.serbis.cut.pictures.param.*;
import ru.net.serbis.cut.pictures.util.*;

public class FileImageView extends ImageView implements View.OnTouchListener
{
    private TextView nameView;
    private TextView widthView;
    private TextView heightView;
    private TextView scaleView;
    private FrameView parent;

    private List<File> files = new ArrayList<File>();
    private MatrixState state = new MatrixState(this);

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
        state.apply();
        return true;
    }

    public void init(Activity context)
    {
        nameView = UITool.get().findView(context, R.id.name);
        widthView = UITool.get().findView(context, R.id.width);
        heightView = UITool.get().findView(context, R.id.height);
        scaleView = UITool.get().findView(context, R.id.scale);
        parent = (FrameView) getParent();
    }

    public void setFiles(List<File> files)
    {
        this.files.clear();
        this.files.addAll(files);
        if (Params.POS.getValue() > files.size() - 1)
        {
            Params.POS.saveValue(0);
        }
        setFile();
    }

    public void clear()
    {
        setNameView(null);
        setImageBitmap(null);
        state.reset();
        setSizeView(0, 0);
    }

    private void setFile()
    {
        clear();
        File file = getFile();
        if (file == null)
        {
            return;
        }
        Bitmap bitmap = getBitmap(file);
        setImageBitmap(bitmap);
        setSizeView(bitmap.getWidth(), bitmap.getHeight());
        fitWidth(true, true);
    }

    private Bitmap getBitmap(File file)
    {
        setNameView(file.getAbsolutePath());
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }
    
    private File getFile()
    {
        if (files.isEmpty())
        {
            return null;
        }
        return files.get(Params.POS.getValue());
    }

    public void next()
    {
        Params.POS.saveValue(
            Math.min(
                files.size() - 1,
                Params.POS.getValue() + 1));
        setFile();
    }

    public void previous()
    {
        Params.POS.saveValue(
            Math.max(0, Params.POS.getValue() - 1));
        setFile();
    }

    public void setNameView(String text)
    {
        nameView.setText(text);
    }

    public void setSizeView(int width, int height)
    {
        widthView.setText(Strings.get().get(R.string.width_value, width));
        heightView.setText(Strings.get().get(R.string.height_value, height));
    }

    public void setScaleView(float scale)
    {
        scaleView.setText(Strings.get().get(R.string.scale_value, scale));
    }

    public void rotate()
    {
        state.rotate(parent);
        fitWidth(true, true);
    }

    public void fitWidth(boolean moveX, boolean moveY)
    {
        state.fitWidth(parent);
        state.toCenter(moveX, moveY);
        state.apply();
    }

    public void mirror()
    {
        state.mirror(parent);
        state.apply();
    }

    public void save()
    {
        File file = getFile();
        if (file == null)
        {
            return;
        }
        Bitmap bitmap = getBitmap(file);
        RectF rect = new RectF(0, 0, parent.getWidth(), parent.getHeight());
        File result = new ImageSaver(bitmap, state, rect).save();
        if (result == null)
        {
            return;
        }
        result.setLastModified(file.lastModified());
        File backup = new File(Params.BACKUP_FOLDER.getValue(), file.getName());
        if (IOTool.get().moveFileQuietly(file, backup))
        {
            files.set(Params.POS.getValue(), result);
            setFile();
        }
    }
}
