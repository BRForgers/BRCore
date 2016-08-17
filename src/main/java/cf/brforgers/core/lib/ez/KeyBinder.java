package cf.brforgers.core.lib.ez;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class KeyBinder implements Runnable {
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

    public void run() {
        for (KeyBind keyBind : keyBinds) {
            if (keyBind.mapping.isPressed() != keyBind.state) {
                keyBind.state = keyBind.mapping.isPressed();
                keyBind.run();
            }
        }
    }

    public abstract static class KeyBind implements Runnable {
        public final KeyBinding mapping;
        private boolean state;

        public KeyBind(String bindingName, String category, int key) {
            mapping = new KeyBinding(bindingName, key, category);
        }
    }
}
