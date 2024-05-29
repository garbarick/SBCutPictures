package ru.net.serbis.cut.pictures.view;

import android.app.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.param.*;
import ru.net.serbis.cut.pictures.util.*;

public class ViewsHolder
{
    public LinearLayout main;
    public FileImageView img;
    public ProgressBar progress;
    public TextView nameView;
    public TextView widthView;
    public TextView heightView;
    public TextView scaleView;
    public FrameView frameView;
    public TextView fileSizeView;
    public TextView countAllView;
    public PopupMenu categoryMenu;
    
    public void init(Activity context)
    {
        main = UITool.get().findView(context, R.id.main);
        img = UITool.get().findView(context, R.id.img);
        progress = UITool.get().findView(context, R.id.progress);
        nameView = UITool.get().findView(context, R.id.name);
        widthView = UITool.get().findView(context, R.id.width);
        heightView = UITool.get().findView(context, R.id.height);
        scaleView = UITool.get().findView(context, R.id.scale);
        frameView = UITool.get().findView(context, R.id.frame);
        fileSizeView = UITool.get().findView(context, R.id.file_size);
        countAllView = UITool.get().findView(context, R.id.count_all);

        ImageButton category = UITool.get().findView(context, R.id.category);
        categoryMenu = new PopupMenu(context, category);
        updateCategories();
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

    public void setFileView(List<File> files, File file)
    {
        if (file == null)
        {
            fileSizeView.setText(null);
            countAllView.setText(null);
        }
        else
        {
            fileSizeView.setText(Strings.get().get(R.string.file_size, file.length()/1024f));
            countAllView.setText((Params.POS.getValue() + 1) + "/" + files.size());
        }
    }

    public void updateCategories()
    {
        Menu menu = categoryMenu.getMenu();
        menu.clear();
        String current = Params.CATEGORY.getValue();
        for (String category : Params.CATEGORIES.getValue())
        {
            menu.add(category).setCheckable(true).setChecked(category.equals(current));
        }
    }

    public void checkCategory(MenuItem item)
    {
        boolean checked = !item.isChecked();
        Menu menu = categoryMenu.getMenu();
        for (int i = 0; i < menu.size(); i ++)
        {
            menu.getItem(i).setChecked(false);
        }
        item.setChecked(checked);
        Params.CATEGORY.saveValue(checked ? item.getTitle().toString() : "");
    }
}
