package cf.brforgers.core.lib.batch;

import java.util.*;

public abstract class BatchExecutor extends ProfiledRunnable {
    private static Random rnd = new Random();
    public boolean debug = false;
    public boolean discardRunnablesForEfficiencyOnOverload = false;
    public int runnableOverloadMark = 100000;
    private List<Runnable> executions = new ArrayList<Runnable>(), nextTickExecutions = new ArrayList<Runnable>();

    private boolean shouldExecuteRunnable(Runnable runnable) {
        if (runnable == null) return true;
        if (!discardRunnablesForEfficiencyOnOverload) return true;
        if (executions.size() < runnableOverloadMark) return true;
        return rnd.nextBoolean();
    }

    private boolean shouldRunNext(long thisTickStarted) {
        if (tickTimeout == 0) return true;
        return System.currentTimeMillis() < (thisTickStarted + tickTimeout);
    }

    /**
     * Add {@link Runnable}(s) that will be executed at least at Next Tick to the BatchRunnableExecutor<br>
     * Also, not sure if will actually run if surpass 100000 Runnables (It might skip some to actually accelerate)
     */
    public void addRunnablesToNextTick(Runnable... runnables) {
        nextTickExecutions.addAll(Arrays.asList(runnables));
    }

    /**
     * Add {@link Runnable}(s) that will be executed at least at This Tick to the BatchRunnableExecutor<br>
     * Also, not sure if will actually run if surpass 100000 Runnables (It might skip some to actually accelerate)
     */
    public void addRunnablesToThisTick(Runnable... runnables) {
        executions.addAll(Arrays.asList(runnables));
    }

    public void run() {
        if (nextTickExecutions.size() > 0) {
            executions.addAll(nextTickExecutions);
            nextTickExecutions = new ArrayList<Runnable>();
        } else if (executions.size() == 0) {
            return;
        }

        long thisTickStarted = System.currentTimeMillis();
        int runs = 0, skips = 0, total = executions.size();
        //noinspection SuspiciousMethodCalls
        executions.removeAll(Collections.singleton(null));

        for (int i = 0; i < executions.size(); i++) {
            //for (Iterator runnableIterator = executions.iterator(); runnableIterator.hasNext();) {
            Runnable runnable = (Runnable) executions.get(i);
            if (!shouldRunNext(thisTickStarted)) break;
            if (shouldExecuteRunnable(runnable)) {
                runnable.run();
                runs++;
            } else {
                skips++;
            }
            executions.remove(runnable);
        }

        if (debug)
            System.out.println("[BatchExecutor.Tick] Success: " + runs + "; Skipped: " + skips + "; Total " + total + ";");
    }
}
