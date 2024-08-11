package ru.net.serbis.cut.pictures.param;

import android.view.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.utils.*;

public class SeekBarParam extends NumberParam<SeekBar>
{
    private int max;

    public SeekBarParam(int nameId, int max, int defaultValue)
    {
        super(nameId, defaultValue);
        this.max = max;
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.param_seekbar;
    }

    @Override
    public void initViewValue(View parent)
    {
        SeekBar view = getViewValue(parent);
        view.setMax(max);
        setValue(view, getValue());
        
        final TextView viewValue = UITool.get().findView(parent, R.id.view_value);
        viewValue.setText(getValue().toString());
        view.setOnSeekBarChangeListener(
            new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onProgressChanged(SeekBar seek, int progress, boolean byUser)
                {
                    if (byUser)
                    {
                        viewValue.setText(String.valueOf(progress));
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
            }
        );
    }

    @Override
    public void setValue(SeekBar view, Integer value)
    {
        view.setProgress(value);
    }

    @Override
    public Integer getValue(SeekBar view)
    {
        return view.getProgress();
    }
}
