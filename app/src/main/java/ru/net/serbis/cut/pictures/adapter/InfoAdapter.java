package ru.net.serbis.cut.pictures.adapter;

import android.content.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.util.*;
import ru.net.serbis.cut.pictures.sort.*;

public class InfoAdapter extends ArrayAdapter<ImageButton>
{
    public InfoAdapter(Context context)
    {
        super(context, android.R.layout.simple_list_item_1);
        initItems();
    }

    private void initItems()
    {
        ViewGroup group = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.buttons, null, false);
        for (int i = 0; i < group.getChildCount(); i ++)
        {
            View child = group.getChildAt(i);
            if (child instanceof ImageButton)
            {
                add((ImageButton) child);
            }
        }
        sort(new ImageButtonSorter());
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        ImageButton button = getItem(position);
        view = LayoutInflater.from(getContext()).inflate(R.layout.resource_img, parent, false);
        TextView text = UITool.get().findView(view, R.id.name);
        text.setText(button.getTooltipText());
        ImageView img = UITool.get().findView(view, R.id.img);
        img.setBackground(button.getDrawable());
        return view;
    }
}
