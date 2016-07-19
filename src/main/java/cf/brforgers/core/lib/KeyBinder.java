package cf.brforgers.core.lib;

import cf.brforgers.core.lib.utils.Function;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.ArrayList;
import java.util.List;

public class KeyBinder {
    private static KeyBinder instance = null;
    private List<KeyBind> keyBinds = new ArrayList<KeyBind>();

    private KeyBinder() {
    }

    public static void register(KeyBind binding) {
        getInstance().keyBinds.add(binding);
    }

    public static KeyBinder getInstance() {
        if (instance == null) {
            instance = new KeyBinder();
            MinecraftForge.EVENT_BUS.register(instance);
        }
        return instance;
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        for (KeyBind keyBind : keyBinds) {
            if (keyBind.mapping.isPressed() != keyBind.state) {
                keyBind.state = keyBind.mapping.isPressed();
                keyBind.runOnTrigger.run(keyBind.state);
            }
        }

    }

    public static class KeyBind {
        public final Function<Boolean> runOnTrigger;
        public final KeyBinding mapping;
        private boolean state;

        public KeyBind(String bindingName, String category, int key) {
            this(bindingName, category, key, (Function<Boolean>) null);
        }

        public KeyBind(String bindingName, String category, int key, Runnable runOnTrigger) {
            this(bindingName, category, key, Utils.toFunction(runOnTrigger, Boolean.class));
        }

        public KeyBind(String bindingName, String category, int key, Function<Boolean> runOnTrigger) {

            this.runOnTrigger = runOnTrigger;
            mapping = new KeyBinding(bindingName, key, category);
        }
    }
}