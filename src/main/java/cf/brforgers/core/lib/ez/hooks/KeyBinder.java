package cf.brforgers.core.lib.ez.hooks;

import net.minecraft.client.settings.KeyBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KeyBinder {
    private static final List<KeyBind> keyBinds = new ArrayList<KeyBind>(), readOnly = Collections.unmodifiableList(keyBinds);

    private KeyBinder() {
    }

    public static List<KeyBind> getKeyBinds() {
        return readOnly;
    }

    public static void register(KeyBind binding) {
        keyBinds.add(binding);
    }

    public abstract static class KeyBind implements Runnable {
        public final KeyBinding mapping;
        public boolean state;

        public KeyBind(String bindingName, String category, int key) {
            mapping = new KeyBinding(bindingName, key, category);
        }
    }
}