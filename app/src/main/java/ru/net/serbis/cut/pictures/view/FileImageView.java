package ru.net.serbis.cut.pictures.view;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.cut.pictures.bean.*;
import ru.net.serbis.cut.pictures.param.*;
import ru.net.serbis.cut.pictures.task.*;
import ru.net.serbis.cut.pictures.util.*;

public class FileImageView extends ImageView implements View.OnTouchListener, TaskCallback<Boolean>
{
    private ViewsHolder holder;
    private List<File> files = new ArrayList<File>();
    private List<File> undoFiles = new ArrayList<File>();
    private MatrixState state;
    private Position pos = new Position();

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

    public void init(ViewsHolder holder)
    {
        this.holder = holder;
        state = new MatrixState(holder);
    }

    @Override
    public void progress(int progress)
    {
        holder.progress.setProgress(progress);
    }

    @Override
    public void onResult(Boolean result, TaskError error)
    {
        UITool.get().enableAll(holder.main);
        UITool.get().toast(error);
    }

    public void setFiles(List<File> files)
    {
        this.files.clear();
        this.files.addAll(files);
        setFile();
    }

    public void clear()
    {
        holder.setNameView(null);
        setImageBitmap(null);
        state.reset();
        holder.setSizeView(0, 0);
        holder.setFileView(files, null);
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
        holder.setSizeView(bitmap.getWidth(), bitmap.getHeight());
        holder.setFileView(files, file);
        fitWidth(true, true);
    }

    private Bitmap getBitmap(File file)
    {
        holder.setNameView(file.getAbsolutePath());
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }
    
    private File getFile()
    {
        if (files.isEmpty())
        {
            return null;
        }
        return files.get(pos.get(files.size()));
    }

    public void next()
    {
        pos.next(files.size());
        setFile();
    }

    public void previous()
    {
        pos.previous();
        setFile();
    }

    public void rotate()
    {
        state.rotate(holder.frameView);
        fitWidth(true, true);
    }

    public void fitWidth(boolean moveX, boolean moveY)
    {
        state.fitWidth(holder.frameView);
        state.toCenter(moveX, moveY);
        state.apply();
    }

    public void mirror()
    {
        state.mirror(holder.frameView);
        state.apply();
    }

    public void save()
    {
        File file = getFile();
        if (file == null)
        {
            return;
        }
        File result = save(file);
        if (result == null)
        {
            return;
        }
        files.set(Params.POS.getValue(), result);
        deleteFileWithBackup(file);
    }

    private File save(File file)
    {
        Bitmap bitmap = getBitmap(file);
        RectF rect = new RectF(0, 0, holder.frameView.getWidth(), holder.frameView.getHeight());
        File result = new ImageSaver(bitmap, state, rect).save();
        if (result == null)
        {
            return null;
        }
        result.setLastModified(file.lastModified());
        return result;
    }

    private void deleteFileWithBackup(File file)
    {
        File backup = new File(Params.BACKUP_FOLDER.getValue(), file.getName());
        if (IOTool.get().moveFileQuietly(file, backup))
        {
            undoFiles.add(backup);
            setFile();
        }
    }

    public void saveAs()
    {
        File file = getFile();
        if (file == null)
        {
            return;
        }
        File result = save(file);
        if (result == null)
        {
            return;
        }
        files.add(Params.POS.getValue(), result);
        setFile();
    }

    public void delete()
    {
        File file = getFile();
        if (file == null)
        {
            return;
        }
        files.remove(file);
        deleteFileWithBackup(file);
    }

    public void undo()
    {
        if (undoFiles.isEmpty())
        {
            return;
        }
        int last = undoFiles.size() - 1;
        File undoFile = undoFiles.get(last);
        File file = new File(Params.SOURCE_FOLDER.getValue(), undoFile.getName());
        if (IOTool.get().moveFileQuietly(undoFile, file))
        {
            files.add(Params.POS.getValue(), file);
            undoFiles.remove(last);
            setFile();
        }
    }

    public void cleanupBackup()
    {
        undoFiles.clear();
        String dir = Params.BACKUP_FOLDER.getValue();
        UITool.get().disableAll(holder.main);
        new FolderCleanUpTask(this).execute(dir);
    }
}
