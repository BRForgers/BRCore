package cf.brforgers.core.lib.batch;

public abstract class ProfiledRunnable implements Runnable {
    public int tickTimeout = 2;

    private boolean shouldRunNext(long thisTickStarted) {
        if (tickTimeout == 0) return true;
        return System.currentTimeMillis() < (thisTickStarted + tickTimeout);
    }
}
