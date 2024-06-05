package ru.net.serbis.cut.pictures.param;

import android.view.*;
import android.widget.*;
import java.util.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.listener.*;
import ru.net.serbis.cut.pictures.util.*;
import ru.net.serbis.cut.pictures.dialog.*;

public class StringsParam extends Param<Set<String>, Button>
{
    private Set<String> values;

    public StringsParam(int nameId)
    {
        super(nameId, new TreeSet<String>());
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.param_button;
    }

    @Override
    public void initViewValue(View parent)
    {
        Button view = getViewValue(parent);
        setValue(view, getValue());
        view.setOnClickListener(
            new ViewOnClickListener<Button>()
            {
                @Override
                public void onClickView(final Button view)
                {
                    new StringsDialog(context, getValue(view))
                    {
                        @Override
                        public void updateValues(Set<String> values)
                        {
                            setValue(view, values);
                        }
                    };
                }
            }
        );
    }

    @Override
    public Set<String> getValue()
    {
        if (stored)
        {
            return Preferences.get().getStringSet(paramName);
        }
        return value;
    }

    @Override
    public void saveValue(Set<String> value)
    {
        if (stored)
        {
            Preferences.get().setStringSet(paramName, value);
        }
    }

    @Override
    public String typeToString(Set<String> value)
    {
        return "";
    }

    @Override
    public Set<String> stringToType(String value)
    {
        return values;
    }

    @Override
    public void setValue(Button button, Set<String> values)
    {
        this.values = values;
        button.setText(Strings.get().get(R.string.edit_values_, values.size()));
    }

    @Override
    public Set<String> getValue(Button view)
    {
        return values;
    }
}
