package ru.net.serbis.cut.pictures.listener;

import android.view.*;

public abstract class ViewOnClickListener<V extends View> implements View.OnClickListener
{
    @Override
    public void onClick(View view)
    {
        onClickView((V) view);
    }

    protected abstract void onClickView(V view)
}
