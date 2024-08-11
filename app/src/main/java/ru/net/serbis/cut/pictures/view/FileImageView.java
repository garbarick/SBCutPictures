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
import ru.net.serbis.utils.*;
import ru.net.serbis.utils.bean.*;

public class FileImageView extends ImageView implements View.OnTouchListener
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
        holder.setSizeView(null);
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
        holder.frameView.failed(bitmap);
        setImageBitmap(bitmap);
        holder.setSizeView(bitmap);
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
        pos.previous(files.size());
        setFile();
    }

    public void rotateRight()
    {
        state.rotateRight();
        fitWidth(true, true);
    }

    public void rotateLeft()
    {
        state.rotateLeft();
        fitWidth(true, true);
    }

    public void fitWidth(boolean moveX, boolean moveY)
    {
        state.fitWidth();
        state.toCenter(moveX, moveY);
        state.apply();
    }

    public void fitHeight(boolean moveX, boolean moveY)
    {
        state.fitHeight();
        state.toCenter(moveX, moveY);
        state.apply();
    }

    public void mirrorX()
    {
        state.mirrorX();
        state.toCenter(true, true);
        state.apply();
    }

    public void mirrorY()
    {
        state.mirrorY();
        state.toCenter(true, true);
        state.apply();
    }

    public void save()
    {
        final File file = getFile();
        if (file == null)
        {
            return;
        }
        save(
            file,
            new Result<File>()
            {
                public void result(File result)
                {
                    files.set(Params.POS.getValue(), result);
                    deleteFileWithBackup(file);
                    setFile();
                }
            }
        );
    }

    private void save(final File file, final Result<File> fileResult)
    {
        UITool.get().disableAll(holder.main);
        RectF rect = new RectF(0, 0, holder.frameView.getWidth(), holder.frameView.getHeight());
        new ImageSaveTask(
            state,
            rect,            
            new TaskCallback<File>()
            {
                @Override
                public void progress(int progress)
                {
                }

                @Override
                public void onResult(File result, TaskError error)
                {
                    UITool.get().enableAll(holder.main);
                    UITool.get().toast(error);
                    if (result != null)
                    {
                        result.setLastModified(file.lastModified());
                        fileResult.result(result);
                    }
                }
            }).execute(file);
    }

    private void deleteFileWithBackup(File file)
    {
        File backup = new File(Params.BACKUP_FOLDER.getValue(), file.getName());
        if (FileUtil.get().moveFileQuietly(file, backup))
        {
            undoFiles.add(backup);
        }
    }

    public void saveAs()
    {
        File file = getFile();
        if (file == null)
        {
            return;
        }
        save(
            file,
            new Result<File>()
            {
                public void result(File result)
                {
                    files.add(Params.POS.getValue(), result);
                    setFile();
                }
            }
        );
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
        setFile();
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
        if (FileUtil.get().moveFileQuietly(undoFile, file))
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
        new FolderCleanUpTask(
            new TaskCallback<Boolean>()
            {
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
            }
        ).execute(dir);
    }
}
