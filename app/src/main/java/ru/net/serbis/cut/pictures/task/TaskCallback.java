package ru.net.serbis.cut.pictures.task;

public interface TaskCallback<R>
{
    void progress(int progress);
    void onResult(R result, TaskError error);
}
