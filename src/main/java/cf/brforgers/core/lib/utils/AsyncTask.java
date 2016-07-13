package cf.brforgers.core.lib.utils;

import java.util.concurrent.Callable;

public class AsyncTask<T> implements Runnable {
    private static int count = 0;
    public final int taskId = count++;
    public Callable<T> task = null;
    public T result = null;
    public boolean ended = false;
    public boolean crashed = false;
    public Exception exception;
    private Function<T> finishedTrigger = null;
    private Function<Exception> onCrash = null;

    public AsyncTask(Callable<T> task) {

        this.task = task;
    }

    public AsyncTask<T> setTask(Callable<T> task) {
        this.task = task;

        return this;
    }

    public AsyncTask<T> setFinishTrigger(Function<T> trigger) {
        finishedTrigger = trigger;

        return this;
    }

    public AsyncTask<T> setCrashFallback(Function<Exception> crashHandler) {
        onCrash = crashHandler;
        return this;
    }

    public AsyncTask<T> startTaskOnNewThread() {
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
