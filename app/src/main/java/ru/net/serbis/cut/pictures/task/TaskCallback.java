package ru.net.serbis.cut.pictures.task;

import ru.net.serbis.utils.bean.*;

public interface TaskCallback<R>
{
    void progress(int progress);
    void onResult(R result, TaskError error);
}
