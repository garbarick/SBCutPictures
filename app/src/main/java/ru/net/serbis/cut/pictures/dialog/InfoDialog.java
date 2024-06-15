package ru.net.serbis.cut.pictures.dialog;

import android.app.*;
import android.content.*;
import ru.net.serbis.cut.pictures.*;
import android.widget.*;
import ru.net.serbis.cut.pictures.adapter.*;

public class InfoDialog extends AlertDialog.Builder implements DialogInterface.OnClickListener
{
    public InfoDialog(Context context)
    {
        super(context);
        setTitle(R.string.info);
        ListView list = new ListView(context);
        InfoAdapter adapter = new InfoAdapter(context);
        list.setAdapter(adapter);
        setView(list);
        setNegativeButton(android.R.string.cancel, this);
        show();
    }

    @Override
    public void onClick(DialogInterface dialog, int id)
    {
    }
}
