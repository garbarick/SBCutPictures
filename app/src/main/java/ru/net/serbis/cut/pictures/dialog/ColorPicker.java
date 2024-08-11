package ru.net.serbis.cut.pictures.dialog;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.bean.*;
import ru.net.serbis.utils.*;

public abstract class ColorPicker extends AlertDialog.Builder implements DialogInterface.OnClickListener, SeekBar.OnSeekBarChangeListener
{
    private View view;
    private TextView colorView;

    private static final Holder<Integer, Integer> MAP = new Holder<Integer, Integer>()
    {
        {
            put(R.id.color_a, R.id.color_a_value);
            put(R.id.color_r, R.id.color_r_value);
            put(R.id.color_g, R.id.color_g_value);
            put(R.id.color_b, R.id.color_b_value);
        }
    };

    public ColorPicker(Context context, int color)
    {
        super(context);

        setTitle(R.string.color_picker);
        view = LayoutInflater.from(context).inflate(R.layout.color_picker, null, false);
        colorView = UITool.get().findView(view, R.id.color);
        colorView.setBackgroundColor(color);

        initSeekBar(R.id.color_a, R.id.color_a_value, Color.alpha(color));
        initSeekBar(R.id.color_r, R.id.color_r_value, Color.red(color));
        initSeekBar(R.id.color_g, R.id.color_g_value, Color.green(color));
        initSeekBar(R.id.color_b, R.id.color_b_value, Color.blue(color));

		setView(view);
        setPositiveButton(android.R.string.ok,this);
        setNegativeButton(android.R.string.cancel, this);
		show();
    }

    private void initSeekBar(int seekId, int valueId, int value)
    {
        initSeekBar(seekId, value);
        initColorPart(valueId, value);
    }

    private void initSeekBar(int id, int value)
    {
        SeekBar bar = UITool.get().findView(view, id);
        bar.setMax(255);
        bar.setProgress(value);
        bar.setOnSeekBarChangeListener(this);
    }

    private void initColorPart(int id, int value)
    {
        TextView part = UITool.get().findView(view, id);
        part.setText("" + value);
    }

    @Override
    public void onClick(DialogInterface dialog, int id)
    {
        switch(id)
        {
            case Dialog.BUTTON_POSITIVE:
                positive();
                break;
        }
    }

    private void positive()
    {
        ColorDrawable color = (ColorDrawable) colorView.getBackground();
        updateValue(color.getColor());
    }

    public abstract void updateValue(Integer value)

    @Override
    public void onProgressChanged(SeekBar seek, int progress, boolean byUser)
    {
        if (byUser)
        {
            initColorPart(MAP.get(seek.getId()), progress);
            updateColor();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seek)
    {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seek)
    {
    }

    private void updateColor()
    {
        int a = getColorPart(R.id.color_a_value);
        int r = getColorPart(R.id.color_r_value);
        int g = getColorPart(R.id.color_g_value);
        int b = getColorPart(R.id.color_b_value);
        colorView.setBackgroundColor(Color.argb(a, r, g, b));
    }

    private int getColorPart(int id)
    {
        TextView part = UITool.get().findView(view, id);
        return Integer.valueOf(part.getText().toString());
    }
}
