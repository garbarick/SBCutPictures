package ru.net.serbis.cut.pictures.dialog;

import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.adapter.*;
import ru.net.serbis.cut.pictures.param.*;
import ru.net.serbis.cut.pictures.util.*;

public abstract class StringsDialog extends AlertDialog.Builder implements DialogInterface.OnClickListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener, DialogInterface.OnDismissListener
{
    private Activity context;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private PopupMenu menu;

    public StringsDialog(Activity context, Set<String> values)
    {
        super(context);
        this.context = context;
        setTitle(R.string.edit_values);
        list = new ListView(context);
        setView(list);

        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_activated_1);
        adapter.addAll(values);
        list.setAdapter(adapter);

        setNeutralButton(" ", null);
        setNegativeButton(R.string.close, this);
        setOnDismissListener(this);

        show();
    }

    @Override
    public AlertDialog show()
    {
        AlertDialog dialog = super.show();
        initButtons(dialog);
        return dialog;
    }

    public void initButtons(AlertDialog dialog)
    {
        Button neutral = dialog.getButton(Dialog.BUTTON_NEUTRAL);
        neutral.setId(Dialog.BUTTON_NEUTRAL);
        UITool.get().setSandwitchView(neutral);
        neutral.setOnClickListener(this);

        menu = new PopupMenu(getContext(), neutral);
        menu.getMenuInflater().inflate(R.menu.add_edit_delete, menu.getMenu());
        menu.setOnMenuItemClickListener(this);
    }

    @Override
    public void onClick(DialogInterface dialog, int id)
    {
        switch (id)
        {
            case Dialog.BUTTON_NEUTRAL:
                menu.show();
                break;
        }
    }

    @Override
    public void onClick(View view)
    {
        onClick(null, view.getId());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.add:
                add();
                return true;
            case R.id.edit:
                edit();
                return true;
            case R.id.delete:
                delete();
                return true;
        }
        return false;
    }

    private void add()
    {
        edit("", true);
    }

    private void edit()
    {
        int checked = list.getCheckedItemPosition();
        if (checked == -1)
        {
            return;
        }
        edit(adapter.getItem(checked), false);
    }

    private void edit(String value, final boolean add)
    {
        final Map<Integer, Param> params = new LinkedHashMap<Integer, Param>();
        params.put(R.string.value, new EditTextParam(Strings.get().get(R.string.value), value, false));
        Param[] paramsArray = params.values().toArray(new Param[params.size()]);

        new ParamsDialog(context, add ? R.string.add: R.string.edit, paramsArray, true, false)
        {
            @Override
            public void ok(ParamsAdapter adapter)
            {
                update(adapter, add, params);
            }
        }.show();
    }

    private void delete()
    {
        int checked = list.getCheckedItemPosition();
        if (checked == -1)
        {
            return;
        }
        adapter.remove(adapter.getItem(checked));
    }

    private void update(ParamsAdapter paramsAdapter, boolean add, Map<Integer, Param> params)
    {
        String value = paramsAdapter.getValue(params.get(R.string.value));
        if (!add)
        {
            delete();
        }
        adapter.add(value);
        adapter.sort(
            new Comparator<String>()
            {
                @Override
                public int compare(String p1, String p2)
                {
                    return p1.compareTo(p2);
                }
            }
        );
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        Set<String> values = new TreeSet<String>();
        for (int i = 0; i < adapter.getCount(); i++)
        {
            values.add(adapter.getItem(i));
        }
        updateValues(values);
    }

    public abstract void updateValues(Set<String> values)
}
