package cf.brforgers.core.lib.utils;

import java.util.concurrent.Callable;

/**
 * Created by adria on 08/07/2016.
 */
public class AsyncTask<T> implements Runnable {
    private static int count = 0;
    public final int taskId = count++;
    public Callable<T> task = null;
    public T result = null;
    public boolean ended = false;
    public boolean crashed = false;
    public Exception exception;
    private PRunnable<T> finishedTrigger = null;
    private PRunnable<Exception> onCrash = null;

    public AsyncTask(Callable<T> task) {

        this.task = task;
    }

    public AsyncTask<T> SetTask(Callable<T> task) {
        this.task = task;

        return this;
    }

    public AsyncTask<T> SetFinishTrigger(PRunnable<T> trigger) {
        finishedTrigger = trigger;

        return this;
    }

    public AsyncTask<T> SetCrashFallback(PRunnable<Exception> crashHandler) {
        onCrash = crashHandler;
        return this;
    }

    public AsyncTask<T> StartTaskOnNewThread() {
        new Thread(this, "AsyncTask" + taskId).run();
        return this;

    }

    @Override
    public void run() {
        try {
            if (task != null) {
                result = task.call();
            }
            ended = true;
            if (finishedTrigger != null) {
                finishedTrigger.run(result);
            }
        } catch (Exception e) {
            exception = e;
            crashed = true;
            if (onCrash != null) onCrash.run(e);
        }
    }
}
