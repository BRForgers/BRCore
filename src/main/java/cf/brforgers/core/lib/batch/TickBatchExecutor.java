package cf.brforgers.core.lib.batch;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class TickBatchExecutor {
    private TickBatchExecutor() {
    }

    public abstract static class Base extends BatchExecutor {
        public boolean runOnTickStart = true;
        public boolean runOnTickEnd = true;

        public void tickRun(TickEvent.Phase phase) {
            if (phase == TickEvent.Phase.START && runOnTickStart) run();
            if (phase == TickEvent.Phase.END && runOnTickEnd) run();
        }

        public void run() {
            tickTimeout = MathHelper.clamp_int(tickTimeout, 1, 4);
            super.run();
        }
    }

    public static class Server extends Base {
        @SubscribeEvent
        public void serverTick(TickEvent.ServerTickEvent e) {
            tickRun(e.phase);
        }
    }

    public static class Client extends Base {
        @SubscribeEvent
        public void clientTick(TickEvent.ClientTickEvent e) {
            tickRun(e.phase);
        }
    }

    public static class World extends Base {
        @SubscribeEvent
        public void worldTick(TickEvent.WorldTickEvent e) {
            tickRun(e.phase);
        }
    }

    public static class Player extends Base {
        @SubscribeEvent
        public void worldTick(TickEvent.PlayerTickEvent e) {
            tickRun(e.phase);
        }
    }
}